package com.X.representation_manager.modules

import com.google.inject.Provides
import javax.inject.Singleton
import com.X.inject.XModule
import com.X.decider.Decider
import com.X.finagle.mtls.authentication.ServiceIdentifier
import com.X.representation_manager.common.RepresentationManagerDecider
import com.X.storage.client.manhattan.kv.ManhattanKVClientMtlsParams

object StoreModule extends XModule {
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
