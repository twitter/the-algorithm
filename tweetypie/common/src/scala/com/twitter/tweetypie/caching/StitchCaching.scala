package com.tw ter.t etyp e.cach ng

 mport com.tw ter.st ch.St ch

/**
 * Apply cach ng to a [[St ch]] funct on.
 *
 * @see Cac Result for more  nformat on about t  semant cs
 *    mple nted  re.
 */
class St chCach ng[K, V](operat ons: Cac Operat ons[K, V], repo: K => St ch[V])
    extends (K => St ch[V]) {

  pr vate[t ] val st chOps = new St chCac Operat ons(operat ons)

  overr de def apply(key: K): St ch[V] =
    st chOps.get(key).flatMap {
      case Cac Result.Fresh(value) =>
        St ch.value(value)

      case Cac Result.Stale(staleValue) =>
        St chAsync(repo(key).flatMap(refres d => st chOps.set(key, refres d)))
          .map(_ => staleValue)

      case Cac Result.M ss =>
        repo(key)
          .applyEffect(value => St chAsync(st chOps.set(key, value)))

      case Cac Result.Fa lure(_) =>
        //  n t  case of fa lure,   don't attempt to wr e back to
        // cac , because cac  fa lure usually  ans commun cat on
        // fa lure, and send ng more requests to t  cac  that holds
        // t  value for t  key could make t  s uat on worse.
        repo(key)
    }
}
