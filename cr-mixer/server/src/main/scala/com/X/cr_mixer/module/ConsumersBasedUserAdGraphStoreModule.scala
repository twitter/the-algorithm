package com.X.cr_mixer.module

import com.google.inject.Provides
import com.X.cr_mixer.model.ModuleNames
import com.X.inject.XModule
import com.X.recos.user_ad_graph.thriftscala.ConsumersBasedRelatedAdRequest
import com.X.recos.user_ad_graph.thriftscala.RelatedAdResponse
import com.X.recos.user_ad_graph.thriftscala.UserAdGraph
import com.X.storehaus.ReadableStore
import com.X.util.Future
import javax.inject.Named
import javax.inject.Singleton

object ConsumersBasedUserAdGraphStoreModule extends XModule {

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
