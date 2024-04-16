package com.tw ter.servo.repos ory

 mport com.tw ter.servo.cac ._
 mport com.tw ter.ut l._

object ResponseCach ngKeyValueRepos ory {

  /**
   * An cac  f lter that excludes cac d future responses that are already fulf lled.
   * Us ng t  pol cy ensures that t  repos ory w ll only ever have one outstand ng request for t  sa   em.
   */
  def refreshSat sf ed[K, V]: (K, Future[Opt on[V]]) => Boolean =
    (_, v) => v. sDef ned

  /**
   * An cac  f lter that excludes cac d future response that are fa lures
   */
  def refreshFa lures[K, V]: (K, Future[Opt on[V]]) => Boolean =
    (_, v) =>
      v.poll match {
        case So (t) => t. sThrow
        case None => false
      }
}

/**
 * A repos ory that cac s( n-process) Future responses from an underly ng KeyValueRepos ory.
 * Each t   a request for a key  s made, t  repos ory f rst c cks
 *  f any Future responses for that key are already cac d.
 *  f so, t  Future response from cac   s returned.
 *  f not, a new Prom se  s placed  n to cac ,
 * t  underly ng repos ory  s quer ed to fulf ll t  Prom se,
 * and t  new Prom se  s returned to t  caller.
 * @param underly ng
 *   t  underly ng KeyValueRepos ory
 * @param cac 
 *   an  nprocess cac  of (future) responses
 * @param newQuery
 *   a funct on wh ch constructs a new query from a query and a set of keys
 * @param observer
 *   a Cac Observer wh ch records t  h s/m sses on t  request cac 
 */
class ResponseCach ngKeyValueRepos ory[Q <: Seq[K], K, V](
  underly ng: KeyValueRepos ory[Q, K, V],
  cac :  nProcessCac [K, Future[Opt on[V]]],
  newQuery: SubqueryBu lder[Q, K],
  observer: Cac Observer = NullCac Observer)
    extends KeyValueRepos ory[Q, K, V] {
  pr vate[t ] def load(query: Q, prom ses: Seq[(K, Prom se[Opt on[V]])]): Un  = {
     f (prom ses.nonEmpty) {
      underly ng(newQuery(prom ses map { case (k, _) => k }, query)) respond {
        case Throw(t) => prom ses foreach { case (_, p) => p.update fEmpty(Throw(t)) }
        case Return(kvr) => prom ses foreach { case (k, p) => p.update fEmpty(kvr(k)) }
      }
    }
  }

  sealed tra  RefreshResult[K, V] {
    def to nterrupt ble: Future[Opt on[V]]
  }

  pr vate case class Cac dResult[K, V](result: Future[Opt on[V]]) extends RefreshResult[K, V] {
    def to nterrupt ble = result. nterrupt ble
  }

  pr vate case class LoadResult[K, V](keyToLoad: K, result: Prom se[Opt on[V]])
      extends RefreshResult[K, V] {
    def to nterrupt ble = result. nterrupt ble
  }

  pr vate[t ] def refresh(key: K): RefreshResult[K, V] =
    synchron zed {
      cac .get(key) match {
        case So (updated) =>
          observer.h (key.toStr ng)
          Cac dResult(updated)
        case None =>
          observer.m ss(key.toStr ng)
          val prom se = new Prom se[Opt on[V]]
          cac .set(key, prom se)
          LoadResult(key, prom se)
      }
    }

  def apply(query: Q): Future[KeyValueResult[K, V]] =
    KeyValueResult.fromSeqFuture(query) {
      val result: Seq[RefreshResult[K, V]] =
        query map { key =>
          cac .get(key) match {
            case So (value) =>
              observer.h (key.toStr ng)
              Cac dResult[K, V](value)
            case None =>
              refresh(key)
          }
        }

      val toLoad = result collect { case LoadResult(k, p) => k -> p }
      load(query, toLoad)

      result map { _.to nterrupt ble }
    }
}
