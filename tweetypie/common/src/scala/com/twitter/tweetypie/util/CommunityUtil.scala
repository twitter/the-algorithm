package com.twitter.tweetypie.util

import com.twitter.tweetypie.thriftscala.Communities

object CommunityUtil {

  def communityIds(maybeCommunities: Option[Communities]): Seq[Long] = {
    maybeCommunities match {
      case None =>
        Nil
      case Some(Communities(seq)) =>
        seq
    }
  }

  def hasCommunity(maybeCommunities: Option[Communities]): Boolean = {
    maybeCommunities.exists(_.communityIds.nonEmpty)
  }
}
