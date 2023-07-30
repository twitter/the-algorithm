package com.X.timelineranker.config

import com.X.storehaus.Store
import com.X.timelineranker.recap.model.ContentFeatures
import com.X.timelines.model.TweetId
class ClientWrappers(config: RuntimeConfiguration) {
  private[this] val backendClientConfiguration = config.underlyingClients

  val contentFeaturesCache: Store[TweetId, ContentFeatures] =
    backendClientConfiguration.contentFeaturesCache
}
