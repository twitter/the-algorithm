package com.ExTwitter.follow_recommendations.common.clients.geoduck

import com.ExTwitter.finatra.mtls.thriftmux.modules.MtlsClient
import com.ExTwitter.follow_recommendations.common.clients.common.BaseClientModule
import com.ExTwitter.geoduck.thriftscala.LocationService

object LocationServiceModule
    extends BaseClientModule[LocationService.MethodPerEndpoint]
    with MtlsClient {
  override val label = "geoduck_locationservice"
  override val dest = "/s/geo/geoduck_locationservice"
}
