package com.X.tsp.modules

import com.google.inject.Module
import com.google.inject.Provides
import com.google.inject.Singleton
import com.X.app.Flag
import com.X.bijection.scrooge.BinaryScalaCodec
import com.X.conversions.DurationOps._
import com.X.finagle.memcached.{Client => MemClient}
import com.X.finagle.stats.StatsReceiver
import com.X.hermit.store.common.ObservedMemcachedReadableStore
import com.X.inject.XModule
import com.X.simclusters_v2.thriftscala.Score
import com.X.simclusters_v2.thriftscala.ScoreId
import com.X.storehaus.ReadableStore
import com.X.strato.client.{Client => StratoClient}
import com.X.tsp.stores.RepresentationScorerStore

object RepresentationScorerStoreModule extends XModule {
  override def modules: Seq[Module] = Seq(UnifiedCacheClient)

  private val tspRepresentationScoringColumnPath: Flag[String] = flag[String](
    name = "tsp.representationScoringColumnPath",
    default = "recommendations/representation_scorer/score",
    help = "Strato column path for Representation Scorer Store"
  )

  @Provides
  @Singleton
  def providesRepresentationScorerStore(
    statsReceiver: StatsReceiver,
    stratoClient: StratoClient,
    tspUnifiedCacheClient: MemClient
  ): ReadableStore[ScoreId, Score] = {
    val underlyingStore =
      RepresentationScorerStore(stratoClient, tspRepresentationScoringColumnPath(), statsReceiver)
    ObservedMemcachedReadableStore.fromCacheClient(
      backingStore = underlyingStore,
      cacheClient = tspUnifiedCacheClient,
      ttl = 2.hours
    )(
      valueInjection = BinaryScalaCodec(Score),
      statsReceiver = statsReceiver.scope("RepresentationScorerStore"),
      keyToString = { k: ScoreId => s"rsx/$k" }
    )
  }
}
