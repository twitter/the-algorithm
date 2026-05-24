package com.twitter.simclusters_v2.hdfs_sources.injections

import com.twitter.scalding_internal.multiformat.format.keyval.KeyValInjection
import com.twitter.scalding_internal.multiformat.format.keyval.KeyValInjection.{
  Long2BigEndian,
  ScalaCompactThrift
}
import com.twitter.simclusters_v2.thriftscala._

object KnownForInjection {
  val injection = KeyValInjection(Long2BigEndian, ScalaCompactThrift(ClustersUserIsKnownFor))
}
