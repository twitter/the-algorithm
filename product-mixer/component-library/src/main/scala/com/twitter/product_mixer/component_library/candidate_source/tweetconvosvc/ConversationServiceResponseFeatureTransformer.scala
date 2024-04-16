package com.tw ter.product_m xer.component_l brary.cand date_s ce.t etconvosvc

 mport com.tw ter.product_m xer.component_l brary.model.cand date.T etCand date
 mport com.tw ter.product_m xer.core.feature.Feature
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMapBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.transfor r.Cand dateFeatureTransfor r
 mport com.tw ter.product_m xer.core.model.common. dent f er.Transfor r dent f er
 mport com.tw ter.t  l neserv ce.suggests.thr ftscala.SuggestType

object Author dFeature extends Feature[T etCand date, Opt on[Long]]
object Ancestor dsFeature extends Feature[T etCand date, Seq[Long]]
object Conversat onModuleFocalT et dFeature extends Feature[T etCand date, Opt on[Long]]
object  nReplyToFeature extends Feature[T etCand date, Opt on[Long]]
object  sRet etFeature extends Feature[T etCand date, Boolean]
object S ceT et dFeature extends Feature[T etCand date, Opt on[Long]]
object S ceUser dFeature extends Feature[T etCand date, Opt on[Long]]
object SuggestTypeFeature extends Feature[T etCand date, Opt on[SuggestType]]

object Conversat onServ ceResponseFeatureTransfor r
    extends Cand dateFeatureTransfor r[T etW hConversat on tadata] {
  overr de val  dent f er: Transfor r dent f er =
    Transfor r dent f er("Conversat onServ ceResponse")

  overr de val features: Set[Feature[_, _]] =
    Set(
      Author dFeature,
       nReplyToFeature,
       sRet etFeature,
      S ceT et dFeature,
      S ceUser dFeature,
      Conversat onModuleFocalT et dFeature,
      Ancestor dsFeature,
      SuggestTypeFeature
    )

  overr de def transform(cand date: T etW hConversat on tadata): FeatureMap = {
    FeatureMapBu lder()
      .add(Author dFeature, cand date.user d)
      .add( nReplyToFeature, cand date. nReplyToT et d)
      .add( sRet etFeature, cand date.s ceT et d. sDef ned)
      .add(S ceT et dFeature, cand date.s ceT et d)
      .add(S ceUser dFeature, cand date.s ceUser d)
      .add(Conversat onModuleFocalT et dFeature, cand date.conversat on d)
      .add(Ancestor dsFeature, cand date.ancestors.map(_.t et d))
      .add(SuggestTypeFeature, So (SuggestType.Organ cConversat on))
      .bu ld()
  }
}
