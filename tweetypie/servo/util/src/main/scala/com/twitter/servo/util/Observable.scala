package com.tw ter.servo.ut l

 mport com.tw ter.f nagle.thr ft.Cl ent d

/**
 * A tra  def n ng contextual  nformat on necessary to author ze
 * and observe a request.
 */
tra  Observable {
  val requestNa : Str ng
  val cl ent d: Opt on[Cl ent d]

  /**
   * An Opt on[Str ng] representat on of t  request- ssuer's Cl ent d.
   */
  lazy val cl ent dStr ng: Opt on[Str ng] =
    //  's poss ble for `Cl ent d.na ` to be `null`, so   wrap    n
    // `Opt on()` to force such cases to be None.
    cl ent d flatMap { c d =>
      Opt on(c d.na )
    }
}
