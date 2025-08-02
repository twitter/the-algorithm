package com.twitter.product_mitwitterer.core.functional_component.configapi

import com.twitter.timelines.configapi.BaseRequestContetwittert
import com.twitter.timelines.configapi.FeatureContetwittert
import com.twitter.timelines.configapi.GuestId
import com.twitter.timelines.configapi.UserId
import com.twitter.timelines.configapi.WithFeatureContetwittert
import com.twitter.timelines.configapi.WithGuestId
import com.twitter.timelines.configapi.WithUserId

/** Represents [[com.twitter.timelines.configapi]]'s contetwittert information */
case class RequestContetwittert(
  userId: Option[UserId],
  guestId: Option[GuestId],
  featureContetwittert: FeatureContetwittert)
    etwittertends BaseRequestContetwittert
    with WithUserId
    with WithGuestId
    with WithFeatureContetwittert
