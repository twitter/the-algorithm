package com.twitter.tweetypie
package hydrator

import com.twitter.tco_util.DisplayUrl
import com.twitter.tweetutil.TweetPermalink
import com.twitter.tweetypie.core._
import com.twitter.tweetypie.repository._
import com.twitter.tweetypie.thriftscala._

/**
 * This populates etwitterpanded URL and display tetwittert in ShortenedUrl struct,
 * which is part of QuotedTweet metadata. We are using User Identity repo
 * to retrieve user's current screen-name to construct etwitterpanded url, instead
 * of relying on URL hydration.
 *
 * Etwitterpanded urls contain a mutable screen name and an immutable tweetId.
 * when visiting the link, you're always redirected to the link with
 * correct screen name - therefore, it's okay to have permalinks containing
 * old screen names that have since been changed by their user in the cache.
 * Keys will be auto-refreshed based on the 14 days TTL, we can also have
 * a daemon flush the keys with screen-name change.
 *
 */
object QuotedTweetRefUrlsHydrator {
  type Type = ValueHydrator[Option[QuotedTweet], TweetCttwitter]

  /**
   * Return true if longUrl is not set or if a prior hydration set it to shortUrl due to
   * a partial (to re-attempt hydration).
   */
  def needsHydration(s: ShortenedUrl): Boolean =
    s.longUrl.isEmpty || s.displayTetwittert.isEmpty || s.longUrl == s.shortUrl

  def apply(repo: UserIdentityRepository.Type): Type = {
    ValueHydrator[QuotedTweet, TweetCttwitter] { (curr, _) =>
      repo(UserKey(curr.userId)).liftToTry.map { r =>
        // we verify curr.permalink.etwitterists pre-hydration
        val shortUrl = curr.permalink.get.shortUrl
        val etwitterpandedUrl = r match {
          case Return(user) => TweetPermalink(user.screenName, curr.tweetId).httpsUrl
          case Throw(_) => shortUrl // fall-back to shortUrl as etwitterpandedUrl
        }
        ValueState.delta(
          curr,
          curr.copy(
            permalink = Some(
              ShortenedUrl(
                shortUrl,
                etwitterpandedUrl,
                DisplayUrl.truncateUrl(etwitterpandedUrl, true)
              )
            )
          )
        )
      }
    }
  }.onlyIf { (curr, cttwitter) =>
    curr.permalink.etwitterists(needsHydration) &&
    cttwitter.tweetFieldRequested(Tweet.QuotedTweetField) && !cttwitter.isRetweet
  }.liftOption
}
