package com.twitter.tweetypie
package hydrator

import com.twitter.coreservices.IM1837
import com.twitter.tweetypie.core._
import com.twitter.stitch.Stitch

object IM1837FilterHydrator {
  type Type = ValueHydrator[Unit, TweetCttwitter]

  private val Drop =
    Stitch.etwitterception(FilteredState.Unavailable.DropUnspecified)
  private val Success = Stitch.value(ValueState.unmodified(()))

  def apply(): Type =
    ValueHydrator[Unit, TweetCttwitter] { (_, cttwitter) =>
      val userAgent = TwitterContetwittert().flatMap(_.userAgent)
      val userAgentAffected = userAgent.etwitterists(IM1837.isAffectedClient)
      val mightCrash = userAgentAffected && IM1837.tetwittertMightCrashIOS(cttwitter.tetwittert)

      if (mightCrash) Drop else Success
    }
}
