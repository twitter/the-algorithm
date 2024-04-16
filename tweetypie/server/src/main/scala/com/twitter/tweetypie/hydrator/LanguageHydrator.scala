package com.tw ter.t etyp e
package hydrator

 mport com.tw ter.t etyp e.core._
 mport com.tw ter.t etyp e.repos ory._
 mport com.tw ter.t etyp e.thr ftscala._

object LanguageHydrator {
  type Type = ValueHydrator[Opt on[Language], T etCtx]

  val hydratedF eld: F eldByPath = f eldByPath(T et.LanguageF eld)

  pr vate[t ] def  sAppl cable(curr: Opt on[Language], ctx: T etCtx) =
    ctx.t etF eldRequested(T et.LanguageF eld) && !ctx. sRet et && curr. sEmpty

  def apply(repo: LanguageRepos ory.Type): Type =
    ValueHydrator[Opt on[Language], T etCtx] { (langOpt, ctx) =>
      repo(ctx.text).l ftToTry.map {
        case Return(So (l)) => ValueState.mod f ed(So (l))
        case Return(None) => ValueState.unmod f ed(langOpt)
        case Throw(_) => ValueState.part al(None, hydratedF eld)
      }
    }.only f((curr, ctx) =>  sAppl cable(curr, ctx))
}
