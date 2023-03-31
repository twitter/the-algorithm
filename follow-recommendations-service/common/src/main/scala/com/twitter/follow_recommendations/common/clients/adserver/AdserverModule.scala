package com.twitter.follow_recommendations.common.clients.adserver

import com.twitter.adserver.thriftscala.NewAdServer
import com.twitter.conversions.DurationOps._
import com.twitter.finagle.ThriftMux
import com.twitter.finatra.mtls.thriftmux.modules.MtlsClient
import com.twitter.follow_recommendations.common.clients.common.BaseClientModule

object AdserverModule extends BaseClientModule[NewAdServer.MethodPerEndpoint] with MtlsClient {
  override val label = "adserver"
  override val dest = "/s/ads/adserver"

  override def configureThriftMuxClient(client: ThriftMux.Client): ThriftMux.Client =
    client.withRequestTimeout(500.millis)
}
