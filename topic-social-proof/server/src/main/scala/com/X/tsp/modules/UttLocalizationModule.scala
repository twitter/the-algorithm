package com.X.tsp.modules

import com.google.inject.Provides
import com.X.finagle.stats.StatsReceiver
import com.X.inject.XModule
import com.X.topiclisting.TopicListing
import com.X.topiclisting.clients.utt.UttClient
import com.X.topiclisting.utt.UttLocalization
import com.X.topiclisting.utt.UttLocalizationImpl
import javax.inject.Singleton

object UttLocalizationModule extends XModule {

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
