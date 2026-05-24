package com.twitter.simclusters_v2.hdfs_sources.injections

import com.twitter.scalding_internal.multiformat.format.keyval.KeyValInjection
import com.twitter.scalding_internal.multiformat.format.keyval.KeyValInjection.{
  Int2BigEndian,
  Long2BigEndian,
  ScalaCompactThrift
}
import com.twitter.simclusters_v2.thriftscala.SimClustersInferredEntities

object InferredEntitiesInjections {

  final val InferredEntityInjection: KeyValInjection[Long, SimClustersInferredEntities] =
    KeyValInjection(
      Long2BigEndian,
      ScalaCompactThrift(SimClustersInferredEntities)
    )

  final val InferredEntityKeyedByClusterInjection: KeyValInjection[
    Int,
    SimClustersInferredEntities
  ] =
    KeyValInjection(
      Int2BigEndian,
      ScalaCompactThrift(SimClustersInferredEntities)
    )
}
