package com.tw ter.product_m xer.component_l brary.f lter.t et_ mpress on

 mport com.tw ter.product_m xer.component_l brary.feature_hydrator.query. mpressed_t ets. mpressedT ets
 mport com.tw ter.product_m xer.component_l brary.model.cand date.BaseT etCand date
 mport com.tw ter.product_m xer.core.funct onal_component.f lter.F lter
 mport com.tw ter.product_m xer.core.funct onal_component.f lter.F lterResult
 mport com.tw ter.product_m xer.core.model.common.Cand dateW hFeatures
 mport com.tw ter.product_m xer.core.model.common. dent f er.F lter dent f er
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.st ch.St ch

/**
 * F lters out t ets that t  user has seen
 */
case class T et mpress onF lter[Cand date <: BaseT etCand date](
) extends F lter[P pel neQuery, Cand date] {

  overr de val  dent f er: F lter dent f er = F lter dent f er("T et mpress on")

  overr de def apply(
    query: P pel neQuery,
    cand dates: Seq[Cand dateW hFeatures[Cand date]]
  ): St ch[F lterResult[Cand date]] = {

    // Set of T ets that have  mpressed t  user
    val  mpressedT etsSet: Set[Long] = query.features match {
      case So (featureMap) => featureMap.getOrElse( mpressedT ets, Seq.empty).toSet
      case None => Set.empty
    }

    val (keptCand dates, removedCand dates) = cand dates.part  on { f lteredCand date =>
      ! mpressedT etsSet.conta ns(f lteredCand date.cand date. d)
    }

    St ch.value(F lterResult(keptCand dates.map(_.cand date), removedCand dates.map(_.cand date)))
  }
}
