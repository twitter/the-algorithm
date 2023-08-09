package com.twitter.home_mixer.product.scored_tweets.feature_hydrator.offline_aggregates

import com.twitter.home_mixer.product.scored_tweets.feature_hydrator.offline_aggregates.EdgeAggregateFeatures.UserEngagerAggregateFeature
import com.twitter.home_mixer.product.scored_tweets.feature_hydrator.offline_aggregates.EdgeAggregateFeatures.UserEngagerGoodClickAggregateFeature
import com.twitter.home_mixer.product.scored_tweets.feature_hydrator.offline_aggregates.EdgeAggregateFeatures.UserInferredTopicAggregateFeature
import com.twitter.home_mixer.product.scored_tweets.feature_hydrator.offline_aggregates.EdgeAggregateFeatures.UserInferredTopicAggregateV2Feature
import com.twitter.home_mixer.product.scored_tweets.feature_hydrator.offline_aggregates.EdgeAggregateFeatures.UserMediaUnderstandingAnnotationAggregateFeature
import com.twitter.home_mixer.product.scored_tweets.feature_hydrator.offline_aggregates.EdgeAggregateFeatures.UserTopicAggregateFeature
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
      UserInferredTopicAggregateV2Feature,
      UserTopicAggregateFeature,
      UserMediaUnderstandingAnnotationAggregateFeature
    )
}
