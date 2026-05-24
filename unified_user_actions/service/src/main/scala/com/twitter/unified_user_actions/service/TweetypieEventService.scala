package com.twitter.unified_user_actions.service

import com.twitter.finatra.decider.modules.DeciderModule
import com.twitter.finatra.kafka.serde.UnKeyed
import com.twitter.inject.server.TwitterServer
import com.twitter.kafka.client.processor.AtLeastOnceProcessor
import com.twitter.tweetypie.thriftscala.TweetEvent
import com.twitter.unified_user_actions.service.module.KafkaProcessorTweetypieEventModule

object TweetypieEventServiceMain extends TweetypieEventService

class TweetypieEventService extends TwitterServer {

  override val modules = Seq(
    KafkaProcessorTweetypieEventModule,
    DeciderModule
  )

  override protected def setup(): Unit = {}

  override protected def start(): Unit = {
    val processor = injector.instance[AtLeastOnceProcessor[UnKeyed, TweetEvent]]
    closeOnExit(processor)
    processor.start()
  }

}
