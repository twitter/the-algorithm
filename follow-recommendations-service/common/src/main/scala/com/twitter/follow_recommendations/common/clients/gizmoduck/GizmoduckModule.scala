package com.ExTwitter.follow_recommendations.common.clients.gizmoduck

import com.google.inject.Provides
import com.ExTwitter.finatra.mtls.thriftmux.modules.MtlsClient
import com.ExTwitter.follow_recommendations.common.clients.common.BaseClientModule
import com.ExTwitter.gizmoduck.thriftscala.QueryFields
import com.ExTwitter.gizmoduck.thriftscala.UserService
import com.ExTwitter.stitch.gizmoduck.Gizmoduck
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
