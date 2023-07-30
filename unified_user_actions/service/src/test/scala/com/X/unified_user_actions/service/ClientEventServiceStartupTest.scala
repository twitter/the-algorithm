package com.X.unified_user_actions.service

import com.google.inject.Stage
import com.X.app.GlobalFlag
import com.X.clientapp.thriftscala.EventDetails
import com.X.clientapp.thriftscala.EventNamespace
import com.X.clientapp.thriftscala.Item
import com.X.clientapp.thriftscala.ItemType
import com.X.clientapp.thriftscala.LogEvent
import com.X.finatra.kafka.consumers.FinagleKafkaConsumerBuilder
import com.X.finatra.kafka.domain.AckMode
import com.X.finatra.kafka.domain.KafkaGroupId
import com.X.finatra.kafka.domain.KafkaTopic
import com.X.finatra.kafka.domain.SeekStrategy
import com.X.finatra.kafka.producers.FinagleKafkaProducerBuilder
import com.X.finatra.kafka.serde.ScalaSerdes
import com.X.finatra.kafka.serde.UnKeyed
import com.X.finatra.kafka.serde.UnKeyedSerde
import com.X.finatra.kafka.test.KafkaFeatureTest
import com.X.inject.server.EmbeddedXServer
import com.X.kafka.client.processor.KafkaConsumerClient
import com.X.logbase.thriftscala.LogBase
import com.X.unified_user_actions.kafka.ClientConfigs
import com.X.unified_user_actions.service.module.KafkaProcessorClientEventModule
import com.X.unified_user_actions.thriftscala.UnifiedUserAction
import com.X.util.Duration
import com.X.util.StorageUnit

class ClientEventServiceStartupTest extends KafkaFeatureTest {
  private val inputTopic =
    kafkaTopic(UnKeyedSerde, ScalaSerdes.Thrift[LogEvent], name = "source")
  private val outputTopic =
    kafkaTopic(UnKeyedSerde, ScalaSerdes.Thrift[UnifiedUserAction], name = "sink")

  val startupFlags = Map(
    "kafka.group.id" -> "client-event",
    "kafka.producer.client.id" -> "uua",
    "kafka.source.topic" -> inputTopic.topic,
    "kafka.sink.topics" -> outputTopic.topic,
    "kafka.consumer.fetch.min" -> "6.megabytes",
    "kafka.max.pending.requests" -> "100",
    "kafka.worker.threads" -> "1",
    "kafka.trust.store.enable" -> "false",
    "kafka.producer.batch.size" -> "0.byte",
    "cluster" -> "atla",
  )

  val deciderFlags = Map(
    "decider.base" -> "/decider.yml"
  )

  override protected def kafkaBootstrapFlag: Map[String, String] = {
    Map(
      ClientConfigs.kafkaBootstrapServerConfig -> kafkaCluster.bootstrapServers(),
      ClientConfigs.kafkaBootstrapServerRemoteDestConfig -> kafkaCluster.bootstrapServers(),
    )
  }

  override val server: EmbeddedXServer = new EmbeddedXServer(
    XServer = new ClientEventService() {
      override def warmup(): Unit = {
        // noop
      }

      override val overrideModules = Seq(
        KafkaProcessorClientEventModule
      )
    },
    globalFlags = Map[GlobalFlag[_], String](
      com.X.finatra.kafka.consumers.enableTlsAndKerberos -> "false",
    ),
    flags = startupFlags ++ kafkaBootstrapFlag ++ deciderFlags,
    stage = Stage.PRODUCTION
  )

  private def getConsumer(
    seekStrategy: SeekStrategy = SeekStrategy.BEGINNING,
  ) = {
    val builder = FinagleKafkaConsumerBuilder()
      .dest(brokers.map(_.brokerList()).mkString(","))
      .clientId("consumer")
      .groupId(KafkaGroupId("validator"))
      .keyDeserializer(UnKeyedSerde.deserializer)
      .valueDeserializer(ScalaSerdes.Thrift[LogEvent].deserializer)
      .requestTimeout(Duration.fromSeconds(1))
      .enableAutoCommit(false)
      .seekStrategy(seekStrategy)

    new KafkaConsumerClient(builder.config)
  }

  private def getProducer(clientId: String = "producer") = {
    FinagleKafkaProducerBuilder()
      .dest(brokers.map(_.brokerList()).mkString(","))
      .clientId(clientId)
      .ackMode(AckMode.ALL)
      .batchSize(StorageUnit.zero)
      .keySerializer(UnKeyedSerde.serializer)
      .valueSerializer(ScalaSerdes.Thrift[LogEvent].serializer)
      .build()
  }

  test("ClientEventService starts") {
    server.assertHealthy()
  }

  test("ClientEventService should process input events") {
    val producer = getProducer()
    val inputConsumer = getConsumer()

    val value: LogEvent = LogEvent(
      eventName = "test_tweet_render_impression_event",
      eventNamespace =
        Some(EventNamespace(component = Some("stream"), element = None, action = Some("results"))),
      eventDetails = Some(
        EventDetails(
          items = Some(
            Seq[Item](
              Item(id = Some(1L), itemType = Some(ItemType.Tweet))
            ))
        )),
      logBase = Some(LogBase(timestamp = 10001L, transactionId = "", ipAddress = ""))
    )

    try {
      server.assertHealthy()

      // before, should be empty
      inputConsumer.subscribe(Set(KafkaTopic(inputTopic.topic)))
      assert(inputConsumer.poll().count() == 0)

      // after, should contain at least a message
      await(producer.send(inputTopic.topic, new UnKeyed, value, System.currentTimeMillis))
      producer.flush()
      assert(inputConsumer.poll().count() >= 1)
    } finally {
      await(producer.close())
      inputConsumer.close()
    }
  }
}
