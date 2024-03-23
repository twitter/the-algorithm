package com.ExTwitter.home_mixer.product.scored_tweets.feature_hydrator.offline_aggregates

import com.ExTwitter.home_mixer.param.HomeMixerInjectionNames.TimelineAggregateMetadataRepository
import com.ExTwitter.home_mixer.param.HomeMixerInjectionNames.TimelineAggregatePartARepository
import com.ExTwitter.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.ExTwitter.servo.repository.Repository
import com.ExTwitter.timelines.data_processing.jobs.timeline_ranking_user_features.TimelinesPartAStoreRegister
import com.ExTwitter.timelines.data_processing.ml_util.aggregation_framework.StoreConfig
import com.ExTwitter.timelines.suggests.common.dense_data_record.thriftscala.DenseFeatureMetadata
import com.ExTwitter.user_session_store.thriftjava.UserSession
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

object PartAAggregateRootFeature extends BaseAggregateRootFeature {
  override val aggregateStores: Set[StoreConfig[_]] = TimelinesPartAStoreRegister.allStores
}

@Singleton
class PartAAggregateQueryFeatureHydrator @Inject() (
  @Named(TimelineAggregatePartARepository)
  repository: Repository[Long, Option[UserSession]],
  @Named(TimelineAggregateMetadataRepository)
  metadataRepository: Repository[Int, Option[DenseFeatureMetadata]])
    extends BaseAggregateQueryFeatureHydrator(
      repository,
      metadataRepository,
      PartAAggregateRootFeature
    ) {

  override val identifier: FeatureHydratorIdentifier =
    FeatureHydratorIdentifier("PartAAggregateQuery")

  override val features = Set(PartAAggregateRootFeature)
}
