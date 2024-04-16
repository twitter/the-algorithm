package com.tw ter.s mclustersann.modules

 mport com.google. nject.Prov des
 mport javax. nject.S ngleton
 mport com.tw ter. nject.Tw terModule
 mport com.tw ter.f nagle.mtls.aut nt cat on.Serv ce dent f er
 mport com.tw ter.strato.cl ent.Cl ent
 mport com.tw ter.strato.cl ent.Strato

object StratoCl entProv derModule extends Tw terModule {

  @S ngleton
  @Prov des
  def prov desCac (
    serv ce dent f er: Serv ce dent f er,
  ): Cl ent = Strato.cl ent
    .w hMutualTls(serv ce dent f er)
    .bu ld()

}
