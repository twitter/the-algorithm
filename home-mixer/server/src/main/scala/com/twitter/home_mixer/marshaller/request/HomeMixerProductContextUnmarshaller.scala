package com.twitter.home_mixer.marshaller.request

import com.twitter.home_mixer.model.request.FollowingProductContext
import com.twitter.home_mixer.model.request.ForYouProductContext
import com.twitter.home_mixer.model.request.HeavyRankerScoresProductContext
import com.twitter.home_mixer.model.request.ScoredTweetsProductContext
import com.twitter.home_mixer.model.request.ScoredVideoTweetsProductContext
import com.twitter.home_mixer.model.request.SubscribedProductContext
import com.twitter.home_mixer.{thriftscala => t}
import com.twitter.product_mixer.core.model.marshalling.request.ProductContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HomeMixerProductContextUnmarshaller @Inject() (
  deviceContextUnmarshaller: DeviceContextUnmarshaller) {

  def apply(productContext: t.ProductContext): ProductContext = productContext match {
    case t.ProductContext.Following(p) =>
      FollowingProductContext(
        deviceContext = p.deviceContext.map(deviceContextUnmarshaller(_)),
        seenTweetIds = p.seenTweetIds,
        dspClientContext = p.dspClientContext
      )
    case t.ProductContext.ForYou(p) =>
      ForYouProductContext(
        deviceContext = p.deviceContext.map(deviceContextUnmarshaller(_)),
        seenTweetIds = p.seenTweetIds,
        dspClientContext = p.dspClientContext
      )
    case t.ProductContext.ListManagement(p) =>
      throw new UnsupportedOperationException(s"This product is no longer used")
    case t.ProductContext.ScoredTweets(p) =>
      ScoredTweetsProductContext(
        deviceContext = p.deviceContext.map(deviceContextUnmarshaller(_)),
        seenTweetIds = p.seenTweetIds,
        servedTweetIds = p.servedTweetIds,
        backfillTweetIds = p.backfillTweetIds,
        signupCountryCode = p.signupCountryCode,
        allowForYouRecommendations = p.allowForYouRecommendations,
        signupSource = None, // not exposed in thrift interface
        followerCount = p.followerCount
      )
    case t.ProductContext.ScoredVideoTweets(p) =>
      ScoredVideoTweetsProductContext(
        deviceContext = p.deviceContext.map(deviceContextUnmarshaller(_)),
        seenTweetIds = p.seenTweetIds,
        videoType = p.videoType,
        pinnedRelatedTweetIds = p.pinnedRelatedTweetIds,
        scorePinnedTweetsOnly = p.scorePinnedTweetsOnly,
        immersiveClientMetadata = p.immersiveClientMetadata
      )
    case t.ProductContext.ListTweets(p) =>
      throw new UnsupportedOperationException(s"This product is no longer used")
    case t.ProductContext.ListRecommendedUsers(p) =>
      throw new UnsupportedOperationException(s"This product is no longer used")
    case t.ProductContext.Subscribed(p) =>
      SubscribedProductContext(
        deviceContext = p.deviceContext.map(deviceContextUnmarshaller(_)),
        seenTweetIds = p.seenTweetIds,
      )
    case t.ProductContext.HeavyRankerScores(p) =>
      HeavyRankerScoresProductContext(
        deviceContext = p.tweetScoringRequestContext
          .flatMap(_.deviceContext.map(deviceContextUnmarshaller(_))),
        tweetIds = p.tweetIds
      )
    case t.ProductContext.UnknownUnionField(field) =>
      throw new UnsupportedOperationException(s"Unknown display context: ${field.field.name}")
  }
}
