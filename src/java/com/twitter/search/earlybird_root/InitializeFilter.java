package com.tw ter.search.earlyb rd_root;

 mport com.tw ter.f nagle.Serv ce;
 mport com.tw ter.f nagle.S mpleF lter;
 mport com.tw ter.search.common.relevance.rank ng.Act onCha nManager;
 mport com.tw ter.search.common.runt  .Act onCha nDebugManager;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdRequest;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdResponse;
 mport com.tw ter.ut l.Future;
 mport com.tw ter.ut l.FutureEventL stener;

/**
 *  n  al ze request-scope state and clean t m at t  end.
 */
publ c class  n  al zeF lter extends S mpleF lter<Earlyb rdRequest, Earlyb rdResponse> {
  @Overr de
  publ c Future<Earlyb rdResponse> apply(Earlyb rdRequest request,
                                         Serv ce<Earlyb rdRequest, Earlyb rdResponse> serv ce) {
    Act onCha nDebugManager.update(new Act onCha nManager(request.getDebugMode()), "Earlyb rdRoot");
    return serv ce.apply(request).addEventL stener(new FutureEventL stener<Earlyb rdResponse>() {
      @Overr de
      publ c vo d onSuccess(Earlyb rdResponse response) {
        cleanup();
      }

      @Overr de
      publ c vo d onFa lure(Throwable cause) {
        cleanup();
      }
    });
  }

  pr vate vo d cleanup() {
    Act onCha nDebugManager.clearLocals();
  }
}
