package com.X.simclusters_v2.hdfs_sources.injections

import com.X.bijection.Bufferable
import com.X.scalding_internal.multiformat.format.keyval.KeyValInjection
import com.X.scalding_internal.multiformat.format.keyval.KeyValInjection.{
  ScalaCompactThrift,
  genericInjection
}
import com.X.simclusters_v2.thriftscala.ClusterDetails

object ClusterDetailsInjection {
  val injection = KeyValInjection[(String, Int), ClusterDetails](
    genericInjection(Bufferable.injectionOf[(String, Int)]),
    ScalaCompactThrift(ClusterDetails)
  )
}
