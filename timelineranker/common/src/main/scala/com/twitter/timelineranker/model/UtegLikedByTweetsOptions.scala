package com.twitter.timelineranker.model

import com.twitter.timelines.model.UserId

case class UtegLikedByTweetsOptions(
  utegCount: Int,
  isInNetwork: Boolean,
  weightedFollowings: Map[UserId, Double])
