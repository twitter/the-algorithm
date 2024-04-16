package com.tw ter.search.earlyb rd_root.cach ng;

 mport com.google.common.base.Opt onal;

 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdRequest;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdResponse;

publ c class RelevanceZeroResultsCac PostProcessor extends RecencyAndRelevanceCac PostProcessor {
  @Overr de
  protected Opt onal<Earlyb rdResponse> postProcessCac Response(
      Earlyb rdRequest request, Earlyb rdResponse response, long s nce d, long max d) {
    //  f a query (from a logged  n or logged out user) returns 0 results, t n t  sa  query w ll
    // always return 0 results, for all users. So   can cac  that result.
     f (Cac CommonUt l.hasResults(response)) {
      return Opt onal.absent();
    }

    return Opt onal.of(response);
  }
}
