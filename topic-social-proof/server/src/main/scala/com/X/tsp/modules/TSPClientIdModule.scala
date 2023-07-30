package com.X.tsp.modules

import com.google.inject.Provides
import com.X.finagle.thrift.ClientId
import com.X.inject.XModule
import javax.inject.Singleton

object TSPClientIdModule extends XModule {
  private val clientIdFlag = flag("thrift.clientId", "topic-social-proof.prod", "Thrift client id")

  @Provides
  @Singleton
  def providesClientId: ClientId = ClientId(clientIdFlag())
}
