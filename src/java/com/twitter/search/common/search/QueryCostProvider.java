package com.twitter.search.common.search;

/**
 * Any class that can track and return query cost.
 */
public interface QueryCostProvider {
  /** Returns the total cost. */
  double getTotalCost();
}
