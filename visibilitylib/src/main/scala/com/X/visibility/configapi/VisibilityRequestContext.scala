package com.X.visibility.configapi

import com.X.timelines.configapi._

case class VisibilityRequestContext(
  userId: Option[Long],
  guestId: Option[Long],
  experimentContext: ExperimentContext = NullExperimentContext,
  featureContext: FeatureContext = NullFeatureContext)
    extends BaseRequestContext
    with WithUserId
    with WithGuestId
    with WithExperimentContext
    with WithFeatureContext
