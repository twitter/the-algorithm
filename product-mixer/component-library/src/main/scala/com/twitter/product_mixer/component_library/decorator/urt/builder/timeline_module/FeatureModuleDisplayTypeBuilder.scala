package com.tw ter.product_m xer.component_l brary.decorator.urt.bu lder.t  l ne_module

 mport com.tw ter.product_m xer.core.feature.Feature
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder.t  l ne_module.BaseModuleD splayTypeBu lder
 mport com.tw ter.product_m xer.core.model.common.Cand dateW hFeatures
 mport com.tw ter.product_m xer.core.model.common.Un versalNoun
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.t  l ne_module.ModuleD splayType
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.t  l ne_module.Vert calConversat on
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery

case class FeatureModuleD splayTypeBu lder(
  d splayTypeFeature: Feature[_, Opt on[ModuleD splayType]],
  defaultD splayType: ModuleD splayType = Vert calConversat on)
    extends BaseModuleD splayTypeBu lder[P pel neQuery, Un versalNoun[Any]] {

  overr de def apply(
    query: P pel neQuery,
    cand dates: Seq[Cand dateW hFeatures[Un versalNoun[Any]]]
  ): ModuleD splayType = cand dates. adOpt on
    .flatMap(_.features.getOrElse(d splayTypeFeature, None))
    .getOrElse(defaultD splayType)
}
