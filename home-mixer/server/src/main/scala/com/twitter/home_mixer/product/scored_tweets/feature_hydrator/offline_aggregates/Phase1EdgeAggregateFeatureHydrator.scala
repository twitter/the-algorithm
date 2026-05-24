package com.twitter.home_mixer.product.scored_tweets.feature_hydrator.offline_aggregates

import com.twitter.home_mixer.product.scored_tweets.feature_hydrator.offline_aggregates.EdgeAggregateFeatures._
import com.twitter.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Phase1EdgeAggregateFeatureHydrator @Inject() extends BaseEdgeAggregateFeatureHydrator {

  override val identifier: FeatureHydratorIdentifier =
    FeatureHydratorIdentifier("Phase1EdgeAggregate")

  override val aggregateFeatures: Set[BaseEdgeAggregateFeature] = Set(
    UserAuthorAggregateFeature,
    UserOriginalAuthorAggregateFeature,
    UserMentionAggregateFeature
  )
}
