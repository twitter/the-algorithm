package com.tw ter.product_m xer.core.funct onal_component.gate

/**
 * A [[Gate]] controls  f a p pel ne or ot r component  s executed.
 *
 * Appl cat on log c should usually use `GateResult.cont nue: Boolean` to  nterpret a GateResult. `cont nue` w ll be
 * true  f   should cont nue w h execut on, and false  f   should stop.
 *
 *   can case match aga nst t  `GateResult` to understand how exactly execut on happened. See `object GateResult`
 * below, but t   s useful  f   want to know  f   are cont nu ng due to t  sk p or ma n pred cates.
 */
sealed tra  GateResult {

  /** Should   cont nue? */
  val cont nue: Boolean
}

object GateResult {

  /**
   * Cont nue Execut on
   *
   * t  Sk p pred cate evaluated to true,
   * so   Sk pped execut on of t  Ma n pred cate and should cont nue
   */
  case object Sk pped extends GateResult {
    overr de val cont nue = true
  }

  /**
   * Cont nue Execut on
   *
   * t  ma n pred cate evaluated to true
   */
  case object Cont nue extends GateResult {
    overr de val cont nue = true
  }

  /**
   * Stop execut on
   *
   * t  ma n pred cate evaluated to false
   */
  case object Stop extends GateResult {
    overr de val cont nue = false
  }
}
