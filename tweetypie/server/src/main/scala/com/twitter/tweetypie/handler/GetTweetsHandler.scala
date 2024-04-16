package com.tw ter.t etyp e
package handler

 mport com.tw ter.conta ner.thr ftscala.Mater al zeAsT etRequest
 mport com.tw ter.context.Test ngS gnalsContext
 mport com.tw ter.servo.except on.thr ftscala.Cl entError
 mport com.tw ter.servo.except on.thr ftscala.Cl entErrorCause
 mport com.tw ter.servo.ut l.FutureArrow
 mport com.tw ter.spam.rtf.thr ftscala.F lteredReason
 mport com.tw ter.spam.rtf.thr ftscala.SafetyLevel
 mport com.tw ter.st ch.NotFound
 mport com.tw ter.st ch.St ch
 mport com.tw ter.t etyp e.add  onalf elds.Add  onalF elds
 mport com.tw ter.t etyp e.core._
 mport com.tw ter.t etyp e.repos ory._
 mport com.tw ter.t etyp e.thr ftscala._

/**
 * Handler for t  `getT ets` endpo nt.
 */
object GetT etsHandler {
  type Type = FutureArrow[GetT etsRequest, Seq[GetT etResult]]

  /**
   * A `T etQuery. nclude`  nstance w h opt ons set as t  default base opt ons
   * for t  `getT ets` endpo nt.
   */
  val Base nclude: T etQuery. nclude =
    T etQuery. nclude(
      t etF elds = Set(
        T et.CoreDataF eld. d,
        T et.UrlsF eld. d,
        T et. nt onsF eld. d,
        T et. d aF eld. d,
        T et.HashtagsF eld. d,
        T et.CashtagsF eld. d,
        T et.TakedownCountryCodesF eld. d,
        T et.TakedownReasonsF eld. d,
        T et.Dev ceS ceF eld. d,
        T et.LanguageF eld. d,
        T et.Contr butorF eld. d,
        T et.QuotedT etF eld. d,
        T et.Underly ngCreat vesConta ner dF eld. d,
      ),
      pasted d a = true
    )

  def apply(
    t etRepo: T etResultRepos ory.Type,
    creat vesConta nerRepo: Creat vesConta nerMater al zat onRepos ory.GetT etType,
    deletedT etV s b l yRepo: DeletedT etV s b l yRepos ory.Type,
    stats: StatsRece ver,
    shouldMater al zeConta ners: Gate[Un ]
  ): Type = {
    FutureArrow[GetT etsRequest, Seq[GetT etResult]] { request =>
      val requestOpt ons = request.opt ons.getOrElse(GetT etOpt ons())

      val  nval dAdd  onalF elds =
        requestOpt ons.add  onalF eld ds.f lter(!Add  onalF elds. sAdd  onalF eld d(_))

       f ( nval dAdd  onalF elds.nonEmpty) {
        Future.except on(
          Cl entError(
            Cl entErrorCause.BadRequest,
            "Requested add  onal f elds conta n  nval d f eld  d " +
              s"${ nval dAdd  onalF elds.mkStr ng(", ")}. Add  onal f elds  ds must be greater than 100."
          )
        )
      } else {
        val opts = toT etQueryOpt ons(requestOpt ons)
        val  asureRacyReads: T et d => Un  = trackLossyReadsAfterWr e(
          stats.stat("racy_reads", "get_t ets"),
          Durat on.fromSeconds(3)
        )

        St ch.run(
          St ch.traverse(request.t et ds) {  d =>
            t etRepo( d, opts).l ftToTry
              .flatMap {
                case Throw(NotFound) =>
                   asureRacyReads( d)

                  St ch.value(GetT etResult( d, StatusState.NotFound))
                case Throw(ex) =>
                  fa lureResult(deletedT etV s b l yRepo,  d, requestOpt ons, ex)
                case Return(r) =>
                  toGetT etResult(
                    deletedT etV s b l yRepo,
                    creat vesConta nerRepo,
                    requestOpt ons,
                    t etResult = r,
                     ncludeS ceT et = requestOpt ons. ncludeS ceT et,
                     ncludeQuotedT et = requestOpt ons. ncludeQuotedT et,
                    stats,
                    shouldMater al zeConta ners
                  )
              }.flatMap { getT etResult =>
                // c ck  f t et data  s backed by creat ves conta ner and needs to be hydrated from creat ves
                // conta ner serv ce.
                hydrateCreat veConta nerBackedT et(
                  getT etResult,
                  requestOpt ons,
                  creat vesConta nerRepo,
                  stats,
                  shouldMater al zeConta ners
                )
              }
          }
        )
      }
    }
  }

  def toT etQueryOpt ons(opt ons: GetT etOpt ons): T etQuery.Opt ons = {
    val shouldSk pCac  = Test ngS gnalsContext().flatMap(_.s mulateBackPressure).nonEmpty
    val cac Control =
       f (shouldSk pCac ) Cac Control.NoCac 
      else  f (opt ons.doNotCac ) Cac Control.ReadOnlyCac 
      else Cac Control.ReadWr eCac 

    val countsF elds = toCountsF elds(opt ons)
    val  d aF elds = to d aF elds(opt ons)

    T etQuery.Opt ons(
       nclude = Base nclude.also(
        t etF elds = toT etF elds(opt ons, countsF elds),
        countsF elds = countsF elds,
         d aF elds =  d aF elds,
        quotedT et = So (opt ons. ncludeQuotedT et)
      ),
      cac Control = cac Control,
      cardsPlatformKey = opt ons.cardsPlatformKey,
      excludeReported = opt ons.excludeReported,
      enforceV s b l yF lter ng = !opt ons.bypassV s b l yF lter ng,
      safetyLevel = opt ons.safetyLevel.getOrElse(SafetyLevel.F lterDefault),
      forUser d = opt ons.forUser d,
      languageTag = opt ons.languageTag,
      extens onsArgs = opt ons.extens onsArgs,
      forExternalConsumpt on = true,
      s mpleQuotedT et = opt ons.s mpleQuotedT et
    )
  }

  pr vate def toT etF elds(opts: GetT etOpt ons, countsF elds: Set[F eld d]): Set[F eld d] = {
    val bldr = Set.newBu lder[F eld d]

    bldr ++= opts.add  onalF eld ds

     f (opts. ncludePlaces) bldr += T et.PlaceF eld. d
     f (opts.forUser d.nonEmpty) {
       f (opts. ncludePerspect vals) bldr += T et.Perspect veF eld. d
       f (opts. ncludeConversat onMuted) bldr += T et.Conversat onMutedF eld. d
    }
     f (opts. ncludeCards && opts.cardsPlatformKey. sEmpty) bldr += T et.CardsF eld. d
     f (opts. ncludeCards && opts.cardsPlatformKey.nonEmpty) bldr += T et.Card2F eld. d
     f (opts. ncludeProf leGeoEnr ch nt) bldr += T et.Prof leGeoEnr ch ntF eld. d

     f (countsF elds.nonEmpty) bldr += T et.CountsF eld. d

     f (opts. ncludeCardUr ) bldr += T et.CardReferenceF eld. d

    bldr.result()
  }

  pr vate def toCountsF elds(opts: GetT etOpt ons): Set[F eld d] = {
    val bldr = Set.newBu lder[F eld d]

     f (opts. ncludeRet etCount) bldr += StatusCounts.Ret etCountF eld. d
     f (opts. ncludeReplyCount) bldr += StatusCounts.ReplyCountF eld. d
     f (opts. ncludeFavor eCount) bldr += StatusCounts.Favor eCountF eld. d
     f (opts. ncludeQuoteCount) bldr += StatusCounts.QuoteCountF eld. d

    bldr.result()
  }

  pr vate def to d aF elds(opts: GetT etOpt ons): Set[F eld d] = {
     f (opts. nclude d aAdd  onal tadata)
      Set( d aEnt y.Add  onal tadataF eld. d)
    else
      Set.empty
  }

  /**
   * Converts a `T etResult`  nto a `GetT etResult`.
   */
  def toGetT etResult(
    deletedT etV s b l yRepo: DeletedT etV s b l yRepos ory.Type,
    creat vesConta nerRepo: Creat vesConta nerMater al zat onRepos ory.GetT etType,
    opt ons: GetT etOpt ons,
    t etResult: T etResult,
     ncludeS ceT et: Boolean,
     ncludeQuotedT et: Boolean,
    stats: StatsRece ver,
    shouldMater al zeConta ners: Gate[Un ]
  ): St ch[GetT etResult] = {
    val t etData = t etResult.value

    // only  nclude m ss ng f elds  f non empty
    def asM ss ngF elds(set: Set[F eldByPath]): Opt on[Set[F eldByPath]] =
       f (set. sEmpty) None else So (set)

    val m ss ngF elds = asM ss ngF elds(t etResult.state.fa ledF elds)

    val s ceT etResult =
      t etData.s ceT etResult
        .f lter(_ =>  ncludeS ceT et)

    val s ceT etData = t etData.s ceT etResult
      .getOrElse(t etResult)
      .value
    val quotedT etResult: Opt on[QuotedT etResult] = s ceT etData.quotedT etResult
      .f lter(_ =>  ncludeQuotedT et)

    val qtF lteredReasonSt ch =
      ((s ceT etData.t et.quotedT et, quotedT etResult) match {
        case (So (quotedT et), So (QuotedT etResult.F ltered(f lteredState))) =>
          deletedT etV s b l yRepo(
            DeletedT etV s b l yRepos ory.V s b l yRequest(
              f lteredState,
              quotedT et.t et d,
              opt ons.safetyLevel,
              opt ons.forUser d,
               s nnerQuotedT et = true
            )
          )
        case _ => St ch.None
      })
      //Use quotedT etResult f ltered reason w n VF f ltered reason  s not present
        .map(fsOpt => fsOpt.orElse(quotedT etResult.flatMap(_.f lteredReason)))

    val suppress = t etData.suppress.orElse(t etData.s ceT etResult.flatMap(_.value.suppress))

    val quotedT etSt ch: St ch[Opt on[T et]] =
      quotedT etResult match {
        // c ck  f quote t et  s backed by creat ves conta ner and needs to be hydrated from creat ves
        // conta ner serv ce. deta l see go/creat ves-conta ners-tdd
        case So (QuotedT etResult.Found(t etResult)) =>
          hydrateCreat veConta nerBackedT et(
            or g nalGetT etResult = GetT etResult(
              t et d = t etResult.value.t et. d,
              t etState = StatusState.Found,
              t et = So (t etResult.value.t et)
            ),
            getT etRequestOpt ons = opt ons,
            creat vesConta nerRepo = creat vesConta nerRepo,
            stats = stats,
            shouldMater al zeConta ners
          ).map(_.t et)
        case _ =>
          St ch.value(
            quotedT etResult
              .flatMap(_.toOpt on)
              .map(_.value.t et)
          )
      }

    St ch.jo n(qtF lteredReasonSt ch, quotedT etSt ch).map {
      case (qtF lteredReason, quotedT et) =>
        GetT etResult(
          t et d = t etData.t et. d,
          t etState =
             f (suppress.nonEmpty) StatusState.Suppress
            else  f (m ss ngF elds.nonEmpty) StatusState.Part al
            else StatusState.Found,
          t et = So (t etData.t et),
          m ss ngF elds = m ss ngF elds,
          f lteredReason = suppress.map(_.f lteredReason),
          s ceT et = s ceT etResult.map(_.value.t et),
          s ceT etM ss ngF elds = s ceT etResult
            .map(_.state.fa ledF elds)
            .flatMap(asM ss ngF elds),
          quotedT et = quotedT et,
          quotedT etM ss ngF elds = quotedT etResult
            .flatMap(_.toOpt on)
            .map(_.state.fa ledF elds)
            .flatMap(asM ss ngF elds),
          quotedT etF lteredReason = qtF lteredReason
        )
    }
  }

  pr vate[t ] val AuthorAccount s nact ve = F lteredReason.AuthorAccount s nact ve(true)

  def fa lureResult(
    deletedT etV s b l yRepo: DeletedT etV s b l yRepos ory.Type,
    t et d: T et d,
    opt ons: GetT etOpt ons,
    ex: Throwable
  ): St ch[GetT etResult] = {
    def deletedState(deleted: Boolean, statusState: StatusState) =
       f (deleted && opt ons.enableDeletedState) {
        statusState
      } else {
        StatusState.NotFound
      }

    ex match {
      case F lteredState.Unava lable.Author.Deact vated =>
        St ch.value(GetT etResult(t et d, StatusState.Deact vatedUser))
      case F lteredState.Unava lable.Author.NotFound =>
        St ch.value(GetT etResult(t et d, StatusState.NotFound))
      case F lteredState.Unava lable.Author.Offboarded =>
        St ch.value(
          GetT etResult(t et d, StatusState.Drop, f lteredReason = So (AuthorAccount s nact ve)))
      case F lteredState.Unava lable.Author.Suspended =>
        St ch.value(GetT etResult(t et d, StatusState.SuspendedUser))
      case F lteredState.Unava lable.Author.Protected =>
        St ch.value(GetT etResult(t et d, StatusState.ProtectedUser))
      case F lteredState.Unava lable.Author.Unsafe =>
        St ch.value(GetT etResult(t et d, StatusState.Drop))
      //Handle delete state w h opt onal F lteredReason
      case F lteredState.Unava lable.T etDeleted =>
        deletedT etV s b l yRepo(
          DeletedT etV s b l yRepos ory.V s b l yRequest(
            ex,
            t et d,
            opt ons.safetyLevel,
            opt ons.forUser d,
             s nnerQuotedT et = false
          )
        ).map(f lteredReasonOpt => {
          val deleteState = deletedState(deleted = true, StatusState.Deleted)
          GetT etResult(t et d, deleteState, f lteredReason = f lteredReasonOpt)
        })

      case F lteredState.Unava lable.BounceDeleted =>
        deletedT etV s b l yRepo(
          DeletedT etV s b l yRepos ory.V s b l yRequest(
            ex,
            t et d,
            opt ons.safetyLevel,
            opt ons.forUser d,
             s nnerQuotedT et = false
          )
        ).map(f lteredReasonOpt => {
          val deleteState = deletedState(deleted = true, StatusState.BounceDeleted)
          GetT etResult(t et d, deleteState, f lteredReason = f lteredReasonOpt)
        })

      case F lteredState.Unava lable.S ceT etNotFound(d) =>
        deletedT etV s b l yRepo(
          DeletedT etV s b l yRepos ory.V s b l yRequest(
            ex,
            t et d,
            opt ons.safetyLevel,
            opt ons.forUser d,
             s nnerQuotedT et = false
          )
        ).map(f lteredReasonOpt => {
          val deleteState = deletedState(d, StatusState.Deleted)
          GetT etResult(t et d, deleteState, f lteredReason = f lteredReasonOpt)
        })
      case F lteredState.Unava lable.Reported =>
        St ch.value(GetT etResult(t et d, StatusState.ReportedT et))
      case fs: F lteredState.HasF lteredReason =>
        St ch.value(
          GetT etResult(t et d, StatusState.Drop, f lteredReason = So (fs.f lteredReason)))
      case OverCapac y(_) => St ch.value(GetT etResult(t et d, StatusState.OverCapac y))
      case _ => St ch.value(GetT etResult(t et d, StatusState.Fa led))
    }
  }

  pr vate def hydrateCreat veConta nerBackedT et(
    or g nalGetT etResult: GetT etResult,
    getT etRequestOpt ons: GetT etOpt ons,
    creat vesConta nerRepo: Creat vesConta nerMater al zat onRepos ory.GetT etType,
    stats: StatsRece ver,
    shouldMater al zeConta ners: Gate[Un ]
  ): St ch[GetT etResult] = {
    // creat ves conta ner backed t et stats
    val ccT etMater al zed = stats.scope("creat ves_conta ner", "get_t ets")
    val ccT etMater al zeF ltered = ccT etMater al zed.scope("f ltered")
    val ccT etMater al zeSuccess = ccT etMater al zed.counter("success")
    val ccT etMater al zeFa led = ccT etMater al zed.counter("fa led")
    val ccT etMater al zeRequests = ccT etMater al zed.counter("requests")

    val t et d = or g nalGetT etResult.t et d
    val t etState = or g nalGetT etResult.t etState
    val underly ngCreat vesConta ner d =
      or g nalGetT etResult.t et.flatMap(_.underly ngCreat vesConta ner d)
    (
      t etState,
      underly ngCreat vesConta ner d,
      getT etRequestOpt ons.d sableT etMater al zat on,
      shouldMater al zeConta ners()
    ) match {
      // 1. creat ves conta ner backed t et  s determ ned by `underly ngCreat vesConta ner d` f eld presence.
      // 2.  f t  frontend t et  s suppressed by any reason, respect that and not do t  hydrat on.
      // (t  log c can be rev s ed and  mproved furt r)
      case (_, None, _, _) =>
        St ch.value(or g nalGetT etResult)
      case (_, So (_), _, false) =>
        ccT etMater al zeF ltered.counter("dec der_suppressed"). ncr()
        St ch.value(GetT etResult(t et d, StatusState.NotFound))
      case (StatusState.Found, So (conta ner d), false, _) =>
        ccT etMater al zeRequests. ncr()
        val mater al zat onRequest =
          Mater al zeAsT etRequest(conta ner d, t et d, So (or g nalGetT etResult))
        creat vesConta nerRepo(
          mater al zat onRequest,
          So (getT etRequestOpt ons)
        ).onSuccess(_ => ccT etMater al zeSuccess. ncr())
          .onFa lure(_ => ccT etMater al zeFa led. ncr())
          .handle {
            case _ => GetT etResult(t et d, StatusState.Fa led)
          }
      case (_, So (_), true, _) =>
        ccT etMater al zeF ltered.counter("suppressed"). ncr()
        St ch.value(GetT etResult(t et d, StatusState.NotFound))
      case (state, So (_), _, _) =>
        ccT etMater al zeF ltered.counter(state.na ). ncr()
        St ch.value(or g nalGetT etResult)
    }
  }
}
