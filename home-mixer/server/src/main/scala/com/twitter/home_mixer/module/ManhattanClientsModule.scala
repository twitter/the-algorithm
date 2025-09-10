package com.twitter.home_mixer.module

import com.google.inject.Provides
import com.twitter.conversions.DurationOps._
import com.twitter.finagle.mtls.authentication.ServiceIdentifier
import com.twitter.home_mixer.param.HomeMixerInjectionNames.RTAManhattanEndpoint
import com.twitter.home_mixer.param.HomeMixerInjectionNames.RTAManhattanStore
import com.twitter.home_mixer.param.HomeMixerInjectionNames.RealGraphManhattanEndpoint
import com.twitter.home_mixer.store.RTAMHStore
import com.twitter.inject.TwitterModule
import com.twitter.inject.annotations.Flag
import com.twitter.storage.client.manhattan.kv._
import com.twitter.timelines.config.ConfigUtils
import com.twitter.util.Duration
import com.twitter.timelines.realtime_aggregates.{thriftscala => thrift}
import com.twitter.ml.api.DataRecord
import com.twitter.storehaus.ReadableStore

import javax.inject.Named
import javax.inject.Singleton

object ManhattanClientsModule extends TwitterModule with ConfigUtils {

  private val ApolloDest = "/s/manhattan/apollo.native-thrift"
  private val BalterDest = "/s/manhattan/baltar.native-thrift"
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

  @Provides
  @Singleton
  @Named(RTAManhattanEndpoint)
  def providesRTAManhattanEndpoint(
    @Flag(Timeout) timeout: Duration,
    serviceIdentifier: ServiceIdentifier
  ): ManhattanKVEndpoint = {
    lazy val client = ManhattanKVClient(
      appId = "timelines_real_time_aggregates",
      dest = BalterDest,
      mtlsParams = ManhattanKVClientMtlsParams(serviceIdentifier = serviceIdentifier),
      label = "rta-test"
    )

    ManhattanKVEndpointBuilder(client)
      .maxRetryCount(2)
      .defaultMaxTimeout(timeout)
      .build()
  }

  @Provides
  @Singleton
  @Named(RTAManhattanStore)
  def providesRTAManhattanStore(
    @Named(RTAManhattanEndpoint) manhattanKVEndpoint: ManhattanKVEndpoint
  ): Option[ReadableStore[thrift.AggregationKey, DataRecord]] = {
    Some(new RTAMHStore(manhattanKVEndpoint))
  }
}
