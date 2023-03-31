package com.twitter.home_mixer.functional_component.side_effect

import com.twitter.finagle.stats.StatsReceiver
import com.twitter.home_mixer.model.HomeFeatures.AuthorIdFeature
import com.twitter.home_mixer.model.HomeFeatures.CandidateSourceIdFeature
import com.twitter.home_mixer.model.HomeFeatures.InNetworkFeature
import com.twitter.home_mixer.model.HomeFeatures.InReplyToTweetIdFeature
import com.twitter.home_mixer.model.HomeFeatures.IsRetweetFeature
import com.twitter.home_mixer.param.HomeGlobalParams.AuthorListForStatsParam
import com.twitter.home_mixer.util.CandidatesUtil
import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.functional_component.side_effect.PipelineResultSideEffect
import com.twitter.product_mixer.core.model.common.identifier.SideEffectIdentifier
import com.twitter.product_mixer.core.model.common.presentation.CandidateWithDetails
import com.twitter.product_mixer.core.model.common.presentation.ItemCandidateWithDetails
import com.twitter.product_mixer.core.model.marshalling.response.urt.Timeline
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ServedStatsSideEffect @Inject() (statsReceiver: StatsReceiver)
    extends PipelineResultSideEffect[PipelineQuery, Timeline] {

  override val identifier: SideEffectIdentifier = SideEffectIdentifier("ServedStats")

  private val baseStatsReceiver = statsReceiver.scope(identifier.toString)
  private val authorStatsReceiver = baseStatsReceiver.scope("Author")
  private val candidateSourceStatsReceiver = baseStatsReceiver.scope("CandidateSource")
  private val contentBalanceStatsReceiver = baseStatsReceiver.scope("ContentBalance")
  private val inNetworkStatsCounter = contentBalanceStatsReceiver.counter("InNetwork")
  private val outOfNetworkStatsCounter = contentBalanceStatsReceiver.counter("OutOfNetwork")

  override def apply(
    inputs: PipelineResultSideEffect.Inputs[PipelineQuery, Timeline]
  ): Stitch[Unit] = {
    val tweetCandidates = CandidatesUtil
      .getItemCandidates(inputs.selectedCandidates).filter(_.isCandidateType[TweetCandidate]())

    recordAuthorStats(tweetCandidates, inputs.query.params(AuthorListForStatsParam))
    recordCandidateSourceStats(tweetCandidates)
    recordContentBalanceStats(tweetCandidates)
    Stitch.Unit
  }

  def recordAuthorStats(candidates: Seq[CandidateWithDetails], authors: Set[Long]): Unit = {
    candidates
      .filter { candidate =>
        candidate.features.getOrElse(AuthorIdFeature, None).exists(authors.contains) &&
        // Only include original tweets
        (!candidate.features.getOrElse(IsRetweetFeature, false)) &&
        candidate.features.getOrElse(InReplyToTweetIdFeature, None).isEmpty
      }
      .groupBy { candidate =>
        (getCandidateSourceId(candidate), candidate.features.get(AuthorIdFeature).get)
      }
      .foreach {
        case ((candidateSourceId, authorId), authorCandidates) =>
          authorStatsReceiver
            .scope(authorId.toString).counter(candidateSourceId).incr(authorCandidates.size)
      }
  }

  def recordCandidateSourceStats(candidates: Seq[ItemCandidateWithDetails]): Unit = {
    candidates.groupBy(getCandidateSourceId).foreach {
      case (candidateSourceId, candidateSourceCandidates) =>
        candidateSourceStatsReceiver.counter(candidateSourceId).incr(candidateSourceCandidates.size)
    }
  }

  def recordContentBalanceStats(candidates: Seq[ItemCandidateWithDetails]): Unit = {
    val (in, oon) = candidates.partition(_.features.getOrElse(InNetworkFeature, true))
    inNetworkStatsCounter.incr(in.size)
    outOfNetworkStatsCounter.incr(oon.size)
  }

  private def getCandidateSourceId(candidate: CandidateWithDetails): String =
    candidate.features.getOrElse(CandidateSourceIdFeature, None).map(_.name).getOrElse("None")
}
