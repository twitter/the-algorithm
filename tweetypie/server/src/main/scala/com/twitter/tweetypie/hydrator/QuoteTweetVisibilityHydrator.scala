package com.tw ter.t etyp e
package hydrator

 mport com.tw ter.t etyp e.core._
 mport com.tw ter.t etyp e.repos ory._
 mport com.tw ter.t etyp e.thr ftscala.QuotedT et

/**
 * Enforce that users are not shown quoted t ets w re t  author of t 
 *  nner quoted t et blocks t  author of t  outer quote t et or t  author
 * of t   nner quoted t et  s ot rw se not v s ble to t  outer author.
 *
 *  n t  example below, QuoteT etV s b l yHydrator c cks  f @jack
 * blocks @trollmaster.
 *
 * {{{
 *   @v e r
 *   +------------------------------+
 *   | @trollmaster                 | <-- OUTER QUOTE TWEET
 *   | lol u can't spell tw ter    |
 *   | +--------------------------+ |
 *   | | @jack                    | <----  NNER QUOTED TWEET
 *   | | just sett ng up   twttr | |
 *   | +--------------------------+ |
 *   +------------------------------+
 * }}}
 *
 *  n t  example below, QuoteT etV s b l yHydrator c cks  f @h4x0r can v ew
 * user @protectedUser.
 *
 * {{{
 *   @v e r
 *   +------------------------------+
 *   | @h4x0r                       | <-- OUTER QUOTE TWEET
 *   | lol n ce password            |
 *   | +--------------------------+ |
 *   | | @protectedUser           | <----  NNER QUOTED TWEET
 *   | |   password  s 1234      | |
 *   | +--------------------------+ |
 *   +------------------------------+
 * }}}
 *
 *
 *  n t  example below, QuoteT etV s b l yHydrator c cks  f @v e r blocks @jack:
 *
 * {{{
 *   @v e r
 *   +------------------------------+
 *   | @so t eter                 | <-- OUTER QUOTE TWEET
 *   | T   s a  tor c t et     |
 *   | +--------------------------+ |
 *   | | @jack                    | <----  NNER QUOTED TWEET
 *   | | just sett ng up   twttr | |
 *   | +--------------------------+ |
 *   +------------------------------+
 * }}}
 *
 */
object QuoteT etV s b l yHydrator {
  type Type = ValueHydrator[Opt on[F lteredState.Unava lable], T etCtx]

  def apply(repo: QuotedT etV s b l yRepos ory.Type): QuoteT etV s b l yHydrator.Type =
    ValueHydrator[Opt on[F lteredState.Unava lable], T etCtx] { (_, ctx) =>
      val  nnerT et: QuotedT et = ctx.quotedT et.get
      val request = QuotedT etV s b l yRepos ory.Request(
        outerT et d = ctx.t et d,
        outerAuthor d = ctx.user d,
         nnerT et d =  nnerT et.t et d,
         nnerAuthor d =  nnerT et.user d,
        v e r d = ctx.opts.forUser d,
        safetyLevel = ctx.opts.safetyLevel
      )

      repo(request).l ftToTry.map {
        case Return(So (f: F lteredState.Unava lable)) =>
          ValueState.mod f ed(So (f))

        // For t et::quotedT et relat onsh ps, all ot r F lteredStates
        // allow t  quotedT et to be hydrated and f ltered  ndependently
        case Return(_) =>
          ValueState.Unmod f edNone

        // On VF fa lure, gracefully degrade to no f lter ng
        case Throw(_) =>
          ValueState.Unmod f edNone
      }
    }.only f { (_, ctx) =>
      !ctx. sRet et &&
      ctx.t etF eldRequested(T et.QuotedT etF eld) &&
      ctx.opts.enforceV s b l yF lter ng &&
      ctx.quotedT et. sDef ned
    }
}
