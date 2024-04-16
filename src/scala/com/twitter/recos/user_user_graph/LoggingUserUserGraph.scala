package com.tw ter.recos.user_user_graph

 mport com.tw ter.logg ng.Logger
 mport com.tw ter.recos.user_user_graph.thr ftscala._
 mport com.tw ter.ut l.Future

tra  Logg ngUserUserGraph extends thr ftscala.UserUserGraph. thodPerEndpo nt {
  pr vate[t ] val accessLog = Logger("access")

  abstract overr de def recom ndUsers(
    request: Recom ndUserRequest
  ): Future[Recom ndUserResponse] = {
    val t   = System.currentT  M ll s
    super.recom ndUsers(request) onSuccess { resp =>
      val t  Taken = System.currentT  M ll s - t  
      val logText =
        s" n ${t  Taken}ms, recom ndUsers(${requestToStr ng(request)}), response ${responseToStr ng(resp)}"
      accessLog. nfo(logText)
    } onFa lure { exc =>
      val t  Taken = System.currentT  M ll s - t  
      val logText = s" n ${t  Taken}ms, recom ndUsers(${requestToStr ng(request)} returned error"
      accessLog.error(exc, logText)
    }
  }

  pr vate def requestToStr ng(request: Recom ndUserRequest): Str ng = {
    Seq(
      request.requester d,
      request.d splayLocat on,
      request.seedsW h  ghts.s ze,
      request.seedsW h  ghts.take(5),
      request.excludedUser ds.map(_.s ze).getOrElse(0),
      request.excludedUser ds.map(_.take(5)),
      request.maxNumResults,
      request.maxNumSoc alProofs,
      request.m nUserPerSoc alProof,
      request.soc alProofTypes,
      request.maxEdgeEngage ntAge nM ll s
    ).mkStr ng(",")
  }

  pr vate def responseToStr ng(response: Recom ndUserResponse): Str ng = {
    response.recom ndedUsers.toL st.map { recUser =>
      val soc alProof = recUser.soc alProofs.map {
        case (proofType, proofs) =>
          (proofType, proofs)
      }
      (recUser.user d, recUser.score, soc alProof)
    }.toStr ng
  }
}
