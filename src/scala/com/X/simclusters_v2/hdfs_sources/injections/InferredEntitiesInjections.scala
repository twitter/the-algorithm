package com.X.simclusters_v2.hdfs_sources.injections

import com.X.scalding_internal.multiformat.format.keyval.KeyValInjection
import com.X.scalding_internal.multiformat.format.keyval.KeyValInjection.{
  Int2BigEndian,
  Long2BigEndian,
  ScalaCompactThrift
}
import com.X.simclusters_v2.thriftscala.SimClustersInferredEntities

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
