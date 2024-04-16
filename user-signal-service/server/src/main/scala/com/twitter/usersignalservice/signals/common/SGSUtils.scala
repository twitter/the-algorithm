package com.tw ter.users gnalserv ce.s gnals
package common

 mport com.tw ter.s mclusters_v2.thr ftscala. nternal d
 mport com.tw ter.soc algraph.thr ftscala.EdgesRequest
 mport com.tw ter.soc algraph.thr ftscala.EdgesResult
 mport com.tw ter.soc algraph.thr ftscala.PageRequest
 mport com.tw ter.soc algraph.thr ftscala.Relat onsh pType
 mport com.tw ter.soc algraph.thr ftscala.Soc alGraphServ ce
 mport com.tw ter.soc algraph.thr ftscala.SrcRelat onsh p
 mport com.tw ter.tw stly.common.User d
 mport com.tw ter.users gnalserv ce.thr ftscala.S gnal
 mport com.tw ter.users gnalserv ce.thr ftscala.S gnalType
 mport com.tw ter.ut l.Durat on
 mport com.tw ter.ut l.Future
 mport com.tw ter.ut l.T  

object SGSUt ls {
  val MaxNumSoc alGraphS gnals = 200
  val MaxAge: Durat on = Durat on.fromDays(90)

  def getSGSRawS gnals(
    user d: User d,
    sgsCl ent: Soc alGraphServ ce. thodPerEndpo nt,
    relat onsh pType: Relat onsh pType,
    s gnalType: S gnalType,
  ): Future[Opt on[Seq[S gnal]]] = {
    val edgeRequest = EdgesRequest(
      relat onsh p = SrcRelat onsh p(user d, relat onsh pType),
      pageRequest = So (PageRequest(count = None))
    )
    val now = T  .now. nM ll seconds

    sgsCl ent
      .edges(Seq(edgeRequest))
      .map { sgsEdges =>
        sgsEdges.flatMap {
          case EdgesResult(edges, _, _) =>
            edges.collect {
              case edge  f edge.createdAt >= now - MaxAge. nM ll seconds =>
                S gnal(
                  s gnalType,
                  t  stamp = edge.createdAt,
                  target nternal d = So ( nternal d.User d(edge.target)))
            }
        }
      }
      .map { s gnals =>
        s gnals
          .take(MaxNumSoc alGraphS gnals)
          .groupBy(_.target nternal d)
          .mapValues(_.maxBy(_.t  stamp))
          .values
          .toSeq
          .sortBy(-_.t  stamp)
      }
      .map(So (_))
  }
}
