package com.twitter.cr_mitwitterer.similarity_engine

import com.twitter.finagle.stats.StatsReceiver
import com.twitter.search.earlybird.thriftscala.EarlybirdRequest
import com.twitter.search.earlybird.thriftscala.EarlybirdService
import com.twitter.search.earlybird.thriftscala.ThriftSearchQuery
import com.twitter.util.Time
import com.twitter.search.common.query.thriftjava.thriftscala.CollectorParams
import com.twitter.search.common.ranking.thriftscala.ThriftRankingParams
import com.twitter.search.common.ranking.thriftscala.ThriftScoringFunctionType
import com.twitter.search.earlybird.thriftscala.ThriftSearchRelevanceOptions
import javatwitter.inject.Inject
import javatwitter.inject.Singleton
import EarlybirdSimilarityEngineBase._
import com.twitter.cr_mitwitterer.config.TimeoutConfig
import com.twitter.cr_mitwitterer.similarity_engine.EarlybirdTensorflowBasedSimilarityEngine.EarlybirdTensorflowBasedSearchQuery
import com.twitter.cr_mitwitterer.util.EarlybirdSearchUtil.EarlybirdClientId
import com.twitter.cr_mitwitterer.util.EarlybirdSearchUtil.FacetsToFetch
import com.twitter.cr_mitwitterer.util.EarlybirdSearchUtil.GetCollectorTerminationParams
import com.twitter.cr_mitwitterer.util.EarlybirdSearchUtil.GetEarlybirdQuery
import com.twitter.cr_mitwitterer.util.EarlybirdSearchUtil.MetadataOptions
import com.twitter.cr_mitwitterer.util.EarlybirdSearchUtil.GetNamedDisjunctions
import com.twitter.search.earlybird.thriftscala.ThriftSearchRankingMode
import com.twitter.simclusters_v2.common.TweetId
import com.twitter.simclusters_v2.common.UserId
import com.twitter.util.Duration

@Singleton
case class EarlybirdTensorflowBasedSimilarityEngine @Inject() (
  earlybirdSearchClient: EarlybirdService.MethodPerEndpoint,
  timeoutConfig: TimeoutConfig,
  stats: StatsReceiver)
    etwittertends EarlybirdSimilarityEngineBase[EarlybirdTensorflowBasedSearchQuery] {
  import EarlybirdTensorflowBasedSimilarityEngine._
  override val statsReceiver: StatsReceiver = stats.scope(this.getClass.getSimpleName)
  override def getEarlybirdRequest(
    query: EarlybirdTensorflowBasedSearchQuery
  ): Option[EarlybirdRequest] = {
    if (query.seedUserIds.nonEmpty)
      Some(
        EarlybirdRequest(
          searchQuery = getThriftSearchQuery(query, timeoutConfig.earlybirdServerTimeout),
          clientHost = None,
          clientRequestID = None,
          clientId = Some(EarlybirdClientId),
          clientRequestTimeMs = Some(Time.now.inMilliseconds),
          cachingParams = None,
          timeoutMs = timeoutConfig.earlybirdServerTimeout.inMilliseconds.intValue(),
          facetRequest = None,
          termStatisticsRequest = None,
          debugMode = 0,
          debugOptions = None,
          searchSegmentId = None,
          returnStatusType = None,
          successfulResponseThreshold = None,
          querySource = None,
          getOlderResults = Some(false),
          followedUserIds = Some(query.seedUserIds),
          adjustedProtectedRequestParams = None,
          adjustedFullArchiveRequestParams = None,
          getProtectedTweetsOnly = Some(false),
          retokenizeSerializedQuery = None,
          skipVeryRecentTweets = true,
          etwitterperimentClusterToUse = None
        ))
    else None
  }
}

object EarlybirdTensorflowBasedSimilarityEngine {
  case class EarlybirdTensorflowBasedSearchQuery(
    searcherUserId: Option[UserId],
    seedUserIds: Seq[UserId],
    matwitterNumTweets: Int,
    beforeTweetIdEtwitterclusive: Option[TweetId],
    afterTweetIdEtwitterclusive: Option[TweetId],
    filterOutRetweetsAndReplies: Boolean,
    useTensorflowRanking: Boolean,
    etwittercludedTweetIds: Set[TweetId],
    matwitterNumHitsPerShard: Int)
      etwittertends EarlybirdSearchQuery

  private def getThriftSearchQuery(
    query: EarlybirdTensorflowBasedSearchQuery,
    processingTimeout: Duration
  ): ThriftSearchQuery =
    ThriftSearchQuery(
      serializedQuery = GetEarlybirdQuery(
        query.beforeTweetIdEtwitterclusive,
        query.afterTweetIdEtwitterclusive,
        query.etwittercludedTweetIds,
        query.filterOutRetweetsAndReplies).map(_.serialize),
      fromUserIDFilter64 = Some(query.seedUserIds),
      numResults = query.matwitterNumTweets,
      // Whether to collect conversation IDs. Remove it for now.
      // collectConversationId = Gate.True(), // true for Home
      rankingMode = ThriftSearchRankingMode.Relevance,
      relevanceOptions = Some(getRelevanceOptions),
      collectorParams = Some(
        CollectorParams(
          // numResultsToReturn defines how many results each EB shard will return to search root
          numResultsToReturn = 1000,
          // terminationParams.matwitterHitsToProcess is used for early terminating per shard results fetching.
          terminationParams =
            GetCollectorTerminationParams(query.matwitterNumHitsPerShard, processingTimeout)
        )),
      facetFieldNames = Some(FacetsToFetch),
      resultMetadataOptions = Some(MetadataOptions),
      searcherId = query.searcherUserId,
      searchStatusIds = None,
      namedDisjunctionMap = GetNamedDisjunctions(query.etwittercludedTweetIds)
    )

  // The specific values of recap relevance/reranking options correspond to
  // etwitterperiment: enable_recap_reranking_2988,timeline_internal_disable_recap_filter
  // bucket    : enable_rerank,disable_filter
  private def getRelevanceOptions: ThriftSearchRelevanceOptions = {
    ThriftSearchRelevanceOptions(
      protwitterimityScoring = true,
      matwitterConsecutiveSameUser = Some(2),
      rankingParams = Some(getTensorflowBasedRankingParams),
      matwitterHitsToProcess = Some(500),
      matwitterUserBlendCount = Some(3),
      protwitterimityPhraseWeight = 9.0,
      returnAllResults = Some(true)
    )
  }

  private def getTensorflowBasedRankingParams: ThriftRankingParams = {
    ThriftRankingParams(
      `type` = Some(ThriftScoringFunctionType.TensorflowBased),
      selectedTensorflowModel = Some("timelines_rectweet_replica"),
      minScore = -1.0e100,
      applyBoosts = false,
      authorSpecificScoreAdjustments = None
    )
  }
}
