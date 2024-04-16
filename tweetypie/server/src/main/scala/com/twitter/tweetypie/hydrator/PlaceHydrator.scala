package com.tw ter.t etyp e
package hydrator

 mport com.tw ter.st ch.NotFound
 mport com.tw ter.t etyp e.core._
 mport com.tw ter.t etyp e.repos ory._
 mport com.tw ter.t etyp e.thr ftscala._

object PlaceHydrator {
  type Type = ValueHydrator[Opt on[Place], T etCtx]

  val HydratedF eld: F eldByPath = f eldByPath(T et.PlaceF eld)

  def apply(repo: PlaceRepos ory.Type): Type =
    ValueHydrator[Opt on[Place], T etCtx] { (_, ctx) =>
      val key = PlaceKey(ctx.place d.get, ctx.opts.languageTag)
      repo(key).l ftToTry.map {
        case Return(place) => ValueState.mod f ed(So (place))
        case Throw(NotFound) => ValueState.Unmod f edNone
        case Throw(_) => ValueState.part al(None, HydratedF eld)
      }
    }.only f { (curr, ctx) =>
      curr. sEmpty &&
      ctx.t etF eldRequested(T et.PlaceF eld) &&
      !ctx. sRet et &&
      ctx.place d.nonEmpty
    }
}
