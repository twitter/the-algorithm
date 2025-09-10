package com.twitter.home_mixer.product.scored_tweets.gate

import com.twitter.home_mixer.model.HomeFeatures.SignupSourceFeature
import com.twitter.home_mixer.model.signup.MarchMadness
import com.twitter.home_mixer.product.scored_tweets.param.ScoredTweetsParam.EnableLowSignalUserCheck
import com.twitter.home_mixer.util.SignalUtil
import com.twitter.product_mixer.core.functional_component.gate.Gate
import com.twitter.product_mixer.core.model.common.identifier.GateIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch

/**
 * Continue for all users except low signal users who also follow < 25 users
 *
 * Check gate param last to only evaluate for eligible users and avoid experimental dilution.
 */
object DenyLowSignalUserGate extends Gate[PipelineQuery] {

  override val identifier: GateIdentifier = GateIdentifier("DenyLowSignalUser")

  override def shouldContinue(query: PipelineQuery): Stitch[Boolean] = {
    val signupSource = query.features.flatMap(_.getOrElse(SignupSourceFeature, None))

    val stop = signupSource.contains(MarchMadness) &&
      SignalUtil.isLowSignalUser(query) &&
      query.params(EnableLowSignalUserCheck)

    Stitch.value(!stop)
  }
}
