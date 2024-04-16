package com.tw ter.search.earlyb rd_root.cach ng;

 mport com.tw ter.search.common.cach ng.f lter.QueryCac Pred cate;
 mport com.tw ter.search.common.dec der.SearchDec der;
 mport com.tw ter.search.earlyb rd.common.Earlyb rdRequestUt l;
 mport com.tw ter.search.earlyb rd_root.common.Earlyb rdRequestContext;
 mport com.tw ter.search.earlyb rd_root.common.Earlyb rdRequestType;

publ c class RecencyQueryCac Pred cate extends QueryCac Pred cate<Earlyb rdRequestContext> {
  pr vate f nal SearchDec der dec der;
  pr vate f nal Str ng recencyCac EnabledDec derKey;

  publ c RecencyQueryCac Pred cate(SearchDec der dec der, Str ng normal zedSearchRootNa ) {
    t .dec der = dec der;
    t .recencyCac EnabledDec derKey = "recency_cac _enabled_" + normal zedSearchRootNa ;
  }

  @Overr de
  publ c Boolean shouldQueryCac (Earlyb rdRequestContext request) {
    return Earlyb rdRequestType.RECENCY == request.getEarlyb rdRequestType()
        && Earlyb rdRequestUt l. sCach ngAllo d(request.getRequest())
        && dec der. sAva lable(recencyCac EnabledDec derKey);
  }
}
