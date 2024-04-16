package com.tw ter.search.earlyb rd_root.f lters;

 mport java.ut l.Map;

 mport com.google.common.collect.Maps;

 mport com.tw ter.f nagle.Serv ce;
 mport com.tw ter.f nagle.S mpleF lter;
 mport com.tw ter.search.common. tr cs.SearchCounter;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdRequest;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdResponse;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdResponseCode;
 mport com.tw ter.ut l.Future;
 mport com.tw ter.ut l.FutureEventL stener;

publ c class ResponseCodeStatF lter
    extends S mpleF lter<Earlyb rdRequest, Earlyb rdResponse> {

  pr vate f nal Map<Earlyb rdResponseCode, SearchCounter> responseCodeCounters;

  /**
   * Create ResponseCodeStatF lter
   */
  publ c ResponseCodeStatF lter() {
    responseCodeCounters = Maps.newEnumMap(Earlyb rdResponseCode.class);
    for (Earlyb rdResponseCode code : Earlyb rdResponseCode.values()) {
      SearchCounter stat = SearchCounter.export("response_code_" + code.na ().toLo rCase());
      responseCodeCounters.put(code, stat);
    }
  }

  @Overr de
  publ c Future<Earlyb rdResponse> apply(
      f nal Earlyb rdRequest request,
      f nal Serv ce<Earlyb rdRequest, Earlyb rdResponse> serv ce) {

    return serv ce.apply(request).addEventL stener(
        new FutureEventL stener<Earlyb rdResponse>() {

          @Overr de
          publ c vo d onSuccess(f nal Earlyb rdResponse response) {
            responseCodeCounters.get(response.getResponseCode()). ncre nt();
          }

          @Overr de
          publ c vo d onFa lure(f nal Throwable cause) { }
        });

  }
}
