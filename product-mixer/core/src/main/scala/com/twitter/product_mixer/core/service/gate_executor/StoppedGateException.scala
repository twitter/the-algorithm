package com.tw ter.product_m xer.core.serv ce.gate_executor

 mport com.tw ter.product_m xer.core.model.common. dent f er.Gate dent f er
 mport com.tw ter.product_m xer.core.p pel ne.p pel ne_fa lure.P pel neFa lure
 mport com.tw ter.product_m xer.core.p pel ne.p pel ne_fa lure.P pel neFa lureCategory
 mport com.tw ter.product_m xer.core.p pel ne.p pel ne_fa lure.P pel neFa lureClass f er

 mport scala.ut l.control.NoStackTrace

case class StoppedGateExcept on( dent f er: Gate dent f er)
    extends Except on("Closed gate stopped execut on of t  p pel ne")
    w h NoStackTrace {
  overr de def toStr ng: Str ng = s"StoppedGateExcept on($ dent f er)"
}

object StoppedGateExcept on {

  /**
   * Creates a [[P pel neFa lureClass f er]] that  s used as t  default for class fy ng fa lures
   *  n a p pel ne by mapp ng [[StoppedGateExcept on]] to a [[P pel neFa lure]] w h t  prov ded
   * [[P pel neFa lureCategory]]
   */
  def class f er(
    category: P pel neFa lureCategory
  ): P pel neFa lureClass f er = P pel neFa lureClass f er {
    case stoppedGateExcept on: StoppedGateExcept on =>
      P pel neFa lure(category, stoppedGateExcept on.get ssage, So (stoppedGateExcept on))
  }
}
