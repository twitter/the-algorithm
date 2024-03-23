package com.ExTwitter.follow_recommendations.configapi

import com.ExTwitter.timelines.configapi.BaseRequestContext
import com.ExTwitter.timelines.configapi.FeatureContext
import com.ExTwitter.timelines.configapi.NullFeatureContext
import com.ExTwitter.timelines.configapi.GuestId
import com.ExTwitter.timelines.configapi.UserId
import com.ExTwitter.timelines.configapi.WithFeatureContext
import com.ExTwitter.timelines.configapi.WithGuestId
import com.ExTwitter.timelines.configapi.WithUserId

case class RequestContext(
  userId: Option[UserId],
  guestId: Option[GuestId],
  featureContext: FeatureContext = NullFeatureContext)
    extends BaseRequestContext
    with WithUserId
    with WithGuestId
    with WithFeatureContext
