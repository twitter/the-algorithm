package com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder.r chtext

 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.model.common.Un versalNoun
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.r chtext.R chText
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery

tra  BaseR chTextBu lder[-Query <: P pel neQuery, -Cand date <: Un versalNoun[Any]] {

  def apply(query: Query, cand date: Cand date, cand dateFeatures: FeatureMap): R chText
}
