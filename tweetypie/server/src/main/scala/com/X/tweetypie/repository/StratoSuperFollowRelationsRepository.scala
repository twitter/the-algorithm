package com.X.tweetypie.repository

import com.X.audience_rewards.thriftscala.HasSuperFollowingRelationshipRequest
import com.X.stitch.Stitch
import com.X.strato.client.Fetcher
import com.X.strato.client.{Client => StratoClient}
import com.X.tweetypie.Future
import com.X.tweetypie.UserId
import com.X.tweetypie.core.TweetCreateFailure
import com.X.tweetypie.thriftscala.ExclusiveTweetControl
import com.X.tweetypie.thriftscala.TweetCreateState

object StratoSuperFollowRelationsRepository {
  type Type = (UserId, UserId) => Stitch[Boolean]

  def apply(client: StratoClient): Type = {

    val column = "audiencerewards/superFollows/hasSuperFollowingRelationshipV2"

    val fetcher: Fetcher[HasSuperFollowingRelationshipRequest, Unit, Boolean] =
      client.fetcher[HasSuperFollowingRelationshipRequest, Boolean](column)

    (authorId, viewerId) => {
      // Owner of an exclusive tweet chain can respond to their own
      // tweets / replies, despite not super following themselves
      if (authorId == viewerId) {
        Stitch.True
      } else {
        val key = HasSuperFollowingRelationshipRequest(authorId, viewerId)
        // The default relation for this column is "missing", aka None.
        // This needs to be mapped to false since Super Follows are a sparse relation.
        fetcher.fetch(key).map(_.v.getOrElse(false))
      }
    }
  }

  object Validate {
    def apply(
      exclusiveTweetControl: Option[ExclusiveTweetControl],
      userId: UserId,
      superFollowRelationsRepo: StratoSuperFollowRelationsRepository.Type
    ): Future[Unit] = {
      Stitch
        .run {
          exclusiveTweetControl.map(_.conversationAuthorId) match {
            // Don't do exclusive tweet validation on non exclusive tweets.
            case None =>
              Stitch.value(true)

            case Some(convoAuthorId) =>
              superFollowRelationsRepo(userId, convoAuthorId)
          }
        }.map {
          case true => Future.Unit
          case false =>
            Future.exception(TweetCreateFailure.State(TweetCreateState.SourceTweetNotFound))
        }.flatten
    }
  }
}
