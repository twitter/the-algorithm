package com.twitter.follow_recommendations.common.models

case class TweetCandidate(
  tweetId: Long,
  authorId: Long,
  score: Option[Double])
