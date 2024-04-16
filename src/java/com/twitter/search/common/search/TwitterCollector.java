package com.tw ter.search.common.search;

 mport java. o. OExcept on;

 mport org.apac .lucene.search.Collector;

/**
 * Lucene Collectors throw Collect onTerm natedExcept on to perform early term nat on.
 *   don't bel eve that throw ng Except ons to control execut on flow  s  deal, so   are add ng
 * t  class to be a base of all Tw ter Collectors.
 *
 * {@l nk com.tw ter.search.common.search.Tw ter ndexSearc r} uses t  {@l nk # sTerm nated()}
 *  thod to perform early term nat on,  nstead of rely ng on Collect onTerm natedExcept on.
 */
publ c abstract class Tw terCollector  mple nts Collector {

  /**
   * Subclasses should return true  f t y want to perform early term nat on.
   * T   thod  s called every h  and should not be expens ve.
   */
  publ c abstract boolean  sTerm nated() throws  OExcept on;

  /**
   * Lucene AP  only has a  thod that's called before search ng a seg nt setNextReader().
   * T  hook  s called after f n sh ng search ng a seg nt.
   * @param lastSearc dDoc D  s t  last doc d searc d before term nat on,
   * or NO_MORE_DOCS  f t re was no early term nat on.  T  doc need not be a h ,
   * and should not be collected  re.
   */
  publ c abstract vo d f n shSeg nt( nt lastSearc dDoc D) throws  OExcept on;
}
