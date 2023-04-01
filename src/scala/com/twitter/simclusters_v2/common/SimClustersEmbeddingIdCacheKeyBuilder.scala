package com.twitter.simclusters_v2.common

import com.twitter.simclusters_v2.thriftscala.SimClustersEmbeddingId

/**
 * A common library to construct Cache Key for SimClustersEmbeddingId.
 */
case class SimClustersEmbeddingIdCacheKeyBuilder(
  hash: Array[Byte] => Long,
  prefix: String = "") {

  // Example: "CR:SCE:1:2:1234567890ABCDEF"
  def apply(embeddingId: SimClustersEmbeddingId): String = {
    f"$prefix:SCE:${embeddingId.embeddingType.getValue()}%X:" +
      f"${embeddingId.modelVersion.getValue()}%X" +
      f":${hash(embeddingId.internalId.toString.getBytes)}%X"
  }

}
