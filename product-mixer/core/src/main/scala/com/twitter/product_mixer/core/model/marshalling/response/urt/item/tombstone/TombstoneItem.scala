package com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.tombstone

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.t et.T et em
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.Cl entEvent nfo
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.FeedbackAct on nfo
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.EntryNa space
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.T  l neEntry
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.T  l ne em

object Tombstone em {
  val TombstoneEntryNa space = EntryNa space("tombstone")
}

case class Tombstone em(
  overr de val  d: Long,
  overr de val sort ndex: Opt on[Long],
  overr de val cl entEvent nfo: Opt on[Cl entEvent nfo],
  overr de val feedbackAct on nfo: Opt on[FeedbackAct on nfo],
  tombstoneD splayType: TombstoneD splayType,
  tombstone nfo: Opt on[Tombstone nfo],
  t et: Opt on[T et em])
    extends T  l ne em {
  overr de val entryNa space: EntryNa space = Tombstone em.TombstoneEntryNa space

  overr de def w hSort ndex(sort ndex: Long): T  l neEntry = copy(sort ndex = So (sort ndex))
}
