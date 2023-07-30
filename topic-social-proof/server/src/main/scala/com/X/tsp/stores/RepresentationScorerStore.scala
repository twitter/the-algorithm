package com.X.tsp.stores

import com.X.contentrecommender.thriftscala.ScoringResponse
import com.X.finagle.stats.StatsReceiver
import com.X.frigate.common.store.strato.StratoFetchableStore
import com.X.hermit.store.common.ObservedReadableStore
import com.X.simclusters_v2.thriftscala.Score
import com.X.simclusters_v2.thriftscala.ScoreId
import com.X.storehaus.ReadableStore
import com.X.strato.client.Client
import com.X.strato.thrift.ScroogeConvImplicits._
import com.X.tsp.utils.ReadableStoreWithMapOptionValues

object RepresentationScorerStore {

  def apply(
    stratoClient: Client,
    scoringColumnPath: String,
    stats: StatsReceiver
  ): ReadableStore[ScoreId, Score] = {
    val stratoFetchableStore = StratoFetchableStore
      .withUnitView[ScoreId, ScoringResponse](stratoClient, scoringColumnPath)

    val enrichedStore = new ReadableStoreWithMapOptionValues[ScoreId, ScoringResponse, Score](
      stratoFetchableStore).mapOptionValues(_.score)

    ObservedReadableStore(
      enrichedStore
    )(stats.scope("representation_scorer_store"))
  }
}
