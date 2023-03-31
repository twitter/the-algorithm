package com.twitter.simclusters_v2.hdfs_sources.injections

import com.twitter.scalding_internal.multiformat.format.keyval.KeyValInjection
import com.twitter.scalding_internal.multiformat.format.keyval.KeyValInjection.ScalaBinaryThrift
import com.twitter.simclusters_v2.thriftscala._
import com.twitter.ml.api.thriftscala.Embedding
import com.twitter.scalding_internal.multiformat.format.keyval.KeyValInjection.Long2BigEndian
import com.twitter.scalding_internal.multiformat.format.keyval.KeyValInjection.ScalaCompactThrift

object EntityEmbeddingsInjections {

  final val EntitySimClustersEmbeddingInjection: KeyValInjection[
    SimClustersEmbeddingId,
    SimClustersEmbedding
  ] =
    KeyValInjection(
      ScalaBinaryThrift(SimClustersEmbeddingId),
      ScalaBinaryThrift(SimClustersEmbedding)
    )

  final val InternalIdEmbeddingInjection: KeyValInjection[
    SimClustersEmbeddingId,
    InternalIdEmbedding
  ] =
    KeyValInjection(
      ScalaBinaryThrift(SimClustersEmbeddingId),
      ScalaBinaryThrift(InternalIdEmbedding)
    )

  final val EntitySimClustersMultiEmbeddingInjection: KeyValInjection[
    SimClustersMultiEmbeddingId,
    SimClustersMultiEmbedding
  ] =
    KeyValInjection(
      ScalaBinaryThrift(SimClustersMultiEmbeddingId),
      ScalaBinaryThrift(SimClustersMultiEmbedding)
    )

  final val UserMbcgEmbeddingInjection: KeyValInjection[
    Long,
    Embedding
  ] =
    KeyValInjection[Long, Embedding](
      Long2BigEndian,
      ScalaCompactThrift(Embedding)
    )
}
