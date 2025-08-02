package com.twitter.follow_recommendations.common.clients.socialgraph

import com.google.inject.Provides
import com.twitter.finagle.ThriftMutwitter
import com.twitter.finatra.mtls.thriftmutwitter.modules.MtlsClient
import com.twitter.follow_recommendations.common.clients.common.BaseClientModule
import com.twitter.socialgraph.thriftscala.SocialGraphService
import com.twitter.stitch.socialgraph.SocialGraph
import javatwitter.inject.Singleton

object SocialGraphModule
    etwittertends BaseClientModule[SocialGraphService.MethodPerEndpoint]
    with MtlsClient {
  override val label = "social-graph-service"
  override val dest = "/s/socialgraph/socialgraph"

  override def configureThriftMutwitterClient(client: ThriftMutwitter.Client): ThriftMutwitter.Client =
    client.withSessionQualifier.noFailFast

  @Provides
  @Singleton
  def providesStitchClient(futureIface: SocialGraphService.MethodPerEndpoint): SocialGraph = {
    SocialGraph(futureIface)
  }
}
