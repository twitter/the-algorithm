package com.tw ter.t etyp e
package handler

 mport com.tw ter.flockdb.cl ent._
 mport com.tw ter.snowflake. d.Snowflake d
 mport com.tw ter.st ch.St ch
 mport com.tw ter.t etyp e.add  onalf elds.Add  onalF elds.setAdd  onalF elds
 mport com.tw ter.t etyp e.core._
 mport com.tw ter.t etyp e.repos ory._
 mport com.tw ter.t etyp e.thr ftscala._
 mport com.tw ter.t etyp e.thr ftscala.ent  es.Ent yExtractor
 mport com.tw ter.t etyp e.t ettext.Truncator
 mport com.tw ter.t etyp e.ut l.Commun yUt l
 mport com.tw ter.t etyp e.ut l.Ed ControlUt l

case class S ceT etRequest(
  t et d: T et d,
  user: User,
  hydrateOpt ons: Wr ePathHydrat onOpt ons)

object Ret etBu lder {
   mport T etBu lder._
   mport UpstreamFa lure._

  type Type = FutureArrow[Ret etRequest, T etBu lderResult]

  val SGSTestRole = "soc algraph"

  val log: Logger = Logger(getClass)

  /**
   * Ret ets text gets RT and userna  prepended
   */
  def composeRet etText(text: Str ng, s ceUser: User): Str ng =
    composeRet etText(text, s ceUser.prof le.get.screenNa )

  /**
   * Ret ets text gets RT and userna  prepended
   */
  def composeRet etText(text: Str ng, screenNa : Str ng): Str ng =
    Truncator.truncateForRet et("RT @" + screenNa  + ": " + text)

  //   do not want to allow commun y t ets to be ret eted.
  def val dateNotCommun yT et(s ceT et: T et): Future[Un ] =
     f (Commun yUt l.hasCommun y(s ceT et.commun  es)) {
      Future.except on(T etCreateFa lure.State(T etCreateState.Commun yRet etNotAllo d))
    } else {
      Future.Un 
    }

  //   do not want to allow Trusted Fr ends t ets to be ret eted.
  def val dateNotTrustedFr endsT et(s ceT et: T et): Future[Un ] =
    s ceT et.trustedFr endsControl match {
      case So (trustedFr endsControl) =>
        Future.except on(T etCreateFa lure.State(T etCreateState.TrustedFr endsRet etNotAllo d))
      case None =>
        Future.Un 
    }

  //   do not want to allow ret et of a stale vers on of a t et  n an ed  cha n.
  def val dateStaleT et(s ceT et: T et): Future[Un ] = {
     f (!Ed ControlUt l. sLatestEd (s ceT et.ed Control, s ceT et. d).getOrElse(true)) {
      Future.except on(T etCreateFa lure.State(T etCreateState.StaleT etRet etNotAllo d))
    } else {
      // t  s ce t et does not have any ed  control or t  s ce t et  s t  latest t et
      Future.Un 
    }
  }

  /**
   * Bu lds t  Ret etBu lder
   */
  def apply(
    val dateRequest: Ret etRequest => Future[Un ],
    t et dGenerator: T et dGenerator,
    t etRepo: T etRepos ory.Type,
    userRepo: UserRepos ory.Type,
    tflock: TFlockCl ent,
    dev ceS ceRepo: Dev ceS ceRepos ory.Type,
    val dateUpdateRateL m : RateL m C cker.Val date,
    spamC cker: Spam.C cker[Ret etSpamRequest] = Spam.DoNotC ckSpam,
    updateUserCounts: (User, T et) => Future[User],
    superFollowRelat onsRepo: StratoSuperFollowRelat onsRepos ory.Type,
    unret etEd s: T etDeletePathHandler.Unret etEd s,
    setEd W ndowToS xtyM nutes: Gate[Un ]
  ): Ret etBu lder.Type = {
    val ent yExtactor = Ent yExtractor.mutat onAll.endo

    val s ceT etRepo: S ceT etRequest => St ch[T et] =
      req => {
        t etRepo(
          req.t et d,
          Wr ePathQueryOpt ons.ret etS ceT et(req.user, req.hydrateOpt ons)
        ).rescue {
            case _: F lteredState => St ch.NotFound
          }
          .rescue {
            convertRepoExcept ons(T etCreateState.S ceT etNotFound, T etLookupFa lure(_))
          }
      }

    val getUser = userLookup(userRepo)
    val getS ceUser = s ceUserLookup(userRepo)
    val getDev ceS ce = dev ceS ceLookup(dev ceS ceRepo)

    /**
     *   exempt SGS test users from t  c ck to get t m through Block v2 test ng.
     */
    def  sSGSTestRole(user: User): Boolean =
      user.roles.ex sts { roles => roles.roles.conta ns(SGSTestRole) }

    def val dateCanRet et(
      user: User,
      s ceUser: User,
      s ceT et: T et,
      request: Ret etRequest
    ): Future[Un ] =
      Future
        .jo n(
          val dateNotCommun yT et(s ceT et),
          val dateNotTrustedFr endsT et(s ceT et),
          val dateS ceUserRet etable(user, s ceUser),
          val dateStaleT et(s ceT et),
          Future.w n(!request.dark) {
             f (request.returnSuccessOnDupl cate)
              fa lW hRet et d fAlreadyRet eted(user, s ceT et)
            else
              val dateNotAlreadyRet eted(user, s ceT et)
          }
        )
        .un 

    def val dateS ceUserRet etable(user: User, s ceUser: User): Future[Un ] =
       f (s ceUser.prof le. sEmpty)
        Future.except on(UserProf leEmptyExcept on)
      else  f (s ceUser.safety. sEmpty)
        Future.except on(UserSafetyEmptyExcept on)
      else  f (s ceUser.v ew. sEmpty)
        Future.except on(UserV ewEmptyExcept on)
      else  f (user. d != s ceUser. d && s ceUser.safety.get. sProtected)
        Future.except on(T etCreateFa lure.State(T etCreateState.CannotRet etProtectedT et))
      else  f (s ceUser.safety.get.deact vated)
        Future.except on(T etCreateFa lure.State(T etCreateState.CannotRet etDeact vatedUser))
      else  f (s ceUser.safety.get.suspended)
        Future.except on(T etCreateFa lure.State(T etCreateState.CannotRet etSuspendedUser))
      else  f (s ceUser.v ew.get.blockedBy && ! sSGSTestRole(user))
        Future.except on(T etCreateFa lure.State(T etCreateState.CannotRet etBlock ngUser))
      else  f (s ceUser.prof le.get.screenNa . sEmpty)
        Future.except on(
          T etCreateFa lure.State(T etCreateState.CannotRet etUserW houtScreenNa )
        )
      else
        Future.Un 

    def tflockGraphConta ns(
      graph: StatusGraph,
      from d: Long,
      to d: Long,
      d r: D rect on
    ): Future[Boolean] =
      tflock.conta ns(graph, from d, to d, d r).rescue {
        case ex: OverCapac y => Future.except on(ex)
        case ex => Future.except on(TFlockLookupFa lure(ex))
      }

    def getRet et dFromTflock(s ceT et d: T et d, user d: User d): Future[Opt on[Long]] =
      tflock
        .selectAll(
          Select(
            s ce d = s ceT et d,
            graph = Ret etsGraph,
            d rect on = Forward
          ). ntersect(
            Select(
              s ce d = user d,
              graph = UserT  l neGraph,
              d rect on = Forward
            )
          )
        )
        .map(_. adOpt on)

    def val dateNotAlreadyRet eted(user: User, s ceT et: T et): Future[Un ] =
      // use t  perspect ve object from TLS  f ava lable, ot rw se, c ck w h tflock
      (s ceT et.perspect ve match {
        case So (perspect ve) =>
          Future.value(perspect ve.ret eted)
        case None =>
          //   have to query t  Ret etS ceGraph  n t  Reverse order because
          //    s only def ned  n that d rect on,  nstead of b -d rect onally
          tflockGraphConta ns(Ret etS ceGraph, user. d, s ceT et. d, Reverse)
      }).flatMap {
        case true =>
          Future.except on(T etCreateFa lure.State(T etCreateState.AlreadyRet eted))
        case false => Future.Un 
      }

    def fa lW hRet et d fAlreadyRet eted(user: User, s ceT et: T et): Future[Un ] =
      // use t  perspect ve object from TLS  f ava lable, ot rw se, c ck w h tflock
      (s ceT et.perspect ve.flatMap(_.ret et d) match {
        case So (t et d) => Future.value(So (t et d))
        case None =>
          getRet et dFromTflock(s ceT et. d, user. d)
      }).flatMap {
        case None => Future.Un 
        case So (t et d) =>
          Future.except on(T etCreateFa lure.AlreadyRet eted(t et d))
      }

    def val dateContr butor(contr butor dOpt: Opt on[User d]): Future[Un ] =
       f (contr butor dOpt. sDef ned)
        Future.except on(T etCreateFa lure.State(T etCreateState.Contr butorNotSupported))
      else
        Future.Un 

    case class Ret etS ce(s ceT et: T et, parentUser d: User d)

    /**
     * Recurs vely follows a ret et cha n to t  root s ce t et.  Also returns user  d from t 
     * f rst walked t et as t  'parentUser d'.
     *  n pract ce, t  depth of t  cha n should never be greater than 2 because
     * share.s ceStatus d should always reference t  root (unl ke share.parentStatus d).
     */
    def f ndRet etS ce(
      t et d: T et d,
      forUser: User,
      hydrateOpt ons: Wr ePathHydrat onOpt ons
    ): Future[Ret etS ce] =
      St ch
        .run(s ceT etRepo(S ceT etRequest(t et d, forUser, hydrateOpt ons)))
        .flatMap { t et =>
          getShare(t et) match {
            case None => Future.value(Ret etS ce(t et, getUser d(t et)))
            case So (share) =>
              f ndRet etS ce(share.s ceStatus d, forUser, hydrateOpt ons)
                .map(_.copy(parentUser d = getUser d(t et)))
          }
        }

    FutureArrow { request =>
      for {
        () <- val dateRequest(request)
        userFuture = St ch.run(getUser(request.user d))
        t et dFuture = t et dGenerator()
        devsrcFuture = St ch.run(getDev ceS ce(request.createdV a))
        user <- userFuture
        t et d <- t et dFuture
        devsrc <- devsrcFuture
        rtS ce <- f ndRet etS ce(
          request.s ceStatus d,
          user,
          request.hydrat onOpt ons.getOrElse(Wr ePathHydrat onOpt ons(s mpleQuotedT et = true))
        )
        s ceT et = rtS ce.s ceT et
        s ceUser <- St ch.run(getS ceUser(getUser d(s ceT et), request.user d))

        //   want to conf rm that a user  s actually allo d to
        // ret et an Exclus ve T et (only ava lable to super follo rs)
        () <- StratoSuperFollowRelat onsRepos ory.Val date(
          s ceT et.exclus veT etControl,
          user. d,
          superFollowRelat onsRepo)

        () <- val dateUser(user)
        () <- val dateUpdateRateL m ((user. d, request.dark))
        () <- val dateContr butor(request.contr butorUser d)
        () <- val dateCanRet et(user, s ceUser, s ceT et, request)
        () <- unret etEd s(s ceT et.ed Control, s ceT et. d, user. d)

        spamRequest = Ret etSpamRequest(
          ret et d = t et d,
          s ceUser d = getUser d(s ceT et),
          s ceT et d = s ceT et. d,
          s ceT etText = getText(s ceT et),
          s ceUserNa  = s ceUser.prof le.map(_.screenNa ),
          safety taData = request.safety taData
        )

        spamResult <- spamC cker(spamRequest)

        safety = user.safety.get

        share = Share(
          s ceStatus d = s ceT et. d,
          s ceUser d = s ceUser. d,
          parentStatus d = request.s ceStatus d
        )

        ret etText = composeRet etText(getText(s ceT et), s ceUser)
        createdAt = Snowflake d(t et d).t  

        coreData = T etCoreData(
          user d = request.user d,
          text = ret etText,
          createdAtSecs = createdAt. nSeconds,
          createdV a = devsrc. nternalNa ,
          share = So (share),
          hasTakedown = safety.hasTakedown,
          track ng d = request.track ng d,
          nsfwUser = safety.nsfwUser,
          nsfwAdm n = safety.nsfwAdm n,
          narrowcast = request.narrowcast,
          nullcast = request.nullcast
        )

        ret et = T et(
           d = t et d,
          coreData = So (coreData),
          contr butor = getContr butor(request.user d),
          ed Control = So (
            Ed Control. n  al(
              Ed ControlUt l
                .makeEd Control n  al(
                  t et d = t et d,
                  createdAt = createdAt,
                  setEd W ndowToS xtyM nutes = setEd W ndowToS xtyM nutes
                )
                . n  al
                .copy( sEd El g ble = So (false))
            )
          ),
        )

        ret etW hEnt  es = ent yExtactor(ret et)
        ret etW hAdd  onalF elds = setAdd  onalF elds(
          ret etW hEnt  es,
          request.add  onalF elds
        )
        // update t  perspect ve and counts f elds of t  s ce t et to reflect t  effects
        // of t  user perform ng a ret et, even though those effects haven't happened yet.
        updatedS ceT et = s ceT et.copy(
          perspect ve = s ceT et.perspect ve.map {
            _.copy(ret eted = true, ret et d = So (ret et. d))
          },
          counts = s ceT et.counts.map { c => c.copy(ret etCount = c.ret etCount.map(_ + 1)) }
        )

        user <- updateUserCounts(user, ret etW hAdd  onalF elds)
      } y eld {
        T etBu lderResult(
          t et = ret etW hAdd  onalF elds,
          user = user,
          createdAt = createdAt,
          s ceT et = So (updatedS ceT et),
          s ceUser = So (s ceUser),
          parentUser d = So (rtS ce.parentUser d),
           sS lentFa l = spamResult == Spam.S lentFa l
        )
      }
    }
  }
}
