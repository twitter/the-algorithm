package com.twitter.simclusters_v2.summingbird.stores

import com.twitter.frigate.common.store.strato.StratoFetchableStore
import com.twitter.simclusters_v2.common.TweetId
import com.twitter.storehaus.ReadableStore
import com.twitter.strato.client.Client
import com.twitter.strato.thrift.ScroogeConvImplicits._
import com.twitter.tweetypie.thriftscala.{GetTweetOptions, StatusCounts, Tweet}

object TweetStatusCountsStore {

  def tweetStatusCountsStore(
    stratoClient: Client,
    column: String
  ): ReadableStore[TweetId, StatusCounts] = {
    StratoFetchableStore
      .withView[TweetId, GetTweetOptions, Tweet](stratoClient, column, getTweetOptions)
      .mapValues(_.counts.getOrElse(emptyStatusCount))
  }

  private val emptyStatusCount = StatusCounts()

  private val getTweetOptions =
    GetTweetOptions(
      includeRetweetCount = true,
      includeReplyCount = true,
      includeFavoriteCount = true,
      includeQuoteCount = true)
}
