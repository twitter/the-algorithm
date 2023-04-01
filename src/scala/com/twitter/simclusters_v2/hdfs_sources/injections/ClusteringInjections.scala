package com.twitter.simclusters_v2.hdfs_sources.injections

import com.twitter.scalding_internal.multiformat.format.keyval.KeyValInjection
import com.twitter.scalding_internal.multiformat.format.keyval.KeyValInjection.ScalaBinaryThrift
import com.twitter.scalding_internal.multiformat.format.keyval.KeyValInjection.Long2BigEndian
import com.twitter.simclusters_v2.common.UserId
import com.twitter.simclusters_v2.thriftscala._

object ClusteringInjections {

  final val OrderedClustersAndMembersInjection: KeyValInjection[
    UserId,
    OrderedClustersAndMembers
  ] =
    KeyValInjection(Long2BigEndian, ScalaBinaryThrift(OrderedClustersAndMembers))
}
