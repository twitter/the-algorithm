package com.tw ter.t etyp e
package hydrator

 mport com.tw ter.featuresw c s.v2.FeatureSw chResults
 mport com.tw ter.spam.rtf.thr ftscala.SafetyLevel
 mport com.tw ter.st ch.St ch
 mport com.tw ter.st ch.t  l neserv ce.T  l neServ ce.GetPerspect ves.Query
 mport com.tw ter.t  l neserv ce.{thr ftscala => tls}
 mport com.tw ter.t etyp e.core._
 mport com.tw ter.t etyp e.repos ory.Perspect veRepos ory
 mport com.tw ter.t etyp e.thr ftscala.F eldByPath
 mport com.tw ter.t etyp e.thr ftscala.StatusPerspect ve

object Perspect veHydrator {
  type Type = ValueHydrator[Opt on[StatusPerspect ve], Ctx]
  val hydratedF eld: F eldByPath = f eldByPath(T et.Perspect veF eld)

  case class Ctx(featureSw chResults: Opt on[FeatureSw chResults], underly ngT etCtx: T etCtx)
      extends T etCtx.Proxy

  val Types: Set[tls.Perspect veType] =
    Set(
      tls.Perspect veType.Reported,
      tls.Perspect veType.Favor ed,
      tls.Perspect veType.Ret eted,
      tls.Perspect veType.Bookmarked
    )

  val TypesW houtBookmarked: Set[tls.Perspect veType] =
    Set(
      tls.Perspect veType.Reported,
      tls.Perspect veType.Favor ed,
      tls.Perspect veType.Ret eted
    )

  pr vate[t ] val part alResult = ValueState.part al(None, hydratedF eld)

  val bookmarksPerspect veHydrat onEnabledKey = "bookmarks_perspect ve_hydrat on_enabled"

  def evaluatePerspect veTypes(
    user d: Long,
    bookmarksPerspect veDec der: Gate[Long],
    featureSw chResults: Opt on[FeatureSw chResults]
  ): Set[tls.Perspect veType] = {
     f (bookmarksPerspect veDec der(user d) ||
      featureSw chResults
        .flatMap(_.getBoolean(bookmarksPerspect veHydrat onEnabledKey, false))
        .getOrElse(false))
      Types
    else
      TypesW houtBookmarked
  }

  def apply(
    repo: Perspect veRepos ory.Type,
    shouldHydrateBookmarksPerspect ve: Gate[Long],
    stats: StatsRece ver
  ): Type = {
    val statsByLevel =
      SafetyLevel.l st.map(level => (level, stats.counter(level.na , "calls"))).toMap

    ValueHydrator[Opt on[StatusPerspect ve], Ctx] { (_, ctx) =>
      val res: St ch[tls.T  l neEntryPerspect ve] =  f (ctx. sRet et) {
        St ch.value(
          tls.T  l neEntryPerspect ve(
            favor ed = false,
            ret et d = None,
            ret eted = false,
            reported = false,
            bookmarked = None
          )
        )
      } else {
        statsByLevel
          .getOrElse(ctx.opts.safetyLevel, stats.counter(ctx.opts.safetyLevel.na , "calls"))
          . ncr()

        repo(
          Query(
            user d = ctx.opts.forUser d.get,
            t et d = ctx.t et d,
            types = evaluatePerspect veTypes(
              ctx.opts.forUser d.get,
              shouldHydrateBookmarksPerspect ve,
              ctx.featureSw chResults)
          ))
      }

      res.l ftToTry.map {
        case Return(perspect ve) =>
          ValueState.mod f ed(
            So (
              StatusPerspect ve(
                user d = ctx.opts.forUser d.get,
                favor ed = perspect ve.favor ed,
                ret eted = perspect ve.ret eted,
                ret et d = perspect ve.ret et d,
                reported = perspect ve.reported,
                bookmarked = perspect ve.bookmarked
              )
            )
          )
        case _ => part alResult
      }

    }.only f { (curr, ctx) =>
      curr. sEmpty &&
      ctx.opts.forUser d.nonEmpty &&
      (ctx.t etF eldRequested(T et.Perspect veF eld) || ctx.opts.excludeReported)
    }
  }
}
