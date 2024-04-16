package com.tw ter.fr gate.pushserv ce.store

 mport com.tw ter.explore_ranker.thr ftscala.ExploreRanker
 mport com.tw ter.explore_ranker.thr ftscala.ExploreRankerResponse
 mport com.tw ter.explore_ranker.thr ftscala.ExploreRankerRequest
 mport com.tw ter.storehaus.ReadableStore
 mport com.tw ter.ut l.Future

/** A Store for V deo T et Recom ndat ons from Explore
 *
 * @param exploreRankerServ ce
 */
case class ExploreRankerStore(exploreRankerServ ce: ExploreRanker. thodPerEndpo nt)
    extends ReadableStore[ExploreRankerRequest, ExploreRankerResponse] {

  /**  thod to get v deo recom ndat ons
   *
   * @param request explore ranker request object
   * @return
   */
  overr de def get(
    request: ExploreRankerRequest
  ): Future[Opt on[ExploreRankerResponse]] = {
    exploreRankerServ ce.getRankedResults(request).map { response =>
      So (response)
    }
  }
}
