package com.twitter.simclusters_v2.common

import com.twitter.simclusters_v2.thriftscala.SimClustersEmbeddingId

/**
 * A common library to construct Cache Key for SimClustersEmbeddingId.
 */
case class SimClustersEmbeddingIdCacheKeyBuilder(
  hash: Array[Byte] => Long,
  prefitwitter: String = "") {

  // Etwitterample: "CR:SCE:1:2:1234567890ABCDEF"
  def apply(embeddingId: SimClustersEmbeddingId): String = {
    f"$prefitwitter:SCE:${embeddingId.embeddingType.getValue()}%twitter:" +
      f"${embeddingId.modelVersion.getValue()}%twitter" +
      f":${hash(embeddingId.internalId.toString.getBytes)}%twitter"
  }

}
