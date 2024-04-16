package com.tw ter.search.earlyb rd.search.relevance.scor ng;

 mport java. o. OExcept on;
 mport java.ut l.L st;

 mport com.google.common.base.Precond  ons;

 mport org.apac .lucene. ndex. ndexReader;
 mport org.apac .lucene.search.Explanat on;

 mport com.tw ter.common.collect ons.Pa r;
 mport com.tw ter.search.common.constants.thr ftjava.Thr ftLanguage;
 mport com.tw ter.search.common.features.thr ft.Thr ftSearchResultFeatures;
 mport com.tw ter.search.common.query.H Attr bute lper;
 mport com.tw ter.search.common.relevance.features.Earlyb rdDocu ntFeatures;
 mport com.tw ter.search.common.results.thr ftjava.F eldH Attr but on;
 mport com.tw ter.search.common.sc ma.base. mmutableSc ma nterface;
 mport com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants.Earlyb rdF eldConstant;
 mport com.tw ter.search.core.earlyb rd. ndex.Doc DToT et DMapper;
 mport com.tw ter.search.core.earlyb rd. ndex.Earlyb rd ndexSeg ntAtom cReader;
 mport com.tw ter.search.core.earlyb rd. ndex.T  Mapper;
 mport com.tw ter.search.earlyb rd.common.conf g.Earlyb rdConf g;
 mport com.tw ter.search.earlyb rd.search.relevance.L nearScor ngData;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftSearchResult tadata;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftSearchResult tadataOpt ons;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftSearchResultType;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftSearchResultsRelevanceStats;
 mport com.tw ter.search.queryparser.query.Query;

/**
 * Def nes a rank ng funct on wh ch computes t  score of a docu nt that matc s a query.
 */
publ c abstract class Scor ngFunct on {
  /**
   * Returned by a {@l nk #score( nt, float)} to  nd cate that a h  should be scored below all.
   *
   *   have so  equal y tests l ke:
   *   " f (score == Scor ngFunct on.SK P_H T) {...}" (DefaultScor ngFunct on#updateRelevanceStats)
   *   m ght also have double to float casts.
   *
   * Such cast ngs seem to work w h t  equal y test, but t re m ght corner cases w n cast ng
   * t  float value to a double (and back) m ght not work properly.
   *
   *  f poss ble,   should choose a constant that  s not  n t  val d score range. T n   can
   * turn t  float equal y tests  nto Math.abs(...) < EPS LON tests.
   */
  publ c stat c f nal float SK P_H T = -Float.MAX_VALUE;

  pr vate f nal  mmutableSc ma nterface sc ma;

  // T  current doc  D and t  reader for t  current seg nt should be pr vate, because   don't
  // want sub-classes to  ncorrectly update t m. T  doc  D should only be updated by t  score()
  // and expla n()  thods, and t  reader should only be updated by t  setNextReader()  thod.
  pr vate  nt currentDoc D = -1;

  protected Doc DToT et DMapper t et DMapper = null;
  protected T  Mapper t  Mapper = null;
  protected Earlyb rdDocu ntFeatures docu ntFeatures;

  protected  nt debugMode = 0;
  protected H Attr bute lper h Attr bute lper;
  protected Query query;

  protected F eldH Attr but on f eldH Attr but on;

  publ c Scor ngFunct on( mmutableSc ma nterface sc ma) {
    t .sc ma = Precond  ons.c ckNotNull(sc ma);
  }

  protected  mmutableSc ma nterface getSc ma() {
    return sc ma;
  }

  /**
   * Updates t  reader that w ll be used to retr eve t  t et  Ds and creat on t  s assoc ated
   * w h scored doc  Ds, as  ll as t  values for var ous CSFs. Should be called every t   t 
   * searc r starts search ng  n a new seg nt.
   */
  publ c vo d setNextReader(Earlyb rd ndexSeg ntAtom cReader reader) throws  OExcept on {
    t et DMapper = reader.getSeg ntData().getDoc DToT et DMapper();
    t  Mapper = reader.getSeg ntData().getT  Mapper();
    docu ntFeatures = new Earlyb rdDocu ntFeatures(reader);
     n  al zeNextSeg nt(reader);
  }

  publ c vo d setH Attr bute lperAndQuery(H Attr bute lper newH Attr bute lper,
                                            Query parsedQuery) {
    t .h Attr bute lper = newH Attr bute lper;
    t .query = parsedQuery;
  }

  publ c vo d setF eldH Attr but on(F eldH Attr but on f eldH Attr but on) {
    t .f eldH Attr but on = f eldH Attr but on;
  }

  publ c vo d setDebugMode( nt debugMode) {
    t .debugMode = debugMode;
  }

  /**
   * Allow scor ng funct ons to perform more per-seg nt-spec f c setup.
   */
  protected vo d  n  al zeNextSeg nt(Earlyb rd ndexSeg ntAtom cReader reader)
      throws  OExcept on {
    // Noop by default
  }

  // Updates t  current docu nt  D and advances all Nu r cDocValues to t  doc  D.
  pr vate vo d setCurrentDoc D( nt currentDoc D) throws  OExcept on {
    t .currentDoc D = currentDoc D;
    docu ntFeatures.advance(currentDoc D);
  }

  /**
   * Returns t  current doc  D stored  n t  scor ng funct on.
   */
  publ c  nt getCurrentDoc D() {
    return currentDoc D;
  }

  /**
   * Compute t  score for t  current h .  T   s not expected to be thread safe.
   *
   * @param  nternalDoc D     nternal  d of t  match ng h 
   * @param luceneQueryScore t  score that lucene's text query computed for t  h 
   */
  publ c float score( nt  nternalDoc D, float luceneQueryScore) throws  OExcept on {
    setCurrentDoc D( nternalDoc D);
    return score(luceneQueryScore);
  }

  /**
   * Compute t  score for t  current h .  T   s not expected to be thread safe.
   *
   * @param luceneQueryScore t  score that lucene's text query computed for t  h 
   */
  protected abstract float score(float luceneQueryScore) throws  OExcept on;

  /** Returns an explanat on for t  g ven h . */
  publ c f nal Explanat on expla n( ndexReader reader,  nt  nternalDoc D, float luceneScore)
      throws  OExcept on {
    setNextReader((Earlyb rd ndexSeg ntAtom cReader) reader);
    setCurrentDoc D( nternalDoc D);
    return doExpla n(luceneScore);
  }

  /** Returns an explanat on for t  current docu nt. */
  protected abstract Explanat on doExpla n(float luceneScore) throws  OExcept on;

  /**
   * Returns t  scor ng  tadata for t  current doc  D.
   */
  publ c Thr ftSearchResult tadata getResult tadata(Thr ftSearchResult tadataOpt ons opt ons)
      throws  OExcept on {
    Thr ftSearchResult tadata  tadata = new Thr ftSearchResult tadata();
     tadata.setResultType(Thr ftSearchResultType.RELEVANCE);
     tadata.setPengu nVers on(Earlyb rdConf g.getPengu nVers onByte());
     tadata.setLanguage(Thr ftLanguage.f ndByValue(
        ( nt) docu ntFeatures.getFeatureValue(Earlyb rdF eldConstant.LANGUAGE)));
     tadata.setS gnature(
        ( nt) docu ntFeatures.getFeatureValue(Earlyb rdF eldConstant.TWEET_S GNATURE));
     tadata.set sNullcast(docu ntFeatures. sFlagSet(Earlyb rdF eldConstant. S_NULLCAST_FLAG));
    return  tadata;
  }

  /**
   * Updates t  g ven Thr ftSearchResultsRelevanceStats  nstance based on t  scor ng  tadata for
   * t  current doc  D.
   */
  publ c abstract vo d updateRelevanceStats(Thr ftSearchResultsRelevanceStats relevanceStats);

  /**
   * Score a l st of h s. Not thread safe.
   */
  publ c float[] batchScore(L st<BatchH > h s) throws  OExcept on {
    throw new UnsupportedOperat onExcept on("T  operat on (batchScore)  s not  mple nted!");
  }

  /**
   * Collect t  features and CSFs for t  current docu nt. Used for scor ng and generat ng t 
   * returned  tadata.
   */
  publ c Pa r<L nearScor ngData, Thr ftSearchResultFeatures> collectFeatures(
      float luceneQueryScore) throws  OExcept on {
    throw new UnsupportedOperat onExcept on("T  operat on (collectFeatures)  s not  mple nted!");
  }

  /**
   *  mple nt t  funct on to populate t  result  tadata based on t  g ven scor ng data.
   * Ot rw se, t   s a no-op.
   *
   * Scor ng funct ons that  mple nt t  should also  mple nt getScor ngData().
   */
  publ c vo d populateResult tadataBasedOnScor ngData(
      Thr ftSearchResult tadataOpt ons opt ons,
      Thr ftSearchResult tadata  tadata,
      L nearScor ngData data) throws  OExcept on {
    // Make sure that t  scor ng data passed  n  s null because getScor ngDataForCurrentDocu nt()
    // returns null by default and  f a subclass overr des one of t se two  thods,   should
    // overr de both.
    Precond  ons.c ckState(data == null, "L nearScor ngData should be null");
  }

  /**
   * T  should only be called at h  collect on t   because   rel es on t   nternal doc  d.
   *
   * Scor ng funct ons that  mple nt t  should also  mple nt t  funct on
   * populateResult tadataBasedOnScor ngData().
   */
  publ c L nearScor ngData getScor ngDataForCurrentDocu nt() {
    return null;
  }
}
