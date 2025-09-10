package com.twitter.tweet_mixer.candidate_source.twhin_ann

import com.twitter.simclusters_v2.common.TweetId
import com.twitter.simclusters_v2.common.VersionId

case class TwHINRebuildANNKey(
  id: TweetId,
  dataset: String,
  versionId: VersionId,
  maxCandidates: Int)
