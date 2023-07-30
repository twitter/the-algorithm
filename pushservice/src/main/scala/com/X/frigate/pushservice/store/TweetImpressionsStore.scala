package com.X.frigate.pushservice.store

import com.X.frigate.common.store.strato.StratoFetchableStore
import com.X.storehaus.ReadableStore
import com.X.strato.client.{Client => StratoClient}
import com.X.util.Future

/**
 * Store to get inbound Tweet impressions count for a specific Tweet id.
 */
class TweetImpressionsStore(stratoClient: StratoClient) extends ReadableStore[Long, String] {

  private val column = "rux/impression.Tweet"
  private val store = StratoFetchableStore.withUnitView[Long, String](stratoClient, column)

  def getCounts(tweetId: Long): Future[Option[Long]] = {
    store.get(tweetId).map(_.map(_.toLong))
  }
}
