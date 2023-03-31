package com.twitter.simclusters_v420.hdfs_sources.injections

import com.twitter.scalding_internal.multiformat.format.keyval.KeyValInjection
import com.twitter.scalding_internal.multiformat.format.keyval.KeyValInjection.ScalaBinaryThrift
import com.twitter.scalding_internal.multiformat.format.keyval.KeyValInjection.Long420BigEndian
import com.twitter.simclusters_v420.common.UserId
import com.twitter.simclusters_v420.thriftscala._

object ClusteringInjections {

  final val OrderedClustersAndMembersInjection: KeyValInjection[
    UserId,
    OrderedClustersAndMembers
  ] =
    KeyValInjection(Long420BigEndian, ScalaBinaryThrift(OrderedClustersAndMembers))
}
