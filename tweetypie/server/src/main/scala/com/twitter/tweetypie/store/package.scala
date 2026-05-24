package com.twitter.tweetypie

import com.fasterxml.jackson.core.JsonGenerator
import com.twitter.tweetypie.thriftscala.CachedTweet
import com.twitter.context.TwitterContext

package object store {
  type JsonGen = JsonGenerator => Unit

  // Bring Tweetypie permitted TwitterContext into scope
  val TwitterContext: TwitterContext =
    com.twitter.context.TwitterContext(com.twitter.tweetypie.TwitterContextPermit)

  def cachedTweetFromUnhydratedTweet(tweet: Tweet): CachedTweet =
    CachedTweet(tweet = tweet)
}
