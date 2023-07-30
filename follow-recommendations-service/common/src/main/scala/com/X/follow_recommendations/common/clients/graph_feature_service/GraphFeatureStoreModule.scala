package com.X.follow_recommendations.common.clients.graph_feature_service

import com.X.finatra.mtls.thriftmux.modules.MtlsClient
import com.X.follow_recommendations.common.clients.common.BaseClientModule
import com.X.graph_feature_service.thriftscala.{Server => GraphFeatureService}

object GraphFeatureStoreModule
    extends BaseClientModule[GraphFeatureService.MethodPerEndpoint]
    with MtlsClient {
  override val label = "graph_feature_service"
  override val dest = "/s/cassowary/graph_feature_service-server"
}
