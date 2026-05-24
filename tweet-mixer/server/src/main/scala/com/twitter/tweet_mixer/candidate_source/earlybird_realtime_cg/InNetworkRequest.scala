package com.twitter.tweet_mixer.candidate_source.earlybird_realtime_cg

import com.twitter.search.earlybird.{thriftscala => t}

case class InNetworkRequest(
  earlybirdRequest: t.EarlybirdRequest,
  excludedIds: Set[Long] = Set(),
  writeBackToHaplo: Boolean = false)
