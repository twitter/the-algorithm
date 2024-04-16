package com.tw ter.search.earlyb rd_root.cach ng;

 mport com.tw ter.search.common.cach ng.f lter.QueryCac Pred cate;
 mport com.tw ter.search.common.dec der.SearchDec der;
 mport com.tw ter.search.earlyb rd.common.Earlyb rdRequestUt l;
 mport com.tw ter.search.earlyb rd_root.common.Earlyb rdRequestContext;
 mport com.tw ter.search.earlyb rd_root.common.Earlyb rdRequestType;

publ c class TopT etsQueryCac Pred cate extends QueryCac Pred cate<Earlyb rdRequestContext> {
  pr vate f nal SearchDec der dec der;
  pr vate f nal Str ng topt etsCac EnabledDec derKey;

  publ c TopT etsQueryCac Pred cate(SearchDec der dec der, Str ng normal zedSearchRootNa ) {
    t .dec der = dec der;
    t .topt etsCac EnabledDec derKey = "topt ets_cac _enabled_" + normal zedSearchRootNa ;
  }

  @Overr de
  publ c Boolean shouldQueryCac (Earlyb rdRequestContext requestContext) {
    return Earlyb rdRequestType.TOP_TWEETS == requestContext.getEarlyb rdRequestType()
        && Earlyb rdRequestUt l. sCach ngAllo d(requestContext.getRequest())
        && dec der. sAva lable(topt etsCac EnabledDec derKey);
  }
}
