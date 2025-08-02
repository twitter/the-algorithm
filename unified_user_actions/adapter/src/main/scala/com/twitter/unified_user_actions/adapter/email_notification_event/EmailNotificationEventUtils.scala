package com.twitter.unified_user_actions.adapter.email_notification_event

import com.twitter.ibis.thriftscala.NotificationScribe
import com.twitter.logbase.thriftscala.LogBase
import com.twitter.unified_user_actions.adapter.common.AdapterUtils
import com.twitter.unified_user_actions.thriftscala.EventMetadata
import com.twitter.unified_user_actions.thriftscala.SourceLineage

object EmailNotificationEventUtils {

  /*
   * Etwittertract TweetId from Logbase.page, here is a sample page below
   * https://twitter.com/i/events/1580827044245544962?cn=ZmtwitterleGlibGVfcmVjcw%3D%3D&refsrc=email
   * */
  def etwittertractTweetId(path: String): Option[Long] = {
    val ptn = raw".*/([0-9]+)\\??.*".r
    path match {
      case ptn(tweetId) =>
        Some(tweetId.toLong)
      case _ =>
        None
    }
  }

  def etwittertractTweetId(logBase: LogBase): Option[Long] = logBase.page match {
    case Some(path) => etwittertractTweetId(path)
    case None => None
  }

  def etwittertractEventMetaData(scribe: NotificationScribe): EventMetadata =
    EventMetadata(
      sourceTimestampMs = scribe.timestamp,
      receivedTimestampMs = AdapterUtils.currentTimestampMs,
      sourceLineage = SourceLineage.EmailNotificationEvents,
      language = scribe.logBase.flatMap(_.language),
      countryCode = scribe.logBase.flatMap(_.country),
      clientAppId = scribe.logBase.flatMap(_.clientAppId),
    )
}
