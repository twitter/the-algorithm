package com.twitter.tsp.modules

import com.google.inject.Provides
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.inject.TwitterModule
import com.twitter.topiclisting.TopicListing
import com.twitter.topiclisting.clients.utt.UttClient
import com.twitter.topiclisting.utt.UttLocalization
import com.twitter.topiclisting.utt.UttLocalizationImpl
import javax.inject.Singleton

object UttLocalizationModule extends TwitterModule {

  @Provides
  @Singleton
  def providesUttLocalization(
    topicListing: TopicListing,
    uttClient: UttClient,
    statsReceiver: StatsReceiver
  ): UttLocalization = {
    new UttLocalizationImpl(
      topicListing,
      uttClient,
      statsReceiver
    )
  }
}
