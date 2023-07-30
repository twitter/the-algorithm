package com.X.unified_user_actions.client.summingbird

import com.X.summingbird.TimeExtractor
import com.X.summingbird.storm.Storm
import com.X.summingbird_internal.sources.AppId
import com.X.summingbird_internal.sources.SourceFactory
import com.X.tormenta_internal.spout.Kafka2ScroogeSpoutWrapper
import com.X.unified_user_actions.client.config.ClientConfig
import com.X.unified_user_actions.thriftscala.UnifiedUserAction
import com.X.unified_user_actions.client.config.KafkaConfigs

case class UnifiedUserActionsSourceScrooge(
  appId: AppId,
  parallelism: Int,
  kafkaConfig: ClientConfig = KafkaConfigs.ProdUnifiedUserActions,
  skipToLatest: Boolean = false,
  enableTls: Boolean = true)
    extends SourceFactory[Storm, UnifiedUserAction] {

  override def name: String = "UnifiedUserActionsSource"
  override def description: String = "Unified User Actions (UUA) events"

  // The event timestamps from summingbird's perspective (client), is our internally
  // outputted timestamps (producer). This ensures time-continuity between the client and the
  // producer.
  val timeExtractor: TimeExtractor[UnifiedUserAction] = TimeExtractor { e =>
    e.eventMetadata.receivedTimestampMs
  }

  override def source = {
    Storm.source(
      Kafka2ScroogeSpoutWrapper(
        codec = UnifiedUserAction,
        cluster = kafkaConfig.cluster.name,
        topic = kafkaConfig.topic,
        appId = appId.get,
        skipToLatest = skipToLatest,
        enableTls = enableTls
      ),
      Some(parallelism)
    )(timeExtractor)
  }
}
