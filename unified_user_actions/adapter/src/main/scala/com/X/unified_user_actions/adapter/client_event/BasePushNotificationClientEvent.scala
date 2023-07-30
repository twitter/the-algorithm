package com.X.unified_user_actions.adapter.client_event

import com.X.clientapp.thriftscala.LogEvent
import com.X.clientapp.thriftscala.{Item => LogEventItem}
import com.X.unified_user_actions.thriftscala._

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
