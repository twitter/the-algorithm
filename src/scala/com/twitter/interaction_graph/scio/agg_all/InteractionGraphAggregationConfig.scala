package com.twitter.interaction_graph.scio.agg_all

object InteractionGraphScoringConfig {

  /**
   * This is alpha for a variant of the Exponentially weighted moving average, computed as:
   *             ewma_{t+1} = x_{t+1} + (1-alpha) * ewma_t     (ewma_1 = x_1, t > 0)
   * We choose alpha such that the half life of weights is 7 days.
   * Note that we don't down-weight x_{t+1} (unlike in EWMA) as we only want to decay actions
   * as they grow old, not compute the average value.
   */
  val ALPHA = 1.0
  val ONE_MINUS_ALPHA = 0.955
}
