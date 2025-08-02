package com.twitter.home_mitwitterer.product.scored_tweets.query_transformer

import com.twitter.home_mitwitterer.model.HomeFeatures.RealGraphInNetworkScoresFeature
import com.twitter.home_mitwitterer.model.request.HasDeviceContetwittert
import com.twitter.home_mitwitterer.product.scored_tweets.query_transformer.TimelineRankerQueryTransformer._
import com.twitter.home_mitwitterer.util.CachedScoredTweetsHelper
import com.twitter.home_mitwitterer.util.earlybird.EarlybirdRequestUtil
import com.twitter.product_mitwitterer.core.model.common.identifier.CandidatePipelineIdentifier
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery
import com.twitter.product_mitwitterer.core.quality_factor.HasQualityFactorStatus
import com.twitter.timelineranker.{model => tlr}
import com.twitter.timelines.common.model.TweetKindOption
import com.twitter.timelines.earlybird.common.options.EarlybirdOptions
import com.twitter.timelines.earlybird.common.options.EarlybirdScoringModelConfig
import com.twitter.timelines.earlybird.common.utils.SearchOperator
import com.twitter.timelines.model.UserId
import com.twitter.timelines.model.candidate.CandidateTweetSourceId
import com.twitter.timelines.util.SnowflakeSortIndetwitterHelper
import com.twitter.util.Duration
import com.twitter.util.Time

object TimelineRankerQueryTransformer {

  /**
   * Specifies the matwitterimum number of etwittercluded tweet ids to include in the search indetwitter query.
   * Earlybird's named multi term disjunction map feature supports up to 1500 tweet ids.
   */
  private val EarlybirdMatwitterEtwittercludedTweets = 1500

  /**
   * Matwitterimum number of query hits each earlybird shard is allowed to accumulate before
   * early-terminating the query and reducing the hits to MatwitterNumEarlybirdResults.
   */
  private val EarlybirdMatwitterHits = 1000

  /**
   * Matwitterimum number of results TLR should retrieve from each earlybird shard.
   */
  private val EarlybirdMatwitterResults = 300
}

trait TimelineRankerQueryTransformer[
  Query <: PipelineQuery with HasQualityFactorStatus with HasDeviceContetwittert] {
  def matwitterTweetsToFetch: Int
  def options: TweetKindOption.ValueSet = TweetKindOption.Default
  def candidateTweetSourceId: CandidateTweetSourceId.Value
  def utegLikedByTweetsOptions(query: Query): Option[tlr.UtegLikedByTweetsOptions] = None
  def seedAuthorIds(query: Query): Option[Seq[Long]] = None
  def candidatePipelineIdentifier: CandidatePipelineIdentifier
  def earlybirdModels: Seq[EarlybirdScoringModelConfig] =
    EarlybirdRequestUtil.EarlybirdScoringModels.UnifiedEngagementProd
  def getTensorflowModel(query: Query): Option[String] = None

  def buildTimelineRankerQuery(query: Query, sinceDuration: Duration): tlr.RecapQuery = {
    val sinceTime: Time = sinceDuration.ago
    val untilTime: Time = Time.now

    val fromTweetIdEtwitterclusive = SnowflakeSortIndetwitterHelper.timestampToFakeId(sinceTime)
    val toTweetIdEtwitterclusive = SnowflakeSortIndetwitterHelper.timestampToFakeId(untilTime)
    val range = tlr.TweetIdRange(Some(fromTweetIdEtwitterclusive), Some(toTweetIdEtwitterclusive))

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
      .map(_.getOrElse(RealGraphInNetworkScoresFeature, Map.empty[UserId, Double]))
      .getOrElse(Map.empty)

    val deviceContetwittert =
      query.deviceContetwittert.map(_.toTimelineServiceDeviceContetwittert(query.clientContetwittert))

    val tensorflowModel = getTensorflowModel(query)

    val earlyBirdOptions = EarlybirdOptions(
      matwitterNumHitsPerShard = EarlybirdMatwitterHits,
      matwitterNumResultsPerShard = EarlybirdMatwitterResults,
      models = earlybirdModels,
      authorScoreMap = authorScoreMap,
      skipVeryRecentTweets = true,
      tensorflowModel = tensorflowModel
    )

    tlr.RecapQuery(
      userId = query.getRequiredUserId,
      matwitterCount = Some(matwitterCount),
      range = Some(range),
      options = options,
      searchOperator = SearchOperator.Etwitterclude,
      earlybirdOptions = Some(earlyBirdOptions),
      deviceContetwittert = deviceContetwittert,
      authorIds = seedAuthorIds(query),
      etwittercludedTweetIds = etwittercludedTweetIds,
      utegLikedByTweetsOptions = utegLikedByTweetsOptions(query),
      searchClientSubId = None,
      candidateTweetSourceId = Some(candidateTweetSourceId),
      hydratesContentFeatures = Some(false)
    )
  }
}
