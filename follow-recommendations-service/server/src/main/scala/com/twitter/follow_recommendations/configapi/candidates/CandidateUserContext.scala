package com.twitter.follow_recommendations.configapi.candidates

import com.twitter.timelines.configapi.BaseRequestContext
import com.twitter.timelines.configapi.FeatureContext
import com.twitter.timelines.configapi.NullFeatureContext
import com.twitter.timelines.configapi.WithFeatureContext
import com.twitter.timelines.configapi.WithUserId

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
