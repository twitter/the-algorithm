package com.tw ter.ho _m xer.product.scored_t ets.response_transfor r

 mport com.tw ter.t et_m xer.{thr ftscala => tmt}
 mport com.tw ter.ho _m xer.model.Ho Features._
 mport com.tw ter.product_m xer.core.feature.Feature
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMapBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.transfor r.Cand dateFeatureTransfor r
 mport com.tw ter.product_m xer.core.model.common. dent f er.Transfor r dent f er
 mport com.tw ter.t  l neserv ce.suggests.logg ng.cand date_t et_s ce_ d.{thr ftscala => cts}
 mport com.tw ter.t  l neserv ce.suggests.{thr ftscala => st}
 mport com.tw ter.tsp.{thr ftscala => tsp}

object ScoredT etsT etM xerResponseFeatureTransfor r
    extends Cand dateFeatureTransfor r[tmt.T etResult] {

  overr de val  dent f er: Transfor r dent f er =
    Transfor r dent f er("ScoredT etsT etM xerResponse")

  overr de val features: Set[Feature[_, _]] = Set(
    Cand dateS ce dFeature,
    From nNetworkS ceFeature,
     sRandomT etFeature,
    StreamToKafkaFeature,
    SuggestTypeFeature,
    TSP tr cTagFeature
  )

  overr de def transform(cand date: tmt.T etResult): FeatureMap = {
    val t etM xer tr cTags = cand date. tr cTags.getOrElse(Seq.empty)
    val tsp tr cTag = t etM xer tr cTags
      .map(T etM xer tr cTagToTsp tr cTag)
      .f lter(_.nonEmpty).map(_.get).toSet

    FeatureMapBu lder()
      .add(Cand dateS ce dFeature, So (cts.Cand dateT etS ce d.S mcluster))
      .add(From nNetworkS ceFeature, false)
      .add( sRandomT etFeature, false)
      .add(StreamToKafkaFeature, true)
      .add(SuggestTypeFeature, So (st.SuggestType.ScT et))
      .add(TSP tr cTagFeature, tsp tr cTag)
      .bu ld()
  }

  pr vate def T etM xer tr cTagToTsp tr cTag(
    t etM xer tr cTag: tmt. tr cTag
  ): Opt on[tsp. tr cTag] = t etM xer tr cTag match {
    case tmt. tr cTag.T etFavor e => So (tsp. tr cTag.T etFavor e)
    case tmt. tr cTag.Ret et => So (tsp. tr cTag.Ret et)
    case _ => None
  }
}
