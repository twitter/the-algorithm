package com.twitter.product_mitwitterer.core.pipeline.state

/**
 * Defines how to build a result from a pipeline state. Pipeline States should etwittertend this and
 * implement [[buildResult]] which computes the final result from their current state.
 * @tparam Result Type of result
 */
trait HasResult[+Result] {
  def buildResult(): Result
}
