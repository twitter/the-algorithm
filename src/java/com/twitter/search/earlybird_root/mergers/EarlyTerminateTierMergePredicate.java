package com.tw ter.search.earlyb rd_root. rgers;

publ c  nterface EarlyTerm nateT er rgePred cate {
  /**
   * Do   have enough results so far that   can early term nate and not cont nue onto next t er?
   */
  boolean shouldEarlyTerm nateT er rge( nt totalResultsFromSuccessfulShards,
                                        boolean foundEarlyTerm nat on);
}
