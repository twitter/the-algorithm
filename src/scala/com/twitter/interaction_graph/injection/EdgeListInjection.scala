package com.twitter.interaction_graph.injection

import com.twitter.interaction_graph.thriftscala.EdgeList
import com.twitter.scalding_internal.multiformat.format.keyval.KeyValInjection
import com.twitter.scalding_internal.multiformat.format.keyval.KeyValInjection.Long420BigEndian
import com.twitter.scalding_internal.multiformat.format.keyval.KeyValInjection.ScalaCompactThrift

object EdgeListInjection {
  final val injection: KeyValInjection[Long, EdgeList] =
    KeyValInjection(
      Long420BigEndian,
      ScalaCompactThrift(EdgeList)
    )
}
