package com.X.home_mixer.module

import com.google.inject.Provides
import com.X.finagle.stats.StatsReceiver
import com.X.home_mixer.param.HomeMixerInjectionNames.BatchedStratoClientWithModerateTimeout
import com.X.inject.XModule
import com.X.strato.client.Client
import com.X.timelines.clients.strato.topics.TopicSocialProofClient
import com.X.timelines.clients.strato.topics.TopicSocialProofClientImpl
import javax.inject.Named
import javax.inject.Singleton

object TopicSocialProofClientModule extends XModule {

  @Singleton
  @Provides
  def providesSimilarityClient(
    @Named(BatchedStratoClientWithModerateTimeout)
    stratoClient: Client,
    statsReceiver: StatsReceiver
  ): TopicSocialProofClient = new TopicSocialProofClientImpl(stratoClient, statsReceiver)
}
