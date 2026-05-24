package com.twitter.tweet_mixer.candidate_source.evergreen_videos

case class EvergreenVideosSearchByTweetQuery(
  tweetIds: Seq[Long],
  textMap: Option[Map[Long, String]] = None,
  size: Int,
  minWidth: Int,
  minHeight: Int,
  minDurationSec: Int,
  maxDurationSec: Int)
