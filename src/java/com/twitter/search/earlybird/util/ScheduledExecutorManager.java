package com.tw ter.search.earlyb rd.ut l;

 mport java.ut l.concurrent.Sc duledExecutorServ ce;
 mport java.ut l.concurrent.Sc duledFuture;
 mport java.ut l.concurrent.T  Un ;

 mport org.slf4j.Logger;
 mport org.slf4j.LoggerFactory;

 mport com.tw ter.common.ut l.Clock;
 mport com.tw ter.search.common. tr cs.SearchCounter;
 mport com.tw ter.search.common. tr cs.SearchStatsRece ver;
 mport com.tw ter.search.earlyb rd.except on.Cr  calExcept onHandler;

/**
 * Base class for classes that run per od c tasks.
 */
publ c abstract class Sc duledExecutorManager {
  pr vate stat c f nal Logger LOG = LoggerFactory.getLogger(Sc duledExecutorManager.class);
  pr vate stat c f nal long SHUTDOWN_WA T_ NTERVAL_SEC = 30;

  publ c stat c f nal Str ng SCHEDULED_EXECUTOR_TASK_PREF X = "sc duled_executor_task_";

  pr vate f nal Str ng na ;
  pr vate f nal Sc duledExecutorServ ce executor;

  pr vate f nal ShutdownWa T  Params shutdownWa T  Params;

  pr vate f nal SearchCounter  erat onCounter;
  pr vate f nal SearchStatsRece ver searchStatsRece ver;

  protected f nal Cr  calExcept onHandler cr  calExcept onHandler;
  pr vate f nal Clock clock;

  protected boolean shouldLog = true;

  publ c Sc duledExecutorManager(
      Sc duledExecutorServ ce executor,
      ShutdownWa T  Params shutdownWa T  Params,
      SearchStatsRece ver searchStatsRece ver,
      Cr  calExcept onHandler cr  calExcept onHandler,
      Clock clock) {
    t (executor, shutdownWa T  Params, searchStatsRece ver, null,
        cr  calExcept onHandler, clock);
  }

  Sc duledExecutorManager(
      Sc duledExecutorServ ce executor,
      ShutdownWa T  Params shutdownWa T  Params,
      SearchStatsRece ver searchStatsRece ver,
      SearchCounter  erat onCounter,
      Cr  calExcept onHandler cr  calExcept onHandler,
      Clock clock) {
    t .na  = getClass().getS mpleNa ();
    t .executor = executor;
    t .cr  calExcept onHandler = cr  calExcept onHandler;
    t .shutdownWa T  Params = shutdownWa T  Params;

     f ( erat onCounter != null) {
      t . erat onCounter =  erat onCounter;
    } else {
      t . erat onCounter = searchStatsRece ver.getCounter(SCHEDULED_EXECUTOR_TASK_PREF X + na );
    }

    t .searchStatsRece ver = searchStatsRece ver;
    t .clock = clock;
  }

  /**
   * Sc dule a task.
   */
  protected f nal Sc duledFuture sc duleNewTask(
      Sc duledExecutorTask task,
      Per od cAct onParams per od cAct onParams) {
    long  nterval = per od cAct onParams.get ntervalDurat on();
    T  Un  t  Un  = per od cAct onParams.get ntervalUn ();
    long  n  alDelay = per od cAct onParams.get n  alDelayDurat on();

     f ( nterval <= 0) {
      Str ng  ssage = Str ng.format(
          "Not sc dul ng manager %s for wrong  nterval %d %s", na ,  nterval, t  Un );
      LOG.error( ssage);
      throw new UnsupportedOperat onExcept on( ssage);
    }

     f (shouldLog) {
      LOG. nfo("Sc dul ng to run {} every {} {} w h {}", na ,  nterval, t  Un ,
              per od cAct onParams.getDelayType());
    }
    f nal Sc duledFuture sc duledFuture;
     f (per od cAct onParams. sF xedDelay()) {
      sc duledFuture = executor.sc duleW hF xedDelay(task,  n  alDelay,  nterval, t  Un );
    } else {
      sc duledFuture = executor.sc duleAtF xedRate(task,  n  alDelay,  nterval, t  Un );
    }
    return sc duledFuture;
  }

  /**
   * Shutdown everyth ng that's runn ng w h t  executor.
   */
  publ c boolean shutdown() throws  nterruptedExcept on {
    LOG. nfo("Start shutt ng down {}.", na );
    executor.shutdownNow();

    boolean term nated = false;
    long wa Seconds = shutdownWa T  Params.getWa Un ().toSeconds(
        shutdownWa T  Params.getWa Durat on()
    );

     f (wa Seconds == 0) {
      LOG. nfo("Not wa  ng at all for {}, wa  t    s set to zero.", na );
    } else {
      wh le (!term nated && wa Seconds > 0) {
        long wa T   = Math.m n(wa Seconds, SHUTDOWN_WA T_ NTERVAL_SEC);
        term nated = executor.awa Term nat on(wa T  , T  Un .SECONDS);
        wa Seconds -= wa T  ;

         f (!term nated) {
          LOG. nfo("St ll shutt ng down {} ...", na );
        }
      }
    }

    LOG. nfo("Done shutt ng down {}, term nated: {}", na , term nated);

    shutdownComponent();
    return term nated;
  }

  protected Sc duledExecutorServ ce getExecutor() {
    return executor;
  }

  publ c f nal Str ng getNa () {
    return na ;
  }

  publ c SearchCounter get erat onCounter() {
    return  erat onCounter;
  }

  protected f nal SearchStatsRece ver getSearchStatsRece ver() {
    return searchStatsRece ver;
  }

  // Overr de  f   need to shutdown add  onal serv ces.
  protected vo d shutdownComponent() {
  }
}
