package com.X.home_mixer.functional_component.gate

import com.X.gizmoduck.{thriftscala => t}
import com.X.home_mixer.model.HomeFeatures.UserTypeFeature
import com.X.product_mixer.core.functional_component.gate.Gate
import com.X.product_mixer.core.model.common.identifier.GateIdentifier
import com.X.product_mixer.core.pipeline.PipelineQuery
import com.X.stitch.Stitch

/**
 * A Soft User is a user who is in the gradual onboarding state. This gate can be
 * used to turn off certain functionality like ads for these users.
 */
object ExcludeSoftUserGate extends Gate[PipelineQuery] {

  override val identifier: GateIdentifier = GateIdentifier("ExcludeSoftUser")

  override def shouldContinue(query: PipelineQuery): Stitch[Boolean] = {
    val softUser = query.features
      .exists(_.getOrElse(UserTypeFeature, None).exists(_ == t.UserType.Soft))
    Stitch.value(!softUser)
  }
}
