package com.twitter.product_mixer.shared_library.http_client

import com.twitter.finagle.Http
import com.twitter.finagle.mtls.authentication.ServiceIdentifier
import com.twitter.finagle.mtls.client.MtlsStackClient._
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.util.Duration

object FinagleHttpClientBuilder {

  /**
   * Build a Finagle HTTP client with S2S Auth / Mutual TLS
   *
   * @param requestTimeout     HTTP client request timeout
   * @param connectTimeout     HTTP client transport connect timeout
   * @param acquisitionTimeout HTTP client session acquisition timeout
   * @param serviceIdentifier  Service ID used to S2S Auth
   * @param statsReceiver      Stats
   *
   * @return Finagle HTTP Client with S2S Auth / Mutual TLS
   */
  def buildFinagleHttpClientMutualTls(
    requestTimeout: Duration,
    connectTimeout: Duration,
    acquisitionTimeout: Duration,
    serviceIdentifier: ServiceIdentifier,
    statsReceiver: StatsReceiver
  ): Http.Client =
    buildFinagleHttpClient(
      requestTimeout = requestTimeout,
      connectTimeout = connectTimeout,
      acquisitionTimeout = acquisitionTimeout,
      statsReceiver = statsReceiver
    ).withMutualTls(serviceIdentifier)

  /**
   * Build a Finagle HTTP client
   *
   * @param requestTimeout     HTTP client request timeout
   * @param connectTimeout     HTTP client transport connect timeout
   * @param acquisitionTimeout HTTP client session acquisition timeout
   * @param statsReceiver      stats
   *
   * @return Finagle HTTP Client
   */
  def buildFinagleHttpClient(
    requestTimeout: Duration,
    connectTimeout: Duration,
    acquisitionTimeout: Duration,
    statsReceiver: StatsReceiver,
  ): Http.Client =
    Http.client
      .withStatsReceiver(statsReceiver)
      .withRequestTimeout(requestTimeout)
      .withTransport.connectTimeout(connectTimeout)
      .withSession.acquisitionTimeout(acquisitionTimeout)
}
