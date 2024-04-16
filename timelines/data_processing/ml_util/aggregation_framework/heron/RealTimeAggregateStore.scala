package com.tw ter.t  l nes.data_process ng.ml_ut l.aggregat on_fra work. ron

 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter.f nagle.mtls.aut nt cat on.EmptyServ ce dent f er
 mport com.tw ter.f nagle.mtls.aut nt cat on.Serv ce dent f er
 mport com.tw ter.storehaus_ nternal. mcac .Connect onConf g
 mport com.tw ter.storehaus_ nternal. mcac . mcac Conf g
 mport com.tw ter.storehaus_ nternal.ut l.KeyPref x
 mport com.tw ter.storehaus_ nternal.ut l.TTL
 mport com.tw ter.storehaus_ nternal.ut l.ZkEndPo nt
 mport com.tw ter.summ ngb rd_ nternal.runner.store_conf g.Onl neStoreOnlyConf g
 mport com.tw ter.t  l nes.data_process ng.ml_ut l.aggregat on_fra work.AggregateStore
 mport com.tw ter.ut l.Durat on

object RealT  AggregateStore {
  val twCac W lyPref x = "/srv#" // s2s  s only supported for w ly path

  def makeEndpo nt(
     mcac DataSet: Str ng,
     sProd: Boolean,
    twCac W lyPref x: Str ng = twCac W lyPref x
  ): Str ng = {
    val env =  f ( sProd) "prod" else "test"
    s"$twCac W lyPref x/$env/local/cac /$ mcac DataSet"
  }
}

case class RealT  AggregateStore(
   mcac DataSet: Str ng,
   sProd: Boolean = false,
  cac TTL: Durat on = 1.day)
    extends Onl neStoreOnlyConf g[ mcac Conf g]
    w h AggregateStore {
   mport RealT  AggregateStore._

  overr de val na : Str ng = ""
  val storeKeyPref x: KeyPref x = KeyPref x(na )
  val  mcac ZkEndPo nt: Str ng = makeEndpo nt( mcac DataSet,  sProd)

  def onl ne:  mcac Conf g = onl ne(serv ce dent f er = EmptyServ ce dent f er)

  def onl ne(serv ce dent f er: Serv ce dent f er = EmptyServ ce dent f er):  mcac Conf g =
    new  mcac Conf g {
      val endpo nt = ZkEndPo nt( mcac ZkEndPo nt)
      overr de val connect onConf g =
        Connect onConf g(endpo nt, serv ce dent f er = serv ce dent f er)
      overr de val keyPref x = storeKeyPref x
      overr de val ttl = TTL(Durat on.fromM ll seconds(cac TTL. nM ll s))
    }
}
