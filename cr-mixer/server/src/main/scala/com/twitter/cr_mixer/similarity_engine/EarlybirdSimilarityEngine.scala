package com.twitter.cr_mixer.similarity_engine

import com.twitter.cr_mixer.model.TweetWithAuthor
import com.twitter.cr_mixer.similarity_engine.SimilarityEngine.SimilarityEngineConfig
import com.twitter.cr_mixer.thriftscala.SimilarityEngineType
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.storehaus.ReadableStore
import com.twitter.util.Future

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
