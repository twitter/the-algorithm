package com.tw ter.product_m xer.component_l brary.decorator.urt.bu lder.r chtext

 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder. tadata.BaseStr
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder.r chtext.BaseR chTextBu lder
 mport com.tw ter.product_m xer.core.model.common.Un versalNoun
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.UrlType
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.r chtext.R chText
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.r chtext.R chTextAl gn nt
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery

case class R chTextBu lder[-Query <: P pel neQuery, -Cand date <: Un versalNoun[Any]](
  textBu lder: BaseStr[Query, Cand date],
  l nkMap: Map[Str ng, Str ng],
  rtl: Opt on[Boolean],
  al gn nt: Opt on[R chTextAl gn nt],
  l nkTypeMap: Map[Str ng, UrlType] = Map.empty)
    extends BaseR chTextBu lder[Query, Cand date] {

  def apply(query: Query, cand date: Cand date, cand dateFeatures: FeatureMap): R chText = {
    R chTextMarkupUt l.r chTextFromMarkup(
      text = textBu lder(query, cand date, cand dateFeatures),
      l nkMap = l nkMap,
      rtl = rtl,
      al gn nt = al gn nt,
      l nkTypeMap = l nkTypeMap)
  }
}
