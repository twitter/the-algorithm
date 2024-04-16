package com.tw ter.cr_m xer.s m lar y_eng ne

 mport com.tw ter.cr_m xer.model.T etW hAuthor
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.search.earlyb rd.thr ftscala.Earlyb rdRequest
 mport com.tw ter.search.earlyb rd.thr ftscala.Earlyb rdResponseCode
 mport com.tw ter.search.earlyb rd.thr ftscala.Earlyb rdServ ce
 mport com.tw ter.s mclusters_v2.common.User d
 mport com.tw ter.storehaus.ReadableStore
 mport com.tw ter.ut l.Future

/**
 * T  tra   s a base tra  for Earlyb rd s m lar y eng nes. All Earlyb rd s m lar y
 * eng nes extend from   and overr de t  construct on  thod for Earlyb rdRequest
 */
tra  Earlyb rdS m lar yEng neBase[Earlyb rdSearchQuery]
    extends ReadableStore[Earlyb rdSearchQuery, Seq[T etW hAuthor]] {
  def earlyb rdSearchCl ent: Earlyb rdServ ce. thodPerEndpo nt

  def statsRece ver: StatsRece ver

  def getEarlyb rdRequest(query: Earlyb rdSearchQuery): Opt on[Earlyb rdRequest]

  overr de def get(query: Earlyb rdSearchQuery): Future[Opt on[Seq[T etW hAuthor]]] = {
    getEarlyb rdRequest(query)
      .map { earlyb rdRequest =>
        earlyb rdSearchCl ent
          .search(earlyb rdRequest).map { response =>
            response.responseCode match {
              case Earlyb rdResponseCode.Success =>
                val earlyb rdSearchResult =
                  response.searchResults
                    .map(
                      _.results
                        .map(searchResult =>
                          T etW hAuthor(
                            searchResult. d,
                            // fromUser d should be t re s nce  tadataOpt ons.getFromUser d = true
                            searchResult. tadata.map(_.fromUser d).getOrElse(0))).toSeq)
                statsRece ver.scope("result").stat("s ze").add(earlyb rdSearchResult.s ze)
                earlyb rdSearchResult
              case e =>
                statsRece ver.scope("fa lures").counter(e.getClass.getS mpleNa ). ncr()
                So (Seq.empty)
            }
          }
      }.getOrElse(Future.None)
  }
}

object Earlyb rdS m lar yEng neBase {
  tra  Earlyb rdSearchQuery {
    def seedUser ds: Seq[User d]
    def maxNumT ets:  nt
  }
}
