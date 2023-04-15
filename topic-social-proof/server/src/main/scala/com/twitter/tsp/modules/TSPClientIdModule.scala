package com.twitter.tsp.modules

import com.google.inject.Provides
import com.twitter.finagle.thrift.ClientId
import com.twitter.inject.TwitterModule
import javax.inject.Singleton

object TSPClientIdModule extends TwitterModule {
  private val clientIdFlag = flag("thrift.clientId", "topic-social-proof.prod", "Thrift client id")

  @Provides
  @Singleton
  def providesClientId: ClientId = ClientId(clientIdFlag())
}
