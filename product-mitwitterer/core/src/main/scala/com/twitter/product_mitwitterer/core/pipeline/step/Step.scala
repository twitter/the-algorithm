package com.twitter.product_mitwitterer.core.pipeline.step

import com.twitter.product_mitwitterer.core.service.Etwitterecutor
import com.twitter.product_mitwitterer.core.service.EtwitterecutorResult
import com.twitter.stitch.Arrow

/**
 * A Step within a Pipeline, a Step is a unitary phase within an entire chain that makes up a pipeline.
 * @tparam State The request domain model.
 * @tparam EtwitterecutorConfig The configs that should be passed into the etwitterecutor at build time.
 * @tparam EtwitterecutorInput The input type that an etwitterecutor takes at request time.
 * @tparam EtwitterResult The result that a step's etwitterecutor will return.
 * @tparam OutputState The final/updated state a step would output, this is typically taking the EtwitterResult
 *                     and mutating/transforming the State.
 */
trait Step[State, -Config, EtwitterecutorInput, EtwitterResult <: EtwitterecutorResult] {

  /**
   * Adapt the state into the etwitterpected input for the Step's Arrow.
   *
   * @param state State object passed into the Step.
   * @param config The config object used to build the etwitterecutor arrow or input.
   * @return EtwitterecutorInput that is used in the arrow of the underlying etwitterecutor.
   */
  def adaptInput(state: State, config: Config): EtwitterecutorInput

  /**
   * The actual arrow to be etwitterecuted for the step, taking in the adapted input from [[adaptInput]]
   * and returning the etwitterpected result.
   * @param config Runtime configurations to configure the arrow with.
   * @param contetwittert Contetwittert of Etwitterecutor.
   */
  def arrow(
    config: Config,
    contetwittert: Etwitterecutor.Contetwittert
  ): Arrow[EtwitterecutorInput, EtwitterResult]

  /**
   * Whether the step is considered a noop/empty based off of input being passed in. Empty
   * steps are skipped when being etwitterecuted.
   */
  def isEmpty(config: Config): Boolean

  /**
   * Update the passed in state based off of the result from [[arrow]]
   * @param state State object passed into the Step.
   * @param etwitterecutorResult Etwitterecutor result returned from [[arrow]]
   * @param config The config object used to build the etwitterecutor arrow or input.
   * @return Updated state object passed.
   */
  def updateState(state: State, etwitterecutorResult: EtwitterResult, config: Config): State
}
