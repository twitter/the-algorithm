package com.tw ter.search.earlyb rd;

 mport java.ut l.concurrent.ArrayBlock ngQueue;
 mport java.ut l.concurrent.ExecutorServ ce;
 mport java.ut l.concurrent.Executors;
 mport java.ut l.concurrent.RejectedExecut onExcept on;
 mport java.ut l.concurrent.ThreadFactory;
 mport java.ut l.concurrent.ThreadPoolExecutor;
 mport java.ut l.concurrent.T  Un ;

 mport scala.Funct on0;

 mport com.google.common.annotat ons.V s bleForTest ng;
 mport com.google.common.base.Precond  ons;
 mport com.google.common.ut l.concurrent.ThreadFactoryBu lder;

 mport com.tw ter.search.common.concurrent.ThreadPoolExecutorStats;
 mport com.tw ter.search.common. tr cs.SearchRateCounter;
 mport com.tw ter.search.earlyb rd.common.conf g.Earlyb rdProperty;
 mport com.tw ter.ut l.ExecutorServ ceFuturePool;
 mport com.tw ter.ut l.Future;
 mport com.tw ter.ut l.FuturePool;

/**
 * A future pool that delegates all calls to an underly ng futurePool, wh ch can be recreated.
 */
publ c class Earlyb rdFuturePoolManager  mple nts FuturePool {
  pr vate volat le ExecutorServ ceFuturePool pool = null;

  pr vate f nal Str ng threadNa ;
  pr vate f nal ThreadPoolExecutorStats threadPoolExecutorStats;

  publ c Earlyb rdFuturePoolManager(Str ng threadNa ) {
    t .threadNa  = threadNa ;
    t .threadPoolExecutorStats = new ThreadPoolExecutorStats(threadNa );
  }

  f nal synchron zed vo d createUnderly ngFuturePool( nt threadCount) {
    Precond  ons.c ckState(pool == null, "Cannot create a new pool before stopp ng t  old one");

    ExecutorServ ce executorServ ce =
        createExecutorServ ce(threadCount, getMaxQueueS ze());
     f (executorServ ce  nstanceof ThreadPoolExecutor) {
      threadPoolExecutorStats.setUnderly ngExecutorForStats((ThreadPoolExecutor) executorServ ce);
    }

    pool = new ExecutorServ ceFuturePool(executorServ ce);
  }

  f nal synchron zed vo d stopUnderly ngFuturePool(long t  out, T  Un  t  un )
      throws  nterruptedExcept on {
    Precond  ons.c ckNotNull(pool);
    pool.executor().shutdown();
    pool.executor().awa Term nat on(t  out, t  un );
    pool = null;
  }

  boolean  sPoolReady() {
    return pool != null;
  }

  @Overr de
  publ c f nal <T> Future<T> apply(Funct on0<T> f) {
    return Precond  ons.c ckNotNull(pool).apply(f);
  }

  @V s bleForTest ng
  protected ExecutorServ ce createExecutorServ ce( nt threadCount,  nt maxQueueS ze) {
     f (maxQueueS ze <= 0) {
      return Executors.newF xedThreadPool(threadCount, createThreadFactory(threadNa ));
    }

    SearchRateCounter rejectedTaskCounter =
        SearchRateCounter.export(threadNa  + "_rejected_task_count");
    return new ThreadPoolExecutor(
        threadCount, threadCount, 0, T  Un .M LL SECONDS,
        new ArrayBlock ngQueue<>(maxQueueS ze),
        createThreadFactory(threadNa ),
        (runnable, executor) -> {
          rejectedTaskCounter. ncre nt();
          throw new RejectedExecut onExcept on(threadNa  + " queue  s full");
        });
  }

  @V s bleForTest ng
  protected  nt getMaxQueueS ze() {
    return Earlyb rdProperty.MAX_QUEUE_S ZE.get(0);
  }

  @V s bleForTest ng
  stat c ThreadFactory createThreadFactory(Str ng threadNa ) {
    return new ThreadFactoryBu lder()
        .setNa Format(threadNa  + "-%d")
        .setDaemon(true)
        .bu ld();
  }

  @Overr de
  publ c  nt poolS ze() {
    return Precond  ons.c ckNotNull(pool).poolS ze();
  }

  @Overr de
  publ c  nt numAct veTasks() {
    return Precond  ons.c ckNotNull(pool).numAct veTasks();
  }

  @Overr de
  publ c long numCompletedTasks() {
    return Precond  ons.c ckNotNull(pool).numCompletedTasks();
  }


}
