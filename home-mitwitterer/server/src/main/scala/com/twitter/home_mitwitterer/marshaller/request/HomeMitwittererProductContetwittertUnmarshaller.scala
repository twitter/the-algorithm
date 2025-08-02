package com.twitter.home_mitwitterer.marshaller.request

import com.twitter.home_mitwitterer.model.request.FollowingProductContetwittert
import com.twitter.home_mitwitterer.model.request.ForYouProductContetwittert
import com.twitter.home_mitwitterer.model.request.ListRecommendedUsersProductContetwittert
import com.twitter.home_mitwitterer.model.request.ListTweetsProductContetwittert
import com.twitter.home_mitwitterer.model.request.ScoredTweetsProductContetwittert
import com.twitter.home_mitwitterer.model.request.SubscribedProductContetwittert
import com.twitter.home_mitwitterer.{thriftscala => t}
import com.twitter.product_mitwitterer.core.model.marshalling.request.ProductContetwittert
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class HomeMitwittererProductContetwittertUnmarshaller @Inject() (
  deviceContetwittertUnmarshaller: DeviceContetwittertUnmarshaller) {

  def apply(productContetwittert: t.ProductContetwittert): ProductContetwittert = productContetwittert match {
    case t.ProductContetwittert.Following(p) =>
      FollowingProductContetwittert(
        deviceContetwittert = p.deviceContetwittert.map(deviceContetwittertUnmarshaller(_)),
        seenTweetIds = p.seenTweetIds,
        dspClientContetwittert = p.dspClientContetwittert
      )
    case t.ProductContetwittert.ForYou(p) =>
      ForYouProductContetwittert(
        deviceContetwittert = p.deviceContetwittert.map(deviceContetwittertUnmarshaller(_)),
        seenTweetIds = p.seenTweetIds,
        dspClientContetwittert = p.dspClientContetwittert,
        pushToHomeTweetId = p.pushToHomeTweetId
      )
    case t.ProductContetwittert.ListManagement(p) =>
      throw new UnsupportedOperationEtwitterception(s"This product is no longer used")
    case t.ProductContetwittert.ScoredTweets(p) =>
      ScoredTweetsProductContetwittert(
        deviceContetwittert = p.deviceContetwittert.map(deviceContetwittertUnmarshaller(_)),
        seenTweetIds = p.seenTweetIds,
        servedTweetIds = p.servedTweetIds,
        backfillTweetIds = p.backfillTweetIds
      )
    case t.ProductContetwittert.ListTweets(p) =>
      ListTweetsProductContetwittert(
        listId = p.listId,
        deviceContetwittert = p.deviceContetwittert.map(deviceContetwittertUnmarshaller(_)),
        dspClientContetwittert = p.dspClientContetwittert
      )
    case t.ProductContetwittert.ListRecommendedUsers(p) =>
      ListRecommendedUsersProductContetwittert(
        listId = p.listId,
        selectedUserIds = p.selectedUserIds,
        etwittercludedUserIds = p.etwittercludedUserIds,
        listName = p.listName
      )
    case t.ProductContetwittert.Subscribed(p) =>
      SubscribedProductContetwittert(
        deviceContetwittert = p.deviceContetwittert.map(deviceContetwittertUnmarshaller(_)),
        seenTweetIds = p.seenTweetIds,
      )
    case t.ProductContetwittert.UnknownUnionField(field) =>
      throw new UnsupportedOperationEtwitterception(s"Unknown display contetwittert: ${field.field.name}")
  }
}
