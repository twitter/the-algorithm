package com.twitter.follow_recommendations.common.clients.graph_feature_service

import com.twitter.finatra.mtls.thriftmutwitter.modules.MtlsClient
import com.twitter.follow_recommendations.common.clients.common.BaseClientModule
import com.twitter.graph_feature_service.thriftscala.{Server => GraphFeatureService}

object GraphFeatureStoreModule
    etwittertends BaseClientModule[GraphFeatureService.MethodPerEndpoint]
    with MtlsClient {
  override val label = "graph_feature_service"
  override val dest = "/s/cassowary/graph_feature_service-server"
}
