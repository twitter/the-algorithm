package com.ExTwitter.cr_mixer.similarity_engine

import com.ExTwitter.cr_mixer.model.TweetWithAuthor
import com.ExTwitter.cr_mixer.similarity_engine.SimilarityEngine.SimilarityEngineConfig
import com.ExTwitter.cr_mixer.thriftscala.SimilarityEngineType
import com.ExTwitter.finagle.stats.StatsReceiver
import com.ExTwitter.storehaus.ReadableStore
import com.ExTwitter.util.Future

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
