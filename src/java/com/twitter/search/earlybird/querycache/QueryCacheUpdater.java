package com.tw ter.search.earlyb rd.querycac ;

 mport java.ut l.ArrayL st;
 mport java.ut l.Collect on;
 mport java.ut l. erator;
 mport java.ut l.L st;
 mport java.ut l.concurrent.Sc duledExecutorServ ce;
 mport java.ut l.concurrent.Sc duledFuture;
 mport java.ut l.concurrent.T  Un ;

 mport com.google.common.annotat ons.V s bleForTest ng;
 mport com.google.common.base.Precond  ons;
 mport com.google.common.collect.L sts;

 mport org.slf4j.Logger;
 mport org.slf4j.LoggerFactory;

 mport com.tw ter.common.quant y.Amount;
 mport com.tw ter.common.quant y.T  ;
 mport com.tw ter.common.ut l.Clock;
 mport com.tw ter.dec der.Dec der;
 mport com.tw ter.search.common.concurrent.Sc duledExecutorServ ceFactory;
 mport com.tw ter.search.common. tr cs.SearchCustomGauge;
 mport com.tw ter.search.common. tr cs.SearchStatsRece ver;
 mport com.tw ter.search.earlyb rd.common.userupdates.UserTable;
 mport com.tw ter.search.earlyb rd.except on.Cr  calExcept onHandler;
 mport com.tw ter.search.earlyb rd.factory.QueryCac UpdaterSc duledExecutorServ ce;
 mport com.tw ter.search.earlyb rd.part  on.Seg nt nfo;
 mport com.tw ter.search.earlyb rd.stats.Earlyb rdSearc rStats;
 mport com.tw ter.search.earlyb rd.ut l.Per od cAct onParams;
 mport com.tw ter.search.earlyb rd.ut l.Sc duledExecutorManager;
 mport com.tw ter.search.earlyb rd.ut l.ShutdownWa T  Params;

/**
 * Class to manage t  sc duler serv ce and all t  update tasks. Through t 
 * class, update tasks are created and sc duled, canceled and removed.
 *
 * T  class  s not thread-safe.
 */
@V s bleForTest ng
f nal class QueryCac Updater extends Sc duledExecutorManager {
  pr vate stat c f nal Logger LOG = LoggerFactory.getLogger(QueryCac Updater.class);

  pr vate f nal L st<Task> tasks;
  pr vate f nal Earlyb rdSearc rStats searc rStats;
  pr vate f nal Dec der dec der;
  pr vate f nal UserTable userTable;
  pr vate f nal Clock clock;

  @V s bleForTest ng
  stat c f nal class Task {
    @V s bleForTest ng publ c f nal QueryCac UpdateTask updateTask;
    @V s bleForTest ng publ c f nal Sc duledFuture future;

    pr vate Task(QueryCac UpdateTask updateTask, Sc duledFuture future) {
      t .updateTask = updateTask;
      t .future = future;
    }
  }

  publ c QueryCac Updater(Collect on<QueryCac F lter> cac F lters,
                           Sc duledExecutorServ ceFactory updaterSc duledExecutorServ ceFactory,
                           UserTable userTable,
                           SearchStatsRece ver searchStatsRece ver,
                           Earlyb rdSearc rStats searc rStats,
                           Dec der dec der,
                           Cr  calExcept onHandler cr  calExcept onHandler,
                           Clock clock) {
    super(updaterSc duledExecutorServ ceFactory.bu ld("QueryCac UpdateThread-%d", true),
        ShutdownWa T  Params. m d ately(), searchStatsRece ver,
        cr  calExcept onHandler, clock);
    Precond  ons.c ckNotNull(cac F lters);
    Precond  ons.c ckArgu nt(getExecutor()  nstanceof QueryCac UpdaterSc duledExecutorServ ce,
        getExecutor().getClass());

    t .searc rStats = searc rStats;
    t .dec der = dec der;
    t .userTable = userTable;
    t .clock = clock;

    shouldLog = false;
    // One update task per <query, seg nt>
    tasks = L sts.newArrayL stW hCapac y(cac F lters.s ze() * 20);

    SearchCustomGauge.export(
        "querycac _num_tasks",
        tasks::s ze
    );
  }

  /**
   * Create an update task and add   to t  executor
   *
   * @param f lter T  f lter t  task should execute
   * @param seg nt nfo T  seg nt that t  task would be respons ble for
   * @param update nterval t    n m ll seconds bet en success ve updates
   * @param  n  alDelay  ntroduce a delay w n add ng t  task to t  executor
   */
  vo d addTask(QueryCac F lter f lter, Seg nt nfo seg nt nfo,
               Amount<Long, T  > update nterval, Amount<Long, T  >  n  alDelay) {
    Str ng f lterNa  = f lter.getF lterNa ();
    Str ng query = f lter.getQueryStr ng();

    // Create t  task.
    QueryCac UpdateTask qcTask = new QueryCac UpdateTask(
        f lter,
        seg nt nfo,
        userTable,
        update nterval,
         n  alDelay,
        get erat onCounter(),
        searc rStats,
        dec der,
        cr  calExcept onHandler,
        clock);

    long  n  alDelayAsMS =  n  alDelay.as(T  .M LL SECONDS);
    long update ntervalAsMS = update nterval.as(T  .M LL SECONDS);
    Precond  ons.c ckArgu nt(
         n  alDelayAsMS >=  n  alDelay.getValue(), " n  al delay un  granular y too small");
    Precond  ons.c ckArgu nt(
        update ntervalAsMS >= update nterval.getValue(),
        "update  nterval un  granular y too small");

    // Sc dule t  task.
    Sc duledFuture future = sc duleNewTask(qcTask,
        Per od cAct onParams.w h nt alWa AndF xedDelay(
             n  alDelayAsMS, update ntervalAsMS, T  Un .M LL SECONDS
        )
    );

    tasks.add(new Task(qcTask, future));

    LOG.debug("Added a task for f lter [" + f lterNa 
            + "] for seg nt [" + seg nt nfo.getT  Sl ce D()
            + "] w h query [" + query
            + "] update  nterval " + update nterval + " "
            + ( n  alDelay.getValue() == 0 ? "w hout" : "w h " +  n  alDelay)
            + "  n  al delay");

  }

  vo d removeAllTasksForSeg nt(Seg nt nfo seg nt nfo) {
     nt removedTasksCount = 0;
    for ( erator<Task>   = tasks. erator();  .hasNext();) {
      Task task =  .next();
       f (task.updateTask.getT  Sl ce D() == seg nt nfo.getT  Sl ce D()) {
        task.future.cancel(true);
         .remove();
        removedTasksCount += 1;
      }
    }

    LOG. nfo("Removed {} update tasks for seg nt {}.", removedTasksCount,
        seg nt nfo.getT  Sl ce D());
  }

  publ c vo d clearTasks() {
     nt totalTasks = tasks.s ze();
    LOG. nfo("Remov ng {} update tasks for all seg nts.", totalTasks);
    for (Task task : tasks) {
      task.future.cancel(true);
    }
    tasks.clear();
    LOG. nfo("Canceled {} QueryCac  update tasks", totalTasks);
  }

  // Have all tasks run at least once (even  f t y fa led)?
  publ c boolean allTasksRan() {
    boolean allTasksRan = true;
    for (Task task : tasks) {
       f (!task.updateTask.ranOnce()) {
        allTasksRan = false;
        break;
      }
    }

    return allTasksRan;
  }

  // Have all tasks for t  run at least once (even  f t y fa led)?
  publ c boolean allTasksRanForSeg nt(Seg nt nfo seg nt nfo) {
    boolean allTasksRanForSeg nt = true;
    for (Task task : tasks) {
       f ((task.updateTask.getT  Sl ce D() == seg nt nfo.getT  Sl ce D())
          && !task.updateTask.ranOnce()) {
        allTasksRanForSeg nt = false;
        break;
      }
    }

    return allTasksRanForSeg nt;
  }

  /**
   * After startup,   want only one thread to update t  query cac .
   */
  vo d setWorkerPoolS zeAfterStartup() {
    QueryCac UpdaterSc duledExecutorServ ce executor =
        (QueryCac UpdaterSc duledExecutorServ ce) getExecutor();
    executor.setWorkerPoolS zeAfterStartup();
    LOG. nfo("Done sett ng executor core pool s ze to one");
  }

  @Overr de
  protected vo d shutdownComponent() {
    clearTasks();
  }

  //////////////////////////
  // for un  tests only
  //////////////////////////

  /**
   * Returns t  l st of all query cac  updater tasks. T   thod should be used only  n tests.
   */
  @V s bleForTest ng
  L st<Task> getTasksForTest() {
    synchron zed (tasks) {
      return new ArrayL st<>(tasks);
    }
  }

  @V s bleForTest ng
   nt getTasksS ze() {
    synchron zed (tasks) {
      return tasks.s ze();
    }
  }

  @V s bleForTest ng
  boolean tasksConta ns(Task task) {
    synchron zed (tasks) {
      return tasks.conta ns(task);
    }
  }

  @V s bleForTest ng
  publ c Sc duledExecutorServ ce getExecutorForTest() {
    return getExecutor();
  }
}
