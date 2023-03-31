package com.twitter.visibility.models

object TweetDeleteReason extends Enumeration {
  type TweetDeleteReason = Value
  val Deleted, BounceDeleted = Value
}
