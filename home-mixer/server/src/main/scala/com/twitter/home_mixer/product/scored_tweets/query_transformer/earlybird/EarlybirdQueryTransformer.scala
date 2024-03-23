package com.ExTwitter.home_mixer.product.scored_tweets.query_transformer.earlybird

import com.ExTwitter.home_mixer.model.HomeFeatures.RealGraphInNetworkScoresFeature
import com.ExTwitter.home_mixer.model.request.HasDeviceContext
import com.ExTwitter.home_mixer.util.CachedScoredTweetsHelper
import com.ExTwitter.home_mixer.util.earlybird.EarlybirdRequestUtil
import com.ExTwitter.product_mixer.core.model.common.identifier.CandidatePipelineIdentifier
import com.ExTwitter.product_mixer.core.pipeline.PipelineQuery
import com.ExTwitter.product_mixer.core.quality_factor.HasQualityFactorStatus
import com.ExTwitter.search.earlybird.{thriftscala => eb}
import com.ExTwitter.timelines.clients.relevance_search.SearchClient.TweetTypes
import com.ExTwitter.timelines.common.model.TweetKindOption
import com.ExTwitter.timelines.util.SnowflakeSortIndexHelper
import com.ExTwitter.util.Duration
import com.ExTwitter.util.Time

trait EarlybirdQueryTransformer[
  Query <: PipelineQuery with HasQualityFactorStatus with HasDeviceContext] {

  def candidatePipelineIdentifier: CandidatePipelineIdentifier
  def clientId: Option[String] = None
  def maxTweetsToFetch: Int = 100
  def tweetKindOptions: TweetKindOption.ValueSet
  def tensorflowModel: Option[String] = None

  private val EarlybirdMaxExcludedTweets = 1500

  def buildEarlybirdQuery(
    query: Query,
    sinceDuration: Duration,
    followedUserIds: Set[Long] = Set.empty
  ): eb.EarlybirdRequest = {
    val sinceTime: Time = sinceDuration.ago
    val untilTime: Time = Time.now

    val fromTweetIdExclusive = SnowflakeSortIndexHelper.timestampToFakeId(sinceTime)
    val toTweetIdExclusive = SnowflakeSortIndexHelper.timestampToFakeId(untilTime)

    val excludedTweetIds = query.features.map { featureMap =>
      CachedScoredTweetsHelper.tweetImpressionsAndCachedScoredTweetsInRange(
        featureMap,
        candidatePipelineIdentifier,
        EarlybirdMaxExcludedTweets,
        sinceTime,
        untilTime)
    }

    val maxCount =
      (query.getQualityFactorCurrentValue(candidatePipelineIdentifier) * maxTweetsToFetch).toInt

    val authorScoreMap = query.features
      .map(_.getOrElse(RealGraphInNetworkScoresFeature, Map.empty[Long, Double]))
      .getOrElse(Map.empty)

    EarlybirdRequestUtil.getTweetsRequest(
      userId = Some(query.getRequiredUserId),
      clientId = clientId,
      skipVeryRecentTweets = true,
      followedUserIds = followedUserIds,
      retweetsMutedUserIds = Set.empty,
      beforeTweetIdExclusive = Some(toTweetIdExclusive),
      afterTweetIdExclusive = Some(fromTweetIdExclusive),
      excludedTweetIds = excludedTweetIds.map(_.toSet),
      maxCount = maxCount,
      tweetTypes = TweetTypes.fromTweetKindOption(tweetKindOptions),
      authorScoreMap = Some(authorScoreMap),
      tensorflowModel = tensorflowModel
    )
  }
}
