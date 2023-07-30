package com.X.product_mixer.component_library.module.http

import com.google.inject.Provides
import com.X.finagle.Http
import com.X.finagle.stats.StatsReceiver
import com.X.inject.XModule
import com.X.inject.annotations.Flag
import com.X.product_mixer.component_library.module.http.FinagleHttpClientModule.HttpClientAcquisitionTimeout
import com.X.product_mixer.component_library.module.http.FinagleHttpClientModule.HttpClientConnectTimeout
import com.X.product_mixer.component_library.module.http.FinagleHttpClientModule.HttpClientRequestTimeout
import com.X.product_mixer.core.module.product_mixer_flags.ProductMixerFlagModule.ServiceLocal
import com.X.product_mixer.shared_library.http_client.FinagleHttpClientWithProxyBuilder.buildFinagleHttpClientWithProxy
import com.X.product_mixer.shared_library.http_client.HttpHostPort
import com.X.util.Duration
import javax.inject.Named
import javax.inject.Singleton

object FinagleHttpClientWithProxyModule extends XModule {
  final val HttpClientWithProxyXHost = "http_client.proxy.X_host"
  final val HttpClientWithProxyXPort = "http_client.proxy.X_port"
  final val HttpClientWithProxyRemoteHost = "http_client.proxy.remote_host"
  final val HttpClientWithProxyRemotePort = "http_client.proxy.remote_port"

  flag[String](
    HttpClientWithProxyXHost,
    "httpproxy.local.X.com",
    "X egress proxy host")

  flag[Int](HttpClientWithProxyXPort, 3128, "X egress proxy port")

  flag[String](HttpClientWithProxyRemoteHost, "Host that the proxy will connect to")

  flag[Int](HttpClientWithProxyRemotePort, 443, "Port that the proxy will connect to")

  final val FinagleHttpClientWithProxy = "FinagleHttpClientWithProxy"

  /**
   * Provide a Finagle HTTP client with Egress Proxy support
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
   * @param statsReceiver          Stats
   *
   * @return Finagle HTTP client with Egress Proxy support
   */
  @Provides
  @Singleton
  @Named(FinagleHttpClientWithProxy)
  def providesFinagleHttpClientWithProxy(
    @Flag(HttpClientWithProxyXHost) proxyXHost: String,
    @Flag(HttpClientWithProxyXPort) proxyXPort: Int,
    @Flag(HttpClientWithProxyRemoteHost) proxyRemoteHost: String,
    @Flag(HttpClientWithProxyRemotePort) proxyRemotePort: Int,
    @Flag(HttpClientRequestTimeout) requestTimeout: Duration,
    @Flag(HttpClientConnectTimeout) connectTimeout: Duration,
    @Flag(HttpClientAcquisitionTimeout) acquisitionTimeout: Duration,
    @Flag(ServiceLocal) isServiceLocal: Boolean,
    statsReceiver: StatsReceiver
  ): Http.Client = {
    val XProxyHostPort = HttpHostPort(proxyXHost, proxyXPort)
    val remoteProxyHostPort = HttpHostPort(proxyRemoteHost, proxyRemotePort)

    buildFinagleHttpClientWithProxy(
      XProxyHostPort = XProxyHostPort,
      remoteProxyHostPort = remoteProxyHostPort,
      requestTimeout = requestTimeout,
      connectTimeout = connectTimeout,
      acquisitionTimeout = acquisitionTimeout,
      statsReceiver = statsReceiver
    )
  }
}
