package com.X.unified_user_actions.adapter.email_notification_event

import com.X.ibis.thriftscala.NotificationScribe
import com.X.logbase.thriftscala.LogBase
import com.X.unified_user_actions.adapter.common.AdapterUtils
import com.X.unified_user_actions.thriftscala.EventMetadata
import com.X.unified_user_actions.thriftscala.SourceLineage

object EmailNotificationEventUtils {

  /*
   * Extract TweetId from Logbase.page, here is a sample page below
   * https://X.com/i/events/1580827044245544962?cn=ZmxleGlibGVfcmVjcw%3D%3D&refsrc=email
   * */
  def extractTweetId(path: String): Option[Long] = {
    val ptn = raw".*/([0-9]+)\\??.*".r
    path match {
      case ptn(tweetId) =>
        Some(tweetId.toLong)
      case _ =>
        None
    }
  }

  def extractTweetId(logBase: LogBase): Option[Long] = logBase.page match {
    case Some(path) => extractTweetId(path)
    case None => None
  }

  def extractEventMetaData(scribe: NotificationScribe): EventMetadata =
    EventMetadata(
      sourceTimestampMs = scribe.timestamp,
      receivedTimestampMs = AdapterUtils.currentTimestampMs,
      sourceLineage = SourceLineage.EmailNotificationEvents,
      language = scribe.logBase.flatMap(_.language),
      countryCode = scribe.logBase.flatMap(_.country),
      clientAppId = scribe.logBase.flatMap(_.clientAppId),
    )
}
