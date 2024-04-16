package com.tw ter.v s b l y. nterfaces.conversat ons

 mport com.tw ter.dec der.Dec der
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.g zmoduck.thr ftscala.User
 mport com.tw ter.spam.rtf.thr ftscala.F lteredReason
 mport com.tw ter.spam.rtf.thr ftscala.F lteredReason.Unspec f edReason
 mport com.tw ter.spam.rtf.thr ftscala.SafetyLevel
 mport com.tw ter.spam.rtf.thr ftscala.SafetyResult
 mport com.tw ter.st ch.St ch
 mport com.tw ter.t  l nes.render.thr ftscala.R chText
 mport com.tw ter.t  l nes.render.thr ftscala.TombstoneD splayType
 mport com.tw ter.t  l nes.render.thr ftscala.Tombstone nfo
 mport com.tw ter.t etyp e.thr ftscala.GetT etF eldsResult
 mport com.tw ter.t etyp e.thr ftscala.T etF eldsResultFa led
 mport com.tw ter.t etyp e.thr ftscala.T etF eldsResultF ltered
 mport com.tw ter.t etyp e.thr ftscala.T etF eldsResultFound
 mport com.tw ter.t etyp e.thr ftscala.T etF eldsResultNotFound
 mport com.tw ter.t etyp e.thr ftscala.T etF eldsResultState
 mport com.tw ter.v s b l y.V s b l yL brary
 mport com.tw ter.v s b l y.bu lder.t ets.Moderat onFeatures
 mport com.tw ter.v s b l y.bu lder.users.AuthorFeatures
 mport com.tw ter.v s b l y.bu lder.users.Relat onsh pFeatures
 mport com.tw ter.v s b l y.bu lder.users.V e rFeatures
 mport com.tw ter.v s b l y.common.UserRelat onsh pS ce
 mport com.tw ter.v s b l y.common.UserS ce
 mport com.tw ter.v s b l y.common.act ons. nterst  alReason
 mport com.tw ter.v s b l y.common.act ons.L m edEngage ntReason
 mport com.tw ter.v s b l y.common.act ons.TombstoneReason
 mport com.tw ter.v s b l y.common.act ons.converter.scala. nterst  alReasonConverter
 mport com.tw ter.v s b l y.common.act ons.converter.scala.Local zed ssageConverter
 mport com.tw ter.v s b l y.common.act ons.converter.scala.TombstoneReasonConverter
 mport com.tw ter.v s b l y.common.f ltered_reason.F lteredReason lper
 mport com.tw ter.v s b l y.conf gap .conf gs.V s b l yDec derGates
 mport com.tw ter.v s b l y.features.FocalT et d
 mport com.tw ter.v s b l y.features.T et d
 mport com.tw ter.v s b l y.models.Content d
 mport com.tw ter.v s b l y.models.SafetyLevel.Tombston ng
 mport com.tw ter.v s b l y.models.V e rContext
 mport com.tw ter.v s b l y.results.r chtext.Ep aphToR chText
 mport com.tw ter.v s b l y.results.r chtext.Local zed ssageToR chText
 mport com.tw ter.v s b l y.results.urt.ReasonToUrtParser
 mport com.tw ter.v s b l y.results.urt.SafetyResultToUrtParser
 mport com.tw ter.v s b l y.rules._
 mport com.tw ter.v s b l y.{thr ftscala => t}

case class TombstoneV s b l yRequest(
  conversat on d: Long,
  focalT et d: Long,
  t ets: Seq[(GetT etF eldsResult, Opt on[SafetyLevel])],
  authorMap: Map[
    Long,
    User
  ],
  moderatedT et ds: Seq[Long],
  v e rContext: V e rContext,
  useR chText: Boolean = true)

case class TombstoneV s b l yResponse(t etVerd cts: Map[Long, VfTombstone])

case class TombstoneV s b l yL brary(
  v s b l yL brary: V s b l yL brary,
  statsRece ver: StatsRece ver,
  dec der: Dec der) {

  pr vate case class TombstoneType(
    t et d: Long,
    tombstone d: Long,
    act on: Act on) {

    lazy val  s nnerTombstone: Boolean = t et d != tombstone d

    lazy val tombstoneD splayType: TombstoneD splayType = act on match {
      case _:  nterst  alL m edEngage nts | _: E rgencyDynam c nterst  al =>
        TombstoneD splayType.NonCompl ant
      case _ => TombstoneD splayType. nl ne
    }
  }

  val En: Str ng = "en"
  val V ew: Str ng = "V ew"
  val relat onsh pFeatures =
    new Relat onsh pFeatures(
      statsRece ver)
  val v s b l yDec derGates = V s b l yDec derGates(dec der)


  def toAct on(
    f lteredReason: F lteredReason,
    act onStatsRece ver: StatsRece ver
  ): Opt on[Act on] = {

    val enableLocal zed nterst  als =
      v s b l yDec derGates.enableConvosLocal zed nterst  al()
    val enableLegacy nterst  als =
      v s b l yDec derGates.enableConvosLegacy nterst  al()

    val tombstoneStatsRece ver = act onStatsRece ver.scope("tombstone")
    val  nterst  alLocalStatsRece ver =
      act onStatsRece ver.scope(" nterst  al").scope("local zed")
    val  nterst  alLegacyStatsRece ver =
      act onStatsRece ver.scope(" nterst  al").scope("legacy")

    f lteredReason match {
      case _  f F lteredReason lper. sTombstone(f lteredReason) =>
        createLocal zedTombstone(f lteredReason, tombstoneStatsRece ver) match {
          case tombstoneOpt @ So (Local zedTombstone(_, _)) => tombstoneOpt
          case _ =>
            createTombstone(Ep aph.Unava lable, tombstoneStatsRece ver, So ("emptyTombstone"))
        }

      case _
           f enableLocal zed nterst  als &&
            F lteredReason lper. sLocal zedSuppressedReason nterst  al(f lteredReason) =>
        F lteredReason lper.getLocal zedSuppressedReason nterst  al(f lteredReason) match {
          case So (t. nterst  al(reasonOpt, So ( ssage))) =>
             nterst  alReasonConverter.fromThr ft(reasonOpt).map {  nterst  alReason =>
               nterst  alLocalStatsRece ver.counter(" nterst  al"). ncr()
               nterst  al(
                Reason.from nterst  alReason( nterst  alReason),
                So (Local zed ssageConverter.fromThr ft( ssage)))
            }

          case _ => None
        }

      case _  f F lteredReason lper.conta nNsfw d a(f lteredReason) =>
        None

      case _  f F lteredReason lper.poss blyUndes rable(f lteredReason) =>
        None

      case _  f F lteredReason lper.reportedT et(f lteredReason) =>
        f lteredReason match {
          case F lteredReason.ReportedT et(true) =>
             nterst  alLegacyStatsRece ver.counter("fr_reported"). ncr()
            So ( nterst  al(Reason.V e rReportedAuthor))

          case F lteredReason.SafetyResult(safetyResult: SafetyResult)
               f enableLegacy nterst  als =>
            val safetyResultReported =  nterst  alReasonConverter
              .fromAct on(safetyResult.act on).collect {
                case  nterst  alReason.V e rReportedT et => true
                case  nterst  alReason.V e rReportedAuthor => true
              }.getOrElse(false)

             f (safetyResultReported) {
               nterst  alLegacyStatsRece ver.counter("reported_author"). ncr()
              So ( nterst  al(Reason.V e rReportedAuthor))
            } else None

          case _ => None
        }

      case _  f F lteredReason lper.t etMatc sV e rMutedKeyword(f lteredReason) =>
        f lteredReason match {
          case F lteredReason.T etMatc sV e rMutedKeyword(_) =>
             nterst  alLegacyStatsRece ver.counter("fr_muted_keyword"). ncr()
            So ( nterst  al(Reason.MutedKeyword))

          case F lteredReason.SafetyResult(safetyResult: SafetyResult)
               f enableLegacy nterst  als =>
            val safetyResultMutedKeyword =  nterst  alReasonConverter
              .fromAct on(safetyResult.act on).collect {
                case _:  nterst  alReason.Matc sMutedKeyword => true
              }.getOrElse(false)

             f (safetyResultMutedKeyword) {
               nterst  alLegacyStatsRece ver.counter("muted_keyword"). ncr()
              So ( nterst  al(Reason.MutedKeyword))
            } else None

          case _ => None
        }

      case _ =>
        None
    }
  }

  def toAct on(
    tfrs: T etF eldsResultState,
    act onStatsRece ver: StatsRece ver
  ): Opt on[Act on] = {

    val enableLocal zed nterst  als = v s b l yDec derGates.enableConvosLocal zed nterst  al()
    val enableLegacy nterst  als = v s b l yDec derGates.enableConvosLegacy nterst  al()

    val tombstoneStatsRece ver = act onStatsRece ver.scope("tombstone")
    val  nterst  alLocalStatsRece ver =
      act onStatsRece ver.scope(" nterst  al").scope("local zed")
    val  nterst  alLegacyStatsRece ver =
      act onStatsRece ver.scope(" nterst  al").scope("legacy")

    tfrs match {

      case T etF eldsResultState.NotFound(T etF eldsResultNotFound(_, _, So (f lteredReason)))
           f F lteredReason lper. sTombstone(f lteredReason) =>
        createLocal zedTombstone(f lteredReason, tombstoneStatsRece ver)

      case T etF eldsResultState.NotFound(tfr: T etF eldsResultNotFound)  f tfr.deleted =>
        createTombstone(Ep aph.Deleted, tombstoneStatsRece ver)

      case T etF eldsResultState.NotFound(_: T etF eldsResultNotFound) =>
        createTombstone(Ep aph.NotFound, tombstoneStatsRece ver)

      case T etF eldsResultState.Fa led(T etF eldsResultFa led(_, _, _)) =>
        createTombstone(Ep aph.Unava lable, tombstoneStatsRece ver, So ("fa led"))

      case T etF eldsResultState.F ltered(T etF eldsResultF ltered(Unspec f edReason(true))) =>
        createTombstone(Ep aph.Unava lable, tombstoneStatsRece ver, So ("f ltered"))

      case T etF eldsResultState.F ltered(T etF eldsResultF ltered(f lteredReason)) =>
        toAct on(f lteredReason, act onStatsRece ver)

      case T etF eldsResultState.Found(T etF eldsResultFound(_, _, So (f lteredReason)))
           f enableLocal zed nterst  als &&
            F lteredReason lper. sSuppressedReasonPubl c nterest nterst al(f lteredReason) =>
         nterst  alLocalStatsRece ver.counter(" p "). ncr()
        F lteredReason lper
          .getSafetyResult(f lteredReason)
          .flatMap(_.reason)
          .flatMap(Publ c nterest.SafetyResultReasonToReason.get) match {
          case So (safetyResultReason) =>
            F lteredReason lper
              .getSuppressedReasonPubl c nterest nterst al(f lteredReason)
              .map(ed  => ed .local zed ssage)
              .map(tlm => Local zed ssageConverter.fromThr ft(tlm))
              .map(lm =>
                 nterst  alL m edEngage nts(
                  safetyResultReason,
                  So (L m edEngage ntReason.NonCompl ant),
                  lm))
          case _ => None
        }

      case T etF eldsResultState.Found(T etF eldsResultFound(_, _, So (f lteredReason)))
           f enableLegacy nterst  als &&
            F lteredReason lper. sSuppressedReasonPubl c nterest nterst al(f lteredReason) =>
         nterst  alLegacyStatsRece ver.counter(" p "). ncr()
        F lteredReason lper
          .getSafetyResult(f lteredReason)
          .flatMap(_.reason)
          .flatMap(Publ c nterest.SafetyResultReasonToReason.get)
          .map( nterst  alL m edEngage nts(_, So (L m edEngage ntReason.NonCompl ant)))

      case T etF eldsResultState.Found(T etF eldsResultFound(_, _, So (f lteredReason)))
           f enableLocal zed nterst  als &&
            F lteredReason lper. sLocal zedSuppressedReasonE rgencyDynam c nterst  al(
              f lteredReason) =>
         nterst  alLocalStatsRece ver.counter("ed "). ncr()
        F lteredReason lper
          .getSuppressedReasonE rgencyDynam c nterst  al(f lteredReason)
          .map(e =>
            E rgencyDynam c nterst  al(
              e.copy,
              e.l nk,
              Local zed ssageConverter.fromThr ft(e.local zed ssage)))

      case T etF eldsResultState.Found(T etF eldsResultFound(_, _, So (f lteredReason)))
           f enableLegacy nterst  als &&
            F lteredReason lper. sSuppressedReasonE rgencyDynam c nterst  al(f lteredReason) =>
         nterst  alLegacyStatsRece ver.counter("ed "). ncr()
        F lteredReason lper
          .getSuppressedReasonE rgencyDynam c nterst  al(f lteredReason)
          .map(e => E rgencyDynam c nterst  al(e.copy, e.l nk))

      case T etF eldsResultState.Found(T etF eldsResultFound(t et, _, _))
           f t et.perspect ve.ex sts(_.reported) =>
         nterst  alLegacyStatsRece ver.counter("reported"). ncr()
        So ( nterst  al(Reason.V e rReportedAuthor))

      case T etF eldsResultState.Found(
            T etF eldsResultFound(_, _, So (Unspec f edReason(true)))) =>
        None

      case T etF eldsResultState.Found(T etF eldsResultFound(_, _, So (f lteredReason))) =>
        toAct on(f lteredReason, act onStatsRece ver)

      case _ =>
        None
    }
  }

  pr vate[conversat ons] def shouldTruncateDescendantsW nFocal(act on: Act on): Boolean =
    act on match {
      case _:  nterst  alL m edEngage nts | _: E rgencyDynam c nterst  al =>
        true
      case Tombstone(Ep aph.Bounced, _) | Tombstone(Ep aph.BounceDeleted, _) =>
        true
      case Local zedTombstone(TombstoneReason.Bounced, _) |
          Local zedTombstone(TombstoneReason.BounceDeleted, _) =>
        true
      case L m edEngage nts(L m edEngage ntReason.NonCompl ant, _) =>
        true
      case _ => false
    }

  def apply(request: TombstoneV s b l yRequest): St ch[TombstoneV s b l yResponse] = {

    val moderat onFeatures = new Moderat onFeatures(
      moderat onS ce = request.moderatedT et ds.conta ns,
      statsRece ver = statsRece ver
    )

    val userS ce = UserS ce.fromFunct on({
      case (user d, _) =>
        request.authorMap
          .get(user d)
          .map(St ch.value).getOrElse(St ch.NotFound)
    })

    val authorFeatures = new AuthorFeatures(userS ce, statsRece ver)
    val v e rFeatures = new V e rFeatures(userS ce, statsRece ver)

    val languageTag = request.v e rContext.requestCountryCode.getOrElse(En)
    val f rstRound: Seq[(GetT etF eldsResult, Opt on[TombstoneType])] = request.t ets.map {
      case (gtfr, safetyLevel) =>
        val act onStats = statsRece ver
          .scope("act on")
          .scope(safetyLevel.map(_.toStr ng().toLo rCase()).getOrElse("unknown_safety_level"))
        toAct on(gtfr.t etResult, act onStats) match {
          case So (act on) =>
            (gtfr, So (TombstoneType(gtfr.t et d, gtfr.t et d, act on)))

          case None =>
            val quotedT et d: Opt on[Long] = gtfr.t etResult match {
              case T etF eldsResultState.Found(T etF eldsResultFound(t et, _, _)) =>
                t et.quotedT et.map(_.t et d)
              case _ => None
            }

            (quotedT et d, gtfr.quotedT etResult) match {
              case (So (quotedT et d), So (tfrs)) =>
                val qtAct onStats = act onStats.scope("quoted")
                toAct on(tfrs, qtAct onStats) match {
                  case None =>
                    (gtfr, None)

                  case So (act on) =>
                    (gtfr, So (TombstoneType(gtfr.t et d, quotedT et d, act on)))
                }

              case _ =>
                (gtfr, None)
            }
        }
    }

    val (f rstRoundAct ons, secondRound nput) = f rstRound.part  on {
      case (_, So (tombstoneType)) =>
        !tombstoneType. s nnerTombstone
      case (_, None) => false
    }

    def  nvokeV s b l yL brary(t et d: Long, author: User): St ch[Act on] = {
      v s b l yL brary
        .runRuleEng ne(
          Content d.T et d(t et d),
          v s b l yL brary.featureMapBu lder(
            Seq(
              v e rFeatures.forV e rContext(request.v e rContext),
              moderat onFeatures.forT et d(t et d),
              authorFeatures.forAuthor(author),
              relat onsh pFeatures
                .forAuthor(author, request.v e rContext.user d),
              _.w hConstantFeature(T et d, t et d),
              _.w hConstantFeature(FocalT et d, request.focalT et d)
            )
          ),
          request.v e rContext,
          Tombston ng
        ).map(_.verd ct)
    }

    val secondRoundAct ons: St ch[Seq[(GetT etF eldsResult, Opt on[TombstoneType])]] =
      St ch.traverse(secondRound nput) {
        case (gtfr: GetT etF eldsResult, f rstRoundTombstone: Opt on[TombstoneType]) =>
          val secondRoundTombstone: St ch[Opt on[TombstoneType]] = gtfr.t etResult match {
            case T etF eldsResultState.Found(T etF eldsResultFound(t et, _, _)) =>
              val t et d = t et. d

              t et.coreData
                .flatMap { coreData => request.authorMap.get(coreData.user d) } match {
                case So (author) =>
                   nvokeV s b l yL brary(t et d, author).flatMap {
                    case Allow =>
                      val quotedT et d = t et.quotedT et.map(_.t et d)
                      val quotedT etAuthor = t et.quotedT et.flatMap { qt =>
                        request.authorMap.get(qt.user d)
                      }

                      (quotedT et d, quotedT etAuthor) match {
                        case (So (quotedT et d), So (quotedT etAuthor)) =>
                           nvokeV s b l yL brary(quotedT et d, quotedT etAuthor).flatMap {
                            case Allow =>
                              St ch.None

                            case reason =>
                              St ch.value(So (TombstoneType(t et d, quotedT et d, reason)))
                          }

                        case _ =>
                          St ch.None
                      }

                    case reason =>
                      St ch.value(So (TombstoneType(t et d, t et d, reason)))
                  }

                case None =>
                  St ch.None
              }

            case _ =>
              St ch.None
          }

          secondRoundTombstone.map { opt => opt.orElse(f rstRoundTombstone) }.map { opt =>
            (gtfr, opt)
          }
      }

    secondRoundAct ons.map { secondRound =>
      val tombstones: Seq[(Long, VfTombstone)] = (f rstRoundAct ons ++ secondRound).flatMap {
        case (gtfr, tombstoneTypeOpt) => {

          val nonCompl antL m edEngage ntsOpt = gtfr.t etResult match {
            case T etF eldsResultState.Found(T etF eldsResultFound(_, _, So (f lteredReason)))
                 f F lteredReason lper. sL m edEngage ntsNonCompl ant(f lteredReason) =>
              So (L m edEngage nts(L m edEngage ntReason.NonCompl ant))
            case _ => None
          }

          (tombstoneTypeOpt, nonCompl antL m edEngage ntsOpt) match {
            case (So (tombstoneType), nonCompl antOpt) =>
              val tombstone d = tombstoneType.tombstone d
              val act on = tombstoneType.act on
              val textOpt: Opt on[R chText] = act on match {

                case  nterst  alL m edEngage nts(_, _, So (local zed ssage), _) =>
                  So (Local zed ssageToR chText(local zed ssage))
                case  p :  nterst  alL m edEngage nts =>
                  So (
                    SafetyResultToUrtParser.fromSafetyResultToR chText(
                      SafetyResult(
                        So (Publ c nterest.ReasonToSafetyResultReason( p .reason)),
                         p .toAct onThr ft()
                      ),
                      languageTag
                    )
                  )

                case E rgencyDynam c nterst  al(_, _, So (local zed ssage), _) =>
                  So (Local zed ssageToR chText(local zed ssage))
                case ed : E rgencyDynam c nterst  al =>
                  So (
                    SafetyResultToUrtParser.fromSafetyResultToR chText(
                      SafetyResult(
                        None,
                        ed .toAct onThr ft()
                      ),
                      languageTag
                    )
                  )

                case Tombstone(ep aph, _) =>
                   f (request.useR chText)
                    So (Ep aphToR chText(ep aph, languageTag))
                  else
                    So (Ep aphToR chText(Ep aph.Unava lableW houtL nk, languageTag))

                case Local zedTombstone(_,  ssage) =>
                   f (request.useR chText)
                    So (Local zed ssageToR chText(Local zed ssageConverter.toThr ft( ssage)))
                  else
                    So (Ep aphToR chText(Ep aph.Unava lableW houtL nk, languageTag))

                case  nterst  al(_, So (local zed ssage), _) =>
                  So (Local zed ssageToR chText.apply(local zed ssage))

                case  nterst  al:  nterst  al =>
                  ReasonToUrtParser.fromReasonToR chText( nterst  al.reason, languageTag)

                case _ =>
                  None
              }

              val  sRoot: Boolean = gtfr.t et d == request.conversat on d
              val  sOuter: Boolean = tombstone d == request.conversat on d
              val revealTextOpt: Opt on[R chText] = act on match {
                case _:  nterst  alL m edEngage nts | _: E rgencyDynam c nterst  al
                     f  sRoot &&  sOuter =>
                  None

                case _:  nterst  al | _:  nterst  alL m edEngage nts |
                    _: E rgencyDynam c nterst  al =>
                  So (ReasonToUrtParser.getR chRevealText(languageTag))

                case _ =>
                  None
              }

              val  ncludeT et = act on match {
                case _:  nterst  al | _:  nterst  alL m edEngage nts |
                    _: E rgencyDynam c nterst  al =>
                  true
                case _ => false
              }

              val truncateForAct on: Boolean =
                shouldTruncateDescendantsW nFocal(act on)
              val truncateForNonCompl ant: Boolean =
                nonCompl antOpt
                  .map(shouldTruncateDescendantsW nFocal).getOrElse(false)
              val truncateDescendants: Boolean =
                truncateForAct on || truncateForNonCompl ant

              val tombstone = textOpt match {
                case So (_)  f request.useR chText =>
                  VfTombstone(
                     ncludeT et =  ncludeT et,
                    act on = act on,
                    tombstone nfo = So (
                      Tombstone nfo(
                        cta = None,
                        revealText = None,
                        r chText = textOpt,
                        r chRevealText = revealTextOpt
                      )
                    ),
                    tombstoneD splayType = tombstoneType.tombstoneD splayType,
                    truncateDescendantsW nFocal = truncateDescendants
                  )
                case So (_) =>
                  VfTombstone(
                     ncludeT et =  ncludeT et,
                    act on = act on,
                    tombstone nfo = So (
                      Tombstone nfo(
                        text = textOpt
                          .map(r chText => r chText.text).getOrElse(
                            ""
                        cta = None,
                        revealText = revealTextOpt.map(_.text),
                        r chText = None,
                        r chRevealText = None
                      )
                    ),
                    tombstoneD splayType = tombstoneType.tombstoneD splayType,
                    truncateDescendantsW nFocal = truncateDescendants
                  )

                case None =>
                  VfTombstone(
                     ncludeT et = false,
                    act on = act on,
                    tombstone nfo = So (
                      Tombstone nfo(
                        cta = None,
                        revealText = None,
                        r chText = So (Ep aphToR chText(Ep aph.Unava lable, languageTag)),
                        r chRevealText = None
                      )
                    ),
                    tombstoneD splayType = tombstoneType.tombstoneD splayType,
                    truncateDescendantsW nFocal = truncateDescendants
                  )
              }

              So ((gtfr.t et d, tombstone))

            case (None, So (l m edEngage nts))
                 f shouldTruncateDescendantsW nFocal(l m edEngage nts) =>
              val tombstone = VfTombstone(
                tombstone d = gtfr.t et d,
                 ncludeT et = true,
                act on = l m edEngage nts,
                tombstone nfo = None,
                tombstoneD splayType = TombstoneD splayType.NonCompl ant,
                truncateDescendantsW nFocal = true
              )
              So ((gtfr.t et d, tombstone))

            case _ =>
              None
          }
        }
      }

      TombstoneV s b l yResponse(
        t etVerd cts = tombstones.toMap
      )
    }
  }

  pr vate def createLocal zedTombstone(
    f lteredReason: F lteredReason,
    tombstoneStats: StatsRece ver,
  ): Opt on[Local zedTombstone] = {

    val tombstoneOpt = F lteredReason lper.getTombstone(f lteredReason)
    tombstoneOpt match {
      case So (t.Tombstone(reasonOpt, So ( ssage))) =>
        TombstoneReasonConverter.fromThr ft(reasonOpt).map { localReason =>
          tombstoneStats
            .scope("local zed").counter(localReason.toStr ng().toLo rCase()). ncr()
          Local zedTombstone(localReason, Local zed ssageConverter.fromThr ft( ssage))
        }

      case _ => None
    }
  }

  pr vate def createTombstone(
    ep aph: Ep aph,
    tombstoneStats: StatsRece ver,
    extraCounterOpt: Opt on[Str ng] = None
  ): Opt on[Act on] = {
    tombstoneStats
      .scope("legacy")
      .counter(ep aph.toStr ng().toLo rCase())
      . ncr()
    extraCounterOpt.map { extraCounter =>
      tombstoneStats
        .scope("legacy")
        .scope(ep aph.toStr ng().toLo rCase())
        .counter(extraCounter)
        . ncr()
    }
    So (Tombstone(ep aph))
  }
}
