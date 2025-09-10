package com.twitter.home_mixer.functional_component.feature_hydrator.offline_aggregates

import com.twitter.home_mixer.functional_component.feature_hydrator.offline_aggregates.EdgeAggregateFeatures.UserMediaUnderstandingAnnotationAggregateFeature
import com.twitter.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier

object TweetContentEdgeAggregateFeatureHydrator extends BaseEdgeAggregateFeatureHydrator {

  override val identifier: FeatureHydratorIdentifier =
    FeatureHydratorIdentifier("TweetContentEdgeAggregate")

  override val aggregateFeatures: Set[BaseEdgeAggregateFeature] = Set(
    UserMediaUnderstandingAnnotationAggregateFeature
  )
}
