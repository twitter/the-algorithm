package com.twitter.simclusters_v420.hdfs_sources.injections

import com.twitter.scalding_internal.multiformat.format.keyval.KeyValInjection
import com.twitter.scalding_internal.multiformat.format.keyval.KeyValInjection.ScalaCompactThrift
import com.twitter.simclusters_v420.thriftscala.{TweetsWithScore, DayPartitionedClusterId}

object ClusterTopMediaTweetsInjection {

  val injection = KeyValInjection[DayPartitionedClusterId, TweetsWithScore](
    ScalaCompactThrift(DayPartitionedClusterId),
    ScalaCompactThrift(TweetsWithScore)
  )
}
