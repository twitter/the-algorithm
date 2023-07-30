package com.X.unified_user_actions.adapter.email_notification_event

import com.X.finagle.stats.NullStatsReceiver
import com.X.finagle.stats.StatsReceiver
import com.X.finatra.kafka.serde.UnKeyed
import com.X.ibis.thriftscala.NotificationScribe
import com.X.ibis.thriftscala.NotificationScribeType
import com.X.unified_user_actions.adapter.AbstractAdapter
import com.X.unified_user_actions.thriftscala.ActionType
import com.X.unified_user_actions.thriftscala.EmailNotificationInfo
import com.X.unified_user_actions.thriftscala.Item
import com.X.unified_user_actions.thriftscala.ProductSurface
import com.X.unified_user_actions.thriftscala.ProductSurfaceInfo
import com.X.unified_user_actions.thriftscala.TweetInfo
import com.X.unified_user_actions.thriftscala.UnifiedUserAction
import com.X.unified_user_actions.thriftscala.UserIdentifier

class EmailNotificationEventAdapter
    extends AbstractAdapter[NotificationScribe, UnKeyed, UnifiedUserAction] {
  import EmailNotificationEventAdapter._
  override def adaptOneToKeyedMany(
    input: NotificationScribe,
    statsReceiver: StatsReceiver = NullStatsReceiver
  ): Seq[(UnKeyed, UnifiedUserAction)] =
    adaptEvent(input).map { e => (UnKeyed, e) }
}

object EmailNotificationEventAdapter {

  def adaptEvent(scribe: NotificationScribe): Seq[UnifiedUserAction] = {
    Option(scribe).flatMap { e =>
      e.`type` match {
        case NotificationScribeType.Click =>
          val tweetIdOpt = e.logBase.flatMap(EmailNotificationEventUtils.extractTweetId)
          (tweetIdOpt, e.impressionId) match {
            case (Some(tweetId), Some(impressionId)) =>
              Some(
                UnifiedUserAction(
                  userIdentifier = UserIdentifier(userId = e.userId),
                  item = Item.TweetInfo(TweetInfo(actionTweetId = tweetId)),
                  actionType = ActionType.ClientTweetEmailClick,
                  eventMetadata = EmailNotificationEventUtils.extractEventMetaData(e),
                  productSurface = Some(ProductSurface.EmailNotification),
                  productSurfaceInfo = Some(
                    ProductSurfaceInfo.EmailNotificationInfo(
                      EmailNotificationInfo(notificationId = impressionId)))
                )
              )
            case _ => None
          }
        case _ => None
      }
    }.toSeq
  }
}
