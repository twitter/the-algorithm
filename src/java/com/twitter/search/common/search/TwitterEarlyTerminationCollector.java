package com.tw ter.search.common.search;

 mport java. o. OExcept on;
 mport java.ut l.L st;
 mport javax.annotat on.Nonnull;
 mport javax.annotat on.Nullable;

 mport com.google.common.annotat ons.V s bleForTest ng;
 mport com.google.common.base.Precond  ons;

 mport org.apac .lucene. ndex.LeafReader;
 mport org.apac .lucene. ndex.LeafReaderContext;
 mport org.apac .lucene.search.LeafCollector;
 mport org.apac .lucene.search.Scorable;
 mport org.slf4j.Logger;
 mport org.slf4j.LoggerFactory;

 mport com.tw ter.common.ut l.Clock;
 mport com.tw ter.search.common. tr cs.SearchCounter;
 mport com.tw ter.search.common. tr cs.SearchRateCounter;
 mport com.tw ter.search.common.query.thr ftjava.CollectorParams;
 mport com.tw ter.search.common.query.thr ftjava.CollectorTerm nat onParams;

/**
 * A Tw terCollector conta n ng t  most common early term nat on log c based on
 * t  out, cost, and max h s. T  class does not do any actual h  collect on---t  class
 *  s abstract and cannot be  nstant ated.
 *
 *  f a Collector and all  s subclasses need early term nat on,   should extend t  class.
 *
 * Ho ver,  f one just wants to add EarlyTerm nat on to any s ngle collector,   can just
 * use {@l nk Delegat ngEarlyTerm nat onCollector}
 * as a wrapper.
 */
publ c abstract class Tw terEarlyTerm nat onCollector
    extends Tw terCollector  mple nts LeafCollector {
  pr vate stat c f nal Logger LOG = LoggerFactory.getLogger(Tw terEarlyTerm nat onCollector.class);
  pr vate stat c f nal SearchCounter NEGAT VE_T ME_PER_SEGMENT =
      SearchCounter.export("Tw terEarlyTerm nat onCollector_negat ve_t  _per_seg nt");
  pr vate stat c f nal SearchRateCounter QUERY_T MEOUT_ENFORCED =
      SearchRateCounter.export("Tw terEarlyTerm nat onCollector_query_t  out_enforced");

  protected  nt curDoc d = -1;

  protected Scorable scorer = null;
  pr vate LeafReader curReader = null;
  pr vate f nal long maxH sToProcess;
  pr vate long numH sProcessed = 0;
  pr vate  nt lastEarlyTerm nat onC ckDoc d = -1;
  pr vate f nal Clock clock;

  @Nullable
  pr vate f nal QueryCostProv der queryCostProv der;

  pr vate f nal Term nat onTracker term nat onTracker;

  // T  determ nes how often t  expens ve early term nat on c ck  s perfor d.
  //  f set to be negat ve, expens ve early term nat on c ck only perfor d at seg nt boundar es.
  //  f set to a pos  ve number X, t  c ck  s perfor d every X docs processed.
  pr vate  nt numDocsBet enT  outC cks;

  // Number of seg nts searc d so far.
  // T   s used to pred cat vely early term nate.
  // Expens ve early term nat on c cks may not happen often enough. So t  s t  request
  // t  s out  n bet en t  term nat on c cks.
  // After f n sh ng search ng a seg nt,   est mate how much t    s needed to search one
  // seg nt on average.   f search ng t  next seg nt would cause a t  out,   early term nate.
  pr vate  nt numSearc dSeg nts = 0;

  /**
   * Creates a new Tw terEarlyTerm nat onCollector  nstance.
   *
   * @param collectorParams t  para ters needed to gu de early term nat on.
   * @param term nat onTracker  f null  s passed  n, a new Term nat onTrack  s created. Ot rw se,
   *        t  one passed  n  s used.
   * @param numDocsBet enT  outC cks Term nat onTracker based c ck are perfor d upon a h 
   *        every numDocsBet enT  outC cks docs.  f a non-pos  ve number  s passed
   *         n, Term nat onTracker based c cks are d sabled.
   *         f collectorParams spec f es a value as  ll, that value  s used.
   */
  publ c Tw terEarlyTerm nat onCollector(
      CollectorParams collectorParams,
      Term nat onTracker term nat onTracker,
      @Nullable QueryCostProv der queryCostProv der,
       nt numDocsBet enT  outC cks,
      Clock clock) {
    CollectorTerm nat onParams term nat onParams = collectorParams.getTerm nat onParams();

     f (term nat onParams == null) {
      term nat onParams = new CollectorTerm nat onParams()
          .setMaxH sToProcess( nteger.MAX_VALUE)
          .setMaxQueryCost(Double.MAX_VALUE)
          .setT  outMs( nteger.MAX_VALUE);
    }

     f (!term nat onParams. sSetMaxH sToProcess() || term nat onParams.getMaxH sToProcess() < 0) {
      maxH sToProcess =  nteger.MAX_VALUE;
    } else {
      maxH sToProcess = term nat onParams.getMaxH sToProcess();
    }

     f (term nat onParams. sSetNumDocsBet enT  outC cks()) {
      t .numDocsBet enT  outC cks = term nat onParams.getNumDocsBet enT  outC cks();
    } else {
      t .numDocsBet enT  outC cks = numDocsBet enT  outC cks;
    }

    t .term nat onTracker = Precond  ons.c ckNotNull(term nat onTracker);
    t .queryCostProv der = queryCostProv der;
    t .clock = clock;
  }

  publ c f nal LeafCollector getLeafCollector(LeafReaderContext context) throws  OExcept on {
    t .setNextReader(context);
    return t ;
  }

  /**
   * Sub-classes may overr de t  to add more collect on log c.
   */
  protected abstract vo d doCollect() throws  OExcept on;

  /**
   * Sub-classes may overr de t  to add more seg nt complet on log c.
   * @param lastSearc dDoc D  s t  last doc d searc d before term nat on,
   * or NO_MORE_DOCS  f t re was no early term nat on.  T  doc may not be a h !
   */
  protected abstract vo d doF n shSeg nt( nt lastSearc dDoc D) throws  OExcept on;

  /**
   *  sub classes can overr de t  to perform more early term nat on c cks.
   */
  publ c EarlyTerm nat onState  nnerShouldCollectMore() throws  OExcept on {
    return EarlyTerm nat onState.COLLECT NG;
  }

  /**
   * After early term nat on, t   thod can be used to retr eve early term nat on reason.
   */
  @Nonnull
  publ c f nal EarlyTerm nat onState getEarlyTerm nat onState() {
    return term nat onTracker.getEarlyTerm nat onState();
  }

  protected f nal EarlyTerm nat onState setEarlyTerm nat onState(
      EarlyTerm nat onState newEarlyTerm nat onState) {
    term nat onTracker.setEarlyTerm nat onState(newEarlyTerm nat onState);
    return newEarlyTerm nat onState;
  }

  @Overr de
  publ c f nal boolean  sTerm nated() throws  OExcept on {
    EarlyTerm nat onState earlyTerm nat onState = getEarlyTerm nat onState();

     f (earlyTerm nat onState. sTerm nated()) {
      return true;
    }

     f (getNumH sProcessed() >= getMaxH sToProcess()) {
      collectedEnoughResults();
       f (shouldTerm nate()) {
        return setEarlyTerm nat onState(EarlyTerm nat onState.TERM NATED_MAX_H TS_EXCEEDED)
            . sTerm nated();
      } else {
        return false;
      }
    }

    return  nnerShouldCollectMore(). sTerm nated();
  }

  /**
   * Note: subclasses overr d ng t   thod are expected to call "super.setNextReader"
   *  n t  r setNextReader().
   * @deprecated Remove t   thods  n favor of {@l nk #getLeafCollector(LeafReaderContext)}
   */
  @Deprecated
  publ c vo d setNextReader(LeafReaderContext context) throws  OExcept on {
     f (!term nat onTracker.useLastSearc dDoc dOnT  out()) {
      expens veEarlyTerm nat onC ck();
    }

    // Reset curDoc d for next seg nt
    curDoc d = -1;
    lastEarlyTerm nat onC ckDoc d = -1;
    curReader = context.reader();
  }

  /**
   * Sub-classes overr d ng t   thod are expected to call super.setScorer()
   */
  @Overr de
  publ c vo d setScorer(Scorable scorer) throws  OExcept on {
    t .scorer = scorer;
  }

  @Overr de
  publ c f nal vo d collect( nt doc) throws  OExcept on {
    curDoc d = doc;
    doCollect();
    numH sProcessed++;
     f (numDocsBet enT  outC cks > 0
        && (curDoc d - lastEarlyTerm nat onC ckDoc d) >= numDocsBet enT  outC cks) {
      lastEarlyTerm nat onC ckDoc d = curDoc d;

       f (!term nat onTracker.useLastSearc dDoc dOnT  out()) {
        expens veEarlyTerm nat onC ck();
      }
    }
  }

  /**
   * Account ng for a seg nt searc d.
   * @param lastSearc dDoc D  s t  last doc d searc d before term nat on,
   * or NO_MORE_DOCS  f t re was no early term nat on.  T  doc may not be a h !
   */
  protected f nal vo d trackCompleteSeg nt( nt lastSearc dDoc D) throws  OExcept on {
    doF n shSeg nt(lastSearc dDoc D);
  }

  @Overr de
  publ c f nal vo d f n shSeg nt( nt lastSearc dDoc D) throws  OExcept on {
    // f n s d search ng a seg nt. Computer average t   needed to search a seg nt.
    Precond  ons.c ckState(curReader != null, "D d subclass call super.setNextReader()?");
    numSearc dSeg nts++;

    long totalT   = clock.nowM ll s() - term nat onTracker.getLocalStartT  M ll s();

     f (totalT   >=  nteger.MAX_VALUE) {
      Str ng msg = Str ng.format(
          "%s: A query runs for %d that  s longer than  nteger.MAX_VALUE ms. lastSearc dDoc D: %d",
          getClass().getS mpleNa (), totalT  , lastSearc dDoc D
      );
      LOG.error(msg);
      throw new  llegalStateExcept on(msg);
    }

     nt t  PerSeg nt = (( nt) totalT  ) / numSearc dSeg nts;

     f (t  PerSeg nt < 0) {
      NEGAT VE_T ME_PER_SEGMENT. ncre nt();
      t  PerSeg nt = 0;
    }

    //  f  're enforc ng t  out v a t  last searc d doc  D,   don't need to add t  buffer,
    // s nce  'll detect t  t  out r ght away.
     f (!term nat onTracker.useLastSearc dDoc dOnT  out()) {
      term nat onTracker.setPreTerm nat onSafeBufferT  M ll s(t  PerSeg nt);
    }

    // C ck w t r   t  d out and are c ck ng for t  out at t  leaves.  f so,   should use
    // t  captured lastSearc dDoc d from t  tracker  nstead, wh ch  s t  most up-to-date amongst
    // t  query nodes.
     f (term nat onTracker.useLastSearc dDoc dOnT  out()
        && EarlyTerm nat onState.TERM NATED_T ME_OUT_EXCEEDED.equals(
            term nat onTracker.getEarlyTerm nat onState())) {
      QUERY_T MEOUT_ENFORCED. ncre nt();
      trackCompleteSeg nt(term nat onTracker.getLastSearc dDoc d());
    } else {
      trackCompleteSeg nt(lastSearc dDoc D);
    }

    //   f n s d a seg nt, so clear out t  Doc dTrackers. T  next seg nt w ll reg ster  s
    // own trackers, and   don't need to keep t  trackers from t  current seg nt.
    term nat onTracker.resetDoc dTrackers();

    curDoc d = -1;
    curReader = null;
    scorer = null;
  }

  /**
   * More expens ve Early Term nat on c cks, wh ch are not called every h .
   * T  sets EarlyTerm nat onState  f   dec des that early term nat on should k ck  n.
   * See: SEARCH-29723.
   */
  pr vate vo d expens veEarlyTerm nat onC ck() {
     f (queryCostProv der != null) {
      double totalQueryCost = queryCostProv der.getTotalCost();
      double maxQueryCost = term nat onTracker.getMaxQueryCost();
       f (totalQueryCost >= maxQueryCost) {
        setEarlyTerm nat onState(EarlyTerm nat onState.TERM NATED_MAX_QUERY_COST_EXCEEDED);
      }
    }

    f nal long nowM ll s = clock.nowM ll s();
     f (nowM ll s >= term nat onTracker.getT  outEndT  W hReservat on()) {
      setEarlyTerm nat onState(EarlyTerm nat onState.TERM NATED_T ME_OUT_EXCEEDED);
    }
  }

  publ c long getMaxH sToProcess() {
    return maxH sToProcess;
  }

  publ c f nal vo d setNumH sProcessed(long numH sProcessed) {
    t .numH sProcessed = numH sProcessed;
  }

  protected f nal long getNumH sProcessed() {
    return numH sProcessed;
  }

  protected f nal  nt getNumSearc dSeg nts() {
    return numSearc dSeg nts;
  }

  protected f nal Clock getClock() {
    return clock;
  }

  @V s bleForTest ng
  protected f nal Term nat onTracker getTerm nat onTracker() {
    return t .term nat onTracker;
  }

  protected vo d collectedEnoughResults() throws  OExcept on {
  }

  protected boolean shouldTerm nate() {
    return true;
  }

  /**
   * Debug  nfo collected dur ng execut on.
   */
  publ c abstract L st<Str ng> getDebug nfo();
}
