package com.tw ter.representat on_manager

 mport com.tw ter.f nagle. mcac d.{Cl ent =>  mcac dCl ent}
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.fr gate.common.store.strato.StratoFetchableStore
 mport com.tw ter. rm .store.common.ObservedCac dReadableStore
 mport com.tw ter. rm .store.common.ObservedReadableStore
 mport com.tw ter.representat on_manager.conf g.Cl entConf g
 mport com.tw ter.representat on_manager.conf g.D sabled n moryCac Params
 mport com.tw ter.representat on_manager.conf g.Enabled n moryCac Params
 mport com.tw ter.representat on_manager.thr ftscala.S mClustersEmbedd ngV ew
 mport com.tw ter.s mclusters_v2.common.S mClustersEmbedd ng
 mport com.tw ter.s mclusters_v2.thr ftscala. nternal d
 mport com.tw ter.s mclusters_v2.thr ftscala.LocaleEnt y d
 mport com.tw ter.s mclusters_v2.thr ftscala.S mClustersEmbedd ng d
 mport com.tw ter.s mclusters_v2.thr ftscala.Top c d
 mport com.tw ter.s mclusters_v2.thr ftscala.{S mClustersEmbedd ng => Thr ftS mClustersEmbedd ng}
 mport com.tw ter.storehaus.ReadableStore
 mport com.tw ter.strato.cl ent.{Cl ent => StratoCl ent}
 mport com.tw ter.strato.thr ft.ScroogeConv mpl c s._

/**
 * T   s t  class that offers features to bu ld readable stores for a g ven
 * S mClustersEmbedd ngV ew ( .e. embedd ngType and modelVers on).   appl es Cl entConf g
 * for a part cular serv ce and bu ld ReadableStores wh ch  mple nt that conf g.
 */
class StoreBu lder(
  cl entConf g: Cl entConf g,
  stratoCl ent: StratoCl ent,
   mCac dCl ent:  mcac dCl ent,
  globalStats: StatsRece ver,
) {
  pr vate val stats =
    globalStats.scope("representat on_manager_cl ent").scope(t .getClass.getS mpleNa )

  // Column consts
  pr vate val ColPathPref x = "recom ndat ons/representat on_manager/"
  pr vate val S mclustersT etColPath = ColPathPref x + "s mClustersEmbedd ng.T et"
  pr vate val S mclustersUserColPath = ColPathPref x + "s mClustersEmbedd ng.User"
  pr vate val S mclustersTop c dColPath = ColPathPref x + "s mClustersEmbedd ng.Top c d"
  pr vate val S mclustersLocaleEnt y dColPath =
    ColPathPref x + "s mClustersEmbedd ng.LocaleEnt y d"

  def bu ldS mclustersT etEmbedd ngStore(
    embedd ngColumnV ew: S mClustersEmbedd ngV ew
  ): ReadableStore[Long, S mClustersEmbedd ng] = {
    val rawStore = StratoFetchableStore
      .w hV ew[Long, S mClustersEmbedd ngV ew, Thr ftS mClustersEmbedd ng](
        stratoCl ent,
        S mclustersT etColPath,
        embedd ngColumnV ew)
      .mapValues(S mClustersEmbedd ng(_))

    addCac Layer(rawStore, embedd ngColumnV ew)
  }

  def bu ldS mclustersUserEmbedd ngStore(
    embedd ngColumnV ew: S mClustersEmbedd ngV ew
  ): ReadableStore[Long, S mClustersEmbedd ng] = {
    val rawStore = StratoFetchableStore
      .w hV ew[Long, S mClustersEmbedd ngV ew, Thr ftS mClustersEmbedd ng](
        stratoCl ent,
        S mclustersUserColPath,
        embedd ngColumnV ew)
      .mapValues(S mClustersEmbedd ng(_))

    addCac Layer(rawStore, embedd ngColumnV ew)
  }

  def bu ldS mclustersTop c dEmbedd ngStore(
    embedd ngColumnV ew: S mClustersEmbedd ngV ew
  ): ReadableStore[Top c d, S mClustersEmbedd ng] = {
    val rawStore = StratoFetchableStore
      .w hV ew[Top c d, S mClustersEmbedd ngV ew, Thr ftS mClustersEmbedd ng](
        stratoCl ent,
        S mclustersTop c dColPath,
        embedd ngColumnV ew)
      .mapValues(S mClustersEmbedd ng(_))

    addCac Layer(rawStore, embedd ngColumnV ew)
  }

  def bu ldS mclustersLocaleEnt y dEmbedd ngStore(
    embedd ngColumnV ew: S mClustersEmbedd ngV ew
  ): ReadableStore[LocaleEnt y d, S mClustersEmbedd ng] = {
    val rawStore = StratoFetchableStore
      .w hV ew[LocaleEnt y d, S mClustersEmbedd ngV ew, Thr ftS mClustersEmbedd ng](
        stratoCl ent,
        S mclustersLocaleEnt y dColPath,
        embedd ngColumnV ew)
      .mapValues(S mClustersEmbedd ng(_))

    addCac Layer(rawStore, embedd ngColumnV ew)
  }

  def bu ldS mclustersT etEmbedd ngStoreW hEmbedd ng dAsKey(
    embedd ngColumnV ew: S mClustersEmbedd ngV ew
  ): ReadableStore[S mClustersEmbedd ng d, S mClustersEmbedd ng] = {
    val rawStore = StratoFetchableStore
      .w hV ew[Long, S mClustersEmbedd ngV ew, Thr ftS mClustersEmbedd ng](
        stratoCl ent,
        S mclustersT etColPath,
        embedd ngColumnV ew)
      .mapValues(S mClustersEmbedd ng(_))
    val embedd ng dAsKeyStore = rawStore.composeKeyMapp ng[S mClustersEmbedd ng d] {
      case S mClustersEmbedd ng d(_, _,  nternal d.T et d(t et d)) =>
        t et d
    }

    addCac Layer(embedd ng dAsKeyStore, embedd ngColumnV ew)
  }

  def bu ldS mclustersUserEmbedd ngStoreW hEmbedd ng dAsKey(
    embedd ngColumnV ew: S mClustersEmbedd ngV ew
  ): ReadableStore[S mClustersEmbedd ng d, S mClustersEmbedd ng] = {
    val rawStore = StratoFetchableStore
      .w hV ew[Long, S mClustersEmbedd ngV ew, Thr ftS mClustersEmbedd ng](
        stratoCl ent,
        S mclustersUserColPath,
        embedd ngColumnV ew)
      .mapValues(S mClustersEmbedd ng(_))
    val embedd ng dAsKeyStore = rawStore.composeKeyMapp ng[S mClustersEmbedd ng d] {
      case S mClustersEmbedd ng d(_, _,  nternal d.User d(user d)) =>
        user d
    }

    addCac Layer(embedd ng dAsKeyStore, embedd ngColumnV ew)
  }

  def bu ldS mclustersTop cEmbedd ngStoreW hEmbedd ng dAsKey(
    embedd ngColumnV ew: S mClustersEmbedd ngV ew
  ): ReadableStore[S mClustersEmbedd ng d, S mClustersEmbedd ng] = {
    val rawStore = StratoFetchableStore
      .w hV ew[Top c d, S mClustersEmbedd ngV ew, Thr ftS mClustersEmbedd ng](
        stratoCl ent,
        S mclustersTop c dColPath,
        embedd ngColumnV ew)
      .mapValues(S mClustersEmbedd ng(_))
    val embedd ng dAsKeyStore = rawStore.composeKeyMapp ng[S mClustersEmbedd ng d] {
      case S mClustersEmbedd ng d(_, _,  nternal d.Top c d(top c d)) =>
        top c d
    }

    addCac Layer(embedd ng dAsKeyStore, embedd ngColumnV ew)
  }

  def bu ldS mclustersTop c dEmbedd ngStoreW hEmbedd ng dAsKey(
    embedd ngColumnV ew: S mClustersEmbedd ngV ew
  ): ReadableStore[S mClustersEmbedd ng d, S mClustersEmbedd ng] = {
    val rawStore = StratoFetchableStore
      .w hV ew[Top c d, S mClustersEmbedd ngV ew, Thr ftS mClustersEmbedd ng](
        stratoCl ent,
        S mclustersTop c dColPath,
        embedd ngColumnV ew)
      .mapValues(S mClustersEmbedd ng(_))
    val embedd ng dAsKeyStore = rawStore.composeKeyMapp ng[S mClustersEmbedd ng d] {
      case S mClustersEmbedd ng d(_, _,  nternal d.Top c d(top c d)) =>
        top c d
    }

    addCac Layer(embedd ng dAsKeyStore, embedd ngColumnV ew)
  }

  def bu ldS mclustersLocaleEnt y dEmbedd ngStoreW hEmbedd ng dAsKey(
    embedd ngColumnV ew: S mClustersEmbedd ngV ew
  ): ReadableStore[S mClustersEmbedd ng d, S mClustersEmbedd ng] = {
    val rawStore = StratoFetchableStore
      .w hV ew[LocaleEnt y d, S mClustersEmbedd ngV ew, Thr ftS mClustersEmbedd ng](
        stratoCl ent,
        S mclustersLocaleEnt y dColPath,
        embedd ngColumnV ew)
      .mapValues(S mClustersEmbedd ng(_))
    val embedd ng dAsKeyStore = rawStore.composeKeyMapp ng[S mClustersEmbedd ng d] {
      case S mClustersEmbedd ng d(_, _,  nternal d.LocaleEnt y d(localeEnt y d)) =>
        localeEnt y d
    }

    addCac Layer(embedd ng dAsKeyStore, embedd ngColumnV ew)
  }

  pr vate def addCac Layer[K](
    rawStore: ReadableStore[K, S mClustersEmbedd ng],
    embedd ngColumnV ew: S mClustersEmbedd ngV ew,
  ): ReadableStore[K, S mClustersEmbedd ng] = {
    // Add  n- mory cach ng based on Cl entConf g
    val  n mCac Params = cl entConf g. n moryCac Conf g
      .getCac Setup(embedd ngColumnV ew.embedd ngType, embedd ngColumnV ew.modelVers on)

    val statsPerStore = stats
      .scope(embedd ngColumnV ew.embedd ngType.na ).scope(embedd ngColumnV ew.modelVers on.na )

     n mCac Params match {
      case D sabled n moryCac Params =>
        ObservedReadableStore(
          store = rawStore
        )(statsPerStore)
      case Enabled n moryCac Params(ttl, maxKeys, cac Na ) =>
        ObservedCac dReadableStore.from[K, S mClustersEmbedd ng](
          rawStore,
          ttl = ttl,
          maxKeys = maxKeys,
          cac Na  = cac Na ,
          w ndowS ze = 10000L
        )(statsPerStore)
    }
  }

}
