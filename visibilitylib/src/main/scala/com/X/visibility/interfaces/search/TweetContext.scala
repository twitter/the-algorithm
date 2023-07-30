package com.X.visibility.interfaces.search

import com.X.tweetypie.thriftscala.Tweet
import com.X.visibility.models.SafetyLevel

case class TweetContext(
  tweet: Tweet,
  quotedTweet: Option[Tweet],
  retweetSourceTweet: Option[Tweet] = None,
  safetyLevel: SafetyLevel)
