package com.twitter.tweet_mixer.module.thrift_client

import com.twitter.finagle.ThriftMux
import com.twitter.finagle.thrift.ClientId
import com.twitter.geoduck.service.common.clientmodules.HydrationThriftClientModule
import com.twitter.inject.Injector

object GeoduckHydrationClientModule extends HydrationThriftClientModule {
  override protected def configureThriftMuxClient(
    injector: Injector,
    client: ThriftMux.Client
  ): ThriftMux.Client = {
    client.withClientId(ClientId("tweet-mixer"))
  }
}
