package com.tw ter.s mclusters_v2.summ ngb rd.stores

 mport com.tw ter.fr gate.common.store.strato.StratoStore
 mport com.tw ter.s mclusters_v2.common.S mClustersEmbedd ng
 mport com.tw ter.s mclusters_v2.common.ModelVers ons
 mport com.tw ter.s mclusters_v2.common.ModelVers ons._
 mport com.tw ter.s mclusters_v2.thr ftscala.Embedd ngType
 mport com.tw ter.s mclusters_v2.thr ftscala. nternal d
 mport com.tw ter.s mclusters_v2.thr ftscala.S mClustersEmbedd ng d
 mport com.tw ter.s mclusters_v2.thr ftscala.Top c d
 mport com.tw ter.s mclusters_v2.thr ftscala.{S mClustersEmbedd ng => Thr ftS mClustersEmbedd ng}
 mport com.tw ter.storehaus.ReadableStore
 mport com.tw ter.strato.cl ent.Cl ent

object ApeTop cEmbedd ngStore {

  pr vate val logFavBasedAPEColumn20M145K2020 =
    "recom ndat ons/s mclusters_v2/embedd ngs/logFavBasedAPE20M145K2020"

  pr vate def getStore(
    stratoCl ent: Cl ent,
    column: Str ng
  ): ReadableStore[S mClustersEmbedd ng d, Thr ftS mClustersEmbedd ng] = {
    StratoStore
      .w hUn V ew[S mClustersEmbedd ng d, Thr ftS mClustersEmbedd ng](stratoCl ent, column)
  }

  def getFavBasedLocaleEnt yEmbedd ng2020Store(
    stratoCl ent: Cl ent,
  ): ReadableStore[Top c d, S mClustersEmbedd ng] = {

    getStore(stratoCl ent, logFavBasedAPEColumn20M145K2020)
      .composeKeyMapp ng[Top c d] { top c d =>
        S mClustersEmbedd ng d(
          Embedd ngType.LogFavBasedKgoApeTop c,
          ModelVers ons.Model20M145K2020,
           nternal d.Top c d(top c d)
        )
      }
      .mapValues(S mClustersEmbedd ng(_))
  }

}
