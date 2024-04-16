package com.tw ter.ho _m xer.product.scored_t ets.f lter

 mport com.tw ter.ho _m xer.model.Ho Features.AncestorsFeature
 mport com.tw ter.ho _m xer.ut l.Cand datesUt l
 mport com.tw ter.product_m xer.component_l brary.model.cand date.T etCand date
 mport com.tw ter.product_m xer.core.funct onal_component.f lter.F lter
 mport com.tw ter.product_m xer.core.funct onal_component.f lter.F lterResult
 mport com.tw ter.product_m xer.core.model.common.Cand dateW hFeatures
 mport com.tw ter.product_m xer.core.model.common. dent f er.F lter dent f er
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.st ch.St ch

/**
 * Remove any cand date that  s  n t  ancestor l st of any reply,  nclud ng ret ets of ancestors.
 *
 * E.g.  f B repl ed to A and D was a ret et of A,   would prefer to drop D s nce ot rw se
 *   may end up serv ng t  sa  t et tw ce  n t  t  l ne (e.g. serv ng both A->B and D).
 */
object Dupl cateConversat onT etsF lter extends F lter[P pel neQuery, T etCand date] {

  overr de val  dent f er: F lter dent f er = F lter dent f er("Dupl cateConversat onT ets")

  overr de def apply(
    query: P pel neQuery,
    cand dates: Seq[Cand dateW hFeatures[T etCand date]]
  ): St ch[F lterResult[T etCand date]] = {
    val allAncestors = cand dates
      .flatMap(_.features.getOrElse(AncestorsFeature, Seq.empty))
      .map(_.t et d).toSet

    val (kept, removed) = cand dates.part  on { cand date =>
      !allAncestors.conta ns(Cand datesUt l.getOr g nalT et d(cand date))
    }

    St ch.value(F lterResult(kept = kept.map(_.cand date), removed = removed.map(_.cand date)))
  }
}
