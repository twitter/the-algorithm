package com.twitter.tweet_mixer.candidate_source.popular_grok_topic_tweets

case class GrokTopicTweetsQuery(
  userId: Long,
  language: Option[String],
  placeId: Option[Long],
  maxNumCandidates: Int)
