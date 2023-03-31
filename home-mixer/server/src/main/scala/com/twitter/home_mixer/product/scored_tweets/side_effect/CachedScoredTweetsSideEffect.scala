package com.twitter.home_mixer.product.scored_tweets.side_effect

import com.twitter.home_mixer.model.HomeFeatures.AncestorsFeature
import com.twitter.home_mixer.model.HomeFeatures.AuthorIdFeature
import com.twitter.home_mixer.model.HomeFeatures.AuthorIsBlueVerifiedFeature
import com.twitter.home_mixer.model.HomeFeatures.CachedCandidatePipelineIdentifierFeature
import com.twitter.home_mixer.model.HomeFeatures.DirectedAtUserIdFeature
import com.twitter.home_mixer.model.HomeFeatures.FavoritedByUserIdsFeature
import com.twitter.home_mixer.model.HomeFeatures.FollowedByUserIdsFeature
import com.twitter.home_mixer.model.HomeFeatures.InNetworkFeature
import com.twitter.home_mixer.model.HomeFeatures.InReplyToTweetIdFeature
import com.twitter.home_mixer.model.HomeFeatures.InReplyToUserIdFeature
import com.twitter.home_mixer.model.HomeFeatures.IsRetweetFeature
import com.twitter.home_mixer.model.HomeFeatures.LastScoredTimestampMsFeature
import com.twitter.home_mixer.model.HomeFeatures.QuotedTweetIdFeature
import com.twitter.home_mixer.model.HomeFeatures.QuotedUserIdFeature
import com.twitter.home_mixer.model.HomeFeatures.ScoreFeature
import com.twitter.home_mixer.model.HomeFeatures.SourceTweetIdFeature
import com.twitter.home_mixer.model.HomeFeatures.SourceUserIdFeature
import com.twitter.home_mixer.model.HomeFeatures.SuggestTypeFeature
import com.twitter.home_mixer.model.HomeFeatures.TopicContextFunctionalityTypeFeature
import com.twitter.home_mixer.model.HomeFeatures.TopicIdSocialContextFeature
import com.twitter.home_mixer.model.HomeFeatures.TweetUrlsFeature
import com.twitter.home_mixer.model.HomeFeatures.WeightedModelScoreFeature
import com.twitter.home_mixer.product.scored_tweets.model.ScoredTweetsResponse
import com.twitter.home_mixer.product.scored_tweets.param.ScoredTweetsParam.CachedScoredTweets
import com.twitter.home_mixer.service.HomeMixerAlertConfig
import com.twitter.home_mixer.{thriftscala => hmt}
import com.twitter.product_mixer.core.functional_component.marshaller.response.urt.metadata.TopicContextFunctionalityTypeMarshaller
import com.twitter.product_mixer.core.functional_component.side_effect.PipelineResultSideEffect
import com.twitter.product_mixer.core.model.common.identifier.SideEffectIdentifier
import com.twitter.product_mixer.core.model.common.presentation.CandidateWithDetails
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.servo.cache.TtlCache
import com.twitter.stitch.Stitch
import com.twitter.util.Time

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CachedScoredTweetsSideEffect @Inject() (
  scoredTweetsCache: TtlCache[Long, hmt.CachedScoredTweets])
    extends PipelineResultSideEffect[PipelineQuery, ScoredTweetsResponse] {

  override val identifier: SideEffectIdentifier = SideEffectIdentifier("CachedScoredTweets")

  private val MaxTweetsToCache = 1000

  def buildCachedScoredTweets(
    candidates: Seq[CandidateWithDetails]
  ): hmt.CachedScoredTweets = {
    val tweets = candidates.map { candidate =>
      val favoritedByUserIds = candidate.features.getOrElse(FavoritedByUserIdsFeature, Seq.empty)
      val followedByUserIds = candidate.features.getOrElse(FollowedByUserIdsFeature, Seq.empty)
      val ancestors = candidate.features.getOrElse(AncestorsFeature, Seq.empty)
      val urlsList = candidate.features.getOrElse(TweetUrlsFeature, Seq.empty)

      hmt.CachedScoredTweet(
        tweetId = candidate.candidateIdLong,
        // Cache the model score instead of the final score because rescoring is per-request
        score = candidate.features.getOrElse(WeightedModelScoreFeature, None),
        lastScoredTimestampMs = Some(
          candidate.features
            .getOrElse(LastScoredTimestampMsFeature, None)
            .getOrElse(Time.now.inMilliseconds)),
        candidatePipelineIdentifier = Some(
          candidate.features
            .getOrElse(CachedCandidatePipelineIdentifierFeature, None)
            .getOrElse(candidate.source.name)),
        userId = candidate.features.getOrElse(AuthorIdFeature, None),
        sourceTweetId = candidate.features.getOrElse(SourceTweetIdFeature, None),
        sourceUserId = candidate.features.getOrElse(SourceUserIdFeature, None),
        isRetweet = Some(candidate.features.getOrElse(IsRetweetFeature, false)),
        isInNetwork = Some(candidate.features.getOrElse(InNetworkFeature, false)),
        suggestType = candidate.features.getOrElse(SuggestTypeFeature, None),
        quotedTweetId = candidate.features.getOrElse(QuotedTweetIdFeature, None),
        quotedUserId = candidate.features.getOrElse(QuotedUserIdFeature, None),
        inReplyToTweetId = candidate.features.getOrElse(InReplyToTweetIdFeature, None),
        inReplyToUserId = candidate.features.getOrElse(InReplyToUserIdFeature, None),
        directedAtUserId = candidate.features.getOrElse(DirectedAtUserIdFeature, None),
        favoritedByUserIds = if (favoritedByUserIds.nonEmpty) Some(favoritedByUserIds) else None,
        followedByUserIds = if (followedByUserIds.nonEmpty) Some(followedByUserIds) else None,
        topicId = candidate.features.getOrElse(TopicIdSocialContextFeature, None),
        topicFunctionalityType = candidate.features
          .getOrElse(TopicContextFunctionalityTypeFeature, None).map(
            TopicContextFunctionalityTypeMarshaller(_)),
        ancestors = if (ancestors.nonEmpty) Some(ancestors) else None,
        urlsList = if (urlsList.nonEmpty) Some(urlsList) else None,
        authorIsBlueVerified =
          Some(candidate.features.getOrElse(AuthorIsBlueVerifiedFeature, false))
      )
    }

    hmt.CachedScoredTweets(tweets = tweets)
  }

  final override def apply(
    inputs: PipelineResultSideEffect.Inputs[PipelineQuery, ScoredTweetsResponse]
  ): Stitch[Unit] = {
    val candidates = (inputs.selectedCandidates ++ inputs.remainingCandidates).filter { candidate =>
      val score = candidate.features.getOrElse(ScoreFeature, None)
      score.exists(_ > 0.0)
    }

    val truncatedCandidates =
      if (candidates.size > MaxTweetsToCache)
        candidates
          .sortBy(-_.features.getOrElse(ScoreFeature, None).getOrElse(0.0))
          .take(MaxTweetsToCache)
      else candidates

    if (truncatedCandidates.nonEmpty) {
      val ttl = inputs.query.params(CachedScoredTweets.TTLParam)
      val scoredTweets = buildCachedScoredTweets(truncatedCandidates)
      Stitch.callFuture(scoredTweetsCache.set(inputs.query.getRequiredUserId, scoredTweets, ttl))
    } else Stitch.Unit
  }

  override val alerts = Seq(
    HomeMixerAlertConfig.BusinessHours.defaultSuccessRateAlert(99.4)
  )
}
