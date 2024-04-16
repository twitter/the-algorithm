package com.tw ter.search.earlyb rd.part  on;

 mport java.ut l.concurrent.ConcurrentL nkedDeque;
 mport java.ut l.concurrent.atom c.Atom cLong;

 mport com.tw ter.search.common. tr cs.SearchLongGauge;
 mport com.tw ter.search.common. tr cs.SearchRateCounter;

/**
 * A queue w h  tr cs on s ze, enqueue rate and dequeue rate.
 */
publ c class  nstru ntedQueue<T> {
  pr vate f nal SearchRateCounter enqueueRate;
  pr vate f nal SearchRateCounter dequeueRate;
  pr vate f nal Atom cLong queueS ze = new Atom cLong();

  pr vate f nal ConcurrentL nkedDeque<T> queue;

  publ c  nstru ntedQueue(Str ng statsPref x) {
    SearchLongGauge.export(statsPref x + "_s ze", queueS ze);
    enqueueRate = SearchRateCounter.export(statsPref x + "_enqueue");
    dequeueRate = SearchRateCounter.export(statsPref x + "_dequeue");

    queue = new ConcurrentL nkedDeque<>();
  }

  /**
   * Adds a new ele nt to t  queue.
   */
  publ c vo d add(T tve) {
    queue.add(tve);
    enqueueRate. ncre nt();
    queueS ze. ncre ntAndGet();
  }

  /**
   * Returns t  f rst ele nt  n t  queue.  f t  queue  s empty, {@code null}  s returned.
   */
  publ c T poll() {
    T tve = queue.poll();
     f (tve != null) {
      dequeueRate. ncre nt();
      queueS ze.decre ntAndGet();
    }
    return tve;
  }

  publ c long getQueueS ze() {
    return queueS ze.get();
  }
}
