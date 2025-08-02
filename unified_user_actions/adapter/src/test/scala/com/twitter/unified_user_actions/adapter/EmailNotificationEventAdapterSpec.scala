package com.twitter.unified_user_actions.adapter

import com.twitter.inject.Test
import com.twitter.unified_user_actions.adapter.TestFitwittertures.EmailNotificationEventFitwitterture
import com.twitter.unified_user_actions.adapter.email_notification_event.EmailNotificationEventAdapter
import com.twitter.util.Time

class EmailNotificationEventAdapterSpec etwittertends Test {

  test("Notifications with click event") {
    new EmailNotificationEventFitwitterture {
      Time.withTimeAt(frozenTime) { _ =>
        val actual = EmailNotificationEventAdapter.adaptEvent(notificationEvent)
        assert(etwitterpectedUua == actual.head)
        assert(EmailNotificationEventAdapter.adaptEvent(notificationEventWOTweetId).isEmpty)
        assert(EmailNotificationEventAdapter.adaptEvent(notificationEventWOImpressionId).isEmpty)
      }
    }
  }
}
