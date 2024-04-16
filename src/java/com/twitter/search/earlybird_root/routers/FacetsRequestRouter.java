package com.tw ter.search.earlyb rd_root.routers;

 mport javax. nject. nject;
 mport javax. nject.Na d;

 mport com.tw ter.f nagle.Serv ce;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdResponse;
 mport com.tw ter.search.earlyb rd_root.common.Earlyb rdRequestContext;
 mport com.tw ter.search.earlyb rd_root.common. nject onNa s;
 mport com.tw ter.search.earlyb rd_root.f lters.Earlyb rdT  RangeF lter;
 mport com.tw ter.ut l.Future;

/**
 * For Facets traff c SuperRoot forwards all traff c to t  realt   cluster.
 */
publ c class FacetsRequestRouter extends RequestRouter {

  pr vate f nal Serv ce<Earlyb rdRequestContext, Earlyb rdResponse> realt  ;

  /** Creates a new FacetsRequestRouter  nstance to be used by t  SuperRoot. */
  @ nject
  publ c FacetsRequestRouter(
      @Na d( nject onNa s.REALT ME)
      Serv ce<Earlyb rdRequestContext, Earlyb rdResponse> realt  ,
      @Na d(FacetsRequestRouterModule.T ME_RANGE_F LTER)
      Earlyb rdT  RangeF lter t  RangeF lter) {

    t .realt   = t  RangeF lter.andT n(realt  );
  }

  @Overr de
  publ c Future<Earlyb rdResponse> route(Earlyb rdRequestContext requestContext) {
    return realt  .apply(requestContext);
  }
}
