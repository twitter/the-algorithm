package com.tw ter.search.earlyb rd.search;

 mport java. o. OExcept on;
 mport java.ut l.ArrayL st;
 mport java.ut l.Arrays;
 mport java.ut l.HashSet;
 mport java.ut l.L nkedHashMap;
 mport java.ut l.L st;
 mport java.ut l.Map;
 mport java.ut l.Set;
 mport java.ut l.stream.Collectors;

 mport com.google.common.annotat ons.V s bleForTest ng;
 mport com.google.common.base.Precond  ons;

 mport org.apac .lucene. ndex.LeafReaderContext;
 mport org.apac .lucene. ndex.Mult Reader;
 mport org.apac .lucene. ndex.Term;
 mport org.apac .lucene.search.Collector;
 mport org.apac .lucene.search.Explanat on;
 mport org.apac .lucene.search.Query;
 mport org.apac .lucene.search.Scorer;
 mport org.apac .lucene.search.ScoreMode;
 mport org.apac .lucene.search.  ght;
 mport org.slf4j.Logger;
 mport org.slf4j.LoggerFactory;

 mport com.tw ter.common.ut l.Clock;
 mport com.tw ter.search.common.sc ma.base. mmutableSc ma nterface;
 mport com.tw ter.search.core.earlyb rd. ndex.Earlyb rd ndexSeg ntData;
 mport com.tw ter.search.earlyb rd.Earlyb rdSearc r;
 mport com.tw ter.search.earlyb rd. ndex.Earlyb rdS ngleSeg ntSearc r;
 mport com.tw ter.search.earlyb rd. ndex.T et DMapper;
 mport com.tw ter.search.earlyb rd.search.facets.AbstractFacetTermCollector;
 mport com.tw ter.search.earlyb rd.search.facets.TermStat st csCollector;
 mport com.tw ter.search.earlyb rd.search.facets.TermStat st csCollector.TermStat st csSearchResults;
 mport com.tw ter.search.earlyb rd.search.facets.TermStat st csRequest nfo;
 mport com.tw ter.search.earlyb rd.search.quer es.S nceMax DF lter;
 mport com.tw ter.search.earlyb rd.search.quer es.S nceUnt lF lter;
 mport com.tw ter.search.earlyb rd.stats.Earlyb rdSearc rStats;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftFacetCount;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftSearchResult;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftSearchResults;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftTermStat st csResults;
 mport com.tw ter.search.queryparser.ut l. dT  Ranges;

publ c class Earlyb rdMult Seg ntSearc r extends Earlyb rdLuceneSearc r {
  pr vate stat c f nal Logger LOG = LoggerFactory.getLogger(Earlyb rdMult Seg ntSearc r.class);

  pr vate f nal  mmutableSc ma nterface sc ma;
  pr vate f nal Map<Long, Earlyb rdS ngleSeg ntSearc r> seg ntSearc rs;
  protected f nal  nt numSeg nts;
  pr vate f nal Clock clock;

  // T  w ll prevent us from even cons der ng seg nts that are out of range.
  //  's an  mportant opt m zat on for a certa n class of quer es.
  protected  dT  Ranges  dT  Ranges = null;

  pr vate f nal Earlyb rdSearc rStats searc rStats;

  publ c Earlyb rdMult Seg ntSearc r(
       mmutableSc ma nterface sc ma,
      L st<Earlyb rdS ngleSeg ntSearc r> searc rs,
      Earlyb rdSearc rStats searc rStats,
      Clock clock) throws  OExcept on {
    // NOTE:   pass  n an empty Mult Reader to super and reta n t  l st of searc rs  n t 
    // class s nce Mult Reader does not allow an aggregate of more than  nteger.MAX_VALUE docs,
    // wh ch so  of   larger arch ve  ndexes may have.
    super(new Mult Reader());
    // seg ntSearc rs are mapped from t   sl ce  Ds to searc rs so that   can qu ckly
    // f nd t  correct searc r for a g ven t   sl ce  D (see f llPayload).
    // make sure   ma nta n order of seg nts,  nce a L nkedHashMap  nstead of just a HashMap
    t .seg ntSearc rs = new L nkedHashMap<>();
    t .sc ma = sc ma;
    for (Earlyb rdS ngleSeg ntSearc r searc r : searc rs) {
       f (searc r != null) {
        long t  Sl ce D = searc r.getT  Sl ce D();
        t .seg ntSearc rs.put(t  Sl ce D, searc r);
      }
    }
    //  n  al z ng t  after populat ng t  l st.  prev ously  n  al zed before, and
    // t  may have lead to a race cond  on, although t  doesn't seem poss ble g ven
    // that seg nts should be an  mmutable cloned l st.
    t .numSeg nts = seg ntSearc rs.s ze();

    t .searc rStats = searc rStats;
    t .clock = clock;
  }

  publ c vo d set dT  Ranges( dT  Ranges  dT  Ranges) {
    t . dT  Ranges =  dT  Ranges;
  }

  @Overr de
  protected vo d search(L st<LeafReaderContext> unusedLeaves,   ght   ght, Collector coll)
      throws  OExcept on {
    Precond  ons.c ckState(coll  nstanceof AbstractResultsCollector);
    AbstractResultsCollector<?, ?> collector = (AbstractResultsCollector<?, ?>) coll;

    for (Earlyb rdS ngleSeg ntSearc r seg ntSearc r : seg ntSearc rs.values()) {
       f (shouldSk pSeg nt(seg ntSearc r)) {
        collector.sk pSeg nt(seg ntSearc r);
      } else {
        seg ntSearc r.search(  ght.getQuery(), collector);
         f (collector. sTerm nated()) {
          break;
        }
      }
    }
  }

  @V s bleForTest ng
  protected boolean shouldSk pSeg nt(Earlyb rdS ngleSeg ntSearc r seg ntSearc r) {
    Earlyb rd ndexSeg ntData seg ntData =
        seg ntSearc r.getTw ter ndexReader().getSeg ntData();
     f ( dT  Ranges != null) {
       f (!S nceMax DF lter.s nceMax Ds nRange(
              (T et DMapper) seg ntData.getDoc DToT et DMapper(),
               dT  Ranges.getS nce DExclus ve().or(S nceMax DF lter.NO_F LTER),
               dT  Ranges.getMax D nclus ve().or(S nceMax DF lter.NO_F LTER))
          || !S nceUnt lF lter.s nceUnt lT  s nRange(
              seg ntData.getT  Mapper(),
               dT  Ranges.getS nceT   nclus ve().or(S nceUnt lF lter.NO_F LTER),
               dT  Ranges.getUnt lT  Exclus ve().or(S nceUnt lF lter.NO_F LTER))) {
        return true;
      }
    }
    return false;
  }

  @Overr de
  publ c vo d f llFacetResults(
      AbstractFacetTermCollector collector, Thr ftSearchResults searchResults) throws  OExcept on {
    for (Earlyb rdS ngleSeg ntSearc r seg ntSearc r : seg ntSearc rs.values()) {
      seg ntSearc r.f llFacetResults(collector, searchResults);
    }
  }

  @Overr de
  publ c TermStat st csSearchResults collectTermStat st cs(
      TermStat st csRequest nfo searchRequest nfo,
      Earlyb rdSearc r searc r,
       nt requestDebugMode) throws  OExcept on {
    TermStat st csCollector collector = new TermStat st csCollector(
        sc ma, searchRequest nfo, searc rStats, clock, requestDebugMode);
    search(collector.getSearchRequest nfo().getLuceneQuery(), collector);
    searc r.maybeSetCollectorDebug nfo(collector);
    return collector.getResults();
  }

  @Overr de
  publ c vo d expla nSearchResults(SearchRequest nfo searchRequest nfo,
      S mpleSearchResults h s, Thr ftSearchResults searchResults) throws  OExcept on {
    for (Earlyb rdS ngleSeg ntSearc r seg ntSearc r : seg ntSearc rs.values()) {
      // t  h s that are gett ng passed  nto t   thod are h s across
      // all searc d seg nts.   need to get t  per seg nt h s and
      // generate explanat ons one seg nt at a t  .
      L st<H > h sForCurrentSeg nt = new ArrayL st<>();
      Set<Long> t et dsForCurrentSeg nt = new HashSet<>();
      L st<Thr ftSearchResult> h ResultsForCurrentSeg nt = new ArrayL st<>();

      for (H  h  : h s.h s) {
         f (h .getT  Sl ce D() == seg ntSearc r.getT  Sl ce D()) {
          h sForCurrentSeg nt.add(h );
          t et dsForCurrentSeg nt.add(h .status D);
        }
      }
      for (Thr ftSearchResult result : searchResults.getResults()) {
         f (t et dsForCurrentSeg nt.conta ns(result. d)) {
          h ResultsForCurrentSeg nt.add(result);
        }
      }
      Thr ftSearchResults resultsForSeg nt = new Thr ftSearchResults()
          .setResults(h ResultsForCurrentSeg nt);

      S mpleSearchResults f nalH s = new S mpleSearchResults(h sForCurrentSeg nt);
      seg ntSearc r.expla nSearchResults(searchRequest nfo, f nalH s, resultsForSeg nt);
    }
    //   should not see h s that are not assoc ated w h an act ve seg nt
    L st<H > h sW hUnknownSeg nt =
        Arrays.stream(h s.h s()).f lter(h  -> !h . sHasExplanat on())
            .collect(Collectors.toL st());
    for (H  h  : h sW hUnknownSeg nt) {
      LOG.error("Unable to f nd seg nt assoc ated w h h : " + h .toStr ng());
    }
  }

  @Overr de
  publ c vo d f llFacetResult tadata(Map<Term, Thr ftFacetCount> facetResults,
                                       mmutableSc ma nterface docu ntSc ma, byte debugMode)
      throws  OExcept on {
    for (Earlyb rdS ngleSeg ntSearc r seg ntSearc r : seg ntSearc rs.values()) {
      seg ntSearc r.f llFacetResult tadata(facetResults, docu ntSc ma, debugMode);
    }
  }

  @Overr de
  publ c vo d f llTermStats tadata(Thr ftTermStat st csResults termStatsResults,
                                     mmutableSc ma nterface docu ntSc ma, byte debugMode)
      throws  OExcept on {
    for (Earlyb rdS ngleSeg ntSearc r seg ntSearc r : seg ntSearc rs.values()) {
      seg ntSearc r.f llTermStats tadata(termStatsResults, docu ntSc ma, debugMode);
    }
  }

  /**
   * T  searc rs for  nd v dual seg nts w ll rewr e t  query as t y see f , so t  mult 
   * seg nt searc r does not need to rewr e  .  n fact, not rewr  ng t  query  re  mproves
   * t  request latency by ~5%.
   */
  @Overr de
  publ c Query rewr e(Query or g nal) {
    return or g nal;
  }

  /**
   * T  searc rs for  nd v dual seg nts w ll create t  r own   ghts. T   thod only creates
   * a dum    ght to pass t  Lucene query to t  search()  thod of t se  nd v dual seg nt
   * searc rs.
   */
  @Overr de
  publ c   ght create  ght(Query query, ScoreMode scoreMode, float boost) {
    return new Dum   ght(query);
  }

  /**
   * Dum    ght used solely to pass Lucene Query around.
   */
  pr vate stat c f nal class Dum   ght extends   ght {
    pr vate Dum   ght(Query luceneQuery) {
      super(luceneQuery);
    }

    @Overr de
    publ c Explanat on expla n(LeafReaderContext context,  nt doc) {
      throw new UnsupportedOperat onExcept on();
    }

    @Overr de
    publ c Scorer scorer(LeafReaderContext context) {
      throw new UnsupportedOperat onExcept on();
    }

    @Overr de
    publ c vo d extractTerms(Set<Term> terms) {
      throw new UnsupportedOperat onExcept on();
    }

    @Overr de
    publ c boolean  sCac able(LeafReaderContext context) {
      return true;
    }
  }
}
