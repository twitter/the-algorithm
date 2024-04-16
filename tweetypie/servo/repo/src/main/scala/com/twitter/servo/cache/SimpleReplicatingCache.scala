package com.tw ter.servo.cac 

 mport com.tw ter.servo.keyvalue._
 mport com.tw ter.servo.ut l.{Opt onOrder ng, TryOrder ng}
 mport com.tw ter.ut l.{Future, Return, Throw, T  , Try}

object S mpleRepl cat ngCac  {

  /**
   * Bu lds a S mpleRepl cat ngCac  that wr es a value mult ple t  s to t  sa  underly ng
   * cac  but under d fferent keys.   f t  underly ng cac   s backed by enough shards, t re
   *  s a good chance that t  d fferent keys w ll end up on d fferent shards, g v ng   s m lar
   * behav or to hav ng mult ple d st nct cac s.
   */
  def apply[K, K2, V](
    underly ng: Lock ngCac [K2, Cac d[V]],
    keyRepl cator: (K,  nt) => K2,
    repl cas:  nt = 2
  ) = new S mpleRepl cat ngCac (
    (0 unt l repl cas).toSeq map { repl ca =>
      new KeyTransform ngLock ngCac (
        underly ng,
        (key: K) => keyRepl cator(key, repl ca)
      )
    }
  )
}

/**
 * A very s mple repl cat ng cac   mple ntat on.    wr es t  sa  key/value pa r to
 * mult ple underly ng cac s. On read, each underly ng cac   s quer ed w h t  key;  f t 
 * results are not all t  sa  for a g ven key, t n t  most recent value  s chosen and
 * repl cated to all cac s.
 *
 * So  cac  operat ons are not currently supported, because t  r semant cs are a l tle fuzzy
 *  n t  repl cat on case.  Spec f cally: add and c ckAndSet.
 */
class S mpleRepl cat ngCac [K, V](underly ngCac s: Seq[Lock ngCac [K, Cac d[V]]])
    extends Lock ngCac [K, Cac d[V]] {
  pr vate type CsValue = (Try[Cac d[V]], C cksum)

  pr vate val cac dOrder ng = new Order ng[Cac d[V]] {
    // sort by ascend ng t  stamp
    def compare(a: Cac d[V], b: Cac d[V]) = a.cac dAt.compare(b.cac dAt)
  }

  pr vate val csValueOrder ng = new Order ng[CsValue] {
    // order by Try[V],  gnore c cksum
    val suborder ng = TryOrder ng(cac dOrder ng)
    def compare(a: CsValue, b: CsValue) = suborder ng.compare(a._1, b._1)
  }

  pr vate val tryOpt onCsValueOrder ng = TryOrder ng(Opt onOrder ng(csValueOrder ng))
  pr vate val tryOpt onCac dOrder ng = TryOrder ng(Opt onOrder ng(cac dOrder ng))

  /**
   * release any underly ng res ces
   */
  def release(): Un  = {
    underly ngCac s foreach { _.release() }
  }

  /**
   * Fetc s from all underly ng cac s  n parallel, and  f results d ffer, w ll choose a
   * w nner and push updated results back to t  stale cac s.
   */
  def get(keys: Seq[K]): Future[KeyValueResult[K, Cac d[V]]] = {
    getW hC cksum(keys) map { csKvRes =>
      val resBldr = new KeyValueResultBu lder[K, Cac d[V]]

      csKvRes.found foreach {
        case (k, (Return(v), _)) => resBldr.addFound(k, v)
        case (k, (Throw(t), _)) => resBldr.addFa led(k, t)
      }

      resBldr.addNotFound(csKvRes.notFound)
      resBldr.addFa led(csKvRes.fa led)
      resBldr.result()
    }
  }

  /**
   * Fetc s from all underly ng cac s  n parallel, and  f results d ffer, w ll choose a
   * w nner and push updated results back to t  stale cac s.
   */
  def getW hC cksum(keys: Seq[K]): Future[CsKeyValueResult[K, Cac d[V]]] = {
    Future.collect {
      underly ngCac s map { underly ng =>
        underly ng.getW hC cksum(keys)
      }
    } map { underly ngResults =>
      val resBldr = new KeyValueResultBu lder[K, CsValue]

      for (key <- keys) {
        val keyResults = underly ngResults map { _(key) }
        resBldr(key) = getAndRepl cate(key, keyResults) map {
          // treat ev ct ons as m sses
          case So ((Return(c), _))  f c.status == Cac dValueStatus.Ev cted => None
          case v => v
        }
      }

      resBldr.result()
    }
  }

  /**
   * Looks at all t  returned values for a g ven set of repl cat on keys, return ng t  most recent
   * cac d value  f ava lable, or  nd cate a m ss  f appl cable, or return a fa lure  f all
   * keys fa led.   f a cac d value  s returned, and so  keys don't have that cac d value,
   * t  cac d value w ll be repl cated to those keys, poss bly overwr  ng stale data.
   */
  pr vate def getAndRepl cate(
    key: K,
    keyResults: Seq[Try[Opt on[CsValue]]]
  ): Try[Opt on[CsValue]] = {
    val max = keyResults.max(tryOpt onCsValueOrder ng)

    max match {
      //  f one of t  repl cat on keys returned a cac d value, t n make sure all repl cat on
      // keys conta n that cac d value.
      case Return(So ((Return(cac d), cs))) =>
        for ((underly ng, keyResult) <- underly ngCac s z p keyResults) {
           f (keyResult != max) {
            repl cate(key, cac d, keyResult, underly ng)
          }
        }
      case _ =>
    }

    max
  }

  pr vate def repl cate(
    key: K,
    cac d: Cac d[V],
    current: Try[Opt on[CsValue]],
    underly ng: Lock ngCac [K, Cac d[V]]
  ): Future[Un ] = {
    current match {
      case Throw(_) =>
        //  f   fa led to read a part cular value,   don't want to wr e to that key
        // because that key could potent ally have t  real ne st value
        Future.Un 
      case Return(None) =>
        // add rat r than set, and fa l  f anot r value  s wr ten f rst
        underly ng.add(key, cac d).un 
      case Return(So ((_, cs))) =>
        underly ng.c ckAndSet(key, cac d, cs).un 
    }
  }

  /**
   * Currently not supported.  Use set or lockAndSet.
   */
  def add(key: K, value: Cac d[V]): Future[Boolean] = {
    Future.except on(new UnsupportedOperat onExcept on("use set or lockAndSet"))
  }

  /**
   * Currently not supported.
   */
  def c ckAndSet(key: K, value: Cac d[V], c cksum: C cksum): Future[Boolean] = {
    Future.except on(new UnsupportedOperat onExcept on("use set or lockAndSet"))
  }

  /**
   * Calls set on all underly ng cac s.   f at least one set succeeds, Future.Un   s
   * returned.   f all fa l, a Future.except on w ll be returned.
   */
  def set(key: K, value: Cac d[V]): Future[Un ] = {
    l ftAndCollect {
      underly ngCac s map { _.set(key, value) }
    } flatMap { seqTryUn s =>
      // return Future.Un   f any underly ng call succeeded, ot rw se return
      // t  f rst fa lure.
       f (seqTryUn s ex sts { _. sReturn })
        Future.Un 
      else
        Future.const(seqTryUn s. ad)
    }
  }

  /**
   * Calls lockAndSet on t  underly ng cac  for all repl cat on keys.   f at least one
   * underly ng call succeeds, a successful result w ll be returned.
   */
  def lockAndSet(key: K, handler: Lock ngCac .Handler[Cac d[V]]): Future[Opt on[Cac d[V]]] = {
    l ftAndCollect {
      underly ngCac s map { _.lockAndSet(key, handler) }
    } flatMap { seqTryOpt onCac d =>
      Future.const(seqTryOpt onCac d.max(tryOpt onCac dOrder ng))
    }
  }

  /**
   * Returns Future(true)  f any of t  underly ng cac s return Future(true); ot rw se,
   * returns Future(false)  f any of t  underly ng cac s return Future(false); ot rw se,
   * returns t  f rst fa lure.
   */
  def replace(key: K, value: Cac d[V]): Future[Boolean] = {
    l ftAndCollect {
      underly ngCac s map { _.replace(key, value) }
    } flatMap { seqTryBools =>
       f (seqTryBools.conta ns(Return.True))
        Future.value(true)
      else  f (seqTryBools.conta ns(Return.False))
        Future.value(false)
      else
        Future.const(seqTryBools. ad)
    }
  }

  /**
   * Perform ng an actual delet on on t  underly ng cac s  s not a good  dea  n t  face
   * of potent al fa lure, because fa l ng to remove all values would allow a cac d value to
   * be resurrected.   nstead, delete actually does a replace on t  underly ng cac s w h a
   * Cac dValueStatus of Ev cted, wh ch w ll be treated as a m ss on read.
   */
  def delete(key: K): Future[Boolean] = {
    replace(key, Cac d(None, Cac dValueStatus.Ev cted, T  .now))
  }

  /**
   * Convets a Seq[Future[A]]  nto a Future[Seq[Try[A]]],  solat ng fa lures  nto Trys,  nstead
   * of allow ng t  ent re Future to fa lure.
   */
  pr vate def l ftAndCollect[A](seq: Seq[Future[A]]): Future[Seq[Try[A]]] = {
    Future.collect { seq map { _ transform { Future(_) } } }
  }
}
