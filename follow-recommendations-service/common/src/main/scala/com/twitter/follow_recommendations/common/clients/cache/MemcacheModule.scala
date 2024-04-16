package com.tw ter.follow_recom ndat ons.common.cl ents.cac 

 mport com.google. nject.Prov des
 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter.f nagle. mcac d
 mport com.tw ter.f nagle. mcac d.Cl ent
 mport com.tw ter.f nagle.mtls.cl ent.MtlsStackCl ent._
 mport com.tw ter.f nagle.mtls.aut nt cat on.Serv ce dent f er
 mport com.tw ter.f nagle.serv ce.Retr es
 mport com.tw ter.f nagle.serv ce.RetryPol cy
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter. nject.Tw terModule
 mport javax. nject.S ngleton

object  mcac Module extends Tw terModule {
  @Prov des
  @S ngleton
  def prov de mcac Cl ent(
    serv ce dent f er: Serv ce dent f er,
    statsRece ver: StatsRece ver,
  ): Cl ent = {
     mcac d.cl ent
      .w hMutualTls(serv ce dent f er)
      .w hStatsRece ver(statsRece ver.scope("t mcac "))
      .w hTransport.connectT  out(1.seconds)
      .w hRequestT  out(1.seconds)
      .w hSess on.acqu s  onT  out(10.seconds)
      .conf gured(Retr es.Pol cy(RetryPol cy.tr es(1)))
  }
}
