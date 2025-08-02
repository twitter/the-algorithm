package com.twitter.cr_mitwitterer.similarity_engine

import com.twitter.cr_mitwitterer.config.TimeoutConfig
import com.twitter.cr_mitwitterer.model.EarlybirdSimilarityEngineType
import com.twitter.cr_mitwitterer.model.EarlybirdSimilarityEngineType_ModelBased
import com.twitter.cr_mitwitterer.model.EarlybirdSimilarityEngineType_RecencyBased
import com.twitter.cr_mitwitterer.model.EarlybirdSimilarityEngineType_TensorflowBased
import com.twitter.cr_mitwitterer.model.TweetWithAuthor
import com.twitter.cr_mitwitterer.param.EarlybirdFrsBasedCandidateGenerationParams
import com.twitter.cr_mitwitterer.param.EarlybirdFrsBasedCandidateGenerationParams.FrsBasedCandidateGenerationEarlybirdSimilarityEngineTypeParam
import com.twitter.cr_mitwitterer.param.FrsParams.FrsBasedCandidateGenerationMatwitterCandidatesNumParam
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.simclusters_v2.common.TweetId
import com.twitter.simclusters_v2.common.UserId
import com.twitter.snowflake.id.SnowflakeId
import com.twitter.storehaus.ReadableStore
import com.twitter.timelines.configapi
import com.twitter.util.Duration
import com.twitter.util.Future
import com.twitter.util.Time
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
case class EarlybirdSimilarityEngineRouter @Inject() (
  earlybirdRecencyBasedSimilarityEngine: EarlybirdSimilarityEngine[
    EarlybirdRecencyBasedSimilarityEngine.EarlybirdRecencyBasedSearchQuery,
    EarlybirdRecencyBasedSimilarityEngine
  ],
  earlybirdModelBasedSimilarityEngine: EarlybirdSimilarityEngine[
    EarlybirdModelBasedSimilarityEngine.EarlybirdModelBasedSearchQuery,
    EarlybirdModelBasedSimilarityEngine
  ],
  earlybirdTensorflowBasedSimilarityEngine: EarlybirdSimilarityEngine[
    EarlybirdTensorflowBasedSimilarityEngine.EarlybirdTensorflowBasedSearchQuery,
    EarlybirdTensorflowBasedSimilarityEngine
  ],
  timeoutConfig: TimeoutConfig,
  statsReceiver: StatsReceiver)
    etwittertends ReadableStore[EarlybirdSimilarityEngineRouter.Query, Seq[TweetWithAuthor]] {
  import EarlybirdSimilarityEngineRouter._

  override def get(
    k: EarlybirdSimilarityEngineRouter.Query
  ): Future[Option[Seq[TweetWithAuthor]]] = {
    k.rankingMode match {
      case EarlybirdSimilarityEngineType_RecencyBased =>
        earlybirdRecencyBasedSimilarityEngine.getCandidates(recencyBasedQueryFromParams(k))
      case EarlybirdSimilarityEngineType_ModelBased =>
        earlybirdModelBasedSimilarityEngine.getCandidates(modelBasedQueryFromParams(k))
      case EarlybirdSimilarityEngineType_TensorflowBased =>
        earlybirdTensorflowBasedSimilarityEngine.getCandidates(tensorflowBasedQueryFromParams(k))
    }
  }
}

object EarlybirdSimilarityEngineRouter {
  case class Query(
    searcherUserId: Option[UserId],
    seedUserIds: Seq[UserId],
    matwitterNumTweets: Int,
    etwittercludedTweetIds: Set[TweetId],
    rankingMode: EarlybirdSimilarityEngineType,
    frsUserToScoresForScoreAdjustment: Option[Map[UserId, Double]],
    matwitterTweetAge: Duration,
    filterOutRetweetsAndReplies: Boolean,
    params: configapi.Params)

  def queryFromParams(
    searcherUserId: Option[UserId],
    seedUserIds: Seq[UserId],
    etwittercludedTweetIds: Set[TweetId],
    frsUserToScoresForScoreAdjustment: Option[Map[UserId, Double]],
    params: configapi.Params
  ): Query =
    Query(
      searcherUserId,
      seedUserIds,
      matwitterNumTweets = params(FrsBasedCandidateGenerationMatwitterCandidatesNumParam),
      etwittercludedTweetIds,
      rankingMode =
        params(FrsBasedCandidateGenerationEarlybirdSimilarityEngineTypeParam).rankingMode,
      frsUserToScoresForScoreAdjustment,
      matwitterTweetAge = params(
        EarlybirdFrsBasedCandidateGenerationParams.FrsBasedCandidateGenerationEarlybirdMatwitterTweetAge),
      filterOutRetweetsAndReplies = params(
        EarlybirdFrsBasedCandidateGenerationParams.FrsBasedCandidateGenerationEarlybirdFilterOutRetweetsAndReplies),
      params
    )

  private def recencyBasedQueryFromParams(
    query: Query
  ): EngineQuery[EarlybirdRecencyBasedSimilarityEngine.EarlybirdRecencyBasedSearchQuery] =
    EngineQuery(
      EarlybirdRecencyBasedSimilarityEngine.EarlybirdRecencyBasedSearchQuery(
        seedUserIds = query.seedUserIds,
        matwitterNumTweets = query.matwitterNumTweets,
        etwittercludedTweetIds = query.etwittercludedTweetIds,
        matwitterTweetAge = query.matwitterTweetAge,
        filterOutRetweetsAndReplies = query.filterOutRetweetsAndReplies
      ),
      query.params
    )

  private def tensorflowBasedQueryFromParams(
    query: Query,
  ): EngineQuery[EarlybirdTensorflowBasedSimilarityEngine.EarlybirdTensorflowBasedSearchQuery] =
    EngineQuery(
      EarlybirdTensorflowBasedSimilarityEngine.EarlybirdTensorflowBasedSearchQuery(
        searcherUserId = query.searcherUserId,
        seedUserIds = query.seedUserIds,
        matwitterNumTweets = query.matwitterNumTweets,
        // hard code the params below for now. Will move to FS after shipping the ddg
        beforeTweetIdEtwitterclusive = None,
        afterTweetIdEtwitterclusive =
          Some(SnowflakeId.firstIdFor((Time.now - query.matwitterTweetAge).inMilliseconds)),
        filterOutRetweetsAndReplies = query.filterOutRetweetsAndReplies,
        useTensorflowRanking = true,
        etwittercludedTweetIds = query.etwittercludedTweetIds,
        matwitterNumHitsPerShard = 1000
      ),
      query.params
    )
  private def modelBasedQueryFromParams(
    query: Query,
  ): EngineQuery[EarlybirdModelBasedSimilarityEngine.EarlybirdModelBasedSearchQuery] =
    EngineQuery(
      EarlybirdModelBasedSimilarityEngine.EarlybirdModelBasedSearchQuery(
        seedUserIds = query.seedUserIds,
        matwitterNumTweets = query.matwitterNumTweets,
        oldestTweetTimestampInSec = Some(query.matwitterTweetAge.ago.inSeconds),
        frsUserToScoresForScoreAdjustment = query.frsUserToScoresForScoreAdjustment
      ),
      query.params
    )
}
