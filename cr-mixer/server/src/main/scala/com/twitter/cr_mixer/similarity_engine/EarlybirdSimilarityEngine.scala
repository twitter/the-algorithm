package com.tw ter.cr_m xer.s m lar y_eng ne

 mport com.tw ter.cr_m xer.model.T etW hAuthor
 mport com.tw ter.cr_m xer.s m lar y_eng ne.S m lar yEng ne.S m lar yEng neConf g
 mport com.tw ter.cr_m xer.thr ftscala.S m lar yEng neType
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.storehaus.ReadableStore
 mport com.tw ter.ut l.Future

class Earlyb rdS m lar yEng ne[
  Query,
  Earlyb rdS m lar yEng neStore <: ReadableStore[Query, Seq[T etW hAuthor]]
](
   mple nt ngStore: Earlyb rdS m lar yEng neStore,
  overr de val  dent f er: S m lar yEng neType,
  globalStats: StatsRece ver,
  eng neConf g: S m lar yEng neConf g,
) extends S m lar yEng ne[Eng neQuery[Query], T etW hAuthor] {
  pr vate val scopedStats = globalStats.scope("s m lar yEng ne",  dent f er.toStr ng)

  def getScopedStats: StatsRece ver = scopedStats

  def getCand dates(query: Eng neQuery[Query]): Future[Opt on[Seq[T etW hAuthor]]] = {
    S m lar yEng ne.getFromFn(
       mple nt ngStore.get,
      query.storeQuery,
      eng neConf g,
      query.params,
      scopedStats
    )
  }
}
