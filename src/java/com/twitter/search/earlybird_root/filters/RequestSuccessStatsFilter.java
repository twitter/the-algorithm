package com.tw ter.search.earlyb rd_root.f lters;

 mport java.ut l.concurrent.T  Un ;
 mport javax. nject. nject;

 mport com.tw ter.f nagle.Serv ce;
 mport com.tw ter.f nagle.S mpleF lter;
 mport com.tw ter.search.common.root.RequestSuccessStats;
 mport com.tw ter.search.common.ut l.F nagleUt l;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdRequest;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdResponse;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdResponseCode;
 mport com.tw ter.ut l.Future;
 mport com.tw ter.ut l.FutureEventL stener;

 mport stat c com.tw ter.search.common.ut l.earlyb rd.Earlyb rdResponseUt l.responseCons deredFa led;


/**
 * Records cancellat ons, t  outs, and fa lures for requests that do not go through
 * ScatterGat rServ ce (wh ch also updates t se stats, but for d fferent requests).
 */
publ c class RequestSuccessStatsF lter
    extends S mpleF lter<Earlyb rdRequest, Earlyb rdResponse> {

  pr vate f nal RequestSuccessStats stats;

  @ nject
  RequestSuccessStatsF lter(RequestSuccessStats stats) {
    t .stats = stats;
  }


  @Overr de
  publ c Future<Earlyb rdResponse> apply(
      Earlyb rdRequest request,
      Serv ce<Earlyb rdRequest, Earlyb rdResponse> serv ce) {

    f nal long startT   = System.nanoT  ();

    return serv ce.apply(request).addEventL stener(
        new FutureEventL stener<Earlyb rdResponse>() {
          @Overr de
          publ c vo d onSuccess(Earlyb rdResponse response) {
            boolean success = true;

             f (response.getResponseCode() == Earlyb rdResponseCode.CL ENT_CANCEL_ERROR) {
              success = false;
              stats.getCancelledRequestCount(). ncre nt();
            } else  f (response.getResponseCode() == Earlyb rdResponseCode.SERVER_T MEOUT_ERROR) {
              success = false;
              stats.getT  doutRequestCount(). ncre nt();
            } else  f (responseCons deredFa led(response.getResponseCode())) {
              success = false;
              stats.getErroredRequestCount(). ncre nt();
            }

            long latencyNanos = System.nanoT  () - startT  ;
            stats.getRequestLatencyStats().requestComplete(
                T  Un .NANOSECONDS.toM ll s(latencyNanos), 0, success);
          }

          @Overr de
          publ c vo d onFa lure(Throwable cause) {
            long latencyNanos = System.nanoT  () - startT  ;
            stats.getRequestLatencyStats().requestComplete(
                T  Un .NANOSECONDS.toM ll s(latencyNanos), 0, false);

             f (F nagleUt l. sCancelExcept on(cause)) {
              stats.getCancelledRequestCount(). ncre nt();
            } else  f (F nagleUt l. sT  outExcept on(cause)) {
              stats.getT  doutRequestCount(). ncre nt();
            } else {
              stats.getErroredRequestCount(). ncre nt();
            }
          }
        });
  }
}
