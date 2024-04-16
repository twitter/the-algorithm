package com.tw ter.product_m xer.core.funct onal_component.common.alert

 mport com.tw ter.product_m xer.core.funct onal_component.common.alert.pred cate.Tr gger fBelow

/**
 * S m lar to [[SuccessRateAlert]] but  ntended for use w h an external cl ent call ng Product M xer
 *
 * [[Gener cCl entSuccessRateAlert]] tr ggers w n t  Success Rate for t  external cl ent
 * drops below t  [[Tr gger fBelow]] threshold for t  conf gured amount of t  
 *
 * @note SuccessRate thresholds must be bet en 0 and 100%
 */
case class Gener cCl entSuccessRateAlert(
  overr de val s ce: Gener cCl ent,
  overr de val not f cat onGroup: Not f cat onGroup,
  overr de val warnPred cate: Tr gger fBelow,
  overr de val cr  calPred cate: Tr gger fBelow,
  overr de val runbookL nk: Opt on[Str ng] = None)
    extends Alert {
  overr de val alertType: AlertType = SuccessRate
  requ re(
    warnPred cate.threshold > 0 && warnPred cate.threshold <= 100,
    s"SuccessRateAlert pred cates must be bet en 0 and 100 but got warnPred cate = ${warnPred cate.threshold}"
  )
  requ re(
    cr  calPred cate.threshold > 0 && cr  calPred cate.threshold <= 100,
    s"SuccessRateAlert pred cates must be bet en 0 and 100 but got cr  calPred cate = ${cr  calPred cate.threshold}"
  )
}
