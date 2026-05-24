package com.twitter.simclusters_v2.hdfs_sources.injections

import com.twitter.bijection.Bufferable
import com.twitter.scalding_internal.multiformat.format.keyval.KeyValInjection
import com.twitter.scalding_internal.multiformat.format.keyval.KeyValInjection.{
  ScalaCompactThrift,
  genericInjection
}
import com.twitter.simclusters_v2.thriftscala.ClusterDetails

object ClusterDetailsInjection {
  val injection = KeyValInjection[(String, Int), ClusterDetails](
    genericInjection(Bufferable.injectionOf[(String, Int)]),
    ScalaCompactThrift(ClusterDetails)
  )
}
