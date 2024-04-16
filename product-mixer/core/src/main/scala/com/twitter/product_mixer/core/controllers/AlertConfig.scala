package com.tw ter.product_m xer.core.controllers

 mport com.fasterxml.jackson.annotat on.Json gnorePropert es
 mport com.tw ter.product_m xer.core.funct onal_component.common.alert.Alert
 mport com.tw ter.product_m xer.core.funct onal_component.common.alert.Not f cat onGroup
 mport com.tw ter.product_m xer.core.funct onal_component.common.alert.S ce

/**
 * S mple representat on for an [[Alert]] used for Product M xer's JSON AP , wh ch  n turn  s
 * consu d by   mon or ng scr pt generat on job and Turntable.
 *
 * @note not all m xers w ll upgrade at t  sa  t   so new f elds should be added w h backwards
 *       compat b l y  n m nd.
 */
@Json gnorePropert es( gnoreUnknown = true)
pr vate[core] case class AlertConf g(
  s ce: S ce,
   tr cType: Str ng,
  not f cat onGroup: Not f cat onGroup,
  warnPred cate: Pred cateConf g,
  cr  calPred cate: Pred cateConf g,
  runbookL nk: Opt on[Str ng],
   tr cSuff x: Opt on[Str ng])

pr vate[core] object AlertConf g {

  /** Represent t  [[Alert]] as an [[AlertConf g]] case class */
  pr vate[core] def apply(alert: Alert): AlertConf g =
    AlertConf g(
      alert.s ce,
      alert.alertType. tr cType,
      alert.not f cat onGroup,
      Pred cateConf g(alert.warnPred cate),
      Pred cateConf g(alert.cr  calPred cate),
      alert.runbookL nk,
      alert. tr cSuff x
    )
}
