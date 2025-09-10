package com.twitter.tweet_mixer.candidate_source.simclusters_ann

import com.twitter.simclustersann.thriftscala.{Query => SimClustersANNQuery}

case class SANNQuery(
  queries: Seq[SimClustersANNQuery],
  maxCandidates: Int,
  minScore: Double,
  enableCache: Boolean)
