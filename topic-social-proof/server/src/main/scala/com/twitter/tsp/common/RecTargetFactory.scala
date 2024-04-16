package com.tw ter.tsp.common

 mport com.tw ter.abdec der.Logg ngABDec der
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.fr gate.common.base.TargetUser
 mport com.tw ter.fr gate.common.cand date.TargetABDec der
 mport com.tw ter.fr gate.common.ut l.ABDec derW hOverr de
 mport com.tw ter.g zmoduck.thr ftscala.User
 mport com.tw ter.s mclusters_v2.common.User d
 mport com.tw ter.storehaus.ReadableStore
 mport com.tw ter.t  l nes.conf gap .Params
 mport com.tw ter.tsp.thr ftscala.Top cSoc alProofRequest
 mport com.tw ter.ut l.Future

case class DefaultRecTop cSoc alProofTarget(
  top cSoc alProofRequest: Top cSoc alProofRequest,
  target d: User d,
  user: Opt on[User],
  abDec der: ABDec derW hOverr de,
  params: Params
)(
   mpl c  statsRece ver: StatsRece ver)
    extends TargetUser
    w h Top cSoc alProofRecRequest
    w h TargetABDec der {
  overr de def globalStats: StatsRece ver = statsRece ver
  overr de val targetUser: Future[Opt on[User]] = Future.value(user)
}

tra  Top cSoc alProofRecRequest {
  tuc: TargetUser =>

  val top cSoc alProofRequest: Top cSoc alProofRequest
}

case class RecTargetFactory(
  abDec der: Logg ngABDec der,
  userStore: ReadableStore[User d, User],
  paramBu lder: ParamsBu lder,
  statsRece ver: StatsRece ver) {

  type RecTop cSoc alProofTarget = DefaultRecTop cSoc alProofTarget

  def bu ldRecTop cSoc alProofTarget(
    request: Top cSoc alProofRequest
  ): Future[RecTop cSoc alProofTarget] = {
    val user d = request.user d
    userStore.get(user d).map { userOpt =>
      val userRoles = userOpt.flatMap(_.roles.map(_.roles.toSet))

      val context = request.context.copy(user d = So (request.user d)) // overr de to make sure

      val params = paramBu lder
        .bu ldFromTop cL st ngV e rContext(So (context), request.d splayLocat on, userRoles)

      DefaultRecTop cSoc alProofTarget(
        request,
        user d,
        userOpt,
        ABDec derW hOverr de(abDec der, None)(statsRece ver),
        params
      )(statsRece ver)
    }
  }
}
