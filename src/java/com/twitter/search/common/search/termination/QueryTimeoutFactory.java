package com.tw ter.search.common.search.term nat on;

 mport com.tw ter.common.ut l.Clock;
 mport com.tw ter.search.common.search.Term nat onTracker;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdRequest;

publ c class QueryT  outFactory {
  /**
   * Creates a QueryT  out  nstance for a g ven Earlyb rdRequest and Term nat onTracker,  f t 
   * requ red cond  ons for leaf-level t  out c ck ng are  t. Returns null ot rw se.
   *
   * T  cond  ons are:
   *   1) CollectorTerm nat onParams. sEnforceQueryT  out()
   *   2) CollectorTerm nat onParams. sSetT  outMs()
   */
  publ c QueryT  out createQueryT  out(
      Earlyb rdRequest request,
      Term nat onTracker tracker,
      Clock clock) {
     f (tracker != null
        && request != null
        && request. sSetSearchQuery()
        && request.getSearchQuery(). sSetCollectorParams()
        && request.getSearchQuery().getCollectorParams(). sSetTerm nat onParams()
        && request.getSearchQuery().getCollectorParams().getTerm nat onParams()
            . sEnforceQueryT  out()
        && request.getSearchQuery().getCollectorParams().getTerm nat onParams()
            . sSetT  outMs()) {
      return new QueryT  out mpl(request.getCl ent d(), tracker, clock);
    } else {
      return null;
    }
  }
}
