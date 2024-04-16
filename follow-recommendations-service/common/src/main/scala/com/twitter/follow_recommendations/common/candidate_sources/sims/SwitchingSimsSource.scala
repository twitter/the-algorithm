package com.tw ter.follow_recom ndat ons.common.cand date_s ces.s ms

 mport com.tw ter.f nagle.stats.NullStatsRece ver
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.follow_recom ndat ons.common.models.Cand dateUser
 mport com.tw ter.follow_recom ndat ons.common.models.HasS m larToContext
 mport com.tw ter. rm .model.Algor hm
 mport com.tw ter.product_m xer.core.funct onal_component.cand date_s ce.Cand dateS ce
 mport com.tw ter.product_m xer.core.model.common. dent f er.Cand dateS ce dent f er
 mport com.tw ter.st ch.St ch
 mport com.tw ter.t  l nes.conf gap .HasParams

 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class Sw ch ngS msS ce @ nject() (
  cac dDBV2S msStore: Cac dDBV2S msStore,
  cac dDBV2S msRefreshStore: Cac dDBV2S msRefreshStore,
  cac dS msExper  ntalStore: Cac dS msExper  ntalStore,
  cac dS msStore: Cac dS msStore,
  statsRece ver: StatsRece ver = NullStatsRece ver)
    extends Cand dateS ce[HasParams w h HasS m larToContext, Cand dateUser] {

  overr de val  dent f er: Cand dateS ce dent f er = Sw ch ngS msS ce. dent f er

  pr vate val stats = statsRece ver.scope("Sw ch ngS msS ce")
  pr vate val dbV2S msStoreCounter = stats.counter("DBV2S msStore")
  pr vate val dbV2S msRefreshStoreCounter = stats.counter("DBV2S msRefreshStore")
  pr vate val s msExper  ntalStoreCounter = stats.counter("S msExper  ntalStore")
  pr vate val s msStoreCounter = stats.counter("S msStore")

  overr de def apply(request: HasParams w h HasS m larToContext): St ch[Seq[Cand dateUser]] = {
    val selectedS msStore =
       f (request.params(S msS ceParams.EnableDBV2S msStore)) {
        dbV2S msStoreCounter. ncr()
        cac dDBV2S msStore
      } else  f (request.params(S msS ceParams.EnableDBV2S msRefreshStore)) {
        dbV2S msRefreshStoreCounter. ncr()
        cac dDBV2S msRefreshStore
      } else  f (request.params(S msS ceParams.EnableExper  ntalS msStore)) {
        s msExper  ntalStoreCounter. ncr()
        cac dS msExper  ntalStore
      } else {
        s msStoreCounter. ncr()
        cac dS msStore
      }
    stats.counter("total"). ncr()
    selectedS msStore(request)
  }
}

object Sw ch ngS msS ce {
  val  dent f er: Cand dateS ce dent f er = Cand dateS ce dent f er(Algor hm.S ms.toStr ng)
}
