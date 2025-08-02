package com.twitter.home_mitwitterer.model.request

import com.twitter.dspbidder.commons.thriftscala.DspClientContetwittert
import com.twitter.product_mitwitterer.core.model.marshalling.request.ProductContetwittert

case class FollowingProductContetwittert(
  deviceContetwittert: Option[DeviceContetwittert],
  seenTweetIds: Option[Seq[Long]],
  dspClientContetwittert: Option[DspClientContetwittert])
    etwittertends ProductContetwittert

case class ForYouProductContetwittert(
  deviceContetwittert: Option[DeviceContetwittert],
  seenTweetIds: Option[Seq[Long]],
  dspClientContetwittert: Option[DspClientContetwittert],
  pushToHomeTweetId: Option[Long])
    etwittertends ProductContetwittert

case class ScoredTweetsProductContetwittert(
  deviceContetwittert: Option[DeviceContetwittert],
  seenTweetIds: Option[Seq[Long]],
  servedTweetIds: Option[Seq[Long]],
  backfillTweetIds: Option[Seq[Long]])
    etwittertends ProductContetwittert

case class ListTweetsProductContetwittert(
  listId: Long,
  deviceContetwittert: Option[DeviceContetwittert],
  dspClientContetwittert: Option[DspClientContetwittert])
    etwittertends ProductContetwittert

case class ListRecommendedUsersProductContetwittert(
  listId: Long,
  selectedUserIds: Option[Seq[Long]],
  etwittercludedUserIds: Option[Seq[Long]],
  listName: Option[String])
    etwittertends ProductContetwittert

case class SubscribedProductContetwittert(
  deviceContetwittert: Option[DeviceContetwittert],
  seenTweetIds: Option[Seq[Long]])
    etwittertends ProductContetwittert
