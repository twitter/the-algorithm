package com.tw ter.search.earlyb rd_root;

 mport java.ut l.L st;

 mport javax. nject. nject;

 mport com.google.common.collect.L sts;

 mport org.slf4j.Logger;
 mport org.slf4j.LoggerFactory;

 mport com.tw ter.f nagle.Serv ce;
 mport com.tw ter.search.common.root.Part  onLogg ngSupport;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdResponse;
 mport com.tw ter.search.earlyb rd_root.common.Earlyb rdRequestContext;
 mport com.tw ter.ut l.Future;

/**
 * A cha n of scatter gat r serv ces.
 * Regular roots use ScatterGat rServ ce d rectly. T  class  s only used by mult -t er roots.
 */
publ c class Earlyb rdCha nedScatterGat rServ ce extends
    Serv ce<Earlyb rdRequestContext, L st<Future<Earlyb rdResponse>>> {

  pr vate stat c f nal Logger LOG =
    LoggerFactory.getLogger(Earlyb rdCha nedScatterGat rServ ce.class);

  pr vate f nal L st<Serv ce<Earlyb rdRequestContext, Earlyb rdResponse>> serv ceCha n;

  /**
   * Construct a ScatterGat rServ ceCha n, by load ng conf gurat ons from earlyb rd-t ers.yml.
   */
  @ nject
  publ c Earlyb rdCha nedScatterGat rServ ce(
      Earlyb rdServ ceCha nBu lder serv ceCha nBu lder,
      Earlyb rdServ ceScatterGat rSupport scatterGat rSupport,
      Part  onLogg ngSupport<Earlyb rdRequestContext> part  onLogg ngSupport) {

    serv ceCha n =
        serv ceCha nBu lder.bu ldServ ceCha n(scatterGat rSupport, part  onLogg ngSupport);

     f (serv ceCha n. sEmpty()) {
      LOG.error("At least one t er has to be enabled.");
      throw new Runt  Except on("Root does not work w h all t ers d sabled.");
    }
  }

  @Overr de
  publ c Future<L st<Future<Earlyb rdResponse>>> apply(Earlyb rdRequestContext requestContext) {
    // H  all t ers  n parallel.
    L st<Future<Earlyb rdResponse>> resultL st =
        L sts.newArrayL stW hCapac y(serv ceCha n.s ze());
    for (f nal Serv ce<Earlyb rdRequestContext, Earlyb rdResponse> serv ce : serv ceCha n) {
      resultL st.add(serv ce.apply(requestContext));
    }
    return Future.value(resultL st);
  }
}
