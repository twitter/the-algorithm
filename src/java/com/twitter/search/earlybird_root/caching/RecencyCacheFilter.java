package com.tw ter.search.earlyb rd_root.cach ng;

 mport javax. nject. nject;
 mport javax. nject.Na d;

 mport com.tw ter.search.common.cach ng.Cac ;
 mport com.tw ter.search.common.cach ng.f lter.Cac F lter;
 mport com.tw ter.search.common.dec der.SearchDec der;
 mport com.tw ter.search.common.root.SearchRootModule;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdRequest;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdResponse;
 mport com.tw ter.search.earlyb rd_root.common.Earlyb rdRequestContext;
 mport com.tw ter.search.earlyb rd_root.common.Earlyb rdRequestType;

publ c class RecencyCac F lter extends
    Cac F lter<Earlyb rdRequestContext, Earlyb rdRequest, Earlyb rdResponse> {
  /**
   * Creates a cac  f lter for earlyb rd recency requests.
   */
  @ nject
  publ c RecencyCac F lter(
      @RecencyCac  Cac <Earlyb rdRequest, Earlyb rdResponse> cac ,
      SearchDec der dec der,
      @Na d(SearchRootModule.NAMED_NORMAL ZED_SEARCH_ROOT_NAME) Str ng normal zedSearchRootNa ,
      @Na d(Cac CommonUt l.NAMED_MAX_CACHE_RESULTS)  nt maxCac Results) {
    super(cac ,
          new RecencyQueryCac Pred cate(dec der, normal zedSearchRootNa ),
          new RecencyCac RequestNormal zer(),
          new RecencyAndRelevanceCac PostProcessor(),
          new RecencyServ cePostProcessor(cac , maxCac Results),
          new Earlyb rdRequestPerCl entCac Stats(
              Earlyb rdRequestType.RECENCY.getNormal zedNa ()));
  }
}
