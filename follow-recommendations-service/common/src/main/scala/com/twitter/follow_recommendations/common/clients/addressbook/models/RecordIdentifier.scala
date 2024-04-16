package com.tw ter.follow_recom ndat ons.common.cl ents.addressbook.models

 mport com.tw ter.addressbook.datatypes.{thr ftscala => t}

case class Record dent f er(
  user d: Opt on[Long],
  ema l: Opt on[Str ng],
  phoneNumber: Opt on[Str ng]) {
  def toThr ft: t.Record dent f er = t.Record dent f er(user d, ema l, phoneNumber)
}
