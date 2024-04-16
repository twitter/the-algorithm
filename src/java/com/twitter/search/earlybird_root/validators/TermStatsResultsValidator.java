package com.tw ter.search.earlyb rd_root.val dators;

 mport com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdCluster;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdResponse;
 mport com.tw ter.ut l.Future;

publ c class TermStatsResultsVal dator  mple nts Serv ceResponseVal dator<Earlyb rdResponse> {
  pr vate f nal Earlyb rdCluster cluster;

  publ c TermStatsResultsVal dator(Earlyb rdCluster cluster) {
    t .cluster = cluster;
  }

  @Overr de
  publ c Future<Earlyb rdResponse> val date(Earlyb rdResponse response) {
     f (!response. sSetTermStat st csResults()
        || !response.getTermStat st csResults(). sSetTermResults()) {
      return Future.except on(
          new  llegalStateExcept on(cluster + " returned null term stat st cs results."));
    }
    return Future.value(response);
  }
}
