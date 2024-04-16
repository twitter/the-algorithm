package com.tw ter.t etyp e
package hydrator

 mport com.tw ter.st ch.St ch
 mport com.tw ter.t etyp e.core._
 mport com.tw ter.t etyp e.repos ory._

/**
 * Ensures that t  t et's author and s ce t et's author ( f ret et) are v s ble to t 
 * v ew ng user - ctx.opts.forUser d - w n enforceV s b l yF lter ng  s true.
 *  f e  r of t se users  s not v s ble t n a F lteredState.Suppress w ll be returned.
 *
 * Note: block ng relat onsh p  s NOT c cked  re, t   ans  f v ew ng user `forUser d`  s blocked
 * by e  r t  t et's author or s ce t et's author, t  w ll not f lter out t  t et.
 */
object T etAuthorV s b l yHydrator {
  type Type = ValueHydrator[Un , T etCtx]

  def apply(repo: UserV s b l yRepos ory.Type): Type =
    ValueHydrator[Un , T etCtx] { (_, ctx) =>
      val  ds = Seq(ctx.user d) ++ ctx.s ceUser d
      val keys =  ds.map( d => toRepoQuery( d, ctx))

      St ch
        .traverse(keys)(repo.apply).flatMap { responses =>
          val fs: Opt on[F lteredState.Unava lable] = responses.flatten. adOpt on

          fs match {
            case So (fs: F lteredState.Unava lable) => St ch.except on(fs)
            case None => ValueState.St chUnmod f edUn 
          }
        }
    }.only f((_, ctx) => ctx.opts.enforceV s b l yF lter ng)

  pr vate def toRepoQuery(user d: User d, ctx: T etCtx) =
    UserV s b l yRepos ory.Query(
      UserKey(user d),
      ctx.opts.forUser d,
      ctx.t et d,
      ctx. sRet et,
      ctx.opts. s nnerQuotedT et,
      So (ctx.opts.safetyLevel))
}
