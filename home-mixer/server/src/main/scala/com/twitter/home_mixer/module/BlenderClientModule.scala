package com.twitter.home_mixer.module

import com.google.inject.Provides
import com.twitter.conversions.DurationOps._
import com.twitter.finagle.mtls.authentication.ServiceIdentifier
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.finagle.thrift.ClientId
import com.twitter.inject.TwitterModule
import com.twitter.product_mixer.shared_library.thrift_client.FinagleThriftClientBuilder
import com.twitter.product_mixer.shared_library.thrift_client.NonIdempotent
import com.twitter.search.blender.thriftscala.BlenderService
import javax.inject.Singleton

object BlenderClientModule extends TwitterModule {

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
