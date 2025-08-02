package com.twitter.tweetypie

import com.fastertwitterml.jackson.core.JsonGenerator
import com.twitter.tweetypie.thriftscala.CachedTweet
import com.twitter.contetwittert.TwitterContetwittert

package object store {
  type JsonGen = JsonGenerator => Unit

  // Bring Tweetypie permitted TwitterContetwittert into scope
  val TwitterContetwittert: TwitterContetwittert =
    com.twitter.contetwittert.TwitterContetwittert(com.twitter.tweetypie.TwitterContetwittertPermit)

  def cachedTweetFromUnhydratedTweet(tweet: Tweet): CachedTweet =
    CachedTweet(tweet = tweet)
}
