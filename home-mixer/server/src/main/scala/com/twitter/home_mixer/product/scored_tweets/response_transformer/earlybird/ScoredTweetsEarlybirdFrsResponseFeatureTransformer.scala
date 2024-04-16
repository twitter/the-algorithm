package com.tw ter.ho _m xer.product.scored_t ets.response_transfor r.earlyb rd

 mport com.tw ter.ho _m xer.model.Ho Features.Cand dateS ce dFeature
 mport com.tw ter.ho _m xer.model.Ho Features.SuggestTypeFeature
 mport com.tw ter.product_m xer.core.feature.Feature
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMapBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.transfor r.Cand dateFeatureTransfor r
 mport com.tw ter.product_m xer.core.model.common. dent f er.Transfor r dent f er
 mport com.tw ter.search.earlyb rd.{thr ftscala => eb}
 mport com.tw ter.t  l neserv ce.suggests.logg ng.cand date_t et_s ce_ d.{thr ftscala => cts}
 mport com.tw ter.t  l neserv ce.suggests.{thr ftscala => st}

object ScoredT etsEarlyb rdFrsResponseFeatureTransfor r
    extends Cand dateFeatureTransfor r[eb.Thr ftSearchResult] {

  overr de val  dent f er: Transfor r dent f er =
    Transfor r dent f er("ScoredT etsEarlyb rdFrsResponse")

  overr de val features: Set[Feature[_, _]] = Earlyb rdResponseTransfor r.features

  overr de def transform(cand date: eb.Thr ftSearchResult): FeatureMap = {

    val baseFeatures = Earlyb rdResponseTransfor r.transform(cand date)

    val features = FeatureMapBu lder()
      .add(Cand dateS ce dFeature, So (cts.Cand dateT etS ce d.FrsT et))
      .add(SuggestTypeFeature, So (st.SuggestType.FrsT et))
      .bu ld()

    baseFeatures ++ features
  }
}
