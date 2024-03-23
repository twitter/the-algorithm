package com.ExTwitter.product_mixer.component_library.feature_hydrator.candidate.ads

import com.ExTwitter.adserver.{thriftscala => ad}
import com.ExTwitter.product_mixer.component_library.model.candidate.ads.AdsCandidate
import com.ExTwitter.product_mixer.component_library.model.query.ads.AdsQuery
import com.ExTwitter.product_mixer.core.feature.Feature
import com.ExTwitter.product_mixer.core.feature.FeatureWithDefaultOnFailure
import com.ExTwitter.product_mixer.core.feature.featuremap.FeatureMap
import com.ExTwitter.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.ExTwitter.product_mixer.core.functional_component.feature_hydrator.CandidateFeatureHydrator
import com.ExTwitter.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.ExTwitter.product_mixer.core.pipeline.PipelineQuery
import com.ExTwitter.stitch.Stitch
import com.ExTwitter.storehaus.ReadableStore
import com.ExTwitter.util.Future

import javax.inject.Inject
import javax.inject.Singleton

object AdvertiserBrandSafetySettingsFeature
    extends FeatureWithDefaultOnFailure[AdsCandidate, Option[ad.AdvertiserBrandSafetySettings]] {
  override val defaultValue = None
}

@Singleton
case class AdvertiserBrandSafetySettingsFeatureHydrator[
  Query <: PipelineQuery with AdsQuery,
  Candidate <: AdsCandidate] @Inject() (
  advertiserBrandSafetySettingsStore: ReadableStore[Long, ad.AdvertiserBrandSafetySettings])
    extends CandidateFeatureHydrator[Query, Candidate] {

  override val identifier: FeatureHydratorIdentifier = FeatureHydratorIdentifier(
    "AdvertiserBrandSafetySettings")

  override val features: Set[Feature[_, _]] = Set(AdvertiserBrandSafetySettingsFeature)

  override def apply(
    query: Query,
    candidate: Candidate,
    existingFeatures: FeatureMap
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
