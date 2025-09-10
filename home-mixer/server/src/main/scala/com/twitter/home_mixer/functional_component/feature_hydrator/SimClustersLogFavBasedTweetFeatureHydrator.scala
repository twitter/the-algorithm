package com.twitter.home_mixer.functional_component.feature_hydrator

import com.twitter.finagle.stats.StatsReceiver
import com.twitter.home_mixer.functional_component.feature_hydrator.DiversityRescoringFeatureHydrator.EmptyDataRecord
import com.twitter.home_mixer_features.thriftjava.HomeMixerFeaturesRequest
import com.twitter.home_mixer_features.{thriftjava => t}
import com.twitter.ml.api.DataRecord
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
import com.twitter.util.Future
import javax.inject.Inject
import javax.inject.Singleton
import scala.collection.JavaConverters._

object SimClustersLogFavBasedTweetFeature
    extends DataRecordInAFeature[TweetCandidate]
    with FeatureWithDefaultOnFailure[TweetCandidate, DataRecord] {
  override def defaultValue: DataRecord = new DataRecord()
}

@Singleton
class SimClustersLogFavBasedTweetFeatureHydrator @Inject() (
  homeMixerFeatureService: t.HomeMixerFeatures.ServiceToClient,
  statsReceiver: StatsReceiver)
    extends BulkCandidateFeatureHydrator[PipelineQuery, TweetCandidate] {

  override val identifier: FeatureHydratorIdentifier = FeatureHydratorIdentifier(
    "SimClustersLogFavBasedTweet")

  override val features: Set[Feature[_, _]] = Set(SimClustersLogFavBasedTweetFeature)

  private val scopedStatsReceiver = statsReceiver.scope(getClass.getSimpleName)
  private val keyFoundCounter = scopedStatsReceiver.counter("key/found")
  private val keyTotalCounter = scopedStatsReceiver.counter("key/total")

  private val batchSize = 50

  private def getEmbeddingsFromHMF(
    tweetIds: Seq[Long]
  ): Future[Seq[DataRecord]] = {
    val keysSerialized = tweetIds.map(_.toString)
    val request = new HomeMixerFeaturesRequest()
    request.setKeys(keysSerialized.asJava)
    request.setCache(t.Cache.LOG_FAV_BASED_TWEET_20M145K2020_EMBEDDINGS)
    homeMixerFeatureService
      .getHomeMixerFeatures(request)
      .map { resp => unmarshallHomeMixerFeaturesResponse(resp) }
  }

  private def unmarshallHomeMixerFeaturesResponse(
    response: t.HomeMixerFeaturesResponse
  ): Seq[DataRecord] = {
    response.getHomeMixerFeatures.asScala.map { homeMixerFeatureOpt =>
      if (homeMixerFeatureOpt.isSetHomeMixerFeaturesType) {
        val homeMixerFeature = homeMixerFeatureOpt.getHomeMixerFeaturesType
        if (homeMixerFeature.isSet(t.HomeMixerFeaturesType._Fields.DATA_RECORD)) {
          homeMixerFeature.getDataRecord
        } else {
          throw new Exception("Unexpected type")
        }
      } else EmptyDataRecord
    }
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
      response.map { dataRecordOpt =>
        keyFoundCounter.incr()
        FeatureMap(SimClustersLogFavBasedTweetFeature, dataRecordOpt)
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
