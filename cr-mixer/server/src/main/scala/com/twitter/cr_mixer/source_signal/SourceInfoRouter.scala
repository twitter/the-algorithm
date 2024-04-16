package com.tw ter.cr_m xer.s ce_s gnal

 mport com.tw ter.core_workflows.user_model.thr ftscala.UserState
 mport com.tw ter.cr_m xer.model.GraphS ce nfo
 mport com.tw ter.cr_m xer.model.S ce nfo
 mport com.tw ter.cr_m xer.s ce_s gnal.S ceFetc r.Fetc rQuery
 mport com.tw ter.cr_m xer.thr ftscala.S ceType
 mport com.tw ter.cr_m xer.thr ftscala.{Product => TProduct}
 mport com.tw ter.s mclusters_v2.common.User d
 mport com.tw ter.t  l nes.conf gap 
 mport com.tw ter.ut l.Future
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
case class S ce nfoRouter @ nject() (
  ussS ceS gnalFetc r: UssS ceS gnalFetc r,
  frsS ceS gnalFetc r: FrsS ceS gnalFetc r,
  frsS ceGraphFetc r: FrsS ceGraphFetc r,
  realGraphOonS ceGraphFetc r: RealGraphOonS ceGraphFetc r,
  realGraph nS ceGraphFetc r: RealGraph nS ceGraphFetc r,
) {

  def get(
    user d: User d,
    product: TProduct,
    userState: UserState,
    params: conf gap .Params
  ): Future[(Set[S ce nfo], Map[Str ng, Opt on[GraphS ce nfo]])] = {

    val fetc rQuery = Fetc rQuery(user d, product, userState, params)
    Future.jo n(
      getS ceS gnals(fetc rQuery),
      getS ceGraphs(fetc rQuery)
    )
  }

  pr vate def getS ceS gnals(
    fetc rQuery: Fetc rQuery
  ): Future[Set[S ce nfo]] = {
    Future
      .jo n(
        ussS ceS gnalFetc r.get(fetc rQuery),
        frsS ceS gnalFetc r.get(fetc rQuery)).map {
        case (ussS gnalsOpt, frsS gnalsOpt) =>
          (ussS gnalsOpt.getOrElse(Seq.empty) ++ frsS gnalsOpt.getOrElse(Seq.empty)).toSet
      }
  }

  pr vate def getS ceGraphs(
    fetc rQuery: Fetc rQuery
  ): Future[Map[Str ng, Opt on[GraphS ce nfo]]] = {

    Future
      .jo n(
        frsS ceGraphFetc r.get(fetc rQuery),
        realGraphOonS ceGraphFetc r.get(fetc rQuery),
        realGraph nS ceGraphFetc r.get(fetc rQuery)
      ).map {
        case (frsGraphOpt, realGraphOonGraphOpt, realGraph nGraphOpt) =>
          Map(
            S ceType.FollowRecom ndat on.na  -> frsGraphOpt,
            S ceType.RealGraphOon.na  -> realGraphOonGraphOpt,
            S ceType.RealGraph n.na  -> realGraph nGraphOpt,
          )
      }
  }
}
