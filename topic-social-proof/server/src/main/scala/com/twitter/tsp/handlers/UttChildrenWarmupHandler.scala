package com.twitter.tsp.handlers

import com.twitter.inject.utils.Handler
import com.twitter.topiclisting.FollowableTopicProductId
import com.twitter.topiclisting.ProductId
import com.twitter.topiclisting.TopicListingViewerContext
import com.twitter.topiclisting.utt.UttLocalization
import com.twitter.util.logging.Logging
import javax.inject.Inject
import javax.inject.Singleton

/** *
 * We configure Warmer to help warm up the cache hit rate under `CachedUttClient/get_utt_taxonomy/cache_hit_rate`
 * In uttLocalization.getRecommendableTopics, we fetch all topics exist in UTT, and yet the process
 * is in fact fetching the complete UTT tree struct (by calling getUttChildren recursively), which could take 1 sec
 * Once we have the topics, we stored them in in-memory cache, and the cache hit rate is > 99%
 *
 */
@Singleton
class UttChildrenWarmupHandler @Inject() (uttLocalization: UttLocalization)
    extends Handler
    with Logging {

  /** Executes the function of this handler. *   */
  override def handle(): Unit = {
    uttLocalization
      .getRecommendableTopics(
        productId = ProductId.Followable,
        viewerContext = TopicListingViewerContext(languageCode = Some("en")),
        enableInternationalTopics = true,
        followableTopicProductId = FollowableTopicProductId.AllFollowable
      )
      .onSuccess { result =>
        logger.info(s"successfully warmed up UttChildren. TopicId length = ${result.size}")
      }
      .onFailure { throwable =>
        logger.info(s"failed to warm up UttChildren. Throwable = ${throwable}")
      }
  }
}
