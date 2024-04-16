package com.tw ter.search.common.encod ng.features;

/**
 *  nterface for process ng d fferent feature values  nto an  nt.   prov des a one-way translat on
 * of encod ng us ng com.tw ter.search.common.encod ng.features.ByteNormal zer and supports all t 
 * old normal zers. T  d fference  s that   d rectly return t  normal zed  nt value
 * ( nstead of convert ng from byte).
 */
publ c  nterface  ntNormal zer {
  /**
   * Returns t  normal zed value of {@code val}.
   * T  value may be byte-compressed or as- s depend ng on t  normal zer type
   */
   nt normal ze(double val);
}
