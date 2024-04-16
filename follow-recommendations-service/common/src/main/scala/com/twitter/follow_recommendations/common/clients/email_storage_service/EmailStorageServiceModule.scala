package com.tw ter.follow_recom ndat ons.common.cl ents.ema l_storage_serv ce

 mport com.tw ter.ema lstorage.ap .thr ftscala.Ema lStorageServ ce
 mport com.tw ter.f natra.mtls.thr ftmux.modules.MtlsCl ent
 mport com.tw ter.follow_recom ndat ons.common.cl ents.common.BaseCl entModule

object Ema lStorageServ ceModule
    extends BaseCl entModule[Ema lStorageServ ce. thodPerEndpo nt]
    w h MtlsCl ent {
  overr de val label = "ema l-storage-serv ce"
  overr de val dest = "/s/ema l-server/ema l-server"
}
