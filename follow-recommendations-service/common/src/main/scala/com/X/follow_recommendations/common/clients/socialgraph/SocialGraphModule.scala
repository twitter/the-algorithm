package com.X.follow_recommendations.common.clients.socialgraph

import com.google.inject.Provides
import com.X.finagle.ThriftMux
import com.X.finatra.mtls.thriftmux.modules.MtlsClient
import com.X.follow_recommendations.common.clients.common.BaseClientModule
import com.X.socialgraph.thriftscala.SocialGraphService
import com.X.stitch.socialgraph.SocialGraph
import javax.inject.Singleton

object SocialGraphModule
    extends BaseClientModule[SocialGraphService.MethodPerEndpoint]
    with MtlsClient {
  override val label = "social-graph-service"
  override val dest = "/s/socialgraph/socialgraph"

  override def configureThriftMuxClient(client: ThriftMux.Client): ThriftMux.Client =
    client.withSessionQualifier.noFailFast

  @Provides
  @Singleton
  def providesStitchClient(futureIface: SocialGraphService.MethodPerEndpoint): SocialGraph = {
    SocialGraph(futureIface)
  }
}
