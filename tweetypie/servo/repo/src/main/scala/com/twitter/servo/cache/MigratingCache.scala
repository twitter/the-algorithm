package com.tw ter.servo.cac 

 mport com.tw ter.f nagle.stats.NullStatsRece ver
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.ut l.Durat on
 mport com.tw ter.ut l.Future
 mport com.tw ter.ut l.Return
 mport com.tw ter.ut l.Throw

/**
 * M grat ngReadCac  supports a gradual m grat on from one cac  to anot r. Reads from t 
 * cac  are compared to reads from t  darkCac  and new values are wr ten to t  darkCac 
 *  f necessary.
 */
class M grat ngReadCac [K, V](
  cac : ReadCac [K, V],
  darkCac : Cac [K, V],
  statsRece ver: StatsRece ver = NullStatsRece ver)
    extends ReadCac [K, V] {

  pr vate[t ] val scopedStatsRece ver = statsRece ver.scope("m grat ng_read_cac ")
  pr vate[t ] val getScope = scopedStatsRece ver.scope("get")
  pr vate[t ] val getM smatc dResultsCounter = getScope.counter("m smatc d_results")
  pr vate[t ] val getM ss ngResultsCounter = getScope.counter("m ss ng_results")
  pr vate[t ] val getUnexpectedResultsCounter = getScope.counter("unexpected_results")
  pr vate[t ] val getMatch ngResultsCounter = getScope.counter("match ng_results")

  pr vate[t ] val getW hC cksumScope = scopedStatsRece ver.scope("get_w h_c ksum")
  pr vate[t ] val getW hC cksumM smatc dResultsCounter =
    getW hC cksumScope.counter("m smatc d_results")
  pr vate[t ] val getW hC cksumM ss ngResultsCounter =
    getW hC cksumScope.counter("m ss ng_results")
  pr vate[t ] val getW hC cksumUnexpectedResultsCounter =
    getW hC cksumScope.counter("unexpected_results")
  pr vate[t ] val getW hC cksumMatch ngResultsCounter =
    getW hC cksumScope.counter("match ng_results")

  overr de def get(keys: Seq[K]): Future[KeyValueResult[K, V]] = {
    cac .get(keys) onSuccess { result =>
      darkCac .get(keys) onSuccess { darkResult =>
        keys foreach { k =>
          (result(k), darkResult(k)) match {
            // compare values, set  f t y d ffer
            case (Return(So (v)), Return(So (dv)))  f (v != dv) =>
              getM smatc dResultsCounter. ncr()
              darkCac .set(k, v)
            // set a value  f m ss ng
            case (Return(So (v)), Return.None | Throw(_)) =>
              getM ss ngResultsCounter. ncr()
              darkCac .set(k, v)
            // remove  f necessary
            case (Return.None, Return(So (_)) | Throw(_)) =>
              getUnexpectedResultsCounter. ncr()
              darkCac .delete(k)
            // do noth ng ot rw se
            case _ =>
              getMatch ngResultsCounter. ncr()
              ()
          }
        }
      }
    }
  }

  overr de def getW hC cksum(keys: Seq[K]): Future[CsKeyValueResult[K, V]] = {
    cac .getW hC cksum(keys) onSuccess { result =>
      // no po nt  n t  getW hC cksum from t  darkCac 
      darkCac .get(keys) onSuccess { darkResult =>
        keys foreach { k =>
          (result(k), darkResult(k)) match {
            // compare values, set  f t y d ffer
            case (Return(So ((Return(v), _))), Return(So (dv)))  f (v != dv) =>
              getW hC cksumM smatc dResultsCounter. ncr()
              darkCac .set(k, v)
            // set a value  f m ss ng
            case (Return(So ((Return(v), _))), Return.None | Throw(_)) =>
              getW hC cksumM ss ngResultsCounter. ncr()
              darkCac .set(k, v)
            // remove  f necessary
            case (Return.None, Return(So (_)) | Throw(_)) =>
              getW hC cksumUnexpectedResultsCounter. ncr()
              darkCac .delete(k)
            // do noth ng ot rw se
            case _ =>
              getW hC cksumMatch ngResultsCounter. ncr()
              ()
          }
        }
      }
    }
  }

  overr de def release(): Un  = {
    cac .release()
    darkCac .release()
  }
}

/**
 * M grat ngCac  supports a gradual m grat on from one cac  to anot r. Wr es to t  cac 
 * are propogated to t  darkCac . Reads from t  cac  are compared to reads from t  darkCac 
 * and new values are wr ten to t  darkCac   f necessary.
 *
 * Wr es to t  darkCac  are not lock ng wr es, so t re  s so  r sk of  ncons stenc es from
 * race cond  ons. Ho ver, wr es to t  darkCac  only occur  f t y succeed  n t  cac , so
 *  f a c ckAndSet fa ls, for example, no wr e  s  ssued to t  darkCac .
 */
class M grat ngCac [K, V](
  cac : Cac [K, V],
  darkCac : Cac [K, V],
  statsRece ver: StatsRece ver = NullStatsRece ver)
    extends M grat ngReadCac (cac , darkCac , statsRece ver)
    w h Cac [K, V] {
  overr de def add(key: K, value: V): Future[Boolean] = {
    cac .add(key, value) onSuccess { wasAdded =>
       f (wasAdded) {
        darkCac .set(key, value)
      }
    }
  }

  overr de def c ckAndSet(key: K, value: V, c cksum: C cksum): Future[Boolean] = {
    cac .c ckAndSet(key, value, c cksum) onSuccess { wasSet =>
       f (wasSet) {
        darkCac .set(key, value)
      }
    }
  }

  overr de def set(key: K, value: V): Future[Un ] = {
    cac .set(key, value) onSuccess { _ =>
      darkCac .set(key, value)
    }
  }

  overr de def replace(key: K, value: V): Future[Boolean] = {
    cac .replace(key, value) onSuccess { wasReplaced =>
       f (wasReplaced) {
        darkCac .set(key, value)
      }
    }
  }

  overr de def delete(key: K): Future[Boolean] = {
    cac .delete(key) onSuccess { wasDeleted =>
       f (wasDeleted) {
        darkCac .delete(key)
      }
    }
  }
}

/**
 * L ke M grat ngCac  but for TtlCac s
 */
class M grat ngTtlCac [K, V](
  cac : TtlCac [K, V],
  darkCac : TtlCac [K, V],
  ttl: (K, V) => Durat on)
    extends M grat ngReadCac (cac , new TtlCac ToCac (darkCac , ttl))
    w h TtlCac [K, V] {
  overr de def add(key: K, value: V, ttl: Durat on): Future[Boolean] = {
    cac .add(key, value, ttl) onSuccess { wasAdded =>
       f (wasAdded) {
        darkCac .set(key, value, ttl)
      }
    }
  }

  overr de def c ckAndSet(key: K, value: V, c cksum: C cksum, ttl: Durat on): Future[Boolean] = {
    cac .c ckAndSet(key, value, c cksum, ttl) onSuccess { wasSet =>
       f (wasSet) {
        darkCac .set(key, value, ttl)
      }
    }
  }

  overr de def set(key: K, value: V, ttl: Durat on): Future[Un ] = {
    cac .set(key, value, ttl) onSuccess { _ =>
      darkCac .set(key, value, ttl)
    }
  }

  overr de def replace(key: K, value: V, ttl: Durat on): Future[Boolean] = {
    cac .replace(key, value, ttl) onSuccess { wasReplaced =>
       f (wasReplaced) {
        darkCac .set(key, value, ttl)
      }
    }
  }

  overr de def delete(key: K): Future[Boolean] = {
    cac .delete(key) onSuccess { wasDeleted =>
       f (wasDeleted) {
        darkCac .delete(key)
      }
    }
  }

  overr de def release(): Un  = {
    cac .release()
    darkCac .release()
  }
}

/**
 * A M grat ngTtlCac  for  mcac s,  mple nt ng a m grat ng  ncr and decr.  Race cond  ons
 * are poss ble and may prevent t  counts from be ng perfectly synchron zed.
 */
class M grat ng mcac (
  cac :  mcac ,
  darkCac :  mcac ,
  ttl: (Str ng, Array[Byte]) => Durat on)
    extends M grat ngTtlCac [Str ng, Array[Byte]](cac , darkCac , ttl)
    w h  mcac  {
  def  ncr(key: Str ng, delta: Long = 1): Future[Opt on[Long]] = {
    cac . ncr(key, delta) onSuccess {
      case None =>
        darkCac .delete(key)

      case So (value) =>
        darkCac . ncr(key, delta) onSuccess {
          case So (`value`) => // sa  value!
          case _ =>
            val b = value.toStr ng.getBytes
            darkCac .set(key, b, ttl(key, b))
        }
    }
  }

  def decr(key: Str ng, delta: Long = 1): Future[Opt on[Long]] = {
    cac .decr(key, delta) onSuccess {
      case None =>
        darkCac .delete(key)

      case So (value) =>
        darkCac .decr(key, delta) onSuccess {
          case So (`value`) => // sa  value!
          case _ =>
            val b = value.toStr ng.getBytes
            darkCac .set(key, b, ttl(key, b))
        }
    }
  }
}
