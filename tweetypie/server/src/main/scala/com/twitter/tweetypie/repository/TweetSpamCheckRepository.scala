package com.twitter.tweetypie
package repository

import com.twitter.service.gen.scarecrow.{thriftscala => scarecrow}
import com.twitter.stitch.Stitch
import com.twitter.tweetypie.backends.Scarecrow

object TweetSpamCheckRepository {

  type Type = (scarecrow.TweetNew, scarecrow.TweetContetwittert) => Stitch[scarecrow.CheckTweetResponse]

  def apply(checkTweet: Scarecrow.CheckTweet2): Type =
    (tweetNew, tweetContetwittert) => Stitch.callFuture(checkTweet((tweetNew, tweetContetwittert)))
}
