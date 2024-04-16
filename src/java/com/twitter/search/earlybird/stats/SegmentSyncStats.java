package com.tw ter.search.earlyb rd.stats;

 mport com.tw ter.search.common. tr cs.SearchCounter;
 mport com.tw ter.search.common. tr cs.T  r;

publ c class Seg ntSyncStats {
  pr vate stat c f nal Str ng CPU_TOTAL = "_cpu_total_";
  pr vate stat c f nal Str ng CPU_USER  = "_cpu_user_mode_";
  pr vate stat c f nal Str ng CPU_SYS   = "_cpu_system_mode_";

  pr vate f nal SearchCounter seg ntSyncLatency;
  pr vate f nal SearchCounter seg ntSyncLatencyCpuTotal;
  pr vate f nal SearchCounter seg ntSyncLatencyCpuUserMode;
  pr vate f nal SearchCounter seg ntSyncLatencyCpuSystemMode;
  pr vate f nal SearchCounter seg ntSyncCount;
  pr vate f nal SearchCounter seg ntErrorCount;

  pr vate Seg ntSyncStats(SearchCounter seg ntSyncLatency,
                           SearchCounter seg ntSyncLatencyCpuTotal,
                           SearchCounter seg ntSyncLatencyCpuUserMode,
                           SearchCounter seg ntSyncLatencyCpuSystemMode,
                           SearchCounter seg ntSyncCount,
                           SearchCounter seg ntErrorCount) {
    t .seg ntSyncLatency = seg ntSyncLatency;
    t .seg ntSyncLatencyCpuTotal = seg ntSyncLatencyCpuTotal;
    t .seg ntSyncLatencyCpuUserMode = seg ntSyncLatencyCpuUserMode;
    t .seg ntSyncLatencyCpuSystemMode = seg ntSyncLatencyCpuSystemMode;
    t .seg ntSyncCount = seg ntSyncCount;
    t .seg ntErrorCount = seg ntErrorCount;
  }

  /**
   * Creates a new set of stats for t  g ven seg nt sync act on.
   * @param act on t  na  to be used for t  sync stats.
   */
  publ c Seg ntSyncStats(Str ng act on) {
    t (SearchCounter.export("seg nt_" + act on + "_latency_ms"),
         SearchCounter.export("seg nt_" + act on + "_latency" + CPU_TOTAL + "ms"),
         SearchCounter.export("seg nt_" + act on + "_latency" + CPU_USER + "ms"),
         SearchCounter.export("seg nt_" + act on + "_latency" + CPU_SYS + "ms"),
         SearchCounter.export("seg nt_" + act on + "_count"),
         SearchCounter.export("seg nt_" + act on + "_error_count"));
  }

  /**
   * Records a completed act on us ng t  spec f ed t  r.
   */
  publ c vo d act onComplete(T  r t  r) {
    seg ntSyncCount. ncre nt();
    seg ntSyncLatency.add(t  r.getElapsed());
    seg ntSyncLatencyCpuTotal.add(t  r.getElapsedCpuTotal());
    seg ntSyncLatencyCpuUserMode.add(t  r.getElapsedCpuUserMode());
    seg ntSyncLatencyCpuSystemMode.add(t  r.getElapsedCpuSystemMode());
  }

  publ c vo d recordError() {
    seg ntErrorCount. ncre nt();
  }
}
