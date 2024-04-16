package com.tw ter.search.earlyb rd_root.f lters;

 mport com.tw ter.f nagle.Serv ce;
 mport com.tw ter.f nagle.S mpleF lter;
 mport com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdCluster;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdResponse;
 mport com.tw ter.search.earlyb rd_root.common.Earlyb rdRequestContext;
 mport com.tw ter.ut l.Future;

/** A per-serv ce f lter for handl ng except ons. */
publ c class Serv ceExcept onHandl ngF lter
    extends S mpleF lter<Earlyb rdRequestContext, Earlyb rdResponse> {
  pr vate f nal Earlyb rdResponseExcept onHandler except onHandler;

  /** Creates a new Serv ceExcept onHandl ngF lter  nstance. */
  publ c Serv ceExcept onHandl ngF lter(Earlyb rdCluster cluster) {
    t .except onHandler = new Earlyb rdResponseExcept onHandler(cluster.getNa ForStats());
  }

  @Overr de
  publ c Future<Earlyb rdResponse> apply(
      Earlyb rdRequestContext requestContext,
      Serv ce<Earlyb rdRequestContext, Earlyb rdResponse> serv ce) {
    return except onHandler.handleExcept on(
        requestContext.getRequest(), serv ce.apply(requestContext));
  }
}
