package com.twitter.tweetypie
package handler

import com.twitter.stitch.Stitch
import com.twitter.tweetypie.repository.TweetQuery
import com.twitter.tweetypie.repository.TweetRepository
import com.twitter.tweetypie.store.QuotedTweetDelete
import com.twitter.tweetypie.thriftscala.QuotedTweetDeleteRequest

/**
 * Create the appropriate QuotedTweetDelete.Event for a QuotedTweetDelete request.
 */
object QuotedTweetDeleteEventBuilder {
  type Type = QuotedTweetDeleteRequest => Future[Option[QuotedTweetDelete.Event]]

  val queryOptions: TweetQuery.Options =
    TweetQuery.Options(GetTweetsHandler.BaseInclude)

  def apply(tweetRepo: TweetRepository.Optional): Type =
    request =>
      Stitch.run(
        tweetRepo(request.quotingTweetId, queryOptions).map {
          _.map { quotingTweet =>
            QuotedTweetDelete.Event(
              quotingTweetId = request.quotingTweetId,
              quotingUserId = getUserId(quotingTweet),
              quotedTweetId = request.quotedTweetId,
              quotedUserId = request.quotedUserId,
              timestamp = Time.now
            )
          }
        }
      )
}
