package com.tw ter.product_m xer.core.controllers

 mport com.tw ter.context.Tw terContext
 mport com.tw ter.context.thr ftscala.V e r
 mport com.tw ter.product_m xer.Tw terContextPerm 
 mport com.tw ter.product_m xer.core.model.marshall ng.request.Cl entContext

/**
 * M xes  n support to forge t  User ds  n Tw terContext for debug purposes.
 *
 * A thr ft controller can extend DebugTw terContext and wrap  's execut on log c:
 *
 * {{{
 * w hDebugTw terContext(request.cl entContext) {
 *   St ch.run(...)
 * }
 * }}}
 */
tra  DebugTw terContext {

  pr vate val ctx = Tw terContext(Tw terContextPerm )

  /**
   * Wrap so  funct on  n a debug Tw terContext w h hardcoded user ds
   * to t  Cl entContext.user d.
   *
   * @param cl entContext - A product m xer request cl ent context
   * @param f T  funct on to wrap
   */
  def w hDebugTw terContext[T](cl entContext: Cl entContext)(f: => T): T = {
    ctx.let(
      forgeTw terContext(
        cl entContext.user d
          .getOrElse(throw new  llegalArgu ntExcept on("m ss ng requ red f eld: user  d")))
    )(f)
  }

  // Generate a fake Tw ter Context for debug usage.
  // Generally t  Tw terContext  s created by t  AP  serv ce, and Strato uses   for perm ss on control.
  // W n   use   debug endpo nt,    nstead create   own context so that Strato f nds so th ng useful.
  //   enforce ACLs d rectly v a Thr ft  b Forms' perm ss on system.
  pr vate def forgeTw terContext(user d: Long): V e r = {
    V e r(
      aud  p = None,
       pTags = Set.empty,
      user d = So (user d),
      guest d = None,
      cl entAppl cat on d = None,
      userAgent = None,
      locat onToken = None,
      aut nt catedUser d = So (user d),
      guestToken = None
    )
  }
}
