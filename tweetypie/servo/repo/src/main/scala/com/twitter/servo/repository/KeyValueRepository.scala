package com.tw ter.servo.repos ory

 mport com.tw ter.ut l.{Future, Try}

object KeyValueRepos ory {

  /**
   * Bu lds a KeyValueRepos ory that returns KeyValueResults  n wh ch all keys fa led w h t 
   * prov ded Throwable.
   */
  def alwaysFa l ng[Q <: Seq[K], K, V](fa lure: Throwable): KeyValueRepos ory[Q, K, V] =
    (query: Q) =>
      Future.value(
        KeyValueResult[K, V](
          fa led = query map { _ -> fa lure } toMap
        )
      )

  /**
   * Bu lds an  mmutable KeyValueRepos ory
   */
  def apply[K, V](data: Map[K, Try[V]]): KeyValueRepos ory[Seq[K], K, V] =
    new  mmutableKeyValueRepos ory(data)

  /**
   * Sets up a mapReduce type operat on on a KeyValueRepos ory w re t  query mapp ng funct on
   * breaks t  query up  nto smaller chunks, and t  reduc ng funct on  s just KeyValueResult.sum.
   */
  def chunked[Q, K, V](
    repo: KeyValueRepos ory[Q, K, V],
    chunker: Q => Seq[Q]
  ): KeyValueRepos ory[Q, K, V] =
    Repos ory.mapReduced(repo, chunker, KeyValueResult.sum[K, V])

  /**
   * Wraps a KeyValueRepos ory w h stats record ng funct onal y.
   */
  def observed[Q, K, V](
    repo: KeyValueRepos ory[Q, K, V],
    observer: Repos oryObserver,
    queryS ze: Q =>  nt
  ): KeyValueRepos ory[Q, K, V] =
    query => {
      observer.t  (queryS ze(query)) {
        repo(query).respond(observer.observeKeyValueResult)
      }
    }

  /**
   * Creates a new KeyValueRepos ory that d spatc s to onTrueRepo  f t  key
   * pred cate returns true, d spatc s to onFalseRepo ot rw se.
   */
  def selected[Q <: Seq[K], K, V](
    select: K => Boolean,
    onTrueRepo: KeyValueRepos ory[Q, K, V],
    onFalseRepo: KeyValueRepos ory[Q, K, V],
    queryBu lder: SubqueryBu lder[Q, K]
  ): KeyValueRepos ory[Q, K, V] = selectedByQuery(
    pred cateFactory = _ => select,
    onTrueRepo = onTrueRepo,
    onFalseRepo = onFalseRepo,
    queryBu lder = queryBu lder
  )

  /**
   * Creates a new KeyValueRepos ory that uses pred cateFactory to create a key pred cate, t n
   * d spatc s to onTrueRepo  f t  key pred cate returns true, d spatc s to onFalseRepo
   * ot rw se.
   */
  def selectedByQuery[Q <: Seq[K], K, V](
    pred cateFactory: Q => (K => Boolean),
    onTrueRepo: KeyValueRepos ory[Q, K, V],
    onFalseRepo: KeyValueRepos ory[Q, K, V],
    queryBu lder: SubqueryBu lder[Q, K]
  ): KeyValueRepos ory[Q, K, V] = {
    val query sEmpty = (q: Q) => q. sEmpty
    val r1 = shortC rcu Empty(query sEmpty)(onTrueRepo)
    val r2 = shortC rcu Empty(query sEmpty)(onFalseRepo)

    (query: Q) => {
      val pred cate = pred cateFactory(query)
      val (q1, q2) = query.part  on(pred cate)
      val futureRst1 = r1(queryBu lder(q1, query))
      val futureRst2 = r2(queryBu lder(q2, query))
      for {
        r1 <- futureRst1
        r2 <- futureRst2
      } y eld r1 ++ r2
    }
  }

  /**
   * Creates a new KeyValueRepos ory that d spatc s to onTrueRepo  f t  query
   * pred cate returns true, d spatc s to onFalseRepo ot rw se.
   */
  def choose[Q, K, V](
    pred cate: Q => Boolean,
    onTrueRepo: KeyValueRepos ory[Q, K, V],
    onFalseRepo: KeyValueRepos ory[Q, K, V]
  ): KeyValueRepos ory[Q, K, V] = { (query: Q) =>
    {
       f (pred cate(query)) {
        onTrueRepo(query)
      } else {
        onFalseRepo(query)
      }
    }
  }

  /**
   * Short-c rcu  a KeyValueRepos ory to return an empty
   * KeyValueResult w n t  query  s empty rat r than call ng t 
   * backend.    s up to t  caller to def ne empty.
   *
   * T   mple ntat on of repo and  sEmpty should sat sfy:
   *
   * forAll { (q: Q) => ! sEmpty(q) || (repo(q).get == KeyValueResult.empty[K, V]) }
   */
  def shortC rcu Empty[Q, K, V](
     sEmpty: Q => Boolean
  )(
    repo: KeyValueRepos ory[Q, K, V]
  ): KeyValueRepos ory[Q, K, V] = { q =>
     f ( sEmpty(q)) KeyValueResult.emptyFuture[K, V] else repo(q)
  }

  /**
   * Short-c rcu  a KeyValueRepos ory to return an empty
   * KeyValueResult for any empty Traversable query rat r than
   * call ng t  backend.
   *
   * T   mple ntat on of repo should sat sfy:
   *
   * forAll { (q: Q) => !q. sEmpty || (repo(q).get == KeyValueResult.empty[K, V]) }
   */
  def shortC rcu Empty[Q <: Traversable[_], K, V](
    repo: KeyValueRepos ory[Q, K, V]
  ): KeyValueRepos ory[Q, K, V] = shortC rcu Empty[Q, K, V]((_: Q). sEmpty)(repo)

  /**
   * Turns a bulk ng KeyValueRepos ory  nto a non-bulk ng Repos ory.  T  query to t 
   * KeyValueRepos ory must be noth ng more than a Seq[K].
   */
  def s ngular[K, V](repo: KeyValueRepos ory[Seq[K], K, V]): Repos ory[K, Opt on[V]] =
    s ngular(repo, (key: K) => Seq(key))

  /**
   * Turns a bulk ng KeyValueRepos ory  nto a non-bulk ng Repos ory.
   */
  def s ngular[Q, K, V](
    repo: KeyValueRepos ory[Q, K, V],
    queryBu lder: K => Q
  ): Repos ory[K, Opt on[V]] =
    key => {
      repo(queryBu lder(key)) flatMap { results =>
        Future.const(results(key))
      }
    }

  /**
   * Converts a KeyValueRepos ory w h value type V to one w h value type
   * V2 us ng a funct on that maps found values.
   */
  def mapFound[Q, K, V, V2](
    repo: KeyValueRepos ory[Q, K, V],
    f: V => V2
  ): KeyValueRepos ory[Q, K, V2] =
    repo andT n { _ map { _ mapFound f } }

  /**
   * Converts a KeyValueRepos ory w h value type V to one w h value type
   * V2 us ng a funct on that maps over results.
   */
  def mapValues[Q, K, V, V2](
    repo: KeyValueRepos ory[Q, K, V],
    f: Try[Opt on[V]] => Try[Opt on[V2]]
  ): KeyValueRepos ory[Q, K, V2] =
    repo andT n { _ map { _ mapValues f } }

  /**
   * Turns a KeyValueRepos ory wh ch may throw an except on to anot r
   * KeyValueRepos ory wh ch always returns Future.value(KeyValueResult)
   * even w n t re  s an except on
   */
  def scatterExcept ons[Q <: Traversable[K], K, V](
    repo: KeyValueRepos ory[Q, K, V]
  ): KeyValueRepos ory[Q, K, V] =
    q =>
      repo(q) handle {
        case t => KeyValueResult[K, V](fa led = q map { _ -> t } toMap)
      }
}
