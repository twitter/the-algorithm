package com.tw ter.t etyp e
package hydrator

 mport com.tw ter.t etyp e.core.ValueState
 mport com.tw ter.t etyp e.thr ftscala. nt onEnt y
 mport com.tw ter.t etyp e.un nt ons.thr ftscala.Un nt onData

object Un nt onDataHydrator {
  type Type = ValueHydrator[Opt on[Un nt onData], Ctx]

  case class Ctx(
    conversat on d: Opt on[T et d],
     nt ons: Seq[ nt onEnt y],
    underly ngT etCtx: T etCtx)
      extends T etCtx.Proxy

  def apply(): Type = {
    ValueHydrator.map[Opt on[Un nt onData], Ctx] { (_, ctx) =>
      val  nt onedUser ds: Seq[User d] = ctx. nt ons.flatMap(_.user d)

      ValueState.mod f ed(
        So (Un nt onData(ctx.conversat on d, Opt on( nt onedUser ds).f lter(_.nonEmpty)))
      )
    }
  }.only f { (_, ctx) =>
    ctx.t etF eldRequested(T et.Un nt onDataF eld)
  }
}
