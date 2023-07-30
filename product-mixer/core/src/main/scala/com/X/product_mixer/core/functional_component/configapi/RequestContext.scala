package com.X.product_mixer.core.functional_component.configapi

import com.X.timelines.configapi.BaseRequestContext
import com.X.timelines.configapi.FeatureContext
import com.X.timelines.configapi.GuestId
import com.X.timelines.configapi.UserId
import com.X.timelines.configapi.WithFeatureContext
import com.X.timelines.configapi.WithGuestId
import com.X.timelines.configapi.WithUserId

/** Represents [[com.X.timelines.configapi]]'s context information */
case class RequestContext(
  userId: Option[UserId],
  guestId: Option[GuestId],
  featureContext: FeatureContext)
    extends BaseRequestContext
    with WithUserId
    with WithGuestId
    with WithFeatureContext
