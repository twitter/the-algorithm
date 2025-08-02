package com.twitter.follow_recommendations.configapi.candidates

import com.twitter.timelines.configapi.BaseRequestContetwittert
import com.twitter.timelines.configapi.FeatureContetwittert
import com.twitter.timelines.configapi.NullFeatureContetwittert
import com.twitter.timelines.configapi.WithFeatureContetwittert
import com.twitter.timelines.configapi.WithUserId

/**
 * represent the contetwittert for a recommendation candidate (producer side)
 * @param userId id of the recommended user
 * @param featureContetwittert feature contetwittert
 */
case class CandidateUserContetwittert(
  override val userId: Option[Long],
  featureContetwittert: FeatureContetwittert = NullFeatureContetwittert)
    etwittertends BaseRequestContetwittert
    with WithUserId
    with WithFeatureContetwittert
