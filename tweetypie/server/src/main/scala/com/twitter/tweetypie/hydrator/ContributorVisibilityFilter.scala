package com.tw ter.t etyp e
package hydrator

 mport com.tw ter.t etyp e.core._
 mport com.tw ter.t etyp e.thr ftscala._

/**
 * Remove contr butor data from t et  f   should not be ava lable to t 
 * caller. T  contr butor f eld  s populated  n t  cac d
 * [[Contr butorHydrator]].
 *
 * Contr butor data  s always ava lable on t  wr e path.    s ava lable on
 * t  read path for t  t et author (or user aut nt cated as t  t et
 * author  n t  case of contr butors/teams), or  f t  caller has d sabled
 * v s b l y f lter ng.
 *
 * T  cond  on for runn ng t  f lter ng hydrator (only f) has been a
 * s ce of confus on. Keep  n m nd that t  cond  on expresses w n to
 * *remove* data, not w n to return  .
 *
 *  n short, keep data w n:
 *   !read ng || requested by author || !(enforce v s b l y f lter ng)
 *
 * Remove data w n none of t se cond  ons apply:
 *   read ng && !(requested by author) && enforce v s b l y f lter ng
 *
 */
object Contr butorV s b l yF lter {
  type Type = ValueHydrator[Opt on[Contr butor], T etCtx]

  def apply(): Type =
    ValueHydrator
      .map[Opt on[Contr butor], T etCtx] {
        case (So (_), _) => ValueState.mod f ed(None)
        case (None, _) => ValueState.unmod f ed(None)
      }
      .only f { (_, ctx) =>
        ctx.opts.cause.read ng(ctx.t et d) &&
        !ctx.opts.forUser d.conta ns(ctx.user d) &&
        ctx.opts.enforceV s b l yF lter ng
      }
}
