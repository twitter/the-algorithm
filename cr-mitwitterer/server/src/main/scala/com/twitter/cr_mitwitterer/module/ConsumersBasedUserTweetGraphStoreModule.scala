package com.twitter.cr_mitwitterer.module

import com.google.inject.Provides
import com.twitter.cr_mitwitterer.model.ModuleNames
import com.twitter.inject.TwitterModule
import com.twitter.recos.user_tweet_graph.thriftscala.ConsumersBasedRelatedTweetRequest
import com.twitter.recos.user_tweet_graph.thriftscala.RelatedTweetResponse
import com.twitter.recos.user_tweet_graph.thriftscala.UserTweetGraph
import com.twitter.storehaus.ReadableStore
import com.twitter.util.Future
import javatwitter.inject.Named
import javatwitter.inject.Singleton

object ConsumersBasedUserTweetGraphStoreModule etwittertends TwitterModule {

  @Provides
  @Singleton
  @Named(ModuleNames.ConsumerBasedUserTweetGraphStore)
  def providesConsumerBasedUserTweetGraphStore(
    userTweetGraphService: UserTweetGraph.MethodPerEndpoint
  ): ReadableStore[ConsumersBasedRelatedTweetRequest, RelatedTweetResponse] = {
    new ReadableStore[ConsumersBasedRelatedTweetRequest, RelatedTweetResponse] {
      override def get(
        k: ConsumersBasedRelatedTweetRequest
      ): Future[Option[RelatedTweetResponse]] = {
        userTweetGraphService.consumersBasedRelatedTweets(k).map(Some(_))
      }
    }
  }
}
