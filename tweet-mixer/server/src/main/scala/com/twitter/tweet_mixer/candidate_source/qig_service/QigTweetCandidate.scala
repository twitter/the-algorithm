package com.twitter.tweet_mixer.candidate_source.qig_service

import com.twitter.search.query_interaction_graph.service.{thriftscala => t}

case class QigTweetCandidate(
  tweetCandidate: t.QigTweetCandidate,
  query: String)
