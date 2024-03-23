package com.ExTwitter.product_mixer.component_library.module

import com.google.inject.Provides
import com.ExTwitter.conversions.DurationOps._
import com.ExTwitter.finagle.thriftmux.MethodBuilder
import com.ExTwitter.finatra.mtls.thriftmux.modules.MtlsClient
import com.ExTwitter.inject.Injector
import com.ExTwitter.inject.thrift.modules.ThriftMethodBuilderClientModule
import com.ExTwitter.socialgraph.thriftscala.SocialGraphService
import com.ExTwitter.stitch.socialgraph.SocialGraph
import javax.inject.Singleton

object SocialGraphServiceModule
    extends ThriftMethodBuilderClientModule[
      SocialGraphService.ServicePerEndpoint,
      SocialGraphService.MethodPerEndpoint
    ]
    with MtlsClient {

  val label: String = "socialgraphservice"
  val dest: String = "/s/socialgraph/socialgraph"

  @Singleton
  @Provides
  def provideGizmoduckStitchClient(
    socialGraphService: SocialGraphService.MethodPerEndpoint
  ): SocialGraph =
    new SocialGraph(socialGraphService)

  override protected def configureMethodBuilder(
    injector: Injector,
    methodBuilder: MethodBuilder
  ): MethodBuilder = {
    methodBuilder.withTimeoutPerRequest(400.millis)
  }
}
