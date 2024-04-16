package com.tw ter.follow_recom ndat ons.common.cand date_s ces.user_user_graph

 mport com.tw ter.f nagle.stats.Counter
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.follow_recom ndat ons.common.constants.Gu ceNa dConstants
 mport com.tw ter.follow_recom ndat ons.common.models._
 mport com.tw ter. rm .model.Algor hm
 mport com.tw ter.product_m xer.core.funct onal_component.cand date_s ce.Cand dateS ce
 mport com.tw ter.product_m xer.core.model.common. dent f er.Cand dateS ce dent f er
 mport com.tw ter.product_m xer.core.model.marshall ng.request.HasCl entContext
 mport com.tw ter.recos.recos_common.thr ftscala.UserSoc alProofType
 mport com.tw ter.recos.user_user_graph.thr ftscala.Recom ndUserD splayLocat on
 mport com.tw ter.recos.user_user_graph.thr ftscala.Recom ndUserRequest
 mport com.tw ter.recos.user_user_graph.thr ftscala.Recom ndUserResponse
 mport com.tw ter.recos.user_user_graph.thr ftscala.Recom ndedUser
 mport com.tw ter.st ch.St ch
 mport com.tw ter.strato.cl ent.Fetc r
 mport com.tw ter.t  l nes.conf gap .HasParams
 mport javax. nject. nject
 mport javax. nject.Na d
 mport javax. nject.S ngleton

@S ngleton
class UserUserGraphCand dateS ce @ nject() (
  @Na d(Gu ceNa dConstants.USER_USER_GRAPH_FETCHER)
  fetc r: Fetc r[Recom ndUserRequest, Un , Recom ndUserResponse],
  statsRece ver: StatsRece ver)
    extends Cand dateS ce[
      UserUserGraphCand dateS ce.Target,
      Cand dateUser
    ] {

  overr de val  dent f er: Cand dateS ce dent f er = UserUserGraphCand dateS ce. dent f er
  val stats: StatsRece ver = statsRece ver.scope("UserUserGraph")
  val requestCounter: Counter = stats.counter("requests")

  overr de def apply(
    target: UserUserGraphCand dateS ce.Target
  ): St ch[Seq[Cand dateUser]] = {
     f (target.params(UserUserGraphParams.UserUserGraphCand dateS ceEnabled n  ghtMap)) {
      requestCounter. ncr()
      bu ldRecom ndUserRequest(target)
        .map { request =>
          fetc r
            .fetch(request)
            .map(_.v)
            .map { responseOpt =>
              responseOpt
                .map { response =>
                  response.recom ndedUsers
                    .sortBy(-_.score)
                    .map(convertToCand dateUsers)
                    .map(_.w hCand dateS ce( dent f er))
                }.getOrElse(N l)
            }
        }.getOrElse(St ch.N l)
    } else {
      St ch.N l
    }
  }

  pr vate[t ] def bu ldRecom ndUserRequest(
    target: UserUserGraphCand dateS ce.Target
  ): Opt on[Recom ndUserRequest] = {
    (target.getOpt onalUser d, target.recentFollo dUser ds) match {
      case (So (user d), So (recentFollo dUser ds)) =>
        // use recentFollo dUser ds as seeds for  n  al exper  nt
        val seedsW h  ghts: Map[Long, Double] = recentFollo dUser ds.map {
          recentFollo dUser d =>
            recentFollo dUser d -> UserUserGraphCand dateS ce.DefaultSeed  ght
        }.toMap
        val request = Recom ndUserRequest(
          requester d = user d,
          d splayLocat on = UserUserGraphCand dateS ce.D splayLocat on,
          seedsW h  ghts = seedsW h  ghts,
          excludedUser ds = So (target.excludedUser ds),
          maxNumResults = So (target.params.get nt(UserUserGraphParams.MaxCand datesToReturn)),
          maxNumSoc alProofs = So (UserUserGraphCand dateS ce.MaxNumSoc alProofs),
          m nUserPerSoc alProof = So (UserUserGraphCand dateS ce.M nUserPerSoc alProof),
          soc alProofTypes = So (Seq(UserUserGraphCand dateS ce.Soc alProofType))
        )
        So (request)
      case _ => None
    }
  }

  pr vate[t ] def convertToCand dateUsers(
    recom ndedUser: Recom ndedUser
  ): Cand dateUser = {
    val soc alProofUser ds =
      recom ndedUser.soc alProofs.getOrElse(UserUserGraphCand dateS ce.Soc alProofType, N l)
    val reasonOpt =  f (soc alProofUser ds.nonEmpty) {
      So (
        Reason(
          So (AccountProof(followProof =
            So (FollowProof(soc alProofUser ds, soc alProofUser ds.s ze)))))
      )
    } else {
      None
    }
    Cand dateUser(
       d = recom ndedUser.user d,
      score = So (recom ndedUser.score),
      reason = reasonOpt)
  }
}

object UserUserGraphCand dateS ce {
  type Target = HasParams
    w h HasCl entContext
    w h HasRecentFollo dUser ds
    w h HasExcludedUser ds

  val  dent f er: Cand dateS ce dent f er = Cand dateS ce dent f er(
    Algor hm.UserUserGraph.toStr ng)
  //Use Ho T  l ne for exper  nt
  val D splayLocat on: Recom ndUserD splayLocat on = Recom ndUserD splayLocat on.Ho T  L ne

  //Default params used  n Mag cRecs
  val DefaultSeed  ght: Double = 1.0
  val Soc alProofType = UserSoc alProofType.Follow
  val MaxNumSoc alProofs = 10
  val M nUserPerSoc alProof: Map[UserSoc alProofType,  nt] =
    Map[UserSoc alProofType,  nt]((Soc alProofType, 2))
}
