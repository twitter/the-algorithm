package com.tw ter.ho _m xer.product.scored_t ets.response_transfor r

 mport com.tw ter.ho _m xer.model.Ho Features.Author dFeature
 mport com.tw ter.ho _m xer.model.Ho Features.Cand dateS ce dFeature
 mport com.tw ter.ho _m xer.model.Ho Features.From nNetworkS ceFeature
 mport com.tw ter.ho _m xer.model.Ho Features. sRet etFeature
 mport com.tw ter.ho _m xer.model.Ho Features.S ceT et dFeature
 mport com.tw ter.ho _m xer.model.Ho Features.S ceUser dFeature
 mport com.tw ter.ho _m xer.model.Ho Features.SuggestTypeFeature
 mport com.tw ter.product_m xer.core.feature.Feature
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMapBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.transfor r.Cand dateFeatureTransfor r
 mport com.tw ter.product_m xer.core.model.common. dent f er.Transfor r dent f er
 mport com.tw ter.t  l neserv ce.{thr ftscala => t}
 mport com.tw ter.t  l neserv ce.suggests.{thr ftscala => st}
 mport com.tw ter.t  l neserv ce.suggests.logg ng.cand date_t et_s ce_ d.{thr ftscala => cts}

object ScoredT etsL stsResponseFeatureTransfor r extends Cand dateFeatureTransfor r[t.T et] {

  overr de val  dent f er: Transfor r dent f er =
    Transfor r dent f er("ScoredT etsL stsResponse")

  overr de val features: Set[Feature[_, _]] = Set(
    Author dFeature,
    Cand dateS ce dFeature,
    From nNetworkS ceFeature,
     sRet etFeature,
    SuggestTypeFeature,
    S ceT et dFeature,
    S ceUser dFeature,
  )

  overr de def transform(cand date: t.T et): FeatureMap = {
    FeatureMapBu lder()
      .add(Author dFeature, cand date.user d)
      .add(Cand dateS ce dFeature, So (cts.Cand dateT etS ce d.L stT et))
      .add(From nNetworkS ceFeature, false)
      .add( sRet etFeature, cand date.s ceStatus d. sDef ned)
      .add(SuggestTypeFeature, So (st.SuggestType.RankedL stT et))
      .add(S ceT et dFeature, cand date.s ceStatus d)
      .add(S ceUser dFeature, cand date.s ceUser d)
      .bu ld()
  }
}
