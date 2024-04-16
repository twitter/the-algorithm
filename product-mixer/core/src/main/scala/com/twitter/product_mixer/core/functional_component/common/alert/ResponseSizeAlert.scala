package com.tw ter.product_m xer.core.funct onal_component.common.alert

 mport com.tw ter.product_m xer.core.funct onal_component.common.alert.pred cate.ThroughputPred cate

/**
 * [[ResponseS zeAlert]] tr ggers w n t  spec f ed percent le of requests w h empty responses (def ned
 * as t  number of  ems returned exclud ng cursors)  s beyond t  [[ThroughputPred cate]] threshold
 * for a conf gured amount of t  .
 */
case class ResponseS zeAlert(
  overr de val not f cat onGroup: Not f cat onGroup,
  percent le: Percent le,
  overr de val warnPred cate: ThroughputPred cate,
  overr de val cr  calPred cate: ThroughputPred cate,
  overr de val runbookL nk: Opt on[Str ng] = None)
    extends Alert {
  overr de val  tr cSuff x: Opt on[Str ng] = So (percent le. tr cSuff x)
  overr de val alertType: AlertType = ResponseS ze
  requ re(
    warnPred cate.threshold >= 0,
    s"ResponseS zeAlert pred cates must be >= 0 but got warnPred cate = ${warnPred cate.threshold}"
  )
  requ re(
    cr  calPred cate.threshold >= 0,
    s"ResponseS zeAlert pred cates must be >= 0 but got cr  calPred cate = ${cr  calPred cate.threshold}"
  )
}
