package com.twitter.tweet_mixer.functional_component.hydrator

import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.FeatureWithDefaultOnFailure
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mixer.core.functional_component.feature_hydrator.QueryFeatureHydrator
import com.twitter.product_mixer.core.model.common.Conditionally
import com.twitter.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch
import com.twitter.storehaus.Store
import com.twitter.timelines.impressionbloomfilter.{thriftscala => blm}
import com.twitter.tweet_mixer.model.ModuleNames.MemcachedImpressionVideoBloomFilterStore
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.EnableVideoBloomFilterHydrator
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

object VideoImpressionBloomFilterFeature
    extends FeatureWithDefaultOnFailure[PipelineQuery, blm.ImpressionBloomFilterSeq] {
  override def defaultValue: blm.ImpressionBloomFilterSeq =
    blm.ImpressionBloomFilterSeq(Seq.empty)
}

@Singleton
case class ImpressionVideoBloomFilterQueryFeatureHydrator @Inject() (
  @Named(MemcachedImpressionVideoBloomFilterStore) bloomFilterClient: Store[
    blm.ImpressionBloomFilterKey,
    blm.ImpressionBloomFilterSeq
  ]) extends QueryFeatureHydrator[PipelineQuery]
    with Conditionally[PipelineQuery] {

  override val identifier: FeatureHydratorIdentifier =
    FeatureHydratorIdentifier("ImpressionVideoBloomFilter")

  override val features: Set[Feature[_, _]] = Set(VideoImpressionBloomFilterFeature)

  private val SurfaceArea = blm.SurfaceArea.Explore

  override def onlyIf(query: PipelineQuery): Boolean =
    query.params(EnableVideoBloomFilterHydrator)

  override def hydrate(query: PipelineQuery): Stitch[FeatureMap] = {
    val userId = query.getRequiredUserId
    Stitch.callFuture {
      bloomFilterClient
        .get(blm.ImpressionBloomFilterKey(userId, SurfaceArea))
        .map(_.getOrElse(blm.ImpressionBloomFilterSeq(Seq.empty)))
        .map { bloomFilterSeq =>
          FeatureMapBuilder()
            .add(VideoImpressionBloomFilterFeature, bloomFilterSeq).build()
        }
    }
  }
}
