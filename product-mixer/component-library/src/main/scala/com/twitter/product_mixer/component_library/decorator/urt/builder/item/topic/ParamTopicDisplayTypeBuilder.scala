package com.tw ter.product_m xer.component_l brary.decorator.urt.bu lder. em.top c

 mport com.tw ter.product_m xer.component_l brary.model.cand date.Top cCand date
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.t  l nes.conf gap .FSEnumParam
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.top c.Bas cTop cD splayType
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.top c.P llTop cD splayType
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.top c.No conTop cD splayType
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.top c.P llW houtAct on conD splayType
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.top c.Top cD splayType
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder. em.top c.BaseTop cD splayTypeBu lder

object Top cCand dateD splayType extends Enu rat on {
  type Top cD splayType = Value

  val Bas c = Value
  val P ll = Value
  val No con = Value
  val P llW houtAct on con = Value
}

case class ParamTop cD splayTypeBu lder(
  d splayTypeParam: FSEnumParam[Top cCand dateD splayType.type])
    extends BaseTop cD splayTypeBu lder[P pel neQuery, Top cCand date] {

  overr de def apply(
    query: P pel neQuery,
    cand date: Top cCand date,
    cand dateFeatures: FeatureMap
  ): Opt on[Top cD splayType] = {
    val d splayType = query.params(d splayTypeParam)
    d splayType match {
      case Top cCand dateD splayType.Bas c => So (Bas cTop cD splayType)
      case Top cCand dateD splayType.P ll => So (P llTop cD splayType)
      case Top cCand dateD splayType.No con =>
        So (No conTop cD splayType)
      case Top cCand dateD splayType.P llW houtAct on con => So (P llW houtAct on conD splayType)
    }
  }
}
