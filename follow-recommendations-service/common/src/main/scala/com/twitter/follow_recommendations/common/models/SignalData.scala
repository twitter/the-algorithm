package com.tw ter.follow_recom ndat ons.common.models

 mport com.tw ter.s mclusters_v2.thr ftscala. nternal d
 mport com.tw ter.users gnalserv ce.thr ftscala.S gnalType
 mport com.tw ter.users gnalserv ce.thr ftscala.S gnal

tra  S gnalData {
  val user d: Long
  val s gnalType: S gnalType
}

case class RecentFollowsS gnal(
  overr de val user d: Long,
  overr de val s gnalType: S gnalType,
  follo dUser d: Long,
  t  stamp: Long)
    extends S gnalData

object RecentFollowsS gnal {

  def fromUssS gnal(targetUser d: Long, s gnal: S gnal): RecentFollowsS gnal = {
    val  nternal d.User d(follo dUser d) = s gnal.target nternal d.getOrElse(
      throw new  llegalArgu ntExcept on("RecentFollow S gnal does not have  nternal d"))

    RecentFollowsS gnal(
      user d = targetUser d,
      follo dUser d = follo dUser d,
      t  stamp = s gnal.t  stamp,
      s gnalType = s gnal.s gnalType
    )
  }

  def getRecentFollo dUser ds(
    s gnalDataMap: Opt on[Map[S gnalType, Seq[S gnalData]]]
  ): Opt on[Seq[Long]] = {
    s gnalDataMap.map(_.getOrElse(S gnalType.AccountFollow, default = Seq.empty).flatMap {
      case RecentFollowsS gnal(user d, s gnalType, follo dUser d, t  stamp) =>
        So (follo dUser d)
      case _ => None
    })
  }
}
