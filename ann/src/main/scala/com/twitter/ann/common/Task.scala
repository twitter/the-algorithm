package com.tw ter.ann.common

 mport com.tw ter.f nagle.stats.Categor z ngExcept onStatsHandler
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.f nagle.trac ng.DefaultTracer
 mport com.tw ter.f nagle.trac ng.Trace
 mport com.tw ter.f nagle.ut l.DefaultT  r
 mport com.tw ter.f nagle.ut l.Rng
 mport com.tw ter. nject.logg ng.MDCKeys
 mport com.tw ter.ut l.Closable
 mport com.tw ter.ut l.Durat on
 mport com.tw ter.ut l.Future
 mport com.tw ter.ut l.T  
 mport com.tw ter.ut l.T  r
 mport com.tw ter.ut l.logg ng.Logg ng
 mport java.ut l.concurrent.atom c.Atom c nteger
 mport org.slf4j.MDC

/**
 * A Task that w ll be sc duled to execute per od cally on every  nterval.  f a task takes
 * longer than an  nterval to complete,   w ll be  m d ately sc duled to run.
 */
tra  Task extends Closable { self: Logg ng =>

  // Exposed  f t   mple ntat on of `task` need to report fa lures
  val exnStatsHandler = new Categor z ngExcept onStatsHandler(categor zer = _ => So ("fa lures"))

  protected val statsRece ver: StatsRece ver
  pr vate val totalTasks = statsRece ver.counter("total")
  pr vate val successfulTasks = statsRece ver.counter("success")
  pr vate val taskLatency = statsRece ver.stat("latency_ms")

  pr vate val act veTasks = new Atom c nteger(0)

  protected[common] val rng: Rng = Rng.threadLocal
  protected[common] val t  r: T  r = DefaultT  r

  @volat le pr vate var taskLoop: Future[Un ] = null

  /** Execute t  task w h bookkeep ng **/
  pr vate def run(): Future[Un ] = {
    totalTasks. ncr()
    act veTasks.getAnd ncre nt()

    val start = T  .now
    val runn ngTask =
      // Setup a new trace root for t  task.   also want logs to conta n
      // t  sa  trace  nformat on f natra populates for requests.
      // See com.tw ter.f natra.thr ft.f lters.Trace dMDCF lter
      Trace.letTracerAndNext d(DefaultTracer) {
        val trace = Trace()
        MDC.put(MDCKeys.Trace d, trace. d.trace d.toStr ng)
        MDC.put(MDCKeys.TraceSampled, trace. d._sampled.getOrElse(false).toStr ng)
        MDC.put(MDCKeys.TraceSpan d, trace. d.span d.toStr ng)

         nfo(s"start ng task ${getClass.toStr ng}")
        task()
          .onSuccess({ _ =>
             nfo(s"completed task ${getClass.toStr ng}")
            successfulTasks. ncr()
          })
          .onFa lure({ e =>
            warn(s"fa led task. ", e)
            exnStatsHandler.record(statsRece ver, e)
          })
      }

    runn ngTask.transform { _ =>
      val elapsed = T  .now - start
      act veTasks.getAndDecre nt()
      taskLatency.add(elapsed. nM ll seconds)

      Future
        .sleep(task nterval)(t  r)
        .before(run())
    }
  }

  // Body of a task to run
  protected def task(): Future[Un ]

  // Task  nterval
  protected def task nterval: Durat on

  /**
   * Start t  task after random j ter
   */
  f nal def j teredStart(): Un  = synchron zed {
     f (taskLoop != null) {
      throw new Runt  Except on(s"task already started")
    } else {
      val j terNs = rng.nextLong(task nterval. nNanoseconds)
      val j ter = Durat on.fromNanoseconds(j terNs)

      taskLoop = Future
        .sleep(j ter)(t  r)
        .before(run())
    }
  }

  /**
   * Start t  task w hout apply ng any delay
   */
  f nal def start m d ately(): Un  = synchron zed {
     f (taskLoop != null) {
      throw new Runt  Except on(s"task already started")
    } else {
      taskLoop = run()
    }
  }

  /**
   * Close t  task. A closed task cannot be restarted.
   */
  overr de def close(deadl ne: T  ): Future[Un ] = {
     f (taskLoop != null) {
      taskLoop.ra se(new  nterruptedExcept on("task closed"))
    }
    Future.Done
  }
}
