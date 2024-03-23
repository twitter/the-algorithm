package com.ExTwitter.follow_recommendations.common.clients.adserver

import com.ExTwitter.adserver.thriftscala.NewAdServer
import com.ExTwitter.conversions.DurationOps._
import com.ExTwitter.finagle.ThriftMux
import com.ExTwitter.finatra.mtls.thriftmux.modules.MtlsClient
import com.ExTwitter.follow_recommendations.common.clients.common.BaseClientModule

object AdserverModule extends BaseClientModule[NewAdServer.MethodPerEndpoint] with MtlsClient {
  override val label = "adserver"
  override val dest = "/s/ads/adserver"

  override def configureThriftMuxClient(client: ThriftMux.Client): ThriftMux.Client =
    client.withRequestTimeout(500.millis)
}
