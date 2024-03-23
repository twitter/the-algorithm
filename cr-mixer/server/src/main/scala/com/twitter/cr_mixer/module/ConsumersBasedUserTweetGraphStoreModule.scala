package com.ExTwitter.cr_mixer.module

import com.google.inject.Provides
import com.ExTwitter.cr_mixer.model.ModuleNames
import com.ExTwitter.inject.ExTwitterModule
import com.ExTwitter.recos.user_tweet_graph.thriftscala.ConsumersBasedRelatedTweetRequest
import com.ExTwitter.recos.user_tweet_graph.thriftscala.RelatedTweetResponse
import com.ExTwitter.recos.user_tweet_graph.thriftscala.UserTweetGraph
import com.ExTwitter.storehaus.ReadableStore
import com.ExTwitter.util.Future
import javax.inject.Named
import javax.inject.Singleton

object ConsumersBasedUserTweetGraphStoreModule extends ExTwitterModule {

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
