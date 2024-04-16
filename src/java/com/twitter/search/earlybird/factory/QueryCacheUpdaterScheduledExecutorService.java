package com.tw ter.search.earlyb rd.factory;

 mport java.ut l.concurrent.Callable;
 mport java.ut l.concurrent.Sc duledExecutorServ ce;
 mport java.ut l.concurrent.Sc duledFuture;
 mport java.ut l.concurrent.T  Un ;

 mport com.google.common.annotat ons.V s bleForTest ng;

 mport com.tw ter.common.ut l.concurrent.Forward ngExecutorServ ce;

/**
 * T  delegate type  s  ntended for QueryCac Updater because   uses mult ple threads to
 * create query cac  dur ng startup and t n sw ch later to use s ngle thread to update t 
 * cac .
 */
publ c abstract class QueryCac UpdaterSc duledExecutorServ ce<T extends Sc duledExecutorServ ce>
  extends Forward ngExecutorServ ce<T>  mple nts Sc duledExecutorServ ce {
  publ c QueryCac UpdaterSc duledExecutorServ ce(T executor) {
    super(executor);
  }

  /**
   * Sets t  number of worker threads  n t  executor serv ce to an appropr ate value after t 
   * earlyb rd startup has f n s d. Wh le earlyb rd  s start ng up,   m ght want t  executor
   * serv ce to have more threads,  n order to parallel ze more so  start up tasks. But once
   * earlyb rd  s up,   m ght make sense to lo r t  number of worker threads.
   */
  publ c abstract vo d setWorkerPoolS zeAfterStartup();

  @Overr de
  publ c Sc duledFuture<?> sc dule(Runnable command, long delay, T  Un  un ) {
    return delegate.sc dule(command, delay, un );
  }

  @Overr de
  publ c Sc duledFuture<?> sc duleAtF xedRate(
      Runnable command, long  n  alDelay, long per od, T  Un  un ) {
    return delegate.sc duleAtF xedRate(command,  n  alDelay, per od, un );
  }

  @Overr de
  publ c Sc duledFuture<?> sc duleW hF xedDelay(
      Runnable command, long  n  alDelay, long delay, T  Un  un ) {
    return delegate.sc duleW hF xedDelay(command,  n  alDelay, delay, un );
  }

  @Overr de
  publ c <V> Sc duledFuture<V> sc dule(Callable<V> callable, long delay, T  Un  un ) {
    return delegate.sc dule(callable, delay, un );
  }

  @V s bleForTest ng
  publ c T getDelegate() {
    return delegate;
  }
}
