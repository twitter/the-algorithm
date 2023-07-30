package com.X.home_mixer.module

import com.google.inject.Provides
import com.X.conversions.DurationOps._
import com.X.finagle.mtls.authentication.ServiceIdentifier
import com.X.home_mixer.param.HomeMixerInjectionNames.RealGraphManhattanEndpoint
import com.X.inject.XModule
import com.X.inject.annotations.Flag
import com.X.storage.client.manhattan.kv._
import com.X.timelines.config.ConfigUtils
import com.X.util.Duration
import javax.inject.Named
import javax.inject.Singleton

object ManhattanClientsModule extends XModule with ConfigUtils {

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
