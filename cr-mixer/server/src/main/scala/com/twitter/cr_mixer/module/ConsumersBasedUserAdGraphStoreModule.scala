package com.ExTwitter.cr_mixer.module

import com.google.inject.Provides
import com.ExTwitter.cr_mixer.model.ModuleNames
import com.ExTwitter.inject.ExTwitterModule
import com.ExTwitter.recos.user_ad_graph.thriftscala.ConsumersBasedRelatedAdRequest
import com.ExTwitter.recos.user_ad_graph.thriftscala.RelatedAdResponse
import com.ExTwitter.recos.user_ad_graph.thriftscala.UserAdGraph
import com.ExTwitter.storehaus.ReadableStore
import com.ExTwitter.util.Future
import javax.inject.Named
import javax.inject.Singleton

object ConsumersBasedUserAdGraphStoreModule extends ExTwitterModule {

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
