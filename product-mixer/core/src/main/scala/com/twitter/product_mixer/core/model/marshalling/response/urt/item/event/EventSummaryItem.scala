package com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.event

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.Cl entEvent nfo
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.FeedbackAct on nfo
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata. mageVar ant
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.Url
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.EntryNa space
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.T  l neEntry
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.T  l ne em

object EventSummary em {
  val EventSummary emEntryNa space = EntryNa space("eventsummary")
}

case class EventSummary em(
  overr de val  d: Long,
  overr de val sort ndex: Opt on[Long],
  overr de val cl entEvent nfo: Opt on[Cl entEvent nfo],
  overr de val feedbackAct on nfo: Opt on[FeedbackAct on nfo],
  t le: Str ng,
  d splayType: EventSummaryD splayType,
  url: Url,
   mage: Opt on[ mageVar ant],
  t  Str ng: Opt on[Str ng])
    extends T  l ne em {
  overr de val entryNa space: EntryNa space =
    EventSummary em.EventSummary emEntryNa space

  overr de def w hSort ndex(sort ndex: Long): T  l neEntry = copy(sort ndex = So (sort ndex))
}
