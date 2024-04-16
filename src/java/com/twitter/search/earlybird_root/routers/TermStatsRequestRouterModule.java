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

publ c class TermStatsRequestRouterModule extends Tw terModule {
  publ c stat c f nal Str ng FULL_ARCH VE_T ME_RANGE_F LTER =
      "term_stats_full_arch ve_t  _range_f lter";
  publ c stat c f nal Str ng REALT ME_T ME_RANGE_F LTER =
      "term_stats_realt  _t  _range_f lter";

  pr vate stat c f nal Str ng SUPERROOT_TERM_STATS_SERV NG_RANGE_BOUNDARY_HOURS_AGO_DEC DER_KEY =
      "superroot_term_stats_serv ng_range_boundary_h s_ago";

  pr vate Serv ngRangeProv der getFullArch veT  RangeProv der(f nal SearchDec der dec der)
      throws Except on {
    return new FullArch veServ ngRangeProv der(
        dec der, SUPERROOT_TERM_STATS_SERV NG_RANGE_BOUNDARY_HOURS_AGO_DEC DER_KEY);
  }

  pr vate Serv ngRangeProv der getRealt  T  RangeProv der(f nal SearchDec der dec der)
      throws Except on {
    return new Realt  Serv ngRangeProv der(
        dec der, SUPERROOT_TERM_STATS_SERV NG_RANGE_BOUNDARY_HOURS_AGO_DEC DER_KEY);
  }

  /**
   * For term stats full arch ve cluster spans from 21 March to 2006 to 6 days ago from current t  
   */
  @Prov des
  @S ngleton
  @Na d(FULL_ARCH VE_T ME_RANGE_F LTER)
  pr vate Earlyb rdT  RangeF lter prov desFullArch veT  RangeF lter(f nal SearchDec der dec der)
      throws Except on {
    return Earlyb rdT  RangeF lter.newT  RangeF lterW hQueryRewr er(
        getFullArch veT  RangeProv der(dec der), dec der);
  }

  /**
   * For term stats realt   cluster spans from 6 days ago from current t   to a far away date
   *  nto t  future
   */
  @Prov des
  @S ngleton
  @Na d(REALT ME_T ME_RANGE_F LTER)
  pr vate Earlyb rdT  RangeF lter prov desRealt  T  RangeF lter(f nal SearchDec der dec der)
      throws Except on {
    return Earlyb rdT  RangeF lter.newT  RangeF lterW hQueryRewr er(
        getRealt  T  RangeProv der(dec der), dec der);
  }
}
