package com.twitter.tweet_mixer.candidate_source.simclusters_ann

case class SimclusterColdPostsQuery(
  userId: Long,
  postsPerSimcluster: Int,
  maxCandidates: Int
)