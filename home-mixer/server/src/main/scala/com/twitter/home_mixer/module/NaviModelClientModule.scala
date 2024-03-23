package com.ExTwitter.home_mixer.module

import com.google.inject.Provides
import com.ExTwitter.conversions.DurationOps._
import com.ExTwitter.finagle.Http
import com.ExTwitter.finagle.grpc.FinagleChannelBuilder
import com.ExTwitter.finagle.mtls.authentication.ServiceIdentifier
import com.ExTwitter.finagle.mtls.client.MtlsStackClient.MtlsStackClientSyntax
import com.ExTwitter.inject.ExTwitterModule
import com.ExTwitter.timelines.clients.predictionservice.PredictionGRPCService
import com.ExTwitter.util.Duration
import io.grpc.ManagedChannel
import javax.inject.Singleton

object NaviModelClientModule extends ExTwitterModule {

  @Singleton
  @Provides
  def providesPredictionGRPCService(
    serviceIdentifier: ServiceIdentifier,
  ): PredictionGRPCService = {
    //  Wily path to the ML Model service (e.g. /s/ml-serving/navi-explore-ranker).
    val modelPath = "/s/ml-serving/navi_home_recap_onnx"

    val MaxPredictionTimeoutMs: Duration = 500.millis
    val ConnectTimeoutMs: Duration = 200.millis
    val AcquisitionTimeoutMs: Duration = 500.millis
    val MaxRetryAttempts: Int = 2

    val client = Http.client
      .withLabel(modelPath)
      .withMutualTls(serviceIdentifier)
      .withRequestTimeout(MaxPredictionTimeoutMs)
      .withTransport.connectTimeout(ConnectTimeoutMs)
      .withSession.acquisitionTimeout(AcquisitionTimeoutMs)
      .withHttpStats

    val channel: ManagedChannel = FinagleChannelBuilder
      .forTarget(modelPath)
      .overrideAuthority("rustserving")
      .maxRetryAttempts(MaxRetryAttempts)
      .enableRetryForStatus(io.grpc.Status.RESOURCE_EXHAUSTED)
      .enableRetryForStatus(io.grpc.Status.UNKNOWN)
      .enableUnsafeFullyBufferingMode()
      .httpClient(client)
      .build()

    new PredictionGRPCService(channel)
  }
}
