package com.twitter.home_mixer.product.scored_tweets.side_effect

import com.twitter.finagle.mtls.authentication.ServiceIdentifier
import com.twitter.finatra.kafka.serde.ScalaSerdes
import com.twitter.home_mixer.functional_component.side_effect.CommonFeaturesPldrConverter
import com.twitter.home_mixer.param.HomeMixerFlagName.ScribeFeaturesFlag
import com.twitter.home_mixer.param.HomeMixerInjectionNames.CommonFeaturesScribeEventPublisher
import com.twitter.home_mixer.service.HomeMixerAlertConfig
import com.twitter.inject.annotations.Flag
import com.twitter.logpipeline.client.common.EventPublisher
import com.twitter.product_mixer.component_library.side_effect.KafkaAndScribePublishingSideEffect
import com.twitter.product_mixer.core.functional_component.side_effect.PipelineResultSideEffect.Conditionally
import com.twitter.product_mixer.core.model.common.identifier.SideEffectIdentifier
import com.twitter.product_mixer.core.model.common.presentation.CandidateWithDetails
import com.twitter.product_mixer.core.model.marshalling.HasMarshalling
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.timelines.ml.kafka.serde.TBaseSerde
import com.twitter.timelines.suggests.common.poly_data_record.thriftjava.PolyDataRecord
import com.twitter.timelines.suggests.common.poly_data_record.{thriftjava => pldr}
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.serialization.Serializer

/**
 * Publish common features sent to prediction service + some other features as PLDR format
 * into both scribe logs and kafka
 */
@Singleton
class CommonFeaturesSideEffect @Inject() (
  serviceIdentifier: ServiceIdentifier,
  commonFeaturesPldrConverter: CommonFeaturesPldrConverter,
  @Flag(ScribeFeaturesFlag) enableScribeFeatures: Boolean,
  @Named(CommonFeaturesScribeEventPublisher) override val logPipelinePublisher: EventPublisher[
    pldr.PolyDataRecord
  ]) extends KafkaAndScribePublishingSideEffect[
      Long,
      pldr.PolyDataRecord,
      PipelineQuery,
      HasMarshalling
    ]
    with Conditionally[PipelineQuery, HasMarshalling] {

  override val identifier: SideEffectIdentifier = SideEffectIdentifier("CommonFeaturesSideEffect")

  private val kafkaTopic: String = serviceIdentifier.environment.toLowerCase match {
    case "prod" => "tq_ct_common_features"
    case _ => "tq_ct_common_features_staging"
  }

  override val bootstrapServer: String = "/s/kafka/timeline:kafka-tls"
  override val keySerde: Serializer[Long] = ScalaSerdes.Long.serializer()
  override val valueSerde: Serializer[PolyDataRecord] =
    TBaseSerde.Thrift[pldr.PolyDataRecord]().serializer
  override val clientId: String = "home_mixer_common_features_producer"

  override def enableScribePublishing(query: PipelineQuery): Boolean = enableScribeFeatures

  /** @see [[common.Conditionally.onlyIf]] */
  override def onlyIf(
    query: PipelineQuery,
    selectedCandidates: Seq[CandidateWithDetails],
    remainingCandidates: Seq[CandidateWithDetails],
    droppedCandidates: Seq[CandidateWithDetails],
    response: HasMarshalling
  ): Boolean = selectedCandidates.nonEmpty

  /**
   * Build the record to be published to Kafka from query, selections and response
   *
   * @param query               PipelineQuery
   * @param selectedCandidates  Result after Selectors are executed
   * @param remainingCandidates Candidates which were not selected
   * @param droppedCandidates   Candidates dropped during selection
   * @param response            Result after Unmarshalling
   *
   * @return A sequence of to-be-published ProducerRecords
   */
  override def buildRecords(
    query: PipelineQuery,
    selectedCandidates: Seq[CandidateWithDetails],
    remainingCandidates: Seq[CandidateWithDetails],
    droppedCandidates: Seq[CandidateWithDetails],
    response: HasMarshalling
  ): Seq[ProducerRecord[Long, PolyDataRecord]] = {
    commonFeaturesPldrConverter
      .getCommonFeaturesPldr(query, selectedCandidates).map {
        case (predictionRequestId, pldr) =>
          new ProducerRecord(kafkaTopic, predictionRequestId, pldr)
      }.toSeq
  }

  override val alerts = Seq(HomeMixerAlertConfig.BusinessHours.defaultSuccessRateAlert(98.5))

}
