package com.tw ter.ho _m xer.product.scored_t ets.feature_hydrator.offl ne_aggregates

 mport com.tw ter.ho _m xer.product.scored_t ets.feature_hydrator.offl ne_aggregates.EdgeAggregateFeatures._
 mport com.tw ter.product_m xer.core.model.common. dent f er.FeatureHydrator dent f er
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class Phase1EdgeAggregateFeatureHydrator @ nject() extends BaseEdgeAggregateFeatureHydrator {

  overr de val  dent f er: FeatureHydrator dent f er =
    FeatureHydrator dent f er("Phase1EdgeAggregate")

  overr de val aggregateFeatures: Set[BaseEdgeAggregateFeature] = Set(
    UserAuthorAggregateFeature,
    UserOr g nalAuthorAggregateFeature,
    User nt onAggregateFeature
  )
}
