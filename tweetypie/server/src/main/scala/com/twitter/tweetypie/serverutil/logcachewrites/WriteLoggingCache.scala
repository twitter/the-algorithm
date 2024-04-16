package com.tw ter.t etyp e.serverut l.logcac wr es

 mport com.tw ter.servo.cac .C cksum
 mport com.tw ter.servo.cac .Cac Wrapper
 mport com.tw ter.ut l.Future
 mport com.tw ter.ut l.logg ng.Logger
 mport scala.ut l.control.NonFatal

tra  Wr eLogg ngCac [K, V] extends Cac Wrapper[K, V] {
  // Use getClass so   can see wh ch  mple ntat on  s actually fa l ng.
  pr vate[t ] lazy val logFa lureLogger = Logger(getClass)

  def selectKey(k: K): Boolean
  def select(k: K, v: V): Boolean
  def log(act on: Str ng, k: K, v: Opt on[V]): Un 

  def safeLog(act on: Str ng, k: K, v: Opt on[V]): Un  =
    try {
      log(act on, k, v)
    } catch {
      case NonFatal(e) =>
        // T  except on occurred  n logg ng, and   don't want to fa l t 
        // request w h t  logg ng fa lure  f t  happens, so log   and carry
        // on.
        logFa lureLogger.error("Logg ng cac  wr e", e)
    }

  overr de def add(k: K, v: V): Future[Boolean] =
    // Call t  select on funct on before do ng t  work. S nce  's h ghly
    // l kely that t  Future w ll succeed,  's c aper to call t  funct on
    // before   make t  call so that   can avo d creat ng t  callback and
    // attach ng   to t  Future  f   would not log.
     f (select(k, v)) {
      underly ngCac .add(k, v).onSuccess(r =>  f (r) safeLog("add", k, So (v)))
    } else {
      underly ngCac .add(k, v)
    }

  overr de def c ckAndSet(k: K, v: V, c cksum: C cksum): Future[Boolean] =
     f (select(k, v)) {
      underly ngCac .c ckAndSet(k, v, c cksum).onSuccess(r =>  f (r) safeLog("cas", k, So (v)))
    } else {
      underly ngCac .c ckAndSet(k, v, c cksum)
    }

  overr de def set(k: K, v: V): Future[Un ] =
     f (select(k, v)) {
      underly ngCac .set(k, v).onSuccess(_ => safeLog("set", k, So (v)))
    } else {
      underly ngCac .set(k, v)
    }

  overr de def replace(k: K, v: V): Future[Boolean] =
     f (select(k, v)) {
      underly ngCac .replace(k, v).onSuccess(r =>  f (r) safeLog("replace", k, So (v)))
    } else {
      underly ngCac .replace(k, v)
    }

  overr de def delete(k: K): Future[Boolean] =
     f (selectKey(k)) {
      underly ngCac .delete(k).onSuccess(r =>  f (r) safeLog("delete", k, None))
    } else {
      underly ngCac .delete(k)
    }
}
