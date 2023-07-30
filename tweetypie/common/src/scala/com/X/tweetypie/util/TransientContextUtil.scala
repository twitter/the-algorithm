package com.X.tweetypie.util

import com.X.tweetypie.thriftscala.TransientCreateContext
import com.X.tweetypie.thriftscala.TweetCreateContextKey
import com.X.tweetypie.thriftscala.TweetCreateContextKey.PeriscopeCreatorId
import com.X.tweetypie.thriftscala.TweetCreateContextKey.PeriscopeIsLive

object TransientContextUtil {

  def toAdditionalContext(context: TransientCreateContext): Map[TweetCreateContextKey, String] =
    Seq
      .concat(
        context.periscopeIsLive.map(PeriscopeIsLive -> _.toString), // "true" or "false"
        context.periscopeCreatorId.map(PeriscopeCreatorId -> _.toString) // userId
      )
      .toMap
}
