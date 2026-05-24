package com.twitter.home_mixer.product.scored_tweets.feature_hydrator

import com.twitter.finagle.stats.StatsReceiver
import com.twitter.home_mixer.model.HomeFeatures.IsColdStartPostFeature
import com.twitter.home_mixer.param.HomeMixerInjectionNames.IsColdStartPostInMemCache
import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.functional_component.feature_hydrator.CandidateFeatureHydrator
import com.twitter.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.servo.cache.InProcessCache
import com.twitter.strato.generated.client.content_understanding.ColdStartPostsMetadataMhClientColumn
import com.twitter.stitch.Stitch
import com.twitter.strato.columns.content_understanding.content_exploration.thriftscala.ColdStartPostStatus
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class IsColdStartPostFeatureHydrator @Inject() (
  coldStartPostsMetadataMhClientColumn: ColdStartPostsMetadataMhClientColumn,
  @Named(IsColdStartPostInMemCache) isColdStartPostInMemCache: InProcessCache[
    Long,
    Boolean
  ],
  statsReceiver: StatsReceiver)
    extends CandidateFeatureHydrator[PipelineQuery, TweetCandidate] {

  override val identifier: FeatureHydratorIdentifier =
    FeatureHydratorIdentifier("IsColdStartPost")

  override val features: Set[Feature[_, _]] = Set(IsColdStartPostFeature)

  private val DefaultFeatureMap = FeatureMap(IsColdStartPostFeature, false)

  private val ineligibleStatusSet: Set[ColdStartPostStatus] =
    Set(ColdStartPostStatus.Tier1Ineligible, ColdStartPostStatus.Tier1IneligibleHighQuality)

  private val scopedStatsReceiver = statsReceiver.scope(getClass.getSimpleName)
  private val cacheHitCounter = scopedStatsReceiver.counter("cache/hit")
  private val cacheMissCounter = scopedStatsReceiver.counter("cache/miss")
  private val storeHitCounter = scopedStatsReceiver.counter("store/hit")
  private val storeMissCounter = scopedStatsReceiver.counter("store/miss")
  private val fetchExceptionCounter = scopedStatsReceiver.counter("fetch/exception")

  override def apply(
    query: PipelineQuery,
    candidate: TweetCandidate,
    existingFeatures: FeatureMap
  ): Stitch[FeatureMap] = {
    val postId = candidate.id
    isColdStartPostInMemCache
      .get(postId)
      .map { cachedValue =>
        cacheHitCounter.incr()
        Stitch.value(FeatureMap(IsColdStartPostFeature, cachedValue))
      }.getOrElse {
        cacheMissCounter.incr()
        coldStartPostsMetadataMhClientColumn.fetcher
          .fetch(postId)
          .map { response =>
            if (response.v.isDefined) {
              storeHitCounter.incr()
            } else {
              storeMissCounter.incr()
            }
            val isColdStartPost = response.v.flatMap(_.status) match {
              case Some(status) => !ineligibleStatusSet.contains(status)
              case _ => false
            }
            isColdStartPostInMemCache.set(postId, isColdStartPost)
            FeatureMap(IsColdStartPostFeature, isColdStartPost)
          }.handle {
            case _: Exception =>
              fetchExceptionCounter.incr()
              DefaultFeatureMap
          }
      }
  }

}
