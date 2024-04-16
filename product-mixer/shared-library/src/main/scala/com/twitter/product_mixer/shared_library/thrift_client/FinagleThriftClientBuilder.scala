package com.tw ter.product_m xer.shared_l brary.thr ft_cl ent

 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter.f nagle.Thr ftMux
 mport com.tw ter.f nagle.mtls.aut nt cat on.Serv ce dent f er
 mport com.tw ter.f nagle.mtls.cl ent.MtlsStackCl ent._
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.f nagle.thr ft.Cl ent d
 mport com.tw ter.f nagle.thr ft.serv ce.F lterable
 mport com.tw ter.f nagle.thr ft.serv ce. thodPerEndpo ntBu lder
 mport com.tw ter.f nagle.thr ft.serv ce.Serv cePerEndpo ntBu lder
 mport com.tw ter.f nagle.thr ftmux. thodBu lder
 mport com.tw ter.ut l.Durat on
 mport org.apac .thr ft.protocol.TProtocolFactory

sealed tra   dempotency
case object Non dempotent extends  dempotency
case class  dempotent(maxExtraLoadPercent: Double) extends  dempotency

object F nagleThr ftCl entBu lder {

  /**
   * L brary to bu ld a F nagle Thr ft  thod per endpo nt cl ent  s a less error-prone way w n
   * compared to t  bu lders  n F nagle. T   s ach eved by requ r ng values for f elds that should
   * always be set  n pract ce. For example, request t  outs  n F nagle are unbounded w n not
   * expl c ly set, and t   thod requ res that t  out durat ons are passed  nto t   thod and
   * set on t  F nagle bu lder.
   *
   * Usage of
   * [[com.tw ter. nject.thr ft.modules.Thr ft thodBu lderCl entModule]]  s almost always preferred,
   * and t  Product M xer component l brary [[com.tw ter.product_m xer.component_l brary.module]]
   * package conta ns nu rous examples. Ho ver,  f mult ple vers ons of a cl ent are needed e.g.
   * for d fferent t  out sett ngs, t   thod  s useful to eas ly prov de mult ple var ants.
   *
   * @example
   * {{{
   *   f nal val SampleServ ceCl entNa  = "SampleServ ceCl ent"
   *   @Prov des
   *   @S ngleton
   *   @Na d(SampleServ ceCl entNa )
   *   def prov deSampleServ ceCl ent(
   *     serv ce dent f er: Serv ce dent f er,
   *     cl ent d: Cl ent d,
   *     statsRece ver: StatsRece ver,
   *   ): SampleServ ce. thodPerEndpo nt =
   *     bu ldF nagle thodPerEndpo nt[SampleServ ce.Serv cePerEndpo nt, SampleServ ce. thodPerEndpo nt](
   *       serv ce dent f er = serv ce dent f er,
   *       cl ent d = cl ent d,
   *       dest = "/s/sample/sample",
   *       label = "sample",
   *       statsRece ver = statsRece ver,
   *        dempotency =  dempotent(5.percent),
   *       t  outPerRequest = 200.m ll seconds,
   *       t  outTotal = 400.m ll seconds
   *     )
   * }}}
   * @param serv ce dent f er         Serv ce  D used to S2S Auth
   * @param cl ent d                  Cl ent  D
   * @param dest                      Dest nat on as a W ly path e.g. "/s/sample/sample"
   * @param label                     Label of t  cl ent
   * @param statsRece ver             Stats
   * @param  dempotency                dempotency semant cs of t  cl ent
   * @param t  outPerRequest         Thr ft cl ent t  out per request. T  F nagle default  s
   *                                  unbounded wh ch  s almost never opt mal.
   * @param t  outTotal              Thr ft cl ent total t  out. T  F nagle default  s
   *                                  unbounded wh ch  s almost never opt mal.
   *                                   f t  cl ent  s set as  dempotent, wh ch adds a
   *                                  [[com.tw ter.f nagle.cl ent.BackupRequestF lter]],
   *                                  be sure to leave enough room for t  backup request. A
   *                                  reasonable (albe  usually too large) start ng po nt  s to
   *                                  make t  total t  out 2x relat ve to t  per request t  out.
   *                                   f t  cl ent  s set as non- dempotent, t  total t  out and
   *                                  t  per request t  out should be t  sa , as t re w ll be
   *                                  no backup requests.
   * @param connectT  out            Thr ft cl ent transport connect t  out. T  F nagle default
   *                                  of one second  s reasonable but   lo r t  to match
   *                                  acqu s  onT  out for cons stency.
   * @param acqu s  onT  out        Thr ft cl ent sess on acqu s  on t  out. T  F nagle default
   *                                   s unbounded wh ch  s almost never opt mal.
   * @param protocolFactoryOverr de   Overr de t  default protocol factory
   *                                  e.g. [[org.apac .thr ft.protocol.TCompactProtocol.Factory]]
   * @param serv cePerEndpo ntBu lder  mpl c  serv ce per endpo nt bu lder
   * @param  thodPerEndpo ntBu lder   mpl c   thod per endpo nt bu lder
   *
   * @see [[https://tw ter.g hub. o/f nagle/gu de/ thodBu lder.html user gu de]]
   * @see [[https://tw ter.g hub. o/f nagle/gu de/ thodBu lder.html# dempotency user gu de]]
   * @return  thod per endpo nt F nagle Thr ft Cl ent
   */
  def bu ldF nagle thodPerEndpo nt[
    Serv cePerEndpo nt <: F lterable[Serv cePerEndpo nt],
     thodPerEndpo nt
  ](
    serv ce dent f er: Serv ce dent f er,
    cl ent d: Cl ent d,
    dest: Str ng,
    label: Str ng,
    statsRece ver: StatsRece ver,
     dempotency:  dempotency,
    t  outPerRequest: Durat on,
    t  outTotal: Durat on,
    connectT  out: Durat on = 500.m ll seconds,
    acqu s  onT  out: Durat on = 500.m ll seconds,
    protocolFactoryOverr de: Opt on[TProtocolFactory] = None,
  )(
     mpl c  serv cePerEndpo ntBu lder: Serv cePerEndpo ntBu lder[Serv cePerEndpo nt],
     thodPerEndpo ntBu lder:  thodPerEndpo ntBu lder[Serv cePerEndpo nt,  thodPerEndpo nt]
  ):  thodPerEndpo nt = {
    val serv ce: Serv cePerEndpo nt = bu ldF nagleServ cePerEndpo nt(
      serv ce dent f er = serv ce dent f er,
      cl ent d = cl ent d,
      dest = dest,
      label = label,
      statsRece ver = statsRece ver,
       dempotency =  dempotency,
      t  outPerRequest = t  outPerRequest,
      t  outTotal = t  outTotal,
      connectT  out = connectT  out,
      acqu s  onT  out = acqu s  onT  out,
      protocolFactoryOverr de = protocolFactoryOverr de
    )

    Thr ftMux.Cl ent. thodPerEndpo nt(serv ce)
  }

  /**
   * Bu ld a F nagle Thr ft serv ce per endpo nt cl ent.
   *
   * @note [[bu ldF nagle thodPerEndpo nt]] should be preferred over t  serv ce per endpo nt var ant
   *
   * @param serv ce dent f er       Serv ce  D used to S2S Auth
   * @param cl ent d                Cl ent  D
   * @param dest                    Dest nat on as a W ly path e.g. "/s/sample/sample"
   * @param label                   Label of t  cl ent
   * @param statsRece ver           Stats
   * @param  dempotency              dempotency semant cs of t  cl ent
   * @param t  outPerRequest       Thr ft cl ent t  out per request. T  F nagle default  s
   *                                unbounded wh ch  s almost never opt mal.
   * @param t  outTotal            Thr ft cl ent total t  out. T  F nagle default  s
   *                                unbounded wh ch  s almost never opt mal.
   *                                 f t  cl ent  s set as  dempotent, wh ch adds a
   *                                [[com.tw ter.f nagle.cl ent.BackupRequestF lter]],
   *                                be sure to leave enough room for t  backup request. A
   *                                reasonable (albe  usually too large) start ng po nt  s to
   *                                make t  total t  out 2x relat ve to t  per request t  out.
   *                                 f t  cl ent  s set as non- dempotent, t  total t  out and
   *                                t  per request t  out should be t  sa , as t re w ll be
   *                                no backup requests.
   * @param connectT  out          Thr ft cl ent transport connect t  out. T  F nagle default
   *                                of one second  s reasonable but   lo r t  to match
   *                                acqu s  onT  out for cons stency.
   * @param acqu s  onT  out      Thr ft cl ent sess on acqu s  on t  out. T  F nagle default
   *                                 s unbounded wh ch  s almost never opt mal.
   * @param protocolFactoryOverr de Overr de t  default protocol factory
   *                                e.g. [[org.apac .thr ft.protocol.TCompactProtocol.Factory]]
   *
   * @return serv ce per endpo nt F nagle Thr ft Cl ent
   */
  def bu ldF nagleServ cePerEndpo nt[Serv cePerEndpo nt <: F lterable[Serv cePerEndpo nt]](
    serv ce dent f er: Serv ce dent f er,
    cl ent d: Cl ent d,
    dest: Str ng,
    label: Str ng,
    statsRece ver: StatsRece ver,
     dempotency:  dempotency,
    t  outPerRequest: Durat on,
    t  outTotal: Durat on,
    connectT  out: Durat on = 500.m ll seconds,
    acqu s  onT  out: Durat on = 500.m ll seconds,
    protocolFactoryOverr de: Opt on[TProtocolFactory] = None,
  )(
     mpl c  serv cePerEndpo ntBu lder: Serv cePerEndpo ntBu lder[Serv cePerEndpo nt]
  ): Serv cePerEndpo nt = {
    val thr ftMux: Thr ftMux.Cl ent = Thr ftMux.cl ent
      .w hMutualTls(serv ce dent f er)
      .w hCl ent d(cl ent d)
      .w hLabel(label)
      .w hStatsRece ver(statsRece ver)
      .w hTransport.connectT  out(connectT  out)
      .w hSess on.acqu s  onT  out(acqu s  onT  out)

    val protocolThr ftMux: Thr ftMux.Cl ent = protocolFactoryOverr de
      .map { protocolFactory =>
        thr ftMux.w hProtocolFactory(protocolFactory)
      }.getOrElse(thr ftMux)

    val  thodBu lder:  thodBu lder = protocolThr ftMux
      . thodBu lder(dest)
      .w hT  outPerRequest(t  outPerRequest)
      .w hT  outTotal(t  outTotal)

    val  dempotency thodBu lder:  thodBu lder =  dempotency match {
      case Non dempotent =>  thodBu lder.non dempotent
      case  dempotent(maxExtraLoad) =>  thodBu lder. dempotent(maxExtraLoad = maxExtraLoad)
    }

     dempotency thodBu lder.serv cePerEndpo nt[Serv cePerEndpo nt]
  }
}
