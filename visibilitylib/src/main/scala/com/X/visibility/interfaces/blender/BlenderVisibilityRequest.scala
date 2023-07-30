package com.X.visibility.interfaces.blender

import com.X.tweetypie.thriftscala.Tweet
import com.X.visibility.models.SafetyLevel
import com.X.visibility.models.ViewerContext
import com.X.visibility.interfaces.common.blender.BlenderVFRequestContext

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
