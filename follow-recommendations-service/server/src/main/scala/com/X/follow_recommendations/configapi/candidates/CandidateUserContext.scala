package com.X.follow_recommendations.configapi.candidates

import com.X.timelines.configapi.BaseRequestContext
import com.X.timelines.configapi.FeatureContext
import com.X.timelines.configapi.NullFeatureContext
import com.X.timelines.configapi.WithFeatureContext
import com.X.timelines.configapi.WithUserId

/**
 * represent the context for a recommendation candidate (producer side)
 * @param userId id of the recommended user
 * @param featureContext feature context
 */
case class CandidateUserContext(
  override val userId: Option[Long],
  featureContext: FeatureContext = NullFeatureContext)
    extends BaseRequestContext
    with WithUserId
    with WithFeatureContext
