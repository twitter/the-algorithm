package com.twitter.home_mixer.functional_component.feature_hydrator

import com.twitter.finagle.stats.StatsReceiver
import com.twitter.home_mixer.functional_component.feature_hydrator.adapters.transformer_embeddings.PostTransformerEmbeddingsHomeBlueAdapter
import com.twitter.home_mixer.functional_component.feature_hydrator.adapters.transformer_embeddings.PostTransformerEmbeddingsHomeGreenAdapter
import com.twitter.home_mixer.functional_component.feature_hydrator.adapters.transformer_embeddings.PostTransformerEmbeddingsJointBlueAdapter
import com.twitter.home_mixer_features.{thriftscala => hmf}
import com.twitter.ml.api.DataRecord
import com.twitter.ml.api.{thriftscala => ml}
import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.FeatureWithDefaultOnFailure
import com.twitter.product_mixer.core.feature.datarecord.DataRecordInAFeature
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.functional_component.feature_hydrator.BulkCandidateFeatureHydrator
import com.twitter.product_mixer.core.model.common.CandidateWithFeatures
import com.twitter.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.product_mixer.core.util.OffloadFuturePools
import com.twitter.stitch.Stitch
import com.twitter.timelines.prediction.common.adapters.TimelinesMutatingAdapterBase
import com.twitter.util.Future
import javax.inject.Inject
import javax.inject.Singleton
import scala.collection.JavaConverters._

object TransformerPostEmbeddingHomeBlueFeature
    extends DataRecordInAFeature[TweetCandidate]
    with FeatureWithDefaultOnFailure[TweetCandidate, DataRecord] {
  override def defaultValue: DataRecord = new DataRecord()
}

object TransformerPostEmbeddingHomeGreenFeature
    extends DataRecordInAFeature[TweetCandidate]
    with FeatureWithDefaultOnFailure[TweetCandidate, DataRecord] {
  override def defaultValue: DataRecord = new DataRecord()
}

object TransformerPostEmbeddingJointBlueFeature
    extends DataRecordInAFeature[TweetCandidate]
    with FeatureWithDefaultOnFailure[TweetCandidate, DataRecord] {
  override def defaultValue: DataRecord = new DataRecord()
}

@Singleton
class TransformerPostEmbeddingHomeBlueFeatureHydrator @Inject() (
  homeMixerFeatureService: hmf.HomeMixerFeatures.MethodPerEndpoint,
  statsReceiver: StatsReceiver)
    extends TransformerPostEmbeddingFeatureHydrator(
      homeMixerFeatureService,
      statsReceiver,
      hmf.Cache.TransformerPostEmbeddings,
      TransformerPostEmbeddingHomeBlueFeature,
      PostTransformerEmbeddingsHomeBlueAdapter
    ) {
  override val identifier: FeatureHydratorIdentifier = FeatureHydratorIdentifier(
    "TransformerPostEmbeddingBlue")
}

@Singleton
class TransformerPostEmbeddingHomeGreenFeatureHydrator @Inject() (
  homeMixerFeatureService: hmf.HomeMixerFeatures.MethodPerEndpoint,
  statsReceiver: StatsReceiver)
    extends TransformerPostEmbeddingFeatureHydrator(
      homeMixerFeatureService,
      statsReceiver,
      hmf.Cache.TransformerPostEmbeddingsGreen,
      TransformerPostEmbeddingHomeGreenFeature,
      PostTransformerEmbeddingsHomeGreenAdapter
    ) {
  override val identifier: FeatureHydratorIdentifier = FeatureHydratorIdentifier(
    "TransformerPostEmbeddingGreen")
}

@Singleton
class TransformerPostEmbeddingJointBlueFeatureHydrator @Inject() (
  homeMixerFeatureService: hmf.HomeMixerFeatures.MethodPerEndpoint,
  statsReceiver: StatsReceiver)
    extends TransformerPostEmbeddingFeatureHydrator(
      homeMixerFeatureService,
      statsReceiver,
      hmf.Cache.TransformerPostJointEmbeddingsBlue,
      TransformerPostEmbeddingJointBlueFeature,
      PostTransformerEmbeddingsJointBlueAdapter
    ) {
  override val identifier: FeatureHydratorIdentifier = FeatureHydratorIdentifier(
    "TransformerPostEmbeddingGreen")
}

abstract class TransformerPostEmbeddingFeatureHydrator(
  homeMixerFeatureService: hmf.HomeMixerFeatures.MethodPerEndpoint,
  statsReceiver: StatsReceiver,
  cache: hmf.Cache,
  feature: DataRecordInAFeature[TweetCandidate],
  dataRecordAdapter: TimelinesMutatingAdapterBase[Option[ml.FloatTensor]])
    extends BulkCandidateFeatureHydrator[PipelineQuery, TweetCandidate] {

  override val features: Set[Feature[_, _]] = Set(feature)

  private val scopedStatsReceiver = statsReceiver.scope(getClass.getSimpleName)
  private val keyFoundCounter = scopedStatsReceiver.counter("key/found")
  private val keyTotalCounter = scopedStatsReceiver.counter("key/total")

  private val batchSize = 50

  private def getEmbeddingsFromHMF(
    tweetIds: Seq[Long]
  ): Future[Seq[Option[Seq[Double]]]] = {
    val keysSerialized = tweetIds.map(_.toString)
    val request = hmf.HomeMixerFeaturesRequest(keysSerialized, cache)
    val responseFut =
      homeMixerFeatureService.getHomeMixerFeatures(request)
    responseFut
      .map { response =>
        response.homeMixerFeatures
          .map { homeMixerFeatureOpt =>
            homeMixerFeatureOpt.homeMixerFeaturesType.map {
              case hmf.HomeMixerFeaturesType.RawEmbedding(rawEmbedding) =>
                rawEmbedding
              case _ => throw new Exception("Unknown type returned")
            }
          }
      }.handle { case _ => Seq.fill(tweetIds.size)(None) }
  }

  private def getBatchedFeatureMap(
    candidatesBatch: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Future[Seq[FeatureMap]] = {
    val tweetIds =
      candidatesBatch.map { candidate =>
        keyTotalCounter.incr()
        candidate.candidate.id
      }

    getEmbeddingsFromHMF(tweetIds).map { response =>
      response.map { embeddingOpt =>
        val floatTensor =
          embeddingOpt.map { embedding =>
            keyFoundCounter.incr()
            ml.FloatTensor(embedding)
          }
        val dataRecord =
          dataRecordAdapter.adaptToDataRecords(floatTensor).asScala.head
        FeatureMap(feature, dataRecord)
      }
    }
  }

  override def apply(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Stitch[Seq[FeatureMap]] = OffloadFuturePools.offloadFuture {
    OffloadFuturePools.offloadBatchSeqToFutureSeq(candidates, getBatchedFeatureMap, batchSize)
  }
}
