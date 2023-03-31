package com.twitter.product_mixer.core.pipeline.step

import com.twitter.product_mixer.core.service.Executor
import com.twitter.product_mixer.core.service.ExecutorResult
import com.twitter.stitch.Arrow

/**
 * A Step within a Pipeline, a Step is a unitary phase within an entire chain that makes up a pipeline.
 * @tparam State The request domain model.
 * @tparam ExecutorConfig The configs that should be passed into the executor at build time.
 * @tparam ExecutorInput The input type that an executor takes at request time.
 * @tparam ExResult The result that a step's executor will return.
 * @tparam OutputState The final/updated state a step would output, this is typically taking the ExResult
 *                     and mutating/transforming the State.
 */
trait Step[State, -Config, ExecutorInput, ExResult <: ExecutorResult] {

  /**
   * Adapt the state into the expected input for the Step's Arrow.
   *
   * @param state State object passed into the Step.
   * @param config The config object used to build the executor arrow or input.
   * @return ExecutorInput that is used in the arrow of the underlying executor.
   */
  def adaptInput(state: State, config: Config): ExecutorInput

  /**
   * The actual arrow to be executed for the step, taking in the adapted input from [[adaptInput]]
   * and returning the expected result.
   * @param config Runtime configurations to configure the arrow with.
   * @param context Context of Executor.
   */
  def arrow(
    config: Config,
    context: Executor.Context
  ): Arrow[ExecutorInput, ExResult]

  /**
   * Whether the step is considered a noop/empty based off of input being passed in. Empty
   * steps are skipped when being executed.
   */
  def isEmpty(config: Config): Boolean

  /**
   * Update the passed in state based off of the result from [[arrow]]
   * @param state State object passed into the Step.
   * @param executorResult Executor result returned from [[arrow]]
   * @param config The config object used to build the executor arrow or input.
   * @return Updated state object passed.
   */
  def updateState(state: State, executorResult: ExResult, config: Config): State
}
