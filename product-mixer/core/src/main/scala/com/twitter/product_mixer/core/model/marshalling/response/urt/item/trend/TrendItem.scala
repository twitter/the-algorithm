package com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.trend

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.EntryNa space
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.T  l ne em
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.Cl entEvent nfo
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.FeedbackAct on nfo
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.T  l neEntry
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.Url
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.promoted.Promoted tadata

object Trend em {
  val Trend emEntryNa space = EntryNa space("trend")
}

case class GroupedTrend(trendNa : Str ng, url: Url)

case class Trend em(
  overr de val  d: Str ng,
  overr de val sort ndex: Opt on[Long],
  overr de val cl entEvent nfo: Opt on[Cl entEvent nfo],
  overr de val feedbackAct on nfo: Opt on[FeedbackAct on nfo],
  normal zedTrendNa : Str ng,
  trendNa : Str ng,
  url: Url,
  descr pt on: Opt on[Str ng],
   taDescr pt on: Opt on[Str ng],
  t etCount: Opt on[ nt],
  doma nContext: Opt on[Str ng],
  promoted tadata: Opt on[Promoted tadata],
  groupedTrends: Opt on[Seq[GroupedTrend]])
    extends T  l ne em {
  overr de val entryNa space: EntryNa space = Trend em.Trend emEntryNa space

  overr de def w hSort ndex(sort ndex: Long): T  l neEntry = copy(sort ndex = So (sort ndex))
}
