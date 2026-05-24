package com.twitter.home_mixer.functional_component.scorer

import com.twitter.finagle.stats.BroadcastStatsReceiver
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.home_mixer.param.HomeMixerInjectionNames.NaviModelClientHomeRecap
import com.twitter.home_mixer.param.HomeMixerInjectionNames.NaviModelClientHomeRecapGPU
import com.twitter.home_mixer.param.HomeMixerInjectionNames.NaviModelClientHomeRecapRealtime
import com.twitter.home_mixer.param.HomeMixerInjectionNames.NaviModelClientHomeRecapSecondary
import com.twitter.home_mixer.param.HomeMixerInjectionNames.NaviModelClientHomeRecapVideo
import com.twitter.timelines.clients.predictionservice.PredictionGRPCService
import com.twitter.timelines.clients.predictionservice.PredictionServiceGRPCClient
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
case class PredictClientFactory @Inject() (
  @Named(NaviModelClientHomeRecap) homeRecapPredictionGRPCService: PredictionGRPCService,
  @Named(NaviModelClientHomeRecapSecondary) homeRecapSecondaryPredictionGRPCService: PredictionGRPCService,
  @Named(NaviModelClientHomeRecapRealtime) homeRecapRealtimePredictionGRPCService: PredictionGRPCService,
  @Named(NaviModelClientHomeRecapGPU) homeRecapGPUPredictionGRPCService: PredictionGRPCService,
  @Named(NaviModelClientHomeRecapVideo) homeRecapVideoPredictionGRPCService: PredictionGRPCService,
  statsReceiver: StatsReceiver) {

  val DefaultRequestBatchSize = 32

  def getClient(
    clientName: String,
    customizedBatchSize: Option[Int]
  ): PredictionServiceGRPCClient = {
    clientName match {
      case NaviModelClientHomeRecap =>
        homeRecapModelClient
      case NaviModelClientHomeRecapSecondary =>
        homeRecapSecondaryModelClient
      case NaviModelClientHomeRecapRealtime =>
        homeRecapRealtimeModelClient
      case NaviModelClientHomeRecapVideo =>
        homeRecapVideoModelClient
      case NaviModelClientHomeRecapGPU =>
        val gpuBatchSize = customizedBatchSize.getOrElse(DefaultRequestBatchSize)
        new PredictionServiceGRPCClient(
          service = homeRecapGPUPredictionGRPCService,
          statsReceiver = BroadcastStatsReceiver(
            Seq(statsReceiver, statsReceiver.scope("home_recap_gpu"))),
          requestBatchSize = gpuBatchSize,
          useCompact = false
        )
      case _ =>
        throw new IllegalArgumentException(s"Unknown clientName: $clientName")
    }
  }

  private lazy val homeRecapModelClient = new PredictionServiceGRPCClient(
    service = homeRecapPredictionGRPCService,
    statsReceiver = BroadcastStatsReceiver(Seq(statsReceiver, statsReceiver.scope("home_recap"))),
    requestBatchSize = DefaultRequestBatchSize,
    useCompact = false
  )

  private lazy val homeRecapSecondaryModelClient = new PredictionServiceGRPCClient(
    service = homeRecapSecondaryPredictionGRPCService,
    statsReceiver = BroadcastStatsReceiver(Seq(statsReceiver, statsReceiver.scope("home_recap"))),
    requestBatchSize = DefaultRequestBatchSize,
    useCompact = false
  )

  private lazy val homeRecapRealtimeModelClient = new PredictionServiceGRPCClient(
    service = homeRecapRealtimePredictionGRPCService,
    statsReceiver = BroadcastStatsReceiver(
      Seq(statsReceiver, statsReceiver.scope("home_recap_realtime"))),
    requestBatchSize = DefaultRequestBatchSize,
    useCompact = false
  )

  private lazy val homeRecapVideoModelClient = new PredictionServiceGRPCClient(
    service = homeRecapVideoPredictionGRPCService,
    statsReceiver = BroadcastStatsReceiver(
      Seq(statsReceiver, statsReceiver.scope("home_recap_video"))),
    requestBatchSize = DefaultRequestBatchSize,
    useCompact = false
  )
}
