package com.X.tsp.modules

import com.google.inject.Provides
import com.X.finagle.stats.StatsReceiver
import com.X.inject.XModule
import com.X.topiclisting.TopicListing
import com.X.topiclisting.TopicListingBuilder
import javax.inject.Singleton

object TopicListingModule extends XModule {

  @Provides
  @Singleton
  def providesTopicListing(statsReceiver: StatsReceiver): TopicListing = {
    new TopicListingBuilder(statsReceiver.scope(namespace = "TopicListingBuilder")).build
  }
}
