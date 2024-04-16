package com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.gener c_summary

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.EntryNa space
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.T  l neEntry
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.T  l ne em
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. d a. d a
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.Cl entEvent nfo
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.FeedbackAct on nfo
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.promoted.Promoted tadata
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.r chtext.R chText
 mport com.tw ter.ut l.T  

object Gener cSummary em {
  val Gener cSummary emNa space: EntryNa space = EntryNa space("gener csummary")
}

case class Gener cSummary em(
  overr de val  d: Str ng,
  overr de val sort ndex: Opt on[Long],
  overr de val cl entEvent nfo: Opt on[Cl entEvent nfo],
  overr de val feedbackAct on nfo: Opt on[FeedbackAct on nfo],
   adl ne: R chText,
  d splayType: Gener cSummary emD splayType,
  userAttr but on ds: Seq[Long],
   d a: Opt on[ d a],
  context: Opt on[Gener cSummaryContext],
  t  stamp: Opt on[T  ],
  onCl ckAct on: Opt on[Gener cSummaryAct on],
  promoted tadata: Opt on[Promoted tadata])
    extends T  l ne em {
  overr de val entryNa space: EntryNa space = Gener cSummary em.Gener cSummary emNa space

  overr de def w hSort ndex(sort ndex: Long): T  l neEntry = copy(sort ndex = So (sort ndex))
}
