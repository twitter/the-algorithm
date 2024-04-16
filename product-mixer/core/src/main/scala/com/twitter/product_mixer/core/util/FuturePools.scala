package com.tw ter.product_m xer.core.ut l

 mport com.tw ter.concurrent.Na dPoolThreadFactory
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.ut l.Durat on
 mport com.tw ter.ut l.FuturePool

 mport java.ut l.concurrent.ArrayBlock ngQueue
 mport java.ut l.concurrent.Block ngQueue
 mport java.ut l.concurrent.L nkedBlock ngQueue
 mport java.ut l.concurrent.ThreadPoolExecutor
 mport java.ut l.concurrent.T  Un 

/**
 * Ut l y for mak ng [[FuturePool]] w h f n e thread counts and d fferent work queue opt ons
 * wh le also mon or ng t  s ze of t  work queue that's used.
 *
 * T  only handles t  cases w re t  number of threads are bounded.
 * For unbounded numbers of threads  n a [[FuturePool]] use [[FuturePool. nterrupt bleUnboundedPool]]  nstead.
 */
object FuturePools {

  /**
   * Makes a [[FuturePool]] w h a f xed number of threads and a work queue
   * w h a max mum s ze of `maxWorkQueueS ze`.
   *
   * @note t  [[FuturePool]] returns a fa led [[com.tw ter.ut l.Future]]s conta n ng
   *       [[java.ut l.concurrent.RejectedExecut onExcept on]] w n try ng to enqueue
   *       work w n t  work queue  s full.
   */
  def boundedF xedThreadPool(
    na : Str ng,
    f xedThreadCount:  nt,
    workQueueS ze:  nt,
    statsRece ver: StatsRece ver
  ): FuturePool =
    futurePool(
      na  = na ,
      m nThreads = f xedThreadCount,
      maxThreads = f xedThreadCount,
      keep dleThreadsAl ve = Durat on.Zero,
      workQueue = new ArrayBlock ngQueue[Runnable](workQueueS ze),
      statsRece ver = statsRece ver
    )

  /**
   * Makes a [[FuturePool]] w h a f x number of threads and an unbounded work queue
   *
   * @note S nce t  work queue  s unbounded, t  w ll f ll up  mory  f t  ava lable worker threads can't keep up
   */
  def unboundedF xedThreadPool(
    na : Str ng,
    f xedThreadCount:  nt,
    statsRece ver: StatsRece ver
  ): FuturePool =
    futurePool(
      na  = na ,
      m nThreads = f xedThreadCount,
      maxThreads = f xedThreadCount,
      keep dleThreadsAl ve = Durat on.Zero,
      workQueue = new L nkedBlock ngQueue[Runnable],
      statsRece ver = statsRece ver
    )

  /**
   * Makes a [[FuturePool]] w h t  prov ded thread conf gurat on and
   * who's `workQueue`  s mon ored by a [[com.tw ter.f nagle.stats.Gauge]]
   *
   * @note  f `m nThreads` == `maxThreads` t n t  threadpool has a f xed s ze
   *
   * @param na  na  of t  threadpool
   * @param m nThreads m n mum number of threads  n t  pool
   * @param maxThreads max mum number of threads  n t  pool
   * @param keep dleThreadsAl ve threads that are  dle for t  long w ll be removed from
   *                             t  pool  f t re are more than `m nThreads`  n t  pool.
   *                              f t  pool s ze  s f xed t   s  gnored.
   */
  pr vate def futurePool(
    na : Str ng,
    m nThreads:  nt,
    maxThreads:  nt,
    keep dleThreadsAl ve: Durat on,
    workQueue: Block ngQueue[Runnable],
    statsRece ver: StatsRece ver
  ): FuturePool = {
    val gaugeReference = statsRece ver.addGauge("workQueueS ze")(workQueue.s ze())

    val threadFactory = new Na dPoolThreadFactory(na , makeDaemons = true)

    val executorServ ce =
      new ThreadPoolExecutor(
        m nThreads,
        maxThreads, //  gnored by ThreadPoolExecutor w n an unbounded queue  s prov ded
        keep dleThreadsAl ve. nM ll s,
        T  Un .M LL SECONDS,
        workQueue,
        threadFactory)

    FuturePool. nterrupt ble(executorServ ce)
  }
}
