package com.tw ter.search.common.search;

 mport java.ut l.HashSet;
 mport java.ut l.Set;

 mport com.google.common.base.Precond  ons;

 mport com.tw ter.common.ut l.Clock;
 mport com.tw ter.search.common.query.thr ftjava.CollectorTerm nat onParams;

/**
 * Used for track ng term nat on cr er a for earlyb rd quer es.
 *
 * Currently t  tracks t  query t   out and query cost,  f t y are set on t 
 * {@l nk com.tw ter.search.common.query.thr ftjava.CollectorTerm nat onParams}.
 */
publ c class Term nat onTracker {
  /** Query start t   prov ded by cl ent. */
  pr vate f nal long cl entStartT  M ll s;

  /** T  out end t  s, calculated from {@l nk #cl entStartT  M ll s}. */
  pr vate f nal long t  outEndT  M ll s;

  /** Query start t   recorded at earlyb rd server. */
  pr vate f nal long localStartT  M ll s;

  /** Track ng query cost */
  pr vate f nal double maxQueryCost;

  // So t  s,   want to early term nate before t  outEndT  M ll s, to reserve t   for
  // work that needs to be done after early term nat on (E.g.  rg ng results).
  pr vate f nal  nt postTerm nat onOver adM ll s;

  //   don't c ck for early term nat on often enough. So  t  s requests t  out  n bet en
  // early term nat on c cks. T  buffer t    s also substracted from deadl ne.
  // To  llustrate how t   s used, let's use a s mple example:
  //  f   spent 750ms search ng 5 seg nts, a rough est mate  s that   need 150ms to search
  // one seg nt.  f t  t  out  s set to 800ms,   should not start ng search ng t  next seg nt.
  //  n t  case, on can set preTerm nat onSafeBufferT  M ll s to 150ms, so that w n early
  // term nat on c ck computes t  deadl ne, t  buffer  s also subtracted. See SEARCH-29723.
  pr vate  nt preTerm nat onSafeBufferT  M ll s = 0;

  pr vate EarlyTerm nat onState earlyTerm nat onState = EarlyTerm nat onState.COLLECT NG;

  // T  flag determ nes w t r t  last searc d doc  D trackers should be consulted w n a
  // t  out occurs.
  pr vate f nal boolean useLastSearc dDoc dOnT  out;

  pr vate f nal Set<Doc dTracker> lastSearc dDoc dTrackers = new HashSet<>();

  /**
   * Creates a new term nat on tracker that w ll not spec fy a t  out or max query cost.
   * Can be used for quer es that expl c ly do not want to use a t  out.  ant to be used for
   * tests, and background quer es runn ng for t  query cac .
   */
  publ c Term nat onTracker(Clock clock) {
    t .cl entStartT  M ll s = clock.nowM ll s();
    t .localStartT  M ll s = cl entStartT  M ll s;
    t .t  outEndT  M ll s = Long.MAX_VALUE;
    t .maxQueryCost = Double.MAX_VALUE;
    t .postTerm nat onOver adM ll s = 0;
    t .useLastSearc dDoc dOnT  out = false;
  }

  /**
   * Conven ent  thod overload ng for
   * {@l nk #Term nat onTracker(CollectorTerm nat onParams, long, Clock,  nt)}.
   */
  publ c Term nat onTracker(
      CollectorTerm nat onParams term nat onParams, Clock clock,
       nt postTerm nat onOver adM ll s) {
    t (term nat onParams, clock.nowM ll s(), clock, postTerm nat onOver adM ll s);
  }

  /**
   * Conven ent  thod overload ng for
   * {@l nk #Term nat onTracker(CollectorTerm nat onParams, long, Clock,  nt)}.
   */
  publ c Term nat onTracker(
      CollectorTerm nat onParams term nat onParams,  nt postTerm nat onOver adM ll s) {
    t (
        term nat onParams,
        System.currentT  M ll s(),
        Clock.SYSTEM_CLOCK,
        postTerm nat onOver adM ll s);
  }

  /**
   * Creates a new Term nat onTracker  nstance.
   *
   * @param term nat onParams  CollectorParams.CollectorTerm nat onParams carry ng para ters
   *                           about early term nat on.
   * @param cl entStartT  M ll s T  query start t   ( n m ll s) spec f ed by cl ent. T   s used
   *                              to calculate t  out end t  , l ke {@l nk #t  outEndT  M ll s}.
   * @param clock used to sample {@l nk #localStartT  M ll s}.
   * @param postTerm nat onOver adM ll s How much t   should be reserved.  E.g.  f request t  
   *                                      out  s 800ms, and t   s set to 200ms, early term nat on
   *                                      w ll k ck  n at 600ms mark.
   */
  publ c Term nat onTracker(
      CollectorTerm nat onParams term nat onParams,
      long cl entStartT  M ll s,
      Clock clock,
       nt postTerm nat onOver adM ll s) {
    Precond  ons.c ckNotNull(term nat onParams);
    Precond  ons.c ckArgu nt(postTerm nat onOver adM ll s >= 0);

    t .cl entStartT  M ll s = cl entStartT  M ll s;
    t .localStartT  M ll s = clock.nowM ll s();

     f (term nat onParams. sSetT  outMs()
        && term nat onParams.getT  outMs() > 0) {
      Precond  ons.c ckState(term nat onParams.getT  outMs() >= postTerm nat onOver adM ll s);
      t .t  outEndT  M ll s = t .cl entStartT  M ll s + term nat onParams.getT  outMs();
    } else {
      // Effect vely no t  out.
      t .t  outEndT  M ll s = Long.MAX_VALUE;
    }

    // Track ng query cost
     f (term nat onParams. sSetMaxQueryCost()
        && term nat onParams.getMaxQueryCost() > 0) {
      maxQueryCost = term nat onParams.getMaxQueryCost();
    } else {
      maxQueryCost = Double.MAX_VALUE;
    }

    t .useLastSearc dDoc dOnT  out = term nat onParams. sEnforceQueryT  out();
    t .postTerm nat onOver adM ll s = postTerm nat onOver adM ll s;
  }

  /**
   * Returns t  reserve t   to perform post term nat on work. Return t  deadl ne t  stamp
   * w h postTerm nat onWorkEst mate subtracted.
   */
  publ c long getT  outEndT  W hReservat on() {
    // Return huge value  f t   out  s d sabled.
     f (t  outEndT  M ll s == Long.MAX_VALUE) {
      return t  outEndT  M ll s;
    } else {
      return t  outEndT  M ll s
          - postTerm nat onOver adM ll s
          - preTerm nat onSafeBufferT  M ll s;
    }
  }

  publ c vo d setPreTerm nat onSafeBufferT  M ll s( nt preTerm nat onSafeBufferT  M ll s) {
    Precond  ons.c ckArgu nt(preTerm nat onSafeBufferT  M ll s >= 0);

    t .preTerm nat onSafeBufferT  M ll s = preTerm nat onSafeBufferT  M ll s;
  }

  publ c long getLocalStartT  M ll s() {
    return localStartT  M ll s;
  }

  publ c long getCl entStartT  M ll s() {
    return cl entStartT  M ll s;
  }

  publ c double getMaxQueryCost() {
    return maxQueryCost;
  }

  publ c boolean  sEarlyTerm nated() {
    return earlyTerm nat onState. sTerm nated();
  }

  publ c EarlyTerm nat onState getEarlyTerm nat onState() {
    return earlyTerm nat onState;
  }

  publ c vo d setEarlyTerm nat onState(EarlyTerm nat onState earlyTerm nat onState) {
    t .earlyTerm nat onState = earlyTerm nat onState;
  }

  /**
   * Return t  m n mum searc d doc  D amongst all reg stered trackers, or -1  f t re aren't any
   * trackers. Doc  Ds are stored  n ascend ng order, and trackers update t  r doc  Ds as t y
   * search, so t  m n mum doc  D reflects t  most recent fully searc d doc  D.
   */
   nt getLastSearc dDoc d() {
    return lastSearc dDoc dTrackers.stream()
        .mapTo nt(Doc dTracker::getCurrentDoc d).m n().orElse(-1);
  }

  vo d resetDoc dTrackers() {
    lastSearc dDoc dTrackers.clear();
  }

  /**
   * Add a Doc dTracker, to keep track of t  last fully-searc d doc  D w n early term nat on
   * occurs.
   */
  publ c vo d addDoc dTracker(Doc dTracker doc dTracker) {
    lastSearc dDoc dTrackers.add(doc dTracker);
  }

  publ c boolean useLastSearc dDoc dOnT  out() {
    return useLastSearc dDoc dOnT  out;
  }
}
