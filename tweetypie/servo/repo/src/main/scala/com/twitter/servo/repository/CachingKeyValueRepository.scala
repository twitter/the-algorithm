package com.tw ter.servo.repos ory

 mport com.tw ter.logg ng.{Level, Logger}
 mport com.tw ter.servo.cac ._
 mport com.tw ter.servo.ut l.{Effect, Gate, RateL m  ngLogger}
 mport com.tw ter.ut l._
 mport scala.collect on.mutable
 mport scala.ut l.Random

/**
 * A set of classes that  nd cate how to handle cac d results.
 */
sealed abstract class Cac dResultAct on[+V]

object Cac dResultAct on {

  /**  nd cates a key should be fetc d from t  underly ng repo */
  case object HandleAsM ss extends Cac dResultAct on[Noth ng]

  /**  nd cates a key should be returned as not-found, and not fetc d from t  underly ng repo */
  case object HandleAsNotFound extends Cac dResultAct on[Noth ng]

  /**  nd cates t  value should be returned as found */
  case class HandleAsFound[V](value: V) extends Cac dResultAct on[V]

  /**  nd cates t  value should not be cac d */
  case object HandleAsDoNotCac  extends Cac dResultAct on[Noth ng]

  /**  nd cates that t  g ven act on should be appl ed, and t  g ven funct on appl ed to t  result ng value */
  case class TransformSubAct on[V](act on: Cac dResultAct on[V], f: V => V)
      extends Cac dResultAct on[V]

  /**  nd cates t  key should be returned as a fa lure */
  case class HandleAsFa led(t: Throwable) extends Cac dResultAct on[Noth ng]

  /**  nd cates that t  value should be refetc d asynchronously, be  m d ately treated
   * as t  g ven act on. */
  case class SoftExp rat on[V](act on: Cac dResultAct on[V]) extends Cac dResultAct on[V]
}

/**
 * A set of classes represent ng t  var ous states for a cac d result.
 */
sealed abstract class Cac dResult[+K, +V] {
  def key: K
}

object Cac dResult {
   mport Cac dResultAct on._

  /**  nd cates t  key was not  n cac  */
  case class NotFound[K](key: K) extends Cac dResult[K, Noth ng]

  /**  nd cates t re was an error fetch ng t  key */
  case class Fa led[K](key: K, t: Throwable) extends Cac dResult[K, Noth ng]

  /**  nd cates t  cac d value could not be deser al zed */
  case class Deser al zat onFa led[K](key: K) extends Cac dResult[K, Noth ng]

  /**  nd cates t  cac d value could not be ser al zed */
  case class Ser al zat onFa led[K](key: K) extends Cac dResult[K, Noth ng]

  /**  nd cates that a NotFound tombstone was found  n cac d */
  case class Cac dNotFound[K](
    key: K,
    cac dAt: T  ,
    softTtlStep: Opt on[Short] = None)
      extends Cac dResult[K, Noth ng]

  /**  nd cates that a Deleted tombstone was found  n cac d */
  case class Cac dDeleted[K](
    key: K,
    cac dAt: T  ,
    softTtlStep: Opt on[Short] = None)
      extends Cac dResult[K, Noth ng]

  /**  nd cates that value was found  n cac d */
  case class Cac dFound[K, V](
    key: K,
    value: V,
    cac dAt: T  ,
    softTtlStep: Opt on[Short] = None)
      extends Cac dResult[K, V]

  /**  nd cates that value should not be cac d unt l */
  case class DoNotCac [K](key: K, unt l: Opt on[T  ]) extends Cac dResult[K, Noth ng]

  type Handler[K, V] = Cac dResult[K, V] => Cac dResultAct on[V]

  type Part alHandler[K, V] = Cac dResult[K, V] => Opt on[Cac dResultAct on[V]]

  type HandlerFactory[Q, K, V] = Q => Handler[K, V]

  /**
   * compan on object for Handler type
   */
  object Handler {

    /**
     * term nate a Part alHandler to produce a new Handler
     */
    def apply[K, V](
      part al: Part alHandler[K, V],
      handler: Handler[K, V] = defaultHandler[K, V]
    ): Handler[K, V] = { cac dResult =>
      part al(cac dResult) match {
        case So (s) => s
        case None => handler(cac dResult)
      }
    }
  }

  /**
   * compan on object for Part alHandler type
   */
  object Part alHandler {

    /**
     * Sugar to produce a Part alHandler from a Part alFunct on. Success ve calls to
     *  sDef ned MUST return t  sa  result. Ot rw se, take t  syntax h  and w re
     * up y  own Part alHandler.
     */
    def apply[K, V](
      part al: Part alFunct on[Cac dResult[K, V], Cac dResultAct on[V]]
    ): Part alHandler[K, V] = part al.l ft

    /**
     * cha n one Part alHandler after anot r to produce a new Part alHandler
     */
    def orElse[K, V](
      t Handler: Part alHandler[K, V],
      thatHandler: Part alHandler[K, V]
    ): Part alHandler[K, V] = { cac dResult =>
      t Handler(cac dResult) match {
        case so  @ So (_) => so 
        case None => thatHandler(cac dResult)
      }
    }
  }

  /**
   * compan on object for HandlerFactory type
   */
  object HandlerFactory {
    def apply[Q, K, V](handler: Handler[K, V]): HandlerFactory[Q, K, V] = _ => handler
  }

  def defaultHandlerFactory[Q, K, V]: HandlerFactory[Q, K, V] =
    HandlerFactory[Q, K, V](defaultHandler)

  /**
   * T   s t  default Handler. Fa lures are treated as m sses.
   */
  def defaultHandler[K, V]: Handler[K, V] = {
    case NotFound(_) | Fa led(_, _) => HandleAsM ss
    case Deser al zat onFa led(_) | Ser al zat onFa led(_) => HandleAsM ss
    case Cac dNotFound(_, _, _) | Cac dDeleted(_, _, _) => HandleAsNotFound
    case Cac dFound(_, value, _, _) => HandleAsFound(value)
    case DoNotCac (_, So (t  ))  f T  .now > t   => HandleAsM ss
    case DoNotCac (_, _) => HandleAsDoNotCac 
  }

  /**
   * A Part alHandler that bubbles  mcac  fa lures up  nstead of convert ng
   * those fa lures to m sses.
   */
  def fa luresAreFa lures[K, V] = Part alHandler[K, V] {
    case Fa led(_, t) => HandleAsFa led(t)
  }

  /**
   * A Part alHandler that doesn't attempt to wr e back to cac   f t   n  al
   * cac  read fa led, but st ll fetc s from t  underly ng repo.
   */
  def fa luresAreDoNotCac [K, V] = Part alHandler[K, V] {
    case Fa led(_, _) => HandleAsDoNotCac 
  }

  /**
   * A funct on that takes a cac dAt t   and ttl, and returns an exp ry t  .  T  funct on
   * _must_ be determ n st c w h respect to t  argu nts prov ded, ot rw se,   m ght get a
   * MatchError w n us ng t  w h softTtlExp rat on.
   */
  type Exp ry = (T  , Durat on) => T  

  /**
   * An Exp ry funct on w h an eps lon of zero.
   */
  val f xedExp ry: Exp ry = (cac dAt: T  , ttl: Durat on) => cac dAt + ttl

  /**
   * A repeatable "random" exp ry funct on that perturbs t  ttl w h a random value
   * no greater than +/-(ttl * maxFactor).
   */
  def randomExp ry(maxFactor: Float): Exp ry = {
     f (maxFactor == 0) {
      f xedExp ry
    } else { (cac dAt: T  , ttl: Durat on) =>
      {
        val factor = (2 * new Random(cac dAt. nM ll seconds).nextFloat - 1) * maxFactor
        cac dAt + ttl + Durat on.fromNanoseconds((factor * ttl. nNanoseconds).toLong)
      }
    }
  }

  /**
   * soft-exp res Cac dFound and Cac dNotFound based on a ttl.
   *
   * @param ttl
   *  values older than t  w ll be cons dered exp red, but st ll
   *  returned, and asynchronously refres d  n cac .
   * @param exp ry
   *  (opt onal) funct on to compute t  exp ry t  
   */
  def softTtlExp rat on[K, V](
    ttl: Durat on,
    exp ry: Exp ry = f xedExp ry
  ): Part alHandler[K, V] =
    softTtlExp rat on(_ => ttl, exp ry)

  /**
   * soft-exp res Cac dFound and Cac dNotFound based on a ttl der ved from t  value
   *
   * @param ttl
   *  values older than t  w ll be cons dered exp red, but st ll
   *  returned, and asynchronously refres d  n cac .
   * @param exp ry
   *  (opt onal) funct on to compute t  exp ry t  
   */
  def softTtlExp rat on[K, V](
    ttl: Opt on[V] => Durat on,
    exp ry: Exp ry
  ): Part alHandler[K, V] = Part alHandler[K, V] {
    case Cac dFound(_, value, cac dAt, _)  f exp ry(cac dAt, ttl(So (value))) < T  .now =>
      SoftExp rat on(HandleAsFound(value))
    case Cac dNotFound(_, cac dAt, _)  f exp ry(cac dAt, ttl(None)) < T  .now =>
      SoftExp rat on(HandleAsNotFound)
  }

  /**
   * soft-exp res Cac dFound and Cac dNotFound based on a ttl der ved from both t  value
   * and t  softTtlStep
   *
   * @param ttl
   *   values older than t  w ll be cons dered exp red, but st ll returned, and
   *  asynchronously refres d  n cac .
   * @param exp ry
   *   (opt onal) funct on to compute t  exp ry t  
   */
  def steppedSoftTtlExp rat on[K, V](
    ttl: (Opt on[V], Opt on[Short]) => Durat on,
    exp ry: Exp ry = f xedExp ry
  ): Part alHandler[K, V] = Part alHandler[K, V] {
    case Cac dFound(_, value, cac dAt, softTtlStep)
         f exp ry(cac dAt, ttl(So (value), softTtlStep)) < T  .now =>
      SoftExp rat on(HandleAsFound(value))
    case Cac dNotFound(_, cac dAt, softTtlStep)
         f exp ry(cac dAt, ttl(None, softTtlStep)) < T  .now =>
      SoftExp rat on(HandleAsNotFound)
    case Cac dDeleted(_, cac dAt, softTtlStep)
         f exp ry(cac dAt, ttl(None, softTtlStep)) < T  .now =>
      SoftExp rat on(HandleAsNotFound)
  }

  /**
   * hard-exp res Cac dFound and Cac dNotFound based on a ttl.
   *
   * @param ttl
   *  values older than t  w ll be cons dered a m ss
   * @param exp ry
   *  (opt onal) funct on to compute t  exp ry t  
   */
  def hardTtlExp rat on[K, V](
    ttl: Durat on,
    exp ry: Exp ry = f xedExp ry
  ): Part alHandler[K, V] =
    hardTtlExp rat on(_ => ttl, exp ry)

  /**
   * hard-exp res Cac dFound and Cac dNotFound based on a ttl der ved from t  value
   *
   * @param ttl
   *  values older than t  w ll be cons dered a m ss
   * @param exp ry
   *  (opt onal) funct on to compute t  exp ry t  
   */
  def hardTtlExp rat on[K, V](
    ttl: Opt on[V] => Durat on,
    exp ry: Exp ry
  ): Part alHandler[K, V] = Part alHandler[K, V] {
    case Cac dFound(_, value, cac dAt, _)  f exp ry(cac dAt, ttl(So (value))) < T  .now =>
      HandleAsM ss
    case Cac dNotFound(_, cac dAt, _)  f exp ry(cac dAt, ttl(None)) < T  .now =>
      HandleAsM ss
  }

  /**
   * hard-exp res a Cac dNotFound tombstone based on a ttl
   *
   * @param ttl
   *  values older than t  w ll be cons dered exp red
   * @param exp ry
   *  (opt onal) funct on to compute t  exp ry t  
   */
  def notFoundHardTtlExp rat on[K, V](
    ttl: Durat on,
    exp ry: Exp ry = f xedExp ry
  ): Part alHandler[K, V] = Part alHandler[K, V] {
    case Cac dNotFound(_, cac dAt, _) =>
       f (exp ry(cac dAt, ttl) < T  .now)
        HandleAsM ss
      else
        HandleAsNotFound
  }

  /**
   * hard-exp res a Cac dDeleted tombstone based on a ttl
   *
   * @param ttl
   *  values older than t  w ll be cons dered exp red
   * @param exp ry
   *  (opt onal) funct on to compute t  exp ry t  
   */
  def deletedHardTtlExp rat on[K, V](
    ttl: Durat on,
    exp ry: Exp ry = f xedExp ry
  ): Part alHandler[K, V] = Part alHandler[K, V] {
    case Cac dDeleted(_, cac dAt, _) =>
       f (exp ry(cac dAt, ttl) < T  .now)
        HandleAsM ss
      else
        HandleAsNotFound
  }

  /**
   * read only from cac , never fall back to underly ng KeyValueRepos ory
   */
  def cac Only[K, V]: Handler[K, V] = {
    case Cac dFound(_, value, _, _) => HandleAsFound(value)
    case _ => HandleAsNotFound
  }

  /**
   * use e  r pr mary or backup Handler, depend ng on usePr mary result
   *
   * @param pr maryHandler
   *   t  handler to be used  f usePr mary evaluates to true
   * @param backupHandler
   *   t  handle to be used  f usePr mary evaluates to false
   * @param usePr mary
   *   evaluates t  query to determ ne wh ch handler to use
   */
  def sw c dHandlerFactory[Q, K, V](
    pr maryHandler: Handler[K, V],
    backupHandler: Handler[K, V],
    usePr mary: Q => Boolean
  ): HandlerFactory[Q, K, V] = { query =>
     f (usePr mary(query))
      pr maryHandler
    else
      backupHandler
  }
}

object Cac ResultObserver {
  case class Cach ngRepos oryResult[K, V](
    resultFromCac : KeyValueResult[K, Cac d[V]],
    resultFromCac M ssReadthrough: KeyValueResult[K, V],
    resultFromSoftTtlReadthrough: KeyValueResult[K, V])
  def un [K, V] = Effect.un [Cach ngRepos oryResult[K, V]]
}

object Cach ngKeyValueRepos ory {
  type Cac ResultObserver[K, V] = Effect[Cac ResultObserver.Cach ngRepos oryResult[K, V]]
}

/**
 * Reads keyed values from a Lock ngCac , and reads through to an underly ng
 * KeyValueRepos ory for m sses. supports a "soft ttl", beyond wh ch values
 * w ll be read through out-of-band to t  or g nat ng request
 *
 * @param underly ng
 * t  underly ng KeyValueRepos ory
 * @param cac 
 * t  lock ng cac  to read from
 * @param newQuery
 * a funct on for convert ng a subset of t  keys of t  or g nal query  nto a new
 * query.  t   s used to construct t  query passed to t  underly ng repos ory
 * to fetch t  cac  m sses.
 * @param handlerFactory
 * A factory to produce funct ons that spec fy pol c es about how to handle results
 * from cac . ( .e. to handle fa lures as m sses vs fa lures, etc)
 * @param p cker
 * used to choose bet en t  value  n cac  and t  value read from t  DB w n
 * stor ng values  n t  cac 
 * @param observer
 * a Cac Observer for collect ng cac  stat st cs*
 * @param wr eSoftTtlStep
 * Wr e t  soft_ttl_step value to  nd cate number of cons stent reads from underly ng store
 * @param cac ResultObserver
 * An [[Effect]] of type [[Cac ResultObserver.Cach ngRepos oryResult]] wh ch  s useful for exam n ng
 * t  results from t  cac , underly ng storage, and any later read-throughs. T  effect  s
 * executed asynchronously from t  request path and has no bear ng on t  Future[KeyValueResult]*
 * returned from t  Repos ory.
 */
class Cach ngKeyValueRepos ory[Q <: Seq[K], K, V](
  underly ng: KeyValueRepos ory[Q, K, V],
  val cac : Lock ngCac [K, Cac d[V]],
  newQuery: SubqueryBu lder[Q, K],
  handlerFactory: Cac dResult.HandlerFactory[Q, K, V] =
    Cac dResult.defaultHandlerFactory[Q, K, V],
  p cker: Lock ngCac .P cker[Cac d[V]] = new PreferNe stCac d[V]: PreferNe stCac d[V],
  observer: Cac Observer = NullCac Observer,
  wr eSoftTtlStep: Gate[Un ] = Gate.False,
  cac ResultObserver: Cach ngKeyValueRepos ory.Cac ResultObserver[K, V] =
    Cac ResultObserver.un [K, V]: Effect[Cac ResultObserver.Cach ngRepos oryResult[K, V]])
    extends KeyValueRepos ory[Q, K, V] {
   mport Cac dResult._
   mport Cac dResultAct on._

  protected[t ] val log = Logger.get(getClass.getS mpleNa )
  pr vate[t ] val rateL m edLogger = new RateL m  ngLogger(logger = log)

  protected[t ] val effect veCac Stats = observer.scope("effect ve")

  /**
   * Calculates t  softTtlStep based on result from cac  and underly ng store.
   * T  softTtlStep  nd cates how many t  s   have
   * perfor d & recorded a cons stent read-through.
   * A value of None  s equ valent to So (0) -    nd cates zero cons stent read-throughs.
   */
  protected[t ] def updateSoftTtlStep(
    underly ngResult: Opt on[V],
    cac dResult: Cac d[V]
  ): Opt on[Short] = {
     f (wr eSoftTtlStep() && underly ngResult == cac dResult.value) {
      cac dResult.softTtlStep match {
        case So (step)  f step < Short.MaxValue => So ((step + 1).toShort)
        case So (step)  f step == Short.MaxValue => cac dResult.softTtlStep
        case _ => So (1)
      }
    } else {
      None
    }
  }

  protected case class ProcessedCac Result(
    h s: Map[K, V],
    m sses: Seq[K],
    doNotCac : Set[K],
    fa lures: Map[K, Throwable],
    tombstones: Set[K],
    softExp rat ons: Seq[K],
    transforms: Map[K, (V => V)])

  overr de def apply(keys: Q): Future[KeyValueResult[K, V]] = {
    getFromCac (keys).flatMap { cac Result =>
      val ProcessedCac Result(
        h s,
        m sses,
        doNotCac ,
        fa lures,
        tombstones,
        softExp rat ons,
        transforms
      ) =
        process(keys, cac Result)

       f (log. sLoggable(Level.TRACE)) {
        log.trace(
          "Cach ngKVR.apply keys %d h  %d m ss %d noCac  %d fa lure %d " +
            "tombstone %d softexp %d",
          keys.s ze,
          h s.s ze,
          m sses.s ze,
          doNotCac .s ze,
          fa lures.s ze,
          tombstones.s ze,
          softExp rat ons.s ze
        )
      }
      recordCac Stats(
        keys,
        notFound = m sses.toSet,
        doNotCac  = doNotCac ,
        exp red = softExp rat ons.toSet,
        numFa lures = fa lures.s ze,
        numTombstones = tombstones.s ze
      )

      // now read through all notFound
      val underly ngQuery = newQuery(m sses ++ doNotCac , keys)
      val wr eToCac Query =  f (doNotCac .nonEmpty) newQuery(m sses, keys) else underly ngQuery
      val futureFromUnderly ng = readThrough(underly ngQuery, wr eToCac Query)

      // async read-through for t  exp red results,  gnore results
      val softExp rat onQuery = newQuery(softExp rat ons, keys)
      val futureFromSoftExp ry = readThrough(softExp rat onQuery, softExp rat onQuery, cac Result)

      //  rge all results toget r
      for {
        fromUnderly ng <- futureFromUnderly ng
        fromCac  = KeyValueResult(h s, tombstones, fa lures)
        fromUnderly ngTransfor d = transformResults(fromUnderly ng, transforms)
      } y eld {
        futureFromSoftExp ry.onSuccess { readThroughResults =>
          cac ResultObserver(
            Cac ResultObserver.Cach ngRepos oryResult(
              cac Result,
              fromUnderly ngTransfor d,
              readThroughResults
            )
          )
        }
        KeyValueResult.sum(Seq(fromCac , fromUnderly ngTransfor d))
      }
    }
  }

  /**
   * G ven results and a map of keys to transform funct ons, apply those transform funct ons
   * to t  found results.
   */
  protected[t ] def transformResults(
    results: KeyValueResult[K, V],
    transforms: Map[K, (V => V)]
  ): KeyValueResult[K, V] = {
     f (transforms. sEmpty) {
      results
    } else {
      results.copy(found = results.found.map {
        case (key, value) =>
          (key, transforms.get(key).map(_(value)).getOrElse(value))
      })
    }
  }

  protected[t ] def getFromCac (keys: Seq[K]): Future[KeyValueResult[K, Cac d[V]]] = {
    val un queKeys = keys.d st nct
    cac .get(un queKeys) handle {
      case t: Throwable =>
        rateL m edLogger.logThrowable(t, "except on caught  n cac  get")

        // treat total cac  fa lure as a fetch that returned all fa lures
        KeyValueResult(fa led = un queKeys.map { _ -> t }.toMap)
    }
  }

  /**
   * Buckets cac  results accord ng to t  w s s of t  Cac dResultHandler
   */
  protected[t ] def process(
    keys: Q,
    cac Result: KeyValueResult[K, Cac d[V]]
  ): ProcessedCac Result = {
    val cac dResultHandler = handlerFactory(keys)

    val h s = Map.newBu lder[K, V]
    val m sses = new mutable.ArrayBuffer[K]
    val fa lures = Map.newBu lder[K, Throwable]
    val tombstones = Set.newBu lder[K]
    val softExp redKeys = new mutable.L stBuffer[K]
    val doNotCac  = Set.newBu lder[K]
    val transforms = Map.newBu lder[K, (V => V)]

    for (key <- keys) {
      val cac dResult = cac Result(key) match {
        case Throw(t) => Fa led(key, t)
        case Return(None) => NotFound(key)
        case Return(So (cac d)) =>
          cac d.status match {
            case Cac dValueStatus.Found =>
              cac d.value match {
                case None => NotFound(key)
                case So (value) =>
                  Cac dFound(
                    key,
                    value,
                    cac d.cac dAt,
                    cac d.softTtlStep
                  )
              }
            case Cac dValueStatus.NotFound => Cac dNotFound(key, cac d.cac dAt)
            case Cac dValueStatus.Deleted => Cac dDeleted(key, cac d.cac dAt)
            case Cac dValueStatus.Ser al zat onFa led => Ser al zat onFa led(key)
            case Cac dValueStatus.Deser al zat onFa led => Deser al zat onFa led(key)
            case Cac dValueStatus.Ev cted => NotFound(key)
            case Cac dValueStatus.DoNotCac  => DoNotCac (key, cac d.doNotCac Unt l)
          }
      }

      def processAct on(act on: Cac dResultAct on[V]): Un  = {
        act on match {
          case HandleAsM ss => m sses += key
          case HandleAsFound(value) => h s += key -> value
          case HandleAsNotFound => tombstones += key
          case HandleAsDoNotCac  => doNotCac  += key
          case HandleAsFa led(t) => fa lures += key -> t
          case TransformSubAct on(subAct on, f) =>
            transforms += key -> f
            processAct on(subAct on)
          case SoftExp rat on(subAct on) =>
            softExp redKeys += key
            processAct on(subAct on)
        }
      }

      processAct on(cac dResultHandler(cac dResult))
    }

    ProcessedCac Result(
      h s.result(),
      m sses,
      doNotCac .result(),
      fa lures.result(),
      tombstones.result(),
      softExp redKeys,
      transforms.result()
    )
  }

  protected[t ] def recordCac Stats(
    keys: Seq[K],
    notFound: Set[K],
    doNotCac : Set[K],
    exp red: Set[K],
    numFa lures:  nt,
    numTombstones:  nt
  ): Un  = {
    keys.foreach { key =>
      val wasntFound = notFound.conta ns(key)
      val keyStr ng = key.toStr ng
       f (wasntFound || exp red.conta ns(key))
        effect veCac Stats.m ss(keyStr ng)
      else
        effect veCac Stats.h (keyStr ng)

       f (wasntFound)
        observer.m ss(keyStr ng)
      else
        observer.h (keyStr ng)
    }
    observer.exp red(exp red.s ze)
    observer.fa lure(numFa lures)
    observer.tombstone(numTombstones)
    observer.noCac (doNotCac .s ze)
  }

  /**
   * read through to t  underly ng repos ory
   *
   * @param cac Keys
   *   t  keys to read and cac 
   */
  def readThrough(cac Keys: Q): Future[KeyValueResult[K, V]] = {
    readThrough(cac Keys, cac Keys)
  }

  /**
   * read through to t  underly ng repos ory
   *
   * @param wr eToCac Query
   *   t  query to pass to t  wr eToCac   thod after gett ng a result back from t 
   *   underly ng repos ory.  t  query can be exactly t  sa  as underly ngQuery  f
   *   all readThrough keys should be cac d, or   may conta n a subset of t  keys  f
   *   so  keys should not be wr ten back to cac .
   * @param cac Result
   *   t  current cac  results for underly ngQuery.
   */
  def readThrough(
    underly ngQuery: Q,
    wr eToCac Query: Q,
    cac Result: KeyValueResult[K, Cac d[V]] = KeyValueResult.empty
  ): Future[KeyValueResult[K, V]] = {
     f (underly ngQuery. sEmpty) {
      KeyValueResult.emptyFuture
    } else {
      underly ng(underly ngQuery).onSuccess { result =>
         f (wr eToCac Query.nonEmpty) {
          wr eToCac (wr eToCac Query, result, cac Result)
        }
      }
    }
  }

  /**
   * Wr es t  contents of t  g ven KeyValueResult to cac .
   */
  def wr eToCac (
    keys: Q,
    underly ngResult: KeyValueResult[K, V],
    cac Result: KeyValueResult[K, Cac d[V]] = KeyValueResult[K, Cac d[V]]()
  ): Un  = {
    lazy val cac dEmpty = {
      val now = T  .now
      Cac d[V](None, Cac dValueStatus.NotFound, now, So (now), softTtlStep = None)
    }

    keys.foreach { key =>
      // only cac  Returns from t  underly ng repo, sk p Throws.
      //  ff cac d value matc s value from underly ng store
      // (for both NotFound and Found results),  ncre nt softTtlStep
      // ot rw se, set softTtlStep to None
      underly ngResult(key) match {
        case Return(optUnderly ngVal) =>
          val softTtlStep =
            cac Result(key) match {
              case Return(So (cac Val)) => updateSoftTtlStep(optUnderly ngVal, cac Val)
              case _ => None
            }

          val status =
            optUnderly ngVal match {
              case So (_) => Cac dValueStatus.Found
              case None => Cac dValueStatus.NotFound
            }

          val cac d =
            cac dEmpty.copy(
              value = optUnderly ngVal,
              status = status,
              softTtlStep = softTtlStep
            )

          cac 
            .lockAndSet(key, Lock ngCac .P ck ngHandler(cac d, p cker))
            .onFa lure {
              case t: Throwable =>
                rateL m edLogger.logThrowable(t, "except on caught  n lockAndSet")
            }

        case Throw(_) => None
      }
    }
  }
}
