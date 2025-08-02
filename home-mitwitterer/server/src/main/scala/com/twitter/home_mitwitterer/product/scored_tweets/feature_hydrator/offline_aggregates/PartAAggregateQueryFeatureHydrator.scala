package com.twitter.home_mitwitterer.product.scored_tweets.feature_hydrator.offline_aggregates

import com.twitter.home_mitwitterer.param.HomeMitwittererInjectionNames.TimelineAggregateMetadataRepository
import com.twitter.home_mitwitterer.param.HomeMitwittererInjectionNames.TimelineAggregatePartARepository
import com.twitter.product_mitwitterer.core.model.common.identifier.FeatureHydratorIdentifier
import com.twitter.servo.repository.Repository
import com.twitter.timelines.data_processing.jobs.timeline_ranking_user_features.TimelinesPartAStoreRegister
import com.twitter.timelines.data_processing.ml_util.aggregation_framework.StoreConfig
import com.twitter.timelines.suggests.common.dense_data_record.thriftscala.DenseFeatureMetadata
import com.twitter.user_session_store.thriftjava.UserSession
import javatwitter.inject.Inject
import javatwitter.inject.Named
import javatwitter.inject.Singleton

object PartAAggregateRootFeature etwittertends BaseAggregateRootFeature {
  override val aggregateStores: Set[StoreConfig[_]] = TimelinesPartAStoreRegister.allStores
}

@Singleton
class PartAAggregateQueryFeatureHydrator @Inject() (
  @Named(TimelineAggregatePartARepository)
  repository: Repository[Long, Option[UserSession]],
  @Named(TimelineAggregateMetadataRepository)
  metadataRepository: Repository[Int, Option[DenseFeatureMetadata]])
    etwittertends BaseAggregateQueryFeatureHydrator(
      repository,
      metadataRepository,
      PartAAggregateRootFeature
    ) {

  override val identifier: FeatureHydratorIdentifier =
    FeatureHydratorIdentifier("PartAAggregateQuery")

  override val features = Set(PartAAggregateRootFeature)
}
