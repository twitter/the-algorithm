package com.tw ter.servo.keyvalue

 mport com.tw ter.f nagle. mcac d.ut l.NotFound
 mport com.tw ter.ut l.{Future, Return, Throw, Try}
 mport scala.collect on. mmutable

object KeyValueResult {
  pr vate[t ] val Empty = KeyValueResult()
  pr vate[t ] val EmptyFuture = Future.value(Empty)

  def empty[K, V]: KeyValueResult[K, V] =
    Empty.as nstanceOf[KeyValueResult[K, V]]

  def emptyFuture[K, V]: Future[KeyValueResult[K, V]] =
    EmptyFuture.as nstanceOf[Future[KeyValueResult[K, V]]]

  /**
   * Bu lds a KeyValueResult us ng pa rs of keys to Try[Opt on[V]].  T se values are spl 
   * out to bu ld t  separate found/notFound/fa led collect ons.
   */
  def bu ld[K, V](data: (K, Try[Opt on[V]])*): KeyValueResult[K, V] = {
    val bldr = new KeyValueResultBu lder[K, V]
    data.foreach { case (k, v) => bldr.update(k, v) }
    bldr.result()
  }

  /**
   * Bu lds a future KeyValueResult us ng a future sequence of key-value tuples. That
   * sequence does not necessar ly match up w h t  sequence of keys prov ded. T 
   * sequence of pa rs represent t  found results.  notFound w ll be f lled  n from t 
   * m ss ng keys.
   */
  def fromPa rs[K, V](
    keys:  erable[K] = N l:  mmutable.N l.type
  )(
    futurePa rs: Future[TraversableOnce[(K, V)]]
  ): Future[KeyValueResult[K, V]] = {
    fromMap(keys) {
      futurePa rs map { _.toMap }
    }
  }

  /**
   * Bu lds a future KeyValueResult us ng a future map of found results. notFound w ll be f lled
   *  n from t  m ss ng keys.
   */
  def fromMap[K, V](
    keys:  erable[K] = N l:  mmutable.N l.type
  )(
    futureMap: Future[Map[K, V]]
  ): Future[KeyValueResult[K, V]] = {
    futureMap map { found =>
      KeyValueResult[K, V](found = found, notFound = NotFound(keys.toSet, found.keySet))
    } handle {
      case t =>
        KeyValueResult[K, V](fa led = keys.map { _ -> t }.toMap)
    }
  }

  /**
   * Bu lds a future KeyValueResult us ng a future sequence of opt onal results. That
   * sequence must match up pa r-w se w h t  g ven sequence of keys. A value of So [V]  s
   * counted as a found result, a value of None  s counted as a notFound result.
   */
  def fromSeqOpt on[K, V](
    keys:  erable[K]
  )(
    futureSeq: Future[Seq[Opt on[V]]]
  ): Future[KeyValueResult[K, V]] = {
    futureSeq map { seq =>
      keys.z p(seq).foldLeft(new KeyValueResultBu lder[K, V]) {
        case (bldr, (key, tryRes)) =>
          tryRes match {
            case So (value) => bldr.addFound(key, value)
            case None => bldr.addNotFound(key)
          }
      } result ()
    } handle {
      case t =>
        KeyValueResult[K, V](fa led = keys.map { _ -> t }.toMap)
    }
  }

  /**
   * Bu lds a future KeyValueResult us ng a future sequence of Try results. That
   * sequence must match up pa r-w se w h t  g ven sequence of keys. A value of Return[V]  s
   * counted as a found result, a value of Throw  s counted as a fa led result.
   */
  def fromSeqTry[K, V](
    keys:  erable[K]
  )(
    futureSeq: Future[Seq[Try[V]]]
  ): Future[KeyValueResult[K, V]] = {
    futureSeq map { seq =>
      keys.z p(seq).foldLeft(new KeyValueResultBu lder[K, V]) {
        case (bldr, (key, tryRes)) =>
          tryRes match {
            case Return(value) => bldr.addFound(key, value)
            case Throw(t) => bldr.addFa led(key, t)
          }
      } result ()
    } handle {
      case t =>
        KeyValueResult[K, V](fa led = keys.map { _ -> t }.toMap)
    }
  }

  /**
   * Bu lds a future KeyValueResult us ng a sequence of future opt ons.  That sequence must
   * match up pa r-w se w h t  g ven sequence of keys.  A value of So [V]  s
   * counted as a found result, a value of None  s counted as a notFound result.
   */
  def fromSeqFuture[K, V](
    keys:  erable[K]
  )(
    futureSeq: Seq[Future[Opt on[V]]]
  ): Future[KeyValueResult[K, V]] = {
    fromSeqTryOpt ons(keys) {
      Future.collect {
        futureSeq map { _.transform(Future(_)) }
      }
    }
  }

  /**
   * Bu lds a future KeyValueResult us ng a future sequence of Try[Opt on[V]].  That sequence must
   * match up pa r-w se w h t  g ven sequence of keys.  A value of Return[So [V]]  s
   * counted as a found result, a value of Return[None]  s counted as a notFound result, and a value
   * of Throw[V]  s counted as a fa led result.
   */
  def fromSeqTryOpt ons[K, V](
    keys:  erable[K]
  )(
    futureSeq: Future[Seq[Try[Opt on[V]]]]
  ): Future[KeyValueResult[K, V]] = {
    futureSeq map { seq =>
      keys.z p(seq).foldLeft(new KeyValueResultBu lder[K, V]) {
        case (bldr, (key, tryRes)) =>
          tryRes match {
            case Return(So (value)) => bldr.addFound(key, value)
            case Return(None) => bldr.addNotFound(key)
            case Throw(t) => bldr.addFa led(key, t)
          }
      } result ()
    } handle {
      case t =>
        KeyValueResult[K, V](fa led = keys.map { _ -> t }.toMap)
    }
  }

  /**
   * Bu lds a future KeyValueResult us ng a future map w h value Try[Opt on[V]]. A value of
   * Return[So [V]]  s counted as a found result, a value of Return[None]  s counted as a notFound
   * result, and a value of Throw[V]  s counted as a fa led result.
   *
   * notFound w ll be f lled  n from t  m ss ng keys. Except ons w ll be handled by count ng all
   * keys as fa led. Values that are  n map but not keys w ll be  gnored.
   */
  def fromMapTryOpt ons[K, V](
    keys:  erable[K]
  )(
    futureMapTryOpt ons: Future[Map[K, Try[Opt on[V]]]]
  ): Future[KeyValueResult[K, V]] = {
    futureMapTryOpt ons map { mapTryOpt ons =>
      keys.foldLeft(new KeyValueResultBu lder[K, V]) {
        case (bu lder, key) =>
          mapTryOpt ons.get(key) match {
            case So (Return(So (value))) => bu lder.addFound(key, value)
            case So (Return(None)) | None => bu lder.addNotFound(key)
            case So (Throw(fa lure)) => bu lder.addFa led(key, fa lure)
          }
      } result ()
    } handle {
      case t =>
        KeyValueResult[K, V](fa led = keys.map { _ -> t }.toMap)
    }
  }

  /**
   * Reduces several KeyValueResults down to just 1, by comb n ng as  f by ++, but
   * more eff c ently w h fe r  nter d ate results.
   */
  def sum[K, V](results:  erable[KeyValueResult[K, V]]): KeyValueResult[K, V] = {
    val bldr = new KeyValueResultBu lder[K, V]

    results foreach { result =>
      bldr.addFound(result.found)
      bldr.addNotFound(result.notFound)
      bldr.addFa led(result.fa led)
    }

    val res = bldr.result()

     f (res.notFound. sEmpty && res.fa led. sEmpty) {
      res
    } else {
      val foundKeySet = res.found.keySet
      val notFound = NotFound(res.notFound, foundKeySet)
      val fa led = NotFound(NotFound(res.fa led, foundKeySet), res.notFound)
      KeyValueResult(res.found, notFound, fa led)
    }
  }
}

case class KeyValueResult[K, +V](
  found: Map[K, V] = Map.empty[K, V]:  mmutable.Map[K, V],
  notFound: Set[K] = Set.empty[K]:  mmutable.Set[K],
  fa led: Map[K, Throwable] = Map.empty[K, Throwable]:  mmutable.Map[K, Throwable])
    extends  erable[(K, Try[Opt on[V]])] {

  /**
   * A c aper  mple ntat on of  sEmpty than t  default wh ch rel es
   * on bu ld ng an  erator.
   */
  overr de def  sEmpty = found. sEmpty && notFound. sEmpty && fa led. sEmpty

  /**
   * map over t  keyspace to produce a new KeyValueResult
   */
  def mapKeys[K2](f: K => K2): KeyValueResult[K2, V] =
    copy(
      found = found.map { case (k, v) => f(k) -> v },
      notFound = notFound.map(f),
      fa led = fa led.map { case (k, t) => f(k) -> t }
    )

  /**
   * Maps over found values to produce a new KeyValueResult.   f t  g ven funct on throws an
   * except on for a part cular value, that value w ll be moved to t  `fa led` bucket w h
   * t  thrown except on.
   */
  def mapFound[V2](f: V => V2): KeyValueResult[K, V2] = {
    val bu lder = new KeyValueResultBu lder[K, V2]()

    found.foreach {
      case (k, v) =>
        bu lder.update(k, Try(So (f(v))))
    }
    bu lder.addNotFound(notFound)
    bu lder.addFa led(fa led)

    bu lder.result()
  }

  /**
   * map over t  values prov ded by t   erator, to produce a new KeyValueResult
   */
  def mapValues[V2](f: Try[Opt on[V]] => Try[Opt on[V2]]): KeyValueResult[K, V2] = {
    val bu lder = new KeyValueResultBu lder[K, V2]()

    found.foreach {
      case (k, v) =>
        bu lder.update(k, f(Return(So (v))))
    }
    notFound.foreach { k =>
      bu lder.update(k, f(Return.None))
    }
    fa led.foreach {
      case (k, t) =>
        bu lder.update(k, f(Throw(t)))
    }

    bu lder.result()
  }

  /**
   * Map over found values to create a new KVR w h t  ex st ng notFound and fa led keys  ntact.
   */
  def mapFoundValues[V2](f: V => Try[Opt on[V2]]): KeyValueResult[K, V2] = {
    val bu lder = new KeyValueResultBu lder[K, V2]()

    found.foreach {
      case (k, v) => bu lder.update(k, f(v))
    }
    bu lder.addNotFound(notFound)
    bu lder.addFa led(fa led)

    bu lder.result()
  }

  /**
   * map over t  pa rs of results, creat ng a new KeyValueResult based on t  returned
   * tuples from t  prov ded funct on.
   */
  def mapPa rs[K2, V2](f: (K, Try[Opt on[V]]) => (K2, Try[Opt on[V2]])): KeyValueResult[K2, V2] = {
    val bu lder = new KeyValueResultBu lder[K2, V2]

    def update(k: K, v: Try[Opt on[V]]): Un  =
      f(k, v) match {
        case (k2, v2) => bu lder.update(k2, v2)
      }

    found.foreach {
      case (k, v) =>
        update(k, Return(So (v)))
    }
    notFound.foreach { k =>
      update(k, Return.None)
    }
    fa led.foreach {
      case (k, t) =>
        update(k, Throw(t))
    }

    bu lder.result()
  }

  /**
   * f lter t  KeyValueResult, to produce a new KeyValueResult
   */
  overr de def f lter(p: ((K, Try[Opt on[V]])) => Boolean): KeyValueResult[K, V] = {
    val bu lder = new KeyValueResultBu lder[K, V]

    def update(k: K, v: Try[Opt on[V]]): Un  = {
       f (p((k, v)))
        bu lder.update(k, v)
    }

    found.foreach {
      case (k, v) =>
        update(k, Return(So (v)))
    }
    notFound.foreach { k =>
      update(k, Return.None)
    }
    fa led.foreach {
      case (k, t) =>
        update(k, Throw(t))
    }

    bu lder.result()
  }

  /**
   * f lterNot t  KeyValueResult, to produce a new KeyValueResult
   */
  overr de def f lterNot(p: ((K, Try[Opt on[V]])) => Boolean): KeyValueResult[K, V] = {
    f lter(!p(_))
  }

  /**
   * Returns an  erator that y elds all found, notFound, and fa led values
   * represented  n t  comb ned Try[Opt on[V]] type.
   */
  def  erator:  erator[(K, Try[Opt on[V]])] =
    (found. erator map { case (k, v) => k -> Return(So (v)) }) ++
      (notFound. erator map { k =>
        k -> Return.None
      }) ++
      (fa led. erator map { case (k, t) => k -> Throw(t) })

  /**
   * Returns a copy  n wh ch all fa led entr es are converted to m sses.  T  spec f c
   * fa lure  nformat on  s lost.
   */
  def convertFa ledToNotFound =
    copy(
      notFound = notFound ++ fa led.keySet,
      fa led = Map.empty[K, Throwable]
    )

  /**
   * Returns a copy  n wh ch all not-found entr es are converted to fa lures.
   */
  def convertNotFoundToFa led(f: K => Throwable) =
    copy(
      notFound = Set.empty[K],
      fa led = fa led ++ (notFound map { k =>
        k -> f(k)
      })
    )

  /**
   * Returns a copy  n wh ch fa lures are repa red w h t  suppl ed handler
   */
  def repa rFa led[V2 >: V](handler: Part alFunct on[Throwable, Opt on[V2]]) =
     f (fa led. sEmpty) {
      t 
    } else {
      val bu lder = new KeyValueResultBu lder[K, V2]
      bu lder.addFound(found)
      bu lder.addNotFound(notFound)
      fa led map { case (k, t) => bu lder.update(k, Throw(t) handle handler) }
      bu lder.result()
    }

  /**
   * Comb nes two KeyValueResults.  Confl ct ng founds/notFounds are resolved
   * as founds, and confl ct ng (found|notFound)/fa lures are resolved as (found|notFound).
   */
  def ++[K2 >: K, V2 >: V](that: KeyValueResult[K2, V2]): KeyValueResult[K2, V2] = {
     f (t . sEmpty) that
    else  f (that. sEmpty) t .as nstanceOf[KeyValueResult[K2, V2]]
    else {
      val found = t .found ++ that.found
      val notFound = NotFound(t .notFound ++ that.notFound, found.keySet)
      val fa led = NotFound(NotFound(t .fa led ++ that.fa led, found.keySet), notFound)
      KeyValueResult(found, notFound, fa led)
    }
  }

  /**
   * Looks up a result for a key.
   */
  def apply(key: K): Try[Opt on[V]] = {
    found.get(key) match {
      case so  @ So (_) => Return(so )
      case None =>
        fa led.get(key) match {
          case So (t) => Throw(t)
          case None => Return.None
        }
    }
  }

  /**
   * Looks up a result for a key, return ng a prov ded default  f t  key  s not
   * found or fa led.
   */
  def getOrElse[V2 >: V](key: K, default: => V2): V2 =
    found.getOrElse(key, default)

  /**
   *  f any keys fa l, w ll return t  f rst fa lure. Ot rw se,
   * w ll convert founds/notFounds to a Seq[Opt on[V]], ordered by
   * t  keys prov ded
   */
  def toFutureSeqOfOpt ons(keys: Seq[K]): Future[Seq[Opt on[V]]] = {
    fa led.values. adOpt on match {
      case So (t) => Future.except on(t)
      case None => Future.value(keys.map(found.get))
    }
  }

  // T   s unfortunate, but   end up pull ng  n  erable's toStr ng,
  // wh ch  s not all that readable.
  overr de def toStr ng(): Str ng = {
    val sb = new Str ngBu lder(256)
    sb.append("KeyValueResult(")
    sb.append("found = ")
    sb.append(found)
    sb.append(", notFound = ")
    sb.append(notFound)
    sb.append(", fa led = ")
    sb.append(fa led)
    sb.append(')')
    sb.toStr ng()
  }
}

class KeyValueResultBu lder[K, V] {
  pr vate[t ] val found = Map.newBu lder[K, V]
  pr vate[t ] val notFound = Set.newBu lder[K]
  pr vate[t ] val fa led = Map.newBu lder[K, Throwable]

  def addFound(k: K, v: V) = { found += (k -> v); t  }
  def addNotFound(k: K) = { notFound += k; t  }
  def addFa led(k: K, t: Throwable) = { fa led += (k -> t); t  }

  def addFound(kvs:  erable[(K, V)]) = { found ++= kvs; t  }
  def addNotFound(ks:  erable[K]) = { notFound ++= ks; t  }
  def addFa led(kts:  erable[(K, Throwable)]) = { fa led ++= kts; t  }

  def update(k: K, tryV: Try[Opt on[V]]) = {
    tryV match {
      case Throw(t) => addFa led(k, t)
      case Return(None) => addNotFound(k)
      case Return(So (v)) => addFound(k, v)
    }
  }

  def result() = KeyValueResult(found.result(), notFound.result(), fa led.result())
}
