package com.twitter.frigate.pushservice.store

import com.twitter.etwitterplore_ranker.thriftscala.EtwitterploreRanker
import com.twitter.etwitterplore_ranker.thriftscala.EtwitterploreRankerResponse
import com.twitter.etwitterplore_ranker.thriftscala.EtwitterploreRankerRequest
import com.twitter.storehaus.ReadableStore
import com.twitter.util.Future

/** A Store for Video Tweet Recommendations from Etwitterplore
 *
 * @param etwitterploreRankerService
 */
case class EtwitterploreRankerStore(etwitterploreRankerService: EtwitterploreRanker.MethodPerEndpoint)
    etwittertends ReadableStore[EtwitterploreRankerRequest, EtwitterploreRankerResponse] {

  /** Method to get video recommendations
   *
   * @param request etwitterplore ranker request object
   * @return
   */
  override def get(
    request: EtwitterploreRankerRequest
  ): Future[Option[EtwitterploreRankerResponse]] = {
    etwitterploreRankerService.getRankedResults(request).map { response =>
      Some(response)
    }
  }
}
