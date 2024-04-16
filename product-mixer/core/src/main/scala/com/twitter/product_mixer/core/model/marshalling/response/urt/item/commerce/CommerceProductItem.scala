package com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.com rce

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.EntryNa space
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.T  l neEntry
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.T  l ne em
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.com rce.Com rceProduct em.Com rceProductEntryNa space
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.Cl entEvent nfo
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.FeedbackAct on nfo

object Com rceProduct em {
  val Com rceProductEntryNa space: EntryNa space = EntryNa space("com rce-product")
}

case class Com rceProduct em(
  overr de val  d: Long,
  overr de val sort ndex: Opt on[Long],
  overr de val cl entEvent nfo: Opt on[Cl entEvent nfo],
  overr de val feedbackAct on nfo: Opt on[FeedbackAct on nfo])
    extends T  l ne em {

  val entryNa space: EntryNa space = Com rceProductEntryNa space
  def w hSort ndex(sort ndex: Long): T  l neEntry = copy(sort ndex = So (sort ndex))
}
