package com.tw ter.follow_recom ndat ons.common.cand date_s ces.s ms

 mport com.google. nject.S ngleton
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter. rm .model.Algor hm
 mport com.tw ter.product_m xer.core.model.common. dent f er.Cand dateS ce dent f er
 mport com.tw ter.strato.generated.cl ent.onboard ng.userrecs.NewS msRefreshOnUserCl entColumn
 mport com.tw ter.ut l.Durat on

 mport javax. nject. nject

@S ngleton
class DBV2S msRefreshStore @ nject() (
  newS msRefreshOnUserCl entColumn: NewS msRefreshOnUserCl entColumn)
    extends StratoBasedS msCand dateS ceW hUn V ew(
      fetc r = newS msRefreshOnUserCl entColumn.fetc r,
       dent f er = DBV2S msRefreshStore. dent f er)

@S ngleton
class Cac dDBV2S msRefreshStore @ nject() (
  newS msRefreshOnUserCl entColumn: NewS msRefreshOnUserCl entColumn,
  statsRece ver: StatsRece ver)
    extends Cac BasedS msStore(
       d = DBV2S msRefreshStore. dent f er,
      fetc r = newS msRefreshOnUserCl entColumn.fetc r,
      maxCac S ze = DBV2S msRefreshStore.MaxCac S ze,
      cac Ttl = DBV2S msRefreshStore.Cac TTL,
      statsRece ver = statsRece ver.scope("Cac dDBV2S msRefreshStore", "cac ")
    )

object DBV2S msRefreshStore {
  val  dent f er = Cand dateS ce dent f er(Algor hm.S ms.toStr ng)
  val MaxCac S ze = 5000
  val Cac TTL: Durat on = Durat on.fromH s(24)
}
