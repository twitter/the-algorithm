package com.twitter.home_mixer.functional_component.feature_hydrator

import com.twitter.finagle.stats.StatsReceiver
import com.twitter.home_mixer.model.HomeFeatures.TweetLanguageFromLanguageSignalFeature
import com.twitter.home_mixer.util.CandidatesUtil
import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch
import com.twitter.strato.generated.client.language.tweet.LanguageOnTweetClientColumn
import com.twitter.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mixer.core.functional_component.feature_hydrator.BulkCandidateFeatureHydrator
import com.twitter.product_mixer.core.model.common.CandidateWithFeatures
import com.twitter.product_mixer.core.util.OffloadFuturePools
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TweetLanguageFeatureHydrator @Inject() (
  languageOnTweetClientColumn: LanguageOnTweetClientColumn,
  statsReceiver: StatsReceiver)
    extends BulkCandidateFeatureHydrator[PipelineQuery, TweetCandidate] {

  override val identifier: FeatureHydratorIdentifier =
    FeatureHydratorIdentifier("TweetLanguage")

  private val scopedStatsReceiver = statsReceiver.scope(getClass.getSimpleName)
  private val failedCounter = scopedStatsReceiver.scope(getClass.getSimpleName).counter("failure")

  private val DefaultFeatureMap =
    FeatureMapBuilder().add(TweetLanguageFromLanguageSignalFeature, None).build()

  override def features: Set[Feature[_, _]] = Set(TweetLanguageFromLanguageSignalFeature)

  override def apply(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Stitch[Seq[FeatureMap]] = {
    OffloadFuturePools.offloadStitch {
      Stitch.collect {
        candidates.map { candidate =>
          languageOnTweetClientColumn.fetcher
            .fetch(
              CandidatesUtil.getOriginalTweetId(candidate),
              LanguageOnTweetClientColumn
                .View(true)).map { result =>
              FeatureMapBuilder()
                .add(TweetLanguageFromLanguageSignalFeature, result.v)
                .build()
            }.rescue {
              case _ =>
                failedCounter.incr()
                Stitch.value(DefaultFeatureMap)
            }
        }
      }
    }
  }
}
