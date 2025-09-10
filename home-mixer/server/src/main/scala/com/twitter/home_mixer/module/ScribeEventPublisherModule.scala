package com.twitter.home_mixer.module

import com.google.inject.Provides
import com.twitter.clientapp.{thriftscala => ca}
import com.twitter.finatra.kafka.interceptors.InstanceMetadataProducerInterceptor
import com.twitter.finatra.kafka.interceptors.PublishTimeProducerInterceptor
import com.twitter.finatra.kafka.producers.FinagleKafkaProducerBuilder
import com.twitter.finatra.kafka.serde.ScalaSerdes
import com.twitter.finatra.kafka.serde.UnKeyedSerde
import com.twitter.home_mixer.param.HomeMixerInjectionNames.CommonFeaturesScribeEventPublisher
import com.twitter.home_mixer.param.HomeMixerInjectionNames.CommonFeaturesScribeVideoEventPublisher
import com.twitter.inject.TwitterModule
import com.twitter.logpipeline.client.EventPublisherManager
import com.twitter.logpipeline.client.common.EventPublisher
import com.twitter.logpipeline.client.serializers.EventLogMsgTBinarySerializer
import com.twitter.timelines.suggests.common.poly_data_record.{thriftjava => pldr}
import com.twitter.timelines.timeline_logging.{thriftscala => tl}
import com.twitter.util.Duration
import com.twitter.util.StorageUnit
import javax.inject.Named
import javax.inject.Singleton
import org.apache.kafka.clients.CommonClientConfigs
import org.apache.kafka.common.config.SaslConfigs
import org.apache.kafka.common.config.SslConfigs
import org.apache.kafka.common.security.auth.SecurityProtocol

object ScribeEventPublisherModule extends TwitterModule {

  val ClientEventLogCategory = "client_event"
  val ServedCandidatesLogCategory = "home_timeline_served_candidates_flattened"
  val ScoredCandidatesLogCategory = "home_timeline_scored_candidates"
  val ServedCommonFeaturesLogCategory = "tq_served_common_features_offline"
  val ServedVideoCommonFeaturesLogCategory = "tq_served_video_common_features_offline"

  @Provides
  @Singleton
  def providesClientEventsScribeEventPublisher: EventPublisher[ca.LogEvent] = {
    val builder = FinagleKafkaProducerBuilder()
      .dest(s"/s/kafka/client-events:kafka-tls")
      .keySerializer(UnKeyedSerde.serializer)
      .valueSerializer(ScalaSerdes.Thrift[ca.LogEvent].serializer)
      .clientId("home_mixer_client_event_publisher")
      .linger(Duration.fromMilliseconds(16))
      .batchSize(StorageUnit.fromKilobytes(64))
      .deliveryTimeout(Duration.fromMilliseconds(30000))
      .requestTimeout(Duration.fromMilliseconds(25000))
      .withConfig(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, SecurityProtocol.SASL_SSL.toString)
      .interceptor[PublishTimeProducerInterceptor]
      .interceptor[InstanceMetadataProducerInterceptor]
      .withConfig(SslConfigs.SSL_TRUSTSTORE_LOCATION_CONFIG, "")
      .withConfig(SaslConfigs.SASL_MECHANISM, SaslConfigs.GSSAPI_MECHANISM)
      .withConfig(SaslConfigs.SASL_KERBEROS_SERVICE_NAME, "kafka")
      .withConfig(SaslConfigs.SASL_KERBEROS_SERVER_NAME, "kafka")
    EventPublisherManager.buildKafkaLogPipelinePublisher(builder, ClientEventLogCategory)
  }

  @Provides
  @Singleton
  @Named(CommonFeaturesScribeEventPublisher)
  def providesCommonFeaturesScribeEventPublisher: EventPublisher[pldr.PolyDataRecord] = {
    val serializer = EventLogMsgTBinarySerializer.getNewSerializer
    EventPublisherManager.buildScribeLogPipelinePublisher(
      ServedCommonFeaturesLogCategory,
      serializer)
  }

  @Provides
  @Singleton
  @Named(CommonFeaturesScribeVideoEventPublisher)
  def providesCommonFeaturesScribeVideoEventPublisher: EventPublisher[pldr.PolyDataRecord] = {
    val serializer = EventLogMsgTBinarySerializer.getNewSerializer
    EventPublisherManager.buildScribeLogPipelinePublisher(
      ServedVideoCommonFeaturesLogCategory,
      serializer)
  }

  @Provides
  @Singleton
  def providesServedCandidatesScribeEventPublisher: EventPublisher[tl.ServedEntry] = {
    val builder = FinagleKafkaProducerBuilder()
      .dest("/s/kafka/timeline:kafka-tls")
      .keySerializer(UnKeyedSerde.serializer)
      .valueSerializer(ScalaSerdes.Thrift[tl.ServedEntry].serializer)
      .clientId(s"$ServedCandidatesLogCategory-publisher")
      .linger(Duration.fromMilliseconds(16))
      .batchSize(StorageUnit.fromKilobytes(64))
      .deliveryTimeout(Duration.fromMilliseconds(30000))
      .requestTimeout(Duration.fromMilliseconds(25000))
      .withConfig(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, SecurityProtocol.SASL_SSL.toString)
      .interceptor[PublishTimeProducerInterceptor]
      .interceptor[InstanceMetadataProducerInterceptor]
      .withConfig(SslConfigs.SSL_TRUSTSTORE_LOCATION_CONFIG, "")
      .withConfig(SaslConfigs.SASL_MECHANISM, SaslConfigs.GSSAPI_MECHANISM)
      .withConfig(SaslConfigs.SASL_KERBEROS_SERVICE_NAME, "kafka")
      .withConfig(SaslConfigs.SASL_KERBEROS_SERVER_NAME, "kafka")
    EventPublisherManager.buildKafkaLogPipelinePublisher(builder, ServedCandidatesLogCategory)
  }

  @Provides
  @Singleton
  def provideScoredCandidatesScribeEventPublisher: EventPublisher[tl.ScoredCandidate] = {
    val builder = FinagleKafkaProducerBuilder()
      .dest("/s/kafka/timeline:kafka-tls")
      .keySerializer(UnKeyedSerde.serializer)
      .valueSerializer(ScalaSerdes.Thrift[tl.ScoredCandidate].serializer)
      .clientId(s"$ScoredCandidatesLogCategory-publisher")
      .linger(Duration.fromMilliseconds(16))
      .batchSize(StorageUnit.fromKilobytes(64))
      .deliveryTimeout(Duration.fromMilliseconds(30000))
      .requestTimeout(Duration.fromMilliseconds(25000))
      .withConfig(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, SecurityProtocol.SASL_SSL.toString)
      .interceptor[PublishTimeProducerInterceptor]
      .interceptor[InstanceMetadataProducerInterceptor]
      .withConfig(SslConfigs.SSL_TRUSTSTORE_LOCATION_CONFIG, "")
      .withConfig(SaslConfigs.SASL_MECHANISM, SaslConfigs.GSSAPI_MECHANISM)
      .withConfig(SaslConfigs.SASL_KERBEROS_SERVICE_NAME, "kafka")
      .withConfig(SaslConfigs.SASL_KERBEROS_SERVER_NAME, "kafka")
    EventPublisherManager.buildKafkaLogPipelinePublisher(builder, ScoredCandidatesLogCategory)
  }
}
