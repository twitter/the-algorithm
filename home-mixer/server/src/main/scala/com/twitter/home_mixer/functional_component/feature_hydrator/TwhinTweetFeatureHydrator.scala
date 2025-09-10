package com.twitter.home_mixer.functional_component.feature_hydrator

import com.twitter.home_mixer.functional_component.feature_hydrator.adapters.twhin_embeddings.TwhinTweetEmbeddingsAdapter
import com.twitter.home_mixer.param.HomeMixerInjectionNames.TwhinTweetEmbeddingsStore
import com.twitter.home_mixer.product.scored_tweets.param.ScoredTweetsParam.EnableHomeMixerFeaturesService
import com.twitter.home_mixer.util.CandidatesUtil
import com.twitter.home_mixer_features.{thriftscala => hmf}
import com.twitter.ml.api.DataRecord
import com.twitter.product_mixer.core.functional_component.feature_hydrator.BulkCandidateFeatureHydrator
import com.twitter.product_mixer.core.model.common.CandidateWithFeatures
import com.twitter.util.Future
import com.twitter.ml.api.{thriftscala => ml}
import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.FeatureWithDefaultOnFailure
import com.twitter.product_mixer.core.feature.datarecord.DataRecordInAFeature
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.product_mixer.core.util.OffloadFuturePools
import com.twitter.simclusters_v2.thriftscala.TwhinTweetEmbedding
import com.twitter.stitch.Stitch
import com.twitter.storehaus.ReadableStore
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton
import scala.collection.JavaConverters._

object TwhinTweetFeature
    extends DataRecordInAFeature[TweetCandidate]
    with FeatureWithDefaultOnFailure[TweetCandidate, DataRecord] {
  override def defaultValue: DataRecord = new DataRecord()
}

@Singleton
class TwhinTweetFeatureHydrator @Inject() (
  homeMixerFeatureService: hmf.HomeMixerFeatures.MethodPerEndpoint,
  @Named(TwhinTweetEmbeddingsStore) store: ReadableStore[Long, TwhinTweetEmbedding])
    extends BulkCandidateFeatureHydrator[PipelineQuery, TweetCandidate] {

  override val identifier: FeatureHydratorIdentifier = FeatureHydratorIdentifier("TwhinTweet")

  override val features: Set[Feature[_, _]] = Set(TwhinTweetFeature)

  private val batchSize = 50

  private def getTwhinEmbeddingsFromHMF(
    originalTweetIds: Seq[Long]
  ): Future[Seq[Option[TwhinTweetEmbedding]]] = {
    val keysSerialized = originalTweetIds.map(_.toString)
    val request = hmf.HomeMixerFeaturesRequest(keysSerialized, hmf.Cache.Twhin)
    val responseFut =
      homeMixerFeatureService.getHomeMixerFeatures(request)
    responseFut
      .map { response =>
        response.homeMixerFeatures
          .map { homeMixerFeatureOpt =>
            homeMixerFeatureOpt.homeMixerFeaturesType.map {
              case hmf.HomeMixerFeaturesType.TwhinTweetEmbedding(homeMixerFeature) =>
                homeMixerFeature
              case _ => throw new Exception("Unknown type returned")
            }
          }
      }.handle { case _ => Seq.fill(originalTweetIds.size)(None) }
  }

  private def getTwhinEmbeddingsFromReadableStore(
    originalTweetIds: Seq[Long]
  ): Future[Seq[Option[TwhinTweetEmbedding]]] = {
    Future.collect(store.multiGet(originalTweetIds.toSet)).map { storeResponse =>
      originalTweetIds.map {
        storeResponse.getOrElse(_, None)
      }
    }
  }

  private def getBatchedFeatureMap(
    candidatesBatch: Seq[CandidateWithFeatures[TweetCandidate]],
    callMiddleMan: Boolean
  ): Future[Seq[FeatureMap]] = {
    val originalTweetIds =
      candidatesBatch.map { candidate =>
        CandidatesUtil.getOriginalTweetId(candidate.candidate, candidate.features)
      }

    val responseMap =
      if (callMiddleMan) getTwhinEmbeddingsFromHMF(originalTweetIds)
      else getTwhinEmbeddingsFromReadableStore(originalTweetIds)

    responseMap.map { response =>
      response.map { twhinEmbeddingOpt =>
        val floatTensor =
          twhinEmbeddingOpt.map(twhinEmbedding => ml.FloatTensor(twhinEmbedding.embedding))
        val dataRecord =
          TwhinTweetEmbeddingsAdapter.adaptToDataRecords(floatTensor).asScala.head
        FeatureMap(TwhinTweetFeature, dataRecord)
      }
    }
  }

  override def apply(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Stitch[Seq[FeatureMap]] = OffloadFuturePools.offloadFuture {
    val callMiddleMan = query.params(EnableHomeMixerFeaturesService)
    OffloadFuturePools.offloadBatchSeqToFutureSeq(
      candidates,
      getBatchedFeatureMap(_, callMiddleMan),
      batchSize)
  }
}
