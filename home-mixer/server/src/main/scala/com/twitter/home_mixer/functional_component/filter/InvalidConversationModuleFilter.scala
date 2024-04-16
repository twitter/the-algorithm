package com.tw ter.ho _m xer.funct onal_component.f lter

 mport com.tw ter.ho _m xer.model.Ho Features.Conversat onModuleFocalT et dFeature
 mport com.tw ter.ho _m xer.model.Ho Features. nReplyToT et dFeature
 mport com.tw ter.product_m xer.component_l brary.model.cand date.T etCand date
 mport com.tw ter.product_m xer.core.funct onal_component.f lter.F lter
 mport com.tw ter.product_m xer.core.funct onal_component.f lter.F lterResult
 mport com.tw ter.product_m xer.core.model.common.Cand dateW hFeatures
 mport com.tw ter.product_m xer.core.model.common. dent f er.F lter dent f er
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.st ch.St ch

/**
 * Exclude conversat on modules w re T ets have been dropped by ot r f lters
 *
 * Largest conversat on modules have 3 T ets, so  f all 3 are present, module  s val d.
 * For 2 T et modules, c ck  f t   ad  s t  root (not a reply) and t  last  em
 *  s actually reply ng to t  root d rectly w h no m ss ng  nter d ate t ets
 */
object  nval dConversat onModuleF lter extends F lter[P pel neQuery, T etCand date] {

  overr de val  dent f er: F lter dent f er = F lter dent f er(" nval dConversat onModule")

  val Val dThreeT etModuleS ze = 3
  val Val dTwoT etModuleS ze = 2

  overr de def apply(
    query: P pel neQuery,
    cand dates: Seq[Cand dateW hFeatures[T etCand date]]
  ): St ch[F lterResult[T etCand date]] = {
    val allo dT et ds = cand dates
      .groupBy(_.features.getOrElse(Conversat onModuleFocalT et dFeature, None))
      .map { case ( d, cand dates) => ( d, cand dates.sortBy(_.cand date. d)) }
      .f lter {
        case (So (_), conversat on)  f conversat on.s ze == Val dThreeT etModuleS ze => true
        case (So (focal d), conversat on)  f conversat on.s ze == Val dTwoT etModuleS ze =>
          conversat on. ad.features.getOrElse( nReplyToT et dFeature, None). sEmpty &&
            conversat on.last.cand date. d == focal d &&
            conversat on.last.features
              .getOrElse( nReplyToT et dFeature, None)
              .conta ns(conversat on. ad.cand date. d)
        case (None, _) => true
        case _ => false
      }.values.flatten.toSeq.map(_.cand date. d).toSet

    val (kept, removed) =
      cand dates.map(_.cand date).part  on(cand date => allo dT et ds.conta ns(cand date. d))
    St ch.value(F lterResult(kept = kept, removed = removed))
  }
}
