package com.twitter.tweetypie
package handler

import com.twitter.stitch.Stitch
import com.twitter.tweetypie.repository.TweetQuery
import com.twitter.tweetypie.repository.TweetRepository
import com.twitter.tweetypie.store.QuotedTweetTakedown
import com.twitter.tweetypie.thriftscala.QuotedTweetTakedownRequest

/**
 * Create the appropriate QuotedTweetTakedown.Event for a QuotedTweetTakedown request.
 */
object QuotedTweetTakedownEventBuilder {
  type Type = QuotedTweetTakedownRequest => Future[Option[QuotedTweetTakedown.Event]]

  val queryOptions: TweetQuery.Options =
    TweetQuery.Options(GetTweetsHandler.BaseInclude)

  def apply(tweetRepo: TweetRepository.Optional): Type =
    request =>
      Stitch.run(
        tweetRepo(request.quotingTweetId, queryOptions).map {
          _.map { quotingTweet =>
            QuotedTweetTakedown.Event(
              quotingTweetId = request.quotingTweetId,
              quotingUserId = getUserId(quotingTweet),
              quotedTweetId = request.quotedTweetId,
              quotedUserId = request.quotedUserId,
              takedownCountryCodes = request.takedownCountryCodes,
              takedownReasons = request.takedownReasons,
              timestamp = Time.now
            )
          }
        }
      )
}
