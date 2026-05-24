package com.twitter.tweetypie.util

import com.twitter.tweetypie.thriftscala.TransientCreateContext
import com.twitter.tweetypie.thriftscala.TweetCreateContextKey
import com.twitter.tweetypie.thriftscala.TweetCreateContextKey.PeriscopeCreatorId
import com.twitter.tweetypie.thriftscala.TweetCreateContextKey.PeriscopeIsLive

object TransientContextUtil {

  def toAdditionalContext(context: TransientCreateContext): Map[TweetCreateContextKey, String] =
    Seq
      .concat(
        context.periscopeIsLive.map(PeriscopeIsLive -> _.toString), // "true" or "false"
        context.periscopeCreatorId.map(PeriscopeCreatorId -> _.toString) // userId
      )
      .toMap
}
