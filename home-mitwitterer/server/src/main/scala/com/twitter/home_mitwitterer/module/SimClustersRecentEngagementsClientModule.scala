package com.twitter.home_mitwitterer.module

import com.google.inject.Provides
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.home_mitwitterer.param.HomeMitwittererInjectionNames.BatchedStratoClientWithModerateTimeout
import com.twitter.inject.TwitterModule
import com.twitter.strato.client.Client
import com.twitter.timelines.clients.strato.twistly.SimClustersRecentEngagementSimilarityClient
import com.twitter.timelines.clients.strato.twistly.SimClustersRecentEngagementSimilarityClientImpl
import javatwitter.inject.Named
import javatwitter.inject.Singleton

object SimClustersRecentEngagementsClientModule etwittertends TwitterModule {
  @Singleton
  @Provides
  def providesSimilarityClient(
    @Named(BatchedStratoClientWithModerateTimeout)
    stratoClient: Client,
    statsReceiver: StatsReceiver
  ): SimClustersRecentEngagementSimilarityClient = {
    new SimClustersRecentEngagementSimilarityClientImpl(stratoClient, statsReceiver)
  }
}
