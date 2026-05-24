package com.twitter.home_mixer.functional_component.feature_hydrator

import com.twitter.finagle.stats.StatsReceiver
import com.twitter.home_mixer.functional_component.feature_hydrator.adapters.content.VideoSummaryEmbeddingFeaturesAdaptor
import com.twitter.home_mixer.model.HomeFeatures.TweetMediaIdsFeature
import com.twitter.home_mixer.param.HomeGlobalParams.FeatureHydration.EnableVideoSummaryEmbeddingFeatureDeciderParam
import com.twitter.home_mixer.param.HomeMixerInjectionNames.VideoEmbeddingMHStore
import com.twitter.ml.api.DataRecord
import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.FeatureWithDefaultOnFailure
import com.twitter.product_mixer.core.feature.datarecord.DataRecordInAFeature
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mixer.core.functional_component.feature_hydrator.CandidateFeatureHydrator
import com.twitter.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch
import com.twitter.storehaus.ReadableStore
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton
import com.twitter.media_understanding.video_summary.thriftscala.VideoEmbedding
import com.twitter.product_mixer.core.model.common.Conditionally
import com.twitter.util.logging.Logging
import scala.collection.JavaConverters._

object VideoSummaryEmbeddingFeature
    extends DataRecordInAFeature[TweetCandidate]
    with FeatureWithDefaultOnFailure[TweetCandidate, DataRecord] {
  override def defaultValue: DataRecord = new DataRecord()
}

@Singleton
class VideoSummaryEmbeddingFeatureHydrator @Inject() (
  @Named(VideoEmbeddingMHStore) videoEmbeddingStore: ReadableStore[Long, VideoEmbedding],
  statsReceiver: StatsReceiver)
    extends CandidateFeatureHydrator[PipelineQuery, TweetCandidate]
    with Conditionally[PipelineQuery]
    with Logging {
  override val identifier: FeatureHydratorIdentifier = FeatureHydratorIdentifier(
    "VideoSummaryEmbedding")

  override val features: Set[Feature[_, _]] = Set(VideoSummaryEmbeddingFeature)

  private val DefaultFeatureMap =
    FeatureMapBuilder().add(VideoSummaryEmbeddingFeature, new DataRecord()).build()

  private val scopedCounter = statsReceiver.scope("VideoSummaryEmbeddingHydration")
  private val nonEmptyCounter = scopedCounter.counter("nonEmpty")
  private val emptyCounter = scopedCounter.counter("empty")

  override def onlyIf(query: PipelineQuery): Boolean =
    query.params(EnableVideoSummaryEmbeddingFeatureDeciderParam)

  override def apply(
    query: PipelineQuery,
    candidate: TweetCandidate,
    existingFeatures: FeatureMap
  ): Stitch[FeatureMap] = {
    getFirstMediaId(existingFeatures).fold(Stitch.value(DefaultFeatureMap)) { mediaId =>
      Stitch
        .callFuture(videoEmbeddingStore.get(mediaId)).map { resultOpt =>
          resultOpt match {
            case Some(embedding) =>
              val dataRecord = VideoSummaryEmbeddingFeaturesAdaptor
                .adaptToDataRecords(embedding.embedding).asScala.head
              nonEmptyCounter.incr()
              FeatureMapBuilder().add(VideoSummaryEmbeddingFeature, dataRecord).build()
            case None =>
              emptyCounter.incr()
              DefaultFeatureMap
          }
        }.onFailure { e =>
          error(s"Error fetching VideoSummaryEmbedding: $e")
          Stitch.value(DefaultFeatureMap)
        }
    }
  }

  private def getFirstMediaId(featureMap: FeatureMap): Option[Long] =
    featureMap.getOrElse(TweetMediaIdsFeature, Seq.empty[Long]).headOption
}
