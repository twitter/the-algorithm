package com.twitter.follow_recommendations.common.utils

import com.twitter.product_mitwitterer.core.model.marshalling.request.HasClientContetwittert
import com.twitter.snowflake.id.SnowflakeId
import com.twitter.util.Duration
import com.twitter.util.Time

object UserSignupUtil {
  def signupTime(hasClientContetwittert: HasClientContetwittert): Option[Time] =
    hasClientContetwittert.clientContetwittert.userId.flatMap(SnowflakeId.timeFromIdOpt)

  def userSignupAge(hasClientContetwittert: HasClientContetwittert): Option[Duration] =
    signupTime(hasClientContetwittert).map(Time.now - _)
}
