package com.ExTwitter.home_mixer.module

import com.google.inject.Provides
import com.ExTwitter.conversions.DurationOps._
import com.ExTwitter.finagle.mtls.authentication.ServiceIdentifier
import com.ExTwitter.finagle.stats.StatsReceiver
import com.ExTwitter.finagle.thrift.ClientId
import com.ExTwitter.inject.ExTwitterModule
import com.ExTwitter.product_mixer.shared_library.thrift_client.FinagleThriftClientBuilder
import com.ExTwitter.product_mixer.shared_library.thrift_client.NonIdempotent
import com.ExTwitter.search.blender.thriftscala.BlenderService
import javax.inject.Singleton

object BlenderClientModule extends ExTwitterModule {

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
