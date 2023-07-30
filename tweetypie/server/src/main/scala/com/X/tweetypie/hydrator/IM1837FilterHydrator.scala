package com.X.tweetypie
package hydrator

import com.X.coreservices.IM1837
import com.X.tweetypie.core._
import com.X.stitch.Stitch

object IM1837FilterHydrator {
  type Type = ValueHydrator[Unit, TweetCtx]

  private val Drop =
    Stitch.exception(FilteredState.Unavailable.DropUnspecified)
  private val Success = Stitch.value(ValueState.unmodified(()))

  def apply(): Type =
    ValueHydrator[Unit, TweetCtx] { (_, ctx) =>
      val userAgent = XContext().flatMap(_.userAgent)
      val userAgentAffected = userAgent.exists(IM1837.isAffectedClient)
      val mightCrash = userAgentAffected && IM1837.textMightCrashIOS(ctx.text)

      if (mightCrash) Drop else Success
    }
}
