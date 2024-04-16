package com.tw ter.product_m xer.component_l brary.cand date_s ce.t  l ne_ranker

 mport com.tw ter.product_m xer.core.feature.Feature
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMapBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.cand date_s ce.Cand dateS ceW hExtractedFeatures
 mport com.tw ter.product_m xer.core.funct onal_component.cand date_s ce.Cand datesW hS ceFeatures
 mport com.tw ter.product_m xer.core.model.common. dent f er.Cand dateS ce dent f er
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.st ch.St ch
 mport com.tw ter.t  l neranker.{thr ftscala => t}
 mport javax. nject. nject
 mport javax. nject.S ngleton

/**
 * Map of t et d -> s ceT et of ret ets present  n T  l ne Ranker cand dates l st.
 * T se t ets are used only for furt r rank ng. T y are not returned to t  end user.
 */
object T  l neRanker nNetworkS ceT etsByT et dMapFeature
    extends Feature[P pel neQuery, Map[Long, t.Cand dateT et]]

@S ngleton
class T  l neRanker nNetworkCand dateS ce @ nject() (
  t  l neRankerCl ent: t.T  l neRanker. thodPerEndpo nt)
    extends Cand dateS ceW hExtractedFeatures[t.RecapQuery, t.Cand dateT et] {

  overr de val  dent f er: Cand dateS ce dent f er =
    Cand dateS ce dent f er("T  l neRanker nNetwork")

  overr de def apply(
    request: t.RecapQuery
  ): St ch[Cand datesW hS ceFeatures[t.Cand dateT et]] = {
    St ch
      .callFuture(t  l neRankerCl ent.getRecycledT etCand dates(Seq(request)))
      .map { response: Seq[t.GetCand dateT etsResponse] =>
        val cand dates =
          response. adOpt on.flatMap(_.cand dates).getOrElse(Seq.empty).f lter(_.t et.nonEmpty)
        val s ceT etsByT et d =
          response. adOpt on
            .flatMap(_.s ceT ets).getOrElse(Seq.empty).f lter(_.t et.nonEmpty)
            .map { cand date =>
              (cand date.t et.get. d, cand date)
            }.toMap
        val s ceT etsByT et dMapFeature = FeatureMapBu lder()
          .add(T  l neRanker nNetworkS ceT etsByT et dMapFeature, s ceT etsByT et d)
          .bu ld()
        Cand datesW hS ceFeatures(
          cand dates = cand dates,
          features = s ceT etsByT et dMapFeature)
      }
  }
}
