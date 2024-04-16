package com.tw ter.t etyp e
package hydrator

 mport com.tw ter.t etyp e.core._
 mport com.tw ter.t etyp e.repos ory._
 mport com.tw ter.t etyp e.thr ftscala.F eldByPath
 mport com.tw ter.t etyp e.ut l.Takedowns

/**
 * Hydrates per-country takedowns wh ch  s a un on of:
 * 1. per-t et takedowns, from t etyp eOnlyTakedown{CountryCode|Reasons} f elds
 * 2. user takedowns, read from g zmoduck.
 *
 * Note that t  hydrator performs backwards compat b l y by convert ng to and from
 * [[com.tw ter.tseng.w hhold ng.thr ftscala.TakedownReason]].  T   s poss ble because a taken
 * down country code can always be represented as a
 * [[com.tw ter.tseng.w hhold ng.thr ftscala.Unspec f edReason]].
 */
object TakedownHydrator {
  type Type = ValueHydrator[Opt on[Takedowns], Ctx]

  case class Ctx(t etTakedowns: Takedowns, underly ngT etCtx: T etCtx) extends T etCtx.Proxy

  val hydratedF elds: Set[F eldByPath] =
    Set(
      f eldByPath(T et.TakedownCountryCodesF eld),
      f eldByPath(T et.TakedownReasonsF eld)
    )

  def apply(repo: UserTakedownRepos ory.Type): Type =
    ValueHydrator[Opt on[Takedowns], Ctx] { (curr, ctx) =>
      repo(ctx.user d).l ftToTry.map {
        case Return(userReasons) =>
          val reasons = Seq.concat(ctx.t etTakedowns.reasons, userReasons).toSet
          ValueState.delta(curr, So (Takedowns(reasons)))
        case Throw(_) =>
          ValueState.part al(curr, hydratedF elds)
      }
    }.only f { (_, ctx) =>
      (
        ctx.t etF eldRequested(T et.TakedownCountryCodesF eld) ||
        ctx.t etF eldRequested(T et.TakedownReasonsF eld)
      ) && ctx.hasTakedown
    }
}
