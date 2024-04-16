package com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.art cle

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.Cl entEvent nfo
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.FeedbackAct on nfo
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.Soc alContext
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.EntryNa space
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.T  l neEntry
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.T  l ne em

object Art cle em {
  val Art cleEntryNa space = EntryNa space("art cle")
}

case class Art cle em(
  overr de val  d:  nt,
  art cleSeedType: Art cleSeedType,
  overr de val sort ndex: Opt on[Long],
  overr de val cl entEvent nfo: Opt on[Cl entEvent nfo],
  overr de val feedbackAct on nfo: Opt on[FeedbackAct on nfo],
  d splayType: Opt on[Art cleD splayType],
  soc alContext: Opt on[Soc alContext])
    extends T  l ne em {
  overr de val entryNa space: EntryNa space = Art cle em.Art cleEntryNa space

  overr de def w hSort ndex(sort ndex: Long): T  l neEntry = copy(sort ndex = So (sort ndex))
}
