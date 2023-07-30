package com.X.simclusters_v2.hdfs_sources.injections

import com.X.scalding_internal.multiformat.format.keyval.KeyValInjection
import com.X.scalding_internal.multiformat.format.keyval.KeyValInjection.ScalaBinaryThrift
import com.X.scalding_internal.multiformat.format.keyval.KeyValInjection.Long2BigEndian
import com.X.simclusters_v2.common.UserId
import com.X.simclusters_v2.thriftscala._

object ClusteringInjections {

  final val OrderedClustersAndMembersInjection: KeyValInjection[
    UserId,
    OrderedClustersAndMembers
  ] =
    KeyValInjection(Long2BigEndian, ScalaBinaryThrift(OrderedClustersAndMembers))
}
