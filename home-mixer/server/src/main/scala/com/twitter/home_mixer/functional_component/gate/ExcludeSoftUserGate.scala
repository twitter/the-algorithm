package com.ExTwitter.home_mixer.functional_component.gate

import com.ExTwitter.gizmoduck.{thriftscala => t}
import com.ExTwitter.home_mixer.model.HomeFeatures.UserTypeFeature
import com.ExTwitter.product_mixer.core.functional_component.gate.Gate
import com.ExTwitter.product_mixer.core.model.common.identifier.GateIdentifier
import com.ExTwitter.product_mixer.core.pipeline.PipelineQuery
import com.ExTwitter.stitch.Stitch

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
