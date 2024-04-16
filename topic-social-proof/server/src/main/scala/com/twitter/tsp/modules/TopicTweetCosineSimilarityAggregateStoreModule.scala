package com.tw ter.tsp.modules

 mport com.google. nject.Prov des
 mport com.google. nject.S ngleton
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter. nject.Tw terModule
 mport com.tw ter.s mclusters_v2.common.T et d
 mport com.tw ter.s mclusters_v2.thr ftscala.Score
 mport com.tw ter.s mclusters_v2.thr ftscala.Score d
 mport com.tw ter.s mclusters_v2.thr ftscala.Top c d
 mport com.tw ter.storehaus.ReadableStore
 mport com.tw ter.tsp.stores.Top cT etsCos neS m lar yAggregateStore
 mport com.tw ter.tsp.stores.Top cT etsCos neS m lar yAggregateStore.ScoreKey

object Top cT etCos neS m lar yAggregateStoreModule extends Tw terModule {

  @Prov des
  @S ngleton
  def prov desTop cT etCos neS m lar yAggregateStore(
    representat onScorerStore: ReadableStore[Score d, Score],
    statsRece ver: StatsRece ver,
  ): ReadableStore[(Top c d, T et d, Seq[ScoreKey]), Map[ScoreKey, Double]] = {
    Top cT etsCos neS m lar yAggregateStore(representat onScorerStore)(
      statsRece ver.scope("top cT etsCos neS m lar yAggregateStore"))
  }
}
