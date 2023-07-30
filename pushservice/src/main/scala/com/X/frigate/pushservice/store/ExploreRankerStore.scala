package com.X.frigate.pushservice.store

import com.X.explore_ranker.thriftscala.ExploreRanker
import com.X.explore_ranker.thriftscala.ExploreRankerResponse
import com.X.explore_ranker.thriftscala.ExploreRankerRequest
import com.X.storehaus.ReadableStore
import com.X.util.Future

/** A Store for Video Tweet Recommendations from Explore
 *
 * @param exploreRankerService
 */
case class ExploreRankerStore(exploreRankerService: ExploreRanker.MethodPerEndpoint)
    extends ReadableStore[ExploreRankerRequest, ExploreRankerResponse] {

  /** Method to get video recommendations
   *
   * @param request explore ranker request object
   * @return
   */
  override def get(
    request: ExploreRankerRequest
  ): Future[Option[ExploreRankerResponse]] = {
    exploreRankerService.getRankedResults(request).map { response =>
      Some(response)
    }
  }
}
