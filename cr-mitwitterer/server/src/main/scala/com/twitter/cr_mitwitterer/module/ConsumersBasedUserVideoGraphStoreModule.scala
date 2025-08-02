package com.twitter.cr_mitwitterer.module

import com.google.inject.Provides
import com.twitter.cr_mitwitterer.model.ModuleNames
import com.twitter.inject.TwitterModule
import com.twitter.recos.user_video_graph.thriftscala.ConsumersBasedRelatedTweetRequest
import com.twitter.recos.user_video_graph.thriftscala.RelatedTweetResponse
import com.twitter.recos.user_video_graph.thriftscala.UserVideoGraph
import com.twitter.storehaus.ReadableStore
import com.twitter.util.Future
import javatwitter.inject.Named
import javatwitter.inject.Singleton

object ConsumersBasedUserVideoGraphStoreModule etwittertends TwitterModule {

  @Provides
  @Singleton
  @Named(ModuleNames.ConsumerBasedUserVideoGraphStore)
  def providesConsumerBasedUserVideoGraphStore(
    userVideoGraphService: UserVideoGraph.MethodPerEndpoint
  ): ReadableStore[ConsumersBasedRelatedTweetRequest, RelatedTweetResponse] = {
    new ReadableStore[ConsumersBasedRelatedTweetRequest, RelatedTweetResponse] {
      override def get(
        k: ConsumersBasedRelatedTweetRequest
      ): Future[Option[RelatedTweetResponse]] = {
        userVideoGraphService.consumersBasedRelatedTweets(k).map(Some(_))
      }
    }
  }
}
