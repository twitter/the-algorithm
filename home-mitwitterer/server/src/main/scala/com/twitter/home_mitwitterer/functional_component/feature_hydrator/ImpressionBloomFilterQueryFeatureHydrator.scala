package com.twitter.home_mitwitterer.functional_component.feature_hydrator

import com.twitter.conversions.DurationOps._
import com.twitter.home_mitwitterer.model.HomeFeatures.ImpressionBloomFilterFeature
import com.twitter.home_mitwitterer.model.request.HasSeenTweetIds
import com.twitter.home_mitwitterer.param.HomeGlobalParams.ImpressionBloomFilterFalsePositiveRateParam
import com.twitter.home_mitwitterer.service.HomeMitwittererAlertConfig
import com.twitter.product_mitwitterer.core.feature.Feature
import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMap
import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mitwitterer.core.functional_component.feature_hydrator.QueryFeatureHydrator
import com.twitter.product_mitwitterer.core.model.common.identifier.FeatureHydratorIdentifier
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch
import com.twitter.timelines.clients.manhattan.store.ManhattanStoreClient
import com.twitter.timelines.impressionbloomfilter.{thriftscala => blm}
import com.twitter.timelines.impressionstore.impressionbloomfilter.ImpressionBloomFilter
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
case class ImpressionBloomFilterQueryFeatureHydrator[
  Query <: PipelineQuery with HasSeenTweetIds] @Inject() (
  bloomFilterClient: ManhattanStoreClient[
    blm.ImpressionBloomFilterKey,
    blm.ImpressionBloomFilterSeq
  ]) etwittertends QueryFeatureHydrator[Query] {

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
    HomeMitwittererAlertConfig.BusinessHours.defaultSuccessRateAlert(99.8)
  )
}
