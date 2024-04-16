package com.tw ter.search.earlyb rd.part  on;

 mport java.ut l.concurrent.locks.ReentrantLock;

 mport com.google.common.annotat ons.V s bleForTest ng;

/**
 * Lock used to ensure that flush ng does not occur concurrently w h t  gc_before_opt m zat on
 * and post_opt m zat on_rebu lds act ons - see w re   call t  "lock"  thod of t  class.
 *
 * Both coord nated act ons  nclude a full GC  n t m, for reasons descr bed  n that part
 * of t  code. After t  GC, t y wa  unt l  ndex ng has caught up before rejo n ng t  serverset.
 *
 *  f   flush concurrently w h t se act ons,   can pause  ndex ng for a wh le and wa  ng
 * unt l  're caught up can take so  t  , wh ch can affect t   mory state negat vely.
 * For example, t  f rst GC (before opt m zat on)   do so that   have a clean state of  mory
 * before opt m zat on.
 *
 * T  ot r reason   lock before execut ng t  act ons  s because  f   have flush ng that's
 * currently runn ng, once   f n s s,   w ll rejo n t  serverset and that can be follo d by
 * a stop-t -world GC from t  act ons, wh ch w ll affect   success rate.
 */
publ c class Opt m zat onAndFlush ngCoord nat onLock {
  pr vate f nal ReentrantLock lock;

  publ c Opt m zat onAndFlush ngCoord nat onLock() {
    t .lock = new ReentrantLock();
  }

  publ c vo d lock() {
    lock.lock();
  }

  publ c vo d unlock() {
    lock.unlock();
  }

  publ c boolean tryLock() {
    return lock.tryLock();
  }

  @V s bleForTest ng
  publ c boolean hasQueuedThreads() {
    return lock.hasQueuedThreads();
  }
}
