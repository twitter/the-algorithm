package com.tw ter.search.earlyb rd_root.routers;

 mport javax. nject.Na d;
 mport javax. nject.S ngleton;

 mport com.google. nject.Prov des;

 mport com.tw ter. nject.Tw terModule;
 mport com.tw ter.search.common.dec der.SearchDec der;
 mport com.tw ter.search.earlyb rd_root.f lters.Earlyb rdT  RangeF lter;
 mport com.tw ter.search.earlyb rd_root.f lters.Realt  Serv ngRangeProv der;
 mport com.tw ter.search.earlyb rd_root.f lters.Serv ngRangeProv der;

publ c class TopT etsRequestRouterModule extends Tw terModule {
  publ c stat c f nal Str ng T ME_RANGE_F LTER = "top_t ets_t  _range_f lter";

  publ c stat c f nal Str ng SERV NG_RANGE_BOUNDARY_HOURS_AGO_DEC DER_KEY =
      "superroot_top_t ets_serv ng_range_boundary_h s_ago";

  pr vate Serv ngRangeProv der getServ ngRangeProv der(f nal SearchDec der dec der)
      throws Except on {
    return new Realt  Serv ngRangeProv der(dec der, SERV NG_RANGE_BOUNDARY_HOURS_AGO_DEC DER_KEY);
  }

  @Prov des
  @S ngleton
  @Na d(T ME_RANGE_F LTER)
  pr vate Earlyb rdT  RangeF lter prov desT  RangeF lter(SearchDec der dec der) throws Except on {
    return Earlyb rdT  RangeF lter.newT  RangeF lterW houtQueryRewr er(
        getServ ngRangeProv der(dec der));
  }
}
