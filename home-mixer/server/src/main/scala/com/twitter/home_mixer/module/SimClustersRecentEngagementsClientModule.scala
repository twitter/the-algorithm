package com.ExTwitter.home_mixer.module

import com.google.inject.Provides
import com.ExTwitter.finagle.stats.StatsReceiver
import com.ExTwitter.home_mixer.param.HomeMixerInjectionNames.BatchedStratoClientWithModerateTimeout
import com.ExTwitter.inject.ExTwitterModule
import com.ExTwitter.strato.client.Client
import com.ExTwitter.timelines.clients.strato.twistly.SimClustersRecentEngagementSimilarityClient
import com.ExTwitter.timelines.clients.strato.twistly.SimClustersRecentEngagementSimilarityClientImpl
import javax.inject.Named
import javax.inject.Singleton

object SimClustersRecentEngagementsClientModule extends ExTwitterModule {
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
