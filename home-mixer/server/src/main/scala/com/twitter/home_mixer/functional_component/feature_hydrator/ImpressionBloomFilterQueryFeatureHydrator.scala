package com.twitter.home_mixer.functional_component.feature_hydrator

import com.twitter.home_mixer.model.HomeFeatures.ImpressionBloomFilterFeature
import com.twitter.home_mixer.param.HomeMixerInjectionNames.MemcachedImpressionBloomFilterStore
import com.twitter.home_mixer.service.HomeMixerAlertConfig
import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mixer.core.functional_component.feature_hydrator.QueryFeatureHydrator
import com.twitter.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch
import com.twitter.storehaus.Store
import com.twitter.timelines.impressionbloomfilter.{thriftscala => blm}
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
case class ImpressionBloomFilterQueryFeatureHydrator @Inject() (
  @Named(MemcachedImpressionBloomFilterStore) bloomFilterClient: Store[
    blm.ImpressionBloomFilterKey,
    blm.ImpressionBloomFilterSeq
  ]) extends QueryFeatureHydrator[PipelineQuery] {

  override val identifier: FeatureHydratorIdentifier =
    FeatureHydratorIdentifier("ImpressionBloomFilter")

  override val features: Set[Feature[_, _]] = Set(ImpressionBloomFilterFeature)

  private val SurfaceArea = blm.SurfaceArea.HomeTimeline

  override def hydrate(query: PipelineQuery): Stitch[FeatureMap] = {
    val userId = query.getRequiredUserId
    Stitch.callFuture {
      bloomFilterClient
        .get(blm.ImpressionBloomFilterKey(userId, SurfaceArea))
        .map(_.getOrElse(blm.ImpressionBloomFilterSeq(Seq.empty)))
        .map { bloomFilterSeq =>
          FeatureMapBuilder().add(ImpressionBloomFilterFeature, bloomFilterSeq).build()
        }
    }
  }

  override val alerts = Seq(HomeMixerAlertConfig.BusinessHours.defaultSuccessRateAlert(99))
}
