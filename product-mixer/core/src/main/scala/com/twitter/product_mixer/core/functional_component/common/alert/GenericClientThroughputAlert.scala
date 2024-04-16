package com.tw ter.product_m xer.core.funct onal_component.common.alert

 mport com.tw ter.product_m xer.core.funct onal_component.common.alert.pred cate.ThroughputPred cate

/**
 * S m lar to [[ThroughputAlert]] but  ntended for an external cl ent call ng Product M xer.
 *
 * [[Gener cCl entThroughputAlert]] tr ggers w n t  requests/sec for t  external cl ent
 *  s outs de of t  pred cate set by a [[ThroughputPred cate]] for t  conf gured amount of t  
 */
case class Gener cCl entThroughputAlert(
  overr de val s ce: Gener cCl ent,
  overr de val not f cat onGroup: Not f cat onGroup,
  overr de val warnPred cate: ThroughputPred cate,
  overr de val cr  calPred cate: ThroughputPred cate,
  overr de val runbookL nk: Opt on[Str ng] = None)
    extends Alert {
  overr de val alertType: AlertType = Throughput
  requ re(
    warnPred cate.threshold >= 0,
    s"ThroughputAlert pred cates must be >= 0 but got warnPred cate = ${warnPred cate.threshold}")
  requ re(
    cr  calPred cate.threshold >= 0,
    s"ThroughputAlert pred cates must be >= 0 but got cr  calPred cate = ${cr  calPred cate.threshold}")
}
