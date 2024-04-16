package com.tw ter.follow_recom ndat ons.common.cand date_s ces.base

 mport com.tw ter.esc rb rd.ut l.st chcac .St chCac 
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.product_m xer.core.funct onal_component.cand date_s ce.Cand dateS ce
 mport com.tw ter.product_m xer.core.model.common. dent f er.Cand dateS ce dent f er
 mport com.tw ter.st ch.St ch
 mport com.tw ter.ut l.Durat on

class Cac dCand dateS ce[K <: Object, V <: Object](
  cand dateS ce: Cand dateS ce[K, V],
  maxCac S ze:  nt,
  cac TTL: Durat on,
  statsRece ver: StatsRece ver,
  overr de val  dent f er: Cand dateS ce dent f er)
    extends Cand dateS ce[K, V] {

  pr vate val cac  = St chCac [K, Seq[V]](
    maxCac S ze = maxCac S ze,
    ttl = cac TTL,
    statsRece ver = statsRece ver.scope( dent f er.na , "cac "),
    underly ngCall = (k: K) => cand dateS ce(k)
  )

  overr de def apply(target: K): St ch[Seq[V]] = cac .readThrough(target)
}
