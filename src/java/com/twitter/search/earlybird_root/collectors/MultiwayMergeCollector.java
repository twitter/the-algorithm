package com.tw ter.search.earlyb rd_root.collectors;

 mport java.ut l.Collect ons;
 mport java.ut l.Comparator;
 mport java.ut l.L st;

 mport com.google.common.base.Precond  ons;
 mport com.google.common.collect.L sts;

 mport org.slf4j.Logger;
 mport org.slf4j.LoggerFactory;

 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdResponse;

/**
 * Gener c Mult way rgeCollector class for do ng k-way  rge of earlyb rd responses
 * that takes a comparator and returns a l st of results sorted by t  comparator.
 */
publ c abstract class Mult way rgeCollector<T> {
  protected stat c f nal Logger LOG = LoggerFactory.getLogger(Mult way rgeCollector.class);

  pr vate f nal Comparator<T> resultComparator;
  pr vate f nal  nt numResponsesTo rge;
  pr vate f nal L st<T> results = L sts.newArrayL st();
  pr vate  nt numResponsesAdded = 0;

  /**
   * Constructor that does mult  way  rge and takes  n a custom pred cate search result f lter.
   */
  publ c Mult way rgeCollector( nt numResponses,
                                Comparator<T> comparator) {
    Precond  ons.c ckNotNull(comparator);
    t .resultComparator = comparator;
    t .numResponsesTo rge = numResponses;
  }

  /**
   * Add a s ngle response from one part  on, updates stats.
   *
   * @param response response from one part  on
   */
  publ c f nal vo d addResponse(Earlyb rdResponse response) {
    // On prod, does   ever happen   rece ve more responses than numPart  ons ?
    Precond  ons.c ckArgu nt(numResponsesAdded++ < numResponsesTo rge,
        Str ng.format("Attempt ng to  rge more than %d responses", numResponsesTo rge));
     f (! sResponseVal d(response)) {
      return;
    }
    collectStats(response);
    L st<T> resultsFromResponse = collectResults(response);
     f (resultsFromResponse != null && resultsFromResponse.s ze() > 0) {
      results.addAll(resultsFromResponse);
    }
  }

  /**
   * Parse t  Earlyb rdResponse and retr eve l st of results to be appended.
   *
   * @param response earlyb rd response from w re results are extracted
   * @return  resultsL st to be appended
   */
  protected abstract L st<T> collectResults(Earlyb rdResponse response);

  /**
   *    s recom nded that sub-class overr des t  funct on to add custom log c to
   * collect more stat and call t  base funct on.
   */
  protected vo d collectStats(Earlyb rdResponse response) {
  }

  /**
   * Get full l st of results, after addResponse calls have been  nvoked.
   *
   * @return l st of results extracted from all Earlyb rdResponses that have been collected so far
   */
  protected f nal L st<T> getResultsL st() {
    Collect ons.sort(results, resultComparator);
    return results;
  }

  protected abstract boolean  sResponseVal d(Earlyb rdResponse response);
}
