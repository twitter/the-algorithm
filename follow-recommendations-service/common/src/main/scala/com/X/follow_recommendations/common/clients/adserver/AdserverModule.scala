package com.X.follow_recommendations.common.clients.adserver

import com.X.adserver.thriftscala.NewAdServer
import com.X.conversions.DurationOps._
import com.X.finagle.ThriftMux
import com.X.finatra.mtls.thriftmux.modules.MtlsClient
import com.X.follow_recommendations.common.clients.common.BaseClientModule

object AdserverModule extends BaseClientModule[NewAdServer.MethodPerEndpoint] with MtlsClient {
  override val label = "adserver"
  override val dest = "/s/ads/adserver"

  override def configureThriftMuxClient(client: ThriftMux.Client): ThriftMux.Client =
    client.withRequestTimeout(500.millis)
}
