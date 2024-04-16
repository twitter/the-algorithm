package com.tw ter.search.earlyb rd_root.f lters;

 mport java.ut l.concurrent.T  Un ;

 mport com.tw ter.search.common.dec der.SearchDec der;
 mport com.tw ter.search.common.part  on ng.snowflakeparser.Snowflake dParser;
 mport com.tw ter.search.earlyb rd.conf g.Serv ngRange;
 mport com.tw ter.search.earlyb rd_root.common.Earlyb rdRequestContext;

publ c class Realt  Serv ngRangeProv der  mple nts Serv ngRangeProv der {

  pr vate stat c f nal  nt DEFAULT_SERV NG_RANGE_BOUNDARY_HOURS_AGO = 240;

  pr vate f nal SearchDec der dec der;
  pr vate f nal Str ng dec derKey;

  publ c Realt  Serv ngRangeProv der(SearchDec der dec der, Str ng dec derKey) {
    t .dec der = dec der;
    t .dec derKey = dec derKey;
  }

  @Overr de
  publ c Serv ngRange getServ ngRange(
      f nal Earlyb rdRequestContext requestContext, boolean useBoundaryOverr de) {
    return new Serv ngRange() {
      @Overr de
      publ c long getServ ngRangeS nce d() {
        long serv ngRangeStartM ll s = T  Un .HOURS.toM ll s(
            (dec der.featureEx sts(dec derKey))
                ? dec der.getAva lab l y(dec derKey)
                : DEFAULT_SERV NG_RANGE_BOUNDARY_HOURS_AGO);

        long boundaryT   = requestContext.getCreatedT  M ll s() - serv ngRangeStartM ll s;
        return Snowflake dParser.generateVal dStatus d(boundaryT  , 0);
      }

      @Overr de
      publ c long getServ ngRangeMax d() {
        return Snowflake dParser.generateVal dStatus d(
            requestContext.getCreatedT  M ll s(), 0);
      }

      @Overr de
      publ c long getServ ngRangeS nceT  SecondsFromEpoch() {
        long serv ngRangeStartM ll s = T  Un .HOURS.toM ll s(
            (dec der.featureEx sts(dec derKey))
                ? dec der.getAva lab l y(dec derKey)
                : DEFAULT_SERV NG_RANGE_BOUNDARY_HOURS_AGO);

        long boundaryT   = requestContext.getCreatedT  M ll s() - serv ngRangeStartM ll s;
        return boundaryT   / 1000;
      }

      @Overr de
      publ c long getServ ngRangeUnt lT  SecondsFromEpoch() {
        return requestContext.getCreatedT  M ll s() / 1000;
      }
    };
  }
}
