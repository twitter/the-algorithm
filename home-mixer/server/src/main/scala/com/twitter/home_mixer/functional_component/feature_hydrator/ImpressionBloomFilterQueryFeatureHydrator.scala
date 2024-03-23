package com.ExTwitter.home_mixer.functional_component.feature_hydrator

import com.ExTwitter.conversions.DurationOps._
import com.ExTwitter.home_mixer.model.HomeFeatures.ImpressionBloomFilterFeature
import com.ExTwitter.home_mixer.model.request.HasSeenTweetIds
import com.ExTwitter.home_mixer.param.HomeGlobalParams.ImpressionBloomFilterFalsePositiveRateParam
import com.ExTwitter.home_mixer.service.HomeMixerAlertConfig
import com.ExTwitter.product_mixer.core.feature.Feature
import com.ExTwitter.product_mixer.core.feature.featuremap.FeatureMap
import com.ExTwitter.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.ExTwitter.product_mixer.core.functional_component.feature_hydrator.QueryFeatureHydrator
import com.ExTwitter.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.ExTwitter.product_mixer.core.pipeline.PipelineQuery
import com.ExTwitter.stitch.Stitch
import com.ExTwitter.timelines.clients.manhattan.store.ManhattanStoreClient
import com.ExTwitter.timelines.impressionbloomfilter.{thriftscala => blm}
import com.ExTwitter.timelines.impressionstore.impressionbloomfilter.ImpressionBloomFilter
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
case class ImpressionBloomFilterQueryFeatureHydrator[
  Query <: PipelineQuery with HasSeenTweetIds] @Inject() (
  bloomFilterClient: ManhattanStoreClient[
    blm.ImpressionBloomFilterKey,
    blm.ImpressionBloomFilterSeq
  ]) extends QueryFeatureHydrator[Query] {

  override val identifier: FeatureHydratorIdentifier = FeatureHydratorIdentifier(
    "ImpressionBloomFilter")

  private val ImpressionBloomFilterTTL = 7.day

  override val features: Set[Feature[_, _]] = Set(ImpressionBloomFilterFeature)

  private val SurfaceArea = blm.SurfaceArea.HomeTimeline

  override def hydrate(query: Query): Stitch[FeatureMap] = {
    val userId = query.getRequiredUserId
    bloomFilterClient
      .get(blm.ImpressionBloomFilterKey(userId, SurfaceArea))
      .map(_.getOrElse(blm.ImpressionBloomFilterSeq(Seq.empty)))
      .map { bloomFilterSeq =>
        val updatedBloomFilterSeq =
          if (query.seenTweetIds.forall(_.isEmpty)) bloomFilterSeq
          else {
            ImpressionBloomFilter.addSeenTweetIds(
              surfaceArea = SurfaceArea,
              tweetIds = query.seenTweetIds.get,
              bloomFilterSeq = bloomFilterSeq,
              timeToLive = ImpressionBloomFilterTTL,
              falsePositiveRate = query.params(ImpressionBloomFilterFalsePositiveRateParam)
            )
          }
        FeatureMapBuilder().add(ImpressionBloomFilterFeature, updatedBloomFilterSeq).build()
      }
  }

  override val alerts = Seq(
    HomeMixerAlertConfig.BusinessHours.defaultSuccessRateAlert(99.8)
  )
}
