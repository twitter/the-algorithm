package com.twitter.home_mixer.model.request

import com.twitter.dspbidder.commons.thriftscala.DspClientContext
import com.twitter.product_mixer.core.model.marshalling.request.ProductContext

case class FollowingProductContext(
  deviceContext: Option[DeviceContext],
  seenTweetIds: Option[Seq[Long]],
  dspClientContext: Option[DspClientContext])
    extends ProductContext

case class ForYouProductContext(
  deviceContext: Option[DeviceContext],
  seenTweetIds: Option[Seq[Long]],
  dspClientContext: Option[DspClientContext],
  pushToHomeTweetId: Option[Long])
    extends ProductContext

case class ScoredTweetsProductContext(
  deviceContext: Option[DeviceContext],
  seenTweetIds: Option[Seq[Long]],
  servedTweetIds: Option[Seq[Long]],
  backfillTweetIds: Option[Seq[Long]])
    extends ProductContext

case class ListTweetsProductContext(
  listId: Long,
  deviceContext: Option[DeviceContext],
  dspClientContext: Option[DspClientContext])
    extends ProductContext

case class ListRecommendedUsersProductContext(
  listId: Long,
  selectedUserIds: Option[Seq[Long]],
  excludedUserIds: Option[Seq[Long]],
  listName: Option[String])
    extends ProductContext

case class SubscribedProductContext(
  deviceContext: Option[DeviceContext],
  seenTweetIds: Option[Seq[Long]])
    extends ProductContext
