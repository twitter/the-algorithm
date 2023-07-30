package com.X.follow_recommendations.common.clients.addressbook.models

import com.X.addressbook.datatypes.{thriftscala => t}

sealed trait EdgeType {
  def toThrift: t.EdgeType
}

object EdgeType {
  case object Forward extends EdgeType {
    override val toThrift: t.EdgeType = t.EdgeType.Forward
  }
  case object Reverse extends EdgeType {
    override val toThrift: t.EdgeType = t.EdgeType.Reverse
  }
}
