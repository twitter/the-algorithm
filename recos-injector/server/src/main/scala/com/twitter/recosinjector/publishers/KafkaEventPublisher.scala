package com.twitter.recosinjector.publishers

import com.twitter.finagle.stats.StatsReceiver
import com.twitter.finagle.thrift.ClientId
import com.twitter.finatra.kafka.producers.FinagleKafkaProducerBuilder
import com.twitter.finatra.kafka.serde.ScalaSerdes
import com.twitter.recos.internal.thriftscala.RecosHoseMessage
import org.apache.kafka.clients.CommonClientConfigs
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.config.SaslConfigs
import org.apache.kafka.common.config.SslConfigs
import org.apache.kafka.common.security.auth.SecurityProtocol
import org.apache.kafka.common.serialization.StringSerializer

case class KafkaEventPublisher(
  kafkaDest: String,
  outputKafkaTopicPrefix: String,
  clientId: ClientId,
  truststoreLocation: String) {

  private val producer = FinagleKafkaProducerBuilder[String, RecosHoseMessage]()
    .dest(kafkaDest)
    .clientId(clientId.name)
    .keySerializer(new StringSerializer)
    .valueSerializer(ScalaSerdes.Thrift[RecosHoseMessage].serializer)
    .withConfig(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, SecurityProtocol.SASL_SSL.toString)
    .withConfig(SslConfigs.SSL_TRUSTSTORE_LOCATION_CONFIG, truststoreLocation)
    .withConfig(SaslConfigs.SASL_MECHANISM, SaslConfigs.GSSAPI_MECHANISM)
    .withConfig(SaslConfigs.SASL_KERBEROS_SERVICE_NAME, "kafka")
    .withConfig(SaslConfigs.SASL_KERBEROS_SERVER_NAME, "kafka")
    // Use Native Kafka Client
    .buildClient()

  def publish(
    message: RecosHoseMessage,
    topic: String
  )(
    implicit statsReceiver: StatsReceiver
  ): Unit = {
    val topicName = s"${outputKafkaTopicPrefix}_$topic"
    // Kafka Producer is thread-safe. No extra Future-pool protect.
    producer.send(new ProducerRecord(topicName, message))
    statsReceiver.counter(topicName + "_written_msg_success").incr()
  }
}

object KafkaEventPublisher {
  // Kafka topics available for publishing
  val UserVideoTopic = "user_video"
  val UserTweetEntityTopic = "user_tweet_entity"
  val UserUserTopic = "user_user"
  val UserAdTopic = "user_tweet"
  val UserTweetPlusTopic = "user_tweet_plus"
}
