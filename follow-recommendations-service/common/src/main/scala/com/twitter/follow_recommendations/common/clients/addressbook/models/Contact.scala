package com.tw ter.follow_recom ndat ons.common.cl ents.addressbook.models

 mport com.tw ter.addressbook.{thr ftscala => t}
 mport com.tw ter.ut l.T  

case class Contact(
   d: Long,
  ema ls: Opt on[Set[Str ng]],
  phoneNumbers: Opt on[Set[Str ng]],
  f rstNa : Opt on[Str ng],
  lastNa : Opt on[Str ng],
  na : Opt on[Str ng],
  app d: Opt on[Long],
  app ds: Opt on[Set[Long]],
   mportedT  stamp: Opt on[T  ])

object Contact {
  def fromThr ft(thr ftContact: t.Contact): Contact = Contact(
    thr ftContact. d,
    thr ftContact.ema ls.map(_.toSet),
    thr ftContact.phoneNumbers.map(_.toSet),
    thr ftContact.f rstNa ,
    thr ftContact.lastNa ,
    thr ftContact.na ,
    thr ftContact.app d,
    thr ftContact.app ds.map(_.toSet),
    thr ftContact. mportedT  stamp.map(T  .fromM ll seconds)
  )
}
