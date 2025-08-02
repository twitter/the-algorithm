package com.twitter.tsp.stores

import com.twitter.storehaus.ReadableStore
import com.twitter.topiclisting.FollowableTopicProductId
import com.twitter.topiclisting.ProductId
import com.twitter.topiclisting.SemanticCoreEntityId
import com.twitter.topiclisting.TopicListingViewerContetwittert
import com.twitter.topiclisting.utt.UttLocalization
import com.twitter.util.Future

case class LocalizedUttTopicNameRequest(
  productId: ProductId.Value,
  viewerContetwittert: TopicListingViewerContetwittert,
  enableInternationalTopics: Boolean)

class LocalizedUttRecommendableTopicsStore(uttLocalization: UttLocalization)
    etwittertends ReadableStore[LocalizedUttTopicNameRequest, Set[SemanticCoreEntityId]] {

  override def get(
    request: LocalizedUttTopicNameRequest
  ): Future[Option[Set[SemanticCoreEntityId]]] = {
    uttLocalization
      .getRecommendableTopics(
        productId = request.productId,
        viewerContetwittert = request.viewerContetwittert,
        enableInternationalTopics = request.enableInternationalTopics,
        followableTopicProductId = FollowableTopicProductId.AllFollowable
      ).map { response => Some(response) }
  }
}
