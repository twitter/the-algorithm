package com.tw ter.product_m xer.component_l brary.decorator.urt.bu lder.r chtext.tw ter_text

 mport com.tw ter.product_m xer.component_l brary.decorator.urt.bu lder.r chtext.R chTextReferenceObjectBu lder
 mport com.tw ter.product_m xer.component_l brary.decorator.urt.bu lder.r chtext.R chTextRtlOpt onBu lder
 mport com.tw ter.product_m xer.component_l brary.decorator.urt.bu lder.r chtext.Stat cR chTextRtlOpt onBu lder
 mport com.tw ter.product_m xer.component_l brary.decorator.urt.bu lder.r chtext.tw ter_text.Tw terTextEnt yProcessor.DefaultReferenceObjectBu lder
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder. tadata.BaseStr
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder.r chtext.BaseR chTextBu lder
 mport com.tw ter.product_m xer.core.model.common.Un versalNoun
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.r chtext.Pla n
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.r chtext.R chText
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.r chtext.R chTextAl gn nt
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.r chtext.R chTextFormat
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.r chtext.Strong
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery

case class Tw terTextR chTextBu lder[-Query <: P pel neQuery, -Cand date <: Un versalNoun[Any]](
  str ngBu lder: BaseStr[Query, Cand date],
  al gn nt: Opt on[R chTextAl gn nt] = None,
  formats: Set[R chTextFormat] = Set(Pla n, Strong),
  tw terTextRtlOpt onBu lder: R chTextRtlOpt onBu lder[Query] =
    Stat cR chTextRtlOpt onBu lder[Query](None),
  tw terTextReferenceObjectBu lder: R chTextReferenceObjectBu lder = DefaultReferenceObjectBu lder)
    extends BaseR chTextBu lder[Query, Cand date] {
  def apply(query: Query, cand date: Cand date, cand dateFeatures: FeatureMap): R chText = {
    val tw terTextRenderer = Tw terTextRenderer(
      text = str ngBu lder(query, cand date, cand dateFeatures),
      rtl = tw terTextRtlOpt onBu lder(query),
      al gn nt = al gn nt)

    tw terTextRenderer
      .transform(Tw terTextFormatProcessor(formats))
      .transform(Tw terTextEnt yProcessor(tw terTextReferenceObjectBu lder))
      .bu ld
  }
}
