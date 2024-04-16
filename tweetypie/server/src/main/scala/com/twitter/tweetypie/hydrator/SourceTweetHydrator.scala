package com.tw ter.t etyp e
package hydrator

 mport com.tw ter.st ch.NotFound
 mport com.tw ter.st ch.St ch
 mport com.tw ter.t etyp e.core.F lteredState.Unava lable._
 mport com.tw ter.t etyp e.core.T etResult
 mport com.tw ter.t etyp e.core.ValueState
 mport com.tw ter.t etyp e.repos ory.T etQuery
 mport com.tw ter.t etyp e.repos ory.T etResultRepos ory
 mport com.tw ter.t etyp e.thr ftscala.Detac dRet et

/**
 * Loads t  s ce t et for a ret et
 */
object S ceT etHydrator {
  type Type = ValueHydrator[Opt on[T etResult], T etCtx]

  def conf gureOpt ons(opts: T etQuery.Opt ons): T etQuery.Opt ons = {
    // set scrubUnrequestedF elds to false so that   w ll have access to
    // add  onal f elds, wh ch w ll be cop ed  nto t  ret et.
    // set fetchStoredT ets to false because   don't want to fetch and hydrate
    // t  s ce t et  f    s deleted.
    opts.copy(scrubUnrequestedF elds = false, fetchStoredT ets = false,  sS ceT et = true)
  }

  pr vate object NotFoundExcept on {
    def unapply(t: Throwable): Opt on[Boolean] =
      t match {
        case NotFound => So (false)
        case T etDeleted | BounceDeleted => So (true)
        case _ => None
      }
  }

  def apply(
    repo: T etResultRepos ory.Type,
    stats: StatsRece ver,
    scr beDetac dRet ets: FutureEffect[Detac dRet et] = FutureEffect.un 
  ): Type = {
    val notFoundCounter = stats.counter("not_found")

    ValueHydrator[Opt on[T etResult], T etCtx] { (_, ctx) =>
      ctx.s ceT et d match {
        case None =>
          ValueState.St chUnmod f edNone
        case So (srcT et d) =>
          repo(srcT et d, conf gureOpt ons(ctx.opts)).l ftToTry.flatMap {
            case Throw(NotFoundExcept on( sDeleted)) =>
              notFoundCounter. ncr()
              scr beDetac dRet ets(detac dRet et(srcT et d, ctx))
               f (ctx.opts.requ reS ceT et) {
                St ch.except on(S ceT etNotFound( sDeleted))
              } else {
                ValueState.St chUnmod f edNone
              }

            case Return(r) => St ch.value(ValueState.mod f ed(So (r)))
            case Throw(t) => St ch.except on(t)
          }
      }
    }.only f((curr, _) => curr. sEmpty)
  }

  def detac dRet et(srcT et d: T et d, ctx: T etCtx): Detac dRet et =
    Detac dRet et(ctx.t et d, ctx.user d, srcT et d)
}
