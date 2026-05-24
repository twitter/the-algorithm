package com.twitter.tsp.stores

import com.twitter.contentrecommender.thriftscala.ScoringResponse
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.frigate.common.store.strato.StratoFetchableStore
import com.twitter.hermit.store.common.ObservedReadableStore
import com.twitter.simclusters_v2.thriftscala.Score
import com.twitter.simclusters_v2.thriftscala.ScoreId
import com.twitter.storehaus.ReadableStore
import com.twitter.strato.client.Client
import com.twitter.strato.thrift.ScroogeConvImplicits._
import com.twitter.tsp.utils.ReadableStoreWithMapOptionValues

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
