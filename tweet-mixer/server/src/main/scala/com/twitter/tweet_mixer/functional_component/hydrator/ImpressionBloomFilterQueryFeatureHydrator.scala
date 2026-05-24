package com.twitter.tweet_mixer.functional_component.hydrator

import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.FeatureWithDefaultOnFailure
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.functional_component.feature_hydrator.QueryFeatureHydrator
import com.twitter.product_mixer.core.model.common.Conditionally
import com.twitter.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch
import com.twitter.storehaus.Store
import com.twitter.timelines.impressionbloomfilter.{thriftscala => blm}
import com.twitter.tweet_mixer.model.ModuleNames.MemcachedImpressionBloomFilterStore
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.EnableImpressionBloomFilterHydrator
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

object ImpressionBloomFilterFeature
    extends FeatureWithDefaultOnFailure[PipelineQuery, blm.ImpressionBloomFilterSeq] {
  override def defaultValue: blm.ImpressionBloomFilterSeq =
    blm.ImpressionBloomFilterSeq(Seq.empty)
}

@Singleton
case class ImpressionBloomFilterQueryFeatureHydratorFactory @Inject() (
  @Named(MemcachedImpressionBloomFilterStore) bloomFilterClient: Store[
    blm.ImpressionBloomFilterKey,
    blm.ImpressionBloomFilterSeq
  ]) {

  def build[Query <: PipelineQuery](
    surfaceArea: blm.SurfaceArea
  ): ImpressionBloomFilterQueryFeatureHydrator[Query] =
    ImpressionBloomFilterQueryFeatureHydrator(bloomFilterClient, surfaceArea)
}

case class ImpressionBloomFilterQueryFeatureHydrator[Query <: PipelineQuery](
  @Named(MemcachedImpressionBloomFilterStore) bloomFilterClient: Store[
    blm.ImpressionBloomFilterKey,
    blm.ImpressionBloomFilterSeq
  ],
  surfaceArea: blm.SurfaceArea)
    extends QueryFeatureHydrator[Query]
    with Conditionally[Query] {

  override val identifier: FeatureHydratorIdentifier =
    FeatureHydratorIdentifier("ImpressionBloomFilter")

  override val features: Set[Feature[_, _]] = Set(ImpressionBloomFilterFeature)

  override def onlyIf(query: Query): Boolean = query.params(EnableImpressionBloomFilterHydrator)

  override def hydrate(query: Query): Stitch[FeatureMap] = Stitch.callFuture {
    bloomFilterClient
      .get(blm.ImpressionBloomFilterKey(query.getRequiredUserId, surfaceArea))
      .map(_.getOrElse(blm.ImpressionBloomFilterSeq(Seq.empty)))
      .map(FeatureMap(ImpressionBloomFilterFeature, _))
  }
}
