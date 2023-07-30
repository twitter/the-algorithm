package com.X.tweetypie
package handler

import com.X.stitch.Stitch
import com.X.tweetypie.repository.TweetQuery
import com.X.tweetypie.repository.TweetRepository
import com.X.tweetypie.store.QuotedTweetDelete
import com.X.tweetypie.thriftscala.QuotedTweetDeleteRequest

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
