package com.tw ter.product_m xer.core.funct onal_component.common.alert

 mport com.tw ter.product_m xer.core.funct onal_component.common.alert.pred cate.Pred cate

/**
 * [[Alert]]s w ll tr gger not f cat ons to t  r [[Not f cat onGroup]]
 * w n t  [[Pred cate]]s are tr ggered.
 */
tra  Alert {

  /** A group of alert levels and w re t  alerts for those levels should be sent */
  val not f cat onGroup: Not f cat onGroup

  /** Pred cate  nd cat ng that t  component  s  n a degraded state */
  val warnPred cate: Pred cate

  /** Pred cate  nd cat ng that t  component  s not funct on ng correctly */
  val cr  calPred cate: Pred cate

  /** An opt onal l nk to t  runbook deta l ng how to respond to t  alert */
  val runbookL nk: Opt on[Str ng]

  /**  nd cates wh ch  tr cs t  [[Alert]]  s for */
  val alertType: AlertType

  /** W re t   tr cs are from, @see [[S ce]] */
  val s ce: S ce = Server()

  /** A suff x to add to t  end of t   tr c, t   s often a [[Percent le]] */
  val  tr cSuff x: Opt on[Str ng] = None
}
