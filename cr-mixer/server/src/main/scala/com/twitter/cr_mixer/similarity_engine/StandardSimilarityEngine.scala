package com.tw ter.cr_m xer.s m lar y_eng ne

 mport com.tw ter.cr_m xer.s m lar y_eng ne.S m lar yEng ne. mCac Conf g
 mport com.tw ter.cr_m xer.s m lar y_eng ne.S m lar yEng ne.S m lar yEng neConf g
 mport com.tw ter.cr_m xer.thr ftscala.S m lar yEng neType
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.storehaus.ReadableStore
 mport com.tw ter.t  l nes.conf gap .Params
 mport com.tw ter.ut l.Future

/**
 * @tparam Query ReadableStore's  nput type.
 */
case class Eng neQuery[Query](
  storeQuery: Query,
  params: Params,
)

/**
 * A stra ght forward S m lar yEng ne  mple ntat on that wraps a ReadableStore
 *
 * @param  mple nt ngStore   Prov des t  cand date retr eval's  mple ntat ons
 * @param  mCac Conf g       f spec f ed,   w ll wrap t  underly ng store w h a  mCac  layer
 *                              should only enable t  for cac able quer es, e.x. T et ds.
 *                            consu r based User ds are generally not poss ble to cac .
 * @tparam Query              ReadableStore's  nput type
 * @tparam Cand date          ReadableStore's return type  s Seq[[[Cand date]]]
 */
class StandardS m lar yEng ne[Query, Cand date <: Ser al zable](
   mple nt ngStore: ReadableStore[Query, Seq[Cand date]],
  overr de val  dent f er: S m lar yEng neType,
  globalStats: StatsRece ver,
  eng neConf g: S m lar yEng neConf g,
   mCac Conf g: Opt on[ mCac Conf g[Query]] = None)
    extends S m lar yEng ne[Eng neQuery[Query], Cand date] {

  pr vate val scopedStats = globalStats.scope("s m lar yEng ne",  dent f er.toStr ng)

  def getScopedStats: StatsRece ver = scopedStats

  // Add  mcac  wrapper,  f spec f ed
  pr vate val store = {
     mCac Conf g match {
      case So (conf g) =>
        S m lar yEng ne.add mCac (
          underly ngStore =  mple nt ngStore,
           mCac Conf g = conf g,
          statsRece ver = scopedStats
        )
      case _ =>  mple nt ngStore
    }
  }

  overr de def getCand dates(
    eng neQuery: Eng neQuery[Query]
  ): Future[Opt on[Seq[Cand date]]] = {
    S m lar yEng ne.getFromFn(
      store.get,
      eng neQuery.storeQuery,
      eng neConf g,
      eng neQuery.params,
      scopedStats
    )
  }
}
