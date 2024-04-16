package com.tw ter.t etyp e
package repos ory

 mport com.tw ter.g zmoduck.thr ftscala.UserResponseState
 mport com.tw ter.spam.rtf.thr ftscala.{SafetyLevel => Thr ftSafetyLevel}
 mport com.tw ter.st ch.NotFound
 mport com.tw ter.st ch.St ch
 mport com.tw ter.t etyp e.core._
 mport com.tw ter.t etyp e.thr ftscala.User dent y
 mport com.tw ter.v s b l y. nterfaces.t ets.UserUnava lableStateV s b l yL brary
 mport com.tw ter.v s b l y. nterfaces.t ets.UserUnava lableStateV s b l yRequest
 mport com.tw ter.v s b l y.models.SafetyLevel
 mport com.tw ter.v s b l y.models.UserUnava lableStateEnum
 mport com.tw ter.v s b l y.models.V e rContext
 mport com.tw ter.v s b l y.thr ftscala.UserV s b l yResult

/**
 * So  types of user (e.g. fr ct onless users) may not
 * have prof les, so a m ss ng User dent y may  an that t  user
 * does not ex st, or that t  user does not have a prof le.
 */
object User dent yRepos ory {
  type Type = UserKey => St ch[User dent y]

  def apply(repo: UserRepos ory.Type): Type = { key =>
    val opts = UserQueryOpt ons(Set(UserF eld.Prof le), UserV s b l y. nt onable)
    repo(key, opts)
      .map { user =>
        user.prof le.map { prof le =>
          User dent y(
             d = user. d,
            screenNa  = prof le.screenNa ,
            realNa  = prof le.na 
          )
        }
      }
      .lo rFromOpt on()
  }
}

object UserProtect onRepos ory {
  type Type = UserKey => St ch[Boolean]

  def apply(repo: UserRepos ory.Type): Type = {
    val opts = UserQueryOpt ons(Set(UserF eld.Safety), UserV s b l y.All)

    userKey =>
      repo(userKey, opts)
        .map(user => user.safety.map(_. sProtected))
        .lo rFromOpt on()
  }
}

/**
 * Query G zmoduck to c ck  f a user `forUser d` can see user `userKey`.
 *  f forUser d  s So (), t  w ll also c ck protected relat onsh p,
 *  f  's None,   w ll c ck ot rs as per UserV s b l y.V s ble pol cy  n
 * UserRepos ory.scala.  f forUser d  s None, t  doesn't ver fy any
 * relat onsh ps, v s b l y  s determ ned based solely on user's
 * propert es (eg. deact vated, suspended, etc)
 */
object UserV s b l yRepos ory {
  type Type = Query => St ch[Opt on[F lteredState.Unava lable]]

  case class Query(
    userKey: UserKey,
    forUser d: Opt on[User d],
    t et d: T et d,
     sRet et: Boolean,
     s nnerQuotedT et: Boolean,
    safetyLevel: Opt on[Thr ftSafetyLevel])

  def apply(
    repo: UserRepos ory.Type,
    userUnava lableAuthorStateV s b l yL brary: UserUnava lableStateV s b l yL brary.Type
  ): Type =
    query => {
      repo(
        query.userKey,
        UserQueryOpt ons(
          Set(),
          UserV s b l y.V s ble,
          forUser d = query.forUser d,
          f lteredAsFa lure = true,
          safetyLevel = query.safetyLevel
        )
      )
      //   don't actually care about t  response  re (User's data), only w t r
      //   was f ltered or not
        .map { case _ => None }
        .rescue {
          case fs: F lteredState.Unava lable => St ch.value(So (fs))
          case UserF lteredFa lure(state, reason) =>
            userUnava lableAuthorStateV s b l yL brary
              .apply(
                UserUnava lableStateV s b l yRequest(
                  query.safetyLevel
                    .map(SafetyLevel.fromThr ft).getOrElse(SafetyLevel.F lterDefault),
                  query.t et d,
                  V e rContext.fromContextW hV e r dFallback(query.forUser d),
                  toUserUnava lableState(state, reason),
                  query. sRet et,
                  query. s nnerQuotedT et
                )
              ).map(V s b l yResultToF lteredState.toF lteredStateUnava lable)
          case NotFound => St ch.value(So (F lteredState.Unava lable.Author.NotFound))
        }
    }

  def toUserUnava lableState(
    userResponseState: UserResponseState,
    userV s b l yResult: Opt on[UserV s b l yResult]
  ): UserUnava lableStateEnum = {
    (userResponseState, userV s b l yResult) match {
      case (UserResponseState.Deact vatedUser, _) => UserUnava lableStateEnum.Deact vated
      case (UserResponseState.OffboardedUser, _) => UserUnava lableStateEnum.Offboarded
      case (UserResponseState.ErasedUser, _) => UserUnava lableStateEnum.Erased
      case (UserResponseState.SuspendedUser, _) => UserUnava lableStateEnum.Suspended
      case (UserResponseState.ProtectedUser, _) => UserUnava lableStateEnum.Protected
      case (_, So (result)) => UserUnava lableStateEnum.F ltered(result)
      case _ => UserUnava lableStateEnum.Unava lable
    }
  }
}

object UserV ewRepos ory {
  type Type = Query => St ch[User]

  case class Query(
    userKey: UserKey,
    forUser d: Opt on[User d],
    v s b l y: UserV s b l y,
    queryF elds: Set[UserF eld] = Set(UserF eld.V ew))

  def apply(repo: UserRepos ory.Type): UserV ewRepos ory.Type =
    query =>
      repo(query.userKey, UserQueryOpt ons(query.queryF elds, query.v s b l y, query.forUser d))
}
