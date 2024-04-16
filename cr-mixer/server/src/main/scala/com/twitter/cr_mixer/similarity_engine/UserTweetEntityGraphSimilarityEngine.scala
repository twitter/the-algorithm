package com.tw ter.cr_m xer.s m lar y_eng ne

 mport com.tw ter.recos.recos_common.thr ftscala.Soc alProofType
 mport com.tw ter.cr_m xer.model.S m lar yEng ne nfo
 mport com.tw ter.cr_m xer.model.T etW hScoreAndSoc alProof
 mport com.tw ter.cr_m xer.param.UtegT etGlobalParams
 mport com.tw ter.cr_m xer.thr ftscala.S m lar yEng neType
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.recos.user_t et_ent y_graph.thr ftscala.T etEnt yD splayLocat on
 mport com.tw ter.recos.user_t et_ent y_graph.thr ftscala.UserT etEnt yGraph
 mport com.tw ter.recos.user_t et_ent y_graph.thr ftscala.Recom ndT etEnt yRequest
 mport com.tw ter.recos.user_t et_ent y_graph.thr ftscala.Recom ndat onType
 mport com.tw ter.recos.user_t et_ent y_graph.thr ftscala.UserT etEnt yRecom ndat onUn on.T etRec
 mport com.tw ter.s mclusters_v2.common.User d
 mport com.tw ter.s mclusters_v2.common.T et d
 mport com.tw ter.storehaus.ReadableStore
 mport com.tw ter.t  l nes.conf gap 
 mport com.tw ter.ut l.Durat on
 mport com.tw ter.ut l.Future
 mport javax. nject.S ngleton

@S ngleton
case class UserT etEnt yGraphS m lar yEng ne(
  userT etEnt yGraph: UserT etEnt yGraph. thodPerEndpo nt,
  statsRece ver: StatsRece ver)
    extends ReadableStore[
      UserT etEnt yGraphS m lar yEng ne.Query,
      Seq[T etW hScoreAndSoc alProof]
    ] {

  overr de def get(
    query: UserT etEnt yGraphS m lar yEng ne.Query
  ): Future[Opt on[Seq[T etW hScoreAndSoc alProof]]] = {
    val recom ndT etEnt yRequest =
      Recom ndT etEnt yRequest(
        requester d = query.user d,
        d splayLocat on = T etEnt yD splayLocat on.Ho T  l ne,
        recom ndat onTypes = Seq(Recom ndat onType.T et),
        seedsW h  ghts = query.seedsW h  ghts,
        maxResultsByType = So (Map(Recom ndat onType.T et -> query.maxUtegCand dates)),
        maxT etAge nM ll s = So (query.maxT etAge. nM ll seconds),
        excludedT et ds = query.excludedT et ds,
        maxUserSoc alProofS ze = So (UserT etEnt yGraphS m lar yEng ne.MaxUserSoc alProofS ze),
        maxT etSoc alProofS ze =
          So (UserT etEnt yGraphS m lar yEng ne.MaxT etSoc alProofS ze),
        m nUserSoc alProofS zes = So (Map(Recom ndat onType.T et -> 1)),
        t etTypes = None,
        soc alProofTypes = query.soc alProofTypes,
        soc alProofTypeUn ons = None,
        t etAuthors = None,
        maxEngage ntAge nM ll s = None,
        excludedT etAuthors = None,
      )

    userT etEnt yGraph
      .recom ndT ets(recom ndT etEnt yRequest)
      .map { recom ndT etsResponse =>
        val cand dates = recom ndT etsResponse.recom ndat ons.flatMap {
          case T etRec(recom ndat on) =>
            So (
              T etW hScoreAndSoc alProof(
                recom ndat on.t et d,
                recom ndat on.score,
                recom ndat on.soc alProofByType.toMap))
          case _ => None
        }
        So (cand dates)
      }
  }
}

object UserT etEnt yGraphS m lar yEng ne {

  pr vate val MaxUserSoc alProofS ze = 10
  pr vate val MaxT etSoc alProofS ze = 10

  def toS m lar yEng ne nfo(score: Double): S m lar yEng ne nfo = {
    S m lar yEng ne nfo(
      s m lar yEng neType = S m lar yEng neType.Uteg,
      model d = None,
      score = So (score))
  }

  case class Query(
    user d: User d,
    seedsW h  ghts: Map[User d, Double],
    excludedT et ds: Opt on[Seq[Long]] = None,
    maxUtegCand dates:  nt,
    maxT etAge: Durat on,
    soc alProofTypes: Opt on[Seq[Soc alProofType]])

  def fromParams(
    user d: User d,
    seedsW h  ghts: Map[User d, Double],
    excludedT et ds: Opt on[Seq[T et d]] = None,
    params: conf gap .Params,
  ): Eng neQuery[Query] = {
    Eng neQuery(
      Query(
        user d = user d,
        seedsW h  ghts = seedsW h  ghts,
        excludedT et ds = excludedT et ds,
        maxUtegCand dates = params(UtegT etGlobalParams.MaxUtegCand datesToRequestParam),
        maxT etAge = params(UtegT etGlobalParams.Cand dateRefreshS nceT  OffsetH sParam),
        soc alProofTypes = So (Seq(Soc alProofType.Favor e))
      ),
      params
    )
  }
}
