package com.tw ter.search.earlyb rd_root.f lters;

 mport com.tw ter.f nagle.Serv ce;
 mport com.tw ter.f nagle.S mpleF lter;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdRequest;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdResponse;
 mport com.tw ter.ut l.Future;

/** A top level f lter for handl ng except ons. */
publ c class TopLevelExcept onHandl ngF lter
    extends S mpleF lter<Earlyb rdRequest, Earlyb rdResponse> {
  pr vate f nal Earlyb rdResponseExcept onHandler except onHandler;

  /** Creates a new TopLevelExcept onHandl ngF lter  nstance. */
  publ c TopLevelExcept onHandl ngF lter() {
    t .except onHandler = new Earlyb rdResponseExcept onHandler("top_level");
  }

  @Overr de
  publ c Future<Earlyb rdResponse> apply(Earlyb rdRequest request,
                                         Serv ce<Earlyb rdRequest, Earlyb rdResponse> serv ce) {
    return except onHandler.handleExcept on(request, serv ce.apply(request));
  }
}
