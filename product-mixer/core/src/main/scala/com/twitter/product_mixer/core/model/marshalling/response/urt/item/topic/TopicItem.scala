package com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.top c

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.Cl entEvent nfo
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.FeedbackAct on nfo
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.EntryNa space
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.T  l neEntry
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.T  l ne em

object Top c em {
  val Top cEntryNa space = EntryNa space("top c")
}

case class Top c em(
  overr de val  d: Long,
  overr de val sort ndex: Opt on[Long],
  overr de val cl entEvent nfo: Opt on[Cl entEvent nfo],
  overr de val feedbackAct on nfo: Opt on[FeedbackAct on nfo],
  top cFunct onal yType: Opt on[Top cFunct onal yType],
  top cD splayType: Opt on[Top cD splayType])
    extends T  l ne em {
  overr de val entryNa space: EntryNa space = Top c em.Top cEntryNa space

  overr de def w hSort ndex(sort ndex: Long): T  l neEntry = copy(sort ndex = So (sort ndex))
}
