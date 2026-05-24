package com.twitter.home_mixer.functional_component.feature_hydrator

import com.twitter.finagle.stats.StatsReceiver
import com.twitter.home_mixer.functional_component.feature_hydrator.adapters.twhin_embeddings.TwhinRebuildTweetEmbeddingsAdapter
import com.twitter.home_mixer.param.HomeMixerInjectionNames.TwhinRebuildTweetEmbeddingsStore
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
import com.twitter.simclusters_v2.thriftscala.TwhinEmbeddingDataset
import com.twitter.stitch.Stitch
import com.twitter.storehaus.ReadableStore
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton
import scala.collection.JavaConverters._

object TwhinRebuildTweetFeature
    extends DataRecordInAFeature[TweetCandidate]
    with FeatureWithDefaultOnFailure[TweetCandidate, DataRecord] {
  override def defaultValue: DataRecord = new DataRecord()
}

@Singleton
class TwhinRebuildTweetFeatureHydrator @Inject() (
  homeMixerFeatureService: hmf.HomeMixerFeatures.MethodPerEndpoint,
  @Named(TwhinRebuildTweetEmbeddingsStore) store: ReadableStore[(Long, Long), TwhinTweetEmbedding],
  statsReceiver: StatsReceiver)
    extends BulkCandidateFeatureHydrator[PipelineQuery, TweetCandidate] {

  override val identifier: FeatureHydratorIdentifier = FeatureHydratorIdentifier(
    "TwhinRebuildTweet")

  override val features: Set[Feature[_, _]] = Set(TwhinRebuildTweetFeature)

  private val scopedStatsReceiver = statsReceiver.scope(getClass.getSimpleName)
  private val keyFoundCounter = scopedStatsReceiver.counter("key/found")
  private val keyTotalCounter = scopedStatsReceiver.counter("key/total")

  private val batchSize = 50

  private val versionId = TwhinEmbeddingDataset.RefreshedTwhinTweet.value.toLong

  private def getTwhinEmbeddingsFromHMF(
    originalTweetIds: Seq[Long]
  ): Future[Seq[Option[TwhinTweetEmbedding]]] = {
    val keysSerialized = originalTweetIds.map(_.toString)
    val request = hmf.HomeMixerFeaturesRequest(keysSerialized, hmf.Cache.TwhinRebuild)
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
    val tweetIdVersionIdPairs = originalTweetIds.map(tweetId => (tweetId, versionId))
    Future.collect(store.multiGet(tweetIdVersionIdPairs.toSet)).map { storeResponse =>
      tweetIdVersionIdPairs.map {
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
        {
          keyTotalCounter.incr()
          CandidatesUtil.getOriginalTweetId(candidate.candidate, candidate.features)
        }
      }

    val responseMap =
      if (callMiddleMan) getTwhinEmbeddingsFromHMF(originalTweetIds)
      else getTwhinEmbeddingsFromReadableStore(originalTweetIds)

    responseMap.map { response =>
      response.map { twhinEmbeddingOpt =>
        val floatTensor = {
          keyFoundCounter.incr()
          twhinEmbeddingOpt.map(twhinEmbedding => ml.FloatTensor(twhinEmbedding.embedding))
        }
        val dataRecord =
          TwhinRebuildTweetEmbeddingsAdapter.adaptToDataRecords(floatTensor).asScala.head
        FeatureMap(TwhinRebuildTweetFeature, dataRecord)
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
