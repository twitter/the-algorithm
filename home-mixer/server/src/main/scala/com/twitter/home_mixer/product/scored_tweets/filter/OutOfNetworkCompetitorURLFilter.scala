package com.tw ter.ho _m xer.product.scored_t ets.f lter

 mport com.tw ter.ho _m xer.model.Ho Features. nNetworkFeature
 mport com.tw ter.ho _m xer.model.Ho Features. sRet etFeature
 mport com.tw ter.ho _m xer.model.Ho Features.T etUrlsFeature
 mport com.tw ter.ho _m xer.product.scored_t ets.param.ScoredT etsParam.Compet orURLSeqParam
 mport com.tw ter.product_m xer.component_l brary.model.cand date.T etCand date
 mport com.tw ter.product_m xer.core.funct onal_component.f lter.F lter
 mport com.tw ter.product_m xer.core.funct onal_component.f lter.F lterResult
 mport com.tw ter.product_m xer.core.model.common.Cand dateW hFeatures
 mport com.tw ter.product_m xer.core.model.common. dent f er.F lter dent f er
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.st ch.St ch

object OutOfNetworkCompet orURLF lter extends F lter[P pel neQuery, T etCand date] {

  overr de val  dent f er: F lter dent f er = F lter dent f er("OutOfNetworkCompet orURL")

  overr de def apply(
    query: P pel neQuery,
    cand dates: Seq[Cand dateW hFeatures[T etCand date]]
  ): St ch[F lterResult[T etCand date]] = {
    val compet orUrls = query.params(Compet orURLSeqParam).toSet
    val (removed, kept) = cand dates.part  on(hasOutOfNetworkUrlFromCompet or(_, compet orUrls))

    St ch.value(F lterResult(kept = kept.map(_.cand date), removed = removed.map(_.cand date)))
  }

  def hasOutOfNetworkUrlFromCompet or(
    cand date: Cand dateW hFeatures[T etCand date],
    compet orUrls: Set[Str ng]
  ): Boolean = {
    !cand date.features.getOrElse( nNetworkFeature, true) &&
    !cand date.features.getOrElse( sRet etFeature, false) &&
    cand date.features
      .getOrElse(T etUrlsFeature, Seq.empty).toSet. ntersect(compet orUrls).nonEmpty
  }
}
