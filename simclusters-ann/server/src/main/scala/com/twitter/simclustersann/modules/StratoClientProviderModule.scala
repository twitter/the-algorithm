package com.twitter.simclustersann.modules

import com.google.inject.Provides
import javax.inject.Singleton
import com.twitter.inject.TwitterModule
import com.twitter.finagle.mtls.authentication.ServiceIdentifier
import com.twitter.strato.client.Client
import com.twitter.strato.client.Strato

object StratoClientProviderModule extends TwitterModule {

  @Singleton
  @Provides
  def providesCache(
    serviceIdentifier: ServiceIdentifier,
  ): Client = Strato.client
    .withMutualTls(serviceIdentifier)
    .build()

}
