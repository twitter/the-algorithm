package com.twitter.tweetypie
package repository

import com.twitter.service.gen.scarecrow.{thriftscala => scarecrow}
import com.twitter.stitch.Stitch
import com.twitter.tweetypie.backends.Scarecrow

object RetweetSpamCheckRepository {
  type Type = scarecrow.Retweet => Stitch[scarecrow.TieredAction]

  def apply(checkRetweet: Scarecrow.CheckRetweet): Type =
    retweet => Stitch.callFuture(checkRetweet(retweet))
}
