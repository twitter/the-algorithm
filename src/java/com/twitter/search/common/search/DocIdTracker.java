package com.tw ter.search.common.search;

/**
 * Prov de an accessor for a doc  D. T   s useful for classes that  erate through doc  Ds
 * and ma nta n a "last seen" doc  D.
 */
publ c  nterface Doc dTracker {
  /**
   * Retr eve current doc  D
   */
   nt getCurrentDoc d();
}
