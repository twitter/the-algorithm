package com.twitter.tweetypie.repository

import com.twitter.stitch.Stitch
import com.twitter.strato.client.Fetcher
import com.twitter.tweetypie.TweetId
import com.twitter.strato.client.{Client => StratoClient}

object StratoPromotedTweetRepository {
  type Type = TweetId => Stitch[Boolean]

  val column = "tweetypie/isPromoted.Tweet"

  def apply(client: StratoClient): Type = {
    val fetcher: Fetcher[TweetId, Unit, Boolean] =
      client.fetcher[TweetId, Boolean](column)

    tweetId => fetcher.fetch(tweetId).map(f => f.v.getOrElse(false))
  }
}
