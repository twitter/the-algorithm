package com.twitter.product_mixer.component_library.module.http

import com.google.inject.Provides
import com.twitter.finagle.http.ProxyCredentials
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.finatra.httpclient.HttpClient
import com.twitter.inject.TwitterModule
import com.twitter.inject.annotations.Flag
import com.twitter.product_mixer.component_library.module.http.FinagleHttpClientModule.HttpClientAcquisitionTimeout
import com.twitter.product_mixer.component_library.module.http.FinagleHttpClientModule.HttpClientConnectTimeout
import com.twitter.product_mixer.component_library.module.http.FinagleHttpClientModule.HttpClientRequestTimeout
import com.twitter.product_mixer.component_library.module.http.FinagleHttpClientWithProxyModule.HttpClientWithProxyRemoteHost
import com.twitter.product_mixer.component_library.module.http.FinagleHttpClientWithProxyModule.HttpClientWithProxyRemotePort
import com.twitter.product_mixer.component_library.module.http.FinagleHttpClientWithProxyModule.HttpClientWithProxyTwitterHost
import com.twitter.product_mixer.component_library.module.http.FinagleHttpClientWithProxyModule.HttpClientWithProxyTwitterPort
import com.twitter.product_mixer.core.module.product_mixer_flags.ProductMixerFlagModule.ServiceLocal
import com.twitter.product_mixer.shared_library.http_client.FinagleHttpClientWithProxyBuilder.buildFinagleHttpClientWithCredentialProxy
import com.twitter.product_mixer.shared_library.http_client.FinagleHttpClientWithProxyBuilder.buildFinagleHttpServiceWithProxy
import com.twitter.product_mixer.shared_library.http_client.HttpHostPort
import com.twitter.util.Duration
import com.twitter.util.jackson.ScalaObjectMapper
import javax.inject.Named
import javax.inject.Singleton

object FinatraHttpClientWithCredentialProxyModule extends TwitterModule {

  final val FinatraHttpClientWithCredentialProxy = "FinagleHttpClientWithCredentialProxy"

  /**
   * Build a Finatra HTTP client with Egress Proxy support with Credentials for a host. The Finatra
   * HTTP client can be helpful (as opposed to the base Finagle HTTP Client), as it provides
   * built-in JSON response zparsing and other convenience methods
   *
   * Note that the timeouts configured in this module are meant to be a reasonable starting point
   * only. To further tuning the settings, either override the flags or create local copy of the module.
   *
   * @param proxyTwitterHost       Twitter egress proxy host
   * @param proxyTwitterPort       Twitter egress proxy port
   * @param proxyRemoteHost        Remote proxy host
   * @param proxyRemotePort        Remote proxy port
   * @param requestTimeout         HTTP client request timeout
   * @param connectTimeout         HTTP client transport connect timeout
   * @param acquisitionTimeout     HTTP client session acquisition timeout
   * @param isServiceLocal         Local deployment for testing
   * @param proxyCredentials       Proxy credentials
   * @param scalaObjectMapper      Object mapper used by the built-in JSON response parsing
   * @param statsReceiver          Stats
   *
   * @return Finatra HTTP client with Egress Proxy support for a host
   */
  @Provides
  @Singleton
  @Named(FinatraHttpClientWithCredentialProxy)
  def providesFinatraHttpClientWithCredentialProxy(
    @Flag(HttpClientWithProxyTwitterHost) proxyTwitterHost: String,
    @Flag(HttpClientWithProxyTwitterPort) proxyTwitterPort: Int,
    @Flag(HttpClientWithProxyRemoteHost) proxyRemoteHost: String,
    @Flag(HttpClientWithProxyRemotePort) proxyRemotePort: Int,
    @Flag(HttpClientRequestTimeout) requestTimeout: Duration,
    @Flag(HttpClientConnectTimeout) connectTimeout: Duration,
    @Flag(HttpClientAcquisitionTimeout) acquisitionTimeout: Duration,
    @Flag(ServiceLocal) isServiceLocal: Boolean,
    proxyCredentials: ProxyCredentials,
    scalaObjectMapper: ScalaObjectMapper,
    statsReceiver: StatsReceiver
  ): HttpClient = {
    val twitterProxyHostPort = HttpHostPort(proxyTwitterHost, proxyTwitterPort)
    val proxyRemoteHostPort = HttpHostPort(proxyRemoteHost, proxyRemotePort)

    val finagleHttpClientWithCredentialProxy =
      buildFinagleHttpClientWithCredentialProxy(
        twitterProxyHostPort = twitterProxyHostPort,
        remoteProxyHostPort = proxyRemoteHostPort,
        requestTimeout = requestTimeout,
        connectTimeout = connectTimeout,
        acquisitionTimeout = acquisitionTimeout,
        proxyCredentials = proxyCredentials,
        statsReceiver = statsReceiver
      )

    val finagleHttpServiceWithCredentialProxy =
      buildFinagleHttpServiceWithProxy(
        finagleHttpClientWithProxy = finagleHttpClientWithCredentialProxy,
        twitterProxyHostPort = twitterProxyHostPort
      )

    new HttpClient(
      hostname = twitterProxyHostPort.host,
      httpService = finagleHttpServiceWithCredentialProxy,
      mapper = scalaObjectMapper
    )
  }
}
