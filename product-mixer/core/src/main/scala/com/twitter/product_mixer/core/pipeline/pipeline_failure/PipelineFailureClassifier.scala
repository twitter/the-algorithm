package com.tw ter.product_m xer.core.p pel ne.p pel ne_fa lure

/** Represents a way to class fy a g ven [[Throwable]] to a [[P pel neFa lure]] */
case class P pel neFa lureClass f er(
  class f er: Part alFunct on[Throwable, P pel neFa lure])
    extends Part alFunct on[Throwable, P pel neFa lure] {
  overr de def  sDef nedAt(throwable: Throwable): Boolean = class f er. sDef nedAt(throwable)
  overr de def apply(throwable: Throwable): P pel neFa lure = class f er.apply(throwable)
}

pr vate[core] object P pel neFa lureClass f er {
  val Empty: P pel neFa lureClass f er = P pel neFa lureClass f er(Part alFunct on.empty)
}
