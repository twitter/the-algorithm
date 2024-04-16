package com.tw ter.users gnalserv ce.s gnals

 mport com.tw ter.tw stly.thr ftscala.Engage nt tadata.Favor e tadata
 mport com.tw ter.tw stly.thr ftscala.RecentEngagedT et
 mport com.tw ter.users gnalserv ce.thr ftscala.S gnalType
 mport com.tw ter.ut l.T  

// Shared Log c for f lter ng s gnal across d fferent s gnal types
object S gnalF lter {

  f nal val LookBackW ndow90DayF lterEnabledS gnalTypes: Set[S gnalType] = Set(
    S gnalType.T etFavor e90dV2,
    S gnalType.Ret et90dV2,
    S gnalType.Or g nalT et90dV2,
    S gnalType.Reply90dV2)

  /* Raw S gnal F lter for T etFavor e, Ret et, Or g nal T et and Reply
   * F lter out all raw s gnal  f t  most recent {T et Favor e + Ret et + Or g nal T et + Reply}
   *  s older than 90 days.
   * T  f lter  s shared across 4 s gnal types as t y are stored  n t  sa  phys cal store
   * thus shar ng t  sa  TTL
   * */
  def lookBackW ndow90DayF lter(
    s gnals: Seq[RecentEngagedT et],
    queryS gnalType: S gnalType
  ): Seq[RecentEngagedT et] = {
     f (LookBackW ndow90DayF lterEnabledS gnalTypes.conta ns(
        queryS gnalType) && ! sMostRecentS gnalW h n90Days(s gnals. ad)) {
      Seq.empty
    } else s gnals
  }

  pr vate def  sMostRecentS gnalW h n90Days(
    s gnal: RecentEngagedT et
  ): Boolean = {
    val d ff = T  .now - T  .fromM ll seconds(s gnal.engagedAt)
    d ff. nDays <= 90
  }

  def  sPromotedT et(s gnal: RecentEngagedT et): Boolean = {
    s gnal match {
      case RecentEngagedT et(_, _,  tadata: Favor e tadata, _) =>
         tadata.favor e tadata. sAd.getOrElse(false)
      case _ => false
    }
  }

}
