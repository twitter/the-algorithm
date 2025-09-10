package com.twitter.home_mixer.functional_component.feature_hydrator.offline_aggregates

import com.twitter.home_mixer.functional_component.feature_hydrator.offline_aggregates.EdgeAggregateFeatures.UserInferredTopicAggregateFeature
import com.twitter.home_mixer.functional_component.feature_hydrator.offline_aggregates.EdgeAggregateFeatures.UserInferredTopicAggregateV2Feature
import com.twitter.home_mixer.param.HomeGlobalParams.EnableTopicEdgeAggregateFeatureHydratorParam
import com.twitter.product_mixer.core.model.common.Conditionally
import com.twitter.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery

object TopicEdgeTruncatedAggregateFeatureHydrator
    extends BaseEdgeAggregateFeatureHydrator
    with Conditionally[PipelineQuery] {

  override def onlyIf(query: PipelineQuery): Boolean =
    !query.params(EnableTopicEdgeAggregateFeatureHydratorParam)

  override val identifier: FeatureHydratorIdentifier =
    FeatureHydratorIdentifier("TopicEdgeTruncatedAggregate")

  override val aggregateFeatures: Set[BaseEdgeAggregateFeature] = Set(
    UserInferredTopicAggregateFeature,
    UserInferredTopicAggregateV2Feature,
  )
}
