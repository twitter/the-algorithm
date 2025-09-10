package com.twitter.home_mixer.functional_component.gate

import javax.inject.Inject
import javax.inject.Singleton

import com.twitter.product_mixer.component_library.module.TestUserMapper
import com.twitter.product_mixer.core.functional_component.gate.Gate
import com.twitter.product_mixer.core.model.common.identifier.GateIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch

/**
 * Generic gate used to assign a probability that the associated Component / Pipeline operates
 * on a Synthetic test user.
 *
 * @param testUserMapper the testUserMapper utility object that evaluates if this is a test user
 */
@Singleton
class TestUserProbabilisticGate @Inject() (testUserMapper: TestUserMapper)
    extends Gate[PipelineQuery] {

  override val identifier: GateIdentifier = GateIdentifier("TestUserProbabilistic")
  private val TEST_USERS_GATE_PROBABILITY = 0.05

  override def shouldContinue(query: PipelineQuery): Stitch[Boolean] = {
    if (!testUserMapper.isTestUser(
        query.clientContext) || math.random < TEST_USERS_GATE_PROBABILITY) {
      Stitch.True
    } else {
      Stitch.False
    }
  }
}
