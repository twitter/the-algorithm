package com.twitter.tweet_mixer.functional_component.gate

import com.twitter.product_mixer.core.functional_component.gate.Gate
import com.twitter.product_mixer.core.model.common.identifier.GateIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch
import com.twitter.tweet_mixer.functional_component.hydrator.UserFollowersCountFeature
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.EnableMaxFollowersGate
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.MaxFollowersCountGateParam

/**
 * Continue for users who have < N followers
 *
 * Check the enabled param at the end to prevent experiment dilution
 */
object MaxFollowersGate extends Gate[PipelineQuery] {

  override val identifier: GateIdentifier = GateIdentifier("MaxFollowers")

  override def shouldContinue(query: PipelineQuery): Stitch[Boolean] = Stitch.value {
    val followers =
      query.features.flatMap(_.getOrElse(UserFollowersCountFeature, None)).getOrElse(0)
    val maxEligible = query.params(MaxFollowersCountGateParam)
    followers < maxEligible || !query.params(EnableMaxFollowersGate)
  }
}
