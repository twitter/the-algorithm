package com.X.frigate.pushservice.store

import com.X.interests_discovery.thriftscala.InterestsDiscoveryService
import com.X.interests_discovery.thriftscala.RecommendedListsRequest
import com.X.interests_discovery.thriftscala.RecommendedListsResponse
import com.X.storehaus.ReadableStore
import com.X.util.Future

case class InterestDiscoveryStore(
  client: InterestsDiscoveryService.MethodPerEndpoint)
    extends ReadableStore[RecommendedListsRequest, RecommendedListsResponse] {

  override def get(request: RecommendedListsRequest): Future[Option[RecommendedListsResponse]] = {
    client.getListRecos(request).map(Some(_))
  }
}
