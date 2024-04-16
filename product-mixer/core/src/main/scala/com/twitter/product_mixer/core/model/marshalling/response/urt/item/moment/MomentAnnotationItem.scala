package com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.mo nt

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.EntryNa space
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.T  l neEntry
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.T  l ne em
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.Cl entEvent nfo
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.FeedbackAct on nfo
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.r chtext.R chText

object Mo ntAnnotat on em {
  val Mo ntAnnotat onEntryNa space = EntryNa space("mo ntannotat on")
}

/**
 * Represents a Mo ntAnnotat on URT  em.
 * T   s pr mar ly used by Trends Searth Result Page for d splay ng Trends T le or Descr pt on
 * URT AP  Reference: https://docb rd.tw ter.b z/un f ed_r ch_t  l nes_urt/gen/com/tw ter/t  l nes/render/thr ftscala/Mo ntAnnotat on.html
 */
case class Mo ntAnnotat on em(
  overr de val  d: Long,
  overr de val sort ndex: Opt on[Long],
  overr de val cl entEvent nfo: Opt on[Cl entEvent nfo],
  overr de val feedbackAct on nfo: Opt on[FeedbackAct on nfo],
  overr de val  sP nned: Opt on[Boolean],
  text: Opt on[R chText],
   ader: Opt on[R chText],
) extends T  l ne em {

  overr de val entryNa space: EntryNa space =
    Mo ntAnnotat on em.Mo ntAnnotat onEntryNa space

  overr de def w hSort ndex(sort ndex: Long): T  l neEntry = copy(sort ndex = So (sort ndex))
}
