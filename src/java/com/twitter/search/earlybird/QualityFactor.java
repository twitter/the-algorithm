package com.tw ter.search.earlyb rd;

/**
 *  nterface def n ng a qual y factor.
 */
publ c  nterface Qual yFactor {
  /**
   * Returns t  current qual y factor.
   * @return T  qual y factor; a number bet en 0.0 and 1.0.
   */
  double get();

  /**
   * Starts a thread to update t  qual y factor per od cally.
   */
  vo d startUpdates();
}
