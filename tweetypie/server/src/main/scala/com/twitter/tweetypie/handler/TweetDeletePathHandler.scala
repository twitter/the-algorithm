package com.tw ter.t etyp e
package handler

 mport com.tw ter.convers ons.Durat onOps.R chDurat on
 mport com.tw ter.servo.except on.thr ftscala.Cl entError
 mport com.tw ter.servo.except on.thr ftscala.Cl entErrorCause
 mport com.tw ter.servo.ut l.FutureArrow
 mport com.tw ter.snowflake. d.Snowflake d
 mport com.tw ter.st ch.St ch
 mport com.tw ter.st ch.NotFound
 mport com.tw ter.t  l neserv ce.thr ftscala.Perspect veResult
 mport com.tw ter.t  l neserv ce.{thr ftscala => tls}
 mport com.tw ter.t etyp e.core._
 mport com.tw ter.t etyp e.repos ory._
 mport com.tw ter.t etyp e.store._
 mport com.tw ter.t etyp e.thr ftscala._
 mport com.tw ter.ut l.T  
 mport com.tw ter.ut l.Try
 mport Try._
 mport com.tw ter.spam.rtf.thr ftscala.SafetyLabelType
 mport com.tw ter.t etyp e.backends.T  l neServ ce.GetPerspect ves
 mport com.tw ter.t etyp e.ut l.Ed ControlUt l
 mport scala.ut l.control.NoStackTrace

case class CascadedDeleteNotAva lable(ret et d: T et d) extends Except on w h NoStackTrace {
  overr de def get ssage: Str ng =
    s"""|Cascaded delete t et fa led because t et $ret et d
        | s not present  n cac  or manhattan.""".str pMarg n
}

object T etDeletePathHandler {

  type DeleteT ets =
    (DeleteT etsRequest, Boolean) => Future[Seq[DeleteT etResult]]

  type Unret etEd s = (Opt on[Ed Control], T et d, User d) => Future[Un ]

  /** T   nformat on from a deleteT et request that can be  nspected by a deleteT ets val dator */
  case class DeleteT etsContext(
    byUser d: Opt on[User d],
    aut nt catedUser d: Opt on[User d],
    t etAuthor d: User d,
    users: Map[User d, User],
     sUserErasure: Boolean,
    expectedErasureUser d: Opt on[User d],
    t et sBounced: Boolean,
     sBounceDelete: Boolean)

  /** Prov des reason a t et delet on was allo d */
  sealed tra  DeleteAuthor zat on { def byUser d: Opt on[User d] }

  case class Author zedByT etOwner(user d: User d) extends DeleteAuthor zat on {
    def byUser d: Opt on[User d] = So (user d)
  }
  case class Author zedByT etContr butor(contr butorUser d: User d) extends DeleteAuthor zat on {
    def byUser d: Opt on[User d] = So (contr butorUser d)
  }
  case class Author zedByAdm n(adm nUser d: User d) extends DeleteAuthor zat on {
    def byUser d: Opt on[User d] = So (adm nUser d)
  }
  case object Author zedByErasure extends DeleteAuthor zat on {
    def byUser d: None.type = None
  }

  // Type for a  thod that rece ves all t  relevant  nformat on about a proposed  nternal t et
  // delet on and can return Future.except on to cancel t  delete due to a val dat on error or
  // return a [[DeleteAuthor zat on]] spec fy ng t  reason t  delet on  s allo d.
  type Val dateDeleteT ets = FutureArrow[DeleteT etsContext, DeleteAuthor zat on]

  val userF eldsForDelete: Set[UserF eld] =
    Set(UserF eld.Account, UserF eld.Prof le, UserF eld.Roles, UserF eld.Safety)

  val userQueryOpt ons: UserQueryOpt ons =
    UserQueryOpt ons(
      userF eldsForDelete,
      UserV s b l y.All
    )

  // user_agent property or g nates from t  cl ent so truncate to a reasonable length
  val MaxUserAgentLength = 1000

  // Age under wh ch   treat not found t ets  n
  // cascaded_delete_t et as a temporary cond  on (t  most l kely
  // explanat on be ng that t  t et has not yet been
  // repl cated). T ets older than t    assu  are due to
  // *permanently*  ncons stent data, e  r spur ous edges  n tflock or
  // t ets that are not loadable from Manhattan.
  val MaxCascadedDeleteT etTemporary ncons stencyAge: Durat on =
    10.m nutes
}

tra  T etDeletePathHandler {
   mport T etDeletePathHandler.Val dateDeleteT ets

  def cascadedDeleteT et(request: CascadedDeleteT etRequest): Future[Un ]

  def deleteT ets(
    request: DeleteT etsRequest,
     sUnret etEd s: Boolean = false,
  ): Future[Seq[DeleteT etResult]]

  def  nternalDeleteT ets(
    request: DeleteT etsRequest,
    byUser d: Opt on[User d],
    aut nt catedUser d: Opt on[User d],
    val date: Val dateDeleteT ets,
     sUnret etEd s: Boolean = false
  ): Future[Seq[DeleteT etResult]]

  def unret etEd s(
    optEd Control: Opt on[Ed Control],
    excludedT et d: T et d,
    byUser d: User d
  ): Future[Un ]
}

/**
 *  mple ntat on of T etDeletePathHandler
 */
class DefaultT etDeletePathHandler(
  stats: StatsRece ver,
  t etResultRepo: T etResultRepos ory.Type,
  userRepo: UserRepos ory.Opt onal,
  stratoSafetyLabelsRepo: StratoSafetyLabelsRepos ory.Type,
  lastQuoteOfQuoterRepo: LastQuoteOfQuoterRepos ory.Type,
  t etStore: TotalT etStore,
  getPerspect ves: GetPerspect ves)
    extends T etDeletePathHandler {
   mport T etDeletePathHandler._

  val t etRepo: T etRepos ory.Type = T etRepos ory.fromT etResult(t etResultRepo)

  // attempt to delete t ets was made by so one ot r than t  t et owner or an adm n user
  object DeleteT etsPerm ss onExcept on extends Except on w h NoStackTrace
  object ExpectedUser dM smatchExcept on extends Except on w h NoStackTrace

  pr vate[t ] val log = Logger("com.tw ter.t etyp e.store.T etDelet ons")

  pr vate[t ] val cascadeEd Delete = stats.scope("cascade_ed _delete")
  pr vate[t ] val cascadeEd DeletesEnqueued = cascadeEd Delete.counter("enqueued")
  pr vate[t ] val cascadeEd DeleteT ets = cascadeEd Delete.counter("t ets")
  pr vate[t ] val cascadeEd DeleteFa lures = cascadeEd Delete.counter("fa lures")

  pr vate[t ] val cascadedDeleteT et = stats.scope("cascaded_delete_t et")
  pr vate[t ] val cascadedDeleteT etFa lures = cascadedDeleteT et.counter("fa lures")
  pr vate[t ] val cascadedDeleteT etS ceMatch = cascadedDeleteT et.counter("s ce_match")
  pr vate[t ] val cascadedDeleteT etS ceM smatch =
    cascadedDeleteT et.counter("s ce_m smatch")
  pr vate[t ] val cascadedDeleteT etT etNotFound =
    cascadedDeleteT et.counter("t et_not_found")
  pr vate[t ] val cascadedDeleteT etT etNotFoundAge =
    cascadedDeleteT et.stat("t et_not_found_age")
  pr vate[t ] val cascadedDeleteT etUserNotFound = cascadedDeleteT et.counter("user_not_found")

  pr vate[t ] val deleteT ets = stats.scope("delete_t ets")
  pr vate[t ] val deleteT etsAuth = deleteT ets.scope("per_t et_auth")
  pr vate[t ] val deleteT etsAuthAttempts = deleteT etsAuth.counter("attempts")
  pr vate[t ] val deleteT etsAuthFa lures = deleteT etsAuth.counter("fa lures")
  pr vate[t ] val deleteT etsAuthSuccessAdm n = deleteT etsAuth.counter("success_adm n")
  pr vate[t ] val deleteT etsAuthSuccessByUser = deleteT etsAuth.counter("success_by_user")
  pr vate[t ] val deleteT etsT ets = deleteT ets.counter("t ets")
  pr vate[t ] val deleteT etsFa lures = deleteT ets.counter("fa lures")
  pr vate[t ] val deleteT etsT etNotFound = deleteT ets.counter("t et_not_found")
  pr vate[t ] val deleteT etsUserNotFound = deleteT ets.counter("user_not_found")
  pr vate[t ] val user dM smatch nT etDelete =
    deleteT ets.counter("expected_actual_user_ d_m smatch")
  pr vate[t ] val bounceDeleteFlagNotSet =
    deleteT ets.counter("bounce_delete_flag_not_set")

  pr vate[t ] def getUser(user d: User d): Future[Opt on[User]] =
    St ch.run(userRepo(UserKey(user d), userQueryOpt ons))

  pr vate[t ] def getUsersForDeleteT ets(user ds: Seq[User d]): Future[Map[User d, User]] =
    St ch.run(
      St ch
        .traverse(user ds) { user d =>
          userRepo(UserKey(user d), userQueryOpt ons).map {
            case So (u) => So (user d -> u)
            case None => deleteT etsUserNotFound. ncr(); None
          }
        }
        .map(_.flatten.toMap)
    )

  pr vate[t ] def getT et(t et d: T et d): Future[T et] =
    St ch.run(t etRepo(t et d, Wr ePathQueryOpt ons.deleteT etsW houtEd Control))

  pr vate[t ] def getS ngleDeletedT et(
     d: T et d,
     sCascadedEd T etDelet on: Boolean = false
  ): St ch[Opt on[T etData]] = {
    val opts =  f ( sCascadedEd T etDelet on) {
      // D sable ed  control hydrat on  f t   s cascade delete of ed s.
      // W n ed  control  s hydrated, t  t et w ll actually be cons dered already deleted.
      Wr ePathQueryOpt ons.deleteT etsW houtEd Control
    } else {
      Wr ePathQueryOpt ons.deleteT ets
    }
    t etResultRepo( d, opts)
      .map(_.value)
      .l ftToOpt on {
        //   treat t  request t  sa  w t r t  t et never
        // ex sted or  s  n one of t  already-deleted states by
        // just f lter ng out those t ets. Any t ets that  
        // return should be deleted.  f t  t et has been
        // bounce-deleted,   never want to soft-delete  , and
        // v ce versa.
        case NotFound | F lteredState.Unava lable.T etDeleted |
            F lteredState.Unava lable.BounceDeleted =>
          true
      }
  }

  pr vate[t ] def getT etsForDeleteT ets(
     ds: Seq[T et d],
     sCascadedEd T etDelet on: Boolean
  ): Future[Map[T et d, T etData]] =
    St ch
      .run {
        St ch.traverse( ds) {  d =>
          getS ngleDeletedT et( d,  sCascadedEd T etDelet on)
            .map {
              // W n delet ng a t et that has been ed ed,   want to  nstead delete t   n  al vers on.
              // Because t   n  al t et w ll be hydrated  n every request,  f    s deleted, later
              // rev s ons w ll be h dden, and cleaned up asynchronously by TP Daemons

              // Ho ver,   don't need to do a second lookup  f  's already t  or g nal t et
              // or  f  're do ng a cascad ng ed  t et delete (delet ng t  ent re t et  tory)
              case So (t etData)
                   f Ed ControlUt l. s n  alT et(t etData.t et) ||
                     sCascadedEd T etDelet on =>
                St ch.value(So (t etData))
              case So (t etData) =>
                getS ngleDeletedT et(Ed ControlUt l.get n  alT et d(t etData.t et))
              case None =>
                St ch.value(None)
              //   need to preserve t   nput t et d, and t   n  al T etData
            }.flatten.map(t etData => ( d, t etData))
        }
      }
      .map(_.collect { case (t et d, So (t etData)) => (t et d, t etData) }.toMap)

  pr vate[t ] def getStratoBounceStatuses(
     ds: Seq[Long],
     sUserErasure: Boolean,
     sCascadedEd edT etDelet on: Boolean
  ): Future[Map[T et d, Boolean]] = {
    // Don't load bounce label for user erasure t et delet on.
    // User Erasure delet ons cause unnecessary sp kes of traff c
    // to Strato w n   read t  bounce label that   don't use.

    //   also want to always delete a bounced t et  f t  rest of t 
    // ed  cha n  s be ng deleted  n a cascaded ed  t et delete
     f ( sUserErasure ||  sCascadedEd edT etDelet on) {
      Future.value( ds.map( d =>  d -> false).toMap)
    } else {
      St ch.run(
        St ch
          .traverse( ds) {  d =>
            stratoSafetyLabelsRepo( d, SafetyLabelType.Bounce).map { label =>
               d -> label. sDef ned
            }
          }
          .map(_.toMap)
      )
    }
  }

  /** A suspended/deact vated user can't delete t ets */
  pr vate[t ] def userNotSuspendedOrDeact vated(user: User): Try[User] =
    user.safety match {
      case None => Throw(UpstreamFa lure.UserSafetyEmptyExcept on)
      case So (safety)  f safety.deact vated =>
        Throw(
          AccessDen ed(
            s"User deact vated user d: ${user. d}",
            errorCause = So (AccessDen edCause.UserDeact vated)
          )
        )
      case So (safety)  f safety.suspended =>
        Throw(
          AccessDen ed(
            s"User suspended user d: ${user. d}",
            errorCause = So (AccessDen edCause.UserSuspended)
          )
        )
      case _ => Return(user)
    }

  /**
   * Ensure that byUser has perm ss on to delete t et e  r by v rtue of own ng t  t et or be ng
   * an adm n user.  Returns t  reason as a DeleteAuthor zat on or else throws an Except on  f not
   * author zed.
   */
  pr vate[t ] def userAuthor zedToDeleteT et(
    byUser: User,
    optAut nt catedUser d: Opt on[User d],
    t etAuthor d: User d
  ): Try[DeleteAuthor zat on] = {

    def hasAdm nPr v lege =
      byUser.roles.ex sts(_.r ghts.conta ns("delete_user_t ets"))

    deleteT etsAuthAttempts. ncr()
     f (byUser. d == t etAuthor d) {
      deleteT etsAuthSuccessByUser. ncr()
      optAut nt catedUser d match {
        case So (u d) =>
          Return(Author zedByT etContr butor(u d))
        case None =>
          Return(Author zedByT etOwner(byUser. d))
      }
    } else  f (optAut nt catedUser d. sEmpty && hasAdm nPr v lege) { // contr butor may not assu  adm n role
      deleteT etsAuthSuccessAdm n. ncr()
      Return(Author zedByAdm n(byUser. d))
    } else {
      deleteT etsAuthFa lures. ncr()
      Throw(DeleteT etsPerm ss onExcept on)
    }
  }

  /**
   * expected user  d  s t   d prov ded on t  DeleteT etsRequest that t   nd cates wh ch user
   * owns t  t ets t y want to delete. T  actualUser d  s t  actual user d on t  t et   are about to delete.
   *   c ck to ensure t y are t  sa  as a safety c ck aga nst acc dental delet on of t ets e  r from user m stakes
   * or from corrupted data (e.g bad tflock edges)
   */
  pr vate[t ] def expectedUser dMatc sActualUser d(
    expectedUser d: User d,
    actualUser d: User d
  ): Try[Un ] =
     f (expectedUser d == actualUser d) {
      Return.Un 
    } else {
      user dM smatch nT etDelete. ncr()
      Throw(ExpectedUser dM smatchExcept on)
    }

  /**
   * Val dat on for t  normal publ c t et delete case, t  user must be found and must
   * not be suspended or deact vated.
   */
  val val dateT etsForPubl cDelete: Val dateDeleteT ets = FutureArrow {
    ctx: DeleteT etsContext =>
      Future.const(
        for {

          // byUser d must be present
          byUser d <- ctx.byUser d.orThrow(
            Cl entError(Cl entErrorCause.BadRequest, "M ss ng byUser d")
          )

          // t  byUser must be found
          byUserOpt = ctx.users.get(byUser d)
          byUser <- byUserOpt.orThrow(
            Cl entError(Cl entErrorCause.BadRequest, s"User $byUser d not found")
          )

          _ <- userNotSuspendedOrDeact vated(byUser)

          _ <- val dateBounceCond  ons(
            ctx.t et sBounced,
            ctx. sBounceDelete
          )

          //  f t re's a contr butor, make sure t  user  s found and not suspended or deact vated
          _ <-
            ctx.aut nt catedUser d
              .map { u d =>
                ctx.users.get(u d) match {
                  case None =>
                    Throw(Cl entError(Cl entErrorCause.BadRequest, s"Contr butor $u d not found"))
                  case So (authUser) =>
                    userNotSuspendedOrDeact vated(authUser)
                }
              }
              .getOrElse(Return.Un )

          //  f t  expected user  d  s present, make sure   matc s t  user  d on t  t et
          _ <-
            ctx.expectedErasureUser d
              .map { expectedUser d =>
                expectedUser dMatc sActualUser d(expectedUser d, ctx.t etAuthor d)
              }
              .getOrElse(Return.Un )

          // User must own t  t et or be an adm n
          deleteAuth <- userAuthor zedToDeleteT et(
            byUser,
            ctx.aut nt catedUser d,
            ctx.t etAuthor d
          )
        } y eld deleteAuth
      )
  }

  pr vate def val dateBounceCond  ons(
    t et sBounced: Boolean,
     sBounceDelete: Boolean
  ): Try[Un ] = {
     f (t et sBounced && ! sBounceDelete) {
      bounceDeleteFlagNotSet. ncr()
      Throw(Cl entError(Cl entErrorCause.BadRequest, "Cannot normal delete a Bounced T et"))
    } else {
      Return.Un 
    }
  }

  /**
   * Val dat on for t  user erasure case. User may be m ss ng.
   */
  val val dateT etsForUserErasureDaemon: Val dateDeleteT ets = FutureArrow {
    ctx: DeleteT etsContext =>
      Future
        .const(
          for {
            expectedUser d <- ctx.expectedErasureUser d.orThrow(
              Cl entError(
                Cl entErrorCause.BadRequest,
                "expectedUser d  s requ red for DeleteT etRequests"
              )
            )

            //  's cr  cal to always c ck that t  user d on t  t et   want to delete matc s t 
            // user d on t  erasure request. T  prevents us from acc dentally delet ng t ets not owned by t 
            // erased user, even  f tflock serves us bad data.
            val dat onResult <- expectedUser dMatc sActualUser d(expectedUser d, ctx.t etAuthor d)
          } y eld val dat onResult
        )
        .map(_ => Author zedByErasure)
  }

  /**
   * F ll  n m ss ng values of Aud DeleteT et w h values from Tw terContext.
   */
  def enr chM ss ngFromTw terContext(or g: Aud DeleteT et): Aud DeleteT et = {
    val v e r = Tw terContext()
    or g.copy(
      host = or g.host.orElse(v e r.flatMap(_.aud  p)),
      cl entAppl cat on d = or g.cl entAppl cat on d.orElse(v e r.flatMap(_.cl entAppl cat on d)),
      userAgent = or g.userAgent.orElse(v e r.flatMap(_.userAgent)).map(_.take(MaxUserAgentLength))
    )
  }

  /**
   * core delete t ets  mple ntat on.
   *
   * T  [[deleteT ets]]  thod wraps t   thod and prov des val dat on requ red
   * for a publ c endpo nt.
   */
  overr de def  nternalDeleteT ets(
    request: DeleteT etsRequest,
    byUser d: Opt on[User d],
    aut nt catedUser d: Opt on[User d],
    val date: Val dateDeleteT ets,
     sUnret etEd s: Boolean = false
  ): Future[Seq[DeleteT etResult]] = {

    val aud DeleteT et =
      enr chM ss ngFromTw terContext(request.aud Passthrough.getOrElse(Aud DeleteT et()))
    deleteT etsT ets. ncr(request.t et ds.s ze)
    for {
      t etDataMap <- getT etsForDeleteT ets(
        request.t et ds,
        request.cascadedEd edT etDelet on.getOrElse(false)
      )

      user ds: Seq[User d] = (t etDataMap.values.map { td =>
          getUser d(td.t et)
        } ++ byUser d ++ aut nt catedUser d).toSeq.d st nct

      users <- getUsersForDeleteT ets(user ds)

      stratoBounceStatuses <- getStratoBounceStatuses(
        t etDataMap.keys.toSeq,
        request. sUserErasure,
        request.cascadedEd edT etDelet on.getOrElse(false))

      results <- Future.collect {
        request.t et ds.map { t et d =>
          t etDataMap.get(t et d) match {
            // already deleted, so noth ng to do
            case None =>
              deleteT etsT etNotFound. ncr()
              Future.value(DeleteT etResult(t et d, T etDeleteState.Ok))
            case So (t etData) =>
              val t et: T et = t etData.t et
              val t et sBounced = stratoBounceStatuses(t et d)
              val optS ceT et: Opt on[T et] = t etData.s ceT etResult.map(_.value.t et)

              val val dat on: Future[(Boolean, DeleteAuthor zat on)] = for {
                 sLastQuoteOfQuoter <-  sF nalQuoteOfQuoter(t et)
                deleteAuth <- val date(
                  DeleteT etsContext(
                    byUser d = byUser d,
                    aut nt catedUser d = aut nt catedUser d,
                    t etAuthor d = getUser d(t et),
                    users = users,
                     sUserErasure = request. sUserErasure,
                    expectedErasureUser d = request.expectedUser d,
                    t et sBounced = t et sBounced,
                     sBounceDelete = request. sBounceDelete
                  )
                )
                _ <- optS ceT et match {
                  case So (s ceT et)  f ! sUnret etEd s =>
                    //  f t   s a ret et and t  delet on was not tr ggered by
                    // unret etEd s, unret et ed s of t  s ce T et
                    // before delet ng t  ret et.
                    //
                    // deleteAuth w ll always conta n a byUser d except for erasure delet on,
                    //  n wh ch case t  ret ets w ll be deleted  nd v dually.
                    deleteAuth.byUser d match {
                      case So (user d) =>
                        unret etEd s(s ceT et.ed Control, s ceT et. d, user d)
                      case None => Future.Un 
                    }
                  case _ => Future.Un 
                }
              } y eld {
                ( sLastQuoteOfQuoter, deleteAuth)
              }

              val dat on
                .flatMap {
                  case ( sLastQuoteOfQuoter: Boolean, deleteAuth: DeleteAuthor zat on) =>
                    val  sAdm nDelete = deleteAuth match {
                      case Author zedByAdm n(_) => true
                      case _ => false
                    }

                    val event =
                      DeleteT et.Event(
                        t et = t et,
                        t  stamp = T  .now,
                        user = users.get(getUser d(t et)),
                        byUser d = deleteAuth.byUser d,
                        aud Passthrough = So (aud DeleteT et),
                         sUserErasure = request. sUserErasure,
                         sBounceDelete = request. sBounceDelete && t et sBounced,
                         sLastQuoteOfQuoter =  sLastQuoteOfQuoter,
                         sAdm nDelete =  sAdm nDelete
                      )
                    val numberOfEd s:  nt = t et.ed Control
                      .collect {
                        case Ed Control. n  al( n  al) =>
                           n  al.ed T et ds.count(_ != t et. d)
                      }
                      .getOrElse(0)
                    cascadeEd DeletesEnqueued. ncr(numberOfEd s)
                    t etStore
                      .deleteT et(event)
                      .map(_ => DeleteT etResult(t et d, T etDeleteState.Ok))
                }
                .onFa lure { _ =>
                  deleteT etsFa lures. ncr()
                }
                .handle {
                  case ExpectedUser dM smatchExcept on =>
                    DeleteT etResult(t et d, T etDeleteState.ExpectedUser dM smatch)
                  case DeleteT etsPerm ss onExcept on =>
                    DeleteT etResult(t et d, T etDeleteState.Perm ss onError)
                }
          }
        }
      }
    } y eld results
  }

  pr vate def  sF nalQuoteOfQuoter(t et: T et): Future[Boolean] = {
    t et.quotedT et match {
      case So (qt) =>
        St ch.run {
          lastQuoteOfQuoterRepo
            .apply(qt.t et d, getUser d(t et))
            .l ftToTry
            .map(_.getOrElse(false))
        }
      case None => Future(false)
    }
  }

  /**
   *  Val dat ons for t  publ c deleteT ets endpo nt.
   *   - ensures that t  byUser d user can be found and  s  n t  correct user state
   *   - ensures that t  t et  s be ng deleted by t  t et's owner, or by an adm n
   *   f t re  s a val dat on error, a future.except on  s returned
   *
   *   f t  delete request  s part of a user erasure, val dat ons are relaxed (t  User  s allo d to be m ss ng).
   */
  val deleteT etsVal dator: Val dateDeleteT ets =
    FutureArrow { context =>
       f (context. sUserErasure) {
        val dateT etsForUserErasureDaemon(context)
      } else {
        val dateT etsForPubl cDelete(context)
      }
    }

  overr de def deleteT ets(
    request: DeleteT etsRequest,
     sUnret etEd s: Boolean = false,
  ): Future[Seq[DeleteT etResult]] = {

    // For compar son test ng   only want to compare t  DeleteT etsRequests that are generated
    //  n DeleteT ets path and not t  call that co s from t  Unret et path
    val context = Tw terContext()
     nternalDeleteT ets(
      request,
      byUser d = request.byUser d.orElse(context.flatMap(_.user d)),
      context.flatMap(_.aut nt catedUser d),
      deleteT etsVal dator,
       sUnret etEd s
    )
  }

  // Cascade delete t et  s t  log c for remov ng t ets that are detac d
  // from t  r dependency wh ch has been deleted. T y are already f ltered
  // out from serv ng, so t  operat on reconc les storage w h t  v ew
  // presented by T etyp e.
  // T  RPC call  s delegated from daemons or batch jobs. Currently t re
  // are two use-cases w n t  call  s  ssued:
  // *   Delet ng detac d ret ets after t  s ce t et was deleted.
  //     T   s done through Ret etsDelet on daemon and t 
  //     CleanupDetac dRet ets job.
  // *   Delet ng ed s of an  n  al t et that has been deleted.
  //     T   s done by CascadedEd edT etDelete daemon.
  //     Note that, w n serv ng t  or g nal delete request for an ed ,
  //     t   n  al t et  s only deleted, wh ch makes all ed s h dden.
  overr de def cascadedDeleteT et(request: CascadedDeleteT etRequest): Future[Un ] = {
    val contextV e r = Tw terContext()
    getT et(request.t et d)
      .transform {
        case Throw(
              F lteredState.Unava lable.T etDeleted | F lteredState.Unava lable.BounceDeleted) =>
          // T  ret et or ed  was already deleted v a so  ot r  chan sm
          Future.Un 

        case Throw(NotFound) =>
          cascadedDeleteT etT etNotFound. ncr()
          val recentlyCreated =
             f (Snowflake d. sSnowflake d(request.t et d)) {
              val age = T  .now - Snowflake d(request.t et d).t  
              cascadedDeleteT etT etNotFoundAge.add(age. nM ll seconds)
              age < MaxCascadedDeleteT etTemporary ncons stencyAge
            } else {
              false
            }

           f (recentlyCreated) {
            // Treat t  NotFound as a temporary cond  on, most
            // l kely due to repl cat on lag.
            Future.except on(CascadedDeleteNotAva lable(request.t et d))
          } else {
            // Treat t  NotFound as a permanent  ncons stenty, e  r
            // spur ous edges  n tflock or  nval d data  n Manhattan. T 
            // was happen ng a few t  s an h  dur ng t  t   that  
            //  re not treat ng   spec ally. For now,   w ll just log that
            //   happened, but  n t  longer term,   would be good
            // to collect t  data and repa r t  corrupt on.
            log.warn(
              Seq(
                "cascaded_delete_t et_old_not_found",
                request.t et d,
                request.cascadedFromT et d
              ).mkStr ng("\t")
            )
            Future.Done
          }

        // Any ot r F lteredStates should not be thrown because of
        // t  opt ons that   used to load t  t et, so   w ll just
        // let t m bubble up as an  nternal server error
        case Throw(ot r) =>
          Future.except on(ot r)

        case Return(t et) =>
          Future
            .jo n(
               sF nalQuoteOfQuoter(t et),
              getUser(getUser d(t et))
            )
            .flatMap {
              case ( sLastQuoteOfQuoter, user) =>
                 f (user. sEmpty) {
                  cascadedDeleteT etUserNotFound. ncr()
                }
                val t etS ce d = getShare(t et).map(_.s ceStatus d)
                val  n  alEd  d = t et.ed Control.collect {
                  case Ed Control.Ed (ed ) => ed . n  alT et d
                }
                 f ( n  alEd  d.conta ns(request.cascadedFromT et d)) {
                  cascadeEd DeleteT ets. ncr()
                }
                 f (t etS ce d.conta ns(request.cascadedFromT et d)
                  ||  n  alEd  d.conta ns(request.cascadedFromT et d)) {
                  cascadedDeleteT etS ceMatch. ncr()
                  val deleteEvent =
                    DeleteT et.Event(
                      t et = t et,
                      t  stamp = T  .now,
                      user = user,
                      byUser d = contextV e r.flatMap(_.user d),
                      cascadedFromT et d = So (request.cascadedFromT et d),
                      aud Passthrough = request.aud Passthrough,
                       sUserErasure = false,
                      // cascaded deletes of ret ets or ed s have not been through a bouncer flow,
                      // so are not cons dered to be "bounce deleted".
                       sBounceDelete = false,
                       sLastQuoteOfQuoter =  sLastQuoteOfQuoter,
                       sAdm nDelete = false
                    )
                  t etStore
                    .deleteT et(deleteEvent)
                    .onFa lure { _ =>
                       f ( n  alEd  d.conta ns(request.cascadedFromT et d)) {
                        cascadeEd DeleteFa lures. ncr()
                      }
                    }
                } else {
                  cascadedDeleteT etS ceM smatch. ncr()
                  log.warn(
                    Seq(
                      "cascaded_from_t et_ d_s ce_m smatch",
                      request.t et d,
                      request.cascadedFromT et d,
                      t etS ce d.orElse( n  alEd  d).getOrElse("-")
                    ).mkStr ng("\t")
                  )
                  Future.Done
                }
            }
      }
      .onFa lure(_ => cascadedDeleteT etFa lures. ncr())
  }

  // G ven a l st of ed  T et  ds and a user  d, f nd t  ret et  ds of those ed   ds from t  g ven user
  pr vate def ed T et dRet etsFromUser(
    ed T et ds: Seq[T et d],
    byUser d: User d
  ): Future[Seq[T et d]] = {
     f (ed T et ds. sEmpty) {
      Future.value(Seq())
    } else {
      getPerspect ves(
        Seq(tls.Perspect veQuery(byUser d, ed T et ds))
      ).map { res: Seq[Perspect veResult] =>
        res. adOpt on.toSeq
          .flatMap(_.perspect ves.flatMap(_.ret et d))
      }
    }
  }

  /* T  funct on  s called from three places -
   * 1. W n T etyp e gets a request to ret et t  latest vers on of an ed  cha n, all t 
   * prev ous rev sons should be unret eted.
   *  .e. On Ret et of t  latest t et - unret ets all t  prev ous rev s ons for t  user.
   * - create A
   * - ret et A'(ret et of A)
   * - create ed  B(ed  of A)
   * - ret et B' => Deletes A'
   *
   * 2. W n T etyp e gets an unret et request for a s ce t et that  s an ed  t et, all
   * t  vers ons of t  ed  cha n  s ret eted.
   *  .e. On unret et of any vers on  n t  ed  cha n - unret ets all t  rev s ons for t  user
   * - create A
   * - ret et A'
   * - create B
   * - unret et B => Deletes A' (& also any B'  f   ex sted)
   *
   * 3. W n T etyp e gets a delete request for a ret et, say A1. &  f A happens to t  s ce
   * t et for A1 &  f A  s an ed  t et, t n t  ent re ed  cha n should be unret eted & not
   * A.  .e. On delete of a ret et - unret et all t  rev s ons for t  user.
   * - create A
   * - ret et A'
   * - create B
   * - delete A' => Deletes A' (& also any B'  f   ex sted)
   *
   * T  follow ng funct on has two fa lure scenar os -
   *  . w n   fa ls to get perspect ves of any of t  ed  t ets.
   *   . t  delet on of any of t  ret ets of t se ed s fa l.
   *
   *  n e  r of t  scenar o,   fa l t  ent re request & t  error bubbles up to t  top.
   * Note: T  above unret et of ed s only happens for t  current user.
   *  n normal c rcumstances, a max mum of one T et  n t  ed  cha n w ll have been ret eted,
   * but   don't know wh ch one   was. Add  onally, t re may be c rcumstances w re
   * unret et fa led, and   end up w h mult ple vers ons ret eted. For t se reasons,
   *   always unret et all t  rev s ons (except for `excludedT et d`).
   * T   s a no-op  f none of t se vers ons have been ret eted.
   * */
  overr de def unret etEd s(
    optEd Control: Opt on[Ed Control],
    excludedT et d: T et d,
    byUser d: User d
  ): Future[Un ] = {

    val ed T et ds: Seq[T et d] =
      Ed ControlUt l.getEd T et ds(optEd Control).get().f lter(_ != excludedT et d)

    (ed T et dRet etsFromUser(ed T et ds, byUser d).flatMap { t et ds =>
       f (t et ds.nonEmpty) {
        deleteT ets(
          DeleteT etsRequest(t et ds = t et ds, byUser d = So (byUser d)),
           sUnret etEd s = true
        )
      } else {
        Future.N l
      }
    }).un 
  }
}
