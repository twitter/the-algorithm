package com.twitter.tweetypie
package repository

import com.twitter.stitch.Stitch
import com.twitter.strato.client.Fetcher
import com.twitter.strato.client.{Client => StratoClient}

/**
 * Repository for fetching UserIds that have unmentioned themselves from a conversation.
 */
object UnmentionedEntitiesRepository {
  type Type = (ConversationId, Seq[UserId]) => Stitch[Option[Seq[UserId]]]

  val column = "consumer-privacy/mentions-management/getUnmentionedUsersFromConversation"
  case class GetUnmentionView(userIds: Option[Seq[Long]])

  def apply(client: StratoClient): Type = {
    val fetcher: Fetcher[Long, GetUnmentionView, Seq[Long]] =
      client.fetcher[Long, GetUnmentionView, Seq[Long]](column)

    (conversationId, userIds) =>
      if (userIds.nonEmpty) {
        fetcher.fetch(conversationId, GetUnmentionView(Some(userIds))).map(_.v)
      } else {
        Stitch.None
      }
  }
}
