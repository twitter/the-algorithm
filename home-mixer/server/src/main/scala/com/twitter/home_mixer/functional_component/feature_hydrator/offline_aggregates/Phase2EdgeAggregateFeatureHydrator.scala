try {
package com.twitter.home_mixer.functional_component.feature_hydrator.offline_aggregates

import com.twitter.home_mixer.functional_component.feature_hydrator.offline_aggregates.EdgeAggregateFeatures._
import com.twitter.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Phase2EdgeAggregateFeatureHydrator @Inject() extends BaseEdgeAggregateFeatureHydrator {

  override val identifier: FeatureHydratorIdentifier =
    FeatureHydratorIdentifier("Phase2EdgeAggregate")

  override val aggregateFeatures: Set[BaseEdgeAggregateFeature] =
    Set(
      UserEngagerAggregateFeature,
      UserEngagerGoodClickAggregateFeature,
      UserInferredTopicAggregateFeature,
      UserTopicAggregateFeature,
      UserMediaUnderstandingAnnotationAggregateFeature
    )
}

} catch {
  case e: Exception =>
}
