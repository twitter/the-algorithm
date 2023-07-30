package com.X.tweetypie
package repository

import com.X.service.gen.scarecrow.{thriftscala => scarecrow}
import com.X.stitch.Stitch
import com.X.tweetypie.backends.Scarecrow

object RetweetSpamCheckRepository {
  type Type = scarecrow.Retweet => Stitch[scarecrow.TieredAction]

  def apply(checkRetweet: Scarecrow.CheckRetweet): Type =
    retweet => Stitch.callFuture(checkRetweet(retweet))
}
