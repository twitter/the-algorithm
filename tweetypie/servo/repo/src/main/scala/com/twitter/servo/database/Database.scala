package com.tw ter.servo.database

 mport com.tw ter.servo.repos ory._
 mport com.tw ter.ut l.Future
 mport scala.collect on.mutable.{HashMap, HashSet, L stBuffer}
 mport scala.collect on.gener c.Growable

object Database {

  /**
   * Construct a KeyValueRepos ory wrapp ng access to a database.
   *
   * Data retr eved as a row from t  query  s passed to a Bu lder produc ng a
   * (Key, Row) tuple.  Once all rows have been processed t  way    s passed as a
   * sequence to a post-query funct on that can perform act ons (aggregat on usually)
   * and produce a f nal sequence of (Key, Value).
   *
   * @tparam Q
   *   how  'll be query ng t  t  repos ory
   *
   * @tparam K
   *   t  key used for look ng data up
   *
   * @tparam R
   *   each entry from t  t  database w ll be represented as an  nstance of R
   *
   * @tparam V
   *   t  repos ory w ll return a V produced by process ng one or more Rs
   *
   * @param database
   *   A database used to back t  KeyValueRepos ory be ng bu lt.
   *
   * @param dbQuery
   *   A database query for fetch ng records to be parsed  nto objects of type
   *   Row. T  query str ng can conta n  nstances of t  character '?' as
   *   placeholders for para ter passed  nto t  `Database.select` calls.
   *
   * @param bu lder
   *   A Bu lder that bu lds (K, Row) pa rs from ResultSets from t  database
   *
   * @param postProcess
   *   A funct on wh ch can man pulate t  Seq[(K, Row)] that  s returned from t 
   *   database. Useful for aggregat ng mult -mapped K, V pa rs w re V holds a
   *   conta ner w h mult ple values for t  sa  key  n t  database.  T  funct on
   *   should not man pulate t  l st of keys; do ng so w ll result  n Return.None
   *   ele nts  n t  ensu ng KeyValueResult.
   *
   *   AggregateByKey has a bas c  mple ntat on that groups R objects by a
   *   spec f ed  dent f er and may be useful as a common  mpl.
   *
   * @param selectParams
   *   A funct on that  s appl ed to t  d st nct keys  n a repos ory query.
   *   T  result  s passed to `Database.select` to be used for f ll ng  n
   *   b nd var ables  n dbQuery. By default, t  repos ory query  s passed
   *   d rectly to t  select. T  use cases for t  funct on are s uat ons
   *   w re t  SELECT state nt takes mult ple para ters.
   *
   *   Example:
   *     // A repos ory that takes Seq[Long]s of user ds and returns
   *     //  em objects of a para ter zed  em type.
   *     Database.keyValueRepos ory[Seq[Long], Long,  em,  em](
   *       database,
   *       "SELECT * FROM  ems WHERE user_ d  N (?) AND  em_type = ?;",
   *        emBu lder,
   *       selectParams = Seq(_: Seq[Long],  emType)
   *     )
   */
  def keyValueRepos ory[Q <: Seq[K], K, R, V](
    database: Database,
    dbQuery: Str ng,
    bu lder: Bu lder[(K, R)],
    postProcess: Seq[(K, R)] => Seq[(K, V)] =
      ( dent y[Seq[(K, V)]] _): (Seq[(K, V)] => Seq[(K, V)]),
    selectParams: Seq[K] => Seq[Any] = (Seq(_: Seq[K])): (Seq[K] => collect on.Seq[Seq[K]])
  ): KeyValueRepos ory[Q, K, V] =
    query => {
       f (query. sEmpty) {
        KeyValueResult.emptyFuture
      } else {
        val un queKeys = query.d st nct
        KeyValueResult.fromPa rs(un queKeys) {
          database.select(dbQuery, bu lder, selectParams(un queKeys): _*) map postProcess
        }
      }
    }
}

/**
 * A th n tra  for async  nteract on w h a database.
 */
tra  Database {
  def select[A](query: Str ng, bu lder: Bu lder[A], params: Any*): Future[Seq[A]]
  def selectOne[A](query: Str ng, bu lder: Bu lder[A], params: Any*): Future[Opt on[A]]
  def execute(query: Str ng, params: Any*): Future[ nt]
  def  nsert(query: Str ng, params: Any*): Future[Long]
  def release(): Un 
}

object NullDatabase extends Database {
  overr de def select[Un ](query: Str ng, bu lder: Bu lder[Un ], params: Any*) =
    Future.value(Seq.empty[Un ])

  overr de def selectOne[Un ](query: Str ng, bu lder: Bu lder[Un ], params: Any*) =
    Future.value(None)

  overr de def release() = ()

  overr de def execute(query: Str ng, params: Any*) =
    Future.value(0)

  overr de def  nsert(query: Str ng, params: Any*) =
    Future.value(0)
}

object AggregateByKey {
  def apply[K, R, A](
    extractKey: R => K,
    reduce: Seq[R] => A,
    pruneDupl cates: Boolean = false
  ) = new AggregateByKey(extractKey, reduce, pruneDupl cates)

  /**
   *  n t  event that t   em type (V) does not carry an aggregat on key t n   can have
   * t  Bu lder return a tuple w h so   d attac d.   f that  s done t n each Row from t 
   * bu lder w ll look so th ng l ke (So Group d, So RowObject).  Because   tend to m n m ze
   * data dupl cat on t  seems to be a pretty common pattern and can be seen  n
   * SavedSearc sRepos ory, FacebookConnect onsRepos ory, and UserToRoleRepos ory.
   *
   * @tparam K
   *   T  type for t  key
   * @tparam V
   *   T  type of a s ngle ele nt of t  l st
   * @tparam A
   *   T  object  'll aggregate l st  ems  nto
   * @param reduce
   *   A funct on that comb nes a seq of V  nto A
   * @param pruneDupl cates
   *    f set t  ensures that, at most, one  nstance of any g ven V w ll be passed  nto reduce.
   */
  def w hKeyValuePa rs[K, V, A](
    reduce: Seq[V] => A,
    pruneDupl cates: Boolean
  ): AggregateByKey[K, (K, V), A] =
    new AggregateByKey(
      { case (k, _) => k },
      values => reduce(values map { case (_, v) => v }),
      pruneDupl cates
    )
}

/**
 * Bas c aggregator that extracts keys from a Row, groups  nto a Seq by those keys, and
 * performs so  reduct on step to mash those  nto an aggregated object.  Order  s not
 * necessar ly kept bet en t  retr ev ng rows from t  database and pass ng t m  nto
 * reduce.
 *
 * @tparam K
 *   t  type used by t   em on wh ch   aggregate rows
 *
 * @tparam R
 *   object that a s ngle row of t  query w ll be represented as
 *
 * @tparam A
 *   what   collect groups of R  nto
 *
 * @param extractKey
 *   funct on to extract a key from a row object
 *
 * @param reduce
 *   funct on that can take a sequence of rows and comb ne t m  nto an aggregate
 *
 * @param pruneDupl cates
 *    f set t  w ll ensure that at most one copy of each R w ll be passed  nto reduce (as
 *   determ ned by R's equal  thod) but w ll pass t   nput through a set wh ch w ll
 *   l kely lose order ng.
 */
class AggregateByKey[K, R, A](
  extractKey: R => K,
  reduce: Seq[R] => A,
  pruneDupl cates: Boolean = false)
    extends (Seq[R] => Seq[(K, A)]) {
  overr de def apply( nput: Seq[R]): Seq[(K, A)] = {
    val collect onMap = new HashMap[K, Growable[R] w h  erable[R]]

    def emptyCollect on: Growable[R] w h  erable[R] =
       f (pruneDupl cates) {
        new HashSet[R]
      } else {
        new L stBuffer[R]
      }

     nput foreach { ele nt =>
      (collect onMap.getOrElseUpdate(extractKey(ele nt), emptyCollect on)) += ele nt
    }

    collect onMap map {
      case (key,  ems) =>
        key -> reduce( ems toSeq)
    } toSeq
  }
}
