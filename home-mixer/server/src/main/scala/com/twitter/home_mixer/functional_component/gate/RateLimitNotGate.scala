package com.twitter.home_mixer.functional_component.gate

import com.twitter.home_mixer.model.HomeFeatures.ViewerHasPremiumTier
import com.twitter.home_mixer.model.HomeFeatures.ViewerIsRateLimited
import com.twitter.product_mixer.core.functional_component.gate.Gate
import com.twitter.product_mixer.core.model.common.identifier.GateIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch

object RateLimitNotGate extends Gate[PipelineQuery] {

  override val identifier: GateIdentifier = GateIdentifier("RateLimitNot")

  override def shouldContinue(query: PipelineQuery): Stitch[Boolean] = {
    val isRateLimited = query.features.map(_.getOrElse(ViewerIsRateLimited, false))
    val hasPremiumTier = query.features.map(_.getOrElse(ViewerHasPremiumTier, false))
    Stitch.value(isRateLimited.contains(true) && hasPremiumTier.contains(false))
  }
}
