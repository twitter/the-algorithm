package com.X.home_mixer.product.scored_tweets.side_effect

import com.X.home_mixer.model.HomeFeatures.AncestorsFeature
import com.X.home_mixer.model.HomeFeatures.AuthorIdFeature
import com.X.home_mixer.model.HomeFeatures.AuthorIsBlueVerifiedFeature
import com.X.home_mixer.model.HomeFeatures.AuthorIsCreatorFeature
import com.X.home_mixer.model.HomeFeatures.AuthorIsGoldVerifiedFeature
import com.X.home_mixer.model.HomeFeatures.AuthorIsGrayVerifiedFeature
import com.X.home_mixer.model.HomeFeatures.AuthorIsLegacyVerifiedFeature
import com.X.home_mixer.model.HomeFeatures.CachedCandidatePipelineIdentifierFeature
import com.X.home_mixer.model.HomeFeatures.DirectedAtUserIdFeature
import com.X.home_mixer.model.HomeFeatures.ExclusiveConversationAuthorIdFeature
import com.X.home_mixer.model.HomeFeatures.InNetworkFeature
import com.X.home_mixer.model.HomeFeatures.InReplyToTweetIdFeature
import com.X.home_mixer.model.HomeFeatures.InReplyToUserIdFeature
import com.X.home_mixer.model.HomeFeatures.LastScoredTimestampMsFeature
import com.X.home_mixer.model.HomeFeatures.PerspectiveFilteredLikedByUserIdsFeature
import com.X.home_mixer.model.HomeFeatures.QuotedTweetIdFeature
import com.X.home_mixer.model.HomeFeatures.QuotedUserIdFeature
import com.X.home_mixer.model.HomeFeatures.SGSValidFollowedByUserIdsFeature
import com.X.home_mixer.model.HomeFeatures.SGSValidLikedByUserIdsFeature
import com.X.home_mixer.model.HomeFeatures.ScoreFeature
import com.X.home_mixer.model.HomeFeatures.SourceTweetIdFeature
import com.X.home_mixer.model.HomeFeatures.SourceUserIdFeature
import com.X.home_mixer.model.HomeFeatures.SuggestTypeFeature
import com.X.home_mixer.model.HomeFeatures.TopicContextFunctionalityTypeFeature
import com.X.home_mixer.model.HomeFeatures.TopicIdSocialContextFeature
import com.X.home_mixer.model.HomeFeatures.TweetUrlsFeature
import com.X.home_mixer.model.HomeFeatures.WeightedModelScoreFeature
import com.X.home_mixer.product.scored_tweets.model.ScoredTweetsResponse
import com.X.home_mixer.product.scored_tweets.param.ScoredTweetsParam.CachedScoredTweets
import com.X.home_mixer.service.HomeMixerAlertConfig
import com.X.home_mixer.{thriftscala => hmt}
import com.X.product_mixer.core.functional_component.marshaller.response.urt.metadata.TopicContextFunctionalityTypeMarshaller
import com.X.product_mixer.core.functional_component.side_effect.PipelineResultSideEffect
import com.X.product_mixer.core.model.common.identifier.SideEffectIdentifier
import com.X.product_mixer.core.model.common.presentation.CandidateWithDetails
import com.X.product_mixer.core.pipeline.PipelineQuery
import com.X.servo.cache.TtlCache
import com.X.stitch.Stitch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CachedScoredTweetsSideEffect @Inject() (
  scoredTweetsCache: TtlCache[Long, hmt.ScoredTweetsResponse])
    extends PipelineResultSideEffect[PipelineQuery, ScoredTweetsResponse] {

  override val identifier: SideEffectIdentifier = SideEffectIdentifier("CachedScoredTweets")

  private val MaxTweetsToCache = 1000

  def buildCachedScoredTweets(
    query: PipelineQuery,
    candidates: Seq[CandidateWithDetails]
  ): hmt.ScoredTweetsResponse = {
    val tweets = candidates.map { candidate =>
      val sgsValidLikedByUserIds =
        candidate.features.getOrElse(SGSValidLikedByUserIdsFeature, Seq.empty)
      val sgsValidFollowedByUserIds =
        candidate.features.getOrElse(SGSValidFollowedByUserIdsFeature, Seq.empty)
      val perspectiveFilteredLikedByUserIds =
        candidate.features.getOrElse(PerspectiveFilteredLikedByUserIdsFeature, Seq.empty)
      val ancestors = candidate.features.getOrElse(AncestorsFeature, Seq.empty)

      hmt.ScoredTweet(
        tweetId = candidate.candidateIdLong,
        authorId = candidate.features.get(AuthorIdFeature).get,
        // Cache the model score instead of the final score because rescoring is per-request
        score = candidate.features.getOrElse(WeightedModelScoreFeature, None),
        suggestType = candidate.features.getOrElse(SuggestTypeFeature, None),
        sourceTweetId = candidate.features.getOrElse(SourceTweetIdFeature, None),
        sourceUserId = candidate.features.getOrElse(SourceUserIdFeature, None),
        quotedTweetId = candidate.features.getOrElse(QuotedTweetIdFeature, None),
        quotedUserId = candidate.features.getOrElse(QuotedUserIdFeature, None),
        inReplyToTweetId = candidate.features.getOrElse(InReplyToTweetIdFeature, None),
        inReplyToUserId = candidate.features.getOrElse(InReplyToUserIdFeature, None),
        directedAtUserId = candidate.features.getOrElse(DirectedAtUserIdFeature, None),
        inNetwork = Some(candidate.features.getOrElse(InNetworkFeature, true)),
        sgsValidLikedByUserIds = Some(sgsValidLikedByUserIds),
        sgsValidFollowedByUserIds = Some(sgsValidFollowedByUserIds),
        topicId = candidate.features.getOrElse(TopicIdSocialContextFeature, None),
        topicFunctionalityType = candidate.features
          .getOrElse(TopicContextFunctionalityTypeFeature, None).map(
            TopicContextFunctionalityTypeMarshaller(_)),
        ancestors = if (ancestors.nonEmpty) Some(ancestors) else None,
        isReadFromCache = Some(true),
        streamToKafka = Some(false),
        exclusiveConversationAuthorId = candidate.features
          .getOrElse(ExclusiveConversationAuthorIdFeature, None),
        authorMetadata = Some(
          hmt.AuthorMetadata(
            blueVerified = candidate.features.getOrElse(AuthorIsBlueVerifiedFeature, false),
            goldVerified = candidate.features.getOrElse(AuthorIsGoldVerifiedFeature, false),
            grayVerified = candidate.features.getOrElse(AuthorIsGrayVerifiedFeature, false),
            legacyVerified = candidate.features.getOrElse(AuthorIsLegacyVerifiedFeature, false),
            creator = candidate.features.getOrElse(AuthorIsCreatorFeature, false)
          )),
        lastScoredTimestampMs = candidate.features
          .getOrElse(LastScoredTimestampMsFeature, Some(query.queryTime.inMilliseconds)),
        candidatePipelineIdentifier = candidate.features
          .getOrElse(CachedCandidatePipelineIdentifierFeature, Some(candidate.source.name)),
        tweetUrls = Some(candidate.features.getOrElse(TweetUrlsFeature, Seq.empty)),
        perspectiveFilteredLikedByUserIds = Some(perspectiveFilteredLikedByUserIds)
      )
    }

    hmt.ScoredTweetsResponse(tweets)
  }

  final override def apply(
    inputs: PipelineResultSideEffect.Inputs[PipelineQuery, ScoredTweetsResponse]
  ): Stitch[Unit] = {
    val candidates =
      (inputs.selectedCandidates ++ inputs.remainingCandidates ++ inputs.droppedCandidates)
        .filter(_.features.getOrElse(ScoreFeature, None).exists(_ > 0.0))

    val truncatedCandidates =
      if (candidates.size > MaxTweetsToCache)
        candidates
          .sortBy(-_.features.getOrElse(ScoreFeature, None).getOrElse(0.0)).take(MaxTweetsToCache)
      else candidates

    if (truncatedCandidates.nonEmpty) {
      val ttl = inputs.query.params(CachedScoredTweets.TTLParam)
      val scoredTweets = buildCachedScoredTweets(inputs.query, truncatedCandidates)
      Stitch.callFuture(scoredTweetsCache.set(inputs.query.getRequiredUserId, scoredTweets, ttl))
    } else Stitch.Unit
  }

  override val alerts = Seq(
    HomeMixerAlertConfig.BusinessHours.defaultSuccessRateAlert(99.4)
  )
}
