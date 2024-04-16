package com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.suggest on

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.EntryNa space
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.T  l neEntry
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.T  l ne em
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.Cl entEvent nfo
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.FeedbackAct on nfo

object Spell ng em {
  val Spell ngEntryNa space = EntryNa space("spell ng")
}

/**
 * Represents a Spell ng Suggest on URT  em. T   s pr mary used by Search t  l nes for
 * d splay ng Spell ng correct on  nformat on.
 *
 * URT AP  Reference: https://docb rd.tw ter.b z/un f ed_r ch_t  l nes_urt/gen/com/tw ter/t  l nes/render/thr ftscala/Spell ng.html
 */
case class Spell ng em(
  overr de val  d: Str ng,
  overr de val sort ndex: Opt on[Long],
  overr de val cl entEvent nfo: Opt on[Cl entEvent nfo],
  overr de val feedbackAct on nfo: Opt on[FeedbackAct on nfo],
  textResult: TextResult,
  spell ngAct onType: Opt on[Spell ngAct onType],
  or g nalQuery: Opt on[Str ng])
    extends T  l ne em {

  overr de val entryNa space: EntryNa space = Spell ng em.Spell ngEntryNa space

  overr de def w hSort ndex(sort ndex: Long): T  l neEntry = copy(sort ndex = So (sort ndex))
}
