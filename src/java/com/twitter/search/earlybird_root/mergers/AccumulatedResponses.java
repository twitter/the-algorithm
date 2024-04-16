package com.tw ter.search.earlyb rd_root. rgers;

 mport java.ut l.L st;
 mport java.ut l.Map;

 mport com.google.common.base.Precond  ons;
 mport com.google.common.collect.Maps;

 mport com.tw ter.search.common.query.thr ftjava.EarlyTerm nat on nfo;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdResponse;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdResponseCode;
 mport com.tw ter.search.earlyb rd.thr ft.T erResponse;

/**
 * Collect on of Earlyb rdResponses and assoc ated stats to be  rged.
 */
publ c class AccumulatedResponses {
  // T  l st of t  successful responses from all earlyb rd futures. T  does not  nclude empty
  // responses resulted from null requests.
  pr vate f nal L st<Earlyb rdResponse> successResponses;
  // T  l st of t  unsuccessful responses from all earlyb rd futures.
  pr vate f nal L st<Earlyb rdResponse> errorResponses;
  // t  l st of max status ds seen  n each earlyb rd.
  pr vate f nal L st<Long> max ds;
  // t  l st of m n status ds seen  n each earlyb rd.
  pr vate f nal L st<Long> m n ds;

  pr vate f nal EarlyTerm nat on nfo  rgedEarlyTerm nat on nfo;
  pr vate f nal boolean  s rg ngAcrossT ers;
  pr vate f nal Part  onCounts part  onCounts;
  pr vate f nal  nt numSearc dSeg nts;

  publ c stat c f nal class Part  onCounts {
    pr vate f nal  nt numPart  ons;
    pr vate f nal  nt numSuccessfulPart  ons;
    pr vate f nal L st<T erResponse> perT erResponse;

    publ c Part  onCounts( nt numPart  ons,  nt numSuccessfulPart  ons, L st<T erResponse>
        perT erResponse) {
      t .numPart  ons = numPart  ons;
      t .numSuccessfulPart  ons = numSuccessfulPart  ons;
      t .perT erResponse = perT erResponse;
    }

    publ c  nt getNumPart  ons() {
      return numPart  ons;
    }

    publ c  nt getNumSuccessfulPart  ons() {
      return numSuccessfulPart  ons;
    }

    publ c L st<T erResponse> getPerT erResponse() {
      return perT erResponse;
    }
  }

  /**
   * Create AccumulatedResponses
   */
  publ c AccumulatedResponses(L st<Earlyb rdResponse> successResponses,
                              L st<Earlyb rdResponse> errorResponses,
                              L st<Long> max ds,
                              L st<Long> m n ds,
                              EarlyTerm nat on nfo  rgedEarlyTerm nat on nfo,
                              boolean  s rg ngAcrossT ers,
                              Part  onCounts part  onCounts,
                               nt numSearc dSeg nts) {
    t .successResponses = successResponses;
    t .errorResponses = errorResponses;
    t .max ds = max ds;
    t .m n ds = m n ds;
    t . rgedEarlyTerm nat on nfo =  rgedEarlyTerm nat on nfo;
    t . s rg ngAcrossT ers =  s rg ngAcrossT ers;
    t .part  onCounts = part  onCounts;
    t .numSearc dSeg nts = numSearc dSeg nts;
  }

  publ c L st<Earlyb rdResponse> getSuccessResponses() {
    return successResponses;
  }

  publ c L st<Earlyb rdResponse> getErrorResponses() {
    return errorResponses;
  }

  publ c L st<Long> getMax ds() {
    return max ds;
  }

  publ c L st<Long> getM n ds() {
    return m n ds;
  }

  publ c EarlyTerm nat on nfo get rgedEarlyTerm nat on nfo() {
    return  rgedEarlyTerm nat on nfo;
  }

  publ c boolean foundError() {
    return !errorResponses. sEmpty();
  }

  /**
   * Tr es to return a  rged Earlyb rdResponse that propagates as much  nformat on from t  error
   * responses as poss ble.
   *
   *  f all error responses have t  sa  error response code, t   rged response w ll have t 
   * sa  error response code, and t  debugStr ng/debug nfo on t   rged response w ll be set to
   * t  debugStr ng/debug nfo of one of t   rged responses.
   *
   *  f t  error responses have at least 2 d fferent response codes, TRANS ENT_ERROR w ll be set
   * on t   rged response. Also,   w ll look for t  most common error response code, and w ll
   * propagate t  debugStr ng/debug nfo from an error response w h that response code.
   */
  publ c Earlyb rdResponse get rgedErrorResponse() {
    Precond  ons.c ckState(!errorResponses. sEmpty());

    // F nd a response that has t  most common error response code.
     nt maxCount = 0;
    Earlyb rdResponse errorResponseW hMostCommonErrorResponseCode = null;
    Map<Earlyb rdResponseCode,  nteger> responseCodeCounts = Maps.newHashMap();
    for (Earlyb rdResponse errorResponse : errorResponses) {
      Earlyb rdResponseCode responseCode = errorResponse.getResponseCode();
       nteger responseCodeCount = responseCodeCounts.get(responseCode);
       f (responseCodeCount == null) {
        responseCodeCount = 0;
      }
      ++responseCodeCount;
      responseCodeCounts.put(responseCode, responseCodeCount);
       f (responseCodeCount > maxCount) {
        errorResponseW hMostCommonErrorResponseCode = errorResponse;
      }
    }

    //  f all error responses have t  sa  response code, set   on t   rged response.
    // Ot rw se, set TRANS ENT_ERROR on t   rged response.
    Earlyb rdResponseCode  rgedResponseCode = Earlyb rdResponseCode.TRANS ENT_ERROR;
     f (responseCodeCounts.s ze() == 1) {
       rgedResponseCode = responseCodeCounts.keySet(). erator().next();
    }

    Earlyb rdResponse  rgedResponse = new Earlyb rdResponse()
        .setResponseCode( rgedResponseCode);

    // Propagate t  debugStr ng/debug nfo of t  selected error response to t   rged response.
    Precond  ons.c ckNotNull(errorResponseW hMostCommonErrorResponseCode);
     f (errorResponseW hMostCommonErrorResponseCode. sSetDebugStr ng()) {
       rgedResponse.setDebugStr ng(errorResponseW hMostCommonErrorResponseCode.getDebugStr ng());
    }
     f (errorResponseW hMostCommonErrorResponseCode. sSetDebug nfo()) {
       rgedResponse.setDebug nfo(errorResponseW hMostCommonErrorResponseCode.getDebug nfo());
    }

    // Set t  numPart  ons and numPart  onsSucceeded on t   rgedResponse
     rgedResponse.setNumPart  ons(part  onCounts.getNumPart  ons());
     rgedResponse.setNumSuccessfulPart  ons(part  onCounts.getNumSuccessfulPart  ons());

    return  rgedResponse;
  }

  publ c boolean  s rg ngAcrossT ers() {
    return  s rg ngAcrossT ers;
  }

  publ c boolean  s rg ngPart  onsW h nAT er() {
    return ! s rg ngAcrossT ers;
  }

  publ c Part  onCounts getPart  onCounts() {
    return part  onCounts;
  }

  publ c  nt getNumSearc dSeg nts() {
    return numSearc dSeg nts;
  }
}
