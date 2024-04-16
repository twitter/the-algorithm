package com.tw ter.search.feature_update_serv ce.modules;

 mport java.ut l.concurrent.L nkedBlock ngQueue;
 mport java.ut l.concurrent.ThreadPoolExecutor;
 mport java.ut l.concurrent.T  Un ;

 mport com.google.common.annotat ons.V s bleForTest ng;
 mport com.google. nject.Prov des;
 mport com.google. nject.S ngleton;

 mport com.tw ter. nject.Tw terModule;
 mport com.tw ter.search.common. tr cs.SearchCustomGauge;
 mport com.tw ter.search.feature_update_serv ce.stats.FeatureUpdateStats;
 mport com.tw ter.ut l.ExecutorServ ceFuturePool;
 mport com.tw ter.ut l. nterrupt bleExecutorServ ceFuturePool;

publ c class FuturePoolModule extends Tw terModule {
  /**
   * Prov de future pool backed by executor serv ce, w h bounded thread pool and bounded back ng
   * queue.
   */
  @Prov des
  @S ngleton
  publ c ExecutorServ ceFuturePool futurePool() {
    // T se l m s are based on serv ce capac y est mates and test ng on stag ng,
    // attempt ng to g ve t  pool as many res ces as poss ble w hout overload ng anyth ng.
    // 100-200 threads  s manageable, and t  2000 queue s ze  s based on a conservat ve upper
    // l m  that tasks  n t  queue take 1 MB each,  an ng queue maxes out at 2 GB, wh ch should
    // be okay g ven 4 GB RAM w h 3 GB reserved  ap.
    return createFuturePool(100, 200, 2000);
  }

  /**
   * Create a future pool backed by executor serv ce, w h bounded thread pool and bounded back ng
   * queue. ONLY V S B LE FOR TEST NG; don't  nvoke outs de t  class.
   */
  @V s bleForTest ng
  publ c stat c ExecutorServ ceFuturePool createFuturePool(
       nt corePoolS ze,  nt max mumPoolS ze,  nt queueCapac y) {
    f nal L nkedBlock ngQueue<Runnable> queue = new L nkedBlock ngQueue<>(queueCapac y);

    ExecutorServ ceFuturePool futurePool = new  nterrupt bleExecutorServ ceFuturePool(
        new ThreadPoolExecutor(
            corePoolS ze,
            max mumPoolS ze,
            60L,
            T  Un .SECONDS,
            queue));

    SearchCustomGauge.export(FeatureUpdateStats.PREF X + "thread_pool_s ze",
        futurePool::poolS ze);
    SearchCustomGauge.export(FeatureUpdateStats.PREF X + "work_queue_s ze",
        queue::s ze);

    return futurePool;
  }
}
