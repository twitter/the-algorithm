package com.tw ter.search.earlyb rd_root.routers;

 mport javax. nject.Na d;
 mport javax. nject.S ngleton;

 mport com.google. nject.Prov des;

 mport com.tw ter. nject.Tw terModule;
 mport com.tw ter.search.common.dec der.SearchDec der;
 mport com.tw ter.search.earlyb rd_root.f lters.Earlyb rdT  RangeF lter;
 mport com.tw ter.search.earlyb rd_root.f lters.FullArch veServ ngRangeProv der;
 mport com.tw ter.search.earlyb rd_root.f lters.Realt  Serv ngRangeProv der;
 mport com.tw ter.search.earlyb rd_root.f lters.Serv ngRangeProv der;

publ c class RecencyRequestRouterModule extends Tw terModule {
  publ c stat c f nal Str ng FULL_ARCH VE_T ME_RANGE_F LTER =
      "recency_full_arch ve_t  _range_f lter";
  publ c stat c f nal Str ng REALT ME_T ME_RANGE_F LTER =
      "recency_realt  _t  _range_f lter";
  publ c stat c f nal Str ng PROTECTED_T ME_RANGE_F LTER =
      "recency_protected_t  _range_f lter";

  publ c stat c f nal Str ng REALT ME_RANGE_SERV NG_RANGE_BOUNDARY_HOURS_AGO_DEC DER_KEY =
      "superroot_recency_realt  _serv ng_range_boundary_h s_ago";
  publ c stat c f nal Str ng PROTECTED_SERV NG_RANGE_BOUNDARY_HOURS_AGO_DEC DER_KEY =
      "superroot_recency_protected_serv ng_range_boundary_h s_ago";
  publ c stat c f nal Str ng FULL_ARCH VE_SERV NG_RANGE_BOUNDARY_HOURS_AGO_DEC DER_KEY =
      "superroot_recency_full_arch ve_serv ng_range_boundary_h s_ago";

  pr vate Serv ngRangeProv der getFullArch veServ ngRangeProv der(f nal SearchDec der dec der)
      throws Except on {
    return new FullArch veServ ngRangeProv der(
        dec der, FULL_ARCH VE_SERV NG_RANGE_BOUNDARY_HOURS_AGO_DEC DER_KEY);
  }

  pr vate Serv ngRangeProv der getRealt  Serv ngRangeProv der(f nal SearchDec der dec der)
      throws Except on {
    return new Realt  Serv ngRangeProv der(
        dec der, REALT ME_RANGE_SERV NG_RANGE_BOUNDARY_HOURS_AGO_DEC DER_KEY);
  }

  pr vate Serv ngRangeProv der getProtectedServ ngRangeProv der(f nal SearchDec der dec der)
      throws Except on {
    return new Realt  Serv ngRangeProv der(
        dec der, PROTECTED_SERV NG_RANGE_BOUNDARY_HOURS_AGO_DEC DER_KEY);
  }

  @Prov des
  @S ngleton
  @Na d(FULL_ARCH VE_T ME_RANGE_F LTER)
  pr vate Earlyb rdT  RangeF lter prov desFullArch veT  RangeF lter(SearchDec der dec der)
      throws Except on {
    return Earlyb rdT  RangeF lter.newT  RangeF lterW houtQueryRewr er(
        getFullArch veServ ngRangeProv der(dec der));
  }

  @Prov des
  @S ngleton
  @Na d(REALT ME_T ME_RANGE_F LTER)
  pr vate Earlyb rdT  RangeF lter prov desRealt  T  RangeF lter(SearchDec der dec der)
      throws Except on {
    return Earlyb rdT  RangeF lter.newT  RangeF lterW houtQueryRewr er(
        getRealt  Serv ngRangeProv der(dec der));
  }

  @Prov des
  @S ngleton
  @Na d(PROTECTED_T ME_RANGE_F LTER)
  pr vate Earlyb rdT  RangeF lter prov desProtectedT  RangeF lter(SearchDec der dec der)
      throws Except on {
    return Earlyb rdT  RangeF lter.newT  RangeF lterW houtQueryRewr er(
        getProtectedServ ngRangeProv der(dec der));
  }
}
