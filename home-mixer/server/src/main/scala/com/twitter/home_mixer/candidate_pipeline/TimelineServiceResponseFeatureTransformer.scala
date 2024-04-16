package com.tw ter.ho _m xer.cand date_p pel ne

 mport com.tw ter.ho _m xer.model.Ho Features.Author dFeature
 mport com.tw ter.ho _m xer.model.Ho Features. nReplyToT et dFeature
 mport com.tw ter.ho _m xer.model.Ho Features. sRet etFeature
 mport com.tw ter.ho _m xer.model.Ho Features.S ceT et dFeature
 mport com.tw ter.ho _m xer.model.Ho Features.S ceUser dFeature
 mport com.tw ter.product_m xer.core.feature.Feature
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMapBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.transfor r.Cand dateFeatureTransfor r
 mport com.tw ter.product_m xer.core.model.common. dent f er.Transfor r dent f er
 mport com.tw ter.t  l neserv ce.{thr ftscala => t}

object T  l neServ ceResponseFeatureTransfor r extends Cand dateFeatureTransfor r[t.T et] {

  overr de val  dent f er: Transfor r dent f er = Transfor r dent f er("T  l neServ ceResponse")

  overr de val features: Set[Feature[_, _]] = Set(
    Author dFeature,
     nReplyToT et dFeature,
     sRet etFeature,
    S ceT et dFeature,
    S ceUser dFeature,
  )

  overr de def transform(cand date: t.T et): FeatureMap = FeatureMapBu lder()
    .add(Author dFeature, cand date.user d)
    .add( nReplyToT et dFeature, cand date. nReplyToStatus d)
    .add( sRet etFeature, cand date.s ceStatus d. sDef ned)
    .add(S ceT et dFeature, cand date.s ceStatus d)
    .add(S ceUser dFeature, cand date.s ceUser d)
    .bu ld()
}
