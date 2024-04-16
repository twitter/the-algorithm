package com.tw ter.search.feature_update_serv ce.f lters;

 mport com.google. nject. nject;
 mport com.google. nject.S ngleton;

 mport com.tw ter.f nagle.Serv ce;
 mport com.tw ter.f natra.thr ft.AbstractThr ftF lter;
 mport com.tw ter.f natra.thr ft.Thr ftRequest;
 mport com.tw ter. nject.annotat ons.Flag;
 mport com.tw ter.search.common. tr cs.SearchRateCounter;
 mport com.tw ter.search.feature_update_serv ce.thr ftjava.FeatureUpdateResponse;
 mport com.tw ter.search.feature_update_serv ce.thr ftjava.FeatureUpdateResponseCode;
 mport com.tw ter.search.feature_update_serv ce.wh el st.Cl ent dWh el st;
 mport com.tw ter.ut l.Future;

@S ngleton
publ c class Cl ent dWh el stF lter extends AbstractThr ftF lter {
  pr vate f nal boolean enabled;
  pr vate f nal Cl ent dWh el st wh el st;

  pr vate f nal SearchRateCounter unknownCl ent dStat =
      SearchRateCounter.export("unknown_cl ent_ d");
  pr vate f nal SearchRateCounter noCl ent dStat =
      SearchRateCounter.export("no_cl ent_ d");

  @ nject
  publ c Cl ent dWh el stF lter(
      Cl ent dWh el st wh el st,
      @Flag("cl ent.wh el st.enable") Boolean enabled
  ) {
    t .wh el st = wh el st;
    t .enabled = enabled;
  }

  @Overr de
  @SuppressWarn ngs("unc cked")
  publ c <T, R> Future<R> apply(Thr ftRequest<T> request, Serv ce<Thr ftRequest<T>, R> svc) {
     f (!enabled) {
      return svc.apply(request);
    }
     f (request.cl ent d(). sEmpty()) {
      noCl ent dStat. ncre nt();
      return (Future<R>) Future.value(
          new FeatureUpdateResponse(FeatureUpdateResponseCode.M SS NG_CL ENT_ERROR)
              .setDeta l ssage("f nagle cl ent d  s requ red  n request"));

    } else  f (!wh el st. sCl entAllo d(request.cl ent d().get())) {
      //  's safe to use get()  n t  above cond  on because
      // cl ent d was already c cked for empt ness
      unknownCl ent dStat. ncre nt();
      return (Future<R>) Future.value(
          new FeatureUpdateResponse(FeatureUpdateResponseCode.UNKNOWN_CL ENT_ERROR)
              .setDeta l ssage(Str ng.format(
                  "request conta ns unknown f nagle cl ent d: %s", request.cl ent d().toStr ng())));
    } else {
      return svc.apply(request);
    }
  }
}

