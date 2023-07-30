package com.X.product_mixer.component_library.module.http

import com.google.inject.Provides
import com.X.finagle.stats.StatsReceiver
import com.X.finatra.httpclient.HttpClient
import com.X.inject.XModule
import com.X.inject.annotations.Flag
import com.X.product_mixer.component_library.module.http.FinagleHttpClientModule.HttpClientAcquisitionTimeout
import com.X.product_mixer.component_library.module.http.FinagleHttpClientModule.HttpClientConnectTimeout
import com.X.product_mixer.component_library.module.http.FinagleHttpClientModule.HttpClientRequestTimeout
import com.X.product_mixer.component_library.module.http.FinagleHttpClientWithProxyModule.HttpClientWithProxyRemoteHost
import com.X.product_mixer.component_library.module.http.FinagleHttpClientWithProxyModule.HttpClientWithProxyRemotePort
import com.X.product_mixer.component_library.module.http.FinagleHttpClientWithProxyModule.HttpClientWithProxyXHost
import com.X.product_mixer.component_library.module.http.FinagleHttpClientWithProxyModule.HttpClientWithProxyXPort
import com.X.product_mixer.core.module.product_mixer_flags.ProductMixerFlagModule.ServiceLocal
import com.X.product_mixer.shared_library.http_client.FinagleHttpClientWithProxyBuilder.buildFinagleHttpClientWithProxy
import com.X.product_mixer.shared_library.http_client.FinagleHttpClientWithProxyBuilder.buildFinagleHttpServiceWithProxy
import com.X.product_mixer.shared_library.http_client.HttpHostPort
import com.X.util.Duration
import com.X.util.jackson.ScalaObjectMapper
import javax.inject.Named
import javax.inject.Singleton

object FinatraHttpClientWithProxyModule extends XModule {

  final val FinatraHttpClientWithProxy = "FinagleHttpClientWithProxy"

  /**
   * Build a Finatra HTTP client with Egress Proxy support for a host. The Finatra HTTP client can
   * be helpful (as opposed to the base Finagle HTTP Client), as it provides built-in JSON response
   * parsing and other convenience methods
   *
   * Note that the timeouts configured in this module are meant to be a reasonable starting point
   * only. To further tuning the settings, either override the flags or create local copy of the module.
   *
   * @param proxyXHost       X egress proxy host
   * @param proxyXPort       X egress proxy port
   * @param proxyRemoteHost        Remote proxy host
   * @param proxyRemotePort        Remote proxy port
   * @param requestTimeout         HTTP client request timeout
   * @param connectTimeout         HTTP client transport connect timeout
   * @param acquisitionTimeout     HTTP client session acquisition timeout
   * @param isServiceLocal         Local deployment for testing
   * @param scalaObjectMapper      Object mapper used by the built-in JSON response parsing
   * @param statsReceiver          Stats
   *
   * @return Finatra HTTP client with Egress Proxy support for a host
   */
  @Provides
  @Singleton
  @Named(FinatraHttpClientWithProxy)
  def providesFinatraHttpClientWithProxy(
    @Flag(HttpClientWithProxyXHost) proxyXHost: String,
    @Flag(HttpClientWithProxyXPort) proxyXPort: Int,
    @Flag(HttpClientWithProxyRemoteHost) proxyRemoteHost: String,
    @Flag(HttpClientWithProxyRemotePort) proxyRemotePort: Int,
    @Flag(HttpClientRequestTimeout) requestTimeout: Duration,
    @Flag(HttpClientConnectTimeout) connectTimeout: Duration,
    @Flag(HttpClientAcquisitionTimeout) acquisitionTimeout: Duration,
    @Flag(ServiceLocal) isServiceLocal: Boolean,
    scalaObjectMapper: ScalaObjectMapper,
    statsReceiver: StatsReceiver
  ): HttpClient = {
    val XProxyHostPort = HttpHostPort(proxyXHost, proxyXPort)
    val proxyRemoteHostPort = HttpHostPort(proxyRemoteHost, proxyRemotePort)

    val finagleHttpClientWithProxy =
      buildFinagleHttpClientWithProxy(
        XProxyHostPort = XProxyHostPort,
        remoteProxyHostPort = proxyRemoteHostPort,
        requestTimeout = requestTimeout,
        connectTimeout = connectTimeout,
        acquisitionTimeout = acquisitionTimeout,
        statsReceiver = statsReceiver
      )

    val finagleHttpServiceWithProxy =
      buildFinagleHttpServiceWithProxy(
        finagleHttpClientWithProxy = finagleHttpClientWithProxy,
        XProxyHostPort = XProxyHostPort
      )

    new HttpClient(
      hostname = XProxyHostPort.host,
      httpService = finagleHttpServiceWithProxy,
      mapper = scalaObjectMapper
    )
  }
}
