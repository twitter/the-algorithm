package com.twitter.home_mixer.functional_component.feature_hydrator

import com.twitter.finagle.stats.StatsReceiver
import com.twitter.home_mixer.functional_component.feature_hydrator.adapters.content.ClipEmbeddingFeaturesAdapter
import com.twitter.home_mixer.param.HomeGlobalParams.FeatureHydration.EnableClipEmbeddingFeaturesParam
import com.twitter.home_mixer.param.HomeGlobalParams.FeatureHydration.EnableVideoClipEmbeddingFeatureHydrationDeciderParam
import com.twitter.ml.api.DataRecord
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
import com.twitter.strato.generated.client.videoRecommendations.twitterClip.TwitterClipEmbeddingMhClientColumn
import com.twitter.util.logging.Logging

import javax.inject.Inject
import javax.inject.Singleton
import scala.collection.JavaConverters._

@Singleton
class ClipEmbeddingFeatureHydrator @Inject() (
  twitterClipEmbeddingMhClientColumn: TwitterClipEmbeddingMhClientColumn,
  statsReceiver: StatsReceiver)
    extends CandidateFeatureHydrator[PipelineQuery, TweetCandidate]
    with Conditionally[PipelineQuery]
    with Logging {

  override def onlyIf(query: PipelineQuery): Boolean =
    query.params(EnableClipEmbeddingFeaturesParam) &&
      query.params(EnableVideoClipEmbeddingFeatureHydrationDeciderParam)

  override val identifier: FeatureHydratorIdentifier = FeatureHydratorIdentifier("ClipEmbedding")

  private val fetcher: Fetcher[Long, Unit, Seq[Double]] =
    twitterClipEmbeddingMhClientColumn.fetcher

  override val features: Set[Feature[_, _]] = Set(ClipEmbeddingFeature)

  private val DefaultFeatureMap =
    FeatureMapBuilder().add(ClipEmbeddingFeature, new DataRecord()).build()

  private val scopedStatsReceiver: StatsReceiver = statsReceiver.scope(getClass.getSimpleName)
  private val clipEmbeddingNotFoundCounter = scopedStatsReceiver.counter("clipEmbeddingNotFound")
  private val clipEmbeddingFoundCounter = scopedStatsReceiver.counter("clipEmbeddingFound")

  override def apply(
    query: PipelineQuery,
    candidate: TweetCandidate,
    existingFeatures: FeatureMap
  ): Stitch[FeatureMap] = {
    fetcher
      .fetch(candidate.id).map { manhattanResult =>
        manhattanResult.v match {
          case Some(embeddings) =>
            clipEmbeddingFoundCounter.incr()
            val dataRecord =
              ClipEmbeddingFeaturesAdapter.adaptToDataRecords(embeddings).asScala.head
            FeatureMapBuilder().add(ClipEmbeddingFeature, dataRecord).build()
          case None =>
            clipEmbeddingNotFoundCounter.incr()
            DefaultFeatureMap
        }
      }.onFailure(e => {
        error(s"Error fetching VideoClipEmbedding: $e")
      })
  }
}
