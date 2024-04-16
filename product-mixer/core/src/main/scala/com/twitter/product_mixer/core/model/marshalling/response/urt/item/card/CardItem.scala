package com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.card

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.EntryNa space
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.T  l neEntry
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.T  l ne em
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.Cl entEvent nfo
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.FeedbackAct on nfo
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.Url

object Card em {
  val CardEntryNa space = EntryNa space("card")
}

case class Card em(
  overr de val  d: Str ng,
  overr de val sort ndex: Opt on[Long],
  overr de val cl entEvent nfo: Opt on[Cl entEvent nfo],
  overr de val feedbackAct on nfo: Opt on[FeedbackAct on nfo],
  cardUrl: Str ng,
  text: Opt on[Str ng],
  subtext: Opt on[Str ng],
  url: Opt on[Url],
  d splayType: Opt on[CardD splayType])
    extends T  l ne em {
  overr de val entryNa space: EntryNa space = Card em.CardEntryNa space

  overr de def w hSort ndex(sort ndex: Long): T  l neEntry = copy(sort ndex = So (sort ndex))
}
