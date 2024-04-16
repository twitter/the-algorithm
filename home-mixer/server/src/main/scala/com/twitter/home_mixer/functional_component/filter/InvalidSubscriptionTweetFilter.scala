package com.tw ter.ho _m xer.funct onal_component.f lter

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.f nagle.trac ng.Trace
 mport com.tw ter.ho _m xer.model.Ho Features.Exclus veConversat onAuthor dFeature
 mport com.tw ter.product_m xer.component_l brary.model.cand date.T etCand date
 mport com.tw ter.product_m xer.core.funct onal_component.f lter.F lter
 mport com.tw ter.product_m xer.core.funct onal_component.f lter.F lterResult
 mport com.tw ter.product_m xer.core.model.common.Cand dateW hFeatures
 mport com.tw ter.product_m xer.core.model.common. dent f er.F lter dent f er
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.soc algraph.{thr ftscala => sg}
 mport com.tw ter.st ch.St ch
 mport com.tw ter.st ch.soc algraph.Soc alGraph
 mport com.tw ter.ut l.logg ng.Logg ng

 mport javax. nject. nject
 mport javax. nject.S ngleton

/**
 * Exclude  nval d subscr pt on t ets - cases w re t  v e r  s not subscr bed to t  author
 *
 *  f SGS hydrat on fa ls, `SGS nval dSubscr pt onT etFeature` w ll be set to None for
 * subscr pt on t ets, so   expl c ly f lter those t ets out.
 */
@S ngleton
case class  nval dSubscr pt onT etF lter @ nject() (
  soc alGraphCl ent: Soc alGraph,
  statsRece ver: StatsRece ver)
    extends F lter[P pel neQuery, T etCand date]
    w h Logg ng {

  overr de val  dent f er: F lter dent f er = F lter dent f er(" nval dSubscr pt onT et")

  pr vate val scopedStatsRece ver = statsRece ver.scope( dent f er.toStr ng)
  pr vate val val dCounter = scopedStatsRece ver.counter("val dExclus veT et")
  pr vate val  nval dCounter = scopedStatsRece ver.counter(" nval dExclus veT et")

  overr de def apply(
    query: P pel neQuery,
    cand dates: Seq[Cand dateW hFeatures[T etCand date]]
  ): St ch[F lterResult[T etCand date]] = St ch
    .traverse(cand dates) { cand date =>
      val exclus veAuthor d =
        cand date.features.getOrElse(Exclus veConversat onAuthor dFeature, None)

       f (exclus veAuthor d. sDef ned) {
        val request = sg.Ex stsRequest(
          s ce = query.getRequ redUser d,
          target = exclus veAuthor d.get,
          relat onsh ps =
            Seq(sg.Relat onsh p(sg.Relat onsh pType.T erOneSuperFollow ng, hasRelat onsh p = true)),
        )
        soc alGraphCl ent.ex sts(request).map(_.ex sts).map { val d =>
           f (!val d)  nval dCounter. ncr() else val dCounter. ncr()
          val d
        }
      } else St ch.value(true)
    }.map { val dResults =>
      val (kept, removed) = cand dates
        .map(_.cand date)
        .z p(val dResults)
        .part  on { case (cand date, val d) => val d }

      val keptCand dates = kept.map { case (cand date, _) => cand date }
      val removedCand dates = removed.map { case (cand date, _) => cand date }

      F lterResult(kept = keptCand dates, removed = removedCand dates)
    }
}
