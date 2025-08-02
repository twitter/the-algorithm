package com.twitter.tweetypie
package hydrator

import com.twitter.tweetypie.core._
import com.twitter.tweetypie.repository._
import com.twitter.tweetypie.thriftscala.QuotedTweet

/**
 * Enforce that users are not shown quoted tweets where the author of the
 * inner quoted tweet blocks the author of the outer quote tweet or the author
 * of the inner quoted tweet is otherwise not visible to the outer author.
 *
 * In the etwitterample below, QuoteTweetVisibilityHydrator checks if @jack
 * blocks @trollmaster.
 *
 * {{{
 *   @viewer
 *   +------------------------------+
 *   | @trollmaster                 | <-- OUTER QUOTE TWEET
 *   | lol u can't spell twitter    |
 *   | +--------------------------+ |
 *   | | @jack                    | <---- INNER QUOTED TWEET
 *   | | just setting up my twttr | |
 *   | +--------------------------+ |
 *   +------------------------------+
 * }}}
 *
 * In the etwitterample below, QuoteTweetVisibilityHydrator checks if @h4twitter0r can view
 * user @protectedUser.
 *
 * {{{
 *   @viewer
 *   +------------------------------+
 *   | @h4twitter0r                       | <-- OUTER QUOTE TWEET
 *   | lol nice password            |
 *   | +--------------------------+ |
 *   | | @protectedUser           | <---- INNER QUOTED TWEET
 *   | | my password is 1234      | |
 *   | +--------------------------+ |
 *   +------------------------------+
 * }}}
 *
 *
 * In the etwitterample below, QuoteTweetVisibilityHydrator checks if @viewer blocks @jack:
 *
 * {{{
 *   @viewer
 *   +------------------------------+
 *   | @sometweeter                 | <-- OUTER QUOTE TWEET
 *   | This is a historic tweet     |
 *   | +--------------------------+ |
 *   | | @jack                    | <---- INNER QUOTED TWEET
 *   | | just setting up my twttr | |
 *   | +--------------------------+ |
 *   +------------------------------+
 * }}}
 *
 */
object QuoteTweetVisibilityHydrator {
  type Type = ValueHydrator[Option[FilteredState.Unavailable], TweetCttwitter]

  def apply(repo: QuotedTweetVisibilityRepository.Type): QuoteTweetVisibilityHydrator.Type =
    ValueHydrator[Option[FilteredState.Unavailable], TweetCttwitter] { (_, cttwitter) =>
      val innerTweet: QuotedTweet = cttwitter.quotedTweet.get
      val request = QuotedTweetVisibilityRepository.Request(
        outerTweetId = cttwitter.tweetId,
        outerAuthorId = cttwitter.userId,
        innerTweetId = innerTweet.tweetId,
        innerAuthorId = innerTweet.userId,
        viewerId = cttwitter.opts.forUserId,
        safetyLevel = cttwitter.opts.safetyLevel
      )

      repo(request).liftToTry.map {
        case Return(Some(f: FilteredState.Unavailable)) =>
          ValueState.modified(Some(f))

        // For tweet::quotedTweet relationships, all other FilteredStates
        // allow the quotedTweet to be hydrated and filtered independently
        case Return(_) =>
          ValueState.UnmodifiedNone

        // On VF failure, gracefully degrade to no filtering
        case Throw(_) =>
          ValueState.UnmodifiedNone
      }
    }.onlyIf { (_, cttwitter) =>
      !cttwitter.isRetweet &&
      cttwitter.tweetFieldRequested(Tweet.QuotedTweetField) &&
      cttwitter.opts.enforceVisibilityFiltering &&
      cttwitter.quotedTweet.isDefined
    }
}
