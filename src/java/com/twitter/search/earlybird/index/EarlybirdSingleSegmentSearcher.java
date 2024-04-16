package com.tw ter.search.earlyb rd. ndex;

 mport java. o. OExcept on;
 mport java.ut l.L st;
 mport java.ut l.Locale;
 mport java.ut l.Map;
 mport java.ut l.Map.Entry;

 mport com.google.common.base.Precond  ons;

 mport org.apac .lucene. ndex.LeafReaderContext;
 mport org.apac .lucene. ndex.Term;
 mport org.apac .lucene.search.Collect onStat st cs;
 mport org.apac .lucene.search.Collector;
 mport org.apac .lucene.search.Doc dSet erator;
 mport org.apac .lucene.search.Explanat on;
 mport org.apac .lucene.search.LeafCollector;
 mport org.apac .lucene.search.Scorer;
 mport org.apac .lucene.search.ScoreMode;
 mport org.apac .lucene.search.TermStat st cs;
 mport org.apac .lucene.search.  ght;
 mport org.apac .lucene.ut l.BytesRef;
 mport org.slf4j.Logger;
 mport org.slf4j.LoggerFactory;

 mport com.tw ter.common.ut l.Clock;
 mport com.tw ter.search.common.constants.thr ftjava.Thr ftLanguage;
 mport com.tw ter.search.common.relevance.features.Earlyb rdDocu ntFeatures;
 mport com.tw ter.search.common.results.thr ftjava.F eldH Attr but on;
 mport com.tw ter.search.common.sc ma.base. mmutableSc ma nterface;
 mport com.tw ter.search.common.sc ma.base.Sc ma;
 mport com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants.Earlyb rdF eldConstant;
 mport com.tw ter.search.common.search.Tw terCollector;
 mport com.tw ter.search.common.search.Tw ter ndexSearc r;
 mport com.tw ter.search.common.ut l.analys s.LongTermAttr bute mpl;
 mport com.tw ter.search.common.ut l.lang.Thr ftLanguageUt l;
 mport com.tw ter.search.core.earlyb rd.facets.FacetLabelProv der;
 mport com.tw ter.search.core.earlyb rd. ndex.Doc DToT et DMapper;
 mport com.tw ter.search.core.earlyb rd. ndex.Earlyb rd ndexSeg ntAtom cReader;
 mport com.tw ter.search.core.earlyb rd. ndex.Earlyb rd ndexSeg ntData;
 mport com.tw ter.search.earlyb rd.Earlyb rdSearc r;
 mport com.tw ter.search.earlyb rd.common.conf g.Earlyb rdConf g;
 mport com.tw ter.search.earlyb rd.common.userupdates.UserTable;
 mport com.tw ter.search.earlyb rd.search.Earlyb rdLuceneSearc r;
 mport com.tw ter.search.earlyb rd.search.H ;
 mport com.tw ter.search.earlyb rd.search.SearchRequest nfo;
 mport com.tw ter.search.earlyb rd.search.S mpleSearchResults;
 mport com.tw ter.search.earlyb rd.search.facets.AbstractFacetTermCollector;
 mport com.tw ter.search.earlyb rd.search.facets.TermStat st csCollector;
 mport com.tw ter.search.earlyb rd.search.facets.TermStat st csRequest nfo;
 mport com.tw ter.search.earlyb rd.search.relevance.scor ng.RelevanceQuery;
 mport com.tw ter.search.earlyb rd.stats.Earlyb rdSearc rStats;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftFacetCount;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftFacetCount tadata;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftSearchResult;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftSearchResult tadata;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftSearchResults;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftTermRequest;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftTermResults;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftTermStat st csResults;

publ c class Earlyb rdS ngleSeg ntSearc r extends Earlyb rdLuceneSearc r {
  pr vate stat c f nal Logger LOG = LoggerFactory.getLogger(Earlyb rdS ngleSeg ntSearc r.class);

  pr vate f nal Earlyb rd ndexSeg ntAtom cReader tw terReader;
  pr vate f nal  mmutableSc ma nterface sc ma;
  pr vate f nal UserTable userTable;
  pr vate f nal long t  Sl ce D;

  pr vate f nal Earlyb rdSearc rStats searc rStats;
  pr vate Clock clock;

  publ c Earlyb rdS ngleSeg ntSearc r(
       mmutableSc ma nterface sc ma,
      Earlyb rd ndexSeg ntAtom cReader reader,
      UserTable userTable,
      Earlyb rdSearc rStats searc rStats,
      Clock clock) {
    super(reader);
    t .sc ma = sc ma;
    t .tw terReader = reader;
    t .userTable = userTable;
    t .t  Sl ce D = reader.getSeg ntData().getT  Sl ce D();
    t .searc rStats = searc rStats;
    t .clock = clock;
  }

  publ c f nal long getT  Sl ce D() {
    return t  Sl ce D;
  }

  publ c Earlyb rd ndexSeg ntAtom cReader getTw ter ndexReader() {
    return tw terReader;
  }

  /**
   * search() ma n loop.
   * T  behaves exactly l ke  ndexSearc r.search()  f a stock Lucene collector passed  n.
   * Ho ver,  f a Tw terCollector  s passed  n, t  class performs Tw ter style early
   * term nat on w hout rely ng on
   * {@l nk org.apac .lucene.search.Collect onTerm natedExcept on}.
   * T   thod  s nearly  dent cal to Tw ter ndexSearc r.search() w h two d fferences:
   *  1) advances to smallest doc D before search ng.   mportant to sk p  ncomplete docs  n
   *     realt   seg nts.
   *  2) sk ps deletes us ng tw terReader
   */
  @Overr de
  protected vo d search(L st<LeafReaderContext> leaves,   ght   ght, Collector coll)
      throws  OExcept on {
    //  f an Tw terCollector  s passed  n,   can do a few extra th ngs  n  re, such
    // as early term nat on.  Ot rw se   can just fall back to  ndexSearc r.search().
     f (!(coll  nstanceof Tw terCollector)) {
      super.search(leaves,   ght, coll);
      return;
    }

    Tw terCollector collector = (Tw terCollector) coll;
     f (collector. sTerm nated()) {
      return;
    }

    LOG.debug("Start ng seg nt {}", t  Sl ce D);

    // Not fy t  collector that  're start ng t  seg nt, and c ck for early
    // term nat on cr er a aga n.  setNextReader() performs 'expens ve' early
    // term nat on c cks  n so   mple ntat ons such as Tw terEarlyTerm nat onCollector.
    LeafCollector leafCollector = collector.getLeafCollector(tw terReader.getContext());
     f (collector. sTerm nated()) {
      return;
    }

    //  n  al ze t  scorer:
    // Note that construct ng t  scorer may actually do real work, such as advanc ng to t 
    // f rst h .
    // T  scorer may be null  f   can tell r ght away that t  query has no h s: e.g.  f t 
    // f rst h  does not actually ex st.
    Scorer scorer =   ght.scorer(tw terReader.getContext());
     f (scorer == null) {
      LOG.debug("Scorer was null, not search ng seg nt {}", t  Sl ce D);
      collector.f n shSeg nt(Doc dSet erator.NO_MORE_DOCS);
      return;
    }
    leafCollector.setScorer(scorer);

    // Make sure to start search ng at t  smallest doc D.
    Doc dSet erator doc dSet erator = scorer. erator();
     nt smallestDoc d = tw terReader.getSmallestDoc D();
     nt doc D = doc dSet erator.advance(smallestDoc d);

    // Collect results.
    wh le (doc D != Doc dSet erator.NO_MORE_DOCS) {
      // Exclude deleted docs.
       f (!tw terReader.getDeletesV ew(). sDeleted(doc D)) {
        leafCollector.collect(doc D);
      }

      // C ck  f  're done after   consu d t  docu nt.
       f (collector. sTerm nated()) {
        break;
      }

      doc D = doc dSet erator.nextDoc();
    }

    // Always f n sh t  seg nt, prov d ng t  last doc D advanced to.
    collector.f n shSeg nt(doc D);
  }

  @Overr de
  publ c vo d f llFacetResults(
      AbstractFacetTermCollector collector, Thr ftSearchResults searchResults)
      throws  OExcept on {
     f (searchResults == null || searchResults.getResultsS ze() == 0) {
      return;
    }

    Earlyb rd ndexSeg ntData seg ntData = tw terReader.getSeg ntData();
    collector.resetFacetLabelProv ders(
        seg ntData.getFacetLabelProv ders(), seg ntData.getFacet DMap());
    Doc DToT et DMapper doc dMapper = seg ntData.getDoc DToT et DMapper();
    for (Thr ftSearchResult result : searchResults.getResults()) {
       nt doc d = doc dMapper.getDoc D(result.get d());
       f (doc d < 0) {
        cont nue;
      }

      seg ntData.getFacetCount ngArray().collectForDoc d(doc d, collector);
      collector.f llResultAndClear(result);
    }
  }

  @Overr de
  publ c TermStat st csCollector.TermStat st csSearchResults collectTermStat st cs(
      TermStat st csRequest nfo searchRequest nfo,
      Earlyb rdSearc r searc r,  nt requestDebugMode) throws  OExcept on {
    TermStat st csCollector collector = new TermStat st csCollector(
        sc ma, searchRequest nfo, searc rStats, clock, requestDebugMode);

    search(searchRequest nfo.getLuceneQuery(), collector);
    searc r.maybeSetCollectorDebug nfo(collector);
    return collector.getResults();
  }

  /** T   thod  s only used for debugg ng, so  's not opt m zed for speed */
  @Overr de
  publ c vo d expla nSearchResults(SearchRequest nfo searchRequest nfo,
                                   S mpleSearchResults h s,
                                   Thr ftSearchResults searchResults) throws  OExcept on {
      ght   ght =
        create  ght(rewr e(searchRequest nfo.getLuceneQuery()), ScoreMode.COMPLETE, 1.0f);

    Doc DToT et DMapper doc dMapper = tw terReader.getSeg ntData().getDoc DToT et DMapper();
    for ( nt   = 0;   < h s.numH s();  ++) {
      f nal H  h  = h s.getH ( );
      Precond  ons.c ckState(h .getT  Sl ce D() == t  Sl ce D,
          "h : " + h .toStr ng() + "  s not  n t  sl ce: " + t  Sl ce D);
      f nal Thr ftSearchResult result = searchResults.getResults().get( );
       f (!result. sSet tadata()) {
        result.set tadata(new Thr ftSearchResult tadata()
            .setPengu nVers on(Earlyb rdConf g.getPengu nVers onByte()));
      }

      f nal  nt doc dToExpla n = doc dMapper.getDoc D(h .getStatus D());
       f (doc dToExpla n == Doc DToT et DMapper. D_NOT_FOUND) {
        result.get tadata().setExplanat on(
            "ERROR: Could not f nd doc  D to expla n for " + h .toStr ng());
      } else {
        Explanat on explanat on;
        F eldH Attr but on f eldH Attr but on = result.get tadata().getF eldH Attr but on();
         f (  ght  nstanceof RelevanceQuery.Relevance  ght && f eldH Attr but on != null) {
          RelevanceQuery.Relevance  ght relevance  ght =
              (RelevanceQuery.Relevance  ght)   ght;

          explanat on = relevance  ght.expla n(
              tw terReader.getContext(), doc dToExpla n, f eldH Attr but on);
        } else {
          explanat on =   ght.expla n(tw terReader.getContext(), doc dToExpla n);
        }
        h .setHasExplanat on(true);
        result.get tadata().setExplanat on(explanat on.toStr ng());
      }
    }
  }

  @Overr de
  publ c vo d f llFacetResult tadata(Map<Term, Thr ftFacetCount> facetResults,
                                       mmutableSc ma nterface docu ntSc ma,
                                      byte debugMode) throws  OExcept on {
    FacetLabelProv der prov der = tw terReader.getFacetLabelProv ders(
            docu ntSc ma.getFacetF eldByFacetNa (Earlyb rdF eldConstant.TW MG_FACET));

    FacetLabelProv der.FacetLabelAccessor photoAccessor = null;

     f (prov der != null) {
      photoAccessor = prov der.getLabelAccessor();
    }

    for (Entry<Term, Thr ftFacetCount> facetResult : facetResults.entrySet()) {
      Term term = facetResult.getKey();
      Thr ftFacetCount facetCount = facetResult.getValue();

      Thr ftFacetCount tadata  tadata = facetCount.get tadata();
       f ( tadata == null) {
         tadata = new Thr ftFacetCount tadata();
        facetCount.set tadata( tadata);
      }

      f llTerm tadata(term,  tadata, photoAccessor, debugMode);
    }
  }

  @Overr de
  publ c vo d f llTermStats tadata(Thr ftTermStat st csResults termStatsResults,
                                     mmutableSc ma nterface docu ntSc ma,
                                    byte debugMode) throws  OExcept on {

    FacetLabelProv der prov der = tw terReader.getFacetLabelProv ders(
        docu ntSc ma.getFacetF eldByFacetNa (Earlyb rdF eldConstant.TW MG_FACET));

    FacetLabelProv der.FacetLabelAccessor photoAccessor = null;

     f (prov der != null) {
      photoAccessor = prov der.getLabelAccessor();
    }

    for (Map.Entry<Thr ftTermRequest, Thr ftTermResults> entry
         : termStatsResults.termResults.entrySet()) {

      Thr ftTermRequest termRequest = entry.getKey();
       f (termRequest.getF eldNa (). sEmpty()) {
        cont nue;
      }
      Sc ma.F eld nfo facetF eld = sc ma.getFacetF eldByFacetNa (termRequest.getF eldNa ());
      Term term = null;
       f (facetF eld != null) {
        term = new Term(facetF eld.getNa (), termRequest.getTerm());
      }
       f (term == null) {
        cont nue;
      }

      Thr ftFacetCount tadata  tadata = entry.getValue().get tadata();
       f ( tadata == null) {
         tadata = new Thr ftFacetCount tadata();
        entry.getValue().set tadata( tadata);
      }

      f llTerm tadata(term,  tadata, photoAccessor, debugMode);
    }
  }

  pr vate vo d f llTerm tadata(Term term, Thr ftFacetCount tadata  tadata,
                                FacetLabelProv der.FacetLabelAccessor photoAccessor,
                                byte debugMode) throws  OExcept on {
    boolean  sTw mg = term.f eld().equals(Earlyb rdF eldConstant.TW MG_L NKS_F ELD.getF eldNa ());
     nt  nternalDoc D = Doc DToT et DMapper. D_NOT_FOUND;
    long status D = -1;
    long user D = -1;
    Term facetTerm = term;

    // Deal w h t  from_user_ d facet.
     f (term.f eld().equals(Earlyb rdF eldConstant.FROM_USER_ D_CSF.getF eldNa ())) {
      user D = Long.parseLong(term.text());
      facetTerm = new Term(Earlyb rdF eldConstant.FROM_USER_ D_F ELD.getF eldNa (),
          LongTermAttr bute mpl.copy ntoNewBytesRef(user D));
    } else  f ( sTw mg) {
      status D = Long.parseLong(term.text());
       nternalDoc D = tw terReader.getSeg ntData().getDoc DToT et DMapper().getDoc D(status D);
    }

     f ( nternalDoc D == Doc DToT et DMapper. D_NOT_FOUND) {
      //  f t   s not a tw mg, t   s how status D should be looked up
      //
      //  f t   s a tw mg but   couldn't f nd t   nternalDoc D, that  ans t  seg nt,
      // or maybe even t  earlyb rd, does not conta n t  or g nal t et. T n   treat t  as
      // a normal facet for now
       nternalDoc D = tw terReader.getOldestDoc D(facetTerm);
       f ( nternalDoc D >= 0) {
        status D =
            tw terReader.getSeg ntData().getDoc DToT et DMapper().getT et D( nternalDoc D);
      } else {
        status D = -1;
      }
    }

    // make sure t et  s not deleted
     f ( nternalDoc D < 0 || tw terReader.getDeletesV ew(). sDeleted( nternalDoc D)) {
      return;
    }

     f ( tadata. sSetStatus d()
        &&  tadata.getStatus d() > 0
        &&  tadata.getStatus d() <= status D) {
      //   already have t   tadata for t  facet from an earl er t et
      return;
    }

    // now c ck  f t  t et  s offens ve, e.g. ant soc al, nsfw, sens  ve
    Earlyb rdDocu ntFeatures docu ntFeatures = new Earlyb rdDocu ntFeatures(tw terReader);
    docu ntFeatures.advance( nternalDoc D);
    boolean  sOffens veFlagSet =
        docu ntFeatures. sFlagSet(Earlyb rdF eldConstant. S_OFFENS VE_FLAG);
    boolean  sSens  veFlagSet =
        docu ntFeatures. sFlagSet(Earlyb rdF eldConstant. S_SENS T VE_CONTENT);
    boolean offens ve =  sOffens veFlagSet ||  sSens  veFlagSet;

    // also, user should not be marked as ant soc al, nsfw or offens ve
     f (user D < 0) {
      user D = docu ntFeatures.getFeatureValue(Earlyb rdF eldConstant.FROM_USER_ D_CSF);
    }
    offens ve |= userTable. sSet(user D,
        UserTable.ANT SOC AL_B T
        | UserTable.OFFENS VE_B T
        | UserTable.NSFW_B T);

     tadata.setStatus d(status D);
     tadata.setTw terUser d(user D);
     tadata.setCreated_at(tw terReader.getSeg ntData().getT  Mapper().getT  ( nternalDoc D));
     nt lang d = ( nt) docu ntFeatures.getFeatureValue(Earlyb rdF eldConstant.LANGUAGE);
    Locale lang = Thr ftLanguageUt l.getLocaleOf(Thr ftLanguage.f ndByValue(lang d));
     tadata.setStatusLanguage(Thr ftLanguageUt l.getThr ftLanguageOf(lang));
     tadata.setStatusPoss blySens  ve(offens ve);
     f ( sTw mg && photoAccessor != null && ! tadata. sSetNat vePhotoUrl()) {
       nt term D = tw terReader.getTerm D(term);
       f (term D != Earlyb rd ndexSeg ntAtom cReader.TERM_NOT_FOUND) {
        BytesRef termPayload = photoAccessor.getTermPayload(term D);
         f (termPayload != null) {
           tadata.setNat vePhotoUrl(termPayload.utf8ToStr ng());
        }
      }
    }

     f (debugMode > 3) {
      Str ngBu lder sb = new Str ngBu lder(256);
       f ( tadata. sSetExplanat on()) {
        sb.append( tadata.getExplanat on());
      }
      sb.append(Str ng.format("T et d=%d (%s %s), User d=%d (%s %s), Term=%s\n",
          status D,
           sOffens veFlagSet ? "OFFENS VE" : "",
           sSens  veFlagSet ? "SENS T VE" : "",
          user D,
          userTable. sSet(user D, UserTable.ANT SOC AL_B T) ? "ANT SOC AL" : "",
          userTable. sSet(user D, UserTable.NSFW_B T) ? "NSFW" : "",
          term.toStr ng()));
       tadata.setExplanat on(sb.toStr ng());
    }
  }

  publ c  mmutableSc ma nterface getSc maSnapshot() {
    return sc ma;
  }

  @Overr de
  publ c Collect onStat st cs collect onStat st cs(Str ng f eld) throws  OExcept on {
    return Tw ter ndexSearc r.collect onStat st cs(f eld, get ndexReader());
  }

  @Overr de
  publ c TermStat st cs termStat st cs(Term term,  nt docFreq, long totalTermFreq) {
    return Tw ter ndexSearc r.termStats(term, docFreq, totalTermFreq);
  }
}
