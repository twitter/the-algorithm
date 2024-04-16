package com.tw ter.search.earlyb rd.search.facets;

 mport java. o. OExcept on;
 mport java.ut l.ArrayL st;
 mport java.ut l.HashMap;
 mport java.ut l.L st;
 mport java.ut l.Map;

 mport com.google.common.annotat ons.V s bleForTest ng;
 mport com.google.common.base.Precond  ons;

 mport org.apac .commons.lang.Str ngUt ls;
 mport org.apac .lucene. ndex.Post ngsEnum;
 mport org.apac .lucene. ndex.Term;
 mport org.apac .lucene. ndex.Terms;
 mport org.apac .lucene. ndex.TermsEnum;
 mport org.apac .lucene.search.Doc dSet erator;

 mport com.tw ter.common.ut l.Clock;
 mport com.tw ter.search.common. tr cs.SearchCounter;
 mport com.tw ter.search.common. tr cs.SearchResultsStats;
 mport com.tw ter.search.common.sc ma.Sc maUt l;
 mport com.tw ter.search.common.sc ma.base. mmutableSc ma nterface;
 mport com.tw ter.search.common.sc ma.base.Sc ma;
 mport com.tw ter.search.common.search.EarlyTerm nat onState;
 mport com.tw ter.search.common.ut l.earlyb rd.TermStat st csUt l;
 mport com.tw ter.search.core.earlyb rd. ndex.T  Mapper;
 mport com.tw ter.search.earlyb rd. ndex.Earlyb rdS ngleSeg ntSearc r;
 mport com.tw ter.search.earlyb rd.search.AbstractResultsCollector;
 mport com.tw ter.search.earlyb rd.search.SearchResults nfo;
 mport com.tw ter.search.earlyb rd.stats.Earlyb rdSearc rStats;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ft togramSett ngs;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftTermRequest;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftTermResults;

publ c class TermStat st csCollector extends AbstractResultsCollector
        <TermStat st csRequest nfo, TermStat st csCollector.TermStat st csSearchResults> {
  pr vate stat c f nal EarlyTerm nat onState TERM NATED_TERM_STATS_COUNT NG_DONE =
      new EarlyTerm nat onState("term nated_term_stats_count ng_done", true);

  // Stats for track ng  togram results.
  pr vate stat c f nal SearchResultsStats TERM_STATS_H STOGRAM_REQUESTS_W TH_MOVED_BACK_B NS =
      SearchResultsStats.export("term_stat st cs_collector_quer es_w h_moved_back_b ns");
  pr vate stat c f nal SearchCounter TERM_STATS_SK PPED_LARGER_OUT_OF_BOUNDS_H TS =
      SearchCounter.export("term_stat st cs_collector_sk pped_larger_out_of_bounds_h s");

  @V s bleForTest ng
  stat c f nal class TermStat st cs {
    pr vate f nal Thr ftTermRequest termRequest;
    pr vate f nal Term term;  // could be null, for count across all f elds
    pr vate  nt termDF = 0;
    pr vate  nt termCount = 0;
    pr vate f nal  nt[]  togramB ns;

    // Per-seg nt  nformat on.
    pr vate Post ngsEnum seg ntDocsEnum;  // could be null, for count across all f elds
    pr vate boolean seg ntDone;

    @V s bleForTest ng
    TermStat st cs(Thr ftTermRequest termRequest, Term term,  nt numB ns) {
      t .termRequest = termRequest;
      t .term = term;
      t . togramB ns = new  nt[numB ns];
    }

    /**
     * Take t  currently accumulated counts and "move t m back" to make room for counts from more
     * recent b n ds.
     *
     * For example,  f t  oldF rstB n D was set to 10, and t   togramB ns  re {3, 4, 5, 6, 7},
     * after t  call w h newF rstB n D set to 12, t   togramB ns w ll be set
     * to {5, 6, 7, 0, 0}.
     *
     * @param oldF rstB n D t  b n d of t  f rstB n that's been used up to now.
     * @param newF rstB n D t  new b n d of t  f rstB n that w ll be used from now on.
     *     T  newF rstB n D  s presu d to be larger than t  oldF rstB n D, and  s asserted.
     */
    @V s bleForTest ng
    vo d moveBackTermCounts( nt oldF rstB n D,  nt newF rstB n D) {
      Precond  ons.c ckState(oldF rstB n D < newF rstB n D);
      // move counts back by t  many b ns
      f nal  nt moveBackBy = newF rstB n D - oldF rstB n D;

      t .termCount = 0;
      for ( nt   = 0;   <  togramB ns.length;  ++) {
         nt oldCount =  togramB ns[ ];
         togramB ns[ ] = 0;
         nt new ndex =   - moveBackBy;
         f (new ndex >= 0) {
           togramB ns[new ndex] = oldCount;
          t .termCount += oldCount;
        }
      }
    }

    @V s bleForTest ng vo d countH ( nt b n) {
      termCount++;
       togramB ns[b n]++;
    }

    @V s bleForTest ng  nt getTermCount() {
      return termCount;
    }

    @V s bleForTest ng  nt[] get togramB ns() {
      return  togramB ns;
    }
  }

  pr vate TermStat st cs[] termStat st cs;

  //  togram f elds.
  pr vate  nt numB ns;
  pr vate  nt b nS ze;

  pr vate  nt numT  sB ns reMovedBack = 0;
  pr vate  nt numLargerOutOfBoundsB nsSk pped = 0;

  pr vate stat c f nal  nt SEEN_OUT_OF_RANGE_THRESHOLD = 10;

  pr vate  nt seenOutOfRange = 0;

  //  D of t  f rst b n - effect vely t   / b nS ze.  T   s calculated
  // relat ve to t  f rst collected  n-order h .
  pr vate  nt f rstB n D = -1;
  // L st of per-seg nt debug  nformat on spec f cally useful for termstat request debugg ng.
  pr vate L st<Str ng> termStat st csDebug nfo = new ArrayL st<>();

  /**
   * Creates a new term stats collector.
   */
  publ c TermStat st csCollector(
       mmutableSc ma nterface sc ma,
      TermStat st csRequest nfo searchRequest nfo,
      Earlyb rdSearc rStats searc rStats,
      Clock clock,
       nt requestDebugMode) {
    super(sc ma, searchRequest nfo, clock, searc rStats, requestDebugMode);

    // Set up t   togram b ns.
     f (searchRequest nfo. sReturn togram()) {
      Thr ft togramSett ngs  togramSett ngs = searchRequest nfo.get togramSett ngs();
      t .numB ns =  togramSett ngs.getNumB ns();
      b nS ze = TermStat st csUt l.determ neB nS ze( togramSett ngs);
    } else {
      t .numB ns = 0;
      t .b nS ze = 0;
    }

    // Set up t  term stat st cs array.
    L st<Thr ftTermRequest> termRequests = searchRequest nfo.getTermRequests();
     f (termRequests == null) {
      t .termStat st cs = new TermStat st cs[0];
      return;
    }

    t .termStat st cs = new TermStat st cs[searchRequest nfo.getTermRequests().s ze()];
    for ( nt   = 0;   < searchRequest nfo.getTermRequests().s ze();  ++) {
      f nal Thr ftTermRequest termRequest = searchRequest nfo.getTermRequests().get( );

      Term term = null;
      Str ng f eldNa  = termRequest.getF eldNa ();
       f (!Str ngUt ls. sBlank(f eldNa )) {
        // F rst c ck  f  's a facet f eld.
        Sc ma.F eld nfo facetF eld = sc ma.getFacetF eldByFacetNa (termRequest.getF eldNa ());
         f (facetF eld != null) {
          term = new Term(facetF eld.getNa (), termRequest.getTerm());
        } else {
          // Earlyb rdSearc r.val dateRequest() should've already c cked that t  f eld ex sts  n
          // t  sc ma, and that t  term can be converted to t  type of t  f eld. Ho ver,  f
          // that d d not happen for so  reason, an except on w ll be thrown  re, wh ch w ll be
          // converted to a TRANS ENT_ERROR response code.
          Sc ma.F eld nfo f eld nfo = sc ma.getF eld nfo(f eldNa );
          Precond  ons.c ckNotNull(
              f eld nfo,
              "Found a Thr ftTermRequest for a f eld that's not  n t  sc ma: " + f eldNa 
              + ". T  should've been caught by Earlyb rdSearc r.val dateRequest()!");
          term = new Term(f eldNa , Sc maUt l.toBytesRef(f eld nfo, termRequest.getTerm()));
        }
      } else {
        // NOTE:  f t  f eldNa   s empty, t   s a catch-all term request for t  count across
        // all f elds.  'll just use a null term  n t  TermStat st cs object.
      }

      termStat st cs[ ] = new TermStat st cs(termRequest, term, numB ns);
    }
  }

  @Overr de
  publ c vo d startSeg nt() throws  OExcept on {
    termStat st csDebug nfo.add(
        "Start ng seg nt  n t  stamp range: [" + t  Mapper.getF rstT  ()
        + ", " + t  Mapper.getLastT  () + "]");
    for (TermStat st cs termStats : termStat st cs) {
      termStats.seg ntDone = true;  // unt l   know  's false later.
      TermsEnum termsEnum = null;
       f (termStats.term != null) {
        Terms terms = currTw terReader.terms(termStats.term.f eld());
         f (terms != null) {
          termsEnum = terms. erator();
           f (termsEnum != null && termsEnum.seekExact(termStats.term.bytes())) {
            termStats.termDF += termsEnum.docFreq();  // Only  an ngful for matchAll quer es.
            termStats.seg ntDocsEnum =
                termsEnum.post ngs(termStats.seg ntDocsEnum, Post ngsEnum.FREQS);
            termStats.seg ntDone = termStats.seg ntDocsEnum == null
                 || termStats.seg ntDocsEnum.nextDoc() == Doc dSet erator.NO_MORE_DOCS;
          } else {
            // t  term doesn't ex st  n t  seg nt.
          }
        }
      } else {
        // Catch-all case
        termStats.termDF += currTw terReader.numDocs();   // Only  an ngful for matchAll quer es.
        termStats.seg ntDocsEnum = null;
        termStats.seg ntDone = false;
      }
    }
  }

  pr vate  nt calculateB n(f nal  nt t etT  ) {
     f (t etT   == T  Mapper. LLEGAL_T ME) {
      return -1;
    }

    f nal  nt b n D = Math.abs(t etT  ) / b nS ze;
    f nal  nt expectedF rstB n d = b n D - numB ns + 1;

     f (f rstB n D == -1) {
      f rstB n D = expectedF rstB n d;
    } else  f (expectedF rstB n d > f rstB n D) {
      numT  sB ns reMovedBack++;
      f nal  nt oldOutOfOrderF rstB n D = f rstB n D;
      f rstB n D = expectedF rstB n d;
      //   got a more recent out of order b n, move prev ous counts back.
      for (TermStat st cs ts : termStat st cs) {
        ts.moveBackTermCounts(oldOutOfOrderF rstB n D, f rstB n D);
      }
    }

    f nal  nt b n ndex = b n D - f rstB n D;
     f (b n ndex >= numB ns) {
      //  n-order t  s should be decreas ng,
      // and out of order t  s seen after an  n-order t et should also be smaller than t 
      // f rst  n-order t et's t  . W ll track t se and export as a stat.
      numLargerOutOfBoundsB nsSk pped++;
      return -1;
    } else  f (b n ndex < 0) {
      // Early term nat on cr er a.
      seenOutOfRange++;
    } else {
      // Reset t  counter, s nce   want to see consecut ve t ets that are out of   b n range
      // not s ngle anomal es.
      seenOutOfRange = 0;
    }

    return b n ndex;
  }

  @Overr de
  publ c vo d doCollect(long t et D) throws  OExcept on {
     f (searchRequest nfo. sReturn togram()) {
      f nal  nt t etT   = t  Mapper.getT  (curDoc d);
      f nal  nt b n ndex = calculateB n(t etT  );
       f (b n ndex >= 0) {
        for (TermStat st cs ts : termStat st cs) {
           f (!ts.seg ntDone) {
            count t(ts, b n ndex);
          }
        }
      }
    } else {
      for (TermStat st cs ts : termStat st cs) {
         f (!ts.seg ntDone) {
          countNo t(ts);
        }
      }
    }
  }

  @Overr de
  publ c vo d sk pSeg nt(Earlyb rdS ngleSeg ntSearc r searc r) {
    // Do noth ng  re.
    //   don't do account ng that's done  n AbstractResultsCollector for Term Stats
    // requests because ot rw se t  b n  D calculat on w ll be confused.
  }

  pr vate boolean advance(TermStat st cs ts) throws  OExcept on {
    Post ngsEnum docsEnum = ts.seg ntDocsEnum;
     f (docsEnum.doc D() < curDoc d) {
       f (docsEnum.advance(curDoc d) == Doc dSet erator.NO_MORE_DOCS) {
        ts.seg ntDone = true;
        return false;
      }
    }
    return docsEnum.doc D() == curDoc d;
  }

  pr vate boolean count t(TermStat st cs ts,  nt b n) throws  OExcept on {
     f (ts.term != null && !advance(ts)) {
      return false;
    }
    ts.countH (b n);
    return true;
  }

  pr vate boolean countNo t(TermStat st cs ts) throws  OExcept on {
     f (ts.term != null && !advance(ts)) {
      return false;
    }
    ts.termCount++;
    return true;
  }

  @Overr de
  publ c EarlyTerm nat onState  nnerShouldCollectMore() {
     f (readyToTerm nate()) {
      return setEarlyTerm nat onState(TERM NATED_TERM_STATS_COUNT NG_DONE);
    }
    return EarlyTerm nat onState.COLLECT NG;
  }

  /**
   * T  term nat on log c  s s mple -   know what   earl est b n  s and once   see a result
   * that's before   earl est b n,   term nate.
   *
   *   results co  w h  ncreas ng  nternal doc  ds, wh ch should correspond to decreas ng
   * t  stamps. See SEARCH-27729, TWEETYP E-7031.
   *
   *   early term nate after   have seen enough t ets that are outs de of t  b n
   * range that   want to return. T  way  're not term nat ng too early because of s ngle t ets
   * w h wrong t  stamps.
   */
  @V s bleForTest ng
  boolean readyToTerm nate() {
    return t .seenOutOfRange >= SEEN_OUT_OF_RANGE_THRESHOLD;
  }

  @Overr de
  publ c TermStat st csSearchResults doGetResults() {
    return new TermStat st csSearchResults();
  }

  publ c f nal class TermStat st csSearchResults extends SearchResults nfo {
    publ c f nal L st< nteger> b n ds;
    publ c f nal Map<Thr ftTermRequest, Thr ftTermResults> results;
    publ c f nal  nt lastCompleteB n d;
    publ c f nal L st<Str ng>  termStat st csDebug nfo;

    pr vate TermStat st csSearchResults() {
      //  n  al ze term stat debug  nfo
      termStat st csDebug nfo = TermStat st csCollector.t .termStat st csDebug nfo;

       f (termStat st cs.length > 0) {
        results = new HashMap<>();

         f (searchRequest nfo. sReturn togram()) {
          b n ds = new ArrayL st<>(numB ns);
           nt m nSearc dT   = TermStat st csCollector.t .getM nSearc dT  ();

           f (shouldCollectDeta ledDebug nfo()) {
            termStat st csDebug nfo.add("m nSearc dT  : " + m nSearc dT  );
             nt maxSearc dT   = TermStat st csCollector.t .getMaxSearc dT  ();
            termStat st csDebug nfo.add("maxSearc dT  : " + maxSearc dT  );
          }

           nt lastCompleteB n = -1;

          computeF rstB n d(TermStat st csCollector.t . sSetM nSearc dT  (), m nSearc dT  );
          track togramResultStats();

          // Example:
          //  m nSearchT   = 53s
          //  b nS ze = 10
          //  f rstB n d = 5
          //  numB ns = 4
          //  b n d = 5, 6, 7, 8
          //  b nT  Stamp = 50s, 60s, 70s, 80s
          for ( nt   = 0;   < numB ns;  ++) {
             nt b n d = f rstB n D +  ;
             nt b nT  Stamp = b n d * b nS ze;
            b n ds.add(b n d);
             f (lastCompleteB n == -1 && b nT  Stamp > m nSearc dT  ) {
              lastCompleteB n = b n d;
            }
          }

           f (!getEarlyTerm nat onState(). sTerm nated()) {
            // only  f   d dn't early term nate   can be sure to use t  f rstB n D as
            // lastCompleteB n d
            lastCompleteB n d = f rstB n D;
             f (shouldCollectDeta ledDebug nfo()) {
              termStat st csDebug nfo.add("no early term nat on");
            }
          } else {
            lastCompleteB n d = lastCompleteB n;
             f (shouldCollectDeta ledDebug nfo()) {
              termStat st csDebug nfo.add(
                  "early term nated for reason: " + getEarlyTerm nat onReason());
            }
          }
           f (shouldCollectDeta ledDebug nfo()) {
            termStat st csDebug nfo.add("lastCompleteB n d: " + lastCompleteB n d);
          }
        } else {
          b n ds = null;
          lastCompleteB n d = -1;
        }

        for (TermStat st cs ts : termStat st cs) {
          Thr ftTermResults termResults = new Thr ftTermResults().setTotalCount(ts.termCount);

           f (searchRequest nfo. sReturn togram()) {
            L st< nteger> l st = new ArrayL st<>();
            for ( nt count : ts. togramB ns) {
              l st.add(count);
            }
            termResults.set togramB ns(l st);
          }

          results.put(ts.termRequest, termResults);
        }
      } else {
        b n ds = null;
        results = null;
        lastCompleteB n d = -1;
      }
    }

    @Overr de
    publ c Str ng toStr ng() {
      Str ngBu lder res = new Str ngBu lder();
      res.append("TermStat st csSearchResults(\n");
       f (b n ds != null) {
        res.append("  b n ds=").append(b n ds).append("\n");
      }
      res.append("  lastCompleteB n d=").append(lastCompleteB n d).append("\n");
       f (results != null) {
        res.append("  results=").append(results).append("\n");
      }
      res.append(")");
      return res.toStr ng();
    }

    publ c L st<Str ng> getTermStat st csDebug nfo() {
      return termStat st csDebug nfo;
    }
  }

  /**
   * F gure out what t  actual f rstB n d  s for t  query.
   */
  pr vate vo d computeF rstB n d(boolean  sSetM nSearc dT  ,  nt m nSearc dT  ) {
     f (f rstB n D == -1) {
       f (! sSetM nSearc dT  ) {
        // T  would only happen  f   don't search any seg nts, wh ch for now   have
        // only seen happen ng  f s nce_t   or unt l_t   don't  ntersect at all w h
        // t  range of t  served seg nts.
        f rstB n D = 0;
      } else {
        // Example:
        //    m nSearc dT   = 54
        //    b nS ze = 10
        //    f rstB n d = 5
        f rstB n D = m nSearc dT   / b nS ze;
      }

       f (shouldCollectDeta ledDebug nfo()) {
        termStat st csDebug nfo.add("f rstB n d: " + f rstB n D);
      }
    }
  }

  @V s bleForTest ng
   nt getSeenOutOfRange() {
    return seenOutOfRange;
  }

  pr vate vo d track togramResultStats() {
     f (numLargerOutOfBoundsB nsSk pped > 0) {
      TERM_STATS_SK PPED_LARGER_OUT_OF_BOUNDS_H TS. ncre nt();
    }

     f (numT  sB ns reMovedBack > 0) {
      TERM_STATS_H STOGRAM_REQUESTS_W TH_MOVED_BACK_B NS.recordResults(numT  sB ns reMovedBack);
    }
  }
}
