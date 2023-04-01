package com.twitter.follow_recommendations.configapi

import com.twitter.timelines.configapi.BaseRequestContext
import com.twitter.timelines.configapi.FeatureContext
import com.twitter.timelines.configapi.NullFeatureContext
import com.twitter.timelines.configapi.GuestId
import com.twitter.timelines.configapi.UserId
import com.twitter.timelines.configapi.WithFeatureContext
import com.twitter.timelines.configapi.WithGuestId
import com.twitter.timelines.configapi.WithUserId

case class RequestContext(
  userId: Option[UserId],
  guestId: Option[GuestId],
  featureContext: FeatureContext = NullFeatureContext)
    extends BaseRequestContext
    with WithUserId
    with WithGuestId
    with WithFeatureContext
