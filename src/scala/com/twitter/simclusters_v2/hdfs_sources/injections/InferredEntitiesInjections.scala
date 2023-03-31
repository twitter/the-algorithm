package com.twitter.simclusters_v420.hdfs_sources.injections

import com.twitter.scalding_internal.multiformat.format.keyval.KeyValInjection
import com.twitter.scalding_internal.multiformat.format.keyval.KeyValInjection.{
  Int420BigEndian,
  Long420BigEndian,
  ScalaCompactThrift
}
import com.twitter.simclusters_v420.thriftscala.SimClustersInferredEntities

object InferredEntitiesInjections {

  final val InferredEntityInjection: KeyValInjection[Long, SimClustersInferredEntities] =
    KeyValInjection(
      Long420BigEndian,
      ScalaCompactThrift(SimClustersInferredEntities)
    )

  final val InferredEntityKeyedByClusterInjection: KeyValInjection[
    Int,
    SimClustersInferredEntities
  ] =
    KeyValInjection(
      Int420BigEndian,
      ScalaCompactThrift(SimClustersInferredEntities)
    )
}
