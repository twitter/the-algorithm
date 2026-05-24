package com.twitter.home_mixer.product.for_you.gate

import com.twitter.home_mixer.model.HomeFeatures.UserFollowingCountFeature
import com.twitter.home_mixer.product.for_you.param.ForYouParam.{MinFollowingCountParam, MaxFollowingCountParam}
import com.twitter.product_mixer.core.functional_component.gate.Gate
import com.twitter.product_mixer.core.model.common.identifier.GateIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch

/**
 * Gate that checks if the user's following count is within a valid range.
 * The range is controlled by configurable parameters for lower and upper bounds.
 */
object UserFollowingRangeGate extends Gate[PipelineQuery] {

  override val identifier: GateIdentifier = GateIdentifier("UserFollowingRange")

  override def shouldContinue(
    query: PipelineQuery
  ): Stitch[Boolean] = {
    val userFollowingCount = query.features.get.getOrElse(UserFollowingCountFeature, None)
    val lowerBound = query.params(MinFollowingCountParam)
    val upperBound = query.params(MaxFollowingCountParam)

    val isInRange = userFollowingCount match {
      case Some(count) => count >= lowerBound && count <= upperBound
      case None => false // If we don't have the following count, don't continue
    }

    Stitch.value(isInRange)
  }
} 