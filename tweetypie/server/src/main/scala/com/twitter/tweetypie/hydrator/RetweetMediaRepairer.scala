package com.twitter.tweetypie
package hydrator

/**
 * Retweets should never have their own media, and should never be cached with a media
 * entity.
 */
object RetweetMediaRepairer extends Mutation[Tweet] {
  def apply(tweet: Tweet): Option[Tweet] = {
    if (isRetweet(tweet) && getMedia(tweet).nonEmpty)
      Some(TweetLenses.media.set(tweet, Nil))
    else
      None
  }
}
