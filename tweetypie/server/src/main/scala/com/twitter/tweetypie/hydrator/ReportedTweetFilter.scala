package com.tw ter.t etyp e
package hydrator

 mport com.tw ter.st ch.St ch
 mport com.tw ter.t etyp e.core._
 mport com.tw ter.t etyp e.thr ftscala._

object ReportedT etF lter {
  type Type = ValueHydrator[Un , Ctx]

  object M ss ngPerspect veError
      extends T etHydrat onError("Cannot determ ne reported state because perspect ve  s m ss ng")

  case class Ctx(perspect ve: Opt on[StatusPerspect ve], underly ngT etCtx: T etCtx)
      extends T etCtx.Proxy

  def apply(): Type =
    ValueHydrator[Un , Ctx] { (_, ctx) =>
      ctx.perspect ve match {
        case So (p)  f !p.reported => ValueState.St chUnmod f edUn 
        case So (_) => St ch.except on(F lteredState.Unava lable.Reported)
        case None => St ch.except on(M ss ngPerspect veError)
      }
    }.only f { (_, ctx) => ctx.opts.excludeReported && ctx.opts.forUser d. sDef ned }
}
