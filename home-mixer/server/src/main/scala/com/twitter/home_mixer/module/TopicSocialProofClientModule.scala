package com.twitter.home_mixer.module

import com.google.inject.Provides
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.home_mixer.param.HomeMixerInjectionNames.BatchedStratoClientWithModerateTimeout
import com.twitter.inject.TwitterModule
import com.twitter.strato.client.Client
import com.twitter.timelines.clients.strato.topics.TopicSocialProofClient
import com.twitter.timelines.clients.strato.topics.TopicSocialProofClientImpl
import javax.inject.Named
import javax.inject.Singleton

object TopicSocialProofClientModule extends TwitterModule {

  @Singleton
  @Provides
  def providesSimilarityClient(
    @Named(BatchedStratoClientWithModerateTimeout)
    stratoClient: Client,
    statsReceiver: StatsReceiver
  ): TopicSocialProofClient = new TopicSocialProofClientImpl(stratoClient, statsReceiver)
}
