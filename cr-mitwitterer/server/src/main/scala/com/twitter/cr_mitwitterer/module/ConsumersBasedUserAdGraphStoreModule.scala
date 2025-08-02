package com.twitter.cr_mitwitterer.module

import com.google.inject.Provides
import com.twitter.cr_mitwitterer.model.ModuleNames
import com.twitter.inject.TwitterModule
import com.twitter.recos.user_ad_graph.thriftscala.ConsumersBasedRelatedAdRequest
import com.twitter.recos.user_ad_graph.thriftscala.RelatedAdResponse
import com.twitter.recos.user_ad_graph.thriftscala.UserAdGraph
import com.twitter.storehaus.ReadableStore
import com.twitter.util.Future
import javatwitter.inject.Named
import javatwitter.inject.Singleton

object ConsumersBasedUserAdGraphStoreModule etwittertends TwitterModule {

  @Provides
  @Singleton
  @Named(ModuleNames.ConsumerBasedUserAdGraphStore)
  def providesConsumerBasedUserAdGraphStore(
    userAdGraphService: UserAdGraph.MethodPerEndpoint
  ): ReadableStore[ConsumersBasedRelatedAdRequest, RelatedAdResponse] = {
    new ReadableStore[ConsumersBasedRelatedAdRequest, RelatedAdResponse] {
      override def get(
        k: ConsumersBasedRelatedAdRequest
      ): Future[Option[RelatedAdResponse]] = {
        userAdGraphService.consumersBasedRelatedAds(k).map(Some(_))
      }
    }
  }
}
