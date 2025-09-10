package com.twitter.home_mixer.product.for_you.side_effect

import com.twitter.home_mixer.model.HomeFeatures.InNetworkFeature
import com.twitter.home_mixer.model.HomeFeatures.IsReadFromCacheFeature
import com.twitter.home_mixer.model.HomeFeatures.PredictionRequestIdFeature
import com.twitter.home_mixer.model.HomeFeatures.ServedIdFeature
import com.twitter.home_mixer.param.HomeGlobalParams.EnableServedCandidateFeatureKeysKafkaPublishingParam
import com.twitter.home_mixer.service.HomeMixerAlertConfig
import com.twitter.home_mixer.util.CandidatesUtil
import com.twitter.product_mixer.component_library.side_effect.KafkaPublishingSideEffect
import com.twitter.product_mixer.core.functional_component.side_effect.PipelineResultSideEffect
import com.twitter.product_mixer.core.model.common.identifier
import com.twitter.product_mixer.core.model.common.identifier.SideEffectIdentifier
import com.twitter.product_mixer.core.model.common.presentation.CandidateWithDetails
import com.twitter.product_mixer.core.model.marshalling.response.urt.Timeline
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.timelines.ml.cont_train.common.domain.non_scalding.ServedCandidateFeatureKeysAdapter
import com.twitter.timelines.ml.cont_train.common.domain.non_scalding.ServedCandidateFeatureKeysFields
import com.twitter.timelines.ml.kafka.serde.CandidateFeatureKeySerde
import com.twitter.timelines.ml.kafka.serde.TBaseSerde
import com.twitter.timelines.served_candidates_logging.{thriftscala => sc}
import com.twitter.timelines.suggests.common.poly_data_record.{thriftjava => pldr}
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.serialization.Serializer
import scala.collection.JavaConverters._

/**
 * Pipeline side-effect that publishes candidate keys to a Kafka topic.
 */
class ServedCandidateFeatureKeysKafkaSideEffect(
  topic: String,
  sourceIdentifiers: Set[identifier.CandidatePipelineIdentifier])
    extends KafkaPublishingSideEffect[
      sc.CandidateFeatureKey,
      pldr.PolyDataRecord,
      PipelineQuery,
      Timeline
    ]
    with PipelineResultSideEffect.Conditionally[PipelineQuery, Timeline] {

  override val identifier: SideEffectIdentifier = SideEffectIdentifier("ServedCandidateFeatureKeys")

  override def onlyIf(
    query: PipelineQuery,
    selectedCandidates: Seq[CandidateWithDetails],
    remainingCandidates: Seq[CandidateWithDetails],
    droppedCandidates: Seq[CandidateWithDetails],
    response: Timeline
  ): Boolean = query.params.getBoolean(EnableServedCandidateFeatureKeysKafkaPublishingParam)

  override val bootstrapServer: String = "/s/kafka/timeline:kafka-tls"

  override val keySerde: Serializer[sc.CandidateFeatureKey] =
    CandidateFeatureKeySerde().serializer()

  override val valueSerde: Serializer[pldr.PolyDataRecord] =
    TBaseSerde.Thrift[pldr.PolyDataRecord]().serializer

  override val clientId: String = "home_mixer_served_candidate_feature_keys_producer"

  override def buildRecords(
    query: PipelineQuery,
    selectedCandidates: Seq[CandidateWithDetails],
    remainingCandidates: Seq[CandidateWithDetails],
    droppedCandidates: Seq[CandidateWithDetails],
    response: Timeline
  ): Seq[ProducerRecord[sc.CandidateFeatureKey, pldr.PolyDataRecord]] = {
    query.features
      .flatMap(_.getOrElse(ServedIdFeature, None))
      .fold(Seq.empty[ProducerRecord[sc.CandidateFeatureKey, pldr.PolyDataRecord]]) { servedId =>
        CandidatesUtil
          .getItemCandidates {
            selectedCandidates.iterator
              .filter(candidate => sourceIdentifiers.contains(candidate.source)).toSeq
          }
          .flatMap { candidate =>
            candidate.features
              .getOrElse(PredictionRequestIdFeature, None)
              .map { predictionRequestId =>
                val key = sc.CandidateFeatureKey(
                  tweetId = candidate.candidateIdLong,
                  viewerId = query.getRequiredUserId,
                  servedId = -1L)

                val record =
                  ServedCandidateFeatureKeysAdapter
                    .adaptToDataRecords(ServedCandidateFeatureKeysFields(
                      viewerId = query.getRequiredUserId,
                      tweetId = candidate.candidateIdLong,
                      predictionRequestId = predictionRequestId,
                      servedRequestIdOpt = None,
                      servedId = servedId,
                      injectionModuleName = candidate.getClass.getSimpleName,
                      viewerFollowsOriginalAuthor =
                        Some(candidate.features.getOrElse(InNetworkFeature, true)),
                      finalPositionIndex = Some(candidate.sourcePosition),
                      isReadFromCache = candidate.features.getOrElse(IsReadFromCacheFeature, false)
                    )).asScala.head

                new ProducerRecord(topic, key, pldr.PolyDataRecord.dataRecord(record))
              }
          }
      }
  }

  override val alerts = Seq(
    HomeMixerAlertConfig.BusinessHours.defaultSuccessRateAlert(98.5)
  )
}
