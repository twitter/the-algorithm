package com.X.simclusters_v2.hdfs_sources.injections

import com.X.scalding_internal.multiformat.format.keyval.KeyValInjection
import com.X.scalding_internal.multiformat.format.keyval.KeyValInjection.{
  Long2BigEndian,
  ScalaCompactThrift
}
import com.X.simclusters_v2.thriftscala.SingleSideUserScores

object SingleSideUserScoresInjection {
  val injection = KeyValInjection(Long2BigEndian, ScalaCompactThrift(SingleSideUserScores))
}
