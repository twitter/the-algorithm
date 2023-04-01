package com.twitter.timelineranker.config

import com.twitter.storehaus.Store
import com.twitter.timelineranker.recap.model.ContentFeatures
import com.twitter.timelines.model.TweetId
class ClientWrappers(config: RuntimeConfiguration) {
  private[this] val backendClientConfiguration = config.underlyingClients

  val contentFeaturesCache: Store[TweetId, ContentFeatures] =
    backendClientConfiguration.contentFeaturesCache
}
