package com.X.simclusters_v2.summingbird.stores

import com.X.frigate.common.store.strato.StratoFetchableStore
import com.X.simclusters_v2.common.TweetId
import com.X.storehaus.ReadableStore
import com.X.strato.client.Client
import com.X.strato.thrift.ScroogeConvImplicits._
import com.X.tweetypie.thriftscala.{GetTweetOptions, StatusCounts, Tweet}

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
