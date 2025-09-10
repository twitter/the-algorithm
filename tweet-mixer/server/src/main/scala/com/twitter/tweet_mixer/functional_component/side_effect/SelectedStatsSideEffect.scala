package com.twitter.tweet_mixer.functional_component.side_effect

import com.twitter.finagle.stats.StatsReceiver
import com.twitter.product_mixer.component_library.selector.Bucketer
import com.twitter.product_mixer.core.functional_component.side_effect.PipelineResultSideEffect
import com.twitter.product_mixer.core.model.common.identifier.SideEffectIdentifier
import com.twitter.product_mixer.core.model.common.presentation.CandidateWithDetails
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch
import com.twitter.tweet_mixer.feature.TweetInfoFeatures.HasVideo
import com.twitter.tweet_mixer.feature.TweetInfoFeatures.IsLongVideo
import com.twitter.tweet_mixer.feature.TweetInfoFeatures.isFeatureSet
import com.twitter.tweet_mixer.feature.AuthorIdFeature
import com.twitter.tweet_mixer.feature.HydraScoreFeature
import com.twitter.tweet_mixer.feature.TweetBooleanInfoFeature
import com.twitter.tweet_mixer.feature.HighQualitySourceTweet
import com.twitter.tweet_mixer.feature.HighQualitySourceUser
import com.twitter.tweet_mixer.feature.SourceSignalFeature
import com.twitter.tweet_mixer.functional_component.hydrator.SGSFollowedUsersFeature
import com.twitter.tweet_mixer.functional_component.transformer.ReplyFeature
import com.twitter.tweet_mixer.model.response.TweetMixerResponse
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.ExperimentBucketIdentifierParam
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.BlendingParam
import javax.inject.Inject
import javax.inject.Singleton
import com.twitter.tweet_mixer.feature.USSFeatures

/**
 * Side effect that calls hydra dark traffic to score tweets.
 */
@Singleton
class SelectedStatsSideEffect @Inject() (
  statsReceiver: StatsReceiver)
    extends PipelineResultSideEffect[PipelineQuery, TweetMixerResponse] {

  override val identifier: SideEffectIdentifier = SideEffectIdentifier("SelectedStats")

  val scopedStats = statsReceiver.scope("SelectedStats")

  def isVideo(candidateWithDetails: CandidateWithDetails): Boolean = {
    isFeatureSet(
      HasVideo,
      candidateWithDetails.features.getOrElse(TweetBooleanInfoFeature, None).getOrElse(0)
    )
  }

  def isLongVideo(candidateWithDetails: CandidateWithDetails): Boolean = {
    isFeatureSet(
      IsLongVideo,
      candidateWithDetails.features.getOrElse(TweetBooleanInfoFeature, None).getOrElse(0)
    )
  }

  override def apply(
    inputs: PipelineResultSideEffect.Inputs[PipelineQuery, TweetMixerResponse]
  ): Stitch[Unit] = {
    val query = inputs.query
    val allCandidates = (inputs.selectedCandidates ++ inputs.droppedCandidates)
      .groupBy(_.candidateIdLong)
      .map { case (candidateId, candidateSeq) => candidateSeq.head }
      .toSeq
    val selectedCandidates = inputs.selectedCandidates

    val videoSelectedCandidates = selectedCandidates.filter(isVideo)
    val longVideoSelectedCandidates =
      selectedCandidates.filter(candidate => isLongVideo(candidate) && isVideo(candidate))
    val replySelectedCandidates = selectedCandidates.filter { candidate =>
      candidate.features.getOrElse(ReplyFeature, false)
    }

    val authorIds = selectedCandidates.map(_.features.getOrElse(AuthorIdFeature, None))
    val followedUserIds =
      query.features.map(_.getOrElse(SGSFollowedUsersFeature, Nil)).getOrElse(Nil).toSet
    val inNetworkAuthors = authorIds.filter(_.exists(followedUserIds.contains(_)))

    val unscoredCandidates = allCandidates.filter { candidate =>
      candidate.features.getOrElse(HydraScoreFeature, Map.empty[String, Double]).isEmpty
    }

    // Fetch high quality source signals
    val highQualitySourceTweetSignals =
      USSFeatures.getSignals[Long](query, Set(HighQualitySourceTweet))
    val highQualitySourceUserSignals =
      USSFeatures.getSignals[Long](query, Set(HighQualitySourceUser))

    val selectedSourceSignals = selectedCandidates.map { candidate =>
      candidate.features.getOrElse(SourceSignalFeature, 0L)
    }

    // Group candidates by pipeline with their source signals
    val candidatesByPipeline = selectedCandidates
      .zip(selectedSourceSignals)
      .groupBy { case (candidate, _) => Bucketer.ByCandidateSource.apply(candidate) }

    val hasHighQualitySignals =
      highQualitySourceTweetSignals.nonEmpty || highQualitySourceUserSignals.nonEmpty

    val scope = query.params(ExperimentBucketIdentifierParam)

    val blendingStrategy = query.params(BlendingParam)

    Stitch.value {
      scopedStats.scope(scope).counter("AllCandidates").incr(allCandidates.size)
      scopedStats.scope(scope).counter("SelectedCandidates").incr(selectedCandidates.size)
      scopedStats
        .scope(scope).counter("SelectedVideoCandidates").incr(videoSelectedCandidates.size)
      scopedStats
        .scope(scope).counter("SelectedLongVideoCandidates").incr(longVideoSelectedCandidates.size)
      scopedStats.scope(scope).counter("SelectedReplyCandidates").incr(replySelectedCandidates.size)
      scopedStats
        .scope(scope).counter("SelectedInNetworkCandidates").incr(inNetworkAuthors.size)
      scopedStats
        .scope(scope).counter("UnscoredCandidates").incr(unscoredCandidates.size)

      if (hasHighQualitySignals) {
        candidatesByPipeline.foreach {
          case (pipelineId, candidatesWithSignals) =>
            val pipelineScope =
              scopedStats.scope(scope).scope(blendingStrategy.toString).scope(pipelineId.name)
            val signals = candidatesWithSignals.map(_._2)
            pipelineScope
              .counter("HighQualitySignalsPresentSelectedCandidates")
              .incr(candidatesWithSignals.size)
            // Track number of candidates belonging to each signal type
            pipelineScope
              .counter("SelectedHighQualitySourceTweetCandidates")
              .incr(signals.count(highQualitySourceTweetSignals.contains))
            pipelineScope
              .counter("SelectedHighQualitySourceUserCandidates")
              .incr(signals.count(highQualitySourceUserSignals.contains))
            // Track unique number of signals used to retrieve candidates
            pipelineScope
              .counter("SelectedHighQualitySourceTweetUniqueSignals")
              .incr(signals.filter(highQualitySourceTweetSignals.contains).toSet.size)
            pipelineScope
              .counter("SelectedHighQualitySourceUserUniqueSignals")
              .incr(signals.filter(highQualitySourceUserSignals.contains).toSet.size)
        }
      }
    }
  }
}
