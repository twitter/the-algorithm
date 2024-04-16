package com.tw ter.ann.hnsw

 mport com.google.common.annotat ons.V s bleForTest ng
 mport com.tw ter.ann.common.Embedd ngType._
 mport com.tw ter.ann.common. tr c.toThr ft
 mport com.tw ter.ann.common._
 mport com.tw ter.ann.common.thr ftscala.D stance tr c
 mport com.tw ter.ann.hnsw.Hnsw ndex.RandomProv der
 mport com.tw ter.ut l.Future
 mport java.ut l.Random
 mport java.ut l.concurrent.ConcurrentHashMap
 mport java.ut l.concurrent.ThreadLocalRandom
 mport java.ut l.concurrent.locks.Lock
 mport java.ut l.concurrent.locks.ReentrantLock
 mport scala.collect on.JavaConverters._

pr vate[hnsw] object Hnsw {
  pr vate[hnsw] def apply[T, D <: D stance[D]](
    d  ns on:  nt,
     tr c:  tr c[D],
    efConstruct on:  nt,
    maxM:  nt,
    expectedEle nts:  nt,
    futurePool: ReadWr eFuturePool,
     dEmbedd ngMap:  dEmbedd ngMap[T]
  ): Hnsw[T, D] = {
    val randomProv der = new RandomProv der {
      overr de def get(): Random = ThreadLocalRandom.current()
    }
    val d stFn =
      D stanceFunct onGenerator( tr c, (key: T) =>  dEmbedd ngMap.get(key))
    val  nternal ndex = new Hnsw ndex[T, Embedd ngVector](
      d stFn. ndex,
      d stFn.query,
      efConstruct on,
      maxM,
      expectedEle nts,
      randomProv der
    )
    new Hnsw[T, D](
      d  ns on,
       tr c,
       nternal ndex,
      futurePool,
       dEmbedd ngMap,
      d stFn.shouldNormal ze,
      LockedAccess.apply(expectedEle nts)
    )
  }
}

pr vate[hnsw] object LockedAccess {
  protected[hnsw] def apply[T](expectedEle nts:  nt): LockedAccess[T] =
    DefaultLockedAccess(new ConcurrentHashMap[T, Lock](expectedEle nts))
  protected[hnsw] def apply[T](): LockedAccess[T] =
    DefaultLockedAccess(new ConcurrentHashMap[T, Lock]())
}

pr vate[hnsw] case class DefaultLockedAccess[T](locks: ConcurrentHashMap[T, Lock])
    extends LockedAccess[T] {
  overr de def lockProv der( em: T) = locks.compute fAbsent( em, (_: T) => new ReentrantLock())
}

pr vate[hnsw] tra  LockedAccess[T] {
  protected def lockProv der( em: T): Lock
  def lock[K]( em: T)(fn: => K): K = {
    val lock = lockProv der( em)
    lock.lock()
    try {
      fn
    } f nally {
      lock.unlock()
    }
  }
}

@V s bleForTest ng
pr vate[hnsw] class Hnsw[T, D <: D stance[D]](
  d  ns on:  nt,
   tr c:  tr c[D],
  hnsw ndex: Hnsw ndex[T, Embedd ngVector],
  readWr eFuturePool: ReadWr eFuturePool,
   dEmbedd ngMap:  dEmbedd ngMap[T],
  shouldNormal ze: Boolean,
  lockedAccess: LockedAccess[T] = LockedAccess.apply[T]())
    extends Appendable[T, HnswParams, D]
    w h Queryable[T, HnswParams, D]
    w h Updatable[T] {
  overr de def append(ent y: Ent yEmbedd ng[T]): Future[Un ] = {
    readWr eFuturePool.wr e {
      val  ndexD  ns on = ent y.embedd ng.length
      assert(
        toThr ft( tr c) == D stance tr c.Ed D stance ||  ndexD  ns on == d  ns on,
        s"D  ns on m smatch for  ndex(${ ndexD  ns on}) and embedd ng($d  ns on)"
      )

      lockedAccess.lock(ent y. d) {
        // To make t  thread-safe,   are us ng ConcurrentHashMap#put fAbsent underneath,
        // so  f t re  s a pre-ex st ng  em, put() w ll return so th ng that  s not null
        val embedd ng =  dEmbedd ngMap.put fAbsent(ent y. d, updatedEmbedd ng(ent y.embedd ng))

         f (embedd ng == null) { // New ele nt -  nsert  nto t   ndex
          hnsw ndex. nsert(ent y. d)
        } else { // Ex st ng ele nt - update t  embedd ng and graph structure
          throw new  llegalDupl cate nsertExcept on(
            "Append  thod does not perm  dupl cates (try us ng update  thod): " + ent y. d)
        }
      }
    } onFa lure { e =>
      Future.except on(e)
    }
  }

  overr de def toQueryable: Queryable[T, HnswParams, D] = t 

  overr de def query(
    embedd ng: Embedd ngVector,
    numOfNe ghb s:  nt,
    runt  Params: HnswParams
  ): Future[L st[T]] = {
    queryW hD stance(embedd ng, numOfNe ghb s, runt  Params)
      .map(_.map(_.ne ghbor))
  }

  overr de def queryW hD stance(
    embedd ng: Embedd ngVector,
    numOfNe ghb s:  nt,
    runt  Params: HnswParams
  ): Future[L st[Ne ghborW hD stance[T, D]]] = {
    val  ndexD  ns on = embedd ng.length
    assert(
      toThr ft( tr c) == D stance tr c.Ed D stance ||  ndexD  ns on == d  ns on,
      s"D  ns on m smatch for  ndex(${ ndexD  ns on}) and embedd ng($d  ns on)"
    )
    readWr eFuturePool.read {
      hnsw ndex
        .searchKnn(updatedEmbedd ng(embedd ng), numOfNe ghb s, runt  Params.ef)
        .asScala
        .map { nn =>
          Ne ghborW hD stance(
            nn.get em,
             tr c.fromAbsoluteD stance(nn.getD stance)
          )
        }
        .toL st
    }
  }

  pr vate[t ] def updatedEmbedd ng(embedd ng: Embedd ngVector): Embedd ngVector = {
     f (shouldNormal ze) {
       tr cUt l.norm(embedd ng)
    } else {
      embedd ng
    }
  }

  def get ndex: Hnsw ndex[T, Embedd ngVector] = hnsw ndex
  def getD  n:  nt = d  ns on
  def get tr c:  tr c[D] =  tr c
  def get dEmbedd ngMap:  dEmbedd ngMap[T] =  dEmbedd ngMap
  overr de def update(
    ent y: Ent yEmbedd ng[T]
  ): Future[Un ] = {
    readWr eFuturePool.wr e {
      val  ndexD  ns on = ent y.embedd ng.length
      assert(
        toThr ft( tr c) == D stance tr c.Ed D stance ||  ndexD  ns on == d  ns on,
        s"D  ns on m smatch for  ndex(${ ndexD  ns on}) and embedd ng($d  ns on)"
      )

      lockedAccess.lock(ent y. d) {
        val embedd ng =  dEmbedd ngMap.put(ent y. d, updatedEmbedd ng(ent y.embedd ng))
         f (embedd ng == null) { // New ele nt -  nsert  nto t   ndex
          hnsw ndex. nsert(ent y. d)
        } else { // Ex st ng ele nt - update t  embedd ng and graph structure
          hnsw ndex.re nsert(ent y. d);
        }
      }
    } onFa lure { e =>
      Future.except on(e)
    }
  }
}
