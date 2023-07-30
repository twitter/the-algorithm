package com.X.simclustersann.modules

import com.google.inject.Provides
import javax.inject.Singleton
import com.X.inject.XModule
import com.X.finagle.mtls.authentication.ServiceIdentifier
import com.X.strato.client.Client
import com.X.strato.client.Strato

object StratoClientProviderModule extends XModule {

  @Singleton
  @Provides
  def providesCache(
    serviceIdentifier: ServiceIdentifier,
  ): Client = Strato.client
    .withMutualTls(serviceIdentifier)
    .build()

}
