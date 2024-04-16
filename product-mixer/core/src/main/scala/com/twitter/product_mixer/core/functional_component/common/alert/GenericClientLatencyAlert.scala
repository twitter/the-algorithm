package com.tw ter.product_m xer.core.funct onal_component.common.alert

 mport com.tw ter.product_m xer.core.funct onal_component.common.alert.pred cate.Tr gger fLatencyAbove

/**
 * S m lar to [[LatencyAlert]] but  ntended for use w h an external cl ent call ng Product M xer.
 *
 * [[Gener cCl entLatencyAlert]] tr ggers w n t  Latency for t  spec f ed cl ent
 * r ses above t  [[Tr gger fLatencyAbove]] threshold for t  conf gured amount of t  .
 */
case class Gener cCl entLatencyAlert(
  overr de val s ce: Gener cCl ent,
  overr de val not f cat onGroup: Not f cat onGroup,
  overr de val warnPred cate: Tr gger fLatencyAbove,
  overr de val cr  calPred cate: Tr gger fLatencyAbove,
  overr de val runbookL nk: Opt on[Str ng] = None)
    extends Alert {
  overr de val alertType: AlertType = Latency
}
