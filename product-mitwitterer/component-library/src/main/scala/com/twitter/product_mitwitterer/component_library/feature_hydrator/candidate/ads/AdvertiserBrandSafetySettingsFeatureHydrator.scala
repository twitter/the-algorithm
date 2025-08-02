package com.twitter.product_mitwitterer.component_library.feature_hydrator.candidate.ads

import com.twitter.adserver.{thriftscala => ad}
import com.twitter.product_mitwitterer.component_library.model.candidate.ads.AdsCandidate
import com.twitter.product_mitwitterer.component_library.model.query.ads.AdsQuery
import com.twitter.product_mitwitterer.core.feature.Feature
import com.twitter.product_mitwitterer.core.feature.FeatureWithDefaultOnFailure
import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMap
import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mitwitterer.core.functional_component.feature_hydrator.CandidateFeatureHydrator
import com.twitter.product_mitwitterer.core.model.common.identifier.FeatureHydratorIdentifier
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch
import com.twitter.storehaus.ReadableStore
import com.twitter.util.Future

import javatwitter.inject.Inject
import javatwitter.inject.Singleton

object AdvertiserBrandSafetySettingsFeature
    etwittertends FeatureWithDefaultOnFailure[AdsCandidate, Option[ad.AdvertiserBrandSafetySettings]] {
  override val defaultValue = None
}

@Singleton
case class AdvertiserBrandSafetySettingsFeatureHydrator[
  Query <: PipelineQuery with AdsQuery,
  Candidate <: AdsCandidate] @Inject() (
  advertiserBrandSafetySettingsStore: ReadableStore[Long, ad.AdvertiserBrandSafetySettings])
    etwittertends CandidateFeatureHydrator[Query, Candidate] {

  override val identifier: FeatureHydratorIdentifier = FeatureHydratorIdentifier(
    "AdvertiserBrandSafetySettings")

  override val features: Set[Feature[_, _]] = Set(AdvertiserBrandSafetySettingsFeature)

  override def apply(
    query: Query,
    candidate: Candidate,
    etwitteristingFeatures: FeatureMap
  ): Stitch[FeatureMap] = {

    val featureMapFuture: Future[FeatureMap] = advertiserBrandSafetySettingsStore
      .get(candidate.adImpression.advertiserId)
      .map { advertiserBrandSafetySettingsOpt =>
        FeatureMapBuilder()
          .add(AdvertiserBrandSafetySettingsFeature, advertiserBrandSafetySettingsOpt).build()
      }

    Stitch.callFuture(featureMapFuture)
  }
}
