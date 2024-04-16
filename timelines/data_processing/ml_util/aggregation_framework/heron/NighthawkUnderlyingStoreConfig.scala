package com.tw ter.t  l nes.data_process ng.ml_ut l.aggregat on_fra work. ron

 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter.f nagle.mtls.aut nt cat on.EmptyServ ce dent f er
 mport com.tw ter.f nagle.mtls.aut nt cat on.Serv ce dent f er
 mport com.tw ter.f nagle.ssl.Opportun st cTls
 mport com.tw ter.storehaus_ nternal.n ghthawk_kv.Cac Cl entN ghthawkConf g
 mport com.tw ter.storehaus_ nternal.ut l.TTL
 mport com.tw ter.storehaus_ nternal.ut l.TableNa 
 mport com.tw ter.summ ngb rd_ nternal.runner.store_conf g.Onl neStoreOnlyConf g
 mport com.tw ter.ut l.Durat on

case class N ghthawkUnderly ngStoreConf g(
  serversetPath: Str ng = "",
  tableNa : Str ng = "",
  cac TTL: Durat on = 1.day)
    extends Onl neStoreOnlyConf g[Cac Cl entN ghthawkConf g] {

  def onl ne: Cac Cl entN ghthawkConf g = onl ne(EmptyServ ce dent f er)

  def onl ne(
    serv ce dent f er: Serv ce dent f er = EmptyServ ce dent f er
  ): Cac Cl entN ghthawkConf g =
    Cac Cl entN ghthawkConf g(
      serversetPath,
      TableNa (tableNa ),
      TTL(cac TTL),
      serv ce dent f er = serv ce dent f er,
      opportun st cTlsLevel = Opportun st cTls.Requ red
    )
}
