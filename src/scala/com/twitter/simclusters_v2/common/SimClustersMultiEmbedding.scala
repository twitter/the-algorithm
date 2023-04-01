package com.twitter.simclusters_v2.common

import com.twitter.simclusters_v2.common.SimClustersMultiEmbeddingId._
import com.twitter.simclusters_v2.thriftscala.SimClustersMultiEmbedding.{Ids, Values}
import com.twitter.simclusters_v2.thriftscala.{
  SimClustersMultiEmbedding,
  SimClustersEmbeddingId,
  SimClustersMultiEmbeddingId
}

/**
 * Helper methods for SimClustersMultiEmbedding
 */
object SimClustersMultiEmbedding {

  // Convert a multiEmbedding to a list of (embeddingId, score)
  def toSimClustersEmbeddingIdWithScores(
    simClustersMultiEmbeddingId: SimClustersMultiEmbeddingId,
    simClustersMultiEmbedding: SimClustersMultiEmbedding
  ): Seq[(SimClustersEmbeddingId, Double)] = {
    simClustersMultiEmbedding match {
      case Values(values) =>
        values.embeddings.zipWithIndex.map {
          case (embeddingWithScore, i) =>
            (toEmbeddingId(simClustersMultiEmbeddingId, i), embeddingWithScore.score)
        }
      case Ids(ids) =>
        ids.ids.map(_.toTuple)
    }
  }

}
