package com.X.cr_mixer.similarity_engine

import com.X.cr_mixer.config.TimeoutConfig
import com.X.cr_mixer.similarity_engine.EarlybirdModelBasedSimilarityEngine.EarlybirdModelBasedSearchQuery
import com.X.cr_mixer.similarity_engine.EarlybirdSimilarityEngineBase._
import com.X.cr_mixer.util.EarlybirdSearchUtil.EarlybirdClientId
import com.X.cr_mixer.util.EarlybirdSearchUtil.FacetsToFetch
import com.X.cr_mixer.util.EarlybirdSearchUtil.MetadataOptions
import com.X.finagle.stats.StatsReceiver
import com.X.finagle.tracing.Trace
import com.X.search.common.ranking.thriftscala.ThriftRankingParams
import com.X.search.common.ranking.thriftscala.ThriftScoringFunctionType
import com.X.search.earlybird.thriftscala.EarlybirdRequest
import com.X.search.earlybird.thriftscala.EarlybirdService
import com.X.search.earlybird.thriftscala.ThriftSearchQuery
import com.X.search.earlybird.thriftscala.ThriftSearchRankingMode
import com.X.search.earlybird.thriftscala.ThriftSearchRelevanceOptions
import com.X.simclusters_v2.common.UserId
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
case class EarlybirdModelBasedSimilarityEngine @Inject() (
  earlybirdSearchClient: EarlybirdService.MethodPerEndpoint,
  timeoutConfig: TimeoutConfig,
  stats: StatsReceiver)
    extends EarlybirdSimilarityEngineBase[EarlybirdModelBasedSearchQuery] {
  import EarlybirdModelBasedSimilarityEngine._
  override val statsReceiver: StatsReceiver = stats.scope(this.getClass.getSimpleName)
  override def getEarlybirdRequest(
    query: EarlybirdModelBasedSearchQuery
  ): Option[EarlybirdRequest] =
    if (query.seedUserIds.nonEmpty)
      Some(
        EarlybirdRequest(
          searchQuery = getThriftSearchQuery(query),
          clientId = Some(EarlybirdClientId),
          timeoutMs = timeoutConfig.earlybirdServerTimeout.inMilliseconds.intValue(),
          clientRequestID = Some(s"${Trace.id.traceId}"),
        ))
    else None
}

object EarlybirdModelBasedSimilarityEngine {
  case class EarlybirdModelBasedSearchQuery(
    seedUserIds: Seq[UserId],
    maxNumTweets: Int,
    oldestTweetTimestampInSec: Option[UserId],
    frsUserToScoresForScoreAdjustment: Option[Map[UserId, Double]])
      extends EarlybirdSearchQuery

  /**
   * Used by Push Service
   */
  val RealGraphScoringModel = "frigate_unified_engagement_rg"
  val MaxHitsToProcess = 1000
  val MaxConsecutiveSameUser = 1

  private def getModelBasedRankingParams(
    authorSpecificScoreAdjustments: Map[Long, Double]
  ): ThriftRankingParams = ThriftRankingParams(
    `type` = Some(ThriftScoringFunctionType.ModelBased),
    selectedModels = Some(Map(RealGraphScoringModel -> 1.0)),
    applyBoosts = false,
    authorSpecificScoreAdjustments = Some(authorSpecificScoreAdjustments)
  )

  private def getRelevanceOptions(
    authorSpecificScoreAdjustments: Map[Long, Double],
  ): ThriftSearchRelevanceOptions = {
    ThriftSearchRelevanceOptions(
      maxConsecutiveSameUser = Some(MaxConsecutiveSameUser),
      rankingParams = Some(getModelBasedRankingParams(authorSpecificScoreAdjustments)),
      maxHitsToProcess = Some(MaxHitsToProcess),
      orderByRelevance = true
    )
  }

  private def getThriftSearchQuery(query: EarlybirdModelBasedSearchQuery): ThriftSearchQuery =
    ThriftSearchQuery(
      serializedQuery = Some(f"(* [since_time ${query.oldestTweetTimestampInSec.getOrElse(0)}])"),
      fromUserIDFilter64 = Some(query.seedUserIds),
      numResults = query.maxNumTweets,
      maxHitsToProcess = MaxHitsToProcess,
      rankingMode = ThriftSearchRankingMode.Relevance,
      relevanceOptions =
        Some(getRelevanceOptions(query.frsUserToScoresForScoreAdjustment.getOrElse(Map.empty))),
      facetFieldNames = Some(FacetsToFetch),
      resultMetadataOptions = Some(MetadataOptions),
      searcherId = None
    )
}
