package com.twitter.home_mitwitterer.functional_component.gate

import com.twitter.gizmoduck.{thriftscala => t}
import com.twitter.home_mitwitterer.model.HomeFeatures.UserTypeFeature
import com.twitter.product_mitwitterer.core.functional_component.gate.Gate
import com.twitter.product_mitwitterer.core.model.common.identifier.GateIdentifier
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch

/**
 * A Soft User is a user who is in the gradual onboarding state. This gate can be
 * used to turn off certain functionality like ads for these users.
 */
object EtwittercludeSoftUserGate etwittertends Gate[PipelineQuery] {

  override val identifier: GateIdentifier = GateIdentifier("EtwittercludeSoftUser")

  override def shouldContinue(query: PipelineQuery): Stitch[Boolean] = {
    val softUser = query.features
      .etwitterists(_.getOrElse(UserTypeFeature, None).etwitterists(_ == t.UserType.Soft))
    Stitch.value(!softUser)
  }
}
