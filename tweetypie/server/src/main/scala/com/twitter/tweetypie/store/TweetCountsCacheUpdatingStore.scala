package com.tw ter.t etyp e
package store

 mport com.tw ter.concurrent.Ser al zed
 mport com.tw ter.servo.cac .Lock ngCac .Handler
 mport com.tw ter.servo.cac ._
 mport com.tw ter.t etyp e.repos ory.BookmarksKey
 mport com.tw ter.t etyp e.repos ory.FavsKey
 mport com.tw ter.t etyp e.repos ory.QuotesKey
 mport com.tw ter.t etyp e.repos ory.Repl esKey
 mport com.tw ter.t etyp e.repos ory.Ret etsKey
 mport com.tw ter.t etyp e.repos ory.T etCountKey
 mport com.tw ter.ut l.Durat on
 mport com.tw ter.ut l.T  r
 mport scala.collect on.mutable

tra  T etCountsCac Updat ngStore
    extends T etStoreBase[T etCountsCac Updat ngStore]
    w h  nsertT et.Store
    w h Async nsertT et.Store
    w h Repl cated nsertT et.Store
    w h DeleteT et.Store
    w h AsyncDeleteT et.Store
    w h Repl catedDeleteT et.Store
    w h UndeleteT et.Store
    w h Repl catedUndeleteT et.Store
    w h Async ncrFavCount.Store
    w h Repl cated ncrFavCount.Store
    w h Async ncrBookmarkCount.Store
    w h Repl cated ncrBookmarkCount.Store
    w h AsyncSetRet etV s b l y.Store
    w h Repl catedSetRet etV s b l y.Store
    w h Flush.Store {
  def wrap(w: T etStore.Wrap): T etCountsCac Updat ngStore = {
    new T etStoreWrapper(w, t )
      w h T etCountsCac Updat ngStore
      w h  nsertT et.StoreWrapper
      w h Async nsertT et.StoreWrapper
      w h Repl cated nsertT et.StoreWrapper
      w h DeleteT et.StoreWrapper
      w h AsyncDeleteT et.StoreWrapper
      w h Repl catedDeleteT et.StoreWrapper
      w h UndeleteT et.StoreWrapper
      w h Repl catedUndeleteT et.StoreWrapper
      w h Async ncrFavCount.StoreWrapper
      w h Repl cated ncrFavCount.StoreWrapper
      w h Async ncrBookmarkCount.StoreWrapper
      w h Repl cated ncrBookmarkCount.StoreWrapper
      w h AsyncSetRet etV s b l y.StoreWrapper
      w h Repl catedSetRet etV s b l y.StoreWrapper
      w h Flush.StoreWrapper
  }
}

/**
 * An  mple ntat on of T etStore that updates t et-spec f c counts  n
 * t  CountsCac .
 */
object T etCountsCac Updat ngStore {
  pr vate type Act on = T etCountKey => Future[Un ]

  def keys(t et d: T et d): Seq[T etCountKey] =
    Seq(
      Ret etsKey(t et d),
      Repl esKey(t et d),
      FavsKey(t et d),
      QuotesKey(t et d),
      BookmarksKey(t et d))

  def relatedKeys(t et: T et): Seq[T etCountKey] =
    Seq(
      getReply(t et).flatMap(_. nReplyToStatus d).map(Repl esKey(_)),
      getQuotedT et(t et).map(quotedT et => QuotesKey(quotedT et.t et d)),
      getShare(t et).map(share => Ret etsKey(share.s ceStatus d))
    ).flatten

  // p ck all keys except quotes key
  def relatedKeysW houtQuotesKey(t et: T et): Seq[T etCountKey] =
    relatedKeys(t et).f lterNot(_. s nstanceOf[QuotesKey])

  def apply(countsStore: Cac dCountsStore): T etCountsCac Updat ngStore = {
    val  ncr: Act on = key => countsStore. ncr(key, 1)
    val decr: Act on = key => countsStore. ncr(key, -1)
    val  n : Act on = key => countsStore.add(key, 0)
    val delete: Act on = key => countsStore.delete(key)

    def  n Counts(t et d: T et d) = Future.jo n(keys(t et d).map( n ))
    def  ncrRelatedCounts(t et: T et, excludeQuotesKey: Boolean = false) = {
      Future.jo n {
         f (excludeQuotesKey) {
          relatedKeysW houtQuotesKey(t et).map( ncr)
        } else {
          relatedKeys(t et).map( ncr)
        }
      }
    }
    def deleteCounts(t et d: T et d) = Future.jo n(keys(t et d).map(delete))

    // Decre nt all t  counters  f  s t  last quote, ot rw se avo d decre nt ng quote counters
    def decrRelatedCounts(t et: T et,  sLastQuoteOfQuoter: Boolean = false) = {
      Future.jo n {
         f ( sLastQuoteOfQuoter) {
          relatedKeys(t et).map(decr)
        } else {
          relatedKeysW houtQuotesKey(t et).map(decr)
        }
      }
    }

    def updateFavCount(t et d: T et d, delta:  nt) =
      countsStore. ncr(FavsKey(t et d), delta).un 

    def updateBookmarkCount(t et d: T et d, delta:  nt) =
      countsStore. ncr(BookmarksKey(t et d), delta).un 

    // t se are use spec f cally for setRet etV s b l y
    def  ncrRet etCount(t et d: T et d) =  ncr(Ret etsKey(t et d))
    def decrRet etCount(t et d: T et d) = decr(Ret etsKey(t et d))

    new T etCountsCac Updat ngStore {
      overr de val  nsertT et: FutureEffect[ nsertT et.Event] =
        FutureEffect[ nsertT et.Event](e =>  n Counts(e.t et. d))

      overr de val async nsertT et: FutureEffect[Async nsertT et.Event] =
        FutureEffect[Async nsertT et.Event] { e =>
           ncrRelatedCounts(e.cac dT et.t et, e.quoterHasAlreadyQuotedT et)
        }

      overr de val retryAsync nsertT et: FutureEffect[
        T etStoreRetryEvent[Async nsertT et.Event]
      ] =
        FutureEffect.un [T etStoreRetryEvent[Async nsertT et.Event]]

      overr de val repl cated nsertT et: FutureEffect[Repl cated nsertT et.Event] =
        FutureEffect[Repl cated nsertT et.Event] { e =>
          Future
            .jo n(
               n Counts(e.t et. d),
               ncrRelatedCounts(e.t et, e.quoterHasAlreadyQuotedT et)).un 
        }

      overr de val deleteT et: FutureEffect[DeleteT et.Event] =
        FutureEffect[DeleteT et.Event](e => deleteCounts(e.t et. d))

      overr de val asyncDeleteT et: FutureEffect[AsyncDeleteT et.Event] =
        FutureEffect[AsyncDeleteT et.Event](e => decrRelatedCounts(e.t et, e. sLastQuoteOfQuoter))

      overr de val retryAsyncDeleteT et: FutureEffect[
        T etStoreRetryEvent[AsyncDeleteT et.Event]
      ] =
        FutureEffect.un [T etStoreRetryEvent[AsyncDeleteT et.Event]]

      overr de val repl catedDeleteT et: FutureEffect[Repl catedDeleteT et.Event] =
        FutureEffect[Repl catedDeleteT et.Event] { e =>
          Future
            .jo n(deleteCounts(e.t et. d), decrRelatedCounts(e.t et, e. sLastQuoteOfQuoter)).un 
        }

      overr de val undeleteT et: FutureEffect[UndeleteT et.Event] =
        FutureEffect[UndeleteT et.Event] { e =>
           ncrRelatedCounts(e.t et, e.quoterHasAlreadyQuotedT et)
        }

      overr de val repl catedUndeleteT et: FutureEffect[Repl catedUndeleteT et.Event] =
        FutureEffect[Repl catedUndeleteT et.Event] { e =>
           ncrRelatedCounts(e.t et, e.quoterHasAlreadyQuotedT et)
        }

      overr de val async ncrFavCount: FutureEffect[Async ncrFavCount.Event] =
        FutureEffect[Async ncrFavCount.Event](e => updateFavCount(e.t et d, e.delta))

      overr de val repl cated ncrFavCount: FutureEffect[Repl cated ncrFavCount.Event] =
        FutureEffect[Repl cated ncrFavCount.Event](e => updateFavCount(e.t et d, e.delta))

      overr de val async ncrBookmarkCount: FutureEffect[Async ncrBookmarkCount.Event] =
        FutureEffect[Async ncrBookmarkCount.Event](e => updateBookmarkCount(e.t et d, e.delta))

      overr de val repl cated ncrBookmarkCount: FutureEffect[Repl cated ncrBookmarkCount.Event] =
        FutureEffect[Repl cated ncrBookmarkCount.Event] { e =>
          updateBookmarkCount(e.t et d, e.delta)
        }

      overr de val asyncSetRet etV s b l y: FutureEffect[AsyncSetRet etV s b l y.Event] =
        FutureEffect[AsyncSetRet etV s b l y.Event] { e =>
           f (e.v s ble)  ncrRet etCount(e.src d) else decrRet etCount(e.src d)
        }

      overr de val retryAsyncSetRet etV s b l y: FutureEffect[
        T etStoreRetryEvent[AsyncSetRet etV s b l y.Event]
      ] =
        FutureEffect.un [T etStoreRetryEvent[AsyncSetRet etV s b l y.Event]]

      overr de val repl catedSetRet etV s b l y: FutureEffect[
        Repl catedSetRet etV s b l y.Event
      ] =
        FutureEffect[Repl catedSetRet etV s b l y.Event] { e =>
           f (e.v s ble)  ncrRet etCount(e.src d) else decrRet etCount(e.src d)
        }

      overr de val flush: FutureEffect[Flush.Event] =
        FutureEffect[Flush.Event] { e => Future.collect(e.t et ds.map(deleteCounts)).un  }
          .only f(_.flushCounts)
    }
  }
}

/**
 * A s mple tra  around t  cac  operat ons needed by T etCountsCac Updat ngStore.
 */
tra  Cac dCountsStore {
  def add(key: T etCountKey, count: Count): Future[Un ]
  def delete(key: T etCountKey): Future[Un ]
  def  ncr(key: T etCountKey, delta: Count): Future[Un ]
}

object Cac dCountsStore {
  def fromLock ngCac (cac : Lock ngCac [T etCountKey, Cac d[Count]]): Cac dCountsStore =
    new Cac dCountsStore {
      def add(key: T etCountKey, count: Count): Future[Un ] =
        cac .add(key, toCac d(count)).un 

      def delete(key: T etCountKey): Future[Un ] =
        cac .delete(key).un 

      def  ncr(key: T etCountKey, delta: Count): Future[Un ] =
        cac .lockAndSet(key,  ncrDecrHandler(delta)).un 
    }

  def toCac d(count: Count): Cac d[Count] = {
    val now = T  .now
    Cac d(So (count), Cac dValueStatus.Found, now, So (now))
  }

  case class  ncrDecrHandler(delta: Long) extends Handler[Cac d[Count]] {
    overr de def apply( nCac : Opt on[Cac d[Count]]): Opt on[Cac d[Count]] =
       nCac .flatMap( ncrCount)

    pr vate[t ] def  ncrCount(oldCac d: Cac d[Count]): Opt on[Cac d[Count]] = {
      oldCac d.value.map { oldCount => oldCac d.copy(value = So (safer ncr(oldCount))) }
    }

    pr vate[t ] def safer ncr(value: Long) = math.max(0, value + delta)

    overr de lazy val toStr ng: Str ng = " ncrDecrHandler(%s)".format(delta)
  }

  object Queue sFullExcept on extends Except on
}

/**
 * An  mple ntat on of Cac dCountsStore that can queue and aggregate mult ple  ncr
 * updates to t  sa  key toget r.  Currently, updates for a key only start to aggregate
 * after t re  s a fa lure to  ncr on t  underly ng store, wh ch often  nd cates content on
 * due to a h gh level of updates.  After a fa lure, a key  s promoted  nto a "tracked" state,
 * and subsequent updates are aggregated toget r.  Per od cally, t  aggregated updates w ll
 * be flus d.  f t  flush for a key succeeds and no more updates have co   n dur ng t  flush,
 * t n t  key  s demoted out of t  tracked state.  Ot rw se, updates cont nue to aggregate
 * unt l t  next flush attempt.
 */
class Aggregat ngCac dCountsStore(
  underly ng: Cac dCountsStore,
  t  r: T  r,
  flush nterval: Durat on,
  maxS ze:  nt,
  stats: StatsRece ver)
    extends Cac dCountsStore
    w h Ser al zed {
  pr vate[t ] val pend ngUpdates: mutable.Map[T etCountKey, Count] =
    new mutable.HashMap[T etCountKey, Count]

  pr vate[t ] var track ngCount:  nt = 0

  pr vate[t ] val promot onCounter = stats.counter("promot ons")
  pr vate[t ] val demot onCounter = stats.counter("demot ons")
  pr vate[t ] val updateCounter = stats.counter("aggregated_updates")
  pr vate[t ] val overflowCounter = stats.counter("overflows")
  pr vate[t ] val flushFa lureCounter = stats.counter("flush_fa lures")
  pr vate[t ] val track ngCountGauge = stats.addGauge("track ng")(track ngCount.toFloat)

  t  r.sc dule(flush nterval) { flush() }

  def add(key: T etCountKey, count: Count): Future[Un ] =
    underly ng.add(key, count)

  def delete(key: T etCountKey): Future[Un ] =
    underly ng.delete(key)

  def  ncr(key: T etCountKey, delta: Count): Future[Un ] =
    aggregate fTracked(key, delta).flatMap {
      case true => Future.Un 
      case false =>
        underly ng
          . ncr(key, delta)
          .rescue { case _ => aggregate(key, delta) }
    }

  /**
   * Queues an update to be aggregated and appl ed to a key at a later t  , but only  f   are
   * already aggregat ng updates for t  key.
   *
   * @return true t  delta was aggregated, false  f t  key  s not be ng tracked
   * and t   ncr should be attempted d rectly.
   */
  pr vate[t ] def aggregate fTracked(key: T etCountKey, delta: Count): Future[Boolean] =
    ser al zed {
      pend ngUpdates.get(key) match {
        case None => false
        case So (current) =>
          updateCounter. ncr()
          pend ngUpdates(key) = current + delta
          true
      }
    }

  /**
   * Queues an update to be aggregated and appl ed to a key at a later t  .
   */
  pr vate[t ] def aggregate(key: T etCountKey, delta: Count): Future[Un ] =
    ser al zed {
      val alreadyTracked = pend ngUpdates.conta ns(key)

       f (!alreadyTracked) {
         f (pend ngUpdates.s ze < maxS ze)
          promot onCounter. ncr()
        else {
          overflowCounter. ncr()
          throw Cac dCountsStore.Queue sFullExcept on
        }
      }

      (pend ngUpdates.get(key).getOrElse(0L) + delta) match {
        case 0 =>
          pend ngUpdates.remove(key)
          demot onCounter. ncr()

        case aggregatedDelta =>
          pend ngUpdates(key) = aggregatedDelta
      }

      track ngCount = pend ngUpdates.s ze
    }

  pr vate[t ] def flush(): Future[Un ] = {
    for {
      // make a copy of t  updates to flush, so that updates can cont nue to be queued
      // wh le t  flush  s  n progress.   f an  nd v dual flush succeeds, t n  
      // go back and update pend ngUpdates.
      updates <- ser al zed { pend ngUpdates.toSeq.toL st }
      () <- Future.jo n(for ((key, delta) <- updates) y eld flush(key, delta))
    } y eld ()
  }

  pr vate[t ] def flush(key: T etCountKey, delta: Count): Future[Un ] =
    underly ng
      . ncr(key, delta)
      .flatMap(_ => aggregate(key, -delta))
      .handle { case ex => flushFa lureCounter. ncr() }
}
