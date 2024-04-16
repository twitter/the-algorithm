package com.tw ter.fr gate.pushserv ce.pred cate

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.fr gate.common.pred cate.Fat guePred cate._
 mport com.tw ter.fr gate.pushserv ce.model.PushTypes.PushCand date
 mport com.tw ter.fr gate.thr ftscala.CommonRecom ndat onType
 mport com.tw ter.fr gate.thr ftscala.{Not f cat onD splayLocat on => D splayLocat on}
 mport com.tw ter. rm .pred cate.Na dPred cate
 mport com.tw ter.ut l.Durat on

object Fat guePred cate {

  /**
   * Pred cate that operates on a cand date, and appl es custom fat gue rules for t  sl ce of  tory only
   * correspond ng to a g ven rec type.
   *
   * @param  nterval
   * @param max n nterval
   * @param m n nterval
   * @param recom ndat onType
   * @param statsRece ver
   * @return
   */
  def recTypeOnly(
     nterval: Durat on,
    max n nterval:  nt,
    m n nterval: Durat on,
    recom ndat onType: CommonRecom ndat onType,
    not f cat onD splayLocat on: D splayLocat on = D splayLocat on.PushToMob leDev ce
  )(
     mpl c  statsRece ver: StatsRece ver
  ): Na dPred cate[PushCand date] = {
    bu ld(
       nterval =  nterval,
      max n nterval = max n nterval,
      m n nterval = m n nterval,
      f lter tory = recOnlyF lter(recom ndat onType),
      not f cat onD splayLocat on = not f cat onD splayLocat on
    ).flatContraMap { cand date: PushCand date => cand date.target. tory }
      .w hStats(statsRece ver.scope(s"pred cate_${recTypeOnlyFat gue}"))
      .w hNa (recTypeOnlyFat gue)
  }

  /**
   * Pred cate that operates on a cand date, and appl es custom fat gue rules for t  sl ce of  tory only
   * correspond ng to spec f ed rec types
   *
   * @param  nterval
   * @param max n nterval
   * @param m n nterval
   * @param statsRece ver
   * @return
   */
  def recTypeSetOnly(
     nterval: Durat on,
    max n nterval:  nt,
    m n nterval: Durat on,
    recTypes: Set[CommonRecom ndat onType],
    not f cat onD splayLocat on: D splayLocat on = D splayLocat on.PushToMob leDev ce
  )(
     mpl c  statsRece ver: StatsRece ver
  ): Na dPred cate[PushCand date] = {
    val na  = "rec_type_set_fat gue"
    bu ld(
       nterval =  nterval,
      max n nterval = max n nterval,
      m n nterval = m n nterval,
      f lter tory = recTypesOnlyF lter(recTypes),
      not f cat onD splayLocat on = not f cat onD splayLocat on
    ).flatContraMap { cand date: PushCand date => cand date.target. tory }
      .w hStats(statsRece ver.scope(s"${na }_pred cate"))
      .w hNa (na )
  }
}
