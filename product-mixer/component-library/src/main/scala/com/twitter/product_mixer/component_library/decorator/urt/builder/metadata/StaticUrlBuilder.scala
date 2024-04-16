package com.tw ter.product_m xer.component_l brary.decorator.urt.bu lder. tadata

 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder. tadata.BaseUrlBu lder
 mport com.tw ter.product_m xer.core.model.common.Un versalNoun
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.Url
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.UrlType
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery

case class Stat cUrlBu lder(url: Str ng, urlType: UrlType)
    extends BaseUrlBu lder[P pel neQuery, Un versalNoun[Any]] {

  overr de def apply(
    query: P pel neQuery,
    cand date: Un versalNoun[Any],
    cand dateFeatures: FeatureMap
  ): Url = Url(url = url, urlType = urlType)
}
