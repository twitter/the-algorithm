package com.twitter.home_mixer.functional_component.feature_hydrator.offline_aggregates

import com.twitter.home_mixer.functional_component.feature_hydrator.offline_aggregates.EdgeAggregateFeatures.UserEngagerAggregateFeature
import com.twitter.home_mixer.functional_component.feature_hydrator.offline_aggregates.EdgeAggregateFeatures.UserEngagerGoodClickAggregateFeature
import com.twitter.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier

object UserEngagerEdgeAggregateFeatureHydrator extends BaseEdgeAggregateFeatureHydrator {

  override val identifier: FeatureHydratorIdentifier =
    FeatureHydratorIdentifier("UserEngagerEdgeAggregate")

  override val aggregateFeatures: Set[BaseEdgeAggregateFeature] = Set(
    UserEngagerAggregateFeature,
    UserEngagerGoodClickAggregateFeature,
  )
}
