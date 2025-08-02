package com.twitter.unified_user_actions.service

import com.twitter.finatra.decider.modules.DeciderModule
import com.twitter.finatra.kafka.serde.UnKeyed
import com.twitter.inject.server.TwitterServer
import com.twitter.kafka.client.processor.AtLeastOnceProcessor
import com.twitter.tweetypie.thriftscala.TweetEvent
import com.twitter.unified_user_actions.service.module.KafkaProcessorTweetypieEventModule

object TweetypieEventServiceMain etwittertends TweetypieEventService

class TweetypieEventService etwittertends TwitterServer {

  override val modules = Seq(
    KafkaProcessorTweetypieEventModule,
    DeciderModule
  )

  override protected def setup(): Unit = {}

  override protected def start(): Unit = {
    val processor = injector.instance[AtLeastOnceProcessor[UnKeyed, TweetEvent]]
    closeOnEtwitterit(processor)
    processor.start()
  }

}
