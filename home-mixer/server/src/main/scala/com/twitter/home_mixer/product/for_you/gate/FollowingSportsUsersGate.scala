package com.twitter.home_mixer.product.for_you.gate

import com.twitter.home_mixer.model.HomeFeatures.FollowsSportsAccountFeature
import com.twitter.product_mixer.core.functional_component.gate.Gate
import com.twitter.product_mixer.core.model.common.identifier.GateIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch

/**
 * Continue for all users that follow a sports account
 */
object FollowingSportsUserGate extends Gate[PipelineQuery] {

  override val identifier: GateIdentifier = GateIdentifier("FollowingSportsUser")

  override def shouldContinue(query: PipelineQuery): Stitch[Boolean] = {
    val followsSportsAccount = query.features.map(_.getOrElse(FollowsSportsAccountFeature, false))
    Stitch.value(followsSportsAccount.contains(true))
  }
}
