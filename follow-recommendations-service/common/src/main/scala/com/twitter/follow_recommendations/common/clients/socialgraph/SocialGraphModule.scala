package com.ExTwitter.follow_recommendations.common.clients.socialgraph

import com.google.inject.Provides
import com.ExTwitter.finagle.ThriftMux
import com.ExTwitter.finatra.mtls.thriftmux.modules.MtlsClient
import com.ExTwitter.follow_recommendations.common.clients.common.BaseClientModule
import com.ExTwitter.socialgraph.thriftscala.SocialGraphService
import com.ExTwitter.stitch.socialgraph.SocialGraph
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
