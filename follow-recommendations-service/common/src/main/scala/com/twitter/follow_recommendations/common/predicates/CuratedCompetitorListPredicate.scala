package com.tw ter.follow_recom ndat ons.common.pred cates

 mport com.google. nject.na .Na d
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.follow_recom ndat ons.common.base.Pred cate
 mport com.tw ter.follow_recom ndat ons.common.base.Pred cateResult
 mport com.tw ter.follow_recom ndat ons.common.constants.Gu ceNa dConstants
 mport com.tw ter.follow_recom ndat ons.common.models.F lterReason.CuratedAccountsCompet orL st
 mport com.tw ter.follow_recom ndat ons.common.models.Cand dateUser
 mport com.tw ter.st ch.St ch
 mport com.tw ter.strato.cl ent.Fetc r
 mport javax. nject. nject
 mport javax. nject.S ngleton
 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter.esc rb rd.ut l.st chcac .St chCac 

@S ngleton
case class CuratedCompet orL stPred cate @ nject() (
  statsRece ver: StatsRece ver,
  @Na d(Gu ceNa dConstants.CURATED_COMPET TOR_ACCOUNTS_FETCHER) compet orAccountFetc r: Fetc r[
    Str ng,
    Un ,
    Seq[Long]
  ]) extends Pred cate[Cand dateUser] {

  pr vate val stats: StatsRece ver = statsRece ver.scope(t .getClass.getNa )
  pr vate val cac Stats = stats.scope("cac ")

  pr vate val cac  = St chCac [Str ng, Set[Long]](
    maxCac S ze = CuratedCompet orL stPred cate.Cac NumberOfEntr es,
    ttl = CuratedCompet orL stPred cate.Cac TTL,
    statsRece ver = cac Stats,
    underly ngCall = (compet orL stPref x: Str ng) => query(compet orL stPref x)
  )

  pr vate def query(pref x: Str ng): St ch[Set[Long]] =
    compet orAccountFetc r.fetch(pref x).map(_.v.getOrElse(N l).toSet)

  /**
   * Caveat  re  s that though t  s m larToUser ds allows for a Seq[Long],  n pract ce   would
   * only return 1 user d. Mult ple user d's would result  n f lter ng cand dates assoc ated w h
   * a d fferent s m larToUser d. For example:
   *   - s m larToUser1 -> cand date1, cand date2
   *   - s m larToUser2 -> cand date3
   *   and  n t  compet orL st store   have:
   *   - s m larToUser1 -> cand date3
   *    'll be f lter ng cand date3 on account of s m larToUser1, even though   was generated
   *   w h s m larToUser2. T  m ght st ll be des rable at a product level (s nce   don't want
   *   to show t se accounts anyway), but m ght not ach eve what    ntend to code-w se.
   */
  overr de def apply(cand date: Cand dateUser): St ch[Pred cateResult] = {
    cac .readThrough(CuratedCompet orL stPred cate.DefaultKey).map { compet orL stAccounts =>
       f (compet orL stAccounts.conta ns(cand date. d)) {
        Pred cateResult. nval d(Set(CuratedAccountsCompet orL st))
      } else {
        Pred cateResult.Val d
      }
    }
  }
}

object CuratedCompet orL stPred cate {
  val DefaultKey: Str ng = "default_l st"
  val Cac TTL = 5.m nutes
  val Cac NumberOfEntr es = 5
}
