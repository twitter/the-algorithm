package com.twitter.tweet_mixer.candidate_source.evergreen_videos

case class EvergreenVideosSearchByUserIdsQuery(
  userIds: Seq[Long],
  size: Int)
