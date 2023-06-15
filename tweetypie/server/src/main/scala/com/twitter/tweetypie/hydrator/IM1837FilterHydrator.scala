package com.twitter.tweetypie
package hydrator

import com.twitter.coreservices.IM1837
import com.twitter.tweetypie.core._
import com.twitter.stitch.Stitch

object IM1837FilterHydrator {
  type Type = ValueHydrator[Unit, TweetCtx]

  private val Drop =
    Stitch.exception(FilteredState.Unavailable.DropUnspecified)
  private val Success = Stitch.value(ValueState.unmodified(()))

  def apply(): Type =
    ValueHydrator[Unit, TweetCtx] { (_, ctx) =>
      val userAgent = TwitterContext().flatMap(_.userAgent)
      val userAgentAffected = userAgent.exists(IM1837.isAffectedClient)
      val mightCrash = userAgentAffected && IM1837.textMightCrashIOS(ctx.text)

      if (mightCrash) Drop else Success
    }
}
