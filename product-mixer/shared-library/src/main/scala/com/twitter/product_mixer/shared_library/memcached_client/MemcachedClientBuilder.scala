package com.tw ter.product_m xer.shared_l brary. mcac d_cl ent

 mport com.tw ter.f nagle. mcac d.Cl ent
 mport com.tw ter.f nagle. mcac d.protocol.Command
 mport com.tw ter.f nagle. mcac d.protocol.Response
 mport com.tw ter.f nagle.mtls.cl ent.MtlsStackCl ent._
 mport com.tw ter.f nagle.serv ce.RetryExcept onsF lter
 mport com.tw ter.f nagle.serv ce.RetryPol cy
 mport com.tw ter.f nagle.serv ce.T  outF lter
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.f nagle.ut l.DefaultT  r
 mport com.tw ter.f nagle.GlobalRequestT  outExcept on
 mport com.tw ter.f nagle. mcac d
 mport com.tw ter.f nagle.l veness.Fa lureAccrualFactory
 mport com.tw ter.f nagle.l veness.Fa lureAccrualPol cy
 mport com.tw ter.f nagle.mtls.aut nt cat on.Serv ce dent f er
 mport com.tw ter.hash ng.KeyHas r
 mport com.tw ter.ut l.Durat on

object  mcac dCl entBu lder {

  /**
   * Bu ld a F nagle  mcac d [[Cl ent]].
   *
   * @param destNa              Dest nat on as a W ly path e.g. "/s/sample/sample".
   * @param numTr es             Max mum number of t  s to try.
   * @param requestT  out       Thr ft cl ent t  out per request. T  F nagle default
   *                              s unbounded wh ch  s almost never opt mal.
   * @param globalT  out        Thr ft cl ent total t  out. T  F nagle default  s
   *                             unbounded wh ch  s almost never opt mal.
   * @param connectT  out       Thr ft cl ent transport connect t  out. T  F nagle
   *                             default of one second  s reasonable but   lo r t 
   *                             to match acqu s  onT  out for cons stency.
   * @param acqu s  onT  out   Thr ft cl ent sess on acqu s  on t  out. T  F nagle
   *                             default  s unbounded wh ch  s almost never opt mal.
   * @param serv ce dent f er    Serv ce  D used to S2S Auth.
   * @param statsRece ver        Stats.
   * @param fa lureAccrualPol cy Pol cy to determ ne w n to mark a cac  server as dead.
   *                              mcac d cl ent w ll use default fa lure accrual pol cy
   *                              f    s not set.
   * @param keyHas r            Hash algor hm that has s a key  nto a 32-b  or 64-b 
   *                             number.  mcac d cl ent w ll use default hash algor hm
   *                              f    s not set.
   *
   * @see [[https://confluence.tw ter.b z/d splay/CACHE/F nagle- mcac d+User+Gu de user gu de]]
   * @return F nagle  mcac d [[Cl ent]]
   */
  def bu ld mcac dCl ent(
    destNa : Str ng,
    numTr es:  nt,
    requestT  out: Durat on,
    globalT  out: Durat on,
    connectT  out: Durat on,
    acqu s  onT  out: Durat on,
    serv ce dent f er: Serv ce dent f er,
    statsRece ver: StatsRece ver,
    fa lureAccrualPol cy: Opt on[Fa lureAccrualPol cy] = None,
    keyHas r: Opt on[KeyHas r] = None
  ): Cl ent = {
    bu ldRaw mcac dCl ent(
      numTr es,
      requestT  out,
      globalT  out,
      connectT  out,
      acqu s  onT  out,
      serv ce dent f er,
      statsRece ver,
      fa lureAccrualPol cy,
      keyHas r
    ).newR chCl ent(destNa )
  }

  def bu ldRaw mcac dCl ent(
    numTr es:  nt,
    requestT  out: Durat on,
    globalT  out: Durat on,
    connectT  out: Durat on,
    acqu s  onT  out: Durat on,
    serv ce dent f er: Serv ce dent f er,
    statsRece ver: StatsRece ver,
    fa lureAccrualPol cy: Opt on[Fa lureAccrualPol cy] = None,
    keyHas r: Opt on[KeyHas r] = None
  ):  mcac d.Cl ent = {
    val globalT  outF lter = new T  outF lter[Command, Response](
      t  out = globalT  out,
      except on = new GlobalRequestT  outExcept on(globalT  out),
      t  r = DefaultT  r)
    val retryF lter = new RetryExcept onsF lter[Command, Response](
      RetryPol cy.tr es(numTr es),
      DefaultT  r,
      statsRece ver)

    val cl ent =  mcac d.cl ent.w hTransport
      .connectT  out(connectT  out)
      .w hMutualTls(serv ce dent f er)
      .w hSess on
      .acqu s  onT  out(acqu s  onT  out)
      .w hRequestT  out(requestT  out)
      .w hStatsRece ver(statsRece ver)
      .f ltered(globalT  outF lter.andT n(retryF lter))

    (keyHas r, fa lureAccrualPol cy) match {
      case (So (has r), So (pol cy)) =>
        cl ent
          .w hKeyHas r(has r)
          .conf gured(Fa lureAccrualFactory.Param(() => pol cy))
      case (So (has r), None) =>
        cl ent
          .w hKeyHas r(has r)
      case (None, So (pol cy)) =>
        cl ent
          .conf gured(Fa lureAccrualFactory.Param(() => pol cy))
      case _ =>
        cl ent
    }
  }
}
