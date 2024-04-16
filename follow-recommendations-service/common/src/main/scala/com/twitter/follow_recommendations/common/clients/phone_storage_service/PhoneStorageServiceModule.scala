package com.tw ter.follow_recom ndat ons.common.cl ents.phone_storage_serv ce

 mport com.tw ter.f natra.mtls.thr ftmux.modules.MtlsCl ent
 mport com.tw ter.follow_recom ndat ons.common.cl ents.common.BaseCl entModule
 mport com.tw ter.phonestorage.ap .thr ftscala.PhoneStorageServ ce

object PhoneStorageServ ceModule
    extends BaseCl entModule[PhoneStorageServ ce. thodPerEndpo nt]
    w h MtlsCl ent {
  overr de val label = "phone-storage-serv ce"
  overr de val dest = "/s/ b s-ds-ap / b s-ds-ap :thr ft2"
}
