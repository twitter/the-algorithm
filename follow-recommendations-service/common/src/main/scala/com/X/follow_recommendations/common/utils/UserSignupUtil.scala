package com.X.follow_recommendations.common.utils

import com.X.product_mixer.core.model.marshalling.request.HasClientContext
import com.X.snowflake.id.SnowflakeId
import com.X.util.Duration
import com.X.util.Time

object UserSignupUtil {
  def signupTime(hasClientContext: HasClientContext): Option[Time] =
    hasClientContext.clientContext.userId.flatMap(SnowflakeId.timeFromIdOpt)

  def userSignupAge(hasClientContext: HasClientContext): Option[Duration] =
    signupTime(hasClientContext).map(Time.now - _)
}
