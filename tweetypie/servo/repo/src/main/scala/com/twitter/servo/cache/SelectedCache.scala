package com.tw ter.servo.cac 

 mport com.tw ter.ut l.Future

/**
 * Represents mult ple underly ng ReadCac s selected by key at  nvocat on t  .
 */
tra  SelectedReadCac Wrapper[K, V, T  <: ReadCac [K, V]] extends ReadCac [K, V] {

  /** Retr eves t  underly ng cac  for t  g ven key. */
  def underly ngCac (key: K): T 

  /** Retr eves tuples of t  underly ng cac s and t  keys t y apply to. */
  def underly ngCac ForKeys(keys: Seq[K]): Seq[(T , Seq[K])]

  /** Retr eves all underly ng cac s. */
  def underly ngCac s: Seq[T ]

  pr vate[t ] def collectUnderly ng[V2](
    keys: Seq[K]
  )(
    f: (T , Seq[K]) => Future[KeyValueResult[K, V2]]
  ): Future[KeyValueResult[K, V2]] = {
    Future.collect(
      underly ngCac ForKeys(keys) collect {
        case (cac ForKey, keys)  f !keys. sEmpty =>
          f(cac ForKey, keys)
      }
    ) map {
      KeyValueResult.sum(_)
    }
  }

  overr de def get(keys: Seq[K]) = collectUnderly ng(keys) { _.get(_) }
  overr de def getW hC cksum(keys: Seq[K]) = collectUnderly ng(keys) { _.getW hC cksum(_) }

  overr de def release(): Un  = {
    underly ngCac s foreach { _.release() }
  }
}

/**
 * Represents mult ple underly ng Cac s selected by key at  nvocat on t  .
 */
tra  SelectedCac Wrapper[K, V]
    extends Cac [K, V]
    w h SelectedReadCac Wrapper[K, V, Cac [K, V]] {
  overr de def add(key: K, value: V) = underly ngCac (key).add(key, value)

  overr de def c ckAndSet(key: K, value: V, c cksum: C cksum) =
    underly ngCac (key).c ckAndSet(key, value, c cksum)

  overr de def set(key: K, value: V) = underly ngCac (key).set(key, value)

  overr de def replace(key: K, value: V) = underly ngCac (key).replace(key, value)

  overr de def delete(key: K) = underly ngCac (key).delete(key)
}

/**
 * GateSelectedCac   mple nts SelectedCac  to choose bet en two underly ng
 * cac s based on a funct on.
 */
class SelectedCac [K, V](pr mary: Cac [K, V], secondary: Cac [K, V], usePr mary: K => Boolean)
    extends SelectedCac Wrapper[K, V] {
  overr de def underly ngCac (key: K) =  f (usePr mary(key)) pr mary else secondary

  overr de def underly ngCac ForKeys(keys: Seq[K]) = {
    keys part  on (usePr mary) match {
      case (pr maryKeys, secondaryKeys) => Seq((pr mary, pr maryKeys), (secondary, secondaryKeys))
    }
  }

  overr de def underly ngCac s = Seq(pr mary, secondary)
}

/**
 * Factory for SelectedCac   nstances that use a s mple funct on to m grate
 * users from a secondary cac  (funct on returns false) to a pr mary cac 
 * (funct on returns true). Serves a purpose s m lar to Cac Factory, but
 * cannot extend   due to type constra nts.
 *
 * T  funct on  s expected to produce stable results by key over t   to
 * prevent access ng stale cac  entr es due to keys flapp ng bet en t 
 * two cac s.
 */
class SelectedCac Factory[K](
  pr maryFactory: Cac Factory,
  secondaryFactory: Cac Factory,
  usePr mary: K => Boolean) {
  def apply[V](ser al zer: Ser al zer[V], scopes: Str ng*): Cac [K, V] =
    new SelectedCac (
      pr maryFactory[K, V](ser al zer, scopes: _*),
      secondaryFactory[K, V](ser al zer, scopes: _*),
      usePr mary
    )
}
