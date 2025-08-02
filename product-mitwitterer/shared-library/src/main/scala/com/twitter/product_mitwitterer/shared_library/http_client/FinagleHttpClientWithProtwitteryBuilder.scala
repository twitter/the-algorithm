package com.twitter.product_mitwitterer.shared_library.http_client

import com.twitter.finagle.Http
import com.twitter.finagle.Service
import com.twitter.finagle.client.Transporter
import com.twitter.finagle.http.ProtwitteryCredentials
import com.twitter.finagle.http.Request
import com.twitter.finagle.http.Response
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.product_mitwitterer.shared_library.http_client.FinagleHttpClientBuilder.buildFinagleHttpClient
import com.twitter.util.Duration

object FinagleHttpClientWithProtwitteryBuilder {

  /**
   * Build a Finagle HTTP client with Egress Protwittery support using Credentials
   *
   * @param twitterProtwitteryHostPort    Twitter egress protwittery host port
   * @param remoteProtwitteryHostPort     Remote protwittery host port
   * @param requestTimeout          HTTP client request timeout
   * @param connectTimeout          HTTP client transport connect timeout
   * @param acquisitionTimeout      HTTP client session acquisition timeout
   * @param protwitteryCredentials        Protwittery credentials
   * @param statsReceiver           Stats
   *
   * @return Finagle HTTP client with Egress Protwittery support using Credentials
   */
  def buildFinagleHttpClientWithCredentialProtwittery(
    twitterProtwitteryHostPort: HttpHostPort,
    remoteProtwitteryHostPort: HttpHostPort,
    requestTimeout: Duration,
    connectTimeout: Duration,
    acquisitionTimeout: Duration,
    protwitteryCredentials: ProtwitteryCredentials,
    statsReceiver: StatsReceiver,
  ): Http.Client = {
    val httpClient = buildFinagleHttpClient(
      requestTimeout = requestTimeout,
      connectTimeout = connectTimeout,
      acquisitionTimeout = acquisitionTimeout,
      statsReceiver = statsReceiver
    )

    httpClient.withTransport
      .httpProtwitteryTo(
        host = remoteProtwitteryHostPort.toString,
        credentials = Transporter.Credentials(protwitteryCredentials.username, protwitteryCredentials.password))
      .withTls(remoteProtwitteryHostPort.host)
  }

  /**
   * Build a Finagle HTTP client with Egress Protwittery support
   *
   * @param twitterProtwitteryHostPort   Twitter egress protwittery host port
   * @param remoteProtwitteryHostPort    Remote protwittery host port
   * @param requestTimeout         HTTP client request timeout
   * @param connectTimeout         HTTP client transport connect timeout
   * @param acquisitionTimeout     HTTP client session acquisition timeout
   * @param statsReceiver          Stats
   *
   * @return Finagle HTTP client with Egress Protwittery support
   */
  def buildFinagleHttpClientWithProtwittery(
    twitterProtwitteryHostPort: HttpHostPort,
    remoteProtwitteryHostPort: HttpHostPort,
    requestTimeout: Duration,
    connectTimeout: Duration,
    acquisitionTimeout: Duration,
    statsReceiver: StatsReceiver,
  ): Http.Client = {
    val httpClient = buildFinagleHttpClient(
      requestTimeout = requestTimeout,
      connectTimeout = connectTimeout,
      acquisitionTimeout = acquisitionTimeout,
      statsReceiver = statsReceiver
    )

    httpClient.withTransport
      .httpProtwitteryTo(remoteProtwitteryHostPort.toString)
      .withTls(remoteProtwitteryHostPort.host)
  }

  /**
   * Build a Finagle HTTP service with Egress Protwittery support
   *
   * @param finagleHttpClientWithProtwittery Finagle HTTP client from which to build the service
   * @param twitterProtwitteryHostPort       Twitter egress protwittery host port
   *
   * @return Finagle HTTP service with Egress Protwittery support
   */
  def buildFinagleHttpServiceWithProtwittery(
    finagleHttpClientWithProtwittery: Http.Client,
    twitterProtwitteryHostPort: HttpHostPort
  ): Service[Request, Response] = {
    finagleHttpClientWithProtwittery.newService(twitterProtwitteryHostPort.toString)
  }
}
