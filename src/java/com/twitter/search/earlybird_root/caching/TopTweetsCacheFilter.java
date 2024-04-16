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

publ c class TopT etsCac F lter extends
    Cac F lter<Earlyb rdRequestContext, Earlyb rdRequest, Earlyb rdResponse> {
  /**
   * Constructs a new cac  f lter for top t ets requests.
   */
  @ nject
  publ c TopT etsCac F lter(
      @TopT etsCac  Cac <Earlyb rdRequest, Earlyb rdResponse> cac ,
      SearchDec der dec der,
      @Na d(SearchRootModule.NAMED_NORMAL ZED_SEARCH_ROOT_NAME) Str ng normal zedSearchRootNa ) {
    super(cac ,
          new TopT etsQueryCac Pred cate(dec der, normal zedSearchRootNa ),
          new TopT etsCac RequestNormal zer(),
          new Earlyb rdCac PostProcessor(),
          new TopT etsServ cePostProcessor(cac ),
          new Earlyb rdRequestPerCl entCac Stats(
              Earlyb rdRequestType.TOP_TWEETS.getNormal zedNa ()));
  }
}
