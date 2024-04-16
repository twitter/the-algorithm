package com.tw ter.search.earlyb rd_root.f lters;

 mport com.tw ter.search.common.cl entstats.RequestCounters;
 mport com.tw ter.search.common.cl entstats.RequestCountersEventL stener;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdResponse;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdResponseCode;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftSearchResults;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftTermStat st csResults;

 mport stat c com.tw ter.search.common.ut l.earlyb rd.Earlyb rdResponseUt l
    .responseCons deredFa led;


/**
 * C cks Earlyb rdResponse's response to update stats.
 */
publ c f nal class Earlyb rdSuccessfulResponseHandler
     mple nts RequestCountersEventL stener.SuccessfulResponseHandler<Earlyb rdResponse> {

  publ c stat c f nal Earlyb rdSuccessfulResponseHandler  NSTANCE =
      new Earlyb rdSuccessfulResponseHandler();

  pr vate Earlyb rdSuccessfulResponseHandler() { }

  @Overr de
  publ c vo d handleSuccessfulResponse(
      Earlyb rdResponse response,
      RequestCounters requestCounters) {

     f (response == null) {
      requestCounters. ncre ntRequestFa ledCounter();
      return;
    }

     f (response.getResponseCode() == Earlyb rdResponseCode.CL ENT_CANCEL_ERROR) {
      requestCounters. ncre ntRequestCancelCounter();
    } else  f (response.getResponseCode() == Earlyb rdResponseCode.SERVER_T MEOUT_ERROR) {
      requestCounters. ncre ntRequestT  dOutCounter();
    } else  f (responseCons deredFa led(response.getResponseCode())) {
      requestCounters. ncre ntRequestFa ledCounter();
    }

    Thr ftSearchResults results = response.getSearchResults();
     f (results != null) {
      requestCounters. ncre ntResultCounter(results.getResultsS ze());
    }

    Thr ftTermStat st csResults termStats = response.getTermStat st csResults();
     f (termStats != null) {
      requestCounters. ncre ntResultCounter(termStats.getTermResultsS ze());
    }
  }

}
