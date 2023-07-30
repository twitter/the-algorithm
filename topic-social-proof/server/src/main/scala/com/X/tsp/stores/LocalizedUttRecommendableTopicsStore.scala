package com.X.tsp.stores

import com.X.storehaus.ReadableStore
import com.X.topiclisting.FollowableTopicProductId
import com.X.topiclisting.ProductId
import com.X.topiclisting.SemanticCoreEntityId
import com.X.topiclisting.TopicListingViewerContext
import com.X.topiclisting.utt.UttLocalization
import com.X.util.Future

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
