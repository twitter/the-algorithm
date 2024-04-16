package com.tw ter.ho _m xer.module

 mport com.google. nject.Prov des
 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter.f nagle.mtls.aut nt cat on.Serv ce dent f er
 mport com.tw ter.f nagle.serv ce.Retr es
 mport com.tw ter.f nagle.serv ce.RetryPol cy
 mport com.tw ter.f nagle.ssl.Opportun st cTls
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.ho _m xer.param.Ho M xer nject onNa s.Batc dStratoCl entW hModerateT  out
 mport com.tw ter. nject.Tw terModule
 mport com.tw ter.strato.cl ent.Cl ent
 mport com.tw ter.strato.cl ent.Strato
 mport com.tw ter.ut l.Try
 mport javax. nject.Na d
 mport javax. nject.S ngleton

object Opt m zedStratoCl entModule extends Tw terModule {

  pr vate val ModerateStratoServerCl entRequestT  out = 500.m ll s

  pr vate val DefaultRetryPart alFunct on: Part alFunct on[Try[Noth ng], Boolean] =
    RetryPol cy.T  outAndWr eExcept onsOnly
      .orElse(RetryPol cy.ChannelClosedExcept onsOnly)

  protected def mkRetryPol cy(tr es:  nt): RetryPol cy[Try[Noth ng]] =
    RetryPol cy.tr es(tr es, DefaultRetryPart alFunct on)

  @S ngleton
  @Prov des
  @Na d(Batc dStratoCl entW hModerateT  out)
  def prov desStratoCl ent(
    serv ce dent f er: Serv ce dent f er,
    statsRece ver: StatsRece ver
  ): Cl ent = {
    Strato.cl ent
      .w hMutualTls(serv ce dent f er, opportun st cLevel = Opportun st cTls.Requ red)
      .w hSess on.acqu s  onT  out(500.m ll seconds)
      .w hRequestT  out(ModerateStratoServerCl entRequestT  out)
      .w hPerRequestT  out(ModerateStratoServerCl entRequestT  out)
      .w hRpcBatchS ze(5)
      .conf gured(Retr es.Pol cy(mkRetryPol cy(1)))
      .w hStatsRece ver(statsRece ver.scope("strato_cl ent"))
      .bu ld()
  }
}
