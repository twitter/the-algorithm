package com.tw ter.follow_recom ndat ons.common.cand date_s ces.addressbook

 mport com.tw ter.t  l nes.conf gap .FSParam

object AddressBookParams {
  // Used by d splay locat ons that want only to read from t  ABV2 Cl ent and  gnore Manhattan
  // Currently t  only d splay locat on that does t   s t  ABUpload nject on D splayLocat on
  object ReadFromABV2Only extends FSParam[Boolean]("addressbook_read_only_from_abv2", false)
}
