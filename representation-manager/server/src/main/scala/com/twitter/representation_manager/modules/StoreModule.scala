package com.tw ter.representat on_manager.modules

 mport com.google. nject.Prov des
 mport javax. nject.S ngleton
 mport com.tw ter. nject.Tw terModule
 mport com.tw ter.dec der.Dec der
 mport com.tw ter.f nagle.mtls.aut nt cat on.Serv ce dent f er
 mport com.tw ter.representat on_manager.common.Representat onManagerDec der
 mport com.tw ter.storage.cl ent.manhattan.kv.ManhattanKVCl entMtlsParams

object StoreModule extends Tw terModule {
  @S ngleton
  @Prov des
  def prov desMhMtlsParams(
    serv ce dent f er: Serv ce dent f er
  ): ManhattanKVCl entMtlsParams = ManhattanKVCl entMtlsParams(serv ce dent f er)

  @S ngleton
  @Prov des
  def prov desRmsDec der(
    dec der: Dec der
  ): Representat onManagerDec der = Representat onManagerDec der(dec der)

}
