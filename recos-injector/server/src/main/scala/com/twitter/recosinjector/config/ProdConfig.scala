package com.tw ter.recos njector.conf g

 mport com.tw ter.f nagle.mtls.aut nt cat on.Serv ce dent f er
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.f nagle.thr ft.Cl ent d
 mport com.tw ter.logg ng.Logger
 mport com.tw ter.recos njector.dec der.Recos njectorDec der

case class ProdConf g(
  overr de val serv ce dent f er: Serv ce dent f er
)( mpl c  val statsRece ver: StatsRece ver) extends {
  // Due to tra   n  al zat on log c  n Scala, any abstract  mbers declared  n Conf g or
  // DeployConf g should be declared  n t  block. Ot rw se t  abstract  mber m ght  n  al ze
  // to null  f  nvoked before before object creat on f n sh ng.

  val recos njectorThr ftCl ent d = Cl ent d("recos- njector.prod")

  val outputKafkaTop cPref x = "recos_ njector"

  val log = Logger("ProdConf g")

  val recos njectorCoreSvcsCac Dest = "/srv#/prod/local/cac /recos_ tadata"

  val recos njectorDec der = Recos njectorDec der(
     sProd = true,
    dataCenter = serv ce dent f er.zone
  )

} w h DeployConf g
