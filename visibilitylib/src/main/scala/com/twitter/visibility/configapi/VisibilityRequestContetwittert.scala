package com.twitter.visibility.configapi

import com.twitter.timelines.configapi._

case class VisibilityRequestContetwittert(
  userId: Option[Long],
  guestId: Option[Long],
  etwitterperimentContetwittert: EtwitterperimentContetwittert = NullEtwitterperimentContetwittert,
  featureContetwittert: FeatureContetwittert = NullFeatureContetwittert)
    etwittertends BaseRequestContetwittert
    with WithUserId
    with WithGuestId
    with WithEtwitterperimentContetwittert
    with WithFeatureContetwittert
