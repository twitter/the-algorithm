package com.twitter.visibility.interfaces.search

import com.twitter.tweetypie.thriftscala.Tweet
import com.twitter.visibility.models.SafetyLevel

case class TweetContext(
  tweet: Tweet,
  quotedTweet: Option[Tweet],
  retweetSourceTweet: Option[Tweet] = None,
  safetyLevel: SafetyLevel)
