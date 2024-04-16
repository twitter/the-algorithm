package com.tw ter.search.earlyb rd_root.collectors;

 mport java.ut l.Comparator;
 mport java.ut l.L st;

 mport com.tw ter.search.common.relevance.ut ls.ResultComparators;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdResponse;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftSearchResult;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftSearchResults;

/**
 * {@l nk Recency rgeCollector}  n r s {@l nk Mult way rgeCollector} for t  type
 * {@l nk com.tw ter.search.earlyb rd.thr ft.Thr ftSearchResult} as t  result type.
 * <p/>
 *   also  mple nts two publ c  thods to retr eve t  top-k or all results.
 */
publ c class Recency rgeCollector extends Mult way rgeCollector<Thr ftSearchResult> {

  // Conta ner for t  f nal results array and also stats l ke numH sProcessed etc...
  protected f nal Thr ftSearchResults f nalResults = new Thr ftSearchResults();

  publ c Recency rgeCollector( nt numResponses) {
    t (numResponses, ResultComparators. D_COMPARATOR);
  }

  protected Recency rgeCollector( nt numResponses, Comparator<Thr ftSearchResult> comparator) {
    super(numResponses, comparator);
  }

  @Overr de
  protected vo d collectStats(Earlyb rdResponse response) {
    super.collectStats(response);

    Thr ftSearchResults searchResults = response.getSearchResults();
     f (searchResults. sSetNumH sProcessed()) {
      f nalResults.setNumH sProcessed(
          f nalResults.getNumH sProcessed() + searchResults.getNumH sProcessed());
    }
     f (searchResults. sSetNumPart  onsEarlyTerm nated()) {
      f nalResults.setNumPart  onsEarlyTerm nated(
              f nalResults.getNumPart  onsEarlyTerm nated()
                      + searchResults.getNumPart  onsEarlyTerm nated());
    }
  }

  @Overr de
  protected f nal L st<Thr ftSearchResult> collectResults(Earlyb rdResponse response) {
     f (response != null
        && response. sSetSearchResults()
        && response.getSearchResults().getResultsS ze() > 0) {
      return response.getSearchResults().getResults();
    } else {
      return null;
    }
  }

  /**
   * Gets all t  results that has been collected.
   *
   * @return {@l nk Thr ftSearchResults} conta n ng a l st of results sorted by prov ded
   *         comparator  n descend ng order.
   */
  publ c f nal Thr ftSearchResults getAllSearchResults() {
    return f nalResults.setResults(getResultsL st());
  }

  @Overr de
  protected f nal boolean  sResponseVal d(Earlyb rdResponse response) {
     f (response == null || !response. sSetSearchResults()) {
      LOG.warn("searchResults was null: " + response);
      return false;
    }
    return true;
  }
}
