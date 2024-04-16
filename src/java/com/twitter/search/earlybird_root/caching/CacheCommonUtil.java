package com.tw ter.search.earlyb rd_root.cach ng;

 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdResponse;

publ c f nal class Cac CommonUt l {
  publ c stat c f nal Str ng NAMED_MAX_CACHE_RESULTS = "maxCac Results";

  pr vate Cac CommonUt l() {
  }

  publ c stat c boolean hasResults(Earlyb rdResponse response) {
    return response. sSetSearchResults()
      && (response.getSearchResults().getResults() != null)
      && !response.getSearchResults().getResults(). sEmpty();
  }
}
