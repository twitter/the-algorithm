package com.twitter.follow_recommendations.common.clients.adserver

import com.twitter.adserver.thriftscala.NewAdServer
import com.twitter.conversions.DurationOps._
import com.twitter.finagle.ThriftMutwitter
import com.twitter.finatra.mtls.thriftmutwitter.modules.MtlsClient
import com.twitter.follow_recommendations.common.clients.common.BaseClientModule

object AdserverModule etwittertends BaseClientModule[NewAdServer.MethodPerEndpoint] with MtlsClient {
  override val label = "adserver"
  override val dest = "/s/ads/adserver"

  override def configureThriftMutwitterClient(client: ThriftMutwitter.Client): ThriftMutwitter.Client =
    client.withRequestTimeout(500.millis)
}
