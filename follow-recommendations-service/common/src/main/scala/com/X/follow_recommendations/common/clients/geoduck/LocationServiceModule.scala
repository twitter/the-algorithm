package com.X.follow_recommendations.common.clients.geoduck

import com.X.finatra.mtls.thriftmux.modules.MtlsClient
import com.X.follow_recommendations.common.clients.common.BaseClientModule
import com.X.geoduck.thriftscala.LocationService

object LocationServiceModule
    extends BaseClientModule[LocationService.MethodPerEndpoint]
    with MtlsClient {
  override val label = "geoduck_locationservice"
  override val dest = "/s/geo/geoduck_locationservice"
}
