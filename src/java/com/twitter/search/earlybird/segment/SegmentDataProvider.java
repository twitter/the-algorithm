package com.tw ter.search.earlyb rd.seg nt;

/**
 * Seg ntDataProv der prov des  nformat on about ava lable seg nts for  ndex ng. T   nterface
 * abstracts away t  actual s ce of t  seg nt data.   m ght be a  SQL database, a mock
 * object, or a d rectory of flat f les.   also prov des access to t  seg nt nfoMap  self, wh ch
 * conta ns  nformat on about t   ndex ng state of Seg nts.
 */
publ c  nterface Seg ntDataProv der extends Seg ntProv der {
  /**
   * Returns t  set of seg nt data record readers.
   */
  Seg ntDataReaderSet getSeg ntDataReaderSet();
}
