package com.twitter.follow_recommendations.common.utils

import com.twitter.product_mixer.core.model.marshalling.request.HasClientContext
import com.twitter.snowflake.id.SnowflakeId
import com.twitter.util.Duration
import com.twitter.util.Time

object UserSignupUtil {
  def signupTime(hasClientContext: HasClientContext): Option[Time] =
    hasClientContext.clientContext.userId.flatMap(SnowflakeId.timeFromIdOpt)

  def userSignupAge(hasClientContext: HasClientContext): Option[Duration] =
    signupTime(hasClientContext).map(Time.now - _)
}
