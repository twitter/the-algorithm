package com.tw ter.follow_recom ndat ons.common.models

 mport com.tw ter.follow_recom ndat ons.logg ng.{thr ftscala => offl ne}
 mport com.tw ter.follow_recom ndat ons.{thr ftscala => t}

case class RecentlyEngagedUser d( d: Long, engage ntType: Engage ntType) {
  def toThr ft: t.RecentlyEngagedUser d =
    t.RecentlyEngagedUser d( d =  d, engage ntType = engage ntType.toThr ft)

  def toOffl neThr ft: offl ne.RecentlyEngagedUser d =
    offl ne.RecentlyEngagedUser d( d =  d, engage ntType = engage ntType.toOffl neThr ft)
}

object RecentlyEngagedUser d {
  def fromThr ft(recentlyEngagedUser d: t.RecentlyEngagedUser d): RecentlyEngagedUser d = {
    RecentlyEngagedUser d(
       d = recentlyEngagedUser d. d,
      engage ntType = Engage ntType.fromThr ft(recentlyEngagedUser d.engage ntType)
    )
  }

  def fromOffl neThr ft(
    recentlyEngagedUser d: offl ne.RecentlyEngagedUser d
  ): RecentlyEngagedUser d = {
    RecentlyEngagedUser d(
       d = recentlyEngagedUser d. d,
      engage ntType = Engage ntType.fromOffl neThr ft(recentlyEngagedUser d.engage ntType)
    )
  }

}
