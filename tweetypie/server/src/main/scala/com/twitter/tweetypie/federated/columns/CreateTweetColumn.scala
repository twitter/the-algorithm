package com.tw ter.t etyp e
package federated.columns

 mport com.tw ter.accounts.ut l.Safety tadataUt ls
 mport com.tw ter.ads.callback.thr ftscala.Engage ntRequest
 mport com.tw ter.bouncer.thr ftscala.{Bounce => BouncerBounce}
 mport com.tw ter.esc rb rd.thr ftscala.T etEnt yAnnotat on
 mport com.tw ter.geo.model.Lat udeLong ude
 mport com.tw ter.st ch.St ch
 mport com.tw ter.strato.catalog.Op tadata
 mport com.tw ter.strato.conf g.AllOf
 mport com.tw ter.strato.conf g.BouncerAccess
 mport com.tw ter.strato.conf g.Contact nfo
 mport com.tw ter.strato.conf g.Pol cy
 mport com.tw ter.strato.data.Conv
 mport com.tw ter.strato.data.Descr pt on.Pla nText
 mport com.tw ter.strato.data.L fecycle.Product on
 mport com.tw ter.strato.fed.StratoFed
 mport com.tw ter.strato.opcontext.OpContext
 mport com.tw ter.strato.response.Err
 mport com.tw ter.strato.thr ft.ScroogeConv
 mport com.tw ter.t etyp e.dec der.overr des.T etyP eDec derOverr des
 mport com.tw ter.t etyp e.federated.columns.Ap Errors._
 mport com.tw ter.t etyp e.federated.columns.CreateT etColumn.toCreateT etErr
 mport com.tw ter.t etyp e.federated.context.GetRequestContext
 mport com.tw ter.t etyp e.federated.prefetc ddata.Prefetc dDataRequest
 mport com.tw ter.t etyp e.federated.prefetc ddata.Prefetc dDataResponse
 mport com.tw ter.t etyp e.federated.promotedcontent.T etPromotedContentLogger
 mport com.tw ter.t etyp e.federated.promotedcontent.T etPromotedContentLogger._
 mport com.tw ter.t etyp e.repos ory.Un nt on nfoRepos ory
 mport com.tw ter.t etyp e.repos ory.V beRepos ory
 mport com.tw ter.t etyp e.thr ftscala.Trans entCreateContext
 mport com.tw ter.t etyp e.thr ftscala.T etCreateContextKey
 mport com.tw ter.t etyp e.thr ftscala.T etCreateState._
 mport com.tw ter.t etyp e.thr ftscala.{graphql => gql}
 mport com.tw ter.t etyp e.ut l.Commun yAnnotat on
 mport com.tw ter.t etyp e.ut l.Conversat onControls
 mport com.tw ter.t etyp e.ut l.Trans entContextUt l
 mport com.tw ter.t etyp e.{thr ftscala => thr ft}
 mport com.tw ter.ut l.Throwables
 mport com.tw ter. averb rd.common.{GetRequestContext => WGetRequestContext}

class CreateT etColumn(
  postT et: thr ft.PostT etRequest => Future[thr ft.PostT etResult],
  getRequestContext: GetRequestContext,
  prefetc dDataRepos ory: Prefetc dDataRequest => St ch[Prefetc dDataResponse],
  un nt on nfoRepos ory: Un nt on nfoRepos ory.Type,
  v beRepos ory: V beRepos ory.Type,
  logT etPromotedContent: T etPromotedContentLogger.Type,
  statsRece ver: StatsRece ver,
  enableCommun yT etCreatesDec der: Gate[Un ],
) extends StratoFed.Column(CreateT etColumn.Path)
    w h StratoFed.Execute.St chW hContext
    w h StratoFed.HandleDarkRequests {

  overr de val pol cy: Pol cy = AllOf(
    Seq(AccessPol cy.T etMutat onCommonAccessPol c es, BouncerAccess()))

  // T  underly ng call to thr ftT etServ ce.postRet et  s not  dempotent
  overr de val  s dempotent: Boolean = false

  overr de type Arg = gql.CreateT etRequest
  overr de type Result = gql.CreateT etResponseW hSubqueryPrefetch ems

  overr de val argConv: Conv[Arg] = ScroogeConv.fromStruct
  overr de val resultConv: Conv[Result] = ScroogeConv.fromStruct

  overr de val contact nfo: Contact nfo = T etyp eContact nfo
  overr de val  tadata: Op tadata =
    Op tadata(
      So (Product on),
      So (
        Pla nText(
          """
    Creates a t et us ng t  call ng aut nt cated Tw ter user as author. 
    NOTE, not all T et space f elds are GraphQL queryable  n t  CreateT et mutat on response. 
    See http://go/m ss ng-create-t et-f elds.
    """))
    )

  pr vate val get averb rdCtx = new WGetRequestContext()

  overr de def execute(request: Arg, opContext: OpContext): St ch[Result] = {

    val ctx = getRequestContext(opContext)

    // F rst, do any request para ter val dat on that can result  n an error
    // pr or to call ng  nto thr ftT etServ ce.postT et.
    val safetyLevel = ctx.safetyLevel.getOrElse(throw SafetyLevelM ss ngErr)

    val track ng d = request.engage ntRequest match {
      case So (engage ntRequest: Engage ntRequest)  f ctx.hasPr v legePromotedT ets nT  l ne =>
        Track ng d.parse(engage ntRequest. mpress on d, statsRece ver)
      case So (e: Engage ntRequest) =>
        throw Cl entNotPr v legedErr
      case None =>
        None
    }

    val dev ceS ce = ctx.dev ceS ce.getOrElse(throw Gener cAccessDen edErr)

     f (request.nullcast && !ctx.hasPr v legeNullcast ngAccess) {
      throw Gener cAccessDen edErr
    }

    val safety tadata = Safety tadataUt ls.makeSafety taData(
      sess onHash = ctx.sess onHash,
      knownDev ceToken = ctx.knownDev ceToken,
      contr butor d = ctx.contr butor d
    )

    val cardReference: Opt on[thr ft.CardReference] =
      request.cardUr .f lter(_.nonEmpty).map(thr ft.CardReference(_))

    val esc rb rdEnt yAnnotat ons: Opt on[thr ft.Esc rb rdEnt yAnnotat ons] =
      request.semant cAnnotat on ds
        .f lter(_.nonEmpty)
        .map((seq: Seq[gql.T etAnnotat on]) => seq.map(parseT etEnt yAnnotat on))
        .map(thr ft.Esc rb rdEnt yAnnotat ons(_))

    val  d aEnt  es = request. d a.map(_. d aEnt  es)
    val  d aUpload ds =  d aEnt  es.map(_.map(_. d a d)).f lter(_.nonEmpty)

    val  d aTags: Opt on[thr ft.T et d aTags] = {
      val  d aTagsAuthor zed = !ctx. sContr butorRequest

      val tagMap: Map[ d a d, Seq[thr ft. d aTag]] =
         d aEnt  es
          .getOrElse(N l)
          .f lter(_ =>  d aTagsAuthor zed)
          .f lter(_.taggedUsers.nonEmpty)
          .map( d aEnt y =>
             d aEnt y. d a d ->
               d aEnt y.taggedUsers
                .map(user_ d => thr ft. d aTag(thr ft. d aTagType.User, So (user_ d))))
          .toMap

      Opt on(tagMap)
        .f lter(_.nonEmpty)
        .map(thr ft.T et d aTags(_))
    }

    // Can not have both conversat on controls and commun  es def ned for a t et
    // as t y have confl ct ng perm ss ons on who can reply to t  t et.
    val commun  es = parseCommun y ds(esc rb rdEnt yAnnotat ons)
     f (request.conversat onControl. sDef ned && commun  es.nonEmpty) {
      throw CannotConvoControlAndCommun  esErr
    }

    // Currently   do not support post ng to mult ple commun  es.
     f (commun  es.length > 1) {
      throw TooManyCommun  esErr
    }

    // K ll sw ch for commun y t ets  n case   need to d sable t m for app secur y.
     f (commun  es.nonEmpty && !enableCommun yT etCreatesDec der()) {
      throw Commun yUserNotAuthor zedErr
    }

    // add  onalF elds  s used to marshal mult ple  nput params and
    // should only be def ned  f one or more of those params are def ned.
    val add  onalF elds: Opt on[T et] =
      cardReference
        .orElse(esc rb rdEnt yAnnotat ons)
        .orElse( d aTags)
        .map(_ =>
          thr ft.T et(
            0L,
            cardReference = cardReference,
            esc rb rdEnt yAnnotat ons = esc rb rdEnt yAnnotat ons,
             d aTags =  d aTags
          ))

    val trans entContext: Opt on[Trans entCreateContext] =
      parseTrans entContext(
        request.batchCompose,
        request.per scope,
        ctx.tw terUser d,
      )

    // PostT etRequest.add  onalContext  s marked as deprecated  n favor of .trans entContext,
    // but t  REST AP  st ll supports   and    s st ll passed along through T etyp e, and
    // FanoutServ ce and Not f cat ons st ll depend on  .
    val add  onalContext: Opt on[Map[T etCreateContextKey, Str ng]] =
      trans entContext.map(Trans entContextUt l.toAdd  onalContext)

    val thr ftPostT etRequest = thr ft.PostT etRequest(
      user d = ctx.tw terUser d,
      text = request.t etText,
      createdV a = dev ceS ce,
       nReplyToT et d = request.reply.map(_. nReplyToT et d),
      geo = request.geo.flatMap(parseT etCreateGeo),
      autoPopulateReply tadata = request.reply. sDef ned,
      excludeReplyUser ds = request.reply.map(_.excludeReplyUser ds).f lter(_.nonEmpty),
      nullcast = request.nullcast,
      // Send a dark request to T etyp e  f t  dark_request d rect ve  s set or
      //  f t  T et  s undo-able.
      dark = ctx. sDarkRequest || request.undoOpt ons.ex sts(_. sUndo),
      hydrat onOpt ons = So (Hydrat onOpt ons.wr ePathHydrat onOpt ons(ctx.cardsPlatformKey)),
      remoteHost = ctx.remoteHost,
      safety taData = So (safety tadata),
      attach ntUrl = request.attach ntUrl,
       d aUpload ds =  d aUpload ds,
       d a tadata = None,
      trans entContext = trans entContext,
      add  onalContext = add  onalContext,
      conversat onControl = request.conversat onControl.map(parseT etCreateConversat onControl),
      exclus veT etControlOpt ons = request.exclus veT etControlOpt ons.map { _ =>
        thr ft.Exclus veT etControlOpt ons()
      },
      trustedFr endsControlOpt ons =
        request.trustedFr endsControlOpt ons.map(parseTrustedFr endsControlOpt ons),
      ed Opt ons = request.ed Opt ons.flatMap(_.prev ousT et d.map(thr ft.Ed Opt ons(_))),
      collabControlOpt ons = request.collabControlOpt ons.map(parseCollabControlOpt ons),
      add  onalF elds = add  onalF elds,
      track ng d = track ng d,
      noteT etOpt ons = request.noteT etOpt ons.map(opt ons =>
        thr ft.NoteT etOpt ons(
          opt ons.noteT et d,
          opt ons. nt onedScreenNa s,
          opt ons. nt onedUser ds,
          opt ons. sExpandable))
    )

    val st chPostT et =
      St ch.callFuture {
        T etyP eDec derOverr des.Conversat onControlUseFeatureSw chResults.On {
          postT et(thr ftPostT etRequest)
        }
      }

    for {
      engage nt <- request.engage ntRequest
       f !request.reply.ex sts(_. nReplyToT et d == 0) // no op per go/rb/845242
      engage ntType =  f (request.reply. sDef ned) ReplyEngage nt else T etEngage nt
    } logT etPromotedContent(engage nt, engage ntType, ctx. sDarkRequest)

    st chPostT et.flatMap { result: thr ft.PostT etResult =>
      result.state match {

        case thr ft.T etCreateState.Ok =>
          val un nt onSuccessCounter = statsRece ver.counter("un nt on_ nfo_success")
          val un nt onFa luresCounter = statsRece ver.counter("un nt on_ nfo_fa lures")
          val un nt onFa luresScope = statsRece ver.scope("un nt on_ nfo_fa lures")

          val un nt on nfoSt ch = result.t et match {
            case So (t et) =>
              un nt on nfoRepos ory(t et)
                .onFa lure { t =>
                  un nt onFa luresCounter. ncr()
                  un nt onFa luresScope.counter(Throwables.mkStr ng(t): _*). ncr()
                }
                .onSuccess { _ =>
                  un nt onSuccessCounter. ncr()
                }
                .rescue {
                  case _ =>
                    St ch.None
                }
            case _ =>
              St ch.None
          }

          val v beSuccessCounter = statsRece ver.counter("v be_success")
          val v beFa luresCounter = statsRece ver.counter("v be_fa lures")
          val v beFa luresScope = statsRece ver.scope("v be_fa lures")

          val v beSt ch = result.t et match {
            case So (t et) =>
              v beRepos ory(t et)
                .onSuccess { _ =>
                  v beSuccessCounter. ncr()
                }
                .onFa lure { t =>
                  v beFa luresCounter. ncr()
                  v beFa luresScope.counter(Throwables.mkStr ng(t): _*). ncr()
                }
                .rescue {
                  case _ =>
                    St ch.None
                }
            case _ =>
              St ch.None
          }

          St ch
            .jo n(un nt on nfoSt ch, v beSt ch)
            .l ftToOpt on()
            .flatMap { prefetchF elds =>
              val r = Prefetc dDataRequest(
                t et = result.t et.get,
                s ceT et = result.s ceT et,
                quotedT et = result.quotedT et,
                safetyLevel = safetyLevel,
                un nt on nfo = prefetchF elds.flatMap(params => params._1),
                v be = prefetchF elds.flatMap(params => params._2),
                requestContext = get averb rdCtx()
              )

              prefetc dDataRepos ory(r)
                .l ftToOpt on()
                .map((prefetc dData: Opt on[Prefetc dDataResponse]) => {
                  gql.CreateT etResponseW hSubqueryPrefetch ems(
                    data = So (gql.CreateT etResponse(result.t et.map(_. d))),
                    subqueryPrefetch ems = prefetc dData.map(_.value)
                  )
                })
            }

        case errState =>
          throw toCreateT etErr(errState, result.bounce, result.fa lureReason)
      }
    }
  }

  pr vate[t ] def parseT etCreateGeo(gqlGeo: gql.T etGeo): Opt on[thr ft.T etCreateGeo] = {
    val coord nates: Opt on[thr ft.GeoCoord nates] =
      gqlGeo.coord nates.map { coords =>
        Lat udeLong ude.of(coords.lat ude, coords.long ude) match {
          case Return(latlon: Lat udeLong ude) =>
            thr ft.GeoCoord nates(
              lat ude = latlon.lat udeDegrees,
              long ude = latlon.long udeDegrees,
              geoPrec s on = latlon.prec s on,
              d splay = coords.d splayCoord nates
            )
          case Throw(_) =>
            throw  nval dCoord natesErr
        }
      }

    val geoSearchRequest d = gqlGeo.geoSearchRequest d.map {  d =>
       f ( d. sEmpty) {
        throw  nval dGeoSearchRequest dErr
      }
      thr ft.T etGeoSearchRequest D( d)
    }

     f (coord nates. sEmpty && gqlGeo.place d. sEmpty) {
      None
    } else {
      So (
        thr ft.T etCreateGeo(
          coord nates = coord nates,
          place d = gqlGeo.place d,
          geoSearchRequest d = geoSearchRequest d
        ))
    }
  }

  pr vate[t ] def parseT etCreateConversat onControl(
    gqlCC: gql.T etConversat onControl
  ): thr ft.T etCreateConversat onControl =
    gqlCC.mode match {
      case gql.Conversat onControlMode.By nv at on =>
        Conversat onControls.Create.by nv at on()
      case gql.Conversat onControlMode.Commun y =>
        Conversat onControls.Create.commun y()
      case gql.Conversat onControlMode.EnumUnknownConversat onControlMode(_) =>
        throw Conversat onControlNotSupportedErr
    }

  pr vate[t ] def parseT etEnt yAnnotat on(
    gqlT etAnnotat on: gql.T etAnnotat on
  ): T etEnt yAnnotat on =
    T etEnt yAnnotat on(
      gqlT etAnnotat on.group d,
      gqlT etAnnotat on.doma n d,
      gqlT etAnnotat on.ent y d
    )

  pr vate[t ] def parseCommun y ds(
    esc rb rdAnnotat ons: Opt on[thr ft.Esc rb rdEnt yAnnotat ons]
  ): Seq[Long] =
    esc rb rdAnnotat ons
      .map(_.ent yAnnotat ons).getOrElse(N l)
      .flatMap {
        case Commun yAnnotat on( d) => Seq( d)
        case _ => N l
      }

  pr vate[t ] def parseBatchMode(
    gqlBatchComposeMode: gql.BatchComposeMode
  ): thr ft.BatchComposeMode = {

    gqlBatchComposeMode match {
      case gql.BatchComposeMode.BatchF rst =>
        thr ft.BatchComposeMode.BatchF rst
      case gql.BatchComposeMode.BatchSubsequent =>
        thr ft.BatchComposeMode.BatchSubsequent
      case gql.BatchComposeMode.EnumUnknownBatchComposeMode(_) =>
        throw  nval dBatchModePara terErr
    }
  }

  pr vate[t ] def parseTrans entContext(
    gqlBatchComposeMode: Opt on[gql.BatchComposeMode],
    gqlPer scope: Opt on[gql.T etPer scopeContext],
    tw terUser d: User d,
  ): Opt on[Trans entCreateContext] = {
    val batchComposeMode = gqlBatchComposeMode.map(parseBatchMode)

    // Per c.t.fanoutserv ce.model.T et#dev ceFollowType,  sL ve=None and So (false) are
    // equ valent and t  creator d  s d scarded  n both cases.
    val per scope sL ve = gqlPer scope.map(_. sL ve).f lter(_ == true)
    val per scopeCreator d =  f (per scope sL ve. sDef ned) So (tw terUser d) else None

     f (batchComposeMode. sDef ned || per scope sL ve. sDef ned) {
      So (
        thr ft.Trans entCreateContext(
          batchCompose = batchComposeMode,
          per scope sL ve = per scope sL ve,
          per scopeCreator d = per scopeCreator d
        )
      )
    } else {
      None
    }
  }

  pr vate[t ] def parseTrustedFr endsControlOpt ons(
    gqlTrustedFr endsControlOpt ons: gql.TrustedFr endsControlOpt ons
  ): thr ft.TrustedFr endsControlOpt ons = {
    thr ft.TrustedFr endsControlOpt ons(
      trustedFr endsL st d = gqlTrustedFr endsControlOpt ons.trustedFr endsL st d
    )
  }

  pr vate[t ] def parseCollabControlOpt ons(
    gqlCollabControlOpt ons: gql.CollabControlOpt ons
  ): thr ft.CollabControlOpt ons = {
    gqlCollabControlOpt ons.collabControlType match {
      case gql.CollabControlType.Collab nv at on =>
        thr ft.CollabControlOpt ons.Collab nv at on(
          thr ft.Collab nv at onOpt ons(
            collaboratorUser ds = gqlCollabControlOpt ons.collaboratorUser ds
          )
        )
      case gql.CollabControlType.EnumUnknownCollabControlType(_) =>
        throw CollabT et nval dParamsErr
    }
  }
}

object CreateT etColumn {
  val Path = "t etyp e/createT et.T et"

  def toCreateT etErr(
    errState: thr ft.T etCreateState,
    bounce: Opt on[BouncerBounce],
    fa lureReason: Opt on[Str ng]
  ): Err = errState match {
    case TextCannotBeBlank =>
      T etCannotBeBlankErr
    case TextTooLong =>
      T etTextTooLongErr
    case Dupl cate =>
      Dupl cateStatusErr
    case MalwareUrl =>
      MalwareT etErr
    case UserDeact vated | UserSuspended =>
      // should not occur s nce t  cond  on  s caught by access pol cy f lters
      CurrentUserSuspendedErr
    case RateL m Exceeded =>
      RateL m ExceededErr
    case UrlSpam =>
      T etUrlSpamErr
    case Spam | UserReadonly =>
      T etSpam rErr
    case SpamCaptcha =>
      CaptchaChallengeErr
    case SafetyRateL m Exceeded =>
      SafetyRateL m ExceededErr
    case Bounce  f bounce. sDef ned =>
      accessDen edByBouncerErr(bounce.get)
    case  nt onL m Exceeded =>
       nt onL m ExceededErr
    case UrlL m Exceeded =>
      UrlL m ExceededErr
    case HashtagL m Exceeded =>
      HashtagL m ExceededErr
    case CashtagL m Exceeded =>
      CashtagL m ExceededErr
    case HashtagLengthL m Exceeded =>
      HashtagLengthL m ExceededErr
    case TooManyAttach ntTypes =>
      TooManyAttach ntTypesErr
    case  nval dUrl =>
       nval dUrlErr
    case D sabledBy p Pol cy =>
      fa lureReason
        .map(t etEngage ntL m edErr)
        .getOrElse(Gener cT etCreateErr)
    case  nval dAdd  onalF eld =>
      fa lureReason
        .map( nval dAdd  onalF eldW hReasonErr)
        .getOrElse( nval dAdd  onalF eldErr)
    //  nval d mage has been deprecated by t etyp e. Use  nval d d a  nstead.
    case  nval d d a |  nval d mage |  d aNotFound =>
       nval d d aErr(fa lureReason)
    case  nReplyToT etNotFound =>
       nReplyToT etNotFoundErr
    case  nval dAttach ntUrl =>
       nval dAttach ntUrlErr
    case Conversat onControlNotAllo d =>
      Conversat onControlNotAuthor zedErr
    case  nval dConversat onControl =>
      Conversat onControl nval dErr
    case ReplyT etNotAllo d =>
      Conversat onControlReplyRestr cted
    case Exclus veT etEngage ntNotAllo d =>
      Exclus veT etEngage ntNotAllo dErr
    case Commun yReplyT etNotAllo d =>
      Commun yReplyT etNotAllo dErr
    case Commun yUserNotAuthor zed =>
      Commun yUserNotAuthor zedErr
    case Commun yNotFound =>
      Commun yNotFoundErr
    case SuperFollows nval dParams =>
      SuperFollow nval dParamsErr
    case SuperFollowsCreateNotAuthor zed =>
      SuperFollowCreateNotAuthor zedErr
    case Commun yProtectedUserCannotT et =>
      Commun yProtectedUserCannotT etErr
    case TrustedFr ends nval dParams =>
      TrustedFr ends nval dParamsErr
    case TrustedFr endsEngage ntNotAllo d =>
      TrustedFr endsEngage ntNotAllo dErr
    case TrustedFr endsCreateNotAllo d =>
      TrustedFr endsCreateNotAllo dErr
    case TrustedFr endsQuoteT etNotAllo d =>
      TrustedFr endsQuoteT etNotAllo dErr
    case CollabT et nval dParams =>
      CollabT et nval dParamsErr
    case StaleT etEngage ntNotAllo d =>
      StaleT etEngage ntNotAllo dErr
    case StaleT etQuoteT etNotAllo d =>
      StaleT etQuoteT etNotAllo dErr
    case F eldEd NotAllo d =>
      F eldEd NotAllo dErr
    case NotEl g bleForEd  =>
      NotEl g bleForEd Err
    case _ =>
      Gener cT etCreateErr
  }
}
