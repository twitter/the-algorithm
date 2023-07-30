package com.X.follow_recommendations.configapi

import com.X.timelines.configapi.BaseRequestContext
import com.X.timelines.configapi.FeatureContext
import com.X.timelines.configapi.NullFeatureContext
import com.X.timelines.configapi.GuestId
import com.X.timelines.configapi.UserId
import com.X.timelines.configapi.WithFeatureContext
import com.X.timelines.configapi.WithGuestId
import com.X.timelines.configapi.WithUserId

case class RequestContext(
  userId: Option[UserId],
  guestId: Option[GuestId],
  featureContext: FeatureContext = NullFeatureContext)
    extends BaseRequestContext
    with WithUserId
    with WithGuestId
    with WithFeatureContext
