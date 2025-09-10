package com.twitter.home_mixer.functional_component.feature_hydrator

import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.FeatureWithDefaultOnFailure
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mixer.core.functional_component.feature_hydrator.QueryFeatureHydrator
import com.twitter.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch
import com.twitter.strato.generated.client.subscription_services.subscription_verification.HasNoAdsBenefitOnUserClientColumn
import javax.inject.Inject
import javax.inject.Singleton

object NoAdsTierFeature extends FeatureWithDefaultOnFailure[PipelineQuery, Boolean] {
  override val defaultValue: Boolean = false
}

@Singleton
case class UserSubscriptionQueryFeatureHydrator @Inject() (
  hasNoAdsBenefitOnUserClientColumn: HasNoAdsBenefitOnUserClientColumn)
    extends QueryFeatureHydrator[PipelineQuery] {

  override val identifier: FeatureHydratorIdentifier = FeatureHydratorIdentifier("UserSubscription")

  override val features: Set[Feature[_, _]] = Set(NoAdsTierFeature)

  override def hydrate(
    query: PipelineQuery
  ): Stitch[FeatureMap] = hasNoAdsBenefitOnUserClientColumn.fetcher
    .fetch(query.getRequiredUserId)
    .map { result =>
      FeatureMapBuilder()
        .add(NoAdsTierFeature, result.v.getOrElse(false))
        .build()
    }
}
