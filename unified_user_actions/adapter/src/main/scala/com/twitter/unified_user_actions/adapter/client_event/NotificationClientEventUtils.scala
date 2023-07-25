package com.twitter.unified_user_actions.adapter.client_event

import com.twitter.clientapp.thriftscala.LogEvent
import com.twitter.clientapp.thriftscala.{Item => LogEventItem}

object NotificationClientEventUtils {

  // Notification id for notification in the Notification Tab
  def getNotificationIdForNotificationTab(
    ceItem: LogEventItem
  ): Option[String] = {
    for {
      notificationTabDetails <- ceItem.notificationTabDetails
      clientEventMetaData <- notificationTabDetails.clientEventMetadata
      notificationId <- clientEventMetaData.upstreamId
    } yield {
      notificationId
    }
  }

  // Notification id for Push Notification
  def getNotificationIdForPushNotification(logEvent: LogEvent): Option[String] = for {
    pushNotificationDetails <- logEvent.notificationDetails
    notificationId <- pushNotificationDetails.impressionId
  } yield notificationId
}
