package com.twitter.interaction_graph.injection

import com.twitter.interaction_graph.thriftscala.EdgeList
import com.twitter.scalding_internal.multiformat.format.keyval.KeyValInjection
import com.twitter.scalding_internal.multiformat.format.keyval.KeyValInjection.Long2BigEndian
import com.twitter.scalding_internal.multiformat.format.keyval.KeyValInjection.ScalaCompactThrift

object EdgeListInjection {
  final val injection: KeyValInjection[Long, EdgeList] =
    KeyValInjection(
      Long2BigEndian,
      ScalaCompactThrift(EdgeList)
    )
}
