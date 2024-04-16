package com.tw ter.product_m xer.core.model.marshall ng.response.urt.cover

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.EntryNa space
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.Cl entEvent nfo
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.Cover
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.T  l neEntry
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.cover.FullCover.FullCoverEntryNa space
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.cover.HalfCover.HalfCoverEntryNa space
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.FeedbackAct on nfo

object HalfCover {
  val HalfCoverEntryNa space = EntryNa space("half-cover")
}
case class HalfCover(
  overr de val  d: Str ng,
  overr de val sort ndex: Opt on[Long],
  overr de val cl entEvent nfo: Opt on[Cl entEvent nfo],
  content: HalfCoverContent)
    extends Cover {

  overr de val entryNa space: EntryNa space = HalfCoverEntryNa space

  // Note that sort  ndex  s not used for Covers, as t y are not T  l neEntry and do not have entry d
  overr de def w hSort ndex(newSort ndex: Long): T  l neEntry =
    copy(sort ndex = So (newSort ndex))

  // Not used for covers
  overr de def feedbackAct on nfo: Opt on[FeedbackAct on nfo] = None
}

object FullCover {
  val FullCoverEntryNa space = EntryNa space("full-cover")
}
case class FullCover(
  overr de val  d: Str ng,
  overr de val sort ndex: Opt on[Long],
  overr de val cl entEvent nfo: Opt on[Cl entEvent nfo],
  content: FullCoverContent)
    extends Cover {

  overr de val entryNa space: EntryNa space = FullCoverEntryNa space

  // Note that sort  ndex  s not used for Covers, as t y are not T  l neEntry and do not have entry d
  overr de def w hSort ndex(newSort ndex: Long): T  l neEntry =
    copy(sort ndex = So (newSort ndex))

  // Not used for covers
  overr de def feedbackAct on nfo: Opt on[FeedbackAct on nfo] = None
}
