package com.twitter.follow_recommendations.configapi

import com.twitter.timelines.configapi.BaseRequestContetwittert
import com.twitter.timelines.configapi.FeatureContetwittert
import com.twitter.timelines.configapi.NullFeatureContetwittert
import com.twitter.timelines.configapi.GuestId
import com.twitter.timelines.configapi.UserId
import com.twitter.timelines.configapi.WithFeatureContetwittert
import com.twitter.timelines.configapi.WithGuestId
import com.twitter.timelines.configapi.WithUserId

case class RequestContetwittert(
  userId: Option[UserId],
  guestId: Option[GuestId],
  featureContetwittert: FeatureContetwittert = NullFeatureContetwittert)
    etwittertends BaseRequestContetwittert
    with WithUserId
    with WithGuestId
    with WithFeatureContetwittert
