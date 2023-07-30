package com.X.tweetypie.repository

import com.X.stitch.Stitch
import com.X.strato.client.Fetcher
import com.X.tweetypie.TweetId
import com.X.strato.client.{Client => StratoClient}

object StratoPromotedTweetRepository {
  type Type = TweetId => Stitch[Boolean]

  val column = "tweetypie/isPromoted.Tweet"

  def apply(client: StratoClient): Type = {
    val fetcher: Fetcher[TweetId, Unit, Boolean] =
      client.fetcher[TweetId, Boolean](column)

    tweetId => fetcher.fetch(tweetId).map(f => f.v.getOrElse(false))
  }
}
