package com.tw ter.search.earlyb rd_root. rgers;

 mport java.ut l.ArrayL st;
 mport java.ut l.L st;

 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdResponse;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdResponseCode;
 mport com.tw ter.search.earlyb rd.thr ft.T erResponse;

publ c f nal class T erResponseAccumulator extends ResponseAccumulator {
  pr vate stat c f nal Str ng TARGET_TYPE_T ER = "t er";

  pr vate f nal L st<T erResponse> t erResponses = new ArrayL st<>();
  // Total number of part  ons t  request was sent to, across all t ers.
  pr vate  nt totalPart  onsQuer ed nAllT ers = 0;
  // Among t  above part  ons, t  number of t m that returned successful responses.
  pr vate  nt totalSuccessfulPart  ons nAllT ers = 0;

  @Overr de
  publ c Str ng getNa ForLogg ng( nt response ndex,  nt numTotalResponses) {
    return TARGET_TYPE_T ER + (numTotalResponses - response ndex);
  }

  @Overr de
  publ c Str ng getNa ForEarlyb rdResponseCodeStats( nt response ndex,  nt numTotalResponses) {
    return TARGET_TYPE_T ER + (numTotalResponses - response ndex);
  }

  @Overr de
  protected boolean  s rg ngAcrossT ers() {
    return true;
  }

  @Overr de
  publ c boolean shouldEarlyTerm nate rge(EarlyTerm nateT er rgePred cate  rger) {
     f (foundError()) {
      return true;
    }

     nt numResults = 0;
    for (Earlyb rdResponse resp : getSuccessResponses()) {
       f (resp. sSetSearchResults()) {
        numResults += resp.getSearchResults().getResultsS ze();
      }
    }

    return  rger.shouldEarlyTerm nateT er rge(numResults, foundEarlyTerm nat on());
  }

  @Overr de
  publ c vo d handleSk ppedResponse(Earlyb rdResponseCode responseCode) {
    t erResponses.add(new T erResponse()
        .setNumPart  ons(0)
        .setNumSuccessfulPart  ons(0)
        .setT erResponseCode(responseCode));
  }

  @Overr de
  publ c vo d handleErrorResponse(Earlyb rdResponse response) {
    // T erResponse, wh ch  s only returned  f  rg ng results from d fferent t ers.
    T erResponse tr = new T erResponse();
     f (response != null) {
       f (response. sSetResponseCode()) {
        tr.setT erResponseCode(response.getResponseCode());
      } else {
        tr.setT erResponseCode(Earlyb rdResponseCode.TRANS ENT_ERROR);
      }
      tr.setNumPart  ons(response.getNumPart  ons());
      tr.setNumSuccessfulPart  ons(0);
      totalPart  onsQuer ed nAllT ers += response.getNumPart  ons();
    } else {
      tr.setT erResponseCode(Earlyb rdResponseCode.TRANS ENT_ERROR)
          .setNumPart  ons(0)
          .setNumSuccessfulPart  ons(0);
    }

    t erResponses.add(tr);
  }

  @Overr de
  publ c AccumulatedResponses.Part  onCounts getPart  onCounts() {
    return new AccumulatedResponses.Part  onCounts(totalPart  onsQuer ed nAllT ers,
        totalSuccessfulPart  ons nAllT ers, t erResponses);
  }

  @Overr de
  publ c vo d extraSuccessfulResponseHandler(Earlyb rdResponse response) {
    // Record t er stats.
    totalPart  onsQuer ed nAllT ers += response.getNumPart  ons();
    totalSuccessfulPart  ons nAllT ers += response.getNumSuccessfulPart  ons();

    t erResponses.add(new T erResponse()
        .setNumPart  ons(response.getNumPart  ons())
        .setNumSuccessfulPart  ons(response.getNumSuccessfulPart  ons())
        .setT erResponseCode(Earlyb rdResponseCode.SUCCESS));
  }
}
