package com.twitter.home_mixer.functional_component.feature_hydrator

import com.twitter.finagle.stats.StatsReceiver
import com.twitter.home_mixer.functional_component.feature_hydrator.adapters.content.ClipEmbeddingFeaturesAdapter
import com.twitter.home_mixer.model.HomeFeatures.MediaCategoryFeature
import com.twitter.home_mixer.model.HomeFeatures.MediaIdFeature
import com.twitter.home_mixer.param.HomeGlobalParams.FeatureHydration.EnableClipEmbeddingMediaUnderstandingFeaturesParam
import com.twitter.home_mixer.param.HomeGlobalParams.FeatureHydration.EnableVideoClipEmbeddingMediaUnderstandingFeatureHydrationDeciderParam
import com.twitter.media_understanding.embeddings.thriftscala.MediaEmbedding
import com.twitter.media_understanding.embeddings.thriftscala.MediaEmbeddingInfo
import com.twitter.ml.api.DataRecord
import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.FeatureWithDefaultOnFailure
import com.twitter.product_mixer.core.feature.datarecord.DataRecordInAFeature
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mixer.core.functional_component.feature_hydrator.CandidateFeatureHydrator
import com.twitter.product_mixer.core.model.common.Conditionally
import com.twitter.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch
import com.twitter.strato.client.Client
import com.twitter.strato.client.Fetcher
import com.twitter.util.logging.Logging
import javax.inject.Inject
import javax.inject.Singleton
import scala.collection.JavaConverters._

object ClipEmbeddingFeature
    extends DataRecordInAFeature[TweetCandidate]
    with FeatureWithDefaultOnFailure[TweetCandidate, DataRecord] {
  override def defaultValue: DataRecord = new DataRecord()
}

@Singleton
class ClipEmbeddingMediaUnderstandingFeatureHydrator @Inject() (
  stratoClient: Client,
  statsReceiver: StatsReceiver)
    extends CandidateFeatureHydrator[PipelineQuery, TweetCandidate]
    with Conditionally[PipelineQuery]
    with Logging {

  override def onlyIf(query: PipelineQuery): Boolean =
    query.params(EnableClipEmbeddingMediaUnderstandingFeaturesParam) &&
      query.params(EnableVideoClipEmbeddingMediaUnderstandingFeatureHydrationDeciderParam)

  override val identifier: FeatureHydratorIdentifier =
    FeatureHydratorIdentifier("ClipEmbeddingMediaUnderstanding")

  val MediaModelName: String = "twitter_clip_256"
  val MediaVersion: String = "0"

  val mediaEmbeddingFetcher: Fetcher[(String, (String, String)), Unit, MediaEmbeddingInfo] =
    stratoClient.fetcher[(String, (String, String)), Unit, MediaEmbeddingInfo](
      "media-understanding/embeddings/prod/embeddings"
    )

  def getTweetMediaEmbeddings(key: String): Stitch[Option[Seq[Double]]] = {
    mediaEmbeddingFetchCounter.incr()
    mediaEmbeddingFetcher
      .fetch((key, (MediaModelName, MediaVersion)))
      .map { result =>
        result.v match {
          case Some(mediaData) =>
            mediaData.embedding match {
              case Some(MediaEmbedding.DoubleVector(doubles)) =>
                mediaEmbeddingFetchSuccessCounter.incr()
                Some(doubles.map(_.toDouble))
              case _ =>
                mediaEmbeddingFetchFailureCounter.incr()
                None
            }
          case None =>
            mediaEmbeddingFetchFailureCounter.incr()
            None
        }
      }
      .rescue {
        case e: Exception =>
          mediaEmbeddingFetchFailureCounter.incr()
          Stitch.None
      }
  }

  def getClipEmbeddings(
    tweetId: Long,
    existingFeatures: FeatureMap
  ): Stitch[Option[Seq[Double]]] = {
    val mediaId = existingFeatures.getOrElse(MediaIdFeature, None)
    val mediaCategory = existingFeatures.getOrElse(MediaCategoryFeature, None)

    (mediaId, mediaCategory) match {
      case (Some(id), Some(category)) =>
        val key = s"${category.getValue}/$id"
        getTweetMediaEmbeddings(key).map {
          case Some(embeddings) => Some(embeddings)
          case _ =>
            clipEmbeddingNotFoundNoneCounter.incr()
            None
        }
      case _ => Stitch.value(None)
    }
  }

  override val features: Set[Feature[_, _]] = Set(ClipEmbeddingFeature)

  private val DefaultFeatureMap =
    FeatureMapBuilder().add(ClipEmbeddingFeature, new DataRecord()).build()

  private val scopedStatsReceiver: StatsReceiver = statsReceiver.scope(getClass.getSimpleName)
  private val clipEmbeddingNotFoundNoneCounter =
    scopedStatsReceiver.counter("clipEmbeddingNotFoundNoneCounter")
  private val clipEmbeddingNotFoundCounter = scopedStatsReceiver.counter("clipEmbeddingNotFound")
  private val clipEmbeddingFoundCounter = scopedStatsReceiver.counter("clipEmbeddingFound")

  val mediaEmbeddingFetchCounter = statsReceiver.counter("media_embedding_fetch_total")
  val mediaEmbeddingFetchSuccessCounter = statsReceiver.counter("media_embedding_fetch_success")
  val mediaEmbeddingFetchFailureCounter = statsReceiver.counter("media_embedding_fetch_failure")

  override def apply(
    query: PipelineQuery,
    candidate: TweetCandidate,
    existingFeatures: FeatureMap
  ): Stitch[FeatureMap] = {
    getClipEmbeddings(candidate.id, existingFeatures)
      .map {
        case Some(embeddings) =>
          clipEmbeddingFoundCounter.incr()
          val dataRecord =
            ClipEmbeddingFeaturesAdapter.adaptToDataRecords(embeddings).asScala.head
          FeatureMapBuilder().add(ClipEmbeddingFeature, dataRecord).build()
        case None =>
          clipEmbeddingNotFoundCounter.incr()
          DefaultFeatureMap
      }.onFailure {
        case e: Exception => error(s"Error fetching VideoClipEmbedding: $e")
      }
  }
}
