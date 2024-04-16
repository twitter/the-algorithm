package com.tw ter.t  l nes.pred ct on.common.aggregates.real_t  

 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.storehaus.Repl catedReadableStore
 mport com.tw ter.storehaus.Store
 mport com.tw ter.t  l nes.cl ents. mcac _common._
 mport com.tw ter.t  l nes.ut l.Fa lOpenHandler
 mport com.tw ter.ut l.Future

object ServedFeatures mcac Conf gBu lder {
  def getTwCac Dest nat on(cluster: Str ng,  sProd: Boolean = false): Str ng =
     f (! sProd) {
      s"/srv#/test/$cluster/cac //t mcac _t  l nes_served_features_cac "
    } else {
      s"/srv#/prod/$cluster/cac /t  l nes_served_features"
    }

  /**
   * @cluster T  DC of t  cac  that t  cl ent w ll send requests to. T 
   *   can be d fferent to t  DC w re t  summ ngb rd job  s runn ng  n.
   * @ sProd  Def ne  f t  cl ent  s part of a product on summ ngb rd job as
   *   d fferent accesspo nts w ll need to be chosen.
   */
  def bu ld(cluster: Str ng,  sProd: Boolean = false): Storehaus mcac Conf g =
    Storehaus mcac Conf g(
      destNa  = getTwCac Dest nat on(cluster,  sProd),
      keyPref x = "",
      requestT  out = 200.m ll seconds,
      numTr es = 2,
      globalT  out = 400.m ll seconds,
      tcpConnectT  out = 200.m ll seconds,
      connect onAcqu s  onT  out = 200.m ll seconds,
      numPend ngRequests = 1000,
       sReadOnly = false
    )
}

/**
 *  f lookup key does not ex st locally, make a call to t  repl cated store(s).
 *  f value ex sts remotely, wr e t  f rst returned value to t  local store
 * and return  . Map any except ons to None so that t  subsequent operat ons
 * may proceed.
 */
class LocallyRepl catedStore[-K, V](
  localStore: Store[K, V],
  remoteStore: Repl catedReadableStore[K, V],
  scopedStatsRece ver: StatsRece ver)
    extends Store[K, V] {
  pr vate[t ] val fa lOpenHandler = new Fa lOpenHandler(scopedStatsRece ver.scope("fa lOpen"))
  pr vate[t ] val localFa lsCounter = scopedStatsRece ver.counter("localFa ls")
  pr vate[t ] val localWr esCounter = scopedStatsRece ver.counter("localWr es")
  pr vate[t ] val remoteFa lsCounter = scopedStatsRece ver.counter("remoteFa ls")

  overr de def get(k: K): Future[Opt on[V]] =
    fa lOpenHandler {
      localStore
        .get(k)
        .flatMap {
          case So (v) => Future.value(So (v))
          case _ => {
            localFa lsCounter. ncr()
            val repl catedOptFu = remoteStore.get(k)
            // async wr e  f result  s not empty
            repl catedOptFu.onSuccess {
              case So (v) => {
                localWr esCounter. ncr()
                localStore.put((k, So (v)))
              }
              case _ => {
                remoteFa lsCounter. ncr()
                Un 
              }
            }
            repl catedOptFu
          }
        }
    } { _: Throwable => Future.None }
}
