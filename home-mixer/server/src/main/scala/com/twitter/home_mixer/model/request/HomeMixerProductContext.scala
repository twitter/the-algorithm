package com.twitter.home_mixer.model.request

import com.twitter.dspbidder.commons.thriftscala.DspClientContext
import com.twitter.home_mixer.model.signup.SignupSource
import com.twitter.home_mixer.{thriftscala => t}
import com.twitter.product_mixer.core.model.marshalling.request.ProductContext

case class FollowingProductContext(
  deviceContext: Option[DeviceContext],
  seenTweetIds: Option[Seq[Long]],
  dspClientContext: Option[DspClientContext])
    extends ProductContext

case class ForYouProductContext(
  deviceContext: Option[DeviceContext],
  seenTweetIds: Option[Seq[Long]],
  dspClientContext: Option[DspClientContext])
    extends ProductContext

case class ScoredTweetsProductContext(
  deviceContext: Option[DeviceContext],
  seenTweetIds: Option[Seq[Long]],
  servedTweetIds: Option[Seq[Long]],
  backfillTweetIds: Option[Seq[Long]],
  signupCountryCode: Option[String],
  allowForYouRecommendations: Option[Boolean],
  signupSource: Option[SignupSource],
  followerCount: Option[Int],
  servedAuthorIds: Option[Map[Long, Seq[Long]]] = None)
    extends ProductContext

case class ScoredVideoTweetsProductContext(
  deviceContext: Option[DeviceContext],
  seenTweetIds: Option[Seq[Long]],
  videoType: Option[t.VideoType],
  pinnedRelatedTweetIds: Option[Seq[Long]],
  scorePinnedTweetsOnly: Option[Boolean],
  immersiveClientMetadata: Option[t.ImmersiveClientMetadata])
    extends ProductContext

case class SubscribedProductContext(
  deviceContext: Option[DeviceContext],
  seenTweetIds: Option[Seq[Long]])
    extends ProductContext

case class HeavyRankerScoresProductContext(
  deviceContext: Option[DeviceContext],
  tweetIds: Option[Seq[Long]])
    extends ProductContext
