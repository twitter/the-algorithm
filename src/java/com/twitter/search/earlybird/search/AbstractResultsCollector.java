package com.tw ter.search.earlyb rd.search;

 mport java. o. OExcept on;
 mport java.ut l.ArrayL st;
 mport java.ut l.L st;
 mport java.ut l.Map;
 mport java.ut l.Set;

 mport com.google.common.annotat ons.V s bleForTest ng;
 mport com.google.common.base.Opt onal;
 mport com.google.common.base.Precond  ons;
 mport com.google.common.collect.Maps;
 mport com.google.common.collect.Sets;

 mport org.apac .commons.collect ons.Collect onUt ls;
 mport org.apac .lucene. ndex.LeafReader;
 mport org.apac .lucene. ndex.LeafReaderContext;
 mport org.apac .lucene.search.Doc dSet erator;
 mport org.apac .lucene.search.ScoreMode;

 mport com.tw ter.common.ut l.Clock;
 mport com.tw ter.search.common.constants.thr ftjava.Thr ftLanguage;
 mport com.tw ter.search.common.part  on ng.snowflakeparser.Snowflake dParser;
 mport com.tw ter.search.common.relevance.features.Earlyb rdDocu ntFeatures;
 mport com.tw ter.search.common.results.thr ftjava.F eldH Attr but on;
 mport com.tw ter.search.common.results.thr ftjava.F eldH L st;
 mport com.tw ter.search.common.sc ma.base. mmutableSc ma nterface;
 mport com.tw ter.search.common.sc ma.base.Sc ma;
 mport com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdCluster;
 mport com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants.Earlyb rdF eldConstant;
 mport com.tw ter.search.common.search.Tw terEarlyTerm nat onCollector;
 mport com.tw ter.search.common.ut l.spat al.GeoUt l;
 mport com.tw ter.search.core.earlyb rd.facets.AbstractFacetCount ngArray;
 mport com.tw ter.search.core.earlyb rd. ndex.Earlyb rd ndexSeg ntAtom cReader;
 mport com.tw ter.search.core.earlyb rd. ndex.Earlyb rd ndexSeg ntData;
 mport com.tw ter.search.core.earlyb rd. ndex.T  Mapper;
 mport com.tw ter.search.core.earlyb rd. ndex. nverted.QueryCostTracker;
 mport com.tw ter.search.earlyb rd.common.conf g.Earlyb rdConf g;
 mport com.tw ter.search.earlyb rd.common.userupdates.UserTable;
 mport com.tw ter.search.earlyb rd. ndex.Earlyb rdS ngleSeg ntSearc r;
 mport com.tw ter.search.earlyb rd. ndex.T et DMapper;
 mport com.tw ter.search.earlyb rd.search.facets.FacetLabelCollector;
 mport com.tw ter.search.earlyb rd.stats.Earlyb rdSearc rStats;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftFacetLabel;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftSearchQuery;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftSearchResultExtra tadata;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftSearchResultGeoLocat on;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftSearchResult tadata;
 mport com.tw ter.search.queryparser.ut l. dT  Ranges;

 mport geo.google.datamodel.GeoCoord nate;

/**
 * Abstract parent class for all results collectors  n earlyb rd.
 * T  collector should be able to handle both s ngle-seg nt and
 * mult -seg nt collect on.
 */
publ c abstract class AbstractResultsCollector<R extends SearchRequest nfo,
    S extends SearchResults nfo>
    extends Tw terEarlyTerm nat onCollector {
  enum  dAndRangeUpdateType {
    BEG N_SEGMENT,
    END_SEGMENT,
    H T
  }

  // Earlyb rd used to have a spec al early term nat on log c: at seg nt boundar es
  // t  collector est mates how much t    'll take to search t  next seg nt.
  //  f t  est mate * 1.5 w ll cause t  request to t  out, t  search early term nates.
  // That log c  s removed  n favor of more f ne gra ned c cks---now   c ck t  out
  // w h n a seg nt, every 2,000,000 docs processed.
  pr vate stat c f nal  nt EXPENS VE_TERM NAT ON_CHECK_ NTERVAL =
      Earlyb rdConf g.get nt("expens ve_term nat on_c ck_ nterval", 2000000);

  pr vate stat c f nal long NO_T ME_SL CE_ D = -1;

  protected f nal R searchRequest nfo;

  // So t  s maxH sToProcess can also co  from places ot r than collector params.
  // E.g. from searchQuery.getRelevanceOpt ons(). T  prov des a way to allow
  // subclasses to overr de t  maxH sToProcess on collector params.
  pr vate f nal long maxH sToProcessOverr de;

  // m n and max status  d actually cons dered  n t  search (may not be a h )
  pr vate long m nSearc dStatus D = Long.MAX_VALUE;
  pr vate long maxSearc dStatus D = Long.M N_VALUE;

  pr vate  nt m nSearc dT   =  nteger.MAX_VALUE;
  pr vate  nt maxSearc dT   =  nteger.M N_VALUE;

  // per-seg nt start t  . W ll be re-started  n setNextReader().
  pr vate long seg ntStartT  ;

  // Current seg nt be ng searc d.
  protected Earlyb rd ndexSeg ntAtom cReader currTw terReader;
  protected T et DMapper t et dMapper;
  protected T  Mapper t  Mapper;
  protected long currT  Sl ce D = NO_T ME_SL CE_ D;

  pr vate f nal long queryT  ;

  // T   per ods,  n m ll seconds, for wh ch h s are counted.
  pr vate f nal L st<Long> h CountsThresholdsMsec;

  // h Counts[ ]  s t  number of h s that are more recent than h CountsThresholdsMsec[ ]
  pr vate f nal  nt[] h Counts;

  pr vate f nal  mmutableSc ma nterface sc ma;

  pr vate f nal Earlyb rdSearc rStats searc rStats;
  // For collectors that f ll  n t  results' geo locat ons, t  w ll be used to retr eve t 
  // docu nts' lat/lon coord nates.
  pr vate GeoCoord nate resultGeoCoord nate;
  protected f nal boolean f ll nLatLonForH s;

  protected Earlyb rdDocu ntFeatures docu ntFeatures;
  protected boolean featuresRequested = false;

  pr vate f nal FacetLabelCollector facetCollector;

  // debugMode set  n request to determ ne debugg ng level.
  pr vate  nt requestDebugMode;

  // debug  nfo to be returned  n earlyb rd response
  protected L st<Str ng> debug nfo;

  pr vate  nt numH sCollectedPerSeg nt;

  publ c AbstractResultsCollector(
       mmutableSc ma nterface sc ma,
      R searchRequest nfo,
      Clock clock,
      Earlyb rdSearc rStats searc rStats,
       nt requestDebugMode) {
    super(searchRequest nfo.getSearchQuery().getCollectorParams(),
        searchRequest nfo.getTerm nat onTracker(),
        QueryCostTracker.getTracker(),
        EXPENS VE_TERM NAT ON_CHECK_ NTERVAL,
        clock);

    t .sc ma = sc ma;
    t .searchRequest nfo = searchRequest nfo;
    Thr ftSearchQuery thr ftSearchQuery = searchRequest nfo.getSearchQuery();
    t .maxH sToProcessOverr de = searchRequest nfo.getMaxH sToProcess();
    t .facetCollector = bu ldFacetCollector(searchRequest nfo, sc ma);

     f (searchRequest nfo.getT  stamp() > 0) {
      queryT   = searchRequest nfo.getT  stamp();
    } else {
      queryT   = System.currentT  M ll s();
    }
    h CountsThresholdsMsec = thr ftSearchQuery.getH CountBuckets();
    h Counts = h CountsThresholdsMsec == null || h CountsThresholdsMsec.s ze() == 0
        ? null
        : new  nt[h CountsThresholdsMsec.s ze()];

    t .searc rStats = searc rStats;

    Sc ma.F eld nfo latLonCSFF eld =
        sc ma.hasF eld(Earlyb rdF eldConstant.LAT_LON_CSF_F ELD.getF eldNa ())
            ? sc ma.getF eld nfo(Earlyb rdF eldConstant.LAT_LON_CSF_F ELD.getF eldNa ())
            : null;
    boolean loadLatLonMapper ntoRam = true;
     f (latLonCSFF eld != null) {
      //  f t  latlon_csf f eld  s expl c ly def ned, t n take t  conf g from t  sc ma.
      //  f  's not def ned,   assu  that t  latlon mapper  s stored  n  mory.
      loadLatLonMapper ntoRam = latLonCSFF eld.getF eldType(). sCsfLoad ntoRam();
    }
    // Default to not f ll  n lat/lon  f t  lat/lon CSF f eld  s not loaded  nto RAM
    t .f ll nLatLonForH s = Earlyb rdConf g.getBool("f ll_ n_lat_lon_for_h s",
        loadLatLonMapper ntoRam);
    t .requestDebugMode = requestDebugMode;

     f (shouldCollectDeta ledDebug nfo()) {
      t .debug nfo = new ArrayL st<>();
      debug nfo.add("Start ng Search");
    }
  }

  pr vate stat c FacetLabelCollector bu ldFacetCollector(
      SearchRequest nfo request,
       mmutableSc ma nterface sc ma) {
     f (Collect onUt ls. sEmpty(request.getFacetF eldNa s())) {
      return null;
    }

    // Get all facet f eld  ds requested.
    Set<Str ng> requ redF elds = Sets.newHashSet();
    for (Str ng f eldNa  : request.getFacetF eldNa s()) {
      Sc ma.F eld nfo f eld = sc ma.getFacetF eldByFacetNa (f eldNa );
       f (f eld != null) {
        requ redF elds.add(f eld.getF eldType().getFacetNa ());
      }
    }

     f (requ redF elds.s ze() > 0) {
      return new FacetLabelCollector(requ redF elds);
    } else {
      return null;
    }
  }

  /**
   * Subclasses should  mple nt t  follow ng  thods.
   */

  // Subclasses should process collected h s and construct a f nal
  // AbstractSearchResults object.
  protected abstract S doGetResults() throws  OExcept on;

  // Subclasses can overr de t   thod to add more collect on log c.
  protected abstract vo d doCollect(long t et D) throws  OExcept on;

  publ c f nal  mmutableSc ma nterface getSc ma() {
    return sc ma;
  }

  // Updates t  h  count array - each result only  ncre nts t  f rst qual fy ng bucket.
  protected f nal vo d updateH Counts(long status d) {
     f (h Counts == null) {
      return;
    }

    long delta = queryT   - Snowflake dParser.getT  stampFromT et d(status d);
    for ( nt   = 0;   < h CountsThresholdsMsec.s ze(); ++ ) {
       f (delta >= 0 && delta < h CountsThresholdsMsec.get( )) {
        h Counts[ ]++;
        //  ncre nts to t  rest of t  count array are  mpl ed, and aggregated later, s nce t 
        // array  s sorted.
        break;
      }
    }
  }

  pr vate boolean searc dStatus DsAndT  s n  al zed() {
    return maxSearc dStatus D != Long.M N_VALUE;
  }

  // Updates t  f rst searc d status  D w n start ng to search a new seg nt.
  pr vate vo d updateF rstSearc dStatus D() {
    // Only try to update t  m n/max searc d  ds,  f t  seg nt/reader actually has docu nts
    // See SEARCH-4535
     nt m nDoc D = currTw terReader.getSmallestDoc D();
     f (currTw terReader.hasDocs() && m nDoc D >= 0 && !searc dStatus DsAndT  s n  al zed()) {
      f nal long f rstStatus D = t et dMapper.getT et D(m nDoc D);
      f nal  nt f rstStatusT   = t  Mapper.getT  (m nDoc D);
       f (shouldCollectDeta ledDebug nfo()) {
        debug nfo.add(
            "updateF rstSearc dStatus D. m nDoc d=" + m nDoc D + ", f rstStatus D="
                + f rstStatus D + ", f rstStatusT  =" + f rstStatusT  );
      }
      update DandT  Ranges(f rstStatus D, f rstStatusT  ,  dAndRangeUpdateType.BEG N_SEGMENT);
    }
  }

  publ c f nal R getSearchRequest nfo() {
    return searchRequest nfo;
  }

  publ c f nal long getM nSearc dStatus D() {
    return m nSearc dStatus D;
  }

  publ c f nal long getMaxSearc dStatus D() {
    return maxSearc dStatus D;
  }

  publ c f nal  nt getM nSearc dT  () {
    return m nSearc dT  ;
  }

  publ c boolean  sSetM nSearc dT  () {
    return m nSearc dT   !=  nteger.MAX_VALUE;
  }

  publ c f nal  nt getMaxSearc dT  () {
    return maxSearc dT  ;
  }

  @Overr de
  publ c f nal long getMaxH sToProcess() {
    return maxH sToProcessOverr de;
  }

  // Not f es classes that a new  ndex seg nt  s about to be searc d.
  @Overr de
  publ c f nal vo d setNextReader(LeafReaderContext context) throws  OExcept on {
    super.setNextReader(context);
    setNextReader(context.reader());
  }

  /**
   * Not f es t  collector that a new seg nt  s about to be searc d.
   *
   *  's eas er to use t   thod from tests, because LeafReader  s not a f nal class, so   can
   * be mocked (unl ke LeafReaderContext).
   */
  @V s bleForTest ng
  publ c f nal vo d setNextReader(LeafReader reader) throws  OExcept on {
     f (!(reader  nstanceof Earlyb rd ndexSeg ntAtom cReader)) {
      throw new Runt  Except on(" ndexReader type not supported: " + reader.getClass());
    }

    currTw terReader = (Earlyb rd ndexSeg ntAtom cReader) reader;
    docu ntFeatures = new Earlyb rdDocu ntFeatures(currTw terReader);
    t et dMapper = (T et DMapper) currTw terReader.getSeg ntData().getDoc DToT et DMapper();
    t  Mapper = currTw terReader.getSeg ntData().getT  Mapper();
    currT  Sl ce D = currTw terReader.getSeg ntData().getT  Sl ce D();
    updateF rstSearc dStatus D();
     f (shouldCollectDeta ledDebug nfo()) {
      debug nfo.add("Start ng search  n seg nt w h t  sl ce  D: " + currT  Sl ce D);
    }

    seg ntStartT   = getClock().nowM ll s();
    startSeg nt();
  }

  protected abstract vo d startSeg nt() throws  OExcept on;

  @Overr de
  protected f nal vo d doCollect() throws  OExcept on {
    docu ntFeatures.advance(curDoc d);
    long t et D = t et dMapper.getT et D(curDoc d);
    update DandT  Ranges(t et D, t  Mapper.getT  (curDoc d),  dAndRangeUpdateType.H T);
    doCollect(t et D);
    numH sCollectedPerSeg nt++;
  }

  protected vo d collectFeatures(Thr ftSearchResult tadata  tadata) throws  OExcept on {
     f (featuresRequested) {
      ensureExtra tadata sSet( tadata);

       tadata.getExtra tadata().setD rectedAtUser d(
          docu ntFeatures.getFeatureValue(Earlyb rdF eldConstant.D RECTED_AT_USER_ D_CSF));
       tadata.getExtra tadata().setQuotedT et d(
          docu ntFeatures.getFeatureValue(Earlyb rdF eldConstant.QUOTED_TWEET_ D_CSF));
       tadata.getExtra tadata().setQuotedUser d(
          docu ntFeatures.getFeatureValue(Earlyb rdF eldConstant.QUOTED_USER_ D_CSF));

       nt cardLangValue =
          ( nt) docu ntFeatures.getFeatureValue(Earlyb rdF eldConstant.CARD_LANG_CSF);
      Thr ftLanguage thr ftLanguage = Thr ftLanguage.f ndByValue(cardLangValue);
       tadata.getExtra tadata().setCardLang(thr ftLanguage);

      long cardNu r cUr  =
          (long) docu ntFeatures.getFeatureValue(Earlyb rdF eldConstant.CARD_UR _CSF);
       f (cardNu r cUr  > 0) {
         tadata.getExtra tadata().setCardUr (Str ng.format("card://%s", cardNu r cUr ));
      }
    }
  }

  protected vo d collect sProtected(
      Thr ftSearchResult tadata  tadata, Earlyb rdCluster cluster, UserTable userTable)
      throws  OExcept on {
    // ' sUserProtected' f eld  s only set for arch ve cluster because only arch ve cluster user
    // table has  S_PROTECTED_B T populated.
    // S nce t  b   s c cked after UserFlagsExcludeF lter c cked t  b , t re  s a sl ght
    // chance that t  b   s updated  n-bet en. W n that happens,    s poss ble that   w ll
    // see a small number of protected T ets  n t  response w n    ant to exclude t m.
     f (cluster == Earlyb rdCluster.FULL_ARCH VE) {
      ensureExtra tadata sSet( tadata);
      long user d = docu ntFeatures.getFeatureValue(Earlyb rdF eldConstant.FROM_USER_ D_CSF);
      boolean  sProtected = userTable. sSet(user d, UserTable. S_PROTECTED_B T);
       tadata.getExtra tadata().set sUserProtected( sProtected);
    }
  }

  protected vo d collectExclus veConversat onAuthor d(Thr ftSearchResult tadata  tadata)
      throws  OExcept on {
     f (searchRequest nfo. sCollectExclus veConversat onAuthor d()) {
      long exclus veConversat onAuthor d = docu ntFeatures.getFeatureValue(
          Earlyb rdF eldConstant.EXCLUS VE_CONVERSAT ON_AUTHOR_ D_CSF);
       f (exclus veConversat onAuthor d != 0L) {
        ensureExtra tadata sSet( tadata);
         tadata.getExtra tadata().setExclus veConversat onAuthor d(exclus veConversat onAuthor d);
      }
    }
  }

  //   only makes sense to collectFacets for search types that return  nd v dual results (recency,
  // relevance and top_t ets), wh ch use t  AbstractRelevanceCollector and SearchResultsCollector,
  // so t   thod should only be called from t se classes.
  protected vo d collectFacets(Thr ftSearchResult tadata  tadata) {
     f (currTw terReader == null) {
      return;
    }

    AbstractFacetCount ngArray facetCount ngArray = currTw terReader.getFacetCount ngArray();
    Earlyb rd ndexSeg ntData seg ntData = currTw terReader.getSeg ntData();

     f (facetCount ngArray == null || facetCollector == null) {
      return;
    }

    facetCollector.resetFacetLabelProv ders(
        seg ntData.getFacetLabelProv ders(),
        seg ntData.getFacet DMap());

    facetCount ngArray.collectForDoc d(curDoc d, facetCollector);

    L st<Thr ftFacetLabel> labels = facetCollector.getLabels();
     f (labels.s ze() > 0) {
       tadata.setFacetLabels(labels);
    }
  }

  protected vo d ensureExtra tadata sSet(Thr ftSearchResult tadata  tadata) {
     f (! tadata. sSetExtra tadata()) {
       tadata.setExtra tadata(new Thr ftSearchResultExtra tadata());
    }
  }

  @Overr de
  protected f nal vo d doF n shSeg nt( nt lastSearc dDoc D) {
     f (shouldCollectDeta ledDebug nfo()) {
      long t  SpentSearch ngSeg nt nM ll s = getClock().nowM ll s() - seg ntStartT  ;
      debug nfo.add("F n s d seg nt at doc  d: " + lastSearc dDoc D);
      debug nfo.add("T   spent search ng " + currT  Sl ce D
        + ": " + t  SpentSearch ngSeg nt nM ll s + "ms");
      debug nfo.add("Number of h s collected  n seg nt " + currT  Sl ce D + ": "
          + numH sCollectedPerSeg nt);
    }

     f (!currTw terReader.hasDocs()) {
      // Due to race bet en t  reader and t   ndex ng thread, a seem ngly empty seg nt that
      // does not have docu nt comm ted  n t  post ng l sts, m ght already have a docu nt
      //  nserted  nto t   d/t   mappers, wh ch   do not want to take  nto account.
      //  f t re are no docu nts  n t  seg nt,   don't update searc d m n/max  ds to
      // anyth ng.
      return;
    } else  f (lastSearc dDoc D == Doc dSet erator.NO_MORE_DOCS) {
      // Seg nt exhausted.
       f (shouldCollectDeta ledDebug nfo()) {
        debug nfo.add("Seg nt exhausted");
      }
      update DandT  Ranges(t et dMapper.getM nT et D(), t  Mapper.getF rstT  (),
           dAndRangeUpdateType.END_SEGMENT);
    } else  f (lastSearc dDoc D >= 0) {
      long lastSearc dT et D = t et dMapper.getT et D(lastSearc dDoc D);
       nt lastSearchT etT   = t  Mapper.getT  (lastSearc dDoc D);
       f (shouldCollectDeta ledDebug nfo()) {
        debug nfo.add("lastSearc dDoc d=" + lastSearc dDoc D);
      }
      update DandT  Ranges(lastSearc dT et D, lastSearchT etT  ,
           dAndRangeUpdateType.END_SEGMENT);
    }

    numH sCollectedPerSeg nt = 0;
  }

  pr vate vo d update DandT  Ranges(long t et D,  nt t  ,  dAndRangeUpdateType updateType) {
    //   need to update m nSearc dStatus D/maxSearc dStatus D and
    // m nSearc dT  /maxSearc dT    ndependently: SEARCH-6139
    m nSearc dStatus D = Math.m n(m nSearc dStatus D, t et D);
    maxSearc dStatus D = Math.max(maxSearc dStatus D, t et D);
     f (t   > 0) {
      m nSearc dT   = Math.m n(m nSearc dT  , t  );
      maxSearc dT   = Math.max(maxSearc dT  , t  );
    }
     f (shouldCollectVerboseDebug nfo()) {
      debug nfo.add(
          Str ng.format("call to update DandT  Ranges(%d, %d, %s)"
                  + " set m nSearchStatus D=%d, maxSearc dStatus D=%d,"
                  + " m nSearc dT  =%d, maxSearc dT  =%d)",
              t et D, t  , updateType.toStr ng(),
              m nSearc dStatus D, maxSearc dStatus D,
              m nSearc dT  , maxSearc dT  ));
    }
  }

  /**
   * T   s called w n a seg nt  s sk pped but   would want to do account ng
   * for m nSearchDoc d as  ll as numDocsProcessed.
   */
  publ c vo d sk pSeg nt(Earlyb rdS ngleSeg ntSearc r searc r) throws  OExcept on {
    setNextReader(searc r.getTw ter ndexReader().getContext());
    trackCompleteSeg nt(Doc dSet erator.NO_MORE_DOCS);
     f (shouldCollectDeta ledDebug nfo()) {
      debug nfo.add("Sk pp ng seg nt: " + currT  Sl ce D);
    }
  }

  /**
   * Returns t  results collected by t  collector.
   */
  publ c f nal S getResults() throws  OExcept on {
    //  n order to make pag nat on work,  f m nSearc dStatus D  s greater than t  asked max_ d.
    //   force t  m nSearc dStatus D to be max_ d + 1.
     dT  Ranges  dT  Ranges = searchRequest nfo.get dT  Ranges();
     f ( dT  Ranges != null) {
      Opt onal<Long> max D nclus ve =  dT  Ranges.getMax D nclus ve();
       f (max D nclus ve. sPresent() && m nSearc dStatus D > max D nclus ve.get()) {
        searc rStats.numCollectorAdjustedM nSearc dStatus D. ncre nt();
        m nSearc dStatus D = max D nclus ve.get() + 1;
      }
    }

    S results = doGetResults();
    results.setNumH sProcessed(( nt) getNumH sProcessed());
    results.setNumSearc dSeg nts(getNumSearc dSeg nts());
     f (searc dStatus DsAndT  s n  al zed()) {
      results.setMaxSearc dStatus D(maxSearc dStatus D);
      results.setM nSearc dStatus D(m nSearc dStatus D);
      results.setMaxSearc dT  (maxSearc dT  );
      results.setM nSearc dT  (m nSearc dT  );
    }
    results.setEarlyTerm nated(getEarlyTerm nat onState(). sTerm nated());
     f (getEarlyTerm nat onState(). sTerm nated()) {
      results.setEarlyTerm nat onReason(getEarlyTerm nat onState().getTerm nat onReason());
    }
    Map<Long,  nteger> counts = getH CountMap();
     f (counts != null) {
      results.h Counts.putAll(counts);
    }
    return results;
  }

  /**
   * Returns a map of t  stamps (spec f ed  n t  query) to t  number of h s that are more recent
   * that t  respect ve t  stamps.
   */
  publ c f nal Map<Long,  nteger> getH CountMap() {
     nt total = 0;
     f (h Counts == null) {
      return null;
    }
    Map<Long,  nteger> map = Maps.newHashMap();
    // s nce t  array  s  ncre ntal, need to aggregate  re.
    for ( nt   = 0;   < h Counts.length; ++ ) {
      map.put(h CountsThresholdsMsec.get( ), total += h Counts[ ]);
    }
    return map;
  }

  /**
   * Common  lper for collect ng per-f eld h  attr but on data ( f  's ava lable).
   *
   * @param  tadata t   tadata to f ll for t  h .
   */
  protected f nal vo d f llH Attr but on tadata(Thr ftSearchResult tadata  tadata) {
     f (searchRequest nfo.getH Attr bute lper() == null) {
      return;
    }

    Map< nteger, L st<Str ng>> h Attr buteMapp ng =
        searchRequest nfo.getH Attr bute lper().getH Attr but on(curDoc d);
    Precond  ons.c ckNotNull(h Attr buteMapp ng);

    F eldH Attr but on f eldH Attr but on = new F eldH Attr but on();
    for (Map.Entry< nteger, L st<Str ng>> entry : h Attr buteMapp ng.entrySet()) {
      F eldH L st f eldH L st = new F eldH L st();
      f eldH L st.setH F elds(entry.getValue());

      f eldH Attr but on.putToH Map(entry.getKey(), f eldH L st);
    }
     tadata.setF eldH Attr but on(f eldH Attr but on);
  }

  /**
   * F ll t  geo locat on of t  g ven docu nt  n  tadata,  f   have t  lat/lon for  .
   * For quer es that spec fy a geolocat on, t  w ll also have t  d stance from
   * t  locat on spec f ed  n t  query, and t  locat on of t  docu nt.
   */
  protected f nal vo d f llResultGeoLocat on(Thr ftSearchResult tadata  tadata)
      throws  OExcept on {
    Precond  ons.c ckNotNull( tadata);
     f (currTw terReader != null && f ll nLatLonForH s) {
      // See  f   can have a lat/lon for t  doc.
       f (resultGeoCoord nate == null) {
        resultGeoCoord nate = new GeoCoord nate();
      }
      // Only f ll  f necessary
       f (searchRequest nfo. sCollectResultLocat on()
          && GeoUt l.decodeLatLonFrom nt64(
              docu ntFeatures.getFeatureValue(Earlyb rdF eldConstant.LAT_LON_CSF_F ELD),
              resultGeoCoord nate)) {
        Thr ftSearchResultGeoLocat on resultLocat on = new Thr ftSearchResultGeoLocat on();
        resultLocat on.setLat ude(resultGeoCoord nate.getLat ude());
        resultLocat on.setLong ude(resultGeoCoord nate.getLong ude());
         tadata.setResultLocat on(resultLocat on);
      }
    }
  }

  @Overr de
  publ c ScoreMode scoreMode() {
    return ScoreMode.COMPLETE;
  }

  pr vate  nt term nat onDoc D = -1;

  @Overr de
  protected vo d collectedEnoughResults() throws  OExcept on {
    //   f nd 'term nat onDoc D' once   collect enough results, so that   know t  po nt at wh ch
    //   can stop search ng.   must do t  because w h t  unordered doc  D mapper, t ets
    // are not ordered w h n a m ll second, so   must search t  ent re m ll second bucket before
    // term nat ng t  search, ot rw se   could sk p over t ets and have an  ncorrect
    // m nSearc dStatus D.
     f (curDoc d != -1 && term nat onDoc D == -1) {
      long t et d = t et dMapper.getT et D(curDoc d);
      //   want to f nd t  h g st poss ble doc  D for t  t et d, so pass true.
      boolean f ndMaxDoc D = true;
      term nat onDoc D = t et dMapper.f ndDoc dBound(t et d,
          f ndMaxDoc D,
          curDoc d,
          curDoc d);
    }
  }

  @Overr de
  protected boolean shouldTerm nate() {
    return curDoc d >= term nat onDoc D;
  }

  @Overr de
  publ c L st<Str ng> getDebug nfo() {
    return debug nfo;
  }

  protected boolean shouldCollectDeta ledDebug nfo() {
    return requestDebugMode >= 5;
  }

  // Use t  for per-result debug  nfo. Useful for quer es w h no results
  // or a very small number of results.
  protected boolean shouldCollectVerboseDebug nfo() {
    return requestDebugMode >= 6;
  }
}
