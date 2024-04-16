package com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.thread

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.EntryNa space
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.T  l neEntry
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.T  l ne em
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.Cl entEvent nfo
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.FeedbackAct on nfo

object Thread ader em {
  val Thread aderEntryNa space = EntryNa space("thread ader")
}

case class Thread ader em(
  overr de val  d: Long,
  overr de val sort ndex: Opt on[Long],
  overr de val cl entEvent nfo: Opt on[Cl entEvent nfo],
  overr de val feedbackAct on nfo: Opt on[FeedbackAct on nfo],
  overr de val  sP nned: Opt on[Boolean],
  content: Thread aderContent)
    extends T  l ne em {
  overr de val entryNa space: EntryNa space = Thread ader em.Thread aderEntryNa space

  overr de def w hSort ndex(sort ndex: Long): T  l neEntry = copy(sort ndex = So (sort ndex))
}
