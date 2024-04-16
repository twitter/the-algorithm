package com.tw ter.product_m xer.component_l brary.cand date_s ce.t  l ne_ranker

 mport com.tw ter.product_m xer.core.feature.Feature
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMapBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.cand date_s ce.Cand dateS ceW hExtractedFeatures
 mport com.tw ter.product_m xer.core.funct onal_component.cand date_s ce.Cand datesW hS ceFeatures
 mport com.tw ter.product_m xer.core.model.common. dent f er.Cand dateS ce dent f er
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.product_m xer.core.p pel ne.p pel ne_fa lure.P pel neFa lure
 mport com.tw ter.product_m xer.core.p pel ne.p pel ne_fa lure.UnexpectedCand dateResult
 mport com.tw ter.st ch.St ch
 mport com.tw ter.t  l neranker.{thr ftscala => t}
 mport javax. nject. nject
 mport javax. nject.S ngleton

/**
 * S ce t ets of ret ets present  n T  l ne Ranker cand dates l st.
 * T se t ets are used only for furt r rank ng. T y are not returned to t  end user.
 */
case object T  l neRankerUtegS ceT etsFeature
    extends Feature[P pel neQuery, Seq[t.Cand dateT et]]

@S ngleton
class T  l neRankerUtegCand dateS ce @ nject() (
  t  l neRankerCl ent: t.T  l neRanker. thodPerEndpo nt)
    extends Cand dateS ceW hExtractedFeatures[t.UtegL kedByT etsQuery, t.Cand dateT et] {

  overr de val  dent f er: Cand dateS ce dent f er =
    Cand dateS ce dent f er("T  l neRankerUteg")

  overr de def apply(
    request: t.UtegL kedByT etsQuery
  ): St ch[Cand datesW hS ceFeatures[t.Cand dateT et]] = {
    St ch
      .callFuture(t  l neRankerCl ent.getUtegL kedByT etCand dates(Seq(request)))
      .map { response =>
        val result = response. adOpt on.getOrElse(
          throw P pel neFa lure(UnexpectedCand dateResult, "Empty T  l ne Ranker response"))
        val cand dates = result.cand dates.toSeq.flatten
        val s ceT ets = result.s ceT ets.toSeq.flatten

        val cand dateS ceFeatures = FeatureMapBu lder()
          .add(T  l neRankerUtegS ceT etsFeature, s ceT ets)
          .bu ld()

        Cand datesW hS ceFeatures(cand dates = cand dates, features = cand dateS ceFeatures)
      }
  }
}
