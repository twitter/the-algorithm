package com.X.product_mixer.shared_library.http_client

import com.X.finagle.Http
import com.X.finagle.Service
import com.X.finagle.client.Transporter
import com.X.finagle.http.ProxyCredentials
import com.X.finagle.http.Request
import com.X.finagle.http.Response
import com.X.finagle.stats.StatsReceiver
import com.X.product_mixer.shared_library.http_client.FinagleHttpClientBuilder.buildFinagleHttpClient
import com.X.util.Duration

object FinagleHttpClientWithProxyBuilder {

  /**
   * Build a Finagle HTTP client with Egress Proxy support using Credentials
   *
   * @param XProxyHostPort    X egress proxy host port
   * @param remoteProxyHostPort     Remote proxy host port
   * @param requestTimeout          HTTP client request timeout
   * @param connectTimeout          HTTP client transport connect timeout
   * @param acquisitionTimeout      HTTP client session acquisition timeout
   * @param proxyCredentials        Proxy credentials
   * @param statsReceiver           Stats
   *
   * @return Finagle HTTP client with Egress Proxy support using Credentials
   */
  def buildFinagleHttpClientWithCredentialProxy(
    XProxyHostPort: HttpHostPort,
    remoteProxyHostPort: HttpHostPort,
    requestTimeout: Duration,
    connectTimeout: Duration,
    acquisitionTimeout: Duration,
    proxyCredentials: ProxyCredentials,
    statsReceiver: StatsReceiver,
  ): Http.Client = {
    val httpClient = buildFinagleHttpClient(
      requestTimeout = requestTimeout,
      connectTimeout = connectTimeout,
      acquisitionTimeout = acquisitionTimeout,
      statsReceiver = statsReceiver
    )

    httpClient.withTransport
      .httpProxyTo(
        host = remoteProxyHostPort.toString,
        credentials = Transporter.Credentials(proxyCredentials.username, proxyCredentials.password))
      .withTls(remoteProxyHostPort.host)
  }

  /**
   * Build a Finagle HTTP client with Egress Proxy support
   *
   * @param XProxyHostPort   X egress proxy host port
   * @param remoteProxyHostPort    Remote proxy host port
   * @param requestTimeout         HTTP client request timeout
   * @param connectTimeout         HTTP client transport connect timeout
   * @param acquisitionTimeout     HTTP client session acquisition timeout
   * @param statsReceiver          Stats
   *
   * @return Finagle HTTP client with Egress Proxy support
   */
  def buildFinagleHttpClientWithProxy(
    XProxyHostPort: HttpHostPort,
    remoteProxyHostPort: HttpHostPort,
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
      .httpProxyTo(remoteProxyHostPort.toString)
      .withTls(remoteProxyHostPort.host)
  }

  /**
   * Build a Finagle HTTP service with Egress Proxy support
   *
   * @param finagleHttpClientWithProxy Finagle HTTP client from which to build the service
   * @param XProxyHostPort       X egress proxy host port
   *
   * @return Finagle HTTP service with Egress Proxy support
   */
  def buildFinagleHttpServiceWithProxy(
    finagleHttpClientWithProxy: Http.Client,
    XProxyHostPort: HttpHostPort
  ): Service[Request, Response] = {
    finagleHttpClientWithProxy.newService(XProxyHostPort.toString)
  }
}
