package com.tw ter.s mclusters_v2.summ ngb rd.stores

 mport com.tw ter.fr gate.common.store.strato.StratoStore
 mport com.tw ter.s mclusters_v2.common.ModelVers ons
 mport com.tw ter.s mclusters_v2.common.ModelVers ons._
 mport com.tw ter.s mclusters_v2.thr ftscala.{
  Embedd ngType,
   nternal d,
  LocaleEnt y d,
  S mClustersEmbedd ng d,
  S mClustersEmbedd ng => Thr ftS mClustersEmbedd ng
}
 mport com.tw ter.storehaus.ReadableStore
 mport com.tw ter.strato.cl ent.Cl ent
 mport com.tw ter.strato.thr ft.ScroogeConv mpl c s._
 mport com.tw ter.s mclusters_v2.common.S mClustersEmbedd ng

/**
 * ent y -> L st< cluster >
 */
object Semant cCoreEnt yEmbedd ngStore {

  pr vate val column =
    "recom ndat ons/s mclusters_v2/embedd ngs/semant cCoreEnt yPerLanguageEmbedd ngs20M145KUpdated"

  /**
   * Default store, wrapped  n gener c data types. Use t   f   know t  underly ng key struct.
   */
  pr vate def getDefaultStore(
    stratoCl ent: Cl ent
  ): ReadableStore[S mClustersEmbedd ng d, Thr ftS mClustersEmbedd ng] = {
    StratoStore
      .w hUn V ew[S mClustersEmbedd ng d, Thr ftS mClustersEmbedd ng](stratoCl ent, column)
  }

  def getFavBasedLocaleEnt yEmbedd ngStore(
    stratoCl ent: Cl ent
  ): ReadableStore[LocaleEnt y d, S mClustersEmbedd ng] = {
    getDefaultStore(stratoCl ent)
      .composeKeyMapp ng[LocaleEnt y d] { ent y d =>
        S mClustersEmbedd ng d(
          Embedd ngType.FavBasedSemat cCoreEnt y,
          ModelVers ons.Model20M145KUpdated,
           nternal d.LocaleEnt y d(ent y d)
        )
      }
      .mapValues(S mClustersEmbedd ng(_))
  }
}
