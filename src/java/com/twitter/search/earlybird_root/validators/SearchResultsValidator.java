package com.tw ter.search.earlyb rd_root.val dators;

 mport com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdCluster;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdResponse;
 mport com.tw ter.ut l.Future;

publ c class SearchResultsVal dator
     mple nts Serv ceResponseVal dator<Earlyb rdResponse> {

  pr vate f nal Earlyb rdCluster cluster;

  publ c SearchResultsVal dator(Earlyb rdCluster cluster) {
    t .cluster = cluster;
  }

  @Overr de
  publ c Future<Earlyb rdResponse> val date(Earlyb rdResponse response) {
     f (!response. sSetSearchResults()
        || !response.getSearchResults(). sSetResults()) {
      return Future.except on(
          new  llegalStateExcept on(cluster + " d dn't set search results"));
    } else  f (!response.getSearchResults(). sSetMaxSearc dStatus D()) {
      return Future.except on(
          new  llegalStateExcept on(cluster + " d dn't set max searc d status  d"));
    } else {
      boolean  sEarlyTerm nated = response. sSetEarlyTerm nat on nfo()
          && response.getEarlyTerm nat on nfo(). sEarlyTerm nated();
       f (! sEarlyTerm nated && !response.getSearchResults(). sSetM nSearc dStatus D()) {
        return Future.except on(
            new  llegalStateExcept on(
                cluster + " ne  r early term nated nor set m n searc d status  d"));
      } else {
        return Future.value(response);
      }
    }
  }
}
