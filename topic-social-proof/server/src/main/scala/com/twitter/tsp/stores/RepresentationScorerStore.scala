package com.tw ter.tsp.stores

 mport com.tw ter.contentrecom nder.thr ftscala.Scor ngResponse
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.fr gate.common.store.strato.StratoFetchableStore
 mport com.tw ter. rm .store.common.ObservedReadableStore
 mport com.tw ter.s mclusters_v2.thr ftscala.Score
 mport com.tw ter.s mclusters_v2.thr ftscala.Score d
 mport com.tw ter.storehaus.ReadableStore
 mport com.tw ter.strato.cl ent.Cl ent
 mport com.tw ter.strato.thr ft.ScroogeConv mpl c s._
 mport com.tw ter.tsp.ut ls.ReadableStoreW hMapOpt onValues

object Representat onScorerStore {

  def apply(
    stratoCl ent: Cl ent,
    scor ngColumnPath: Str ng,
    stats: StatsRece ver
  ): ReadableStore[Score d, Score] = {
    val stratoFetchableStore = StratoFetchableStore
      .w hUn V ew[Score d, Scor ngResponse](stratoCl ent, scor ngColumnPath)

    val enr c dStore = new ReadableStoreW hMapOpt onValues[Score d, Scor ngResponse, Score](
      stratoFetchableStore).mapOpt onValues(_.score)

    ObservedReadableStore(
      enr c dStore
    )(stats.scope("representat on_scorer_store"))
  }
}
