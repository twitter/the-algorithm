package com.X.home_mixer.module

import com.google.inject.Provides
import com.X.conversions.DurationOps._
import com.X.finagle.mtls.authentication.ServiceIdentifier
import com.X.finagle.thrift.ClientId
import com.X.finagle.thriftmux.MethodBuilder
import com.X.finatra.mtls.thriftmux.modules.MtlsClient
import com.X.inject.Injector
import com.X.inject.annotations.Flags
import com.X.inject.thrift.modules.ThriftMethodBuilderClientModule
import com.X.stitch.tweetypie.TweetyPie
import com.X.tweetypie.thriftscala.TweetService
import com.X.util.Duration
import javax.inject.Singleton

/**
 * Idempotent Tweetypie Thrift and Stitch client.
 */
object TweetypieClientModule
    extends ThriftMethodBuilderClientModule[
      TweetService.ServicePerEndpoint,
      TweetService.MethodPerEndpoint
    ]
    with MtlsClient {

  private val TimeoutRequest = "tweetypie.timeout_request"
  private val TimeoutTotal = "tweetypie.timeout_total"

  flag[Duration](TimeoutRequest, 1000.millis, "Timeout per request")
  flag[Duration](TimeoutTotal, 1000.millis, "Total timeout")

  override val label: String = "tweetypie"
  override val dest: String = "/s/tweetypie/tweetypie"

  @Singleton
  @Provides
  def providesTweetypieStitchClient(tweetService: TweetService.MethodPerEndpoint): TweetyPie =
    new TweetyPie(tweetService)

  /**
   * TweetyPie client id must be in the form of {service.env} or it will not be treated as an
   * unauthorized client
   */
  override protected def clientId(injector: Injector): ClientId = {
    val serviceIdentifier = injector.instance[ServiceIdentifier]
    ClientId(s"${serviceIdentifier.service}.${serviceIdentifier.environment}")
  }

  override protected def configureMethodBuilder(
    injector: Injector,
    methodBuilder: MethodBuilder
  ): MethodBuilder = {
    val timeoutRequest = injector.instance[Duration](Flags.named(TimeoutRequest))
    val timeoutTotal = injector.instance[Duration](Flags.named(TimeoutTotal))

    methodBuilder
      .withTimeoutPerRequest(timeoutRequest)
      .withTimeoutTotal(timeoutTotal)
  }

  override protected def sessionAcquisitionTimeout: Duration = 500.millis
}
