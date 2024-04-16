package com.tw ter.product_m xer.core.model.marshall ng.response.urt. em. ssage

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.Callback
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.Cl entEvent nfo
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.FeedbackAct on nfo
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.EntryNa space
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.T  l neEntry
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.T  l ne em

object  ssagePrompt em {
  val  ssagePromptEntryNa space = EntryNa space(" ssageprompt")
}

case class  ssagePrompt em(
  overr de val  d: Str ng,
  overr de val sort ndex: Opt on[Long],
  overr de val cl entEvent nfo: Opt on[Cl entEvent nfo],
  overr de val feedbackAct on nfo: Opt on[FeedbackAct on nfo],
  overr de val  sP nned: Opt on[Boolean],
  content:  ssageContent,
   mpress onCallbacks: Opt on[L st[Callback]])
    extends T  l ne em {
  overr de val entryNa space: EntryNa space =
     ssagePrompt em. ssagePromptEntryNa space

  overr de def w hSort ndex(sort ndex: Long): T  l neEntry = copy(sort ndex = So (sort ndex))
}
