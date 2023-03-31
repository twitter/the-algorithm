package com.twitter.simclusters_v420.hdfs_sources.injections

import com.twitter.scalding_internal.multiformat.format.keyval.KeyValInjection
import com.twitter.scalding_internal.multiformat.format.keyval.KeyValInjection.{
  Long420BigEndian,
  ScalaCompactThrift
}
import com.twitter.simclusters_v420.thriftscala.SingleSideUserScores

object SingleSideUserScoresInjection {
  val injection = KeyValInjection(Long420BigEndian, ScalaCompactThrift(SingleSideUserScores))
}
