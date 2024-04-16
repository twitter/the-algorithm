package com.tw ter.search.earlyb rd_root.f lters;

 mport com.google.common.base.Precond  ons;
 mport com.google.common.cac .Cac Bu lder;
 mport com.google.common.cac .Cac Loader;
 mport com.google.common.cac .Load ngCac ;
 mport com.google.common.collect. mmutableMap;

 mport com.tw ter.common.ut l.Clock;
 mport com.tw ter.f nagle.Serv ce;
 mport com.tw ter.f nagle.S mpleF lter;
 mport com.tw ter.search.common.cl entstats.RequestCounters;
 mport com.tw ter.search.common.cl entstats.RequestCountersEventL stener;
 mport com.tw ter.search.common.ut l.F nagleUt l;
 mport com.tw ter.search.earlyb rd.common.Cl ent dUt l;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdResponse;
 mport com.tw ter.search.earlyb rd_root.common.Earlyb rdRequestContext;
 mport com.tw ter.search.earlyb rd_root.common.Earlyb rdRequestType;
 mport com.tw ter.ut l.Future;

publ c class RequestTypeCountF lter
    extends S mpleF lter<Earlyb rdRequestContext, Earlyb rdResponse> {
  pr vate f nal  mmutableMap<Earlyb rdRequestType, RequestCounters> typeCounters;
  pr vate f nal RequestCounters allRequestTypesCounter;
  pr vate f nal  mmutableMap<Earlyb rdRequestType, Load ngCac <Str ng, RequestCounters>>
    perTypePerCl entCounters;

  /**
   * Constructs t  f lter.
   */
  publ c RequestTypeCountF lter(f nal Str ng statSuff x) {
     mmutableMap.Bu lder<Earlyb rdRequestType, RequestCounters> perTypeBu lder =
       mmutableMap.bu lder();
    for (Earlyb rdRequestType type : Earlyb rdRequestType.values()) {
      perTypeBu lder.put(type, new RequestCounters(
          "request_type_count_f lter_" + type.getNormal zedNa () + "_" + statSuff x));
    }
    typeCounters = perTypeBu lder.bu ld();

    allRequestTypesCounter =
        new RequestCounters("request_type_count_f lter_all_" + statSuff x, true);

     mmutableMap.Bu lder<Earlyb rdRequestType, Load ngCac <Str ng, RequestCounters>>
      perTypePerCl entBu lder =  mmutableMap.bu lder();

    // No po nt  n sett ng any k nd of exp rat on pol cy for t  cac , s nce t  stats w ll
    // cont nue to be exported, so t  objects w ll not be GCed anyway.
    Cac Bu lder<Object, Object> cac Bu lder = Cac Bu lder.newBu lder();
    for (f nal Earlyb rdRequestType requestType : Earlyb rdRequestType.values()) {
      Cac Loader<Str ng, RequestCounters> cac Loader =
        new Cac Loader<Str ng, RequestCounters>() {
          @Overr de
          publ c RequestCounters load(Str ng cl ent d) {
            return new RequestCounters("request_type_count_f lter_for_" + cl ent d + "_"
                                       + requestType.getNormal zedNa () + "_" + statSuff x);
          }
        };
      perTypePerCl entBu lder.put(requestType, cac Bu lder.bu ld(cac Loader));
    }
    perTypePerCl entCounters = perTypePerCl entBu lder.bu ld();
  }

  @Overr de
  publ c Future<Earlyb rdResponse> apply(
      Earlyb rdRequestContext requestContext,
      Serv ce<Earlyb rdRequestContext, Earlyb rdResponse> serv ce) {
    Earlyb rdRequestType requestType = requestContext.getEarlyb rdRequestType();
    RequestCounters requestCounters = typeCounters.get(requestType);
    Precond  ons.c ckNotNull(requestCounters);

    // Update t  per-type and "all" counters.
    RequestCountersEventL stener<Earlyb rdResponse> requestCountersEventL stener =
        new RequestCountersEventL stener<>(
            requestCounters, Clock.SYSTEM_CLOCK, Earlyb rdSuccessfulResponseHandler. NSTANCE);
    RequestCountersEventL stener<Earlyb rdResponse> allRequestTypesEventL stener =
        new RequestCountersEventL stener<>(
            allRequestTypesCounter, Clock.SYSTEM_CLOCK,
            Earlyb rdSuccessfulResponseHandler. NSTANCE);

    RequestCountersEventL stener<Earlyb rdResponse> perTypePerCl entEventL stener =
      updatePerTypePerCl entCountersL stener(requestContext);

    return serv ce.apply(requestContext)
      .addEventL stener(requestCountersEventL stener)
      .addEventL stener(allRequestTypesEventL stener)
      .addEventL stener(perTypePerCl entEventL stener);
  }

  pr vate RequestCountersEventL stener<Earlyb rdResponse> updatePerTypePerCl entCountersL stener(
      Earlyb rdRequestContext earlyb rdRequestContext) {
    Earlyb rdRequestType requestType = earlyb rdRequestContext.getEarlyb rdRequestType();
    Load ngCac <Str ng, RequestCounters> perCl entCounters =
      perTypePerCl entCounters.get(requestType);
    Precond  ons.c ckNotNull(perCl entCounters);

    Str ng cl ent d = Cl ent dUt l.formatF nagleCl ent dAndCl ent d(
        F nagleUt l.getF nagleCl entNa (),
        Cl ent dUt l.getCl ent dFromRequest(earlyb rdRequestContext.getRequest()));
    RequestCounters cl entCounters = perCl entCounters.getUnc cked(cl ent d);
    Precond  ons.c ckNotNull(cl entCounters);

    return new RequestCountersEventL stener<>(
        cl entCounters, Clock.SYSTEM_CLOCK, Earlyb rdSuccessfulResponseHandler. NSTANCE);
  }
}
