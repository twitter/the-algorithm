package com.twitter.unified_user_actions.adapter.client_event

import com.twitter.clientapp.thriftscala.{Item => LogEventItem}
import com.twitter.suggests.controller_data.home_tweets.thriftscala.HomeTweetsControllerData
import com.twitter.suggests.controller_data.home_tweets.thriftscala.HomeTweetsControllerDataAliases.V1Alias
import com.twitter.suggests.controller_data.thriftscala.ControllerData
import com.twitter.suggests.controller_data.v2.thriftscala.{ControllerData => ControllerDataV2}

object HomeInfoUtils {

  def getHomeTweetControllerDataV1(ceItem: LogEventItem): Option[V1Alias] = {
    ceItem.suggestionDetails
      .flatMap(_.decodedControllerData)
      .flatMap(_ match {
        case ControllerData.V2(
              ControllerDataV2.HomeTweets(
                HomeTweetsControllerData.V1(homeTweetsControllerDataV1)
              )) =>
          Some(homeTweetsControllerDataV1)
        case _ => None
      })
  }

  def getTraceId(ceItem: LogEventItem): Option[Long] =
    getHomeTweetControllerDataV1(ceItem).flatMap(_.traceId)

  def getSuggestType(ceItem: LogEventItem): Option[String] =
    ceItem.suggestionDetails.flatMap(_.suggestionType)

  def getRequestJoinId(ceItem: LogEventItem): Option[Long] =
    getHomeTweetControllerDataV1(ceItem).flatMap(_.requestJoinId)
}
