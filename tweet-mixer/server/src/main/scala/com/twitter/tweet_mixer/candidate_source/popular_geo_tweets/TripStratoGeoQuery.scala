package com.twitter.tweet_mixer.candidate_source.popular_geo_tweets

import com.twitter.trends.trip_v1.trip_tweets.{thriftscala => t}

case class TripStratoGeoQuery(
  domains: Seq[t.TripDomain],
  maxCandidatesPerSource: Int,
  maxPopGeoCandidates: Int)
