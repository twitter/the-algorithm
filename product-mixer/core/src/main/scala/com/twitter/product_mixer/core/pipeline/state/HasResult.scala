package com.tw ter.product_m xer.core.p pel ne.state

/**
 * Def nes how to bu ld a result from a p pel ne state. P pel ne States should extend t  and
 *  mple nt [[bu ldResult]] wh ch computes t  f nal result from t  r current state.
 * @tparam Result Type of result
 */
tra  HasResult[+Result] {
  def bu ldResult(): Result
}
