package com.tw ter.product_m xer.core.funct onal_component.common.alert

 mport com.tw ter.product_m xer.core.funct onal_component.common.alert.pred cate.Pred cate
 mport com.tw ter.strato.catalog.OpTag

/**
 * tr ggers w n t  a Strato column's  s outs de of t  pred cate set by t  prov ded [[Alert]]
 *
 * @note t  [[Alert]] passed  nto a [[StratoColumnAlert]]
 *       can not be a [[StratoColumnAlert]]
 */
case class StratoColumnAlert(column: Str ng, op: OpTag, alert: Alert w h  sObservableFromStrato)
    extends Alert {

  overr de val s ce: S ce = Strato(column, op.tag)
  overr de val not f cat onGroup: Not f cat onGroup = alert.not f cat onGroup
  overr de val warnPred cate: Pred cate = alert.warnPred cate
  overr de val cr  calPred cate: Pred cate = alert.cr  calPred cate
  overr de val runbookL nk: Opt on[Str ng] = alert.runbookL nk
  overr de val alertType: AlertType = alert.alertType
  overr de val  tr cSuff x: Opt on[Str ng] = alert. tr cSuff x
}

object StratoColumnAlerts {

  /** Make a seq of Alerts for t  prov ded Strato column */
  def apply(
    column: Str ng,
    op: OpTag,
    alerts: Seq[Alert w h  sObservableFromStrato]
  ): Seq[Alert] = {
    alerts.map(StratoColumnAlert(column, op, _))
  }
}
