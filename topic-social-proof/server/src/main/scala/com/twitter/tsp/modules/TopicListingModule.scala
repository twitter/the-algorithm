package com.twitter.tsp.modules

import com.google.inject.Provides
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.inject.TwitterModule
import com.twitter.topiclisting.TopicListing
import com.twitter.topiclisting.TopicListingBuilder
import javax.inject.Singleton

object TopicListingModule extends TwitterModule {

  @Provides
  @Singleton
  def providesTopicListing(statsReceiver: StatsReceiver): TopicListing = {
    new TopicListingBuilder(statsReceiver.scope(namespace = "TopicListingBuilder")).build
  }
}
