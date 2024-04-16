package com.tw ter.recos.user_t et_ent y_graph

 mport com.tw ter.f nagle.trac ng.Trace
 mport com.tw ter.logg ng.Logger
 mport com.tw ter.recos.user_t et_ent y_graph.thr ftscala._
 mport com.tw ter.ut l.Future

tra  Logg ngUserT etEnt yGraph extends thr ftscala.UserT etEnt yGraph. thodPerEndpo nt {
  pr vate[t ] val accessLog = Logger("access")

  abstract overr de def recom ndT ets(
    request: Recom ndT etEnt yRequest
  ): Future[Recom ndT etEnt yResponse] = {
    val t   = System.currentT  M ll s
    super.recom ndT ets(request) onSuccess { resp =>
      accessLog. nfo(
        "%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\tRecom ndT etResponse s ze: %s\t%s  n %d ms"
          .format(
            t  ,
            Trace. d.toStr ng(),
            request.requester d,
            request.d splayLocat on,
            request.recom ndat onTypes,
            request.maxResultsByType,
            request.excludedT et ds.map(_.take(5)),
            request.excludedT et ds.map(_.s ze),
            request.seedsW h  ghts.take(5),
            request.seedsW h  ghts.s ze,
            request.maxT etAge nM ll s,
            request.maxUserSoc alProofS ze,
            request.maxT etSoc alProofS ze,
            request.m nUserSoc alProofS zes,
            request.t etTypes,
            request.soc alProofTypes,
            request.soc alProofTypeUn ons,
            resp.recom ndat ons.s ze,
            resp.recom ndat ons.take(20).toL st map {
              case UserT etEnt yRecom ndat onUn on.T etRec(t etRec) =>
                (t etRec.t et d, t etRec.soc alProofByType.map { case (k, v) => (k, v.s ze) })
              case UserT etEnt yRecom ndat onUn on.HashtagRec(hashtagRec) =>
                (hashtagRec. d, hashtagRec.soc alProofByType.map { case (k, v) => (k, v.s ze) })
              case UserT etEnt yRecom ndat onUn on.UrlRec(urlRec) =>
                (urlRec. d, urlRec.soc alProofByType.map { case (k, v) => (k, v.s ze) })
              case _ =>
                throw new Except on("Unsupported recom ndat on types")
            },
            System.currentT  M ll s - t  
          )
      )
    } onFa lure { exc =>
      accessLog.error(
        "%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s  n %d ms".format(
          t  ,
          Trace. d.toStr ng(),
          request.requester d,
          request.d splayLocat on,
          request.recom ndat onTypes,
          request.maxResultsByType,
          request.excludedT et ds.map(_.take(5)),
          request.excludedT et ds.map(_.s ze),
          request.seedsW h  ghts.take(5),
          request.seedsW h  ghts.s ze,
          request.maxT etAge nM ll s,
          request.maxUserSoc alProofS ze,
          request.maxT etSoc alProofS ze,
          request.m nUserSoc alProofS zes,
          request.t etTypes,
          request.soc alProofTypes,
          request.soc alProofTypeUn ons,
          exc,
          System.currentT  M ll s - t  
        )
      )
    }
  }

  abstract overr de def f ndT etSoc alProofs(
    request: Soc alProofRequest
  ): Future[Soc alProofResponse] = {
    val t   = System.currentT  M ll s
    super.f ndT etSoc alProofs(request) onSuccess { resp =>
      accessLog. nfo(
        "%s\t%s\t%d\tResponse: %s\t n %d ms".format(
          Trace. d.toStr ng,
          request.requester d,
          request.seedsW h  ghts.s ze,
          resp.soc alProofResults.toL st,
          System.currentT  M ll s - t  
        )
      )
    } onFa lure { exc =>
      accessLog. nfo(
        "%s\t%s\t%d\tExcept on: %s\t n %d ms".format(
          Trace. d.toStr ng,
          request.requester d,
          request.seedsW h  ghts.s ze,
          exc,
          System.currentT  M ll s - t  
        )
      )
    }
  }
}
