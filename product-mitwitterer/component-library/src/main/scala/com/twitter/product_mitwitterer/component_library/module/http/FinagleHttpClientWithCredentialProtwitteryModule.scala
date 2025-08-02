package com.twitter.product_mitwitterer.component_library.module.http

import com.google.inject.Provides
import com.twitter.finagle.Http
import com.twitter.finagle.http.ProtwitteryCredentials
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.inject.TwitterModule
import com.twitter.inject.annotations.Flag
import com.twitter.product_mitwitterer.component_library.module.http.FinagleHttpClientModule.HttpClientAcquisitionTimeout
import com.twitter.product_mitwitterer.component_library.module.http.FinagleHttpClientModule.HttpClientConnectTimeout
import com.twitter.product_mitwitterer.component_library.module.http.FinagleHttpClientModule.HttpClientRequestTimeout
import com.twitter.product_mitwitterer.component_library.module.http.FinagleHttpClientWithProtwitteryModule.HttpClientWithProtwitteryRemoteHost
import com.twitter.product_mitwitterer.component_library.module.http.FinagleHttpClientWithProtwitteryModule.HttpClientWithProtwitteryRemotePort
import com.twitter.product_mitwitterer.component_library.module.http.FinagleHttpClientWithProtwitteryModule.HttpClientWithProtwitteryTwitterHost
import com.twitter.product_mitwitterer.component_library.module.http.FinagleHttpClientWithProtwitteryModule.HttpClientWithProtwitteryTwitterPort
import com.twitter.product_mitwitterer.core.module.product_mitwitterer_flags.ProductMitwittererFlagModule.ServiceLocal
import com.twitter.product_mitwitterer.shared_library.http_client.FinagleHttpClientWithProtwitteryBuilder.buildFinagleHttpClientWithCredentialProtwittery
import com.twitter.product_mitwitterer.shared_library.http_client.HttpHostPort
import com.twitter.util.Duration
import javatwitter.inject.Named
import javatwitter.inject.Singleton

object FinagleHttpClientWithCredentialProtwitteryModule etwittertends TwitterModule {

  final val FinagleHttpClientWithCredentialProtwittery = "FinagleHttpClientWithCredentialProtwittery"

  /**
   * Provide a Finagle HTTP client with Egress Protwittery support using Credentials
   *
   * Note that the timeouts configured in this module are meant to be a reasonable starting point
   * only. To further tuning the settings, either override the flags or create local copy of the module.
   *
   * @param protwitteryTwitterHost       Twitter egress protwittery host
   * @param protwitteryTwitterPort       Twitter egress protwittery port
   * @param protwitteryRemoteHost        Remote protwittery host
   * @param protwitteryRemotePort        Remote protwittery port
   * @param requestTimeout         HTTP client request timeout
   * @param connectTimeout         HTTP client transport connect timeout
   * @param acquisitionTimeout     HTTP client session acquisition timeout
   * @param isServiceLocal         If this is a Local deployment for testing
   * @param protwitteryCredentials       Protwittery credentials
   * @param statsReceiver          Stats
   *
   * @return Finagle HTTP client with Egress Protwittery support using Credentials
   */
  @Provides
  @Singleton
  @Named(FinagleHttpClientWithCredentialProtwittery)
  def providesFinagleHttpClientWithCredentialProtwittery(
    @Flag(HttpClientWithProtwitteryTwitterHost) protwitteryTwitterHost: String,
    @Flag(HttpClientWithProtwitteryTwitterPort) protwitteryTwitterPort: Int,
    @Flag(HttpClientWithProtwitteryRemoteHost) protwitteryRemoteHost: String,
    @Flag(HttpClientWithProtwitteryRemotePort) protwitteryRemotePort: Int,
    @Flag(HttpClientRequestTimeout) requestTimeout: Duration,
    @Flag(HttpClientConnectTimeout) connectTimeout: Duration,
    @Flag(HttpClientAcquisitionTimeout) acquisitionTimeout: Duration,
    @Flag(ServiceLocal) isServiceLocal: Boolean,
    protwitteryCredentials: ProtwitteryCredentials,
    statsReceiver: StatsReceiver
  ): Http.Client = {

    val twitterProtwitteryHostPort = HttpHostPort(protwitteryTwitterHost, protwitteryTwitterPort)
    val remoteProtwitteryHostPort = HttpHostPort(protwitteryRemoteHost, protwitteryRemotePort)

    buildFinagleHttpClientWithCredentialProtwittery(
      twitterProtwitteryHostPort = twitterProtwitteryHostPort,
      remoteProtwitteryHostPort = remoteProtwitteryHostPort,
      requestTimeout = requestTimeout,
      connectTimeout = connectTimeout,
      acquisitionTimeout = acquisitionTimeout,
      protwitteryCredentials = protwitteryCredentials,
      statsReceiver = statsReceiver
    )
  }
}
