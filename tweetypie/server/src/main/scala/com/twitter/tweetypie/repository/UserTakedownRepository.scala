package com.tw ter.t etyp e
package repos ory

 mport com.tw ter.st ch.St ch
 mport com.tw ter.takedown.ut l.TakedownReasons
 mport com.tw ter.tseng.w hhold ng.thr ftscala.TakedownReason

/**
 * Query TakedownReason objects from g zmoduck
 *
 * No backf ll job has been completed so t re may ex st users that have a takedown
 * country_code w hout a correspond ng Unspec f edReason takedown_reason.  T refore,
 * read from both f elds and  rge  nto a set of TakedownReason, translat ng raw takedown
 * country_code  nto TakedownReason.Unspec f edReason(country_code).
 */
object UserTakedownRepos ory {
  type Type = User d => St ch[Set[TakedownReason]]

  val userQueryOpt ons: UserQueryOpt ons =
    UserQueryOpt ons(Set(UserF eld.Takedowns), UserV s b l y.All)

  def apply(userRepo: UserRepos ory.Type): UserTakedownRepos ory.Type =
    user d =>
      userRepo(UserKey(user d = user d), userQueryOpt ons)
        .map(_.takedowns.map(TakedownReasons.userTakedownsToReasons).getOrElse(Set.empty))
}
