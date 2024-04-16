package com.tw ter.recos.hose.common

 mport com.tw ter.f nagle.stats.{Stat, StatsRece ver}
 mport com.tw ter.logg ng.Logger
 mport com.tw ter.recos. nternal.thr ftscala.RecosHose ssage
 mport java.ut l.concurrent.Semaphore

/**
 * T  class reads a buffer of edges from t  concurrently l nked queue
 * and  nserts each edge  nto t  recos graph.
 *  f t  queue  s empty t  thread w ll sleep for 100ms and attempt to read from t  queue aga n.
 */
case class BufferedEdgeWr er(
  queue: java.ut l.Queue[Array[RecosHose ssage]],
  queuel m : Semaphore,
  edgeCollector: EdgeCollector,
  statsRece ver: StatsRece ver,
   sRunn ng: () => Boolean)
    extends Runnable {
  val logger = Logger()
  pr vate val queueRemoveCounter = statsRece ver.counter("queueRemove")
  pr vate val queueSleepCounter = statsRece ver.counter("queueSleep")

  def runn ng: Boolean = {
     sRunn ng()
  }

  overr de def run(): Un  = {
    wh le (runn ng) {
      val currentBatch = queue.poll
       f (currentBatch != null) {
        queueRemoveCounter. ncr()
        queuel m .release()
        var   = 0
        Stat.t  (statsRece ver.stat("batchAddEdge")) {
          wh le (  < currentBatch.length) {
            edgeCollector.addEdge(currentBatch( ))
              =   + 1
          }
        }
      } else {
        queueSleepCounter. ncr()
        Thread.sleep(100L)
      }
    }
    logger. nfo(t .getClass.getS mpleNa  + "  s done")
  }
}
