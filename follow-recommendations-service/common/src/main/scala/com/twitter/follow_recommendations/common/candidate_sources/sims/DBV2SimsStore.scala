package com.tw ter.follow_recom ndat ons.common.cand date_s ces.s ms

 mport com.google. nject.S ngleton
 mport com.google. nject.na .Na d
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.follow_recom ndat ons.common.constants.Gu ceNa dConstants
 mport com.tw ter. rm .cand date.thr ftscala.Cand dates
 mport com.tw ter. rm .model.Algor hm
 mport com.tw ter.product_m xer.core.model.common. dent f er.Cand dateS ce dent f er
 mport com.tw ter.strato.cl ent.Fetc r
 mport com.tw ter.ut l.Durat on

 mport javax. nject. nject

@S ngleton
class DBV2S msStore @ nject() (
  @Na d(Gu ceNa dConstants.DBV2_S MS_FETCHER) fetc r: Fetc r[Long, Un , Cand dates])
    extends StratoBasedS msCand dateS ceW hUn V ew(
      fetc r,
       dent f er = DBV2S msStore. dent f er)

@S ngleton
class Cac dDBV2S msStore @ nject() (
  @Na d(Gu ceNa dConstants.DBV2_S MS_FETCHER) fetc r: Fetc r[Long, Un , Cand dates],
  statsRece ver: StatsRece ver)
    extends Cac BasedS msStore(
       d = DBV2S msStore. dent f er,
      fetc r = fetc r,
      maxCac S ze = DBV2S msStore.MaxCac S ze,
      cac Ttl = DBV2S msStore.Cac TTL,
      statsRece ver = statsRece ver.scope("Cac dDBV2S msStore", "cac ")
    )

object DBV2S msStore {
  val  dent f er = Cand dateS ce dent f er(Algor hm.S ms.toStr ng)
  val MaxCac S ze = 1000
  val Cac TTL: Durat on = Durat on.fromH s(24)
}
