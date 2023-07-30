package com.X.interaction_graph.injection

import com.X.interaction_graph.thriftscala.EdgeList
import com.X.scalding_internal.multiformat.format.keyval.KeyValInjection
import com.X.scalding_internal.multiformat.format.keyval.KeyValInjection.Long2BigEndian
import com.X.scalding_internal.multiformat.format.keyval.KeyValInjection.ScalaCompactThrift

object EdgeListInjection {
  final val injection: KeyValInjection[Long, EdgeList] =
    KeyValInjection(
      Long2BigEndian,
      ScalaCompactThrift(EdgeList)
    )
}
