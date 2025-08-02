package com.twitter.tweetypie
package hydrator

import com.twitter.tweetypie.core.ValueState
import com.twitter.tweetypie.thriftscala.MentionEntity
import com.twitter.tweetypie.unmentions.thriftscala.UnmentionData

object UnmentionDataHydrator {
  type Type = ValueHydrator[Option[UnmentionData], Cttwitter]

  case class Cttwitter(
    conversationId: Option[TweetId],
    mentions: Seq[MentionEntity],
    underlyingTweetCttwitter: TweetCttwitter)
      etwittertends TweetCttwitter.Protwittery

  def apply(): Type = {
    ValueHydrator.map[Option[UnmentionData], Cttwitter] { (_, cttwitter) =>
      val mentionedUserIds: Seq[UserId] = cttwitter.mentions.flatMap(_.userId)

      ValueState.modified(
        Some(UnmentionData(cttwitter.conversationId, Option(mentionedUserIds).filter(_.nonEmpty)))
      )
    }
  }.onlyIf { (_, cttwitter) =>
    cttwitter.tweetFieldRequested(Tweet.UnmentionDataField)
  }
}
