package com.tw ter.recos.user_t et_graph.store

 mport com.tw ter.s mclusters_v2.common.User d
 mport com.tw ter.soc algraph.thr ftscala.EdgesRequest
 mport com.tw ter.soc algraph.thr ftscala.EdgesResult
 mport com.tw ter.soc algraph.thr ftscala.PageRequest
 mport com.tw ter.soc algraph.thr ftscala.Relat onsh pType
 mport com.tw ter.soc algraph.thr ftscala.SrcRelat onsh p
 mport com.tw ter.soc algraph.thr ftscala.Soc alGraphServ ce
 mport com.tw ter.storehaus.ReadableStore
 mport com.tw ter.ut l.Durat on
 mport com.tw ter.ut l.Future
 mport com.tw ter.ut l.T  

class UserRecentFollo rsStore(
  sgsCl ent: Soc alGraphServ ce. thodPerEndpo nt)
    extends ReadableStore[UserRecentFollo rsStore.Query, Seq[User d]] {

  overr de def get(key: UserRecentFollo rsStore.Query): Future[Opt on[Seq[User d]]] = {
    val edgeRequest = EdgesRequest(
      relat onsh p = SrcRelat onsh p(key.user d, Relat onsh pType.Follo dBy),
      // Could have a better guess at count w n k.maxAge != None
      pageRequest = So (PageRequest(count = key.maxResults))
    )

    val lookbackThresholdM ll s = key.maxAge
      .map(maxAge => (T  .now - maxAge). nM ll seconds)
      .getOrElse(0L)

    sgsCl ent
      .edges(Seq(edgeRequest))
      .map(_.flatMap {
        case EdgesResult(edges, _, _) =>
          edges.collect {
            case e  f e.createdAt >= lookbackThresholdM ll s =>
              e.target
          }
      })
      .map(So (_))
  }
}

object UserRecentFollo rsStore {
  case class Query(
    user d: User d,
    // maxResults -  f So (count),   return only t  `count` most recent follows
    maxResults: Opt on[ nt] = None,
    // maxAge -  f So (durat on), return only follows s nce `T  .now - durat on`
    maxAge: Opt on[Durat on] = None)
}
