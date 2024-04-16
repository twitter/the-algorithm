package com.tw ter.cr_m xer.module

 mport com.google. nject.Prov des
 mport com.tw ter.f nagle.mtls.aut nt cat on.Serv ce dent f er
 mport com.tw ter. nject.Tw terModule
 mport com.tw ter.storage.cl ent.manhattan.kv.ManhattanKVCl entMtlsParams
 mport javax. nject.S ngleton

object MHMtlsParamsModule extends Tw terModule {
  @S ngleton
  @Prov des
  def prov desManhattanMtlsParams(
    serv ce dent f er: Serv ce dent f er
  ): ManhattanKVCl entMtlsParams = {
    ManhattanKVCl entMtlsParams(serv ce dent f er)
  }
}
