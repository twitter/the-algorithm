package com.ExTwitter.follow_recommendations.configapi.candidates

import com.ExTwitter.timelines.configapi.BaseRequestContext
import com.ExTwitter.timelines.configapi.FeatureContext
import com.ExTwitter.timelines.configapi.NullFeatureContext
import com.ExTwitter.timelines.configapi.WithFeatureContext
import com.ExTwitter.timelines.configapi.WithUserId

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
