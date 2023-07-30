package com.X.tweetypie
package service
package observer

import com.X.tweetypie.thriftscala.StoredTweetError
import com.X.tweetypie.thriftscala.StoredTweetInfo
import com.X.tweetypie.thriftscala.StoredTweetState.BounceDeleted
import com.X.tweetypie.thriftscala.StoredTweetState.ForceAdded
import com.X.tweetypie.thriftscala.StoredTweetState.HardDeleted
import com.X.tweetypie.thriftscala.StoredTweetState.NotFound
import com.X.tweetypie.thriftscala.StoredTweetState.SoftDeleted
import com.X.tweetypie.thriftscala.StoredTweetState.Undeleted
import com.X.tweetypie.thriftscala.StoredTweetState.UnknownUnionField

private[service] trait StoredTweetsObserver {

  protected def observeStoredTweets(
    storedTweets: Seq[StoredTweetInfo],
    stats: StatsReceiver
  ): Unit = {
    val stateScope = stats.scope("state")
    val errorScope = stats.scope("error")

    val sizeCounter = stats.counter("count")
    sizeCounter.incr(storedTweets.size)

    val returnedStatesCount = storedTweets
      .groupBy(_.storedTweetState match {
        case None => "found"
        case Some(_: HardDeleted) => "hard_deleted"
        case Some(_: SoftDeleted) => "soft_deleted"
        case Some(_: BounceDeleted) => "bounce_deleted"
        case Some(_: Undeleted) => "undeleted"
        case Some(_: ForceAdded) => "force_added"
        case Some(_: NotFound) => "not_found"
        case Some(_: UnknownUnionField) => "unknown"
      })
      .mapValues(_.size)

    returnedStatesCount.foreach {
      case (state, count) => stateScope.counter(state).incr(count)
    }

    val returnedErrorsCount = storedTweets
      .foldLeft(Seq[StoredTweetError]()) { (errors, storedTweetInfo) =>
        errors ++ storedTweetInfo.errors
      }
      .groupBy(_.name)
      .mapValues(_.size)

    returnedErrorsCount.foreach {
      case (error, count) => errorScope.counter(error).incr(count)
    }
  }

}
