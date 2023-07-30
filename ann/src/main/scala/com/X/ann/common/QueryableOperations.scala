package com.X.ann.common

import com.X.ann.common.EmbeddingType.EmbeddingVector
import com.X.util.Future

object QueryableOperations {
  implicit class Map[T, P <: RuntimeParams, D <: Distance[D]](
    val q: Queryable[T, P, D]) {
    def mapRuntimeParameters(f: P => P): Queryable[T, P, D] = {
      new Queryable[T, P, D] {
        def query(
          embedding: EmbeddingVector,
          numOfNeighbors: Int,
          runtimeParams: P
        ): Future[List[T]] = q.query(embedding, numOfNeighbors, f(runtimeParams))

        def queryWithDistance(
          embedding: EmbeddingVector,
          numOfNeighbors: Int,
          runtimeParams: P
        ): Future[List[NeighborWithDistance[T, D]]] =
          q.queryWithDistance(embedding, numOfNeighbors, f(runtimeParams))
      }
    }
  }
}
