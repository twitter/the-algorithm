package com.tw ter.product_m xer.core.funct onal_component.common.alert

 mport com.tw ter.product_m xer.core.funct onal_component.common.alert.pred cate.Tr gger fLatencyAbove

/**
 * [[Gener cCl entLatencyAlert]] tr ggers w n t  Latency for t  component t   s used w h
 * r ses above t  [[Tr gger fLatencyAbove]] threshold for t  conf gured amount of t  
 */
case class LatencyAlert(
  overr de val not f cat onGroup: Not f cat onGroup,
  percent le: Percent le,
  overr de val warnPred cate: Tr gger fLatencyAbove,
  overr de val cr  calPred cate: Tr gger fLatencyAbove,
  overr de val runbookL nk: Opt on[Str ng] = None)
    extends Alert
    w h  sObservableFromStrato {
  overr de val alertType: AlertType = Latency

  overr de val  tr cSuff x: Opt on[Str ng] = So (percent le. tr cSuff x)
}
