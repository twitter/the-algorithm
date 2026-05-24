package com.twitter.tweetypie.handler

import com.twitter.compliance.userconsent.compliance.birthdate.GlobalBirthdateUtil
import com.twitter.gizmoduck.thriftscala.User
import com.twitter.tweetypie.thriftscala.DeletedTweet
import org.joda.time.DateTime

/*
 * As part of GDPR U13 work, we want to block tweets created from when a user
 * was < 13 from being restored.
 */

private[handler] object U13ValidationUtil {
  def wasTweetCreatedBeforeUserTurned13(user: User, deletedTweet: DeletedTweet): Boolean =
    deletedTweet.createdAtSecs match {
      case None =>
        throw NoCreatedAtTimeException
      case Some(createdAtSecs) =>
        GlobalBirthdateUtil.isUnderSomeAge(13, new DateTime(createdAtSecs * 1000L), user)
    }
}
