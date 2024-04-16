package com.tw ter.t etyp e
package repos ory

 mport com.tw ter.g zmoduck.thr ftscala.LookupContext
 mport com.tw ter.g zmoduck.thr ftscala.UserResponseState
 mport com.tw ter.g zmoduck.thr ftscala.UserResult
 mport com.tw ter.servo.cac .ScopedCac Key
 mport com.tw ter.servo.json.syntax._
 mport com.tw ter.spam.rtf.thr ftscala.SafetyLevel
 mport com.tw ter.st ch.NotFound
 mport com.tw ter.st ch.SeqGroup
 mport com.tw ter.st ch.St ch
 mport com.tw ter.st ch.compat.LegacySeqGroup
 mport com.tw ter.t etyp e.backends.G zmoduck
 mport com.tw ter.t etyp e.core._
 mport com.tw ter.ut l.Base64Long.toBase64
 mport com.tw ter.ut l.logg ng.Logger
 mport com.tw ter.v s b l y.thr ftscala.UserV s b l yResult
 mport scala.ut l.control.NoStackTrace

sealed tra  UserKey

object UserKey {
  def by d(user d: User d): UserKey = User dKey(user d)
  def byScreenNa (screenNa : Str ng): UserKey = ScreenNa Key.toLo rCase(screenNa )
  def apply(user d: User d): UserKey = User dKey(user d)
  def apply(screenNa : Str ng): UserKey = ScreenNa Key.toLo rCase(screenNa )
}

case class User dKey(user d: User d)
    extends ScopedCac Key("t", "usr", 1, " d", toBase64(user d))
    w h UserKey

object ScreenNa Key {
  def toLo rCase(screenNa : Str ng): ScreenNa Key = ScreenNa Key(screenNa .toLo rCase)
}

/**
 * Use UserKey.apply(Str ng)  nstead of ScreenNa Key(Str ng) to construct a key,
 * as   w ll down-case t  screen-na  to better ut l ze t  user cac .
 */
case class ScreenNa Key pr vate (screenNa : Str ng)
    extends ScopedCac Key("t", "usr", 1, "sn", screenNa )
    w h UserKey

/**
 * A set of flags, used  n UserQuery, wh ch control w t r to  nclude or f lter out
 * users  n var ous non-standard states.
 */
case class UserV s b l y(
  f lterProtected: Boolean,
  f lterSuspended: Boolean,
  f lterDeact vated: Boolean,
  f lterOffboardedAndErased: Boolean,
  f lterNoScreenNa : Boolean,
  f lterPer scope: Boolean,
  f lterSoft: Boolean)

object UserV s b l y {

  /**
   * No f lter ng, can see every user that g zmoduck can return.
   */
  val All: UserV s b l y = UserV s b l y(
    f lterProtected = false,
    f lterSuspended = false,
    f lterDeact vated = false,
    f lterOffboardedAndErased = false,
    f lterNoScreenNa  = false,
    f lterPer scope = false,
    f lterSoft = false
  )

  /**
   * Only  ncludes users that would be v s ble to a non-logged  n user,
   * or a logged  n user w re t  follow ng graph  s c cked for
   * protected users.
   *
   * no-screen-na , soft, and per scope users are v s ble, but not
   *  nt onable.
   */
  val V s ble: UserV s b l y = UserV s b l y(
    f lterProtected = true,
    f lterSuspended = true,
    f lterDeact vated = true,
    f lterOffboardedAndErased = true,
    f lterNoScreenNa  = false,
    f lterPer scope = false,
    f lterSoft = false
  )

  val  d aTaggable: UserV s b l y = UserV s b l y(
    f lterProtected = false,
    f lterSuspended = true,
    f lterDeact vated = true,
    f lterOffboardedAndErased = true,
    f lterNoScreenNa  = true,
    f lterPer scope = true,
    f lterSoft = true
  )

  /**
   *  ncludes all  nt onable users (f lter deact vated/offboarded/erased/no-screen-na  users)
   */
  val  nt onable: UserV s b l y = UserV s b l y(
    f lterProtected = false,
    f lterSuspended = false,
    f lterDeact vated = false,
    f lterOffboardedAndErased = true,
    f lterNoScreenNa  = true,
    f lterPer scope = true,
    f lterSoft = true
  )
}

/**
 * T  `v s b l y` f eld  ncludes a set of flags that  nd cate w t r users  n
 * var ous non-standard states should be  ncluded  n t  `found` results, or f ltered
 * out.  By default, "f ltered out"  ans to treat t m as `notFound`, but  f `f lteredAsFa lure`
 *  s true, t n t  f ltered users w ll be  nd cated  n a [[UserF lteredFa lure]] result.
 */
case class UserQueryOpt ons(
  queryF elds: Set[UserF eld] = Set.empty,
  v s b l y: UserV s b l y,
  forUser d: Opt on[User d] = None,
  f lteredAsFa lure: Boolean = false,
  safetyLevel: Opt on[SafetyLevel] = None) {
  def toLookupContext: LookupContext =
    LookupContext(
       ncludeFa led = true,
      forUser d = forUser d,
       ncludeProtected = !v s b l y.f lterProtected,
       ncludeSuspended = !v s b l y.f lterSuspended,
       ncludeDeact vated = !v s b l y.f lterDeact vated,
       ncludeErased = !v s b l y.f lterOffboardedAndErased,
       ncludeNoScreenNa Users = !v s b l y.f lterNoScreenNa ,
       ncludePer scopeUsers = !v s b l y.f lterPer scope,
       ncludeSoftUsers = !v s b l y.f lterSoft,
       ncludeOffboarded = !v s b l y.f lterOffboardedAndErased,
      safetyLevel = safetyLevel
    )
}

case class UserLookupFa lure( ssage: Str ng, state: UserResponseState) extends Runt  Except on {
  overr de def get ssage(): Str ng =
    s"$ ssage: responseState = $state"
}

/**
 *  nd cates a fa lure due to t  user be ng f ltered.
 *
 * @see [[G zmoduckUserRepos ory.F lteredStates]]
 */
case class UserF lteredFa lure(state: UserResponseState, reason: Opt on[UserV s b l yResult])
    extends Except on
    w h NoStackTrace

object UserRepos ory {
  type Type = (UserKey, UserQueryOpt ons) => St ch[User]
  type Opt onal = (UserKey, UserQueryOpt ons) => St ch[Opt on[User]]

  def opt onal(repo: Type): Opt onal =
    (userKey, queryOpt ons) => repo(userKey, queryOpt ons).l ftNotFoundToOpt on

  def userGetter(
    userRepo: UserRepos ory.Opt onal,
    opts: UserQueryOpt ons
  ): UserKey => Future[Opt on[User]] =
    userKey => St ch.run(userRepo(userKey, opts))
}

object G zmoduckUserRepos ory {
  pr vate[t ] val log = Logger(getClass)

  def apply(
    getBy d: G zmoduck.GetBy d,
    getByScreenNa : G zmoduck.GetByScreenNa ,
    maxRequestS ze:  nt =  nt.MaxValue
  ): UserRepos ory.Type = {
    case class GetBy[K](
      opts: UserQueryOpt ons,
      get: ((LookupContext, Seq[K], Set[UserF eld])) => Future[Seq[UserResult]])
        extends SeqGroup[K, UserResult] {
      overr de def run(keys: Seq[K]): Future[Seq[Try[UserResult]]] =
        LegacySeqGroup.l ftToSeqTry(get((opts.toLookupContext, keys, opts.queryF elds)))
      overr de def maxS ze:  nt = maxRequestS ze
    }

    (key, opts) => {
      val result =
        key match {
          case User dKey( d) => St ch.call( d, GetBy(opts, getBy d))
          case ScreenNa Key(sn) => St ch.call(sn, GetBy(opts, getByScreenNa ))
        }

      result.flatMap(r => St ch.const(toTryUser(r, opts.f lteredAsFa lure)))
    }
  }

  pr vate def toTryUser(
    userResult: UserResult,
    f lteredAsFa lure: Boolean
  ): Try[User] =
    userResult.responseState match {
      case s  f s.forall(SuccessStates.conta ns(_)) =>
        userResult.user match {
          case So (u) =>
            Return(u)

          case None =>
            log.warn(
              s"User expected to be present, but not found  n:\n${userResult.prettyPr nt}"
            )
            // T  should never happen, but  f   does, treat   as t 
            // user be ng returned as NotFound.
            Throw(NotFound)
        }

      case So (s)  f NotFoundStates.conta ns(s) =>
        Throw(NotFound)

      case So (s)  f F lteredStates.conta ns(s) =>
        Throw( f (f lteredAsFa lure) UserF lteredFa lure(s, userResult.unsafeReason) else NotFound)

      case So (UserResponseState.Fa led) =>
        def lookupFa lure(msg: Str ng) =
          UserLookupFa lure(msg, UserResponseState.Fa led)

        Throw {
          userResult.fa lureReason
            .map { reason =>
              reason. nternalServerError
                .orElse {
                  reason.overCapac y.map { e =>
                    // Convert G zmoduck OverCapac y to T etyp e
                    // OverCapac y except on, expla n ng that   was
                    // propagated from G zmoduck.
                    OverCapac y(s"g zmoduck over capac y: ${e. ssage}")
                  }
                }
                .orElse(reason.unexpectedExcept on.map(lookupFa lure))
                .getOrElse(lookupFa lure("fa lureReason empty"))
            }
            .getOrElse(lookupFa lure("fa lureReason m ss ng"))
        }

      case So (unexpected) =>
        Throw(UserLookupFa lure("Unexpected response state", unexpected))
    }

  /**
   * States that   expect to correspond to a user be ng returned.
   */
  val SuccessStates: Set[UserResponseState] =
    Set[UserResponseState](
      UserResponseState.Found,
      UserResponseState.Part al
    )

  /**
   * States that always correspond to a NotFound response.
   */
  val NotFoundStates: Set[UserResponseState] =
    Set[UserResponseState](
      UserResponseState.NotFound,
      // T se are really f ltered out, but   treat t m as not found
      // s nce   don't have analogous f lter ng states for t ets.
      UserResponseState.Per scopeUser,
      UserResponseState.SoftUser,
      UserResponseState.NoScreenNa User
    )

  /**
   * Response states that correspond to a F lteredState
   */
  val F lteredStates: Set[UserResponseState] =
    Set(
      UserResponseState.Deact vatedUser,
      UserResponseState.OffboardedUser,
      UserResponseState.ErasedUser,
      UserResponseState.SuspendedUser,
      UserResponseState.ProtectedUser,
      UserResponseState.UnsafeUser
    )
}
