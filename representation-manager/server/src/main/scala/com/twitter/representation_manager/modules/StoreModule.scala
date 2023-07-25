package com.twitter.representation_manager.modules

import com.google.inject.Provides
import javax.inject.Singleton
import com.twitter.inject.TwitterModule
import com.twitter.decider.Decider
import com.twitter.finagle.mtls.authentication.ServiceIdentifier
import com.twitter.representation_manager.common.RepresentationManagerDecider
import com.twitter.storage.client.manhattan.kv.ManhattanKVClientMtlsParams

object StoreModule extends TwitterModule {
  @Singleton
  @Provides
  def providesMhMtlsParams(
    serviceIdentifier: ServiceIdentifier
  ): ManhattanKVClientMtlsParams = ManhattanKVClientMtlsParams(serviceIdentifier)

  @Singleton
  @Provides
  def providesRmsDecider(
    decider: Decider
  ): RepresentationManagerDecider = RepresentationManagerDecider(decider)

}
