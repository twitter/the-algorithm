package com.tw ter.search.earlyb rd_root.cach ng;

 mport com.google.common.base.Opt onal;

 mport com.tw ter.search.common.cach ng.Cac Ut l;
 mport com.tw ter.search.common.cach ng.f lter.Cac RequestNormal zer;
 mport com.tw ter.search.common.dec der.SearchDec der;
 mport com.tw ter.search.common. tr cs.SearchCounter;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdRequest;
 mport com.tw ter.search.earlyb rd_root.common.Earlyb rdRequestContext;

publ c class RelevanceCac RequestNormal zer extends
    Cac RequestNormal zer<Earlyb rdRequestContext, Earlyb rdRequest> {
  pr vate stat c f nal SearchCounter RELEVANCE_FORCE_CACHED_LOGGED_ N_REQUEST =
      SearchCounter.export("relevance_force_cac d_logged_ n_request");

  pr vate f nal SearchDec der dec der;
  pr vate f nal Str ng relevanceStr pPersonal zat onF eldsDec derKey;

  publ c RelevanceCac RequestNormal zer(
      SearchDec der dec der,
      Str ng normal zedSearchRootNa ) {
    t .dec der = dec der;
    t .relevanceStr pPersonal zat onF eldsDec derKey =
        Str ng.format("relevance_%s_force_cac _logged_ n_requests", normal zedSearchRootNa );
  }

  @Overr de
  publ c Opt onal<Earlyb rdRequest> normal zeRequest(Earlyb rdRequestContext requestContext) {
    boolean cac Logged nRequest =
        dec der. sAva lable(relevanceStr pPersonal zat onF eldsDec derKey);

     f (cac Logged nRequest) {
      RELEVANCE_FORCE_CACHED_LOGGED_ N_REQUEST. ncre nt();
    }

    return Opt onal.fromNullable(Cac Ut l.normal zeRequestForCac (
                                     requestContext.getRequest(), cac Logged nRequest));
  }
}
