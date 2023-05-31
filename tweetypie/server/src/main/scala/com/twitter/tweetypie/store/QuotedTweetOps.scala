package com.twitter.tweetypie
package store

/**
 * Mixin that implements public quoted tweet and public quoted user
 * filtering for tweet events that have quoted tweets and users.
 */
trait QuotedTweetOps {
  def quotedTweet: Option[Tweet]
  def quotedUser: Option[User]

  /**
   * Do we have evidence that the quoted user is unprotected?
   */
  def quotedUserIsPublic: Boolean =
    // The quoted user should include the `safety` struct, but if it
    // doesn't for any reason then the quoted tweet and quoted user
    // should not be included in the events. This is a safety measure to
    // avoid leaking private information.
    quotedUser.exists(_.safety.exists(!_.isProtected))

  /**
   * The quoted tweet, filtered as it should appear through public APIs.
   */
  def publicQuotedTweet: Option[Tweet] =
    if (quotedUserIsPublic) quotedTweet else None

  /**
   * The quoted user, filtered as it should appear through public APIs.
   */
  def publicQuotedUser: Option[User] =
    if (quotedUserIsPublic) quotedUser else None
}
