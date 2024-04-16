package com.tw ter.search.earlyb rd_root.f lters;

 mport java.ut l.Date;
 mport java.ut l.concurrent.T  Un ;

 mport com.tw ter.search.common.dec der.SearchDec der;
 mport com.tw ter.search.common.part  on ng.snowflakeparser.Snowflake dParser;
 mport com.tw ter.search.common.ut l.date.DateUt l;
 mport com.tw ter.search.earlyb rd.conf g.Serv ngRange;
 mport com.tw ter.search.earlyb rd_root.common.Earlyb rdRequestContext;

publ c class FullArch veServ ngRangeProv der  mple nts Serv ngRangeProv der {

  publ c stat c f nal Date FULL_ARCH VE_START_DATE = DateUt l.toDate(2006, 3, 21);
  pr vate stat c f nal  nt DEFAULT_SERV NG_RANGE_BOUNDARY_HOURS_AGO = 48;

  pr vate f nal SearchDec der dec der;
  pr vate f nal Str ng dec derKey;

  publ c FullArch veServ ngRangeProv der(
      SearchDec der dec der, Str ng dec derKey) {
    t .dec der = dec der;
    t .dec derKey = dec derKey;
  }

  @Overr de
  publ c Serv ngRange getServ ngRange(
      f nal Earlyb rdRequestContext requestContext, boolean useBoundaryOverr de) {
    return new Serv ngRange() {
      @Overr de
      publ c long getServ ngRangeS nce d() {
        //   use 1  nstead of 0, because t  s nce_ d operator  s  nclus ve  n earlyb rds.
        return 1L;
      }

      @Overr de
      publ c long getServ ngRangeMax d() {
        long serv ngRangeEndM ll s = T  Un .HOURS.toM ll s(
            (dec der.featureEx sts(dec derKey))
                ? dec der.getAva lab l y(dec derKey)
                : DEFAULT_SERV NG_RANGE_BOUNDARY_HOURS_AGO);

        long boundaryT   = requestContext.getCreatedT  M ll s() - serv ngRangeEndM ll s;
        return Snowflake dParser.generateVal dStatus d(boundaryT  , 0);
      }

      @Overr de
      publ c long getServ ngRangeS nceT  SecondsFromEpoch() {
        return FULL_ARCH VE_START_DATE.getT  () / 1000;
      }

      @Overr de
      publ c long getServ ngRangeUnt lT  SecondsFromEpoch() {
        long serv ngRangeEndM ll s = T  Un .HOURS.toM ll s(
            (dec der.featureEx sts(dec derKey))
                ? dec der.getAva lab l y(dec derKey)
                : DEFAULT_SERV NG_RANGE_BOUNDARY_HOURS_AGO);

        long boundaryT   = requestContext.getCreatedT  M ll s() - serv ngRangeEndM ll s;
        return boundaryT   / 1000;
      }
    };
  }
}
