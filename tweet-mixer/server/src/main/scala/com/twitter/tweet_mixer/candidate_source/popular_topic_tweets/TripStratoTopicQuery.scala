package com.twitter.tweet_mixer.candidate_source.popular_topic_tweets

import com.twitter.trends.trip_v1.trip_tweets.{thriftscala => t}

case class TripStratoTopicQuery(
  domains: Seq[t.TripDomain],
  maxCandidatesPerSource: Int,
  maxPopTopicCandidates: Int)
