package com.twitter.home_mixer.product.scored_tweets.query_transformer.earlybird

import com.twitter.home_mixer.model.request.HasDeviceContext
import com.twitter.home_mixer.util.CachedScoredTweetsHelper
import com.twitter.home_mixer.util.earlybird.EarlybirdRequestUtil
import com.twitter.product_mixer.component_library.feature_hydrator.query.social_graph.SGSFollowedUsersFeature
import com.twitter.product_mixer.core.model.common.identifier.CandidatePipelineIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.product_mixer.core.quality_factor.HasQualityFactorStatus
import com.twitter.search.earlybird.{thriftscala => eb}
import com.twitter.timelines.clients.relevance_search.SearchClient.TweetTypes
import com.twitter.timelines.common.model.TweetKindOption
import com.twitter.timelines.util.SnowflakeSortIndexHelper
import com.twitter.util.Duration
import com.twitter.util.Time

trait EarlybirdQueryTransformer[
  Query <: PipelineQuery with HasQualityFactorStatus with HasDeviceContext] {

  def candidatePipelineIdentifier: CandidatePipelineIdentifier
  def clientId: Option[String] = None
  def maxTweetsToFetch: Int = 100
  def tweetKindOptions: TweetKindOption.ValueSet
  def getTensorflowModel(query: Query): Option[String] = None
  def enableExcludeSourceTweetIdsQuery: Boolean = false

  private val EarlybirdMaxExcludedTweets = 1500

  protected def getFollowedUsers(query: Query): Set[Long] = {
    query.features
      .map(_.getOrElse(SGSFollowedUsersFeature, Seq.empty)).getOrElse(
        Nil).toSet + query.getRequiredUserId
  }

  protected def buildEarlybirdQuery(
    query: Query,
    sinceDuration: Duration,
    queryUserIds: Set[Long] = Set.empty,
    authorScoreMap: Option[Map[Long, Double]] = None,
    isVideoOnlyRequest: Boolean = false,
    getOlderTweets: Boolean = false,
    isRecency: Boolean = false,
    until: Time = Time.now
  ): eb.EarlybirdRequest = {
    buildEarlybirdQueryWithTweetKindOptions(
      query,
      sinceDuration,
      queryUserIds,
      authorScoreMap,
      tweetKindOptions,
      isVideoOnlyRequest,
      getOlderTweets,
      isRecency,
      until
    )
  }

  protected def buildEarlybirdQueryWithTweetKindOptions(
    query: Query,
    sinceDuration: Duration,
    queryUserIds: Set[Long] = Set.empty,
    authorScoreMap: Option[Map[Long, Double]] = None,
    tweetKindOptions: TweetKindOption.ValueSet,
    isVideoOnlyRequest: Boolean = false,
    getOlderTweets: Boolean = false,
    isRecency: Boolean = false,
    until: Time = Time.now
  ): eb.EarlybirdRequest = {
    val sinceTime: Time = sinceDuration.ago
    val untilTime: Time = until

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

    EarlybirdRequestUtil.getTweetsRequest(
      userId = Some(query.getRequiredUserId),
      clientId = clientId,
      skipVeryRecentTweets = true,
      queryUserIds = queryUserIds,
      retweetsMutedUserIds = Set.empty,
      beforeTweetIdExclusive = Some(toTweetIdExclusive),
      afterTweetIdExclusive = Some(fromTweetIdExclusive),
      excludedTweetIds = excludedTweetIds.map(_.toSet),
      maxCount = maxTweetsToFetch,
      tweetTypes = TweetTypes.fromTweetKindOption(tweetKindOptions),
      authorScoreMap = authorScoreMap,
      tensorflowModel = getTensorflowModel(query),
      enableExcludeSourceTweetIdsQuery = enableExcludeSourceTweetIdsQuery,
      isVideoOnlyRequest = isVideoOnlyRequest,
      getOlderTweets = getOlderTweets,
      isRecency = isRecency,
    )
  }
}
