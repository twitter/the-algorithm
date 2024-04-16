package com.tw ter.servo.forked

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.logg ng.Logger
 mport com.tw ter.servo.ut l.Except onCounter
 mport com.tw ter.ut l.{Durat on, T  , Local, T  outExcept on}
 mport java.ut l.concurrent.{L nkedBlock ngQueue, T  Un , CountDownLatch}

/**
 * A fork ng act on executor that executes t  act ons  n a separate
 * thread, us ng a bounded queue as t  commun cat on channel.  f t 
 * queue  s full (t  secondary thread  s slow to dra n  ), t n t 
 *  ems w ll be dropped rat r than enqueued.
 */
class QueueExecutor(maxQueueS ze:  nt, stats: StatsRece ver) extends Forked.Executor {
  pr vate val forkExcept onsCounter = new Except onCounter(stats)
  pr vate val enqueuedCounter = stats.counter("forked_act ons_enqueued")
  pr vate val droppedCounter = stats.counter("forked_act ons_dropped")
  pr vate val log = Logger.get("Forked.QueueExecutor")

  @volat le pr vate var  sStopped = false
  pr vate val releaseCountDownLatch = new CountDownLatch(1)
  pr vate val queue = new L nkedBlock ngQueue[() => Un ](maxQueueS ze)
  pr vate val thread = new Thread {
    overr de def run(): Un  = {
      wh le (! sStopped) {
        try {
          queue.take()()
        } catch {
          //  gnore  nterrupts from ot r threads
          case _:  nterruptedExcept on =>
          // TODO: handle fatal errors more ser ously
          case e: Throwable =>
            forkExcept onsCounter(e)
            log.error(e, "Execut ng queued act on")
        }
      }
      releaseCountDownLatch.countDown()
    }
  }

  thread.setDaemon(true)
  thread.start()

  /**
   *  nterrupts t  thread and d rects   to stop process ng. T 
   *  thod w ll not return unt l t  process ng thread has f n s d
   * or t  t  out occurs. Ok to call mult ple t  s.
   */
  def release(t  out: Durat on): Un  = {
     f (! sStopped) {
       sStopped = true
      thread. nterrupt()
      releaseCountDownLatch.awa (t  out. nM ll seconds, T  Un .M LL SECONDS) || {
        throw new T  outExcept on(t  out.toStr ng)
      }
    }
  }

  /**
   * Blocks unt l all t   ems currently  n t  queue have been
   * executed, or t  t  out occurs. Mostly useful dur ng test ng.
   */
  def wa ForQueueToDra n(t  out: Durat on): Un  = {
    val latch = new CountDownLatch(1)
    val start = T  .now
    queue.offer(() => latch.countDown(), t  out. nM ll seconds, T  Un .M LL SECONDS)
    val rema n ng = t  out - (T  .now - start)
    latch.awa (rema n ng. nM ll seconds, T  Un .M LL SECONDS) || {
      throw new T  outExcept on(rema n ng.toStr ng)
    }
  }

  /**
   * Queue t  act on for execut on  n t  object's thread.
   */
  def apply(act on: () => Un ) =
     f (queue.offer(Local.closed(act on)))
      enqueuedCounter. ncr()
    else
      droppedCounter. ncr()
}
