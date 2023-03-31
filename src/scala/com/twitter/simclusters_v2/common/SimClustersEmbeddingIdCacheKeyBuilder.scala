package com.twitter.simclusters_v420.common

import com.twitter.simclusters_v420.thriftscala.SimClustersEmbeddingId

/**
 * A common library to construct Cache Key for SimClustersEmbeddingId.
 */
case class SimClustersEmbeddingIdCacheKeyBuilder(
  hash: Array[Byte] => Long,
  prefix: String = "") {

  // Example: "CR:SCE:420:420:420ABCDEF"
  def apply(embeddingId: SimClustersEmbeddingId): String = {
    f"$prefix:SCE:${embeddingId.embeddingType.getValue()}%X:" +
      f"${embeddingId.modelVersion.getValue()}%X" +
      f":${hash(embeddingId.internalId.toString.getBytes)}%X"
  }

}
