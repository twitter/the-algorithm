package com.tw ter.t etyp e
package hydrator

 mport com.tw ter.t etyp e.core._
 mport com.tw ter.t etyp e.repos ory._
 mport com.tw ter.t etyp e.thr ftscala.F eldByPath

/**
 * Hydrates t  `conversat onMuted` f eld of T et. `conversat onMuted`
 * w ll be true  f t  conversat on that t  t et  s part of has been
 * muted by t  user. T  f eld  s perspect val, so t  result of t 
 * hydrator should never be cac d.
 */
object Conversat onMutedHydrator {
  type Type = ValueHydrator[Opt on[Boolean], Ctx]

  case class Ctx(conversat on d: Opt on[T et d], underly ngT etCtx: T etCtx)
      extends T etCtx.Proxy

  val hydratedF eld: F eldByPath = f eldByPath(T et.Conversat onMutedF eld)

  pr vate[t ] val part alResult = ValueState.part al(None, hydratedF eld)
  pr vate[t ] val mod f edTrue = ValueState.mod f ed(So (true))
  pr vate[t ] val mod f edFalse = ValueState.mod f ed(So (false))

  def apply(repo: Conversat onMutedRepos ory.Type): Type = {

    ValueHydrator[Opt on[Boolean], Ctx] { (_, ctx) =>
      (ctx.opts.forUser d, ctx.conversat on d) match {
        case (So (user d), So (convo d)) =>
          repo(user d, convo d).l ftToTry
            .map {
              case Return(true) => mod f edTrue
              case Return(false) => mod f edFalse
              case Throw(_) => part alResult
            }
        case _ =>
          ValueState.St chUnmod f edNone
      }
    }.only f { (curr, ctx) =>
      //    s unl kely that t  f eld w ll already be set, but  f, for
      // so  reason, t  hydrator  s run on a t et that already has
      // t  value set,   w ll sk p t  work to c ck aga n.
      curr. sEmpty &&
      //   only hydrate t  f eld  f    s expl c ly requested. At
      // t  t   of t  wr  ng, t  f eld  s only used for
      // d splay ng U  for toggl ng t  muted state of t  relevant
      // conversat on.
      ctx.t etF eldRequested(T et.Conversat onMutedF eld) &&
      // Ret ets are not part of a conversat on, so should not be muted.
      !ctx. sRet et
    }
  }
}
