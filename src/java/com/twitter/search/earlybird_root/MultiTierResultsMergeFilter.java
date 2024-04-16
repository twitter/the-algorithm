package com.tw ter.search.earlyb rd_root;

 mport java.ut l.L st;

 mport javax. nject. nject;

 mport com.tw ter.f nagle.F lter;
 mport com.tw ter.f nagle.Serv ce;
 mport com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdCluster;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdResponse;
 mport com.tw ter.search.earlyb rd_root.common.Earlyb rdFeatureSc ma rger;
 mport com.tw ter.search.earlyb rd_root.common.Earlyb rdRequestContext;
 mport com.tw ter.search.earlyb rd_root. rgers.Earlyb rdResponse rger;
 mport com.tw ter.search.earlyb rd_root. rgers.T erResponseAccumulator;
 mport com.tw ter.ut l.Funct on;
 mport com.tw ter.ut l.Future;

/**
 * F lter used to  rge results from mult ple t ers
 */
publ c class Mult T erResults rgeF lter extends
    F lter<Earlyb rdRequestContext, Earlyb rdResponse,
        Earlyb rdRequestContext, L st<Future<Earlyb rdResponse>>> {

  pr vate f nal Earlyb rdFeatureSc ma rger featureSc ma rger;

  @ nject
  publ c Mult T erResults rgeF lter(Earlyb rdFeatureSc ma rger featureSc ma rger) {
    t .featureSc ma rger = featureSc ma rger;
  }

  @Overr de
  publ c Future<Earlyb rdResponse> apply(
      f nal Earlyb rdRequestContext request,
      Serv ce<Earlyb rdRequestContext, L st<Future<Earlyb rdResponse>>> serv ce) {
    return serv ce.apply(request).flatMap(Funct on.func(responses ->  rge(request, responses)));
  }

  pr vate Future<Earlyb rdResponse>  rge(
      Earlyb rdRequestContext requestContext,
      L st<Future<Earlyb rdResponse>> responses) {

    // For mult -t er response  rg ng, t  number of part  ons do not have  an ng because
    // t  response  s not un formly part  oned anymore.    pass  nteger.MAX_VALUE for stats
    // count ng purpose.
    Earlyb rdResponse rger  rger = Earlyb rdResponse rger.getResponse rger(
        requestContext,
        responses,
        new T erResponseAccumulator(),
        Earlyb rdCluster.FULL_ARCH VE,
        featureSc ma rger,
         nteger.MAX_VALUE);
    return  rger. rge();
  }
}
