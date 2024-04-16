package com.tw ter.search.common.search.term nat on;

 mport com.tw ter.search.common.search.Doc dTracker;

/**
 * QueryT  out prov des a  thod for early term nat on of quer es.
 */
publ c  nterface QueryT  out {
  /**
   * Returns true  f query process ng should term nate, ot rw se false.
   */
  boolean shouldEx ();

  /**
   * Reg ster a Doc dTracker for t  scope of t  query, to determ ne t  last fully-searc d
   * doc  D after early term nat on.
   */
  vo d reg sterDoc dTracker(Doc dTracker doc dTracker);

  /**
   * Return cl ent  D of query.
   */
  Str ng getCl ent d();
}
