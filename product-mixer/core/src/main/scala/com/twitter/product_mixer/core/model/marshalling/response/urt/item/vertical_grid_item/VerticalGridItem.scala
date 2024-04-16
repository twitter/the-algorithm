package com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.vert cal_gr d_ em

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.Cl entEvent nfo
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.FeedbackAct on nfo
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.Url
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.EntryNa space
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.T  l neEntry
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.T  l ne em

sealed tra  Vert calGr d em extends T  l ne em

object Vert calGr d emTop cT le {
  val Vert calGr d emTop cT leEntryNa space = EntryNa space("vert calgr d emtop ct le")
}

case class Vert calGr d emTop cT le(
  overr de val  d: Long,
  overr de val sort ndex: Opt on[Long],
  overr de val cl entEvent nfo: Opt on[Cl entEvent nfo],
  overr de val feedbackAct on nfo: Opt on[FeedbackAct on nfo],
  style: Opt on[Vert calGr d emT leStyle],
  funct onal yType: Opt on[Vert calGr d emTop cFunct onal yType],
  url: Opt on[Url])
    extends Vert calGr d em {
  overr de val entryNa space: EntryNa space =
    Vert calGr d emTop cT le.Vert calGr d emTop cT leEntryNa space

  overr de def w hSort ndex(sort ndex: Long): T  l neEntry = copy(sort ndex = So (sort ndex))
}
