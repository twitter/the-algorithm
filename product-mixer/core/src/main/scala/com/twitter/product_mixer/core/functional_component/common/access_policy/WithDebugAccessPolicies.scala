package com.tw ter.product_m xer.core.funct onal_component.common.access_pol cy

 mport com.tw ter.product_m xer.core.model.common.Component

pr vate[core] tra  W hDebugAccessPol c es { self: Component =>

  /** T  [[AccessPol cy]]s that w ll be used for t  component  n turntable & ot r debug tool ng
   * to execute arb rary quer es aga nst t  component */
  val debugAccessPol c es: Set[AccessPol cy] = Set.empty
}
