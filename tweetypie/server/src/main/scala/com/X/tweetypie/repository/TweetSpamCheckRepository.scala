package com.X.tweetypie
package repository

import com.X.service.gen.scarecrow.{thriftscala => scarecrow}
import com.X.stitch.Stitch
import com.X.tweetypie.backends.Scarecrow

object TweetSpamCheckRepository {

  type Type = (scarecrow.TweetNew, scarecrow.TweetContext) => Stitch[scarecrow.CheckTweetResponse]

  def apply(checkTweet: Scarecrow.CheckTweet2): Type =
    (tweetNew, tweetContext) => Stitch.callFuture(checkTweet((tweetNew, tweetContext)))
}
