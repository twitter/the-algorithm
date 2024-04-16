package com.tw ter.cr_m xer.s m lar y_eng ne

 mport com.tw ter.cr_m xer.s m lar y_eng ne.S m lar yEng ne. mCac Conf g
 mport com.tw ter.cr_m xer.s m lar y_eng ne.S m lar yEng ne.S m lar yEng neConf g
 mport com.tw ter.cr_m xer.thr ftscala.S m lar yEng neType
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.storehaus.ReadableStore
 mport com.tw ter.t  l nes.conf gap .Params
 mport com.tw ter.ut l.Future

case class LookupEng neQuery[Query](
  storeQuery: Query, // t  actual Query type of t  underly ng store
  lookupKey: Str ng,
  params: Params,
)

/**
 * T  Eng ne prov des a map  nterface for look ng up d fferent model  mple ntat ons.
 *   prov des model d level mon or ng for free.
 *
 * Example use cases  nclude Offl neS mClusters lookup
 *
 *
 * @param vers onedStoreMap   A mapp ng from a model d to a correspond ng  mple ntat on
 * @param  mCac Conf gOpt    f spec f ed,   w ll wrap t  underly ng store w h a  mCac  layer
 *                              should only enable t  for cac able quer es, e.x. T et ds.
 *                            consu r based User ds are generally not poss ble to cac .
 */
class LookupS m lar yEng ne[Query, Cand date <: Ser al zable](
  vers onedStoreMap: Map[Str ng, ReadableStore[Query, Seq[Cand date]]], // key = model d
  overr de val  dent f er: S m lar yEng neType,
  globalStats: StatsRece ver,
  eng neConf g: S m lar yEng neConf g,
   mCac Conf gOpt: Opt on[ mCac Conf g[Query]] = None)
    extends S m lar yEng ne[LookupEng neQuery[Query], Cand date] {

  pr vate val scopedStats = globalStats.scope("s m lar yEng ne",  dent f er.toStr ng)

  pr vate val underly ngLookupMap = {
     mCac Conf gOpt match {
      case So (conf g) =>
        vers onedStoreMap.map {
          case (model d, store) =>
            (
              model d,
              S m lar yEng ne.add mCac (
                underly ngStore = store,
                 mCac Conf g = conf g,
                keyPref x = So (model d),
                statsRece ver = scopedStats
              )
            )
        }
      case _ => vers onedStoreMap
    }
  }

  overr de def getCand dates(
    eng neQuery: LookupEng neQuery[Query]
  ): Future[Opt on[Seq[Cand date]]] = {
    val vers onedStore =
      underly ngLookupMap
        .getOrElse(
          eng neQuery.lookupKey,
          throw new  llegalArgu ntExcept on(
            s"${t .getClass.getS mpleNa } ${ dent f er.toStr ng}: Model d ${eng neQuery.lookupKey} does not ex st"
          )
        )

    S m lar yEng ne.getFromFn(
      fn = vers onedStore.get,
      storeQuery = eng neQuery.storeQuery,
      eng neConf g = eng neConf g,
      params = eng neQuery.params,
      scopedStats = scopedStats.scope(eng neQuery.lookupKey)
    )
  }
}
