package com.twitter.follow_recommendations.models

import com.twitter.timelines.configapi.Params

case class CandidateUserDebugParams(paramsMap: Map[Long, Params])
