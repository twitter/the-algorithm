package com.X.product_mixer.component_library.module.http

import com.google.inject.Provides
import com.X.finagle.Http
import com.X.finagle.http.ProxyCredentials
import com.X.finagle.stats.StatsReceiver
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
import com.X.product_mixer.shared_library.http_client.FinagleHttpClientWithProxyBuilder.buildFinagleHttpClientWithCredentialProxy
import com.X.product_mixer.shared_library.http_client.HttpHostPort
import com.X.util.Duration
import javax.inject.Named
import javax.inject.Singleton

object FinagleHttpClientWithCredentialProxyModule extends XModule {

  final val FinagleHttpClientWithCredentialProxy = "FinagleHttpClientWithCredentialProxy"

  /**
   * Provide a Finagle HTTP client with Egress Proxy support using Credentials
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
    @Flag(HttpClientWithProxyXHost) proxyXHost: String,
    @Flag(HttpClientWithProxyXPort) proxyXPort: Int,
    @Flag(HttpClientWithProxyRemoteHost) proxyRemoteHost: String,
    @Flag(HttpClientWithProxyRemotePort) proxyRemotePort: Int,
    @Flag(HttpClientRequestTimeout) requestTimeout: Duration,
    @Flag(HttpClientConnectTimeout) connectTimeout: Duration,
    @Flag(HttpClientAcquisitionTimeout) acquisitionTimeout: Duration,
    @Flag(ServiceLocal) isServiceLocal: Boolean,
    proxyCredentials: ProxyCredentials,
    statsReceiver: StatsReceiver
  ): Http.Client = {

    val XProxyHostPort = HttpHostPort(proxyXHost, proxyXPort)
    val remoteProxyHostPort = HttpHostPort(proxyRemoteHost, proxyRemotePort)

    buildFinagleHttpClientWithCredentialProxy(
      XProxyHostPort = XProxyHostPort,
      remoteProxyHostPort = remoteProxyHostPort,
      requestTimeout = requestTimeout,
      connectTimeout = connectTimeout,
      acquisitionTimeout = acquisitionTimeout,
      proxyCredentials = proxyCredentials,
      statsReceiver = statsReceiver
    )
  }
}
