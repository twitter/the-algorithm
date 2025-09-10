package com.twitter.home_mixer.functional_component.feature_hydrator.offline_aggregates

import com.twitter.home_mixer.functional_component.feature_hydrator.offline_aggregates.EdgeAggregateFeatures.UserAuthorAggregateFeature
import com.twitter.home_mixer.functional_component.feature_hydrator.offline_aggregates.EdgeAggregateFeatures.UserMentionAggregateFeature
import com.twitter.home_mixer.functional_component.feature_hydrator.offline_aggregates.EdgeAggregateFeatures.UserOriginalAuthorAggregateFeature
import com.twitter.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier

object UserEntityAggregateFeatureHydrator extends BaseEdgeAggregateFeatureHydrator {

  override val identifier: FeatureHydratorIdentifier =
    FeatureHydratorIdentifier("UserEntityEdgeAggregate")

  override val aggregateFeatures: Set[BaseEdgeAggregateFeature] = Set(
    UserAuthorAggregateFeature,
    UserOriginalAuthorAggregateFeature,
    UserMentionAggregateFeature
  )
}
