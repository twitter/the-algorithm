package com.X.cr_mixer.similarity_engine

import com.X.cr_mixer.model.SimilarityEngineInfo
import com.X.simclusters_v2.common.TweetId
import com.X.cr_mixer.model.TweetWithScore
import com.X.cr_mixer.thriftscala.SimilarityEngineType
import com.X.finagle.stats.StatsReceiver
import com.X.simclusters_v2.thriftscala.InternalId
import com.X.storehaus.ReadableStore
import com.X.timelines.configapi
import com.X.util.Future
import javax.inject.Singleton

@Singleton
case class TwhinCollabFilterSimilarityEngine(
  twhinCandidatesStratoStore: ReadableStore[Long, Seq[TweetId]],
  statsReceiver: StatsReceiver)
    extends ReadableStore[
      TwhinCollabFilterSimilarityEngine.Query,
      Seq[TweetWithScore]
    ] {

  import TwhinCollabFilterSimilarityEngine._
  override def get(
    query: TwhinCollabFilterSimilarityEngine.Query
  ): Future[Option[Seq[TweetWithScore]]] = {

    query.sourceId match {
      case InternalId.UserId(userId) =>
        twhinCandidatesStratoStore.get(userId).map {
          _.map {
            _.map { tweetId => TweetWithScore(tweetId, defaultScore) }
          }
        }
      case _ =>
        Future.None
    }
  }
}

object TwhinCollabFilterSimilarityEngine {

  val defaultScore: Double = 1.0

  case class TwhinCollabFilterView(clusterVersion: String)

  case class Query(
    sourceId: InternalId,
  )

  def toSimilarityEngineInfo(
    query: LookupEngineQuery[Query],
    score: Double
  ): SimilarityEngineInfo = {
    SimilarityEngineInfo(
      similarityEngineType = SimilarityEngineType.TwhinCollabFilter,
      modelId = Some(query.lookupKey),
      score = Some(score))
  }

  def fromParams(
    sourceId: InternalId,
    modelId: String,
    params: configapi.Params,
  ): LookupEngineQuery[Query] = {
    LookupEngineQuery(
      Query(sourceId = sourceId),
      modelId,
      params
    )
  }
}
