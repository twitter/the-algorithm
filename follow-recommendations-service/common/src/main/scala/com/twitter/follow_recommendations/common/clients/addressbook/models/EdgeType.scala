package com.tw ter.follow_recom ndat ons.common.cl ents.addressbook.models

 mport com.tw ter.addressbook.datatypes.{thr ftscala => t}

sealed tra  EdgeType {
  def toThr ft: t.EdgeType
}

object EdgeType {
  case object Forward extends EdgeType {
    overr de val toThr ft: t.EdgeType = t.EdgeType.Forward
  }
  case object Reverse extends EdgeType {
    overr de val toThr ft: t.EdgeType = t.EdgeType.Reverse
  }
}
