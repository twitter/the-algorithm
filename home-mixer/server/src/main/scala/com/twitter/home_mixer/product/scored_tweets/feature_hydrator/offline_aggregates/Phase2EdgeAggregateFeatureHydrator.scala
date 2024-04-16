package com.tw ter.ho _m xer.product.scored_t ets.feature_hydrator.offl ne_aggregates

 mport com.tw ter.ho _m xer.product.scored_t ets.feature_hydrator.offl ne_aggregates.EdgeAggregateFeatures.UserEngagerAggregateFeature
 mport com.tw ter.ho _m xer.product.scored_t ets.feature_hydrator.offl ne_aggregates.EdgeAggregateFeatures.UserEngagerGoodCl ckAggregateFeature
 mport com.tw ter.ho _m xer.product.scored_t ets.feature_hydrator.offl ne_aggregates.EdgeAggregateFeatures.User nferredTop cAggregateFeature
 mport com.tw ter.ho _m xer.product.scored_t ets.feature_hydrator.offl ne_aggregates.EdgeAggregateFeatures.User nferredTop cAggregateV2Feature
 mport com.tw ter.ho _m xer.product.scored_t ets.feature_hydrator.offl ne_aggregates.EdgeAggregateFeatures.User d aUnderstand ngAnnotat onAggregateFeature
 mport com.tw ter.ho _m xer.product.scored_t ets.feature_hydrator.offl ne_aggregates.EdgeAggregateFeatures.UserTop cAggregateFeature
 mport com.tw ter.product_m xer.core.model.common. dent f er.FeatureHydrator dent f er
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class Phase2EdgeAggregateFeatureHydrator @ nject() extends BaseEdgeAggregateFeatureHydrator {

  overr de val  dent f er: FeatureHydrator dent f er =
    FeatureHydrator dent f er("Phase2EdgeAggregate")

  overr de val aggregateFeatures: Set[BaseEdgeAggregateFeature] =
    Set(
      UserEngagerAggregateFeature,
      UserEngagerGoodCl ckAggregateFeature,
      User nferredTop cAggregateFeature,
      User nferredTop cAggregateV2Feature,
      UserTop cAggregateFeature,
      User d aUnderstand ngAnnotat onAggregateFeature
    )
}
