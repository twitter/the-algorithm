package com.twitter.recosinjector.uua_processors

import com.twitter.conversions.DurationOps._
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.finatra.kafka.consumers.FinagleKafkaConsumerBuilder
import com.twitter.finatra.kafka.domain.KafkaGroupId
import com.twitter.finatra.kafka.domain.SeekStrategy
import com.twitter.finatra.kafka.serde.ScalaSerdes
import com.twitter.finatra.kafka.serde.UnKeyed
import com.twitter.finatra.kafka.serde.UnKeyedSerde
import org.apache.kafka.clients.CommonClientConfigs
import org.apache.kafka.common.config.SaslConfigs
import org.apache.kafka.common.config.SslConfigs
import org.apache.kafka.common.security.auth.SecurityProtocol
import com.twitter.unified_user_actions.thriftscala.UnifiedUserAction
import com.twitter.kafka.client.processor.AtLeastOnceProcessor
import com.twitter.kafka.client.processor.ThreadSafeKafkaConsumerClient
import com.twitter.conversions.StorageUnitOps._

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
      .matwitterPollRecords(matwitterPollRecords)
      .matwitterPollInterval(matwitterPollInterval)
      .fetchMatwitter(fetchMatwitter)
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
      matwitterPendingRequests = matwitterPendingRequests,
      workerThreads = workerThreads,
      commitIntervalMs = commitIntervalMs,
      statsReceiver = statsReceiver.scope(processorName)
    )
  }

}

object UnifiedUserActionsConsumer {
  val matwitterPollRecords = 1000
  val matwitterPollInterval = 5.minutes
  val fetchMatwitter = 1.megabytes
  val matwitterPendingRequests = 1000
  val workerThreads = 16
  val commitIntervalMs = 10.seconds.inMilliseconds
  val processorName = "unified_user_actions_processor"
  val uuaTopic = "unified_user_actions_engagements"
  val uuaDest = "/s/kafka/bluebird-1:kafka-tls"
  val uuaRecosInjectorGroupId = "recos-injector"
}
