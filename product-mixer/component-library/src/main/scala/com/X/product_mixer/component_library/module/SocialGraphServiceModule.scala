package com.X.product_mixer.component_library.module

import com.google.inject.Provides
import com.X.conversions.DurationOps._
import com.X.finagle.thriftmux.MethodBuilder
import com.X.finatra.mtls.thriftmux.modules.MtlsClient
import com.X.inject.Injector
import com.X.inject.thrift.modules.ThriftMethodBuilderClientModule
import com.X.socialgraph.thriftscala.SocialGraphService
import com.X.stitch.socialgraph.SocialGraph
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
