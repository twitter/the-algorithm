package com.twitter.tweet_mixer.functional_component.gate

import com.twitter.product_mixer.core.functional_component.gate.Gate
import com.twitter.product_mixer.core.model.common.identifier.GateIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch
import com.twitter.tweet_mixer.functional_component.hydrator.SGSFollowedUsersFeature
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.EnableLowSignalUserCheck
import com.twitter.tweet_mixer.utils.SignalUtils

/**
 * Continue for low signal users who also follow < 25 users
 *
 * Check gate param last to only evaluate for eligible users and avoid experimental dilution.
 */
object AllowLowSignalUserGate extends Gate[PipelineQuery] {

  override val identifier: GateIdentifier = GateIdentifier("AllowLowSignalUser")

  override def shouldContinue(query: PipelineQuery): Stitch[Boolean] =
    Stitch.value(evaluate(query))

  def evaluate(query: PipelineQuery): Boolean = {
    val followGraphSize = query.features.map(_.getOrElse(SGSFollowedUsersFeature, Seq.empty).size)

    SignalUtils.isLowSignalUser(query, followGraphSize) &&
    query.params(EnableLowSignalUserCheck)
  }
}
