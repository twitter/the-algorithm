package com.twitter.tweetypie
package hydrator

import com.twitter.stitch.Stitch
import com.twitter.tweetypie.core._
import com.twitter.tweetypie.repository._

/**
 * Ensures that the tweet's author and source tweet's author (if retweet) are visible to the
 * viewing user - cttwitter.opts.forUserId - when enforceVisibilityFiltering is true.
 * If either of these users is not visible then a FilteredState.Suppress will be returned.
 *
 * Note: blocking relationship is NOT checked here, this means if viewing user `forUserId` is blocked
 * by either the tweet's author or source tweet's author, this will not filter out the tweet.
 */
object TweetAuthorVisibilityHydrator {
  type Type = ValueHydrator[Unit, TweetCttwitter]

  def apply(repo: UserVisibilityRepository.Type): Type =
    ValueHydrator[Unit, TweetCttwitter] { (_, cttwitter) =>
      val ids = Seq(cttwitter.userId) ++ cttwitter.sourceUserId
      val keys = ids.map(id => toRepoQuery(id, cttwitter))

      Stitch
        .traverse(keys)(repo.apply).flatMap { responses =>
          val fs: Option[FilteredState.Unavailable] = responses.flatten.headOption

          fs match {
            case Some(fs: FilteredState.Unavailable) => Stitch.etwitterception(fs)
            case None => ValueState.StitchUnmodifiedUnit
          }
        }
    }.onlyIf((_, cttwitter) => cttwitter.opts.enforceVisibilityFiltering)

  private def toRepoQuery(userId: UserId, cttwitter: TweetCttwitter) =
    UserVisibilityRepository.Query(
      UserKey(userId),
      cttwitter.opts.forUserId,
      cttwitter.tweetId,
      cttwitter.isRetweet,
      cttwitter.opts.isInnerQuotedTweet,
      Some(cttwitter.opts.safetyLevel))
}
