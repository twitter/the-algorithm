package com.twitter.home_mixer.module

import com.google.inject.Provides
import com.google.inject.name.Named
import com.twitter.home_mixer.param.HomeGlobalParams.PhoenixCluster
import com.twitter.home_mixer.param.HomeGlobalParams.PhoenixCluster.Experiment1
import com.twitter.home_mixer.param.HomeGlobalParams.PhoenixCluster.Experiment2
import com.twitter.home_mixer.param.HomeGlobalParams.PhoenixCluster.Experiment3
import com.twitter.home_mixer.param.HomeGlobalParams.PhoenixCluster.Experiment4
import com.twitter.home_mixer.param.HomeGlobalParams.PhoenixCluster.Experiment5
import com.twitter.home_mixer.param.HomeGlobalParams.PhoenixCluster.Experiment6
import com.twitter.home_mixer.param.HomeGlobalParams.PhoenixCluster.Experiment7
import com.twitter.home_mixer.param.HomeGlobalParams.PhoenixCluster.Experiment8
import com.twitter.home_mixer.param.HomeGlobalParams.PhoenixCluster.Prod
import com.twitter.inject.TwitterModule
import io.grpc.ManagedChannel
import io.grpc.netty.NettyChannelBuilder
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

object PhoenixClientModule extends TwitterModule {

  val ChannelsPerHost = 10
  val XAIGrpcPort = 80
  val ProdEndpoint = ""

  private def buildChannels(endpoint: String): Seq[ManagedChannel] = {
    val endpoints = Seq.fill(ChannelsPerHost)(endpoint)
    endpoints.map { host =>
      NettyChannelBuilder
        .forAddress(host, XAIGrpcPort)
        .usePlaintext()
        .keepAliveTime(60, TimeUnit.SECONDS)
        .keepAliveTimeout(20, TimeUnit.SECONDS)
        .keepAliveWithoutCalls(true)
        .initialFlowControlWindow(128 * 1024 * 1024)
        .flowControlWindow(1024 * 1024 * 128)
        .maxInboundMessageSize(20 * 1024 * 1024)
        .build()
    }
  }

  @Provides
  @Singleton
  @Named("PhoenixClient")
  private def getStub(): Map[PhoenixCluster.Value, Seq[ManagedChannel]] = {
    val endpointMap = Map(
      Prod -> ProdEndpoint,
      Experiment1 -> "",
      Experiment2 -> "",
      Experiment3 -> "",
      Experiment4 -> "",
      Experiment5 -> "",
      Experiment6 -> "",
      Experiment7 -> "",
      Experiment8 -> "",
    ).withDefaultValue(ProdEndpoint)

    endpointMap.mapValues(buildChannels)
  }
}
