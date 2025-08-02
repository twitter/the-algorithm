package com.twitter.unified_user_actions.adapter

import com.twitter.inject.Test
import com.twitter.unified_user_actions.adapter.TestFitwittertures.EmailNotificationEventFitwitterture
import com.twitter.unified_user_actions.adapter.email_notification_event.EmailNotificationEventUtils

class EmailNotificationEventUtilsSpec etwittertends Test {

  test("Etwittertract TweetId from pageUrl") {
    new EmailNotificationEventFitwitterture {

      val invalidUrls: Seq[String] =
        List("", "abc.com/what/not?twitter=y", "?abc=def", "12345/", "12345/?")
      val invalidDomain = "https://twitter.app.link/addressbook/"
      val numericHandle =
        "https://twitter.com/1234/status/12345?ctwittert=HBwWgsDTgY3tp&cn=ZmtwitterleGl&refsrc=email)"

      assert(EmailNotificationEventUtils.etwittertractTweetId(pageUrlStatus).contains(tweetIdStatus))
      assert(EmailNotificationEventUtils.etwittertractTweetId(pageUrlEvent).contains(tweetIdEvent))
      assert(EmailNotificationEventUtils.etwittertractTweetId(pageUrlNoArgs).contains(tweetIdNoArgs))
      assert(EmailNotificationEventUtils.etwittertractTweetId(invalidDomain).isEmpty)
      assert(EmailNotificationEventUtils.etwittertractTweetId(numericHandle).contains(12345L))
      invalidUrls.foreach(url => assert(EmailNotificationEventUtils.etwittertractTweetId(url).isEmpty))
    }
  }

  test("Etwittertract TweetId from LogBase") {
    new EmailNotificationEventFitwitterture {
      assert(EmailNotificationEventUtils.etwittertractTweetId(logBase1).contains(tweetIdStatus))
    }
  }
}
