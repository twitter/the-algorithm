package com.ExTwitter.home_mixer.module

import com.google.inject.Provides
import com.ExTwitter.finagle.stats.StatsReceiver
import com.ExTwitter.home_mixer.param.HomeMixerInjectionNames.BatchedStratoClientWithModerateTimeout
import com.ExTwitter.inject.ExTwitterModule
import com.ExTwitter.strato.client.Client
import com.ExTwitter.timelines.clients.strato.topics.TopicSocialProofClient
import com.ExTwitter.timelines.clients.strato.topics.TopicSocialProofClientImpl
import javax.inject.Named
import javax.inject.Singleton

object TopicSocialProofClientModule extends ExTwitterModule {

  @Singleton
  @Provides
  def providesSimilarityClient(
    @Named(BatchedStratoClientWithModerateTimeout)
    stratoClient: Client,
    statsReceiver: StatsReceiver
  ): TopicSocialProofClient = new TopicSocialProofClientImpl(stratoClient, statsReceiver)
}
