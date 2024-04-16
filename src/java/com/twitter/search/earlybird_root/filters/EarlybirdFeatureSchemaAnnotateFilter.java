package com.tw ter.search.earlyb rd_root.f lters;

 mport java.ut l.L st;
 mport javax. nject. nject;

 mport com.tw ter.f nagle.Serv ce;
 mport com.tw ter.f nagle.S mpleF lter;
 mport com.tw ter.search.common.features.thr ft.Thr ftSearchFeatureSc maSpec f er;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdRequest;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdResponse;
 mport com.tw ter.search.earlyb rd_root.common.Earlyb rdFeatureSc ma rger;
 mport com.tw ter.search.earlyb rd_root.common.Earlyb rdRequestContext;
 mport com.tw ter.ut l.Future;

publ c class Earlyb rdFeatureSc maAnnotateF lter
    extends S mpleF lter<Earlyb rdRequestContext, Earlyb rdResponse> {

  pr vate f nal Earlyb rdFeatureSc ma rger sc ma rger;

  @ nject
  publ c Earlyb rdFeatureSc maAnnotateF lter(Earlyb rdFeatureSc ma rger  rger) {
    t .sc ma rger =  rger;
  }

  @Overr de
  publ c Future<Earlyb rdResponse> apply(
      Earlyb rdRequestContext requestContext,
      Serv ce<Earlyb rdRequestContext, Earlyb rdResponse> serv ce) {
    return serv ce.apply(annotateRequestContext(requestContext));
  }

  /**
   * Annotate t  request to  nd cate t  ava lable features sc mas before send ng to earlyb rd.
   *
   * @param requestContext t  earlyb rd request context
   */
  pr vate Earlyb rdRequestContext annotateRequestContext(Earlyb rdRequestContext requestContext) {
    Earlyb rdRequest request = requestContext.getRequest();
     f (request. sSetSearchQuery()
        && request.getSearchQuery(). sSetResult tadataOpt ons()
        && request.getSearchQuery().getResult tadataOpt ons(). sReturnSearchResultFeatures()) {
      // Re mber t  ava lable cl ent s de cac d features sc ma  n t  context and prepare to
      // reset   so th ng new.
      L st<Thr ftSearchFeatureSc maSpec f er> featureSc masAva lable nCl ent =
          request.getSearchQuery().getResult tadataOpt ons().getFeatureSc masAva lable nCl ent();

      return Earlyb rdRequestContext.newContext(
          request,
          requestContext,
          sc ma rger.getAva lableSc maL st(),  // Set t  ava lable feature sc mas based on
                                                  // what  s cac d  n t  current root.
          featureSc masAva lable nCl ent);
    } else {
      return requestContext;
    }
  }
}
