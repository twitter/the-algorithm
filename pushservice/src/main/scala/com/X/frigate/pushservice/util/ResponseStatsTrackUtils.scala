package com.X.frigate.pushservice.util

import com.X.finagle.stats.BroadcastStatsReceiver
import com.X.finagle.stats.StatsReceiver
import com.X.frigate.pushservice.model.PushTypes.Target
import com.X.frigate.pushservice.thriftscala.PushResponse
import com.X.frigate.pushservice.thriftscala.PushStatus
import com.X.frigate.thriftscala.CommonRecommendationType

object ResponseStatsTrackUtils {
  def trackStatsForResponseToRequest(
    crt: CommonRecommendationType,
    target: Target,
    response: PushResponse,
    receivers: Seq[StatsReceiver]
  )(
    originalStats: StatsReceiver
  ): Unit = {
    val newReceivers = Seq(
      originalStats
        .scope("is_model_training_data")
        .scope(target.isModelTrainingData.toString),
      originalStats.scope("scribe_target").scope(IbisScribeTargets.crtToScribeTarget(crt))
    )

    val broadcastStats = BroadcastStatsReceiver(receivers)
    val broadcastStatsWithExpts = BroadcastStatsReceiver(newReceivers ++ receivers)

    if (response.status == PushStatus.Sent) {
      if (target.isModelTrainingData) {
        broadcastStats.counter("num_training_data_recs_sent").incr()
      }
    }
    broadcastStatsWithExpts.counter(response.status.toString).incr()
    if (response.status == PushStatus.Filtered) {
      broadcastStats
        .scope(response.status.toString)
        .counter(response.filteredBy.getOrElse("None"))
        .incr()
    }
  }
}
