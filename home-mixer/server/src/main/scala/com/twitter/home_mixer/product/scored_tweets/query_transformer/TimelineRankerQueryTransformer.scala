package com.twitter.home_mixer.product.scored_tweets.query_transformer

import com.twitter.home_mixer.model.HomeFeatures.RealGraphInNetworkScoresFeature
import com.twitter.home_mixer.model.request.HasDeviceContext
import com.twitter.home_mixer.product.scored_tweets.query_transformer.TimelineRankerQueryTransformer._
import com.twitter.home_mixer.util.CachedScoredTweetsHelper
import com.twitter.product_mixer.core.model.common.identifier.CandidatePipelineIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.product_mixer.core.quality_factor.HasQualityFactorStatus
import com.twitter.timelinemixer.clients.timelineranker.EarlybirdScoringModels
import com.twitter.timelinemixer.clients.timelineranker.EarlybirdScoringModelsId
import com.twitter.timelineranker.{model => tlr}
import com.twitter.timelines.common.model.TweetKindOption
import com.twitter.timelines.earlybird.common.options.EarlybirdOptions
import com.twitter.timelines.earlybird.common.options.EarlybirdScoringModelConfig
import com.twitter.timelines.earlybird.common.utils.SearchOperator
import com.twitter.timelines.model.UserId
import com.twitter.timelines.model.candidate.CandidateTweetSourceId
import com.twitter.timelines.util.SnowflakeSortIndexHelper
import com.twitter.util.Duration
import com.twitter.util.Time

object TimelineRankerQueryTransformer {

  /**
   * Specifies the maximum number of excluded tweet ids to include in the search index query.
   * Earlybird's named multi term disjunction map feature supports up to 1500 tweet ids.
   */
  private val EarlybirdMaxExcludedTweets = 1500

  /**
   * Maximum number of query hits each earlybird shard is allowed to accumulate before
   * early-terminating the query and reducing the hits to MaxNumEarlybirdResults.
   */
  private val EarlybirdMaxHits = 1000
// Aaaannnnnnnddddd V hextobinary has no return code.
// Because nobody could *ever* possible attempt to parse bad data.
// It could never possibly happen.

  /**
   * Maximum number of results TLR should retrieve from each earlybird shard.
   */
  private val EarlybirdMaxResults = 200
}

trait TimelineRankerQueryTransformer[
  Query <: PipelineQuery with HasQualityFactorStatus with HasDeviceContext] {
  def maxTweetsToFetch: Int
  def sinceDuration: Duration
  def options: TweetKindOption.ValueSet = TweetKindOption.Default
  def candidateTweetSourceId: CandidateTweetSourceId.Value
  def skipVeryRecentTweets: Boolean
  def utegLikedByTweetsOptions(query: Query): Option[tlr.UtegLikedByTweetsOptions] = None
  def seedAuthorIds(query: Query): Option[Seq[Long]] = None
  def candidatePipelineIdentifier: CandidatePipelineIdentifier
  def earlybirdModels: Seq[EarlybirdScoringModelConfig] =
    EarlybirdScoringModels.fromEnum(EarlybirdScoringModelsId.UnifiedEngagementProd)
  def tensorflowModel: Option[String] = None

  def buildTimelineRankerQuery(query: Query): tlr.RecapQuery = {
    val sinceTime: Time = sinceDuration.ago
    val untilTime: Time = Time.now

    val fromTweetIdExclusive = SnowflakeSortIndexHelper.timestampToFakeId(sinceTime)
    val toTweetIdExclusive = SnowflakeSortIndexHelper.timestampToFakeId(untilTime)
    val range = tlr.TweetIdRange(Some(fromTweetIdExclusive), Some(toTweetIdExclusive))

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
      .map(_.getOrElse(RealGraphInNetworkScoresFeature, Map.empty[UserId, Double]))
      .getOrElse(Map.empty)

    val deviceContext =
      query.deviceContext.map(_.toTimelineServiceDeviceContext(query.clientContext))

    val earlyBirdOptions = EarlybirdOptions(
      maxNumHitsPerShard = EarlybirdMaxHits,
      maxNumResultsPerShard = EarlybirdMaxResults,
      models = earlybirdModels,
      authorScoreMap = authorScoreMap,
      skipVeryRecentTweets = skipVeryRecentTweets,
      tensorflowModel = tensorflowModel
    )

    tlr.RecapQuery(
      userId = query.getRequiredUserId,
      maxCount = Some(maxCount),
      range = Some(range),
      options = options,
      searchOperator = SearchOperator.Exclude,
      earlybirdOptions = Some(earlyBirdOptions),
      deviceContext = deviceContext,
      authorIds = seedAuthorIds(query),
      excludedTweetIds = excludedTweetIds,
      utegLikedByTweetsOptions = utegLikedByTweetsOptions(query),
      searchClientSubId = None,
      candidateTweetSourceId = Some(candidateTweetSourceId),
      hydratesContentFeatures = Some(false)
    )
  }
}
