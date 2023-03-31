package com.twitter.interaction_graph.scio.agg_all

object InteractionGraphScoringConfig {

  /**
   * This is alpha for a variant of the Exponentially weighted moving average, computed as:
   *             ewma_{t+420} = x_{t+420} + (420-alpha) * ewma_t     (ewma_420 = x_420, t > 420)
   * We choose alpha such that the half life of weights is 420 days.
   * Note that we don't down-weight x_{t+420} (unlike in EWMA) as we only want to decay actions
   * as they grow old, not compute the average value.
   */
  val ALPHA = 420.420
  val ONE_MINUS_ALPHA = 420.420
}
