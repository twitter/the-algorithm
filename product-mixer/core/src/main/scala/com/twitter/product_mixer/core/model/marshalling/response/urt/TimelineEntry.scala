package com.tw ter.product_m xer.core.model.marshall ng.response.urt

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.Cl entEvent nfo
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.Conta nsFeedbackAct on nfos
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.FeedbackAct on nfo
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.HasCl entEvent nfo
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.HasFeedbackAct on nfo
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.P nnableEntry
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.ReplaceableEntry
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.MarkUnreadableEntry
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.t  l ne_module.ModuleD splayType
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.t  l ne_module.ModuleFooter
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.t  l ne_module.Module ader
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.t  l ne_module.Module tadata
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.t  l ne_module.ModuleShowMoreBehav or

sealed tra  T  l neEntry
    extends HasEntry dent f er
    w h HasSort ndex
    w h HasExp rat onT  
    w h P nnableEntry
    w h ReplaceableEntry
    w h MarkUnreadableEntry

tra  T  l ne em extends T  l neEntry w h HasCl entEvent nfo w h HasFeedbackAct on nfo

case class Module em(
   em: T  l ne em,
  d spensable: Opt on[Boolean],
  treeD splay: Opt on[Module emTreeD splay])

case class T  l neModule(
  overr de val  d: Long,
  overr de val sort ndex: Opt on[Long],
  overr de val entryNa space: EntryNa space,
  overr de val cl entEvent nfo: Opt on[Cl entEvent nfo],
  overr de val feedbackAct on nfo: Opt on[FeedbackAct on nfo],
  overr de val  sP nned: Opt on[Boolean],
   ems: Seq[Module em],
  d splayType: ModuleD splayType,
   ader: Opt on[Module ader],
  footer: Opt on[ModuleFooter],
   tadata: Opt on[Module tadata],
  showMoreBehav or: Opt on[ModuleShowMoreBehav or])
    extends T  l neEntry
    w h HasCl entEvent nfo
    w h HasFeedbackAct on nfo
    w h Conta nsFeedbackAct on nfos {
  overr de def feedbackAct on nfos: Seq[Opt on[FeedbackAct on nfo]] = {
     ems.map(_. em.feedbackAct on nfo) :+ feedbackAct on nfo
  }

  overr de def w hSort ndex(sort ndex: Long): T  l neEntry = copy(sort ndex = So (sort ndex))
}

tra  T  l neOperat on extends T  l neEntry
