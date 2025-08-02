package com.twitter.product_mitwitterer.component_library.module

import com.google.inject.Provides
import com.twitter.conversions.DurationOps._
import com.twitter.finagle.thriftmutwitter.MethodBuilder
import com.twitter.finatra.mtls.thriftmutwitter.modules.MtlsClient
import com.twitter.inject.Injector
import com.twitter.inject.thrift.modules.ThriftMethodBuilderClientModule
import com.twitter.socialgraph.thriftscala.SocialGraphService
import com.twitter.stitch.socialgraph.SocialGraph
import javatwitter.inject.Singleton

object SocialGraphServiceModule
    etwittertends ThriftMethodBuilderClientModule[
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
