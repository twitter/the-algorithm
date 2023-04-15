package com.twitter.tsp.stores

import com.twitter.storehaus.ReadableStore
import com.twitter.topiclisting.FollowableTopicProductId
import com.twitter.topiclisting.ProductId
import com.twitter.topiclisting.SemanticCoreEntityId
import com.twitter.topiclisting.TopicListingViewerContext
import com.twitter.topiclisting.utt.UttLocalization
import com.twitter.util.Future

case class LocalizedUttTopicNameRequest(
  productId: ProductId.Value,
  viewerContext: TopicListingViewerContext,
  enableInternationalTopics: Boolean)

class LocalizedUttRecommendableTopicsStore(uttLocalization: UttLocalization)
    extends ReadableStore[LocalizedUttTopicNameRequest, Set[SemanticCoreEntityId]] {

  override def get(
    request: LocalizedUttTopicNameRequest
  ): Future[Option[Set[SemanticCoreEntityId]]] = {
    uttLocalization
      .getRecommendableTopics(
        productId = request.productId,
        viewerContext = request.viewerContext,
        enableInternationalTopics = request.enableInternationalTopics,
        followableTopicProductId = FollowableTopicProductId.AllFollowable
      ).map { response => Some(response) }
  }
}
