package com.twitter.product_mixer.core.functional_component.configapi

import com.twitter.timelines.configapi.BaseRequestContext
import com.twitter.timelines.configapi.FeatureContext
import com.twitter.timelines.configapi.GuestId
import com.twitter.timelines.configapi.UserId
import com.twitter.timelines.configapi.WithFeatureContext
import com.twitter.timelines.configapi.WithGuestId
import com.twitter.timelines.configapi.WithUserId

/** Represents [[com.twitter.timelines.configapi]]'s context information */
case class RequestContext(
  userId: Option[UserId],
  guestId: Option[GuestId],
  featureContext: FeatureContext)
    extends BaseRequestContext
    with WithUserId
    with WithGuestId
    with WithFeatureContext
