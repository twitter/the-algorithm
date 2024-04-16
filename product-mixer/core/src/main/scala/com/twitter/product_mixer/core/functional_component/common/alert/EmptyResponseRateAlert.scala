package com.tw ter.product_m xer.core.funct onal_component.common.alert

 mport com.tw ter.product_m xer.core.funct onal_component.common.alert.pred cate.Tr gger fAbove

/**
 * [[EmptyResponseRateAlert]] tr ggers w n t  percentage of requests w h empty responses (def ned
 * as t  number of  ems returned exclud ng cursors) r ses above t  [[Tr gger fAbove]] threshold
 * for a conf gured amount of t  .
 *
 * @note EmptyResponseRate thresholds must be bet en 0 and 100%
 */
case class EmptyResponseRateAlert(
  overr de val not f cat onGroup: Not f cat onGroup,
  overr de val warnPred cate: Tr gger fAbove,
  overr de val cr  calPred cate: Tr gger fAbove,
  overr de val runbookL nk: Opt on[Str ng] = None)
    extends Alert {
  overr de val alertType: AlertType = EmptyResponseRate
  requ re(
    warnPred cate.threshold > 0 && warnPred cate.threshold <= 100,
    s"EmptyResponseRateAlert pred cates must be bet en 0 and 100 but got warnPred cate = ${warnPred cate.threshold}"
  )
  requ re(
    cr  calPred cate.threshold > 0 && cr  calPred cate.threshold <= 100,
    s"EmptyResponseRateAlert pred cates must be bet en 0 and 100 but got cr  calPred cate = ${cr  calPred cate.threshold}"
  )
}
