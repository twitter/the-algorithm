package com.tw ter. nteract on_graph.sc o.agg_all

object  nteract onGraphScor ngConf g {

  /**
   * T   s alpha for a var ant of t  Exponent ally   ghted mov ng average, computed as:
   *             ewma_{t+1} = x_{t+1} + (1-alpha) * ewma_t     (ewma_1 = x_1, t > 0)
   *   choose alpha such that t  half l fe of   ghts  s 7 days.
   * Note that   don't down-  ght x_{t+1} (unl ke  n EWMA) as   only want to decay act ons
   * as t y grow old, not compute t  average value.
   */
  val ALPHA = 1.0
  val ONE_M NUS_ALPHA = 0.955
}
