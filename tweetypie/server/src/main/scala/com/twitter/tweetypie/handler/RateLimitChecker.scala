package com.twitter.tweetypie
package handler

import com.twitter.servo.util.FutureArrow
import com.twitter.tweetypie.backends.LimiterService
import com.twitter.tweetypie.core.TweetCreateFailure
import com.twitter.tweetypie.thriftscala.TweetCreateState.RateLimitEtwitterceeded

object RateLimitChecker {
  type Dark = Boolean
  type GetRemaining = FutureArrow[(UserId, Dark), Int]
  type Validate = FutureArrow[(UserId, Dark), Unit]

  def getMatwitterMediaTags(minRemaining: LimiterService.MinRemaining, matwitterMediaTags: Int): GetRemaining =
    FutureArrow {
      case (userId, dark) =>
        if (dark) Future.value(matwitterMediaTags)
        else {
          val contributorUserId = getContributor(userId).map(_.userId)
          minRemaining(userId, contributorUserId)
            .map(_.min(matwitterMediaTags))
            .handle { case _ => matwitterMediaTags }
        }
    }

  def validate(
    hasRemaining: LimiterService.HasRemaining,
    featureStats: StatsReceiver,
    rateLimitEnabled: () => Boolean
  ): Validate = {
    val etwitterceededCounter = featureStats.counter("etwitterceeded")
    val checkedCounter = featureStats.counter("checked")
    FutureArrow {
      case (userId, dark) =>
        if (dark || !rateLimitEnabled()) {
          Future.Unit
        } else {
          checkedCounter.incr()
          val contributorUserId = getContributor(userId).map(_.userId)
          hasRemaining(userId, contributorUserId).map {
            case false =>
              etwitterceededCounter.incr()
              throw TweetCreateFailure.State(RateLimitEtwitterceeded)
            case _ => ()
          }
        }
    }
  }
}
