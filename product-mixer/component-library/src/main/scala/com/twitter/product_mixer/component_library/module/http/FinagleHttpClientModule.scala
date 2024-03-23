package com.ExTwitter.product_mixer.component_library.module.http

import com.google.inject.Provides
import com.ExTwitter.conversions.DurationOps._
import com.ExTwitter.finagle.Http
import com.ExTwitter.finagle.mtls.authentication.ServiceIdentifier
import com.ExTwitter.finagle.stats.StatsReceiver
import com.ExTwitter.inject.ExTwitterModule
import com.ExTwitter.inject.annotations.Flag
import com.ExTwitter.product_mixer.shared_library.http_client.FinagleHttpClientBuilder.buildFinagleHttpClientMutualTls
import com.ExTwitter.util.Duration
import javax.inject.Named
import javax.inject.Singleton

object FinagleHttpClientModule extends ExTwitterModule {

  final val HttpClientRequestTimeout = "http_client.request_timeout"
  final val HttpClientConnectTimeout = "http_client.connect_timeout"
  final val HttpClientAcquisitionTimeout = "http_client.acquisition_timeout"

  flag[Duration](
    name = HttpClientRequestTimeout,
    default = 200.milliseconds,
    help = "HTTP client request timeout")

  flag[Duration](
    name = HttpClientConnectTimeout,
    default = 500.milliseconds,
    help = "HTTP client transport connect timeout")

  flag[Duration](
    name = HttpClientAcquisitionTimeout,
    default = 500.milliseconds,
    help = "HTTP client session acquisition timeout")

  final val FinagleHttpClientModule = "FinagleHttpClientModule"

  /**
   * Provides a Finagle HTTP client with S2S Auth / Mutual TLS
   *
   * Note that the timeouts configured in this module are meant to be a reasonable starting point
   * only. To further tuning the settings, either override the flags or create local copy of the module.
   *
   * @param requestTimeout     HTTP client request timeout
   * @param connectTimeout     HTTP client transport connect timeout
   * @param acquisitionTimeout HTTP client session acquisition timeout
   * @param serviceIdentifier  Service ID used to S2S Auth
   * @param statsReceiver      Stats
   *
   * @return Finagle HTTP Client with S2S Auth / Mutual TLS
   */
  @Provides
  @Singleton
  @Named(FinagleHttpClientModule)
  def providesFinagleHttpClient(
    @Flag(HttpClientRequestTimeout) requestTimeout: Duration,
    @Flag(HttpClientConnectTimeout) connectTimeout: Duration,
    @Flag(HttpClientAcquisitionTimeout) acquisitionTimeout: Duration,
    serviceIdentifier: ServiceIdentifier,
    statsReceiver: StatsReceiver
  ): Http.Client =
    buildFinagleHttpClientMutualTls(
      requestTimeout = requestTimeout,
      connectTimeout = connectTimeout,
      acquisitionTimeout = acquisitionTimeout,
      serviceIdentifier = serviceIdentifier,
      statsReceiver = statsReceiver
    )
}
