package com.twitter.home_mitwitterer.product.scored_tweets.feature_hydrator.offline_aggregates

import com.twitter.home_mitwitterer.product.scored_tweets.feature_hydrator.offline_aggregates.EdgeAggregateFeatures._
import com.twitter.product_mitwitterer.core.model.common.identifier.FeatureHydratorIdentifier
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class Phase1EdgeAggregateFeatureHydrator @Inject() etwittertends BaseEdgeAggregateFeatureHydrator {

  override val identifier: FeatureHydratorIdentifier =
    FeatureHydratorIdentifier("Phase1EdgeAggregate")

  override val aggregateFeatures: Set[BaseEdgeAggregateFeature] = Set(
    UserAuthorAggregateFeature,
    UserOriginalAuthorAggregateFeature,
    UserMentionAggregateFeature
  )
}
