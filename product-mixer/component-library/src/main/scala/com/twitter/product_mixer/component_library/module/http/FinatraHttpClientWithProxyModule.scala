package com.ExTwitter.product_mixer.component_library.module.http

import com.google.inject.Provides
import com.ExTwitter.finagle.stats.StatsReceiver
import com.ExTwitter.finatra.httpclient.HttpClient
import com.ExTwitter.inject.ExTwitterModule
import com.ExTwitter.inject.annotations.Flag
import com.ExTwitter.product_mixer.component_library.module.http.FinagleHttpClientModule.HttpClientAcquisitionTimeout
import com.ExTwitter.product_mixer.component_library.module.http.FinagleHttpClientModule.HttpClientConnectTimeout
import com.ExTwitter.product_mixer.component_library.module.http.FinagleHttpClientModule.HttpClientRequestTimeout
import com.ExTwitter.product_mixer.component_library.module.http.FinagleHttpClientWithProxyModule.HttpClientWithProxyRemoteHost
import com.ExTwitter.product_mixer.component_library.module.http.FinagleHttpClientWithProxyModule.HttpClientWithProxyRemotePort
import com.ExTwitter.product_mixer.component_library.module.http.FinagleHttpClientWithProxyModule.HttpClientWithProxyExTwitterHost
import com.ExTwitter.product_mixer.component_library.module.http.FinagleHttpClientWithProxyModule.HttpClientWithProxyExTwitterPort
import com.ExTwitter.product_mixer.core.module.product_mixer_flags.ProductMixerFlagModule.ServiceLocal
import com.ExTwitter.product_mixer.shared_library.http_client.FinagleHttpClientWithProxyBuilder.buildFinagleHttpClientWithProxy
import com.ExTwitter.product_mixer.shared_library.http_client.FinagleHttpClientWithProxyBuilder.buildFinagleHttpServiceWithProxy
import com.ExTwitter.product_mixer.shared_library.http_client.HttpHostPort
import com.ExTwitter.util.Duration
import com.ExTwitter.util.jackson.ScalaObjectMapper
import javax.inject.Named
import javax.inject.Singleton

object FinatraHttpClientWithProxyModule extends ExTwitterModule {

  final val FinatraHttpClientWithProxy = "FinagleHttpClientWithProxy"

  /**
   * Build a Finatra HTTP client with Egress Proxy support for a host. The Finatra HTTP client can
   * be helpful (as opposed to the base Finagle HTTP Client), as it provides built-in JSON response
   * parsing and other convenience methods
   *
   * Note that the timeouts configured in this module are meant to be a reasonable starting point
   * only. To further tuning the settings, either override the flags or create local copy of the module.
   *
   * @param proxyExTwitterHost       ExTwitter egress proxy host
   * @param proxyExTwitterPort       ExTwitter egress proxy port
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
    @Flag(HttpClientWithProxyExTwitterHost) proxyExTwitterHost: String,
    @Flag(HttpClientWithProxyExTwitterPort) proxyExTwitterPort: Int,
    @Flag(HttpClientWithProxyRemoteHost) proxyRemoteHost: String,
    @Flag(HttpClientWithProxyRemotePort) proxyRemotePort: Int,
    @Flag(HttpClientRequestTimeout) requestTimeout: Duration,
    @Flag(HttpClientConnectTimeout) connectTimeout: Duration,
    @Flag(HttpClientAcquisitionTimeout) acquisitionTimeout: Duration,
    @Flag(ServiceLocal) isServiceLocal: Boolean,
    scalaObjectMapper: ScalaObjectMapper,
    statsReceiver: StatsReceiver
  ): HttpClient = {
    val ExTwitterProxyHostPort = HttpHostPort(proxyExTwitterHost, proxyExTwitterPort)
    val proxyRemoteHostPort = HttpHostPort(proxyRemoteHost, proxyRemotePort)

    val finagleHttpClientWithProxy =
      buildFinagleHttpClientWithProxy(
        ExTwitterProxyHostPort = ExTwitterProxyHostPort,
        remoteProxyHostPort = proxyRemoteHostPort,
        requestTimeout = requestTimeout,
        connectTimeout = connectTimeout,
        acquisitionTimeout = acquisitionTimeout,
        statsReceiver = statsReceiver
      )

    val finagleHttpServiceWithProxy =
      buildFinagleHttpServiceWithProxy(
        finagleHttpClientWithProxy = finagleHttpClientWithProxy,
        ExTwitterProxyHostPort = ExTwitterProxyHostPort
      )

    new HttpClient(
      hostname = ExTwitterProxyHostPort.host,
      httpService = finagleHttpServiceWithProxy,
      mapper = scalaObjectMapper
    )
  }
}
