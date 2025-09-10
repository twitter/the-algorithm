package com.twitter.home_mixer.functional_component.feature_hydrator

import com.twitter.finagle.stats.StatsReceiver
import com.twitter.home_mixer.model.HomeFeatures.UserUnderstandableLanguagesFeature
import com.twitter.home_mixer.product.scored_tweets.param.ScoredTweetsParam.EnableGrokAutoTranslateLanguageFilter
import com.twitter.home_mixer.util.ObservedKeyValueResultHandler
import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.functional_component.feature_hydrator.QueryFeatureHydrator
import com.twitter.product_mixer.core.model.common.Conditionally
import com.twitter.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch
import com.twitter.strato.client.Fetcher
import com.twitter.strato.generated.client.language.user.GrokAutoTranslateUnderstandableLanguagesOnUserClientColumn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserUnderstandableLanguagesFeatureHydrator @Inject() (
  understandableLanguagesClientColumn: GrokAutoTranslateUnderstandableLanguagesOnUserClientColumn,
  override val statsReceiver: StatsReceiver)
    extends QueryFeatureHydrator[PipelineQuery]
    with Conditionally[PipelineQuery]
    with ObservedKeyValueResultHandler {

  override val identifier: FeatureHydratorIdentifier =
    FeatureHydratorIdentifier("UserUnderstandableLanguages")

  override val features: Set[Feature[_, _]] = Set(UserUnderstandableLanguagesFeature)

  override val statScope: String = identifier.toString
  private val scopedStatsReceiver = statsReceiver.scope(getClass.getSimpleName)
  private val successCounter = scopedStatsReceiver.counter("success")
  private val failedCounter = scopedStatsReceiver.counter("failure")

  private val DefaultFeatureMap = FeatureMap(UserUnderstandableLanguagesFeature, Seq.empty)

  private val fetcher: Fetcher[Long, Unit, Seq[String]] =
    understandableLanguagesClientColumn.fetcher

  override def onlyIf(query: PipelineQuery): Boolean =
    query.params(EnableGrokAutoTranslateLanguageFilter)

  override def hydrate(query: PipelineQuery): Stitch[FeatureMap] = {
    val key = query.getRequiredUserId
    fetcher
      .fetch(key, ()).map { result =>
        successCounter.incr()
        FeatureMap(UserUnderstandableLanguagesFeature, result.v.getOrElse(Seq.empty))
      }.rescue {
        case _ =>
          failedCounter.incr()
          Stitch.value(DefaultFeatureMap)
      }
  }
}
