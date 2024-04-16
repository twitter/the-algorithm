package com.tw ter.search.earlyb rd.ut l;

 mport java. o.Closeable;
 mport java. o. OExcept on;
 mport java.ut l.concurrent.Sc duledExecutorServ ce;
 mport java.ut l.concurrent.Sc duledFuture;

 mport com.tw ter.common.ut l.Clock;
 mport com.tw ter.search.common.concurrent.Sc duledExecutorServ ceFactory;
 mport com.tw ter.search.common. tr cs.SearchCounter;
 mport com.tw ter.search.common. tr cs.SearchStatsRece ver;
 mport com.tw ter.search.earlyb rd.except on.Cr  calExcept onHandler;

/**
 * Executes a s ngle per od c task.
 */
publ c abstract class OneTaskSc duledExecutorManager
    extends Sc duledExecutorManager  mple nts Closeable {
  pr vate f nal Sc duledExecutorTask sc duledTask;
  pr vate f nal Per od cAct onParams per od cAct onParams;

  publ c OneTaskSc duledExecutorManager(
      Sc duledExecutorServ ceFactory executorServ ceFactory,
      Str ng threadNa Format,
      boolean  sDaemon,
      Per od cAct onParams per od cAct onParams,
      ShutdownWa T  Params shutdownT m ng,
      SearchStatsRece ver searchStatsRece ver,
      Cr  calExcept onHandler cr  calExcept onHandler) {
    t (executorServ ceFactory.bu ld(threadNa Format,  sDaemon), per od cAct onParams,
        shutdownT m ng, searchStatsRece ver, cr  calExcept onHandler);
  }

  publ c OneTaskSc duledExecutorManager(
      Sc duledExecutorServ ce executor,
      Per od cAct onParams per od cAct onParams,
      ShutdownWa T  Params shutdownT m ng,
      SearchStatsRece ver searchStatsRece ver,
      Cr  calExcept onHandler cr  calExcept onHandler) {
    t (executor, per od cAct onParams, shutdownT m ng, searchStatsRece ver, null,
        cr  calExcept onHandler, Clock.SYSTEM_CLOCK);
  }

  publ c OneTaskSc duledExecutorManager(
      Sc duledExecutorServ ce executor,
      Per od cAct onParams per od cAct onParams,
      ShutdownWa T  Params shutdownWa T  Params,
      SearchStatsRece ver searchStatsRece ver,
      SearchCounter  erat onCounter,
      Cr  calExcept onHandler cr  calExcept onHandler,
      Clock clock) {
    super(executor, shutdownWa T  Params, searchStatsRece ver,  erat onCounter,
        cr  calExcept onHandler, clock);

    t .per od cAct onParams = per od cAct onParams;
    t .sc duledTask = new Sc duledExecutorTask(get erat onCounter(), clock) {
      @Overr de
      protected vo d runOne erat on() {
        OneTaskSc duledExecutorManager.t .runOne erat on();
      }
    };
  }

  /**
   * Sc dule t  s ngle  nternally spec f ed task returned by getSc duledTask.
   */
  publ c Sc duledFuture sc dule() {
    return t .sc duleNewTask(
        t .getSc duledTask(),
        t .per od cAct onParams
    );
  }

  /**
   * T  code that t  task executes.
   */
  protected abstract vo d runOne erat on();

  publ c Sc duledExecutorTask getSc duledTask() {
    return sc duledTask;
  }

  @Overr de
  publ c vo d close() throws  OExcept on {
    try {
      shutdown();
    } catch ( nterruptedExcept on e) {
      throw new  OExcept on(e);
    }
  }
}
