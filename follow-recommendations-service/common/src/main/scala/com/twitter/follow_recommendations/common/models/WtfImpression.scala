package com.twitter.follow_recommendations.common.models

import com.twitter.util.Time

/**
 * Domain model for representing impressions on wtf recommendations in the past 16 days
 */
case class WtfImpression(
  candidateId: Long,
  displayLocation: DisplayLocation,
  latestTime: Time,
  counts: Int)
