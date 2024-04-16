package com.tw ter.ho _m xer.cand date_p pel ne

 mport com.tw ter.ho _m xer.model.Ho Features._
 mport com.tw ter.product_m xer.component_l brary.cand date_s ce.t etconvosvc.T etW hConversat on tadata
 mport com.tw ter.product_m xer.core.feature.Feature
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMapBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.transfor r.Cand dateFeatureTransfor r
 mport com.tw ter.product_m xer.core.model.common. dent f er.Transfor r dent f er
 mport com.tw ter.t  l neserv ce.suggests.thr ftscala.SuggestType

object Conversat onServ ceResponseFeatureTransfor r
    extends Cand dateFeatureTransfor r[T etW hConversat on tadata] {

  overr de val  dent f er: Transfor r dent f er =
    Transfor r dent f er("Conversat onServ ceResponse")

  overr de val features: Set[Feature[_, _]] = Set(
    Author dFeature,
     nReplyToT et dFeature,
     sRet etFeature,
    S ceT et dFeature,
    S ceUser dFeature,
    Conversat onModuleFocalT et dFeature,
    AncestorsFeature,
    SuggestTypeFeature
  )

  overr de def transform(cand date: T etW hConversat on tadata): FeatureMap = FeatureMapBu lder()
    .add(Author dFeature, cand date.user d)
    .add( nReplyToT et dFeature, cand date. nReplyToT et d)
    .add( sRet etFeature, cand date.s ceT et d. sDef ned)
    .add(S ceT et dFeature, cand date.s ceT et d)
    .add(S ceUser dFeature, cand date.s ceUser d)
    .add(Conversat onModuleFocalT et dFeature, cand date.conversat on d)
    .add(AncestorsFeature, cand date.ancestors)
    .add(SuggestTypeFeature, So (SuggestType.RankedOrgan cT et))
    .bu ld()
}
