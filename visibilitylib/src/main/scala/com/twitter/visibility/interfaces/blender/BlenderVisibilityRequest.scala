package com.twitter.visibility.interfaces.blender

import com.twitter.tweetypie.thriftscala.Tweet
import com.twitter.visibility.models.SafetyLevel
import com.twitter.visibility.models.ViewerContext
import com.twitter.visibility.interfaces.common.blender.BlenderVFRequestContext

case class BlenderVisibilityRequest(
  tweet: Tweet,
  quotedTweet: Option[Tweet],
  retweetSourceTweet: Option[Tweet] = None,
  isRetweet: Boolean,
  safetyLevel: SafetyLevel,
  viewerContext: ViewerContext,
  blenderVFRequestContext: BlenderVFRequestContext) {

  def getTweetID: Long = tweet.id

  def hasQuotedTweet: Boolean = {
    quotedTweet.nonEmpty
  }
  def hasSourceTweet: Boolean = {
    retweetSourceTweet.nonEmpty
  }

  def getQuotedTweetId: Long = {
    quotedTweet match {
      case Some(qTweet) =>
        qTweet.id
      case None =>
        -1
    }
  }
  def getSourceTweetId: Long = {
    retweetSourceTweet match {
      case Some(sourceTweet) =>
        sourceTweet.id
      case None =>
        -1
    }
  }
}
