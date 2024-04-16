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

/**
 * A f lter that:
 *  - Str ps t  request of all personal zat on f elds, normal zes   and looks   up  n t  cac .
 *     f   f nds a response w h 0 results  n t  cac ,   returns  .
 *  - Cac s t  response for a personal zed query, w never t  response has 0 results. T  cac 
 *    key  s t  normal zed request w h all personal zat on f elds str pped.
 *
 *  f a query (from a logged  n or logged out user) returns 0 results, t n t  sa  query w ll
 * always return 0 results, for all users. So   can cac  that result.
 */
publ c class RelevanceZeroResultsCac F lter
  extends Cac F lter<Earlyb rdRequestContext, Earlyb rdRequest, Earlyb rdResponse> {

  /** Creates a f lter that cac s relevance requests w h 0 results. */
  @ nject
  publ c RelevanceZeroResultsCac F lter(
      @RelevanceCac  Cac <Earlyb rdRequest, Earlyb rdResponse> cac ,
      SearchDec der dec der,
      @Na d(SearchRootModule.NAMED_NORMAL ZED_SEARCH_ROOT_NAME) Str ng normal zedSearchRootNa ) {
    super(cac ,
          new RelevanceZeroResultsQueryCac Pred cate(dec der, normal zedSearchRootNa ),
          new RelevanceZeroResultsCac RequestNormal zer(),
          new RelevanceZeroResultsCac PostProcessor(),
          new RelevanceZeroResultsServ cePostProcessor(cac ),
          new Earlyb rdRequestPerCl entCac Stats("relevance_zero_results"));
  }
}
