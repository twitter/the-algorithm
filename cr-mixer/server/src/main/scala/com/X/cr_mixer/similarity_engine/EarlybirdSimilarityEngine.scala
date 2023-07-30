package com.X.cr_mixer.similarity_engine

import com.X.cr_mixer.model.TweetWithAuthor
import com.X.cr_mixer.similarity_engine.SimilarityEngine.SimilarityEngineConfig
import com.X.cr_mixer.thriftscala.SimilarityEngineType
import com.X.finagle.stats.StatsReceiver
import com.X.storehaus.ReadableStore
import com.X.util.Future

class EarlybirdSimilarityEngine[
  Query,
  EarlybirdSimilarityEngineStore <: ReadableStore[Query, Seq[TweetWithAuthor]]
](
  implementingStore: EarlybirdSimilarityEngineStore,
  override val identifier: SimilarityEngineType,
  globalStats: StatsReceiver,
  engineConfig: SimilarityEngineConfig,
) extends SimilarityEngine[EngineQuery[Query], TweetWithAuthor] {
  private val scopedStats = globalStats.scope("similarityEngine", identifier.toString)

  def getScopedStats: StatsReceiver = scopedStats

  def getCandidates(query: EngineQuery[Query]): Future[Option[Seq[TweetWithAuthor]]] = {
    SimilarityEngine.getFromFn(
      implementingStore.get,
      query.storeQuery,
      engineConfig,
      query.params,
      scopedStats
    )
  }
}
