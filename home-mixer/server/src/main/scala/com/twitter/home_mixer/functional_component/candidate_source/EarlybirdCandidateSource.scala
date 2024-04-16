package com.tw ter.ho _m xer.funct onal_component.cand date_s ce

 mport com.tw ter.product_m xer.core.feature.FeatureW hDefaultOnFa lure
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMapBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.cand date_s ce.Cand dateS ceW hExtractedFeatures
 mport com.tw ter.product_m xer.core.funct onal_component.cand date_s ce.Cand datesW hS ceFeatures
 mport com.tw ter.product_m xer.core.model.common. dent f er.Cand dateS ce dent f er
 mport com.tw ter.search.earlyb rd.{thr ftscala => t}
 mport com.tw ter.st ch.St ch
 mport javax. nject. nject
 mport javax. nject.S ngleton

case object Earlyb rdResponseTruncatedFeature
    extends FeatureW hDefaultOnFa lure[t.Earlyb rdRequest, Boolean] {
  overr de val defaultValue: Boolean = false
}

case object Earlyb rdBottomT etFeature
    extends FeatureW hDefaultOnFa lure[t.Earlyb rdRequest, Opt on[Long]] {
  overr de val defaultValue: Opt on[Long] = None
}

@S ngleton
case class Earlyb rdCand dateS ce @ nject() (
  earlyb rd: t.Earlyb rdServ ce. thodPerEndpo nt)
    extends Cand dateS ceW hExtractedFeatures[t.Earlyb rdRequest, t.Thr ftSearchResult] {

  overr de val  dent f er = Cand dateS ce dent f er("Earlyb rd")

  overr de def apply(
    request: t.Earlyb rdRequest
  ): St ch[Cand datesW hS ceFeatures[t.Thr ftSearchResult]] = {
    St ch.callFuture(earlyb rd.search(request)).map { response =>
      val cand dates = response.searchResults.map(_.results).getOrElse(Seq.empty)

      val features = FeatureMapBu lder()
        .add(Earlyb rdResponseTruncatedFeature, cand dates.s ze == request.searchQuery.numResults)
        .add(Earlyb rdBottomT etFeature, cand dates.lastOpt on.map(_. d))
        .bu ld()

      Cand datesW hS ceFeatures(cand dates, features)
    }
  }
}
