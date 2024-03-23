package com.ExTwitter.follow_recommendations.common.utils

import com.ExTwitter.product_mixer.core.model.marshalling.request.HasClientContext
import com.ExTwitter.snowflake.id.SnowflakeId
import com.ExTwitter.util.Duration
import com.ExTwitter.util.Time

object UserSignupUtil {
  def signupTime(hasClientContext: HasClientContext): Option[Time] =
    hasClientContext.clientContext.userId.flatMap(SnowflakeId.timeFromIdOpt)

  def userSignupAge(hasClientContext: HasClientContext): Option[Duration] =
    signupTime(hasClientContext).map(Time.now - _)
}
