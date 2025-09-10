package com.twitter.home_mixer.functional_component.feature_hydrator.real_time_aggregates

import com.twitter.home_mixer.functional_component.feature_hydrator.WithDefaultFeatureMap
import com.twitter.home_mixer.product.scored_tweets.param.ScoredTweetsParam.EnableHomeMixerFeaturesService
import com.twitter.home_mixer.util.MissingKeyException
import com.twitter.home_mixer_features.thriftjava.HomeMixerFeaturesRequest
import com.twitter.home_mixer_features.{thriftjava => t}
import com.twitter.ml.api.DataRecord
import com.twitter.product_mixer.component_library.feature_hydrator.candidate.real_time_aggregates.BaseRealTimeAggregateBulkCandidateFeatureHydrator
import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mixer.core.model.common.CandidateWithFeatures
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.product_mixer.core.util.OffloadFuturePools
import com.twitter.stitch.Stitch
import com.twitter.util.Future
import com.twitter.util.Return
import com.twitter.util.Throw
import com.twitter.util.Try
import scala.jdk.CollectionConverters.iterableAsScalaIterableConverter
import scala.jdk.CollectionConverters.seqAsJavaListConverter

trait FlagBasedRealTimeAggregateBulkCandidateFeatureHydrator[K]
    extends BaseRealTimeAggregateBulkCandidateFeatureHydrator[K, TweetCandidate]
    with WithDefaultFeatureMap {

  def EmptyDataRecord: DataRecord = new DataRecord()

  override lazy val defaultFeatureMap: FeatureMap = FeatureMap(outputFeature, EmptyDataRecord)

  def serializeKey(key: K): String

  val homeMixerFeatureService: t.HomeMixerFeatures.ServiceToClient

  private val batchSize = 64

  private def createHomeMixerFeaturesRequest(keys: Seq[K]): t.HomeMixerFeaturesRequest = {
    val keysSerialized = keys.map(serializeKey)
    val request = new HomeMixerFeaturesRequest()
    request.setKeys(keysSerialized.asJava)
    request.setCache(t.Cache.RTA)
  }

  private def unmarshallHomeMixerFeaturesResponse(
    response: t.HomeMixerFeaturesResponse
  ): Iterable[DataRecord] = {
    response.getHomeMixerFeatures.asScala
      .map { homeMixerFeatureOpt =>
        if (homeMixerFeatureOpt.isSetHomeMixerFeaturesType) {
          val homeMixerFeature = homeMixerFeatureOpt.getHomeMixerFeaturesType
          if (homeMixerFeature.isSet(t.HomeMixerFeaturesType._Fields.DATA_RECORD)) {
            postTransformer(homeMixerFeature.getDataRecord)
          } else {
            throw new Exception("Unexpected type")
          }
        } else EmptyDataRecord
      }
  }

  private def getFeatureMaps(
    possiblyKeys: Seq[Option[K]],
    dataRecordMap: Map[K, DataRecord]
  ): Future[Seq[Try[DataRecord]]] = {
    val transformer = { key: Option[K] =>
      if (key.nonEmpty)
        Return(dataRecordMap.getOrElse(key.get, EmptyDataRecord))
      else Throw(MissingKeyException)
    }
    OffloadFuturePools.offloadBatchElementToElement(possiblyKeys, transformer, batchSize)
  }

  def fetchRecordsFromHomeMixerFeaturesService(
    possiblyKeys: Seq[Option[K]]
  ): Future[Seq[Try[DataRecord]]] = {
    val keys = possiblyKeys.flatten.distinct

    val transformer = { keyGroup: Seq[K] =>
      val request = createHomeMixerFeaturesRequest(keyGroup)
      val responseFut =
        homeMixerFeatureService.getHomeMixerFeatures(request)
      responseFut
        .map { response =>
          keyGroup.zip(unmarshallHomeMixerFeaturesResponse(response))
        }.handle { case _ => Seq.empty }
    }

    val response =
      OffloadFuturePools.offloadBatchSeqToFutureSeq(keys, transformer, batchSize)

    response.map(_.toMap).flatMap { keyValueResult =>
      getFeatureMaps(possiblyKeys, keyValueResult)
    }
  }

  def fetchRecords(
    possiblyKeys: Seq[Option[K]],
    callMiddleMan: Boolean
  ): Future[Seq[Try[DataRecord]]] = {
    if (callMiddleMan) fetchRecordsFromHomeMixerFeaturesService(possiblyKeys)
    else fetchAndConstructDataRecords(possiblyKeys) // cache is default
  }

  override def apply(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Stitch[Seq[FeatureMap]] = OffloadFuturePools.offloadFuture {
    val possiblyKeys = keysFromQueryAndCandidates(query, candidates)
    val callMiddleMan = query.params(EnableHomeMixerFeaturesService)
    fetchRecords(possiblyKeys, callMiddleMan).map { dataRecords =>
      val featureMaps = dataRecords.map { dataRecord =>
        FeatureMapBuilder().add(outputFeature, dataRecord).build()
      }
      featureMaps
    }
  }

}
