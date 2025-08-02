package com.twitter.home_mitwitterer.product.scored_tweets.query_transformer.earlybird

import com.twitter.home_mitwitterer.model.HomeFeatures.RealGraphInNetworkScoresFeature
import com.twitter.home_mitwitterer.model.request.HasDeviceContetwittert
import com.twitter.home_mitwitterer.util.CachedScoredTweetsHelper
import com.twitter.home_mitwitterer.util.earlybird.EarlybirdRequestUtil
import com.twitter.product_mitwitterer.core.model.common.identifier.CandidatePipelineIdentifier
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery
import com.twitter.product_mitwitterer.core.quality_factor.HasQualityFactorStatus
import com.twitter.search.earlybird.{thriftscala => eb}
import com.twitter.timelines.clients.relevance_search.SearchClient.TweetTypes
import com.twitter.timelines.common.model.TweetKindOption
import com.twitter.timelines.util.SnowflakeSortIndetwitterHelper
import com.twitter.util.Duration
import com.twitter.util.Time

trait EarlybirdQueryTransformer[
  Query <: PipelineQuery with HasQualityFactorStatus with HasDeviceContetwittert] {

  def candidatePipelineIdentifier: CandidatePipelineIdentifier
  def clientId: Option[String] = None
  def matwitterTweetsToFetch: Int = 100
  def tweetKindOptions: TweetKindOption.ValueSet
  def tensorflowModel: Option[String] = None

  private val EarlybirdMatwitterEtwittercludedTweets = 1500

  def buildEarlybirdQuery(
    query: Query,
    sinceDuration: Duration,
    followedUserIds: Set[Long] = Set.empty
  ): eb.EarlybirdRequest = {
    val sinceTime: Time = sinceDuration.ago
    val untilTime: Time = Time.now

    val fromTweetIdEtwitterclusive = SnowflakeSortIndetwitterHelper.timestampToFakeId(sinceTime)
    val toTweetIdEtwitterclusive = SnowflakeSortIndetwitterHelper.timestampToFakeId(untilTime)

    val etwittercludedTweetIds = query.features.map { featureMap =>
      CachedScoredTweetsHelper.tweetImpressionsAndCachedScoredTweetsInRange(
        featureMap,
        candidatePipelineIdentifier,
        EarlybirdMatwitterEtwittercludedTweets,
        sinceTime,
        untilTime)
    }

    val matwitterCount =
      (query.getQualityFactorCurrentValue(candidatePipelineIdentifier) * matwitterTweetsToFetch).toInt

    val authorScoreMap = query.features
      .map(_.getOrElse(RealGraphInNetworkScoresFeature, Map.empty[Long, Double]))
      .getOrElse(Map.empty)

    EarlybirdRequestUtil.getTweetsRequest(
      userId = Some(query.getRequiredUserId),
      clientId = clientId,
      skipVeryRecentTweets = true,
      followedUserIds = followedUserIds,
      retweetsMutedUserIds = Set.empty,
      beforeTweetIdEtwitterclusive = Some(toTweetIdEtwitterclusive),
      afterTweetIdEtwitterclusive = Some(fromTweetIdEtwitterclusive),
      etwittercludedTweetIds = etwittercludedTweetIds.map(_.toSet),
      matwitterCount = matwitterCount,
      tweetTypes = TweetTypes.fromTweetKindOption(tweetKindOptions),
      authorScoreMap = Some(authorScoreMap),
      tensorflowModel = tensorflowModel
    )
  }
}
