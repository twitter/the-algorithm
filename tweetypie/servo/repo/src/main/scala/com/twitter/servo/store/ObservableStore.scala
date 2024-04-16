package com.tw ter.servo.store

 mport com.tw ter.f nagle.stats.{StatsRece ver, Stat}
 mport com.tw ter.servo.ut l.{Except onCounter, Logar hm callyBucketedT  r}
 mport com.tw ter.ut l.Future

class StoreObserver(statsRece ver: StatsRece ver) {
  protected[t ] val except onCounter = new Except onCounter(statsRece ver)

  def t  [T](f: => Future[T]) = {
    Stat.t  Future(statsRece ver.stat(Logar hm callyBucketedT  r.LatencyStatNa ))(f)
  }

  def except on(ts: Throwable*): Un  = except onCounter(ts)
}

class ObservableStore[K, V](underly ng: Store[K, V], statsRece ver: StatsRece ver)
    extends Store[K, V] {
  protected[t ] val observer = new StoreObserver(statsRece ver)

  overr de def create(value: V) = observer.t   {
    underly ng.create(value) onFa lure { observer.except on(_) }
  }

  overr de def update(value: V) = observer.t   {
    underly ng.update(value) onFa lure { observer.except on(_) }
  }

  overr de def destroy(key: K) = observer.t   {
    underly ng.destroy(key) onFa lure { observer.except on(_) }
  }
}
