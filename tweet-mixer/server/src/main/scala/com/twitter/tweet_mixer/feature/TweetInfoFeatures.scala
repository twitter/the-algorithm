package com.twitter.tweet_mixer.feature

import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.feature.Feature

object TweetBooleanInfoFeature extends Feature[TweetCandidate, Option[Int]]

object AuthorIdFeature extends Feature[TweetCandidate, Option[Long]]

object MediaIdFeature extends Feature[TweetCandidate, Option[Long]]

object TweetInfoFeatures {
  val IsReply = "IsReply"
  val HasVideo = "HasVideo"
  val HasUrl = "HasUrl"
  val HasMultipleMedia = "HasMultipleMedia"
  val IsHighMediaResolution = "IsHighMediaResolution"
  val IsLongVideo = "IsLongVideo"
  val IsLandscapeVideo = "IsLandscapeVideo"
  val IsRetweet = "IsRetweet"
  val HasImage = "HasImage"
  val IsShortFormVideo = "IsShortFormVideo"
  val IsLongFormVideo = "IsLongFormVideo"

  val TweetInfoIndex: Map[String, Int] = Map(
    IsReply -> 0,
    HasVideo -> 1,
    HasUrl -> 2,
    HasMultipleMedia -> 3,
    IsHighMediaResolution -> 4,
    IsLongVideo -> 5,
    IsLandscapeVideo -> 6,
    IsRetweet -> 7,
    HasImage -> 8,
    IsShortFormVideo -> 9,
    IsLongFormVideo -> 10
  )

  def setFeature(featureName: String, bitmap: Int): Int = {
    val i = TweetInfoIndex.getOrElse(featureName, 31) //getOrElse should never reach else stage
    bitmap | (1 << i)
  }

  def isFeatureSet(feature: String, bitmap: Int): Boolean = {
    val i = TweetInfoIndex.getOrElse(feature, 31) //getOrElse should never reach else stage
    (bitmap & (1 << i)) != 0
  }
}
