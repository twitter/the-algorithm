package com.twitter.tweetypie
package service
package observer

import com.twitter.servo.etwitterception.thriftscala.ClientError
import com.twitter.tweetypie.thriftscala.GetDeletedTweetResult
import com.twitter.tweetypie.thriftscala.GetDeletedTweetsRequest

private[service] object GetDeletedTweetsObserver {
  type Type = ObserveEtwitterchange[GetDeletedTweetsRequest, Seq[GetDeletedTweetResult]]

  def observeEtwitterchange(stats: StatsReceiver): Effect[Type] = {
    val resultStateStats = ResultStateStats(stats)

    Effect {
      case (request, response) =>
        response match {
          case Return(_) | Throw(ClientError(_)) =>
            resultStateStats.success(request.tweetIds.size)
          case Throw(_) =>
            resultStateStats.failed(request.tweetIds.size)
        }
    }
  }
}
