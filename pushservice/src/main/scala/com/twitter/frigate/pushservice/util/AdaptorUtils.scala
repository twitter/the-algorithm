package com.twitter.frigate.pushservice.util

import com.twitter.stitch.tweetypie.TweetyPie.TweetyPieResult
import com.twitter.storehaus.FutureOps
import com.twitter.storehaus.ReadableStore
import com.twitter.util.Future

object AdaptorUtils {
  def getTweetyPieResults(
    tweetIds: Set[Long],
    tweetyPieStore: ReadableStore[Long, TweetyPieResult],
  ): Future[Map[Long, Option[TweetyPieResult]]] =
    FutureOps
      .mapCollect(tweetyPieStore.multiGet(tweetIds))
}
