package com.ExTwitter.home_mixer.module

import com.google.inject.Provides
import com.ExTwitter.conversions.DurationOps._
import com.ExTwitter.finagle.mtls.authentication.ServiceIdentifier
import com.ExTwitter.home_mixer.param.HomeMixerInjectionNames.RealGraphManhattanEndpoint
import com.ExTwitter.inject.ExTwitterModule
import com.ExTwitter.inject.annotations.Flag
import com.ExTwitter.storage.client.manhattan.kv._
import com.ExTwitter.timelines.config.ConfigUtils
import com.ExTwitter.util.Duration
import javax.inject.Named
import javax.inject.Singleton

object ManhattanClientsModule extends ExTwitterModule with ConfigUtils {

  private val ApolloDest = "/s/manhattan/apollo.native-thrift"
  private final val Timeout = "mh_real_graph.timeout"

  flag[Duration](Timeout, 150.millis, "Timeout total")

  @Provides
  @Singleton
  @Named(RealGraphManhattanEndpoint)
  def providesRealGraphManhattanEndpoint(
    @Flag(Timeout) timeout: Duration,
    serviceIdentifier: ServiceIdentifier
  ): ManhattanKVEndpoint = {
    lazy val client = ManhattanKVClient(
      appId = "real_graph",
      dest = ApolloDest,
      mtlsParams = ManhattanKVClientMtlsParams(serviceIdentifier = serviceIdentifier),
      label = "real-graph-data"
    )

    ManhattanKVEndpointBuilder(client)
      .maxRetryCount(2)
      .defaultMaxTimeout(timeout)
      .build()
  }
}
