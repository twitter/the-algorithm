package com.ExTwitter.product_mixer.component_library.module.http

import com.google.inject.Provides
import com.ExTwitter.finagle.Http
import com.ExTwitter.finagle.stats.StatsReceiver
import com.ExTwitter.inject.ExTwitterModule
import com.ExTwitter.inject.annotations.Flag
import com.ExTwitter.product_mixer.component_library.module.http.FinagleHttpClientModule.HttpClientAcquisitionTimeout
import com.ExTwitter.product_mixer.component_library.module.http.FinagleHttpClientModule.HttpClientConnectTimeout
import com.ExTwitter.product_mixer.component_library.module.http.FinagleHttpClientModule.HttpClientRequestTimeout
import com.ExTwitter.product_mixer.core.module.product_mixer_flags.ProductMixerFlagModule.ServiceLocal
import com.ExTwitter.product_mixer.shared_library.http_client.FinagleHttpClientWithProxyBuilder.buildFinagleHttpClientWithProxy
import com.ExTwitter.product_mixer.shared_library.http_client.HttpHostPort
import com.ExTwitter.util.Duration
import javax.inject.Named
import javax.inject.Singleton

object FinagleHttpClientWithProxyModule extends ExTwitterModule {
  final val HttpClientWithProxyExTwitterHost = "http_client.proxy.ExTwitter_host"
  final val HttpClientWithProxyExTwitterPort = "http_client.proxy.ExTwitter_port"
  final val HttpClientWithProxyRemoteHost = "http_client.proxy.remote_host"
  final val HttpClientWithProxyRemotePort = "http_client.proxy.remote_port"

  flag[String](
    HttpClientWithProxyExTwitterHost,
    "httpproxy.local.ExTwitter.com",
    "ExTwitter egress proxy host")

  flag[Int](HttpClientWithProxyExTwitterPort, 3128, "ExTwitter egress proxy port")

  flag[String](HttpClientWithProxyRemoteHost, "Host that the proxy will connect to")

  flag[Int](HttpClientWithProxyRemotePort, 443, "Port that the proxy will connect to")

  final val FinagleHttpClientWithProxy = "FinagleHttpClientWithProxy"

  /**
   * Provide a Finagle HTTP client with Egress Proxy support
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
   * @param statsReceiver          Stats
   *
   * @return Finagle HTTP client with Egress Proxy support
   */
  @Provides
  @Singleton
  @Named(FinagleHttpClientWithProxy)
  def providesFinagleHttpClientWithProxy(
    @Flag(HttpClientWithProxyExTwitterHost) proxyExTwitterHost: String,
    @Flag(HttpClientWithProxyExTwitterPort) proxyExTwitterPort: Int,
    @Flag(HttpClientWithProxyRemoteHost) proxyRemoteHost: String,
    @Flag(HttpClientWithProxyRemotePort) proxyRemotePort: Int,
    @Flag(HttpClientRequestTimeout) requestTimeout: Duration,
    @Flag(HttpClientConnectTimeout) connectTimeout: Duration,
    @Flag(HttpClientAcquisitionTimeout) acquisitionTimeout: Duration,
    @Flag(ServiceLocal) isServiceLocal: Boolean,
    statsReceiver: StatsReceiver
  ): Http.Client = {
    val ExTwitterProxyHostPort = HttpHostPort(proxyExTwitterHost, proxyExTwitterPort)
    val remoteProxyHostPort = HttpHostPort(proxyRemoteHost, proxyRemotePort)

    buildFinagleHttpClientWithProxy(
      ExTwitterProxyHostPort = ExTwitterProxyHostPort,
      remoteProxyHostPort = remoteProxyHostPort,
      requestTimeout = requestTimeout,
      connectTimeout = connectTimeout,
      acquisitionTimeout = acquisitionTimeout,
      statsReceiver = statsReceiver
    )
  }
}
