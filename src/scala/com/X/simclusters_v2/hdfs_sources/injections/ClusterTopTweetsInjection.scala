package com.X.simclusters_v2.hdfs_sources.injections

import com.X.scalding_internal.multiformat.format.keyval.KeyValInjection
import com.X.scalding_internal.multiformat.format.keyval.KeyValInjection.ScalaCompactThrift
import com.X.simclusters_v2.thriftscala.TopKTweetsWithScores
import com.X.simclusters_v2.thriftscala.FullClusterId

object ClusterTopTweetsInjection {

  val clusterIdToTopKTweetsInjection = KeyValInjection[FullClusterId, TopKTweetsWithScores](
    ScalaCompactThrift(FullClusterId),
    ScalaCompactThrift(TopKTweetsWithScores)
  )
}
