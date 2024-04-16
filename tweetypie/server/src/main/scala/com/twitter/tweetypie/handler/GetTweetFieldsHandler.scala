package com.tw ter.t etyp e
package handler

 mport com.tw ter.conta ner.thr ftscala.Mater al zeAsT etF eldsRequest
 mport com.tw ter.context.Test ngS gnalsContext
 mport com.tw ter.servo.ut l.FutureArrow
 mport com.tw ter.spam.rtf.thr ftscala.F lteredReason
 mport com.tw ter.spam.rtf.thr ftscala.SafetyLevel
 mport com.tw ter.st ch.NotFound
 mport com.tw ter.st ch.St ch
 mport com.tw ter.t etyp e.core.F lteredState
 mport com.tw ter.t etyp e.core._
 mport com.tw ter.t etyp e.repos ory.DeletedT etV s b l yRepos ory
 mport com.tw ter.t etyp e.repos ory._
 mport com.tw ter.t etyp e.thr ftscala.T etF eldsResultState
 mport com.tw ter.t etyp e.thr ftscala._

/**
 * Handler for t  `getT etF elds` endpo nt.
 */
object GetT etF eldsHandler {
  type Type = GetT etF eldsRequest => Future[Seq[GetT etF eldsResult]]

  def apply(
    t etRepo: T etResultRepos ory.Type,
    deletedT etV s b l yRepo: DeletedT etV s b l yRepos ory.Type,
    conta nerAsGetT etF eldsResultRepo: Creat vesConta nerMater al zat onRepos ory.GetT etF eldsType,
    stats: StatsRece ver,
    shouldMater al zeConta ners: Gate[Un ]
  ): Type = {
    FutureArrow[GetT etF eldsRequest, Seq[GetT etF eldsResult]] { request =>
      val queryOpt ons = toT etQueryOpt ons(request.opt ons)

      St ch.run(
        St ch.traverse(request.t et ds) {  d =>
          t etRepo( d, queryOpt ons).l ftToTry.flatMap { t etResult =>
            toGetT etF eldsResult(
               d,
              t etResult,
              request.opt ons,
              deletedT etV s b l yRepo,
              conta nerAsGetT etF eldsResultRepo,
              stats,
              shouldMater al zeConta ners
            )
          }
        }
      )
    }
  }

  /**
   * Converts a `GetT etF eldsOpt ons`  nto an  nternal `T etQuery.Opt ons`.
   */
  def toT etQueryOpt ons(opt ons: GetT etF eldsOpt ons): T etQuery.Opt ons = {
    val  ncludes = opt ons.t et ncludes
    val shouldSk pCac  = Test ngS gnalsContext().flatMap(_.s mulateBackPressure).nonEmpty
    val cac Control =
       f (shouldSk pCac ) Cac Control.NoCac 
      else  f (opt ons.doNotCac ) Cac Control.ReadOnlyCac 
      else Cac Control.ReadWr eCac 

    T etQuery.Opt ons(
       nclude = T etQuery
        . nclude(
          t etF elds =  ncludes.collect {
            case T et nclude.T etF eld d( d) =>  d
            case T et nclude.CountsF eld d(_) => T et.CountsF eld. d
            case T et nclude. d aEnt yF eld d(_) => T et. d aF eld. d
          }.toSet,
          countsF elds =  ncludes.collect { case T et nclude.CountsF eld d( d) =>  d }.toSet,
           d aF elds =  ncludes.collect { case T et nclude. d aEnt yF eld d( d) =>  d }.toSet,
          quotedT et = opt ons. ncludeQuotedT et,
          pasted d a = true
        ).also(
          /**
           * Always fetch ng underly ng creat ves conta ner  d. see
           * [[hydrateCreat veConta nerBackedT et]] for more deta l.
           */
          t etF elds = Seq(T et.Underly ngCreat vesConta ner dF eld. d)
        ),
      cac Control = cac Control,
      enforceV s b l yF lter ng = opt ons.v s b l yPol cy == T etV s b l yPol cy.UserV s ble,
      safetyLevel = opt ons.safetyLevel.getOrElse(SafetyLevel.F lterNone),
      forUser d = opt ons.forUser d,
      languageTag = opt ons.languageTag.getOrElse("en"),
      cardsPlatformKey = opt ons.cardsPlatformKey,
      extens onsArgs = opt ons.extens onsArgs,
      forExternalConsumpt on = true,
      s mpleQuotedT et = opt ons.s mpleQuotedT et
    )
  }

  def toGetT etF eldsResult(
    t et d: T et d,
    res: Try[T etResult],
    opt ons: GetT etF eldsOpt ons,
    deletedT etV s b l yRepo: DeletedT etV s b l yRepos ory.Type,
    conta nerAsGetT etF eldsResultRepo: Creat vesConta nerMater al zat onRepos ory.GetT etF eldsType,
    stats: StatsRece ver,
    shouldMater al zeConta ners: Gate[Un ]
  ): St ch[GetT etF eldsResult] = {
    val  asureRacyReads: T et d => Un  = trackLossyReadsAfterWr e(
      stats.stat("racy_reads", "get_t et_f elds"),
      Durat on.fromSeconds(3)
    )

    res match {
      case Throw(NotFound) =>
         asureRacyReads(t et d)
        St ch.value(GetT etF eldsResult(t et d, NotFoundResultState))

      case Throw(ex) =>
        val resultStateSt ch = fa lureResultState(ex) match {
          case notFoundResultState @ T etF eldsResultState.NotFound(_) =>
            deletedT etV s b l yRepo(
              DeletedT etV s b l yRepos ory.V s b l yRequest(
                ex,
                t et d,
                opt ons.safetyLevel,
                opt ons.forUser d,
                 s nnerQuotedT et = false
              )
            ).map(w hV s b l yF lteredReason(notFoundResultState, _))
          case res => St ch.value(res)
        }
        resultStateSt ch.map(res => GetT etF eldsResult(t et d, res))
      case Return(r) =>
        toT etF eldsResult(
          r,
          opt ons,
          deletedT etV s b l yRepo,
          conta nerAsGetT etF eldsResultRepo,
          stats,
          shouldMater al zeConta ners
        ).flatMap { getT etF eldsResult =>
          hydrateCreat veConta nerBackedT et(
            r.value.t et.underly ngCreat vesConta ner d,
            getT etF eldsResult,
            opt ons,
            conta nerAsGetT etF eldsResultRepo,
            t et d,
            stats,
            shouldMater al zeConta ners
          )
        }
    }
  }

  pr vate def fa lureResultState(ex: Throwable): T etF eldsResultState =
    ex match {
      case F lteredState.Unava lable.T etDeleted => DeletedResultState
      case F lteredState.Unava lable.BounceDeleted => BounceDeletedResultState
      case F lteredState.Unava lable.S ceT etNotFound(d) => notFoundResultState(deleted = d)
      case F lteredState.Unava lable.Author.NotFound => NotFoundResultState
      case fs: F lteredState.HasF lteredReason => toF lteredState(fs.f lteredReason)
      case OverCapac y(_) => toFa ledState(overcapac y = true, None)
      case _ => toFa ledState(overcapac y = false, So (ex.toStr ng))
    }

  pr vate val NotFoundResultState = T etF eldsResultState.NotFound(T etF eldsResultNotFound())

  pr vate val DeletedResultState = T etF eldsResultState.NotFound(
    T etF eldsResultNotFound(deleted = true)
  )

  pr vate val BounceDeletedResultState = T etF eldsResultState.NotFound(
    T etF eldsResultNotFound(deleted = true, bounceDeleted = true)
  )

  def notFoundResultState(deleted: Boolean): T etF eldsResultState.NotFound =
     f (deleted) DeletedResultState else NotFoundResultState

  pr vate def toFa ledState(
    overcapac y: Boolean,
     ssage: Opt on[Str ng]
  ): T etF eldsResultState =
    T etF eldsResultState.Fa led(T etF eldsResultFa led(overcapac y,  ssage))

  pr vate def toF lteredState(reason: F lteredReason): T etF eldsResultState =
    T etF eldsResultState.F ltered(
      T etF eldsResultF ltered(reason = reason)
    )

  /**
   * Converts a `T etResult`  nto a `GetT etF eldsResult`.  For ret ets, m ss ng or f ltered s ce
   * t ets cause t  ret et to be treated as m ss ng or f ltered.
   */
  pr vate def toT etF eldsResult(
    t etResult: T etResult,
    opt ons: GetT etF eldsOpt ons,
    deletedT etV s b l yRepo: DeletedT etV s b l yRepos ory.Type,
    creat vesConta nerRepo: Creat vesConta nerMater al zat onRepos ory.GetT etF eldsType,
    stats: StatsRece ver,
    shouldMater al zeConta ners: Gate[Un ]
  ): St ch[GetT etF eldsResult] = {
    val pr maryResultState = toT etF eldsResultState(t etResult, opt ons)
    val quotedResultStateSt ch = pr maryResultState match {
      case T etF eldsResultState.Found(_)  f opt ons. ncludeQuotedT et =>
        val t etData = t etResult.value.s ceT etResult
          .getOrElse(t etResult)
          .value
        t etData.quotedT etResult
          .map {
            case QuotedT etResult.NotFound => St ch.value(NotFoundResultState)
            case QuotedT etResult.F ltered(state) =>
              val resultState = fa lureResultState(state)

              (t etData.t et.quotedT et, resultState) match {
                //W n QT ex sts => contr bute VF f ltered reason to result state
                case (So (qt), notFoundResultState @ T etF eldsResultState.NotFound(_)) =>
                  deletedT etV s b l yRepo(
                    DeletedT etV s b l yRepos ory.V s b l yRequest(
                      state,
                      qt.t et d,
                      opt ons.safetyLevel,
                      opt ons.forUser d,
                       s nnerQuotedT et = true
                    )
                  ).map(w hV s b l yF lteredReason(notFoundResultState, _))
                //W n QT  s absent => result state w hout f ltered reason
                case _ => St ch.value(resultState)
              }
            case QuotedT etResult.Found(res) =>
              St ch
                .value(toT etF eldsResultState(res, opt ons))
                .flatMap { resultState =>
                  hydrateCreat veConta nerBackedT et(
                    creat vesConta ner d = res.value.t et.underly ngCreat vesConta ner d,
                    or g nalGetT etF eldsResult = GetT etF eldsResult(
                      t et d = res.value.t et. d,
                      t etResult = resultState,
                    ),
                    getT etF eldsRequestOpt ons = opt ons,
                    creat vesConta nerRepo = creat vesConta nerRepo,
                    res.value.t et. d,
                    stats,
                    shouldMater al zeConta ners
                  )
                }
                .map(_.t etResult)
          }
      //Quoted t et result not requested
      case _ => None
    }

    quotedResultStateSt ch
      .map(qtSt ch => qtSt ch.map(So (_)))
      .getOrElse(St ch.None)
      .map(qtResult =>
        GetT etF eldsResult(
          t et d = t etResult.value.t et. d,
          t etResult = pr maryResultState,
          quotedT etResult = qtResult
        ))
  }

  /**
   * @return a copy of resultState w h f ltered reason w n @param f lteredReasonOpt  s present
   */
  pr vate def w hV s b l yF lteredReason(
    resultState: T etF eldsResultState.NotFound,
    f lteredReasonOpt: Opt on[F lteredReason]
  ): T etF eldsResultState.NotFound = {
    f lteredReasonOpt match {
      case So (fs) =>
        resultState.copy(
          notFound = resultState.notFound.copy(
            f lteredReason = So (fs)
          ))
      case _ => resultState
    }
  }

  pr vate def toT etF eldsResultState(
    t etResult: T etResult,
    opt ons: GetT etF eldsOpt ons
  ): T etF eldsResultState = {
    val t etData = t etResult.value
    val suppressReason = t etData.suppress.map(_.f lteredReason)
    val t etFa ledF elds = t etResult.state.fa ledF elds
    val s ceT etFa ledF elds =
      t etData.s ceT etResult.map(_.state.fa ledF elds).getOrElse(Set())
    val s ceT etOpt = t etData.s ceT etResult.map(_.value.t et)
    val s ceT etSuppressReason =
      t etData.s ceT etResult.flatMap(_.value.suppress.map(_.f lteredReason))
    val  sT etPart al = t etFa ledF elds.nonEmpty || s ceT etFa ledF elds.nonEmpty

    val t etFoundResult = t etData.s ceT etResult match {
      case None =>
        //  f `s ceT etResult`  s empty, t   sn't a ret et
        T etF eldsResultFound(
          t et = t etData.t et,
          suppressReason = suppressReason
        )
      case So (r) =>
        //  f t  s ce t et result state  s Found,  rge that  nto t  pr mary result
        T etF eldsResultFound(
          t et = t etData.t et,
          ret etedT et = s ceT etOpt.f lter(_ => opt ons. ncludeRet etedT et),
          suppressReason = suppressReason.orElse(s ceT etSuppressReason)
        )
    }

     f ( sT etPart al) {
      T etF eldsResultState.Fa led(
        T etF eldsResultFa led(
          overCapac y = false,
           ssage = So (
            "Fa led to load: " + (t etFa ledF elds ++ s ceT etFa ledF elds).mkStr ng(", ")),
          part al = So (
            T etF eldsPart al(
              found = t etFoundResult,
              m ss ngF elds = t etFa ledF elds,
              s ceT etM ss ngF elds = s ceT etFa ledF elds
            )
          )
        )
      )
    } else {
      T etF eldsResultState.Found(
        t etFoundResult
      )
    }
  }

  /**
   *  f t et data  s backed by creat ves conta ner,  'll be hydrated from creat ves
   * conta ner serv ce.
   */
  pr vate def hydrateCreat veConta nerBackedT et(
    creat vesConta ner d: Opt on[Long],
    or g nalGetT etF eldsResult: GetT etF eldsResult,
    getT etF eldsRequestOpt ons: GetT etF eldsOpt ons,
    creat vesConta nerRepo: Creat vesConta nerMater al zat onRepos ory.GetT etF eldsType,
    t et d: Long,
    stats: StatsRece ver,
    shouldMater al zeConta ners: Gate[Un ]
  ): St ch[GetT etF eldsResult] = {
    // creat ves conta ner backed t et stats
    val ccT etMater al zed = stats.scope("creat ves_conta ner", "get_t et_f elds")
    val ccT etMater al zeRequests = ccT etMater al zed.counter("requests")
    val ccT etMater al zeSuccess = ccT etMater al zed.counter("success")
    val ccT etMater al zeFa led = ccT etMater al zed.counter("fa led")
    val ccT etMater al zeF ltered = ccT etMater al zed.scope("f ltered")

    (
      creat vesConta ner d,
      or g nalGetT etF eldsResult.t etResult,
      getT etF eldsRequestOpt ons.d sableT etMater al zat on,
      shouldMater al zeConta ners()
    ) match {
      // 1. creat ves conta ner backed t et  s determ ned by `underly ngCreat vesConta ner d` f eld presence.
      // 2.  f t  frontend t et  s suppressed by any reason, respect that and not do t  hydrat on.
      // (t  log c can be rev s ed and  mproved furt r)
      case (None, _, _, _) =>
        St ch.value(or g nalGetT etF eldsResult)
      case (So (_), _, _, false) =>
        ccT etMater al zeF ltered.counter("dec der_suppressed"). ncr()
        St ch.value {
          GetT etF eldsResult(
            t et d = t et d,
            t etResult = T etF eldsResultState.NotFound(T etF eldsResultNotFound())
          )
        }
      case (So (conta ner d), T etF eldsResultState.Found(_), false, _) =>
        ccT etMater al zeRequests. ncr()
        val mater al zat onRequest =
          Mater al zeAsT etF eldsRequest(conta ner d, t et d, So (or g nalGetT etF eldsResult))
        creat vesConta nerRepo(
          mater al zat onRequest,
          getT etF eldsRequestOpt ons
        ).onSuccess(_ => ccT etMater al zeSuccess. ncr())
          .onFa lure(_ => ccT etMater al zeFa led. ncr())
          .handle {
            case ex =>
              GetT etF eldsResult(
                t et d = t et d,
                t etResult = fa lureResultState(ex)
              )
          }
      case (So (_), _, true, _) =>
        ccT etMater al zeF ltered.counter("suppressed"). ncr()
        St ch.value(
          GetT etF eldsResult(
            t et d = t et d,
            t etResult = T etF eldsResultState.NotFound(T etF eldsResultNotFound())
          )
        )
      case (So (_), state, _, _) =>
        ccT etMater al zeF ltered.counter(state.getClass.getNa ). ncr()
        St ch.value(or g nalGetT etF eldsResult)
    }
  }
}
