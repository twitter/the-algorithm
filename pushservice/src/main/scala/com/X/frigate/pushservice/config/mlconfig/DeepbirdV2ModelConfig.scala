package com.X.frigate.pushservice.config.mlconfig

import com.X.cortex.deepbird.thriftjava.DeepbirdPredictionService
import com.X.finagle.stats.StatsReceiver
import com.X.frigate.common.ml.prediction.DeepbirdPredictionEngineServiceStore
import com.X.nrel.heavyranker.PushDBv2PredictionServiceStore

object DeepbirdV2ModelConfig {
  def buildPredictionServiceScoreStore(
    predictionServiceClient: DeepbirdPredictionService.ServiceToClient,
    serviceName: String
  )(
    implicit statsReceiver: StatsReceiver
  ): PushDBv2PredictionServiceStore = {

    val stats = statsReceiver.scope(serviceName)
    val serviceStats = statsReceiver.scope("dbv2PredictionServiceStore")

    new PushDBv2PredictionServiceStore(
      DeepbirdPredictionEngineServiceStore(predictionServiceClient, batchSize = Some(32))(stats)
    )(serviceStats)
  }
}
