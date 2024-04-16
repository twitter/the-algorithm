package com.tw ter.follow_recom ndat ons.common.cand date_s ces.s ms

 mport com.google. nject.S ngleton
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter. rm .model.Algor hm
 mport com.tw ter.product_m xer.core.model.common. dent f er.Cand dateS ce dent f er
 mport com.tw ter.strato.generated.cl ent.recom ndat ons.s m lar y.S m larUsersByS msExper  ntalOnUserCl entColumn
 mport com.tw ter.ut l.Durat on

 mport javax. nject. nject

@S ngleton
class S msExper  ntalStore @ nject() (
  s msExper  ntalOnUserCl entColumn: S m larUsersByS msExper  ntalOnUserCl entColumn)
    extends StratoBasedS msCand dateS ceW hUn V ew(
      fetc r = s msExper  ntalOnUserCl entColumn.fetc r,
       dent f er = S msExper  ntalStore. dent f er
    )

@S ngleton
class Cac dS msExper  ntalStore @ nject() (
  s msExper  ntalOnUserCl entColumn: S m larUsersByS msExper  ntalOnUserCl entColumn,
  statsRece ver: StatsRece ver)
    extends Cac BasedS msStore(
       d = S msExper  ntalStore. dent f er,
      fetc r = s msExper  ntalOnUserCl entColumn.fetc r,
      maxCac S ze = S msExper  ntalStore.MaxCac S ze,
      cac Ttl = S msExper  ntalStore.Cac TTL,
      statsRece ver = statsRece ver.scope("Cac dS msExper  ntalStore", "cac ")
    )

object S msExper  ntalStore {
  val  dent f er = Cand dateS ce dent f er(Algor hm.S ms.toStr ng)
  val MaxCac S ze = 1000
  val Cac TTL: Durat on = Durat on.fromH s(12)
}
