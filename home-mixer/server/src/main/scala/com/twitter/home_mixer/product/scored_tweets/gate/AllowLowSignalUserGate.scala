package com.twitter.home_mixer.product.scored_tweets.gate

import com.twitter.home_mixer.product.scored_tweets.param.ScoredTweetsParam.EnableLowSignalUserCheck
import com.twitter.home_mixer.util.SignalUtil
import com.twitter.product_mixer.core.functional_component.gate.Gate
import com.twitter.product_mixer.core.model.common.identifier.GateIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch

/**
 * Continue for low signal users who also follow < 5 users
 *
 * Check gate param last to only evaluate for eligible users and avoid experimental dilution.
 */
object AllowLowSignalUserGate extends Gate[PipelineQuery] {

  override val identifier: GateIdentifier = GateIdentifier("AllowLowSignalUser")

  override def shouldContinue(query: PipelineQuery): Stitch[Boolean] = {
    val continue = 
      SignalUtil.isLowSignalUser(query) &&
      query.params(EnableLowSignalUserCheck)

    Stitch.value(continue)
  }
}
