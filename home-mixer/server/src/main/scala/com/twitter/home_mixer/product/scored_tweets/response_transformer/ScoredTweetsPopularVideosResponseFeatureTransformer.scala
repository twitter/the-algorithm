package com.tw ter.ho _m xer.product.scored_t ets.response_transfor r

 mport com.tw ter.explore_ranker.{thr ftscala => ert}
 mport com.tw ter.ho _m xer.model.Ho Features.Author dFeature
 mport com.tw ter.ho _m xer.model.Ho Features.Cand dateS ce dFeature
 mport com.tw ter.ho _m xer.model.Ho Features.From nNetworkS ceFeature
 mport com.tw ter.ho _m xer.model.Ho Features.HasV deoFeature
 mport com.tw ter.ho _m xer.model.Ho Features. sRandomT etFeature
 mport com.tw ter.ho _m xer.model.Ho Features.StreamToKafkaFeature
 mport com.tw ter.ho _m xer.model.Ho Features.SuggestTypeFeature
 mport com.tw ter.product_m xer.core.feature.Feature
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMapBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.transfor r.Cand dateFeatureTransfor r
 mport com.tw ter.product_m xer.core.model.common. dent f er.Transfor r dent f er
 mport com.tw ter.t  l neserv ce.suggests.logg ng.cand date_t et_s ce_ d.{thr ftscala => cts}
 mport com.tw ter.t  l neserv ce.suggests.{thr ftscala => st}

object ScoredT etsPopularV deosResponseFeatureTransfor r
    extends Cand dateFeatureTransfor r[ert.ExploreT etRecom ndat on] {

  overr de val  dent f er: Transfor r dent f er =
    Transfor r dent f er("ScoredT etsPopularV deosResponse")

  overr de val features: Set[Feature[_, _]] = Set(
    Author dFeature,
    Cand dateS ce dFeature,
    From nNetworkS ceFeature,
    HasV deoFeature,
     sRandomT etFeature,
    StreamToKafkaFeature,
    SuggestTypeFeature
  )

  overr de def transform(cand date: ert.ExploreT etRecom ndat on): FeatureMap = {
    FeatureMapBu lder()
      .add(Author dFeature, cand date.author d)
      .add(Cand dateS ce dFeature, So (cts.Cand dateT etS ce d. d aT et))
      .add(From nNetworkS ceFeature, false)
      .add(HasV deoFeature, cand date. d aType.conta ns(ert. d aType.V deo))
      .add( sRandomT etFeature, false)
      .add(StreamToKafkaFeature, true)
      .add(SuggestTypeFeature, So (st.SuggestType. d aT et))
      .bu ld()
  }
}
