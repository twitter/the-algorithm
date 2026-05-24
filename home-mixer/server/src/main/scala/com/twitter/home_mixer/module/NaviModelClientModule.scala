package com.twitter.home_mixer.module

import com.google.inject.Provides
import com.twitter.conversions.DurationOps._
import com.twitter.finagle.Http
import com.twitter.finagle.grpc.FinagleChannelBuilder
import com.twitter.finagle.mtls.authentication.ServiceIdentifier
import com.twitter.finagle.mtls.client.MtlsStackClient.MtlsStackClientSyntax
import com.twitter.home_mixer.param.HomeMixerInjectionNames.NaviModelClientHomeRecap
import com.twitter.home_mixer.param.HomeMixerInjectionNames.NaviModelClientHomeRecapGPU
import com.twitter.home_mixer.param.HomeMixerInjectionNames.NaviModelClientHomeRecapRealtime
import com.twitter.home_mixer.param.HomeMixerInjectionNames.NaviModelClientHomeRecapSecondary
import com.twitter.home_mixer.param.HomeMixerInjectionNames.NaviModelClientHomeRecapVideo
import com.twitter.inject.TwitterModule
import com.twitter.timelines.clients.predictionservice.PredictionGRPCService
import com.twitter.util.Duration
import io.grpc.ManagedChannel
import javax.inject.Named
import javax.inject.Singleton

object NaviModelClientModule extends TwitterModule {

  private val Authority = "rustserving"
  private val MaxRetryAttempts = 2
  private val MaxPredictionTimeoutMs: Duration = 700.millis
  private val ConnectTimeoutMs: Duration = 200.millis
  private val AcquisitionTimeoutMs: Duration = 500.millis

  @Singleton
  @Named(NaviModelClientHomeRecap)
  @Provides
  def providesHomeRecapPredictionGRPCService(
    serviceIdentifier: ServiceIdentifier,
  ): PredictionGRPCService = {
    providesPredictionGRPCService(serviceIdentifier, "navi_home_recap_onnx")
  }

  @Singleton
  @Named(NaviModelClientHomeRecapSecondary)
  @Provides
  def providesHomeRecapSecondaryPredictionGRPCService(
    serviceIdentifier: ServiceIdentifier,
  ): PredictionGRPCService = {
    providesPredictionGRPCService(serviceIdentifier, "navi_home_recap_onnx_2")
  }

  @Singleton
  @Named(NaviModelClientHomeRecapRealtime)
  @Provides
  def providesHomeRecapRealtimePredictionGRPCService(
    serviceIdentifier: ServiceIdentifier,
  ): PredictionGRPCService = {
    providesPredictionGRPCService(serviceIdentifier, "navi_home_realtime_recap_onnx")
  }

  @Singleton
  @Named(NaviModelClientHomeRecapGPU)
  @Provides
  def providesHomeRecapGPUPredictionGRPCService(
    serviceIdentifier: ServiceIdentifier,
  ): PredictionGRPCService = {
    providesPredictionGRPCService(serviceIdentifier, "navi_home_recap_onnx_v100")
  }

  @Singleton
  @Named(NaviModelClientHomeRecapVideo)
  @Provides
  def providesHomeRecapVideoPredictionGRPCService(
    serviceIdentifier: ServiceIdentifier,
  ): PredictionGRPCService = {
    providesPredictionGRPCService(serviceIdentifier, "navi_home_recap_video_onnx")
  }

  private def providesPredictionGRPCService(
    serviceIdentifier: ServiceIdentifier,
    naviClusterName: String
  ): PredictionGRPCService = {
    val modelPath = s"/s/ml-serving/$naviClusterName"
    val client = Http.client
      .withLabel(modelPath)
      .withMutualTls(serviceIdentifier)
      .withRequestTimeout(MaxPredictionTimeoutMs)
      .withTransport.connectTimeout(ConnectTimeoutMs)
      .withSession.acquisitionTimeout(AcquisitionTimeoutMs)
      .withHttpStats

    val channel: ManagedChannel = FinagleChannelBuilder
      .forTarget(modelPath)
      .overrideAuthority(Authority)
      .maxRetryAttempts(MaxRetryAttempts)
      .enableRetryForStatus(io.grpc.Status.RESOURCE_EXHAUSTED)
      .enableRetryForStatus(io.grpc.Status.UNKNOWN)
      .enableUnsafeFullyBufferingMode()
      .httpClient(client)
      .build()

    new PredictionGRPCService(channel)
  }
}
