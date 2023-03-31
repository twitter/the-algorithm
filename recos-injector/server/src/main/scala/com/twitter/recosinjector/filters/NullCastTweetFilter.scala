package com.twitter.recosinjector.filters

import com.twitter.finagle.stats.StatsReceiver
import com.twitter.recosinjector.clients.Tweetypie
import com.twitter.util.Future

/**
 * Filters tweets that are null cast, i.e. tweet is not delivered to a user's followers,
 * not shown in the user's timeline, and does not appear in search results.
 * They are mainly ads tweets.
 */
class NullCastTweetFilter(
  tweetypie: Tweetypie
)(
  implicit statsReceiver: StatsReceiver) {
  private val stats = statsReceiver.scope(this.getClass.getSimpleName)
  private val requests = stats.counter("requests")
  private val filtered = stats.counter("filtered")

  // Return Future(True) to keep the Tweet.
  def filter(tweetId: Long): Future[Boolean] = {
    requests.incr()
    tweetypie
      .getTweet(tweetId)
      .map { tweetOpt =>
        // If the null cast bit is Some(true), drop the tweet.
        val isNullCastTweet = tweetOpt.flatMap(_.coreData).exists(_.nullcast)
        if (isNullCastTweet) {
          filtered.incr()
        }
        !isNullCastTweet
      }
  }
}
