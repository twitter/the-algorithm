package com.twitter.frigate.pushservice.store

import com.twitter.interests_discovery.thriftscala.InterestsDiscoveryService
import com.twitter.interests_discovery.thriftscala.RecommendedListsRequest
import com.twitter.interests_discovery.thriftscala.RecommendedListsResponse
import com.twitter.storehaus.ReadableStore
import com.twitter.util.Future

case class InterestDiscoveryStore(
  client: InterestsDiscoveryService.MethodPerEndpoint)
    extends ReadableStore[RecommendedListsRequest, RecommendedListsResponse] {

  override def get(request: RecommendedListsRequest): Future[Option[RecommendedListsResponse]] = {
    client.getListRecos(request).map(Some(_))
  }
}
