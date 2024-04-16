package com.tw ter.t etyp e
package federated.columns

 mport com.tw ter.accounts.ut l.Safety tadataUt ls
 mport com.tw ter.ads.callback.thr ftscala.Engage ntRequest
 mport com.tw ter.bouncer.thr ftscala.{Bounce => BouncerBounce}
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
 mport com.tw ter.t etyp e.federated.columns.Ap Errors._
 mport com.tw ter.t etyp e.federated.columns.CreateRet etColumn.toCreateRet etErr
 mport com.tw ter.t etyp e.federated.context.GetRequestContext
 mport com.tw ter.t etyp e.federated.prefetc ddata.Prefetc dDataRequest
 mport com.tw ter.t etyp e.federated.prefetc ddata.Prefetc dDataResponse
 mport com.tw ter.t etyp e.federated.promotedcontent.T etPromotedContentLogger
 mport com.tw ter.t etyp e.federated.promotedcontent.T etPromotedContentLogger.Ret etEngage nt
 mport com.tw ter.t etyp e.thr ftscala.T etCreateState._
 mport com.tw ter.t etyp e.thr ftscala.{graphql => gql}
 mport com.tw ter.t etyp e.{thr ftscala => thr ft}
 mport com.tw ter. averb rd.common.{GetRequestContext => WGetRequestContext}

class CreateRet etColumn(
  ret et: thr ft.Ret etRequest => Future[thr ft.PostT etResult],
  getRequestContext: GetRequestContext,
  prefetc dDataRepos ory: Prefetc dDataRequest => St ch[Prefetc dDataResponse],
  logT etPromotedContent: T etPromotedContentLogger.Type,
  statsRece ver: StatsRece ver,
) extends StratoFed.Column(CreateRet etColumn.Path)
    w h StratoFed.Execute.St chW hContext
    w h StratoFed.HandleDarkRequests {

  overr de val pol cy: Pol cy = AllOf(
    Seq(AccessPol cy.T etMutat onCommonAccessPol c es, BouncerAccess()))

  // T  underly ng call to thr ftT etServ ce.postRet et  s not  dempotent
  overr de val  s dempotent: Boolean = false

  overr de type Arg = gql.CreateRet etRequest
  overr de type Result = gql.CreateRet etResponseW hSubqueryPrefetch ems

  overr de val argConv: Conv[Arg] = ScroogeConv.fromStruct
  overr de val resultConv: Conv[Result] = ScroogeConv.fromStruct

  overr de val contact nfo: Contact nfo = T etyp eContact nfo
  overr de val  tadata: Op tadata = Op tadata(
    So (Product on),
    So (Pla nText("Creates a ret et by t  call ng Tw ter user of t  g ven s ce t et.")))

  pr vate val get averb rdCtx = new WGetRequestContext()

  overr de def execute(request: Arg, opContext: OpContext): St ch[Result] = {
    val ctx = getRequestContext(opContext)

    // F rst, do any request para ter val dat on that can result  n an error
    // pr or to call ng  nto thr ftT etServ ce.ret et.
    val safetyLevel = ctx.safetyLevel.getOrElse(throw SafetyLevelM ss ngErr)

    // Macaw-t ets returns Ap Error.Cl entNotPr v leged  f t  caller prov des
    // an  mpress on_ d but lacks t  PROMOTED_TWEETS_ N_T MEL NE pr v lege.
    val track ng d = request.engage ntRequest match {
      case So (engage ntRequest: Engage ntRequest)  f ctx.hasPr v legePromotedT ets nT  l ne =>
        Track ng d.parse(engage ntRequest. mpress on d, statsRece ver)
      case So (e: Engage ntRequest) =>
        throw Cl entNotPr v legedErr
      case None =>
        None
    }

    // Dev ceS ce  s an oauth str ng computed from t  Cl entAppl cat on d.
    // Macaw-t ets allows non-oauth callers, but GraphQL does not. An undef ned
    // Cl entAppl cat on d  s s m lar to T etCreateState.Dev ceS ceNotFound,
    // wh ch Macaw-t ets handles v a a catch-all that returns
    // Ap Error.Gener cAccessDen ed
    val dev ceS ce = ctx.dev ceS ce.getOrElse(throw Gener cAccessDen edErr)

    // Macaw-t ets doesn't perform any para ter val dat on for t  components
    // used as  nput to makeSafety taData.
    val safety tadata = Safety tadataUt ls.makeSafety taData(
      sess onHash = ctx.sess onHash,
      knownDev ceToken = ctx.knownDev ceToken,
      contr butor d = ctx.contr butor d
    )

    val thr ftRet etRequest = thr ft.Ret etRequest(
      s ceStatus d = request.t et d,
      user d = ctx.tw terUser d,
      contr butorUser d = None, // no longer supported, per t et_serv ce.thr ft
      createdV a = dev ceS ce,
      nullcast = request.nullcast,
      track ng d = track ng d,
      dark = ctx. sDarkRequest,
      hydrat onOpt ons = So (Hydrat onOpt ons.wr ePathHydrat onOpt ons(ctx.cardsPlatformKey)),
      safety taData = So (safety tadata),
    )

    val st chRet et = St ch.callFuture(ret et(thr ftRet etRequest))

    request.engage ntRequest.foreach { engage nt =>
      logT etPromotedContent(engage nt, Ret etEngage nt, ctx. sDarkRequest)
    }

    st chRet et.flatMap { result: thr ft.PostT etResult =>
      result.state match {
        case thr ft.T etCreateState.Ok =>
          val r = Prefetc dDataRequest(
            t et = result.t et.get,
            s ceT et = result.s ceT et,
            quotedT et = result.quotedT et,
            safetyLevel = safetyLevel,
            requestContext = get averb rdCtx()
          )

          prefetc dDataRepos ory(r)
            .l ftToOpt on()
            .map((prefetc dData: Opt on[Prefetc dDataResponse]) => {
              gql.CreateRet etResponseW hSubqueryPrefetch ems(
                data = So (gql.CreateRet etResponse(result.t et.map(_. d))),
                subqueryPrefetch ems = prefetc dData.map(_.value)
              )
            })
        case errState =>
          throw toCreateRet etErr(errState, result.bounce, result.fa lureReason)
      }
    }
  }
}

object CreateRet etColumn {
  val Path = "t etyp e/createRet et.T et"

  /**
   * Ported from:
   *   StatusesRet etController#ret etStatus rescue block
   *   T etyP eStatusRepos ory.toRet etExcept on
   */
  def toCreateRet etErr(
    errState: thr ft.T etCreateState,
    bounce: Opt on[BouncerBounce],
    fa lureReason: Opt on[Str ng]
  ): Err = errState match {
    case CannotRet etBlock ngUser =>
      BlockedUserErr
    case AlreadyRet eted =>
      AlreadyRet etedErr
    case Dupl cate =>
      Dupl cateStatusErr
    case CannotRet etOwnT et | CannotRet etProtectedT et | CannotRet etSuspendedUser =>
       nval dRet etForStatusErr
    case UserNotFound | S ceT etNotFound | S ceUserNotFound | CannotRet etDeact vatedUser =>
      StatusNotFoundErr
    case UserDeact vated | UserSuspended =>
      UserDen edRet etErr
    case RateL m Exceeded =>
      RateL m ExceededErr
    case UrlSpam =>
      T etUrlSpamErr
    case Spam | UserReadonly =>
      T etSpam rErr
    case SafetyRateL m Exceeded =>
      SafetyRateL m ExceededErr
    case Bounce  f bounce. sDef ned =>
      accessDen edByBouncerErr(bounce.get)
    case D sabledBy p Pol cy =>
      fa lureReason
        .map(t etEngage ntL m edErr)
        .getOrElse(Gener cAccessDen edErr)
    case TrustedFr endsRet etNotAllo d =>
      TrustedFr endsRet etNotAllo dErr
    case StaleT etRet etNotAllo d =>
      StaleT etRet etNotAllo dErr
    case _ =>
      Gener cAccessDen edErr
  }
}
