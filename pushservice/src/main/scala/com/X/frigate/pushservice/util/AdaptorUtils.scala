package com.X.frigate.pushservice.util

import com.X.stitch.tweetypie.TweetyPie.TweetyPieResult
import com.X.storehaus.FutureOps
import com.X.storehaus.ReadableStore
import com.X.util.Future

object AdaptorUtils {
  def getTweetyPieResults(
    tweetIds: Set[Long],
    tweetyPieStore: ReadableStore[Long, TweetyPieResult],
  ): Future[Map[Long, Option[TweetyPieResult]]] =
    FutureOps
      .mapCollect(tweetyPieStore.multiGet(tweetIds))
}
