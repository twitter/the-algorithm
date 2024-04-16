package com.tw ter.t etyp e
package handler

 mport com.tw ter.servo.ut l.FutureArrow
 mport com.tw ter.st ch.St ch
 mport com.tw ter.t etyp e.core.F lteredState
 mport com.tw ter.t etyp e.core.T etHydrat onError
 mport com.tw ter.t etyp e.repos ory.ParentUser dRepos ory
 mport com.tw ter.t etyp e.storage.T etStorageCl ent.Undelete
 mport com.tw ter.t etyp e.storage.DeleteState
 mport com.tw ter.t etyp e.storage.DeletedT etResponse
 mport com.tw ter.t etyp e.storage.T etStorageCl ent
 mport com.tw ter.t etyp e.store.UndeleteT et
 mport com.tw ter.t etyp e.thr ftscala.UndeleteT etState.{Success => T etyp eSuccess, _}
 mport com.tw ter.t etyp e.thr ftscala._
 mport com.tw ter.t etyp e.thr ftscala.ent  es.Ent yExtractor
 mport scala.ut l.control.NoStackTrace

tra  UndeleteExcept on extends Except on w h NoStackTrace

/**
 * Except ons   return to t  user, th ngs that   don't expect to ever happen unless t re  s a
 * problem w h t  underly ng data  n Manhattan or a bug  n [[com.tw ter.t etyp e.storage.T etStorageCl ent]]
 */
object NoDeletedAtT  Except on extends UndeleteExcept on
object NoCreatedAtT  Except on extends UndeleteExcept on
object NoStatusW hSuccessExcept on extends UndeleteExcept on
object NoUser dW hT etExcept on extends UndeleteExcept on
object NoDeletedT etExcept on extends UndeleteExcept on
object SoftDeleteUser dNotFoundExcept on extends UndeleteExcept on

/**
 * represents a problem that   choose to return to t  user as a response state
 * rat r than as an except on.
 */
case class ResponseExcept on(state: UndeleteT etState) extends Except on w h NoStackTrace {
  def toResponse: UndeleteT etResponse = UndeleteT etResponse(state = state)
}

pr vate[t ] object SoftDeleteExp redExcept on extends ResponseExcept on(SoftDeleteExp red)
pr vate[t ] object BounceDeleteExcept on extends ResponseExcept on(T et sBounceDeleted)
pr vate[t ] object S ceT etNotFoundExcept on extends ResponseExcept on(S ceT etNotFound)
pr vate[t ] object S ceUserNotFoundExcept on extends ResponseExcept on(S ceUserNotFound)
pr vate[t ] object T etEx stsExcept on extends ResponseExcept on(T etAlreadyEx sts)
pr vate[t ] object T etNotFoundExcept on extends ResponseExcept on(T etNotFound)
pr vate[t ] object U13T etExcept on extends ResponseExcept on(T et sU13T et)
pr vate[t ] object UserNotFoundExcept on extends ResponseExcept on(UserNotFound)

/**
 * Undelete Notes:
 *
 *  f request.force  s set to true, t n t  undelete w ll take place even  f t  undeleted t et
 *  s already present  n Manhattan. T   s useful  f a t et was recently restored to t  backend,
 * but t  async act ons port on of t  undelete fa led and   want to retry t m.
 *
 * Before undelet ng t  t et   c ck  f  's a ret et,  n wh ch case   requ re that t  s ceT et
 * and s ceUser ex st.
 *
 * T ets can only be undeleted for N days w re N  s t  number of days before t ets marked w h
 * t  soft_delete_state flag are deleted permanently by t  cleanup job
 *
 */
object UndeleteT etHandler {

  type Type = FutureArrow[UndeleteT etRequest, UndeleteT etResponse]

  /** Extract an opt onal value  ns de a future or throw  f  's m ss ng. */
  def requ red[T](opt on: Future[Opt on[T]], ex: => Except on): Future[T] =
    opt on.flatMap {
      case None => Future.except on(ex)
      case So ( ) => Future.value( )
    }

  def apply(
    undelete: T etStorageCl ent.Undelete,
    t etEx sts: FutureArrow[T et d, Boolean],
    getUser: FutureArrow[User d, Opt on[User]],
    getDeletedT ets: T etStorageCl ent.GetDeletedT ets,
    parentUser dRepo: ParentUser dRepos ory.Type,
    save: FutureArrow[UndeleteT et.Event, T et]
  ): Type = {

    def getParentUser d(t et: T et): Future[Opt on[User d]] =
      St ch.run {
        parentUser dRepo(t et)
          .handle {
            case ParentUser dRepos ory.ParentT etNotFound( d) => None
          }
      }

    val ent yExtractor = Ent yExtractor.mutat onAll.endo

    val getDeletedT et: Long => Future[DeletedT etResponse] =
       d => St ch.run(getDeletedT ets(Seq( d)).map(_. ad))

    def getRequ redUser(user d: Opt on[User d]): Future[User] =
      user d match {
        case None => Future.except on(SoftDeleteUser dNotFoundExcept on)
        case So ( d) => requ red(getUser( d), UserNotFoundExcept on)
      }

    def getVal datedDeletedT et(
      t et d: T et d,
      allowNotDeleted: Boolean
    ): Future[DeletedT et] = {
       mport DeleteState._
      val deletedT et = getDeletedT et(t et d).map { response =>
        response.deleteState match {
          case SoftDeleted => response.t et
          // BounceDeleted t ets v olated Tw ter Rules and may not be undeleted
          case BounceDeleted => throw BounceDeleteExcept on
          case HardDeleted => throw SoftDeleteExp redExcept on
          case NotDeleted =>  f (allowNotDeleted) response.t et else throw T etEx stsExcept on
          case NotFound => throw T etNotFoundExcept on
        }
      }

      requ red(deletedT et, NoDeletedT etExcept on)
    }

    /**
     * Fetch t  s ce t et's user for a deleted share
     */
    def getS ceUser(share: Opt on[DeletedT etShare]): Future[Opt on[User]] =
      share match {
        case None => Future.value(None)
        case So (s) => requ red(getUser(s.s ceUser d), S ceUserNotFoundExcept on).map(So (_))
      }

    /**
     * Ensure that t  undelete response conta ns all t  requ red  nformat on to cont nue w h
     * t  t etyp e undelete.
     */
    def val dateUndeleteResponse(response: Undelete.Response, force: Boolean): Future[T et] =
      Future {
        (response.code, response.t et) match {
          case (Undelete.UndeleteResponseCode.NotCreated, _) => throw T etNotFoundExcept on
          case (Undelete.UndeleteResponseCode.BackupNotFound, _) => throw SoftDeleteExp redExcept on
          case (Undelete.UndeleteResponseCode.Success, None) => throw NoStatusW hSuccessExcept on
          case (Undelete.UndeleteResponseCode.Success, So (t et)) =>
            // arch vedAtM ll s  s requ red on t  response unless force  s present
            // or t  t et  s a ret et. ret ets have no favs or ret ets to clean up
            // of t  r own so t  or g nal deleted at t    s not needed
             f (response.arch vedAtM ll s. sEmpty && !force && ! sRet et(t et))
              throw NoDeletedAtT  Except on
            else
              t et
          case (code, _) => throw new Except on(s"Unknown UndeleteResponseCode $code")
        }
      }

    def enforceU13Compl ance(user: User, deletedT et: DeletedT et): Future[Un ] =
      Future.w n(U13Val dat onUt l.wasT etCreatedBeforeUserTurned13(user, deletedT et)) {
        throw U13T etExcept on
      }

    /**
     * Fetch requ red data and perform before/after val dat ons for undelete.
     *  f everyth ng looks good w h t  undelete, k ck off t  t etyp e undelete
     * event.
     */
    FutureArrow { request =>
      val hydrat onOpt ons = request.hydrat onOpt ons.getOrElse(Wr ePathHydrat onOpt ons())
      val force = request.force.getOrElse(false)
      val t et d = request.t et d

      (for {
        //   must be able to query t  t et from t  soft delete table
        deletedT et <- getVal datedDeletedT et(t et d, allowNotDeleted = force)

        //   always requ re t  user
        user <- getRequ redUser(deletedT et.user d)

        // Make sure  're not restor ng any u13 t ets.
        () <- enforceU13Compl ance(user, deletedT et)

        //  f a ret et, t n s ceUser  s requ red; s ceT et w ll be hydrated  n save()
        s ceUser <- getS ceUser(deletedT et.share)

        // val dat ons passed, perform t  undelete.
        undeleteResponse <- St ch.run(undelete(t et d))

        // val date t  response
        t et <- val dateUndeleteResponse(undeleteResponse, force)

        // Extract ent  es from t et text
        t etW hEnt  es = ent yExtractor(t et)

        //  f a ret et, get user  d of parent ret et
        parentUser d <- getParentUser d(t et)

        // undelet on was successful, hydrate t  t et and
        // k ck off t etyp e async undelete act ons
        hydratedT et <- save(
          UndeleteT et.Event(
            t et = t etW hEnt  es,
            user = user,
            t  stamp = T  .now,
            hydrateOpt ons = hydrat onOpt ons,
            deletedAt = undeleteResponse.arch vedAtM ll s.map(T  .fromM ll seconds),
            s ceUser = s ceUser,
            parentUser d = parentUser d
          )
        )
      } y eld {
        UndeleteT etResponse(T etyp eSuccess, So (hydratedT et))
      }).handle {
        case T etHydrat onError(_, So (F lteredState.Unava lable.S ceT etNotFound(_))) =>
          S ceT etNotFoundExcept on.toResponse
        case ex: ResponseExcept on =>
          ex.toResponse
      }
    }
  }
}
