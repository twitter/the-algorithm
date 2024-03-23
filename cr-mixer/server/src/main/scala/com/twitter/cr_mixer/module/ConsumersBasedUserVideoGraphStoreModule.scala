package com.ExTwitter.cr_mixer.module

import com.google.inject.Provides
import com.ExTwitter.cr_mixer.model.ModuleNames
import com.ExTwitter.inject.ExTwitterModule
import com.ExTwitter.recos.user_video_graph.thriftscala.ConsumersBasedRelatedTweetRequest
import com.ExTwitter.recos.user_video_graph.thriftscala.RelatedTweetResponse
import com.ExTwitter.recos.user_video_graph.thriftscala.UserVideoGraph
import com.ExTwitter.storehaus.ReadableStore
import com.ExTwitter.util.Future
import javax.inject.Named
import javax.inject.Singleton

object ConsumersBasedUserVideoGraphStoreModule extends ExTwitterModule {

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
