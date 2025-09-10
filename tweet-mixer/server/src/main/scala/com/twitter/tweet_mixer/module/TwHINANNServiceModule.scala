package com.twitter.tweet_mixer.module

import com.google.inject.Provides
import com.twitter.finagle.mtls.authentication.ServiceIdentifier
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.inject.TwitterModule
import com.twitter.timelines.clients.ann.ANNQdrantGRPCClient
import com.twitter.timelines.clients.ann.ANNQdrantService
import com.twitter.tweet_mixer.model.ModuleNames

import javax.inject.Named
import javax.inject.Singleton

object TwHINANNServiceModule extends TwitterModule {

  @Provides
  @Singleton
  @Named(ModuleNames.TwHINANNServiceClient)
  def providesTwHINANNServiceClient(
    serviceIdentifier: ServiceIdentifier,
    statsReceiver: StatsReceiver,
  ): ANNQdrantGRPCClient = {
    val dest = s"/s/ml-serving/twhin-serving-qdrant"

    new ANNQdrantGRPCClient(
      new ANNQdrantService(
        dest,
        Some(serviceIdentifier)
      ),
      statsReceiver
    )
  }
}
