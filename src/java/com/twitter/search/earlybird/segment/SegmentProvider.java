package com.tw ter.search.earlyb rd.seg nt;

 mport java. o. OExcept on;
 mport java.ut l.L st;

 mport com.tw ter.search.common.part  on ng.base.Seg nt;

publ c  nterface Seg ntProv der {
  /**
   * Returns a *new* sorted l st of all ava lable seg nts on d sk / db / hdfs / etc.
   */
  L st<Seg nt> newSeg ntL st() throws  OExcept on;
}
