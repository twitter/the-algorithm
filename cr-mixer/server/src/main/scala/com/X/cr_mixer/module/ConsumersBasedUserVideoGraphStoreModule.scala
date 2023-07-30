package com.X.cr_mixer.module

import com.google.inject.Provides
import com.X.cr_mixer.model.ModuleNames
import com.X.inject.XModule
import com.X.recos.user_video_graph.thriftscala.ConsumersBasedRelatedTweetRequest
import com.X.recos.user_video_graph.thriftscala.RelatedTweetResponse
import com.X.recos.user_video_graph.thriftscala.UserVideoGraph
import com.X.storehaus.ReadableStore
import com.X.util.Future
import javax.inject.Named
import javax.inject.Singleton

object ConsumersBasedUserVideoGraphStoreModule extends XModule {

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
