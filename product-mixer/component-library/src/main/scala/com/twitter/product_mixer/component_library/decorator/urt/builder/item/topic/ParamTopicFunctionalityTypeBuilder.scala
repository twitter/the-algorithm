package com.tw ter.product_m xer.component_l brary.decorator.urt.bu lder. em.top c

 mport com.tw ter.product_m xer.component_l brary.model.cand date.Top cCand date
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.top c.Bas cTop cFunct onal yType
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.top c.P votTop cFunct onal yType
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.top c.Recom ndat onTop cFunct onal yType
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.top c.Top cFunct onal yType
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder. em.top c.BaseTop cFunct onal yTypeBu lder
 mport com.tw ter.t  l nes.conf gap .FSEnumParam

object Top cFunct onal yTypeParamValue extends Enu rat on {
  type Top cFunct onal yType = Value

  val Bas c = Value
  val P vot = Value
  val Recom ndat on = Value
}

case class ParamTop cFunct onal yTypeBu lder(
  funct onal yTypeParam: FSEnumParam[Top cFunct onal yTypeParamValue.type])
    extends BaseTop cFunct onal yTypeBu lder[P pel neQuery, Top cCand date] {

  overr de def apply(
    query: P pel neQuery,
    cand date: Top cCand date,
    cand dateFeatures: FeatureMap
  ): Opt on[Top cFunct onal yType] = {
    val funct onal yType = query.params(funct onal yTypeParam)
    funct onal yType match {
      case Top cFunct onal yTypeParamValue.Bas c => So (Bas cTop cFunct onal yType)
      case Top cFunct onal yTypeParamValue.P vot => So (P votTop cFunct onal yType)
      case Top cFunct onal yTypeParamValue.Recom ndat on =>
        So (Recom ndat onTop cFunct onal yType)
    }
  }
}
