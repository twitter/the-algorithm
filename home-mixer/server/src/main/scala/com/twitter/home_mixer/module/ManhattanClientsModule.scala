package com.twitter.home_mixer.module

import com.google.inject.Provides
import com.twitter.finagle.mtls.authentication.ServiceIdentifier
import com.twitter.home_mixer.param.HomeMixerInjectionNames.RealGraphManhattanEndpoint
import com.twitter.home_mixer.param.HomeMixerInjectionNames.UserMetadataManhattanEndpoint
import com.twitter.inject.TwitterModule
import com.twitter.storage.client.manhattan.kv._
import com.twitter.timelines.config.ConfigUtils
import com.twitter.util.Duration
import javax.inject.Named
import javax.inject.Singleton

object ManhattanClientsModule extends TwitterModule with ConfigUtils {

  private val starbuckDest: String = "/s/manhattan/starbuck.native-thrift"
  private val apolloDest: String = "/s/manhattan/apollo.native-thrift"

  @Provides
  @Singleton
  @Named(RealGraphManhattanEndpoint)
  def providesRealGraphManhattanEndpoint(
    serviceIdentifier: ServiceIdentifier
  ): ManhattanKVEndpoint = {
    lazy val client = ManhattanKVClient(
      appId = "real_graph",
      dest = apolloDest,
      mtlsParams = ManhattanKVClientMtlsParams(serviceIdentifier = serviceIdentifier),
      label = "real-graph-data"
    )

    ManhattanKVEndpointBuilder(client)
      .maxRetryCount(2)
      .defaultMaxTimeout(Duration.fromMilliseconds(100))
      .build()
  }

  @Provides
  @Singleton
  @Named(UserMetadataManhattanEndpoint)
  def providesUserMetadataManhattanEndpoint(
    serviceIdentifier: ServiceIdentifier
  ): ManhattanKVEndpoint = {
    val client = ManhattanKVClient(
      appId = "user_metadata",
      dest = starbuckDest,
      mtlsParams = ManhattanKVClientMtlsParams(serviceIdentifier = serviceIdentifier),
      label = "user-metadata"
    )

    ManhattanKVEndpointBuilder(client)
      .maxRetryCount(1)
      .defaultMaxTimeout(Duration.fromMilliseconds(70))
      .build()
  }
}
