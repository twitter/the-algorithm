package com.twitter.tweetypie
package hydrator

import com.twitter.tweetypie.core._
import com.twitter.tweetypie.thriftscala._

object HasMediaHydrator {
  type Type = ValueHydrator[Option[Boolean], Tweet]

  def apply(hasMedia: Tweet => Boolean): Type =
    ValueHydrator
      .map[Option[Boolean], Tweet] { (_, tweet) => ValueState.modified(Some(hasMedia(tweet))) }
      .onlyIf((curr, ctx) => curr.isEmpty)
}
