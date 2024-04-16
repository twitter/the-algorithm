package com.tw ter.ho _m xer.module

 mport com.google. nject.Prov des
 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter.f nagle.Thr ftMux
 mport com.tw ter.f nagle.bu lder.Cl entBu lder
 mport com.tw ter.f nagle.mtls.aut nt cat on.Serv ce dent f er
 mport com.tw ter.f nagle.mtls.cl ent.MtlsStackCl ent._
 mport com.tw ter.f nagle.serv ce.RetryPol cy
 mport com.tw ter.f nagle.ssl.Opportun st cTls
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter. nject.Tw terModule
 mport com.tw ter.manhattan.v2.thr ftscala.{ManhattanCoord nator => ManhattanV2}
 mport com.tw ter.t  l nem xer.cl ents.manhattan. nject on toryCl ent
 mport com.tw ter.t  l nem xer.cl ents.manhattan.ManhattanDatasetConf g
 mport com.tw ter.t  l nes.cl ents.manhattan.Dataset
 mport com.tw ter.t  l nes.cl ents.manhattan.ManhattanCl ent
 mport com.tw ter.t  l nes.ut l.stats.RequestScope
 mport javax. nject.S ngleton
 mport org.apac .thr ft.protocol.TB naryProtocol
 mport com.tw ter.t  l nes.conf g.T  l nesUnderly ngCl entConf gurat on.ConnectT  out
 mport com.tw ter.t  l nes.conf g.T  l nesUnderly ngCl entConf gurat on.TCPConnectT  out

object  nject on toryCl entModule extends Tw terModule {
  pr vate val ProdDataset = "suggest on_ tory"
  pr vate val Stag ngDataset = "suggest on_ tory_nonprod"
  pr vate val App d = "tw ter_suggests"
  pr vate val Serv ceNa  = "manhattan.o ga"
  pr vate val O gaManhattanDest = "/s/manhattan/o ga.nat ve-thr ft"
  pr vate val  nject onRequestScope = RequestScope(" nject on toryCl ent")
  pr vate val RequestT  out = 75.m ll s
  pr vate val T  out = 150.m ll s

  val retryPol cy = RetryPol cy.tr es(
    2,
    RetryPol cy.T  outAndWr eExcept onsOnly
      .orElse(RetryPol cy.ChannelClosedExcept onsOnly))

  @Prov des
  @S ngleton
  def prov des nject on toryCl ent(
    serv ce dent f er: Serv ce dent f er,
    statsRece ver: StatsRece ver
  ) = {
    val dataset = serv ce dent f er.env ron nt.toLo rCase match {
      case "prod" => ProdDataset
      case _ => Stag ngDataset
    }

    val thr ftMuxCl ent = Cl entBu lder()
      .na (Serv ceNa )
      .daemon(daemon ze = true)
      .fa lFast(enabled = true)
      .retryPol cy(retryPol cy)
      .tcpConnectT  out(TCPConnectT  out)
      .connectT  out(ConnectT  out)
      .dest(O gaManhattanDest)
      .requestT  out(RequestT  out)
      .t  out(T  out)
      .stack(Thr ftMux.cl ent
        .w hMutualTls(serv ce dent f er)
        .w hOpportun st cTls(Opportun st cTls.Requ red))
      .bu ld()

    val manhattanO gaCl ent = new ManhattanV2.F nagledCl ent(
      serv ce = thr ftMuxCl ent,
      protocolFactory = new TB naryProtocol.Factory(),
      serv ceNa  = Serv ceNa ,
    )

    val readOnlyMhCl ent = new ManhattanCl ent(
      app d = App d,
      manhattan = manhattanO gaCl ent,
      requestScope =  nject onRequestScope,
      serv ceNa  = Serv ceNa ,
      statsRece ver = statsRece ver
    ).readOnly

    val mhDatasetConf g = new ManhattanDatasetConf g {
      overr de val Suggest on toryDataset = Dataset(dataset)
    }

    new  nject on toryCl ent(
      readOnlyMhCl ent,
      mhDatasetConf g
    )
  }
}
