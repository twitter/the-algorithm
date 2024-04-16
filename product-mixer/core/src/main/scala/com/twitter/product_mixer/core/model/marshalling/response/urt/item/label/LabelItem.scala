package com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.label

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.Cl entEvent nfo
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.FeedbackAct on nfo
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.Url
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.EntryNa space
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.T  l neEntry
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.T  l ne em

object Label em {
  val LabelEntryNa space = EntryNa space("label")
}

case class Label em(
  overr de val  d: Long,
  overr de val sort ndex: Opt on[Long],
  overr de val cl entEvent nfo: Opt on[Cl entEvent nfo],
  overr de val feedbackAct on nfo: Opt on[FeedbackAct on nfo],
  text: Str ng,
  subtext: Opt on[Str ng],
  d sclosure nd cator: Opt on[Boolean],
  url: Opt on[Url],
  d splayType: Opt on[LabelD splayType])
    extends T  l ne em {
  overr de val entryNa space: EntryNa space = Label em.LabelEntryNa space

  overr de def w hSort ndex(sort ndex: Long): T  l neEntry = copy(sort ndex = So (sort ndex))
}
