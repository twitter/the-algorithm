package com.X.unified_user_actions.adapter

import com.X.inject.Test
import com.X.unified_user_actions.adapter.TestFixtures.EmailNotificationEventFixture
import com.X.unified_user_actions.adapter.email_notification_event.EmailNotificationEventAdapter
import com.X.util.Time

class EmailNotificationEventAdapterSpec extends Test {

  test("Notifications with click event") {
    new EmailNotificationEventFixture {
      Time.withTimeAt(frozenTime) { _ =>
        val actual = EmailNotificationEventAdapter.adaptEvent(notificationEvent)
        assert(expectedUua == actual.head)
        assert(EmailNotificationEventAdapter.adaptEvent(notificationEventWOTweetId).isEmpty)
        assert(EmailNotificationEventAdapter.adaptEvent(notificationEventWOImpressionId).isEmpty)
      }
    }
  }
}
