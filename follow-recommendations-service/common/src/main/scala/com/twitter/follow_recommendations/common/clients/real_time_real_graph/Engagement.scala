package com.tw ter.follow_recom ndat ons.common.cl ents.real_t  _real_graph

sealed tra  Engage ntType

//   do not  nclude SoftFollow s nce  's deprecated
object Engage ntType {
  object Cl ck extends Engage ntType
  object L ke extends Engage ntType
  object  nt on extends Engage ntType
  object Ret et extends Engage ntType
  object Prof leV ew extends Engage ntType
}

case class Engage nt(engage ntType: Engage ntType, t  stamp: Long)
