package com.X.recosinjector.uua_processors

import com.X.conversions.DurationOps._
import com.X.finagle.stats.StatsReceiver
import com.X.finatra.kafka.consumers.FinagleKafkaConsumerBuilder
import com.X.finatra.kafka.domain.KafkaGroupId
import com.X.finatra.kafka.domain.SeekStrategy
import com.X.finatra.kafka.serde.ScalaSerdes
import com.X.finatra.kafka.serde.UnKeyed
import com.X.finatra.kafka.serde.UnKeyedSerde
import org.apache.kafka.clients.CommonClientConfigs
import org.apache.kafka.common.config.SaslConfigs
import org.apache.kafka.common.config.SslConfigs
import org.apache.kafka.common.security.auth.SecurityProtocol
import com.X.unified_user_actions.thriftscala.UnifiedUserAction
import com.X.kafka.client.processor.AtLeastOnceProcessor
import com.X.kafka.client.processor.ThreadSafeKafkaConsumerClient
import com.X.conversions.StorageUnitOps._

class UnifiedUserActionsConsumer(
  processor: UnifiedUserActionProcessor,
  truststoreLocation: String
)(
  implicit statsReceiver: StatsReceiver) {
  import UnifiedUserActionsConsumer._

  private val kafkaClient = new ThreadSafeKafkaConsumerClient[UnKeyed, UnifiedUserAction](
    FinagleKafkaConsumerBuilder[UnKeyed, UnifiedUserAction]()
      .groupId(KafkaGroupId(uuaRecosInjectorGroupId))
      .keyDeserializer(UnKeyedSerde.deserializer)
      .valueDeserializer(ScalaSerdes.Thrift[UnifiedUserAction].deserializer)
      .dest(uuaDest)
      .maxPollRecords(maxPollRecords)
      .maxPollInterval(maxPollInterval)
      .fetchMax(fetchMax)
      .seekStrategy(SeekStrategy.END)
      .enableAutoCommit(false) // AtLeastOnceProcessor performs commits manually
      .withConfig(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, SecurityProtocol.SASL_SSL.toString)
      .withConfig(SslConfigs.SSL_TRUSTSTORE_LOCATION_CONFIG, truststoreLocation)
      .withConfig(SaslConfigs.SASL_MECHANISM, SaslConfigs.GSSAPI_MECHANISM)
      .withConfig(SaslConfigs.SASL_KERBEROS_SERVICE_NAME, "kafka")
      .withConfig(SaslConfigs.SASL_KERBEROS_SERVER_NAME, "kafka")
      .config)

  val atLeastOnceProcessor: AtLeastOnceProcessor[UnKeyed, UnifiedUserAction] = {
    AtLeastOnceProcessor[UnKeyed, UnifiedUserAction](
      name = processorName,
      topic = uuaTopic,
      consumer = kafkaClient,
      processor = processor.apply,
      maxPendingRequests = maxPendingRequests,
      workerThreads = workerThreads,
      commitIntervalMs = commitIntervalMs,
      statsReceiver = statsReceiver.scope(processorName)
    )
  }

}

object UnifiedUserActionsConsumer {
  val maxPollRecords = 1000
  val maxPollInterval = 5.minutes
  val fetchMax = 1.megabytes
  val maxPendingRequests = 1000
  val workerThreads = 16
  val commitIntervalMs = 10.seconds.inMilliseconds
  val processorName = "unified_user_actions_processor"
  val uuaTopic = "unified_user_actions_engagements"
  val uuaDest = "/s/kafka/bluebird-1:kafka-tls"
  val uuaRecosInjectorGroupId = "recos-injector"
}
