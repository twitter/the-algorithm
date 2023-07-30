package com.X.cr_mixer.module

import com.google.inject.Provides
import com.X.cr_mixer.model.ModuleNames
import com.X.inject.XModule
import com.X.recos.user_tweet_graph.thriftscala.ConsumersBasedRelatedTweetRequest
import com.X.recos.user_tweet_graph.thriftscala.RelatedTweetResponse
import com.X.recos.user_tweet_graph.thriftscala.UserTweetGraph
import com.X.storehaus.ReadableStore
import com.X.util.Future
import javax.inject.Named
import javax.inject.Singleton

object ConsumersBasedUserTweetGraphStoreModule extends XModule {

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
