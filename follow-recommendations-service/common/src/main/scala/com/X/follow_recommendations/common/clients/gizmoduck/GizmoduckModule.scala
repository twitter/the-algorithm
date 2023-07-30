package com.X.follow_recommendations.common.clients.gizmoduck

import com.google.inject.Provides
import com.X.finatra.mtls.thriftmux.modules.MtlsClient
import com.X.follow_recommendations.common.clients.common.BaseClientModule
import com.X.gizmoduck.thriftscala.QueryFields
import com.X.gizmoduck.thriftscala.UserService
import com.X.stitch.gizmoduck.Gizmoduck
import javax.inject.Singleton

object GizmoduckModule extends BaseClientModule[UserService.MethodPerEndpoint] with MtlsClient {
  override val label = "gizmoduck"
  override val dest = "/s/gizmoduck/gizmoduck"

  @Provides
  @Singleton
  def provideExtraGizmoduckQueryFields: Set[QueryFields] = Set.empty

  @Provides
  @Singleton
  def providesStitchClient(futureIface: UserService.MethodPerEndpoint): Gizmoduck = {
    Gizmoduck(futureIface)
  }
}
