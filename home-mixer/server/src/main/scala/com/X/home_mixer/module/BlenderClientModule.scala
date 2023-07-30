package com.X.home_mixer.module

import com.google.inject.Provides
import com.X.conversions.DurationOps._
import com.X.finagle.mtls.authentication.ServiceIdentifier
import com.X.finagle.stats.StatsReceiver
import com.X.finagle.thrift.ClientId
import com.X.inject.XModule
import com.X.product_mixer.shared_library.thrift_client.FinagleThriftClientBuilder
import com.X.product_mixer.shared_library.thrift_client.NonIdempotent
import com.X.search.blender.thriftscala.BlenderService
import javax.inject.Singleton

object BlenderClientModule extends XModule {

  @Singleton
  @Provides
  def providesBlenderClient(
    serviceIdentifier: ServiceIdentifier,
    statsReceiver: StatsReceiver
  ): BlenderService.MethodPerEndpoint = {
    val clientId = serviceIdentifier.environment.toLowerCase match {
      case "prod" => ClientId("")
      case _ => ClientId("")
    }

    FinagleThriftClientBuilder.buildFinagleMethodPerEndpoint[
      BlenderService.ServicePerEndpoint,
      BlenderService.MethodPerEndpoint
    ](
      serviceIdentifier = serviceIdentifier,
      clientId = clientId,
      dest = "/s/blender-universal/blender",
      label = "blender",
      statsReceiver = statsReceiver,
      idempotency = NonIdempotent,
      timeoutPerRequest = 1000.milliseconds,
      timeoutTotal = 1000.milliseconds,
    )
  }
}
