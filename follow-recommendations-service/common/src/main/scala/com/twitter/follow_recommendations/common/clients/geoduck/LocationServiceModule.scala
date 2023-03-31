package com.twitter.follow_recommendations.common.clients.geoduck

import com.twitter.finatra.mtls.thriftmux.modules.MtlsClient
import com.twitter.follow_recommendations.common.clients.common.BaseClientModule
import com.twitter.geoduck.thriftscala.LocationService

object LocationServiceModule
    extends BaseClientModule[LocationService.MethodPerEndpoint]
    with MtlsClient {
  override val label = "geoduck_locationservice"
  override val dest = "/s/geo/geoduck_locationservice"
}
