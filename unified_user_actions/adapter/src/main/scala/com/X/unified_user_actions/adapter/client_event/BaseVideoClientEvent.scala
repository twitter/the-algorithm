package com.X.unified_user_actions.adapter.client_event

import com.X.clientapp.thriftscala.LogEvent
import com.X.clientapp.thriftscala.{Item => LogEventItem}
import com.X.unified_user_actions.thriftscala._

abstract class BaseVideoClientEvent(actionType: ActionType)
    extends BaseClientEvent(actionType = actionType) {

  override def getUuaItem(
    ceItem: LogEventItem,
    logEvent: LogEvent
  ): Option[Item] = for {
    actionTweetId <- ceItem.id
    clientMediaEvent <- ceItem.clientMediaEvent
    sessionState <- clientMediaEvent.sessionState
    mediaIdentifier <- sessionState.contentVideoIdentifier
    mediaId <- VideoClientEventUtils.videoIdFromMediaIdentifier(mediaIdentifier)
    mediaDetails <- ceItem.mediaDetailsV2
    mediaItems <- mediaDetails.mediaItems
    videoMetadata <- VideoClientEventUtils.getVideoMetadata(
      mediaId,
      mediaItems,
      ceItem.cardDetails.flatMap(_.amplifyDetails))
  } yield {
    Item.TweetInfo(
      ClientEventCommonUtils
        .getBasicTweetInfo(
          actionTweetId = actionTweetId,
          ceItem = ceItem,
          ceNamespaceOpt = logEvent.eventNamespace)
        .copy(tweetActionInfo = Some(videoMetadata)))
  }
}
