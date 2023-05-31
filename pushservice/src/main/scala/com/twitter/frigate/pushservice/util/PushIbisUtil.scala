package com.twitter.frigate.pushservice.util

import com.twitter.util.Future

object PushIbisUtil {

  def getSocialContextModelValues(socialContextUserIds: Seq[Long]): Map[String, String] = {

    val socialContextSize = socialContextUserIds.size

    val (displaySocialContexts, otherCount) = {
      if (socialContextSize < 3) (socialContextUserIds, 0)
      else (socialContextUserIds.take(1), socialContextSize - 1)
    }

    val usersValue = displaySocialContexts.map(_.toString).mkString(",")

    if (otherCount > 0) Map("social_users" -> s"$usersValue+$otherCount")
    else Map("social_users" -> usersValue)
  }

  def mergeFutModelValues(
    mvFut1: Future[Map[String, String]],
    mvFut2: Future[Map[String, String]]
  ): Future[Map[String, String]] = {
    Future.join(mvFut1, mvFut2).map {
      case (mv1, mv2) => mv1 ++ mv2
    }
  }

  def mergeModelValues(
    mvFut1: Future[Map[String, String]],
    mv2: Map[String, String]
  ): Future[Map[String, String]] =
    mvFut1.map { mv1 => mv1 ++ mv2 }
}
