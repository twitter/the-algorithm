package com.tw ter.servo

 mport com.tw ter.ut l.Future

package object repos ory {

  /**
   * Base repos ory type.  Maps a Query to a future Result
   */
  type Repos ory[-Q, +R] = Q => Future[R]

  /**
   * Repos oryF lters can be cha ned onto Repos or es to asynchronously apply transformat ons to
   * Repos ory results.
   */
  type Repos oryF lter[-Q, -R, +S] = (Q, Future[R]) => Future[S]

  type KeyValueResult[K, V] = keyvalue.KeyValueResult[K, V]
  val KeyValueResult = keyvalue.KeyValueResult

  /**
   * A KeyValueRepos ory  s a type of repos ory that handles bulk gets of data.  T  query
   * def nes t  values to fetch, and  s usually made of up of a Seq[K], poss bly w h ot r
   * contextual  nformat on needed to perform t  query.  T  result  s a KeyValueResult,
   * wh ch conta ns a break-out of found, notFound, and fa led key lookups.  T  set of
   * keys may or may-not be computable locally from t  query.  T  top-level type does not
   * requ re that t  keys are computable from t  query, but certa n  nstances, such as
   * Cach ngKeyValueRepos ory, do requ re key-computab l y.
   */
  type KeyValueRepos ory[Q, K, V] = Repos ory[Q, KeyValueResult[K, V]]

  type CounterKeyValueRepos ory[K] = KeyValueRepos ory[Seq[K], K, Long]

  /**
   * For KeyValueRepos ory scenar os w re t  query  s a sequence of keys, a SubqueryBu lder
   * def nes how to convert a sub-set of t  keys from t  query  nto a query.
   */
  type SubqueryBu lder[Q <: Seq[K], K] = (Seq[K], Q) => Q

  /**
   * A SubqueryBu lder w re t  query type  s noth ng more than a sequence of keys.
   */
  @deprecated("use keysAsQuery", "1.1.0")
  def KeysAsQuery[K]: SubqueryBu lder[Seq[K], K] = keysAsQuery[K]

  /**
   * A SubqueryBu lder w re t  query type  s noth ng more than a sequence of keys.
   */
  def keysAsQuery[K]: SubqueryBu lder[Seq[K], K] = (keys, parentQuery) => keys
}
