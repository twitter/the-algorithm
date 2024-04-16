package com.tw ter.search.earlyb rd.search;

 mport java. o. OExcept on;
 mport java.ut l.ArrayL st;
 mport java.ut l.Collect ons;
 mport java.ut l.HashSet;
 mport java.ut l.L st;
 mport java.ut l.Set;

 mport com.tw ter.common.ut l.Clock;
 mport com.tw ter.search.common.constants.thr ftjava.Thr ftLanguage;
 mport com.tw ter.search.common.features.thr ft.Thr ftSearchResultFeatures;
 mport com.tw ter.search.common.sc ma.base. mmutableSc ma nterface;
 mport com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdCluster;
 mport com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants.Earlyb rdF eldConstant;
 mport com.tw ter.search.common.search.EarlyTerm nat onState;
 mport com.tw ter.search.common.ut l.Long ntConverter;
 mport com.tw ter.search.earlyb rd.common.conf g.Earlyb rdConf g;
 mport com.tw ter.search.earlyb rd.common.userupdates.UserTable;
 mport com.tw ter.search.earlyb rd.stats.Earlyb rdSearc rStats;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftSearchResult tadata;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftSearchResult tadataOpt ons;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftSearchResultType;

/**
 * T  class collects results for Recency quer es for delegat on to collectors based on query mode
 */
publ c class SearchResultsCollector
    extends AbstractResultsCollector<SearchRequest nfo, S mpleSearchResults> {
  pr vate stat c f nal EarlyTerm nat onState TERM NATED_COLLECTED_ENOUGH_RESULTS =
      new EarlyTerm nat onState("term nated_collected_enough_results", true);

  protected f nal L st<H > results;
  pr vate f nal Set< nteger> requestedFeature ds;
  pr vate f nal Earlyb rdCluster cluster;
  pr vate f nal UserTable userTable;

  publ c SearchResultsCollector(
       mmutableSc ma nterface sc ma,
      SearchRequest nfo searchRequest nfo,
      Clock clock,
      Earlyb rdSearc rStats searc rStats,
      Earlyb rdCluster cluster,
      UserTable userTable,
       nt requestDebugMode) {
    super(sc ma, searchRequest nfo, clock, searc rStats, requestDebugMode);
    results = new ArrayL st<>();
    t .cluster = cluster;
    t .userTable = userTable;

    Thr ftSearchResult tadataOpt ons opt ons =
        searchRequest nfo.getSearchQuery().getResult tadataOpt ons();
     f (opt ons != null && opt ons. sReturnSearchResultFeatures()) {
      requestedFeature ds = sc ma.getSearchFeatureSc ma().getEntr es().keySet();
    } else  f (opt ons != null && opt ons. sSetRequestedFeature Ds()) {
      requestedFeature ds = new HashSet<>(opt ons.getRequestedFeature Ds());
    } else {
      requestedFeature ds = null;
    }
  }

  @Overr de
  publ c vo d startSeg nt() throws  OExcept on {
    featuresRequested = requestedFeature ds != null;
  }

  @Overr de
  publ c vo d doCollect(long t et D) throws  OExcept on {
    H  h  = new H (currT  Sl ce D, t et D);
    Thr ftSearchResult tadata  tadata =
        new Thr ftSearchResult tadata(Thr ftSearchResultType.RECENCY)
            .setPengu nVers on(Earlyb rdConf g.getPengu nVers onByte());

    // Set t et language  n  tadata
    Thr ftLanguage thr ftLanguage = Thr ftLanguage.f ndByValue(
        ( nt) docu ntFeatures.getFeatureValue(Earlyb rdF eldConstant.LANGUAGE));
     tadata.setLanguage(thr ftLanguage);

    // C ck and collect h  attr but on data,  f  's ava lable.
    f llH Attr but on tadata( tadata);

    // Set t  nullcast flag  n  tadata
     tadata.set sNullcast(docu ntFeatures. sFlagSet(Earlyb rdF eldConstant. S_NULLCAST_FLAG));

     f (searchRequest nfo. sCollectConversat on d()) {
      long conversat on d =
          docu ntFeatures.getFeatureValue(Earlyb rdF eldConstant.CONVERSAT ON_ D_CSF);
       f (conversat on d != 0) {
        ensureExtra tadata sSet( tadata);
         tadata.getExtra tadata().setConversat on d(conversat on d);
      }
    }

    f llResultGeoLocat on( tadata);
    collectRet etAndReply tadata( tadata);

    long fromUser d = docu ntFeatures.getFeatureValue(Earlyb rdF eldConstant.FROM_USER_ D_CSF);
     f (requestedFeature ds != null) {
      Thr ftSearchResultFeatures features = docu ntFeatures.getSearchResultFeatures(
          getSc ma(), requestedFeature ds::conta ns);
      ensureExtra tadata sSet( tadata);
       tadata.getExtra tadata().setFeatures(features);
       tadata.setFromUser d(fromUser d);
       f (docu ntFeatures. sFlagSet(Earlyb rdF eldConstant.HAS_CARD_FLAG)) {
         tadata.setCardType(
            (byte) docu ntFeatures.getFeatureValue(Earlyb rdF eldConstant.CARD_TYPE_CSF_F ELD));
      }
    }
     f (searchRequest nfo. sGetFromUser d()) {
       tadata.setFromUser d(fromUser d);
    }

    collectExclus veConversat onAuthor d( tadata);
    collectFacets( tadata);
    collectFeatures( tadata);
    collect sProtected( tadata, cluster, userTable);
    h .set tadata( tadata);
    results.add(h );
    updateH Counts(t et D);
  }

  pr vate f nal vo d collectRet etAndReply tadata(Thr ftSearchResult tadata  tadata)
      throws  OExcept on {
     f (searchRequest nfo. sGet nReplyToStatus d() || searchRequest nfo. sGetReferenceAuthor d()) {
      boolean  sRet et = docu ntFeatures. sFlagSet(Earlyb rdF eldConstant. S_RETWEET_FLAG);
      boolean  sReply = docu ntFeatures. sFlagSet(Earlyb rdF eldConstant. S_REPLY_FLAG);
      // Set t   sRet et and  sReply  tadata so that cl ents who request ret et and reply
      //  tadata know w t r a result  s a ret et or reply or ne  r.
       tadata.set sRet et( sRet et);
       tadata.set sReply( sReply);

      // Only store t  shared status  d  f t  h   s a reply or a ret et and
      // t  get nReplyToStatus d flag  s set.
       f (searchRequest nfo. sGet nReplyToStatus d() && ( sReply ||  sRet et)) {
        long sharedStatus D =
            docu ntFeatures.getFeatureValue(Earlyb rdF eldConstant.SHARED_STATUS_ D_CSF);
         f (sharedStatus D != 0) {
           tadata.setSharedStatus d(sharedStatus D);
        }
      }

      // Only store t  reference t et author  D  f t  h   s a reply or a ret et and t 
      // getReferenceAuthor d flag  s set.
       f (searchRequest nfo. sGetReferenceAuthor d() && ( sReply ||  sRet et)) {
        // t  REFERENCE_AUTHOR_ D_CSF stores t  s ce t et author  d for all ret ets
        long referenceAuthor d =
            docu ntFeatures.getFeatureValue(Earlyb rdF eldConstant.REFERENCE_AUTHOR_ D_CSF);
         f (referenceAuthor d != 0) {
           tadata.setReferencedT etAuthor d(referenceAuthor d);
        } else  f (cluster != Earlyb rdCluster.FULL_ARCH VE) {
          //   also store t  reference author  d for ret ets, d rected at t ets, and self
          // threaded t ets separately on Realt  /Protected Earlyb rds. T  data w ll be moved to
          // t  REFERENCE_AUTHOR_ D_CSF and t se f elds w ll be deprecated  n SEARCH-34958.
          referenceAuthor d = Long ntConverter.convertTwo ntToOneLong(
              ( nt) docu ntFeatures.getFeatureValue(
                  Earlyb rdF eldConstant.REFERENCE_AUTHOR_ D_MOST_S GN F CANT_ NT),
              ( nt) docu ntFeatures.getFeatureValue(
                  Earlyb rdF eldConstant.REFERENCE_AUTHOR_ D_LEAST_S GN F CANT_ NT));
           f (referenceAuthor d > 0) {
             tadata.setReferencedT etAuthor d(referenceAuthor d);
          }
        }
      }
    }
  }

  /**
   * T  d ffers from base class because   c ck aga nst num results collected  nstead of
   * num h s collected.
   */
  @Overr de
  publ c EarlyTerm nat onState  nnerShouldCollectMore() throws  OExcept on {
     f (results.s ze() >= searchRequest nfo.getNumResultsRequested()) {
      collectedEnoughResults();
       f (shouldTerm nate()) {
        return setEarlyTerm nat onState(TERM NATED_COLLECTED_ENOUGH_RESULTS);
      }
    }
    return EarlyTerm nat onState.COLLECT NG;
  }

  @Overr de
  publ c S mpleSearchResults doGetResults() {
    // Sort h s by t et  d.
    Collect ons.sort(results);
    return new S mpleSearchResults(results);
  }
}
