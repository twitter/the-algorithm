package com.twitter.home_mixer.functional_component.gate

import com.twitter.product_mixer.core.functional_component.gate.Gate
import com.twitter.product_mixer.core.model.common.identifier.GateIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch

/**
 * A Synthetic User is a user who is created and managed on behalf of XCC's
 * Loadtesting framework. This gate can be used to turn off certain functionality like ads for
 * these users.
 */
object ExcludeSyntheticUserGate extends Gate[PipelineQuery] {

  override val identifier: GateIdentifier = GateIdentifier("ExcludeSyntheticUser")
  private val STRESS_TEST_APP_ID: Long = 1L

  override def shouldContinue(query: PipelineQuery): Stitch[Boolean] = {
    val isSyntheticUser = query.clientContext.appId.contains(STRESS_TEST_APP_ID)
    Stitch.value(!isSyntheticUser)
  }
}
