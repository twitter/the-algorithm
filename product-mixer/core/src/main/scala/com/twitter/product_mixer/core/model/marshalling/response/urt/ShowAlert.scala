package com.tw ter.product_m xer.core.model.marshall ng.response.urt

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.ShowAlert.ShowAlertEntryNa space
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.alert.ShowAlertColorConf gurat on
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.alert.ShowAlertD splayLocat on
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.alert.ShowAlert conD splay nfo
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.alert.ShowAlertNav gat on tadata
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.alert.ShowAlertType
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.Cl entEvent nfo
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.FeedbackAct on nfo
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.r chtext.R chText
 mport com.tw ter.ut l.Durat on

/**
 * Doma n model for t  URT ShowAlert [[https://docb rd.tw ter.b z/un f ed_r ch_t  l nes_urt/gen/com/tw ter/t  l nes/render/thr ftscala/ShowAlert.html]]
 *
 * @note t  text f eld ( d: 2) has been del berately excluded as  's been deprecated s nce 2018. Use R chText  nstead.
 */
case class ShowAlert(
  overr de val  d: Str ng,
  overr de val sort ndex: Opt on[Long],
  alertType: ShowAlertType,
  tr ggerDelay: Opt on[Durat on],
  d splayDurat on: Opt on[Durat on],
  cl entEvent nfo: Opt on[Cl entEvent nfo],
  collapseDelay: Opt on[Durat on],
  user ds: Opt on[Seq[Long]],
  r chText: Opt on[R chText],
   conD splay nfo: Opt on[ShowAlert conD splay nfo],
  colorConf g: ShowAlertColorConf gurat on,
  d splayLocat on: ShowAlertD splayLocat on,
  nav gat on tadata: Opt on[ShowAlertNav gat on tadata],
) extends T  l ne em {
  overr de val entryNa space: EntryNa space = ShowAlertEntryNa space

  // Note that sort  ndex  s not used for ShowAlerts, as t y are not T  l neEntry and do not have entry d
  overr de def w hSort ndex(newSort ndex: Long): T  l neEntry =
    copy(sort ndex = So (newSort ndex))

  // Not used for ShowAlerts
  overr de def feedbackAct on nfo: Opt on[FeedbackAct on nfo] = None
}

object ShowAlert {
  val ShowAlertEntryNa space: EntryNa space = EntryNa space("show-alert")
}
