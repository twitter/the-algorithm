package com.tw ter.product_m xer.component_l brary.f lter
 mport com.tw ter.product_m xer.component_l brary.feature_hydrator.cand date.t et_t etyp e. sReplyFeature
 mport com.tw ter.product_m xer.component_l brary.model.cand date.BaseT etCand date
 mport com.tw ter.product_m xer.core.funct onal_component.f lter.F lter
 mport com.tw ter.product_m xer.core.funct onal_component.f lter.F lterResult
 mport com.tw ter.product_m xer.core.model.common.Cand dateW hFeatures
 mport com.tw ter.product_m xer.core.model.common. dent f er.F lter dent f er
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.st ch.St ch

/**
 * F lters out t ets that  s a reply to a t et
 */
case class T et sNotReplyF lter[Cand date <: BaseT etCand date]()
    extends F lter[P pel neQuery, Cand date] {
  overr de val  dent f er: F lter dent f er = F lter dent f er("T et sNotReply")

  overr de def apply(
    query: P pel neQuery,
    cand dates: Seq[Cand dateW hFeatures[Cand date]]
  ): St ch[F lterResult[Cand date]] = {

    val (kept, removed) = cand dates
      .part  on { cand date =>
        !cand date.features.get( sReplyFeature)
      }

    val f lterResult = F lterResult(
      kept = kept.map(_.cand date),
      removed = removed.map(_.cand date)
    )

    St ch.value(f lterResult)
  }

}
