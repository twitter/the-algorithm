package com.twitter.home_mixer.functional_component.feature_hydrator

import com.twitter.finagle.stats.StatsReceiver
import com.twitter.home_mixer.model.HomeFeatures.GrokTranslatedPostIsCachedFeature
import com.twitter.home_mixer.product.scored_tweets.param.ScoredTweetsParam.EnableGrokAutoTranslateLanguageFilter
import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mixer.core.functional_component.feature_hydrator.CandidateFeatureHydrator
import com.twitter.product_mixer.core.model.common.Conditionally
import com.twitter.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch
import com.twitter.strato.client.Fetcher
import com.twitter.strato.generated.client.searchai.grok.TranslatedPostClientColumn
import com.twitter.search_router.thriftscala.GrokTranslateData
import com.twitter.util.logging.Logging

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GrokTranslatedPostIsCachedFeatureHydrator @Inject() (
  translatedPostClientColumn: TranslatedPostClientColumn,
  statsReceiver: StatsReceiver)
    extends CandidateFeatureHydrator[PipelineQuery, TweetCandidate]
    with Conditionally[PipelineQuery]
    with Logging {

  override val identifier: FeatureHydratorIdentifier =
    FeatureHydratorIdentifier("GrokTranslatedPostIsCached")

  private val scopedStatsReceiver = statsReceiver.scope(getClass.getSimpleName)
  private val successCounter = scopedStatsReceiver.counter("cacheFetch/success")
  private val failedCounter = scopedStatsReceiver.counter("cacheFetch/failure")
  private val cacheHitCounter = scopedStatsReceiver.counter("cacheHit")
  private val cacheMissCounter = scopedStatsReceiver.counter("cacheMiss")
  private val DefaultFeatureMap =
    FeatureMapBuilder().add(GrokTranslatedPostIsCachedFeature, true).build()

  private val fetcher: Fetcher[(Long, String), Unit, GrokTranslateData] =
    translatedPostClientColumn.fetcher

  override val features: Set[Feature[_, _]] = Set(GrokTranslatedPostIsCachedFeature)

  override def onlyIf(query: PipelineQuery): Boolean =
    query.params(EnableGrokAutoTranslateLanguageFilter)

  override def apply(
    query: PipelineQuery,
    candidate: TweetCandidate,
    existingFeatures: FeatureMap
  ): Stitch[FeatureMap] = {
    val tweetId = candidate.id
    val dstLangOpt = query.getLanguageCode
    dstLangOpt match {
      case Some(dstLang) =>
        fetcher
          .fetch((tweetId, dstLang), ()).map { result =>
            successCounter.incr()
            val cacheAvailable = result.v.isDefined
            if (cacheAvailable) cacheHitCounter.incr()
            else cacheMissCounter.incr()
            FeatureMapBuilder()
              .add(GrokTranslatedPostIsCachedFeature, cacheAvailable)
              .build()
          }.rescue {
            case _ =>
              failedCounter.incr()
              Stitch.value(DefaultFeatureMap)
          }
      case None => Stitch.value(DefaultFeatureMap)
    }
  }
}
