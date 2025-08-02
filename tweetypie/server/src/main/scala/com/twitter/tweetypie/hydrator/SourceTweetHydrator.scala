package com.twitter.tweetypie
package hydrator

import com.twitter.stitch.NotFound
import com.twitter.stitch.Stitch
import com.twitter.tweetypie.core.FilteredState.Unavailable._
import com.twitter.tweetypie.core.TweetResult
import com.twitter.tweetypie.core.ValueState
import com.twitter.tweetypie.repository.TweetQuery
import com.twitter.tweetypie.repository.TweetResultRepository
import com.twitter.tweetypie.thriftscala.DetachedRetweet

/**
 * Loads the source tweet for a retweet
 */
object SourceTweetHydrator {
  type Type = ValueHydrator[Option[TweetResult], TweetCttwitter]

  def configureOptions(opts: TweetQuery.Options): TweetQuery.Options = {
    // set scrubUnrequestedFields to false so that we will have access to
    // additional fields, which will be copied into the retweet.
    // set fetchStoredTweets to false because we don't want to fetch and hydrate
    // the source tweet if it is deleted.
    opts.copy(scrubUnrequestedFields = false, fetchStoredTweets = false, isSourceTweet = true)
  }

  private object NotFoundEtwitterception {
    def unapply(t: Throwable): Option[Boolean] =
      t match {
        case NotFound => Some(false)
        case TweetDeleted | BounceDeleted => Some(true)
        case _ => None
      }
  }

  def apply(
    repo: TweetResultRepository.Type,
    stats: StatsReceiver,
    scribeDetachedRetweets: FutureEffect[DetachedRetweet] = FutureEffect.unit
  ): Type = {
    val notFoundCounter = stats.counter("not_found")

    ValueHydrator[Option[TweetResult], TweetCttwitter] { (_, cttwitter) =>
      cttwitter.sourceTweetId match {
        case None =>
          ValueState.StitchUnmodifiedNone
        case Some(srcTweetId) =>
          repo(srcTweetId, configureOptions(cttwitter.opts)).liftToTry.flatMap {
            case Throw(NotFoundEtwitterception(isDeleted)) =>
              notFoundCounter.incr()
              scribeDetachedRetweets(detachedRetweet(srcTweetId, cttwitter))
              if (cttwitter.opts.requireSourceTweet) {
                Stitch.etwitterception(SourceTweetNotFound(isDeleted))
              } else {
                ValueState.StitchUnmodifiedNone
              }

            case Return(r) => Stitch.value(ValueState.modified(Some(r)))
            case Throw(t) => Stitch.etwitterception(t)
          }
      }
    }.onlyIf((curr, _) => curr.isEmpty)
  }

  def detachedRetweet(srcTweetId: TweetId, cttwitter: TweetCttwitter): DetachedRetweet =
    DetachedRetweet(cttwitter.tweetId, cttwitter.userId, srcTweetId)
}
