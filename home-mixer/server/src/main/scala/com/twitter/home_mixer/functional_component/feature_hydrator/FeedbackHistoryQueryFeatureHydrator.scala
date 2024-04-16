package com.tw ter.ho _m xer.funct onal_component.feature_hydrator

 mport com.tw ter.ho _m xer.model.Ho Features.Feedback toryFeature
 mport com.tw ter.product_m xer.core.feature.Feature
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMapBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.feature_hydrator.QueryFeatureHydrator
 mport com.tw ter.product_m xer.core.model.common. dent f er.FeatureHydrator dent f er
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.st ch.St ch
 mport com.tw ter.t  l nem xer.cl ents.feedback.Feedback toryManhattanCl ent
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
case class Feedback toryQueryFeatureHydrator @ nject() (
  feedback toryCl ent: Feedback toryManhattanCl ent)
    extends QueryFeatureHydrator[P pel neQuery] {

  overr de val  dent f er: FeatureHydrator dent f er = FeatureHydrator dent f er("Feedback tory")

  overr de val features: Set[Feature[_, _]] = Set(Feedback toryFeature)

  overr de def hydrate(
    query: P pel neQuery
  ): St ch[FeatureMap] =
    St ch
      .callFuture(feedback toryCl ent.get(query.getRequ redUser d))
      .map { feedback tory =>
        FeatureMapBu lder().add(Feedback toryFeature, feedback tory).bu ld()
      }
}
