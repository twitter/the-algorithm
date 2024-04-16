package com.tw ter.ho _m xer.product.for_ .response_transfor r

 mport com.tw ter.ho _m xer.model.Ho Features.Author dFeature
 mport com.tw ter.ho _m xer.model.Ho Features. sT etPrev ewFeature
 mport com.tw ter.ho _m xer.model.Ho Features.SuggestTypeFeature
 mport com.tw ter.product_m xer.core.feature.Feature
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMapBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.transfor r.Cand dateFeatureTransfor r
 mport com.tw ter.product_m xer.core.model.common. dent f er.Transfor r dent f er
 mport com.tw ter.t  l neserv ce.suggests.{thr ftscala => st}
 mport com.tw ter.search.earlyb rd.{thr ftscala => eb}

object T etPrev ewResponseFeatureTransfor r
    extends Cand dateFeatureTransfor r[eb.Thr ftSearchResult] {

  overr de val  dent f er: Transfor r dent f er =
    Transfor r dent f er("T etPrev ewResponse")

  overr de val features: Set[Feature[_, _]] =
    Set(Author dFeature,  sT etPrev ewFeature, SuggestTypeFeature)

  def transform(
     nput: eb.Thr ftSearchResult
  ): FeatureMap = {
    FeatureMapBu lder()
      .add( sT etPrev ewFeature, true)
      .add(SuggestTypeFeature, So (st.SuggestType.T etPrev ew))
      .add(Author dFeature,  nput. tadata.map(_.fromUser d))
      .bu ld()
  }
}
