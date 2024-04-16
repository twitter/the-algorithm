package com.tw ter.search.earlyb rd_root. rgers;

 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdResponse;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdResponseCode;


publ c f nal class Part  onResponseAccumulator extends ResponseAccumulator {
  pr vate stat c f nal Str ng TARGET_TYPE_PART T ON = "part  on";

  @Overr de
  publ c Str ng getNa ForLogg ng( nt response ndex,  nt numTotalResponses) {
    return TARGET_TYPE_PART T ON + response ndex;
  }

  @Overr de
  publ c Str ng getNa ForEarlyb rdResponseCodeStats( nt response ndex,  nt numTotalResponses) {
    //   do not need to d fferent ate bet en part  ons:   just want to get t  number of
    // responses returned by Earlyb rds, for each Earlyb rdResponseCode.
    return TARGET_TYPE_PART T ON;
  }

  @Overr de
  boolean shouldEarlyTerm nate rge(EarlyTerm nateT er rgePred cate  rger) {
    return false;
  }

  @Overr de
  publ c vo d handleSk ppedResponse(Earlyb rdResponseCode responseCode) { }

  @Overr de
  publ c vo d handleErrorResponse(Earlyb rdResponse response) {
  }

  @Overr de
  publ c AccumulatedResponses.Part  onCounts getPart  onCounts() {
    return new AccumulatedResponses.Part  onCounts(getNumResponses(),
        getSuccessResponses().s ze() + getSuccessfulEmptyResponseCount(), null);
  }

  @Overr de
  protected boolean  s rg ngAcrossT ers() {
    return false;
  }
}
