package com.tw ter.search.earlyb rd.stats;

 mport java.ut l.concurrent.T  Un ;

 mport com.tw ter.search.common. tr cs.SearchCounter;
 mport com.tw ter.search.common. tr cs.SearchRateCounter;
 mport com.tw ter.search.common. tr cs.SearchRequestStats;

/**
 * SearchRequestStats w h earlyb rd-spec f c add  onal stats.
 */
publ c f nal class Earlyb rdRPCStats {
  pr vate f nal SearchRequestStats requestStats;
  // Number of quer es that  re term nated early.
  pr vate f nal SearchCounter earlyTerm natedRequests;

  //   do not count cl ent error  n t  response error rate, but track   separately.
  pr vate f nal SearchRateCounter responseCl entErrors;

  publ c Earlyb rdRPCStats(Str ng na ) {
    requestStats = SearchRequestStats.export(na , T  Un .M CROSECONDS, true, true);
    earlyTerm natedRequests = SearchCounter.export(na  + "_early_term nated");
    responseCl entErrors = SearchRateCounter.export(na  + "_cl ent_error");
  }

  publ c long getRequestRate() {
    return (long) (double) requestStats.getRequestRate().read();
  }

  publ c long getAverageLatency() {
    return (long) (double) requestStats.getT  rStats().read();
  }

  /**
   * Records a completed earlyb rd request.
   * @param latencyUs how long t  request took to complete,  n m croseconds.
   * @param resultsCount how many results  re returned.
   * @param success w t r t  request was successful or not.
   * @param earlyTerm nated w t r t  request term nated early or not.
   * @param cl entError w t r t  request fa lure  s caused by cl ent errors
   */
  publ c vo d requestComplete(long latencyUs, long resultsCount, boolean success,
                              boolean earlyTerm nated, boolean cl entError) {
    //   treat cl ent errors as successes for top-l ne  tr cs to prevent bad cl ent requests (l ke
    // malfor d quer es) from dropp ng   success rate and generat ng alerts.
    requestStats.requestComplete(latencyUs, resultsCount, success || cl entError);

     f (earlyTerm nated) {
      earlyTerm natedRequests. ncre nt();
    }
     f (cl entError) {
      responseCl entErrors. ncre nt();
    }
  }
}
