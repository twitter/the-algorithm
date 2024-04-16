package com.tw ter.t etyp e
package hydrator

 mport com.tw ter.featuresw c s.v2.FeatureSw chResults
 mport com.tw ter.spam.rtf.thr ftscala.SafetyLevel
 mport com.tw ter.st ch.St ch
 mport com.tw ter.st ch.t  l neserv ce.T  l neServ ce.GetPerspect ves.Query
 mport com.tw ter.t etyp e.core.ValueState
 mport com.tw ter.t etyp e.repos ory.Perspect veRepos ory
 mport com.tw ter.t etyp e.thr ftscala.Ed Control
 mport com.tw ter.t etyp e.thr ftscala.F eldByPath
 mport com.tw ter.t etyp e.thr ftscala.StatusPerspect ve
 mport com.tw ter.t etyp e.thr ftscala.T etPerspect ve

object Ed Perspect veHydrator {

  type Type = ValueHydrator[Opt on[T etPerspect ve], Ctx]
  val HydratedF eld: F eldByPath = f eldByPath(T et.Ed Perspect veF eld)

  case class Ctx(
    currentT etPerspect ve: Opt on[StatusPerspect ve],
    ed Control: Opt on[Ed Control],
    featureSw chResults: Opt on[FeatureSw chResults],
    underly ngT etCtx: T etCtx)
      extends T etCtx.Proxy

  // T  l ne safety levels determ ne so  part of h gh level traff c
  // that   m ght want to turn off w h a dec der  f ed s traff c
  //  s too b g for perspect ves to handle. T  dec der allows us
  // to turn down t  traff c w hout t   mpact on t et deta l.
  val T  l nesSafetyLevels: Set[SafetyLevel] = Set(
    SafetyLevel.T  l neFollow ngAct v y,
    SafetyLevel.T  l neHo ,
    SafetyLevel.T  l neConversat ons,
    SafetyLevel.DeprecatedT  l neConnect,
    SafetyLevel.T  l ne nt ons,
    SafetyLevel.DeprecatedT  l neAct v y,
    SafetyLevel.T  l neFavor es,
    SafetyLevel.T  l neL sts,
    SafetyLevel.T  l ne nject on,
    SafetyLevel.St ckersT  l ne,
    SafetyLevel.L veV deoT  l ne,
    SafetyLevel.QuoteT etT  l ne,
    SafetyLevel.T  l neHo Latest,
    SafetyLevel.T  l neL kedBy,
    SafetyLevel.T  l neRet etedBy,
    SafetyLevel.T  l neBookmark,
    SafetyLevel.T  l ne d a,
    SafetyLevel.T  l neReact veBlend ng,
    SafetyLevel.T  l neProf le,
    SafetyLevel.T  l neFocalT et,
    SafetyLevel.T  l neHo Recom ndat ons,
    SafetyLevel.Not f cat onsT  l neDev ceFollow,
    SafetyLevel.T  l neConversat onsDownrank ng,
    SafetyLevel.T  l neHo Top cFollowRecom ndat ons,
    SafetyLevel.T  l neHo Hydrat on,
    SafetyLevel.Follo dTop csT  l ne,
    SafetyLevel.ModeratedT etsT  l ne,
    SafetyLevel.T  l neModeratedT etsHydrat on,
    SafetyLevel.ElevatedQuoteT etT  l ne,
    SafetyLevel.T  l neConversat onsDownrank ngM n mal,
    SafetyLevel.B rdwatchNoteT etsT  l ne,
    SafetyLevel.T  l neSuperL kedBy,
    SafetyLevel.UserScopedT  l ne,
    SafetyLevel.T etScopedT  l ne,
    SafetyLevel.T  l neHo PromotedHydrat on,
    SafetyLevel.NearbyT  l ne,
    SafetyLevel.T  l neProf leAll,
    SafetyLevel.T  l neProf leSuperFollows,
    SafetyLevel.SpaceT etAvatarHo T  l ne,
    SafetyLevel.SpaceHo T  l neUprank ng,
    SafetyLevel.BlockMuteUsersT  l ne,
    SafetyLevel.R oAct onedT etT  l ne,
    SafetyLevel.T  l neScorer,
    SafetyLevel.Art cleT etT  l ne,
    SafetyLevel.DesQuoteT etT  l ne,
    SafetyLevel.Ed  toryT  l ne,
    SafetyLevel.D rect ssagesConversat onT  l ne,
    SafetyLevel.DesHo T  l ne,
    SafetyLevel.T  l neContentControls,
    SafetyLevel.T  l neFavor esSelfV ew,
    SafetyLevel.T  l neProf leSpaces,
  )
  val T etDeta lSafetyLevels: Set[SafetyLevel] = Set(
    SafetyLevel.T etDeta l,
    SafetyLevel.T etDeta lNonToo,
    SafetyLevel.T etDeta lW h nject onsHydrat on,
    SafetyLevel.DesT etDeta l,
  )

  def apply(
    repo: Perspect veRepos ory.Type,
    t  l nesGate: Gate[Un ],
    t etDeta lsGate: Gate[Un ],
    ot rSafetyLevelsGate: Gate[Un ],
    bookmarksGate: Gate[Long],
    stats: StatsRece ver
  ): Type = {

    val statsByLevel =
      SafetyLevel.l st.map { level =>
        (level, stats.counter("perspect ve_by_safety_label", level.na , "calls"))
      }.toMap
    val ed sAggregated = stats.counter("ed _perspect ve", "ed s_aggregated")

    ValueHydrator[Opt on[T etPerspect ve], Ctx] { (curr, ctx) =>
      val safetyLevel = ctx.opts.safetyLevel
      val lookupsDec der =
         f (T  l nesSafetyLevels.conta ns(safetyLevel)) t  l nesGate
        else  f (T etDeta lSafetyLevels.conta ns(safetyLevel)) t etDeta lsGate
        else ot rSafetyLevelsGate

      val t et ds: Seq[T et d] =  f (lookupsDec der()) t et dsToAggregate(ctx).toSeq else Seq()
      statsByLevel
        .getOrElse(
          safetyLevel,
          stats.counter("perspect ve_by_safety_label", safetyLevel.na , "calls"))
        . ncr(t et ds.s ze)
      ed sAggregated. ncr(t et ds.s ze)

      St ch
        .traverse(t et ds) {  d =>
          repo(
            Query(
              ctx.opts.forUser d.get,
               d,
              Perspect veHydrator.evaluatePerspect veTypes(
                ctx.opts.forUser d.get,
                bookmarksGate,
                ctx.featureSw chResults))).l ftToTry
        }.map { seq =>
           f (seq. sEmpty) {
            val ed Perspect ve = ctx.currentT etPerspect ve.map { c =>
              T etPerspect ve(
                c.favor ed,
                c.ret eted,
                c.bookmarked
              )
            }
            ValueState.delta(curr, ed Perspect ve)
          } else {
            val returns = seq.collect { case Return(r) => r }
            val aggregate = So (
              T etPerspect ve(
                favor ed =
                  returns.ex sts(_.favor ed) || ctx.currentT etPerspect ve.ex sts(_.favor ed),
                ret eted =
                  returns.ex sts(_.ret eted) || ctx.currentT etPerspect ve.ex sts(_.ret eted),
                bookmarked = So (
                  returns.ex sts(_.bookmarked.conta ns(true)) || ctx.currentT etPerspect ve.ex sts(
                    _.bookmarked.conta ns(true)))
              )
            )

             f (seq.ex sts(_. sThrow)) {
              ValueState.part al(aggregate, HydratedF eld)
            } else {
              ValueState.mod f ed(aggregate)
            }
          }
        }
    }.only f { (curr, ctx) =>
      curr. sEmpty &&
      ctx.opts.forUser d. sDef ned &&
      ctx.t etF eldRequested(T et.Ed Perspect veF eld)
    }
  }

  pr vate def t et dsToAggregate(ctx: Ctx): Set[T et d] = {
    ctx.ed Control
      .flatMap {
        case Ed Control. n  al( n  al) => So ( n  al)
        case Ed Control.Ed (ed ) => ed .ed Control n  al
        case _ => None
      }
      .map(_.ed T et ds.toSet)
      .getOrElse(Set()) - ctx.t et d
  }
}
