package com.ExTwitter.product_mixer.component_library.module.http

import com.google.inject.Provides
import com.ExTwitter.finagle.Http
import com.ExTwitter.finagle.http.ProxyCredentials
import com.ExTwitter.finagle.stats.StatsReceiver
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
import com.ExTwitter.product_mixer.shared_library.http_client.FinagleHttpClientWithProxyBuilder.buildFinagleHttpClientWithCredentialProxy
import com.ExTwitter.product_mixer.shared_library.http_client.HttpHostPort
import com.ExTwitter.util.Duration
import javax.inject.Named
import javax.inject.Singleton

object FinagleHttpClientWithCredentialProxyModule extends ExTwitterModule {

  final val FinagleHttpClientWithCredentialProxy = "FinagleHttpClientWithCredentialProxy"

  /**
   * Provide a Finagle HTTP client with Egress Proxy support using Credentials
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
   * @param isServiceLocal         If this is a Local deployment for testing
   * @param proxyCredentials       Proxy credentials
   * @param statsReceiver          Stats
   *
   * @return Finagle HTTP client with Egress Proxy support using Credentials
   */
  @Provides
  @Singleton
  @Named(FinagleHttpClientWithCredentialProxy)
  def providesFinagleHttpClientWithCredentialProxy(
    @Flag(HttpClientWithProxyExTwitterHost) proxyExTwitterHost: String,
    @Flag(HttpClientWithProxyExTwitterPort) proxyExTwitterPort: Int,
    @Flag(HttpClientWithProxyRemoteHost) proxyRemoteHost: String,
    @Flag(HttpClientWithProxyRemotePort) proxyRemotePort: Int,
    @Flag(HttpClientRequestTimeout) requestTimeout: Duration,
    @Flag(HttpClientConnectTimeout) connectTimeout: Duration,
    @Flag(HttpClientAcquisitionTimeout) acquisitionTimeout: Duration,
    @Flag(ServiceLocal) isServiceLocal: Boolean,
    proxyCredentials: ProxyCredentials,
    statsReceiver: StatsReceiver
  ): Http.Client = {

    val ExTwitterProxyHostPort = HttpHostPort(proxyExTwitterHost, proxyExTwitterPort)
    val remoteProxyHostPort = HttpHostPort(proxyRemoteHost, proxyRemotePort)

    buildFinagleHttpClientWithCredentialProxy(
      ExTwitterProxyHostPort = ExTwitterProxyHostPort,
      remoteProxyHostPort = remoteProxyHostPort,
      requestTimeout = requestTimeout,
      connectTimeout = connectTimeout,
      acquisitionTimeout = acquisitionTimeout,
      proxyCredentials = proxyCredentials,
      statsReceiver = statsReceiver
    )
  }
}
