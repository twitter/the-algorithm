package com.X.recosinjector.clients

import com.X.finagle.stats.StatsReceiver
import com.X.stitch.tweetypie.TweetyPie.{TweetyPieException, TweetyPieResult}
import com.X.storehaus.ReadableStore
import com.X.tweetypie.thriftscala.Tweet
import com.X.util.Future

class Tweetypie(
  tweetyPieStore: ReadableStore[Long, TweetyPieResult]
)(
  implicit statsReceiver: StatsReceiver) {
  private val stats = statsReceiver.scope(this.getClass.getSimpleName)
  private val failureStats = stats.scope("getTweetFailure")

  def getTweet(tweetId: Long): Future[Option[Tweet]] = {
    tweetyPieStore
      .get(tweetId)
      .map { _.map(_.tweet) }
      .rescue {
        case e: TweetyPieException =>
          // Usually results from trying to query a protected or unsafe tweet
          failureStats.scope("TweetyPieException").counter(e.result.tweetState.toString).incr()
          Future.None
        case e =>
          failureStats.counter(e.getClass.getSimpleName).incr()
          Future.None
      }
  }
}
