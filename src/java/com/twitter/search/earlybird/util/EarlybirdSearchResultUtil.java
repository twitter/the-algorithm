package com.tw ter.search.earlyb rd.ut l;

 mport java.ut l.L st;
 mport java.ut l.Map;
 mport java.ut l.Set;

 mport javax.annotat on.Nullable;

 mport com.google.common.collect. mmutableMap;

 mport com.tw ter.search.common.constants.thr ftjava.Thr ftLanguage;
 mport com.tw ter.search.common.database.DatabaseConf g;
 mport com.tw ter.search.common.query.thr ftjava.EarlyTerm nat on nfo;
 mport com.tw ter.search.common.ut l.earlyb rd.ResultsUt l;
 mport com.tw ter.search.common.ut l.earlyb rd.Thr ftSearchResultUt l;
 mport com.tw ter.search.common.ut l.earlyb rd.Thr ftSearchResultsRelevanceStatsUt l;
 mport com.tw ter.search.core.earlyb rd.facets.Language togram;
 mport com.tw ter.search.earlyb rd.part  on.Part  onConf g;
 mport com.tw ter.search.earlyb rd.search.H ;
 mport com.tw ter.search.earlyb rd.search.SearchResults nfo;
 mport com.tw ter.search.earlyb rd.search.S mpleSearchResults;
 mport com.tw ter.search.earlyb rd.search.relevance.RelevanceSearchResults;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftSearchResult;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftSearchResultDebug nfo;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftSearchResult tadata;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftSearchResults;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftSearchResultsRelevanceStats;

// Earlyb rdSearchResultUt l conta ns so  s mple stat c  thods for construct ng
// Thr ftSearchResult objects.
publ c f nal class Earlyb rdSearchResultUt l {
  publ c stat c f nal double M N_LANGUAGE_RAT O_TO_KEEP = 0.002;

  pr vate Earlyb rdSearchResultUt l() { }

  /**
   * Update result stats on t  Thr ftSearchResult.
   */
  publ c stat c vo d setResultStat st cs(Thr ftSearchResults results, SearchResults nfo  nfo) {
    results.setNumH sProcessed( nfo.getNumH sProcessed());
    results.setNumPart  onsEarlyTerm nated( nfo. sEarlyTerm nated() ? 1 : 0);
     f ( nfo. sSetSearc dStatus Ds()) {
      results.setMaxSearc dStatus D( nfo.getMaxSearc dStatus D());
      results.setM nSearc dStatus D( nfo.getM nSearc dStatus D());
    }

     f ( nfo. sSetSearc dT  s()) {
      results.setMaxSearc dT  S nceEpoch( nfo.getMaxSearc dT  ());
      results.setM nSearc dT  S nceEpoch( nfo.getM nSearc dT  ());
    }
  }

  /**
   * Create an EarlyTerm nat on nfo based on  nformat on  ns de a SearchResults nfo.
   */
  publ c stat c EarlyTerm nat on nfo prepareEarlyTerm nat on nfo(SearchResults nfo  nfo) {
    EarlyTerm nat on nfo earlyTerm nat on nfo = new EarlyTerm nat on nfo( nfo. sEarlyTerm nated());
     f ( nfo. sEarlyTerm nated()) {
      earlyTerm nat on nfo.setEarlyTerm nat onReason( nfo.getEarlyTerm nat onReason());
    }
    return earlyTerm nat on nfo;
  }

  /**
   * Populate language  togram  ns de Thr ftSerachResults.
   */
  publ c stat c vo d setLanguage togram(Thr ftSearchResults results,
                                          Language togram language togram) {
     nt sum = 0;
    for ( nt value : language togram.getLanguage togram()) {
      sum += value;
    }
     f (sum == 0) {
      return;
    }
     mmutableMap.Bu lder<Thr ftLanguage,  nteger> bu lder =  mmutableMap.bu lder();
     nt threshold = ( nt) (sum * M N_LANGUAGE_RAT O_TO_KEEP);
    for (Map.Entry<Thr ftLanguage,  nteger> entry : language togram.getLanguage togramAsMap()
                                                                     .entrySet()) {
       f (entry.getValue() > threshold) {
        bu lder.put(entry.getKey(), entry.getValue());
      }
    }
    Map<Thr ftLanguage,  nteger> langCounts = bu lder.bu ld();
     f (langCounts.s ze() > 0) {
      results.setLanguage togram(langCounts);
    }
  }

  pr vate stat c vo d addDebug nfoToResults(L st<Thr ftSearchResult> resultArray,
                                            @Nullable Part  onConf g part  onConf g) {
     f (part  onConf g == null) {
      return;
    }
    Thr ftSearchResultDebug nfo debug nfo = new Thr ftSearchResultDebug nfo();
    debug nfo.setHostna (DatabaseConf g.getLocalHostna ());
    // T se  nfo can also co  from Earlyb rdServer.get().getPart  onConf g()  f   add such a
    // getter for part  onConf g().
    debug nfo.setPart  on d(part  onConf g.get ndex ngHashPart  on D());
    debug nfo.setT erna (part  onConf g.getT erNa ());
    debug nfo.setClusterNa (part  onConf g.getClusterNa ());

    for (Thr ftSearchResult result : resultArray) {
      result.setDebug nfo(debug nfo);
    }
  }

  /**
   * Wr e results  nto t  result array.
   * @param resultArray t  result array to wr e  nto.
   * @param h s t  h s from t  search.
   * @param part  onConf g part  on conf g used to f ll  n debug  nfo. Pass  n null  f no debug
   *  nfo should be wr ten  nto results.
   */
  publ c stat c vo d prepareResultsArray(L st<Thr ftSearchResult> resultArray,
                                         S mpleSearchResults h s,
                                         @Nullable Part  onConf g part  onConf g) {
    for ( nt   = 0;   < h s.numH s();  ++) {
      f nal H  h  = h s.getH ( );
      f nal long  d = h .getStatus D();
      f nal Thr ftSearchResult result = new Thr ftSearchResult( d);
      f nal Thr ftSearchResult tadata result tadata = h .get tadata();
      result.set tadata(result tadata);
      resultArray.add(result);
    }
    addDebug nfoToResults(resultArray, part  onConf g);
  }

  /**
   * Wr e results  nto t  result array.
   * @param resultArray t  result array to wr e  nto.
   * @param h s t  h s from t  search.
   * @param user DWh el st Used to set flag Thr ftSearchResult tadata.dontF lterUser.
   * @param part  onConf g part  on conf g used to f ll  n debug  nfo. Pass  n null  f no debug
   *  nfo should be wr ten  nto results.
   */
  publ c stat c vo d prepareRelevanceResultsArray(L st<Thr ftSearchResult> resultArray,
                                                  RelevanceSearchResults h s,
                                                  Set<Long> user DWh el st,
                                                  @Nullable Part  onConf g part  onConf g) {
    for ( nt   = 0;   < h s.numH s();  ++) {
      f nal long  d = h s.getH ( ).getStatus D();
      f nal Thr ftSearchResult result = new Thr ftSearchResult( d);
      f nal Thr ftSearchResult tadata result tadata = h s.result tadata[ ];
      result.set tadata(result tadata);
       f (user DWh el st != null) {
        result tadata.setDontF lterUser(user DWh el st.conta ns(result tadata.getFromUser d()));
      }

      resultArray.add(result);
    }
    addDebug nfoToResults(resultArray, part  onConf g);
  }

  /**
   *  rge a L st of Thr ftSearchResults  nto a s ngle Thr ftSearchResults object.
   */
  publ c stat c Thr ftSearchResults  rgeSearchResults(L st<Thr ftSearchResults> allSearchResults) {
    Thr ftSearchResults  rgedResults = new Thr ftSearchResults();
     rgedResults.setRelevanceStats(new Thr ftSearchResultsRelevanceStats());

     rgedResults.setH Counts(ResultsUt l.aggregateCountMap(allSearchResults,
        Thr ftSearchResultUt l.H T_COUNTS_MAP_GETTER));

     rgedResults.setLanguage togram(ResultsUt l.aggregateCountMap(allSearchResults,
        Thr ftSearchResultUt l.LANG_MAP_GETTER));

    for (Thr ftSearchResults searchResults : allSearchResults) {
      // Add results
       rgedResults.getResults().addAll(searchResults.getResults());
      // Update counts
      Thr ftSearchResultUt l. ncre ntCounts( rgedResults, searchResults);
      // Update relevance stats
       f (searchResults.getRelevanceStats() != null) {
        Thr ftSearchResultsRelevanceStatsUt l.addRelevanceStats( rgedResults.getRelevanceStats(),
            searchResults.getRelevanceStats());
      }
    }

    return  rgedResults;
  }
}
