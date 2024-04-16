package com.tw ter.t etyp e
package store

 mport com.tw ter.f nagle.trac ng.Trace
 mport com.tw ter.t etyp e.store.T etStoreEvent.RetryStrategy
 mport com.tw ter.t etyp e.thr ftscala._

object T etStoreEvent {

  /**
   * Parent tra  for  nd cat ng what type of retry strategy to apply to event handlers
   * for t  correspond ng event type.  D fferent classes of events use d fferent strateg es.
   */
  sealed tra  RetryStrategy

  /**
   *  nd cates that t  event type doesn't support retr es.
   */
  case object NoRetry extends RetryStrategy

  /**
   *  nd cates that  f an event handler encounters a fa lure,   should enqueue a
   * retry to be perfor d asynchronously.
   */
  case class EnqueueAsyncRetry(enqueueRetry: (Thr ftT etServ ce, AsyncWr eAct on) => Future[Un ])
      extends RetryStrategy

  /**
   *  nd cates that  f an event handler encounters a fa lure,   should retry
   * t  event locally so  number of t  s, before eventually g ven up and scr b ng
   * t  fa lure.
   */
  case class LocalRetryT nScr beFa lure(toFa ledAsyncWr e: AsyncWr eAct on => Fa ledAsyncWr e)
      extends RetryStrategy

  /**
   *  nd cates that  f an event handler encounters a fa lure,   should retry
   * t  event locally so  number of t  s.
   */
  case object Repl catedEventLocalRetry extends RetryStrategy
}

/**
 * T  abstract parent class for all T etStoreEvent types.
 */
sealed tra  T etStoreEvent {
  val na : Str ng

  val trace d: Long = Trace. d.trace d.toLong

  /**
   *  nd cates a part cular retry behav or that should be appl ed to event handlers for
   * t  correspond ng event type.  T  spec f cs of t  strategy m ght depend upon t 
   * spec f c T etStore  mple ntat on.
   */
  def retryStrategy: RetryStrategy
}

abstract class SyncT etStoreEvent(val na : Str ng) extends T etStoreEvent {
  overr de def retryStrategy: RetryStrategy = T etStoreEvent.NoRetry
}

abstract class AsyncT etStoreEvent(val na : Str ng) extends T etStoreEvent {
  def enqueueRetry(serv ce: Thr ftT etServ ce, act on: AsyncWr eAct on): Future[Un ]

  overr de def retryStrategy: RetryStrategy = T etStoreEvent.EnqueueAsyncRetry(enqueueRetry)
}

abstract class Repl catedT etStoreEvent(val na : Str ng) extends T etStoreEvent {
  overr de def retryStrategy: RetryStrategy = T etStoreEvent.Repl catedEventLocalRetry
}

/**
 * A tra  for all T etStoreEvents that beco  T etEvents.
 */
tra  T etStoreT etEvent {
  val t  stamp: T  

  val optUser: Opt on[User]

  /**
   * Most T etStoreT etEvents map to a s ngle T etEvent, but so 
   * opt onally map to an event and ot rs map to mult ple events, so
   * t   thod needs to return a Seq of T etEventData.
   */
  def toT etEventData: Seq[T etEventData]
}

/**
 * T  abstract parent class for an event that  nd cates a part cular act on
 * for a part cular event that needs to be retr ed v a t  async-wr e-retry ng  chan sm.
 */
abstract class T etStoreRetryEvent[E <: AsyncT etStoreEvent] extends T etStoreEvent {
  overr de val na  = "async_wr e_retry"

  def act on: AsyncWr eAct on
  def event: E

  def eventType: AsyncWr eEventType

  def scr bedT etOnFa lure: Opt on[T et]

  overr de def retryStrategy: RetryStrategy =
    T etStoreEvent.LocalRetryT nScr beFa lure(act on =>
      Fa ledAsyncWr e(eventType, act on, scr bedT etOnFa lure))
}

/**
 * Funct ons as a d sjunct on bet en an event type E and  's corresond ng
 * retry event type T etStoreRetryEvent[E]
 */
case class T etStoreEventOrRetry[E <: AsyncT etStoreEvent](
  event: E,
  toRetry: Opt on[T etStoreRetryEvent[E]]) {
  def to n  al: Opt on[E] =  f (retryAct on. sDef ned) None else So (event)
  def retryAct on: Opt on[RetryStrategy] = toRetry.map(_.retryStrategy)
  def hydrate(f: E => Future[E]): Future[T etStoreEventOrRetry[E]] =
    f(event).map(e => copy(event = e))
}

object T etStoreEventOrRetry {
  def apply[E <: AsyncT etStoreEvent, R <: T etStoreRetryEvent[E]](
    event: E,
    retryAct on: Opt on[AsyncWr eAct on],
    toRetryEvent: (AsyncWr eAct on, E) => R
  ): T etStoreEventOrRetry[E] =
    T etStoreEventOrRetry(event, retryAct on.map(act on => toRetryEvent(act on, event)))

  object F rst {

    /** matc s aga nst T etStoreEventOrRetry  nstances for an  n  al event */
    def unapply[E <: AsyncT etStoreEvent]( : T etStoreEventOrRetry[E]): Opt on[E] =
       .to n  al
  }

  object Retry {

    /** matc s aga nst T etStoreEventOrRetry  nstances for a retry event */
    def unapply[E <: AsyncT etStoreEvent](
       : T etStoreEventOrRetry[E]
    ): Opt on[T etStoreRetryEvent[E]] =
       .toRetry
  }
}
