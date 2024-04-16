package com.tw ter.product_m xer.core.funct onal_component.cand date_s ce.strato

 mport com.tw ter.product_m xer.core.p pel ne.p pel ne_fa lure.P pel neFa lure
 mport com.tw ter.product_m xer.core.p pel ne.p pel ne_fa lure.Unauthor zed
 mport com.tw ter.st ch.St ch
 mport com.tw ter.strato.response.Err

/**
 * Categor ze Strato's Err  ssages to   P pel neFa lures.
 *
 * T  should be used by all strato-based cand date s ce, and   can
 * add more cases  re as t y're useful.
 */
object StratoErrCategor zer {
  val Categor zeStratoExcept on: Part alFunct on[Throwable, St ch[Noth ng]] = {
    case err @ Err(Err.Author zat on, reason, context) =>
      St ch.except on(
        P pel neFa lure(Unauthor zed, s"$reason [${context.toStr ng}]", underly ng = So (err))
      )
  }
}
