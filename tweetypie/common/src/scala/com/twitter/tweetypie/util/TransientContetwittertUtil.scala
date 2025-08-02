package com.twitter.tweetypie.util

import com.twitter.tweetypie.thriftscala.TransientCreateContetwittert
import com.twitter.tweetypie.thriftscala.TweetCreateContetwittertKey
import com.twitter.tweetypie.thriftscala.TweetCreateContetwittertKey.PeriscopeCreatorId
import com.twitter.tweetypie.thriftscala.TweetCreateContetwittertKey.PeriscopeIsLive

object TransientContetwittertUtil {

  def toAdditionalContetwittert(contetwittert: TransientCreateContetwittert): Map[TweetCreateContetwittertKey, String] =
    Seq
      .concat(
        contetwittert.periscopeIsLive.map(PeriscopeIsLive -> _.toString), // "true" or "false"
        contetwittert.periscopeCreatorId.map(PeriscopeCreatorId -> _.toString) // userId
      )
      .toMap
}
