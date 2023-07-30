package com.X.tweetypie

import com.fasterxml.jackson.core.JsonGenerator
import com.X.tweetypie.thriftscala.CachedTweet
import com.X.context.XContext

package object store {
  type JsonGen = JsonGenerator => Unit

  // Bring Tweetypie permitted XContext into scope
  val XContext: XContext =
    com.X.context.XContext(com.X.tweetypie.XContextPermit)

  def cachedTweetFromUnhydratedTweet(tweet: Tweet): CachedTweet =
    CachedTweet(tweet = tweet)
}
