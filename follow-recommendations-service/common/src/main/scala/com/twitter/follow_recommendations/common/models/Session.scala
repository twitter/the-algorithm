package com.ExTwitter.follow_recommendations.common.models

import com.ExTwitter.finagle.tracing.Trace

object Session {

  /**
   * The sessionId in FRS is the finagle trace id which is static within the lifetime of a single
   * request.
   *
   * It is used when generating per-candidate tokens (in TrackingTokenTransform) and is also passed
   * in to downstream Optimus ranker requests.
   *
   */
  def getSessionId: Long = Trace.id.traceId.toLong
}
