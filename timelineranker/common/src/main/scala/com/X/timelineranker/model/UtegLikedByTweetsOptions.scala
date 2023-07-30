package com.X.timelineranker.model

import com.X.timelines.model.UserId

case class UtegLikedByTweetsOptions(
  utegCount: Int,
  isInNetwork: Boolean,
  weightedFollowings: Map[UserId, Double])
