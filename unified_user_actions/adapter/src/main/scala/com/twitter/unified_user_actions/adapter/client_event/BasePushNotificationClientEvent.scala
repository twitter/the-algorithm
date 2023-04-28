package com.twitter.unified_user_actions.adapter.client_event

import com.twitter.clientapp.thriftscala.LogEvent
import com.twitter.clientapp.thriftscala.{Item => LogEventItem}
import com.twitter.unified_user_actions.thriftscala._

abstract class BasePushNotificationClientEvent(actionType: ActionType)
    extends BaseClientEvent(actionType = actionType) {

  override def getUuaItem(
    ceItem: LogEventItem,
    logEvent: LogEvent
  ): Option[Item] = for {
    itemId <- ceItem.id
    notificationId <- NotificationClientEventUtils.getNotificationIdForPushNotification(logEvent)
  } yield {
    Item.NotificationInfo(
      NotificationInfo(
        actionNotificationId = notificationId,
        content = NotificationContent.TweetNotification(TweetNotification(tweetId = itemId))))
  }
}
