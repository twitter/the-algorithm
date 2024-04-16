package com.tw ter.search.earlyb rd_root.f lters;

 mport java.ut l.EnumMap;
 mport java.ut l.L st;
 mport java.ut l.Map;

 mport com.google.common.annotat ons.V s bleForTest ng;

 mport com.tw ter.f nagle.Serv ce;
 mport com.tw ter.f nagle.S mpleF lter;
 mport com.tw ter.search.common. tr cs.SearchCounter;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdResponse;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftSearchResult;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftSearchResultExtra tadata;
 mport com.tw ter.search.earlyb rd_root.common.Earlyb rdRequestContext;
 mport com.tw ter.search.earlyb rd_root.common.Earlyb rdRequestType;
 mport com.tw ter.ut l.Future;
 mport com.tw ter.ut l.FutureEventL stener;

/**
 * F lter tracks t   sUserProtected  tadata stats returned from Earlyb rds.
 */
publ c class  sUserProtected tadataTrack ngF lter
    extends S mpleF lter<Earlyb rdRequestContext, Earlyb rdResponse> {
  pr vate stat c f nal Str ng COUNTER_PREF X = " s_user_protected_ tadata_count_f lter_";
  @V s bleForTest ng
  f nal Map<Earlyb rdRequestType, SearchCounter> totalCounterByRequestTypeMap;
  @V s bleForTest ng
  f nal Map<Earlyb rdRequestType, SearchCounter>  sProtectedCounterByRequestTypeMap;

  publ c  sUserProtected tadataTrack ngF lter() {
    t .totalCounterByRequestTypeMap = new EnumMap<>(Earlyb rdRequestType.class);
    t . sProtectedCounterByRequestTypeMap = new EnumMap<>(Earlyb rdRequestType.class);
    for (Earlyb rdRequestType requestType : Earlyb rdRequestType.values()) {
      t .totalCounterByRequestTypeMap.put(requestType,
          SearchCounter.export(COUNTER_PREF X + requestType.getNormal zedNa () + "_total"));
      t . sProtectedCounterByRequestTypeMap.put(requestType,
          SearchCounter.export(COUNTER_PREF X + requestType.getNormal zedNa () + "_ s_protected"));
    }
  }

  @Overr de
  publ c Future<Earlyb rdResponse> apply(
      Earlyb rdRequestContext request,
      Serv ce<Earlyb rdRequestContext, Earlyb rdResponse> serv ce) {
    Future<Earlyb rdResponse> response = serv ce.apply(request);

    Earlyb rdRequestType requestType = request.getEarlyb rdRequestType();
    response.addEventL stener(new FutureEventL stener<Earlyb rdResponse>() {
      @Overr de
      publ c vo d onSuccess(Earlyb rdResponse response) {
         f (!response. sSetSearchResults() || response.getSearchResults().getResults(). sEmpty()) {
          return;
        }
        L st<Thr ftSearchResult> searchResults = response.getSearchResults().getResults();
         nt totalCount = searchResults.s ze();
         nt  sUserProtectedCount = 0;
        for (Thr ftSearchResult searchResult : searchResults) {
           f (searchResult. sSet tadata() && searchResult.get tadata(). sSetExtra tadata()) {
            Thr ftSearchResultExtra tadata extra tadata =
                searchResult.get tadata().getExtra tadata();
             f (extra tadata. s sUserProtected()) {
               sUserProtectedCount++;
            }
          }
        }
         sUserProtected tadataTrack ngF lter.t 
            .totalCounterByRequestTypeMap.get(requestType).add(totalCount);
         sUserProtected tadataTrack ngF lter.t 
            . sProtectedCounterByRequestTypeMap.get(requestType).add( sUserProtectedCount);
      }

      @Overr de
      publ c vo d onFa lure(Throwable cause) { }
    });

    return response;
  }

}
