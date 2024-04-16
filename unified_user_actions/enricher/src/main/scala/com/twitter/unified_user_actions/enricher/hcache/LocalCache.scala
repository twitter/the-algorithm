package com.tw ter.un f ed_user_act ons.enr c r.hcac 

 mport com.google.common.cac .Cac 
 mport com.tw ter.cac .FutureCac 
 mport com.tw ter.cac .guava.GuavaCac 
 mport com.tw ter.f nagle.stats.NullStatsRece ver
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.ut l.Future

/**
 * A local cac   mple ntat on us ng GuavaCac .
 * Underneath   uses a custom zed vers on of t  Ev ct ngCac  to 1) deal w h Futures, 2) add more stats.
 */
class LocalCac [K, V](
  underly ng: Cac [K, Future[V]],
  statsRece ver: StatsRece ver = NullStatsRece ver) {

  pr vate[t ] val cac  = new GuavaCac (underly ng)
  pr vate[t ] val ev ct ngCac : FutureCac [K, V] =
    ObservedEv ct ngCac (underly ng = cac , statsRece ver = statsRece ver)

  def getOrElseUpdate(key: K)(fn: => Future[V]): Future[V] = ev ct ngCac .getOrElseUpdate(key)(fn)

  def get(key: K): Opt on[Future[V]] = ev ct ngCac .get(key)

  def ev ct(key: K, value: Future[V]): Boolean = ev ct ngCac .ev ct(key, value)

  def set(key: K, value: Future[V]): Un  = ev ct ngCac .set(key, value)

  def reset(): Un  =
    underly ng. nval dateAll()

  def s ze:  nt = ev ct ngCac .s ze
}
