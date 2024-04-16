package com.tw ter.follow_recom ndat ons.common.cl ents.addressbook

 mport com.tw ter.addressbook.thr ftscala.Addressbook2
 mport com.tw ter.f natra.mtls.thr ftmux.modules.MtlsCl ent
 mport com.tw ter.follow_recom ndat ons.common.cl ents.common.BaseCl entModule

object AddressbookModule extends BaseCl entModule[Addressbook2. thodPerEndpo nt] w h MtlsCl ent {
  overr de val label = "addressbook"
  overr de val dest = "/s/addressbook/addressbook2"
}
