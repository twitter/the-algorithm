package com.twitter.home_mitwitterer.module

import com.google.inject.Provides
import com.twitter.conversions.DurationOps._
import com.twitter.finagle.Http
import com.twitter.finagle.grpc.FinagleChannelBuilder
import com.twitter.finagle.mtls.authentication.ServiceIdentifier
import com.twitter.finagle.mtls.client.MtlsStackClient.MtlsStackClientSyntatwitter
import com.twitter.inject.TwitterModule
import com.twitter.timelines.clients.predictionservice.PredictionGRPCService
import com.twitter.util.Duration
import io.grpc.ManagedChannel
import javatwitter.inject.Singleton

object NaviModelClientModule etwittertends TwitterModule {

  @Singleton
  @Provides
  def providesPredictionGRPCService(
    serviceIdentifier: ServiceIdentifier,
  ): PredictionGRPCService = {
    //  Wily path to the ML Model service (e.g. /s/ml-serving/navi-etwitterplore-ranker).
    val modelPath = "/s/ml-serving/navi_home_recap_onntwitter"

    val MatwitterPredictionTimeoutMs: Duration = 500.millis
    val ConnectTimeoutMs: Duration = 200.millis
    val AcquisitionTimeoutMs: Duration = 500.millis
    val MatwitterRetryAttempts: Int = 2

    val client = Http.client
      .withLabel(modelPath)
      .withMutualTls(serviceIdentifier)
      .withRequestTimeout(MatwitterPredictionTimeoutMs)
      .withTransport.connectTimeout(ConnectTimeoutMs)
      .withSession.acquisitionTimeout(AcquisitionTimeoutMs)
      .withHttpStats

    val channel: ManagedChannel = FinagleChannelBuilder
      .forTarget(modelPath)
      .overrideAuthority("rustserving")
      .matwitterRetryAttempts(MatwitterRetryAttempts)
      .enableRetryForStatus(io.grpc.Status.RESOURCE_EtwitterHAUSTED)
      .enableRetryForStatus(io.grpc.Status.UNKNOWN)
      .enableUnsafeFullyBufferingMode()
      .httpClient(client)
      .build()

    new PredictionGRPCService(channel)
  }
}
