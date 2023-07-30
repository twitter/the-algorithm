package com.X.simclusters_v2.hdfs_sources.injections

import com.X.scalding_internal.multiformat.format.keyval.KeyValInjection
import com.X.scalding_internal.multiformat.format.keyval.KeyValInjection.ScalaCompactThrift
import com.X.simclusters_v2.thriftscala.{TweetsWithScore, DayPartitionedClusterId}

object ClusterTopMediaTweetsInjection {

  val injection = KeyValInjection[DayPartitionedClusterId, TweetsWithScore](
    ScalaCompactThrift(DayPartitionedClusterId),
    ScalaCompactThrift(TweetsWithScore)
  )
}
