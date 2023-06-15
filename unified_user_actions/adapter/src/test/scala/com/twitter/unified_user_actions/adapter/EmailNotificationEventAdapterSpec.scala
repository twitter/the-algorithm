package com.twitter.unified_user_actions.adapter

import com.twitter.inject.Test
import com.twitter.unified_user_actions.adapter.TestFixtures.EmailNotificationEventFixture
import com.twitter.unified_user_actions.adapter.email_notification_event.EmailNotificationEventAdapter
import com.twitter.util.Time

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
