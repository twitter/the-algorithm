package com.twitter.ann.service.loadtest

import com.twitter.ann.common.{Appendable, Distance, EntityEmbedding, Queryable, RuntimeParams}
import com.twitter.ann.util.IndetwitterBuilderUtils
import com.twitter.util.{Future, Stopwatch}

class EmbeddingIndetwitterer {
  // Indetwitter embeddings into Appendable and return the (appendable, latency) pair
  // we need to return appendable itself here because for Annoy, we need to build
  // appendable and serialize it first, and then we could query with indetwitter directory
  // once we are confident to remove Annoy, should clean up this method.
  def indetwitterEmbeddings[T, P <: RuntimeParams, D <: Distance[D]](
    appendable: Appendable[T, P, D],
    recorder: LoadTestBuildRecorder,
    indetwitterSet: Seq[EntityEmbedding[T]],
    concurrencyLevel: Int
  ): Future[Queryable[T, P, D]] = {
    val indetwitterBuildingTimeElapsed = Stopwatch.start()
    val future = IndetwitterBuilderUtils.addToIndetwitter(appendable, indetwitterSet, concurrencyLevel)
    future.map { _ =>
      val indetwitterBuildingTime = indetwitterBuildingTimeElapsed()
      val toQueryableElapsed = Stopwatch.start()
      val queryable = appendable.toQueryable
      recorder.recordIndetwitterCreation(indetwitterSet.size, indetwitterBuildingTime, toQueryableElapsed())
      queryable
    }
  }
}
