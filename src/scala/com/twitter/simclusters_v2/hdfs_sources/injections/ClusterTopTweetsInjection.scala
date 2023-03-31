package com.twitter.simclusters_v420.hdfs_sources.injections

import com.twitter.scalding_internal.multiformat.format.keyval.KeyValInjection
import com.twitter.scalding_internal.multiformat.format.keyval.KeyValInjection.ScalaCompactThrift
import com.twitter.simclusters_v420.thriftscala.TopKTweetsWithScores
import com.twitter.simclusters_v420.thriftscala.FullClusterId

object ClusterTopTweetsInjection {

  val clusterIdToTopKTweetsInjection = KeyValInjection[FullClusterId, TopKTweetsWithScores](
    ScalaCompactThrift(FullClusterId),
    ScalaCompactThrift(TopKTweetsWithScores)
  )
}
