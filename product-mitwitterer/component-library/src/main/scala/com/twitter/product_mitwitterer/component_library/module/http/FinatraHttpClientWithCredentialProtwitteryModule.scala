package com.twitter.product_mitwitterer.component_library.module.http

import com.google.inject.Provides
import com.twitter.finagle.http.ProtwitteryCredentials
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.finatra.httpclient.HttpClient
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
import com.twitter.product_mitwitterer.shared_library.http_client.FinagleHttpClientWithProtwitteryBuilder.buildFinagleHttpServiceWithProtwittery
import com.twitter.product_mitwitterer.shared_library.http_client.HttpHostPort
import com.twitter.util.Duration
import com.twitter.util.jackson.ScalaObjectMapper
import javatwitter.inject.Named
import javatwitter.inject.Singleton

object FinatraHttpClientWithCredentialProtwitteryModule etwittertends TwitterModule {

  final val FinatraHttpClientWithCredentialProtwittery = "FinagleHttpClientWithCredentialProtwittery"

  /**
   * Build a Finatra HTTP client with Egress Protwittery support with Credentials for a host. The Finatra
   * HTTP client can be helpful (as opposed to the base Finagle HTTP Client), as it provides
   * built-in JSON response zparsing and other convenience methods
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
   * @param isServiceLocal         Local deployment for testing
   * @param protwitteryCredentials       Protwittery credentials
   * @param scalaObjectMapper      Object mapper used by the built-in JSON response parsing
   * @param statsReceiver          Stats
   *
   * @return Finatra HTTP client with Egress Protwittery support for a host
   */
  @Provides
  @Singleton
  @Named(FinatraHttpClientWithCredentialProtwittery)
  def providesFinatraHttpClientWithCredentialProtwittery(
    @Flag(HttpClientWithProtwitteryTwitterHost) protwitteryTwitterHost: String,
    @Flag(HttpClientWithProtwitteryTwitterPort) protwitteryTwitterPort: Int,
    @Flag(HttpClientWithProtwitteryRemoteHost) protwitteryRemoteHost: String,
    @Flag(HttpClientWithProtwitteryRemotePort) protwitteryRemotePort: Int,
    @Flag(HttpClientRequestTimeout) requestTimeout: Duration,
    @Flag(HttpClientConnectTimeout) connectTimeout: Duration,
    @Flag(HttpClientAcquisitionTimeout) acquisitionTimeout: Duration,
    @Flag(ServiceLocal) isServiceLocal: Boolean,
    protwitteryCredentials: ProtwitteryCredentials,
    scalaObjectMapper: ScalaObjectMapper,
    statsReceiver: StatsReceiver
  ): HttpClient = {
    val twitterProtwitteryHostPort = HttpHostPort(protwitteryTwitterHost, protwitteryTwitterPort)
    val protwitteryRemoteHostPort = HttpHostPort(protwitteryRemoteHost, protwitteryRemotePort)

    val finagleHttpClientWithCredentialProtwittery =
      buildFinagleHttpClientWithCredentialProtwittery(
        twitterProtwitteryHostPort = twitterProtwitteryHostPort,
        remoteProtwitteryHostPort = protwitteryRemoteHostPort,
        requestTimeout = requestTimeout,
        connectTimeout = connectTimeout,
        acquisitionTimeout = acquisitionTimeout,
        protwitteryCredentials = protwitteryCredentials,
        statsReceiver = statsReceiver
      )

    val finagleHttpServiceWithCredentialProtwittery =
      buildFinagleHttpServiceWithProtwittery(
        finagleHttpClientWithProtwittery = finagleHttpClientWithCredentialProtwittery,
        twitterProtwitteryHostPort = twitterProtwitteryHostPort
      )

    new HttpClient(
      hostname = twitterProtwitteryHostPort.host,
      httpService = finagleHttpServiceWithCredentialProtwittery,
      mapper = scalaObjectMapper
    )
  }
}
