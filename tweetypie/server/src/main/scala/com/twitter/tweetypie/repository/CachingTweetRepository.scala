package com.tw ter.t etyp e
package repos ory

 mport com.fasterxml.jackson.datab nd.ObjectMapper
 mport com.fasterxml.jackson.module.scala.DefaultScalaModule
 mport com.tw ter.f nagle.trac ng.Trace
 mport com.tw ter.servo.cac ._
 mport com.tw ter.servo.repos ory._
 mport com.tw ter.servo.ut l.Transfor r
 mport com.tw ter.snowflake. d.Snowflake d
 mport com.tw ter.st ch.St ch
 mport com.tw ter.t etyp e.cl ent_ d.Cl ent d lper
 mport com.tw ter.t etyp e.core.F lteredState.Unava lable.BounceDeleted
 mport com.tw ter.t etyp e.core.F lteredState.Unava lable.T etDeleted
 mport com.tw ter.t etyp e.core._
 mport com.tw ter.t etyp e.repos ory.Cac dBounceDeleted. sBounceDeleted
 mport com.tw ter.t etyp e.repos ory.Cac dBounceDeleted.toBounceDeletedT etResult
 mport com.tw ter.t etyp e.thr ftscala.Cac dT et
 mport com.tw ter.ut l.Base64Long

case class T etKey(cac Vers on:  nt,  d: T et d)
    extends ScopedCac Key("t", "t", cac Vers on, Base64Long.toBase64( d))

case class T etKeyFactory(cac Vers on:  nt) {
  val from d: T et d => T etKey = ( d: T et d) => T etKey(cac Vers on,  d)
  val fromT et: T et => T etKey = (t et: T et) => from d(t et. d)
  val fromCac dT et: Cac dT et => T etKey = (ms: Cac dT et) => fromT et(ms.t et)
}

//  lper  thods for work ng w h cac d bounce-deleted t ets,
// grouped toget r  re to keep t  def n  ons of "bounce
// deleted"  n one place.
object Cac dBounceDeleted {
  // Cac dT et for use  n Cach ngT etStore
  def toBounceDeletedCac dT et(t et d: T et d): Cac dT et =
    Cac dT et(
      t et = T et( d = t et d),
       sBounceDeleted = So (true)
    )

  def  sBounceDeleted(cac d: Cac d[Cac dT et]): Boolean =
    cac d.status == Cac dValueStatus.Found &&
      cac d.value.flatMap(_. sBounceDeleted).conta ns(true)

  // T etResult for use  n Cach ngT etRepos ory
  def toBounceDeletedT etResult(t et d: T et d): T etResult =
    T etResult(
      T etData(
        t et = T et( d = t et d),
         sBounceDeleted = true
      )
    )

  def  sBounceDeleted(t etResult: T etResult): Boolean =
    t etResult.value. sBounceDeleted
}

object T etResultCac  {
  def apply(
    t etDataCac : Cac [T et d, Cac d[T etData]]
  ): Cac [T et d, Cac d[T etResult]] = {
    val transfor r: Transfor r[Cac d[T etResult], Cac d[T etData]] =
      new Transfor r[Cac d[T etResult], Cac d[T etData]] {
        def to(cac d: Cac d[T etResult]) =
          Return(cac d.map(_.value))

        def from(cac d: Cac d[T etData]) =
          Return(cac d.map(T etResult(_)))
      }

    new KeyValueTransform ngCac (
      t etDataCac ,
      transfor r,
       dent y
    )
  }
}

object T etDataCac  {
  def apply(
    cac dT etCac : Cac [T etKey, Cac d[Cac dT et]],
    t etKeyFactory: T et d => T etKey
  ): Cac [T et d, Cac d[T etData]] = {
    val transfor r: Transfor r[Cac d[T etData], Cac d[Cac dT et]] =
      new Transfor r[Cac d[T etData], Cac d[Cac dT et]] {
        def to(cac d: Cac d[T etData]) =
          Return(cac d.map(_.toCac dT et))

        def from(cac d: Cac d[Cac dT et]) =
          Return(cac d.map(c => T etData.fromCac dT et(c, cac d.cac dAt)))
      }

    new KeyValueTransform ngCac (
      cac dT etCac ,
      transfor r,
      t etKeyFactory
    )
  }
}

object TombstoneTtl {
   mport Cac dResult._

  def f xed(ttl: Durat on): Cac dNotFound[T et d] => Durat on =
    _ => ttl

  /**
   * A s mple ttl calculator that  s set to `m n`  f t  age  s less than `from`,
   * t n l nearly  nterpolated  bet en `m n` and `max` w n t  age  s bet en `from` and `to`,
   * and t n equal to `max`  f t  age  s greater than `to`.
   */
  def l near(
    m n: Durat on,
    max: Durat on,
    from: Durat on,
    to: Durat on
  ): Cac dNotFound[T et d] => Durat on = {
    val rate = (max - m n). nM ll seconds / (to - from). nM ll seconds.toDouble
    cac d => {
       f (Snowflake d. sSnowflake d(cac d.key)) {
        val age = cac d.cac dAt - Snowflake d(cac d.key).t  
         f (age <= from) m n
        else  f (age >= to) max
        else m n + (age - from) * rate
      } else {
        // W n  's not a snowflake  d, cac    for t  max mum t  .
        max
      }
    }
  }

  /**
   * C cks  f t  g ven `cac d` value  s an exp red tombstone
   */
  def  sExp red(
    tombstoneTtl: Cac dNotFound[T et d] => Durat on,
    cac d: Cac dNotFound[T et d]
  ): Boolean =
    T  .now - cac d.cac dAt > tombstoneTtl(cac d)
}

object Cach ngT etRepos ory {
   mport Cac dResult._
   mport Cac dResultAct on._

  val fa luresLog: Logger = Logger("com.tw ter.t etyp e.repos ory.Cach ngT etRepoFa lures")

  def apply(
    cac : Lock ngCac [T et d, Cac d[T etResult]],
    tombstoneTtl: Cac dNotFound[T et d] => Durat on,
    stats: StatsRece ver,
    cl ent d lper: Cl ent d lper,
    logCac Except ons: Gate[Un ] = Gate.False,
  )(
    underly ng: T etResultRepos ory.Type
  ): T etResultRepos ory.Type = {
    val cach ngRepo: ((T et d, T etQuery.Opt ons)) => St ch[T etResult] =
      Cac St ch[(T et d, T etQuery.Opt ons), T et d, T etResult](
        repo = underly ng.tupled,
        cac  = St chLock ngCac (
          underly ng = cac ,
          p cker = new T etRepoCac P cker[T etResult](_.value.cac dAt)
        ),
        queryToKey = _._1, // extract t et  d from (T et d, T etQuery.Opt ons)
        handler = mkHandler(tombstoneTtl, stats, logCac Except ons, cl ent d lper),
        cac able = cac able
      )

    (t et d, opt ons) =>
       f (opt ons.cac Control.readFromCac ) {
        cach ngRepo((t et d, opt ons))
      } else {
        underly ng(t et d, opt ons)
      }
  }

  val cac able: Cac St ch.Cac able[(T et d, T etQuery.Opt ons), T etResult] = {
    case ((t et d, opt ons), t etResult) =>
       f (!opt ons.cac Control.wr eToCac ) {
        None
      } else {
        t etResult match {
          // Wr e st ch.NotFound as a NotFound cac  entry
          case Throw(com.tw ter.st ch.NotFound) =>
            So (St chLock ngCac .Val.NotFound)

          // Wr e F lteredState.T etDeleted as a Deleted cac  entry
          case Throw(T etDeleted) =>
            So (St chLock ngCac .Val.Deleted)

          // Wr e BounceDeleted as a Found cac  entry, w h t  Cac dT et. sBounceDeleted flag.
          // servo.cac .thr ftscala.Cac dValueStatus.Deleted tombstones do not allow for stor ng
          // app-def ned  tadata.
          case Throw(BounceDeleted) =>
            So (St chLock ngCac .Val.Found(toBounceDeletedT etResult(t et d)))

          // Regular found t ets are not wr ten to cac   re -  nstead t  cac able result  s
          // wr ten to cac  v a T etHydrat on.cac Changes
          case Return(_: T etResult) => None

          // Don't wr e ot r except ons back to cac 
          case _ => None
        }
      }
  }

  object LogLens {
    pr vate[t ] val mapper = new ObjectMapper().reg sterModule(DefaultScalaModule)

    def log ssage(logger: Logger, cl ent d lper: Cl ent d lper, data: (Str ng, Any)*): Un  = {
      val allData = data ++ defaultData(cl ent d lper)
      val msg = mapper.wr eValueAsStr ng(Map(allData: _*))
      logger. nfo(msg)
    }

    pr vate def defaultData(cl ent d lper: Cl ent d lper): Seq[(Str ng, Any)] = {
      val v e r = Tw terContext()
      Seq(
        "cl ent_ d" -> cl ent d lper.effect veCl ent d,
        "trace_ d" -> Trace. d.trace d.toStr ng,
        "aud _ p" -> v e r.flatMap(_.aud  p),
        "appl cat on_ d" -> v e r.flatMap(_.cl entAppl cat on d),
        "user_agent" -> v e r.flatMap(_.userAgent),
        "aut nt cated_user_ d" -> v e r.flatMap(_.aut nt catedUser d)
      )
    }
  }

  def mkHandler(
    tombstoneTtl: Cac dNotFound[T et d] => Durat on,
    stats: StatsRece ver,
    logCac Except ons: Gate[Un ],
    cl ent d lper: Cl ent d lper,
  ): Handler[T et d, T etResult] = {
    val baseHandler = defaultHandler[T et d, T etResult]
    val cac ErrorState = Hydrat onState(mod f ed = false, cac ErrorEncountered = true)
    val cac dFoundCounter = stats.counter("cac d_found")
    val notFoundCounter = stats.counter("not_found")
    val cac dNotFoundAsNotFoundCounter = stats.counter("cac d_not_found_as_not_found")
    val cac dNotFoundAsM ssCounter = stats.counter("cac d_not_found_as_m ss")
    val cac dDeletedCounter = stats.counter("cac d_deleted")
    val cac dBounceDeletedCounter = stats.counter("cac d_bounce_deleted")
    val fa ledCounter = stats.counter("fa led")
    val ot rCounter = stats.counter("ot r")

    {
      case res @ Cac dFound(_, t etResult, _, _) =>
         f ( sBounceDeleted(t etResult)) {
          cac dBounceDeletedCounter. ncr()
          HandleAsFa led(F lteredState.Unava lable.BounceDeleted)
        } else {
          cac dFoundCounter. ncr()
          baseHandler(res)
        }

      case res @ NotFound(_) =>
        notFoundCounter. ncr()
        baseHandler(res)

      // exp res NotFound tombstones  f old enough
      case cac d @ Cac dNotFound(_, _, _) =>
         f (TombstoneTtl. sExp red(tombstoneTtl, cac d)) {
          cac dNotFoundAsM ssCounter. ncr()
          HandleAsM ss
        } else {
          cac dNotFoundAsNotFoundCounter. ncr()
          HandleAsNotFound
        }

      case Cac dDeleted(_, _, _) =>
        cac dDeletedCounter. ncr()
        HandleAsFa led(F lteredState.Unava lable.T etDeleted)

      // don't attempt to wr e back to cac  on a cac  read fa lure
      case Fa led(k, t) =>
        // After result  s found, mark   w h cac ErrorEncountered
        fa ledCounter. ncr()

         f (logCac Except ons()) {
          LogLens.log ssage(
            fa luresLog,
            cl ent d lper,
            "type" -> "cac _fa led",
            "t et_ d" -> k,
            "throwable" -> t.getClass.getNa 
          )
        }

        TransformSubAct on[T etResult](HandleAsDoNotCac , _.mapState(_ ++ cac ErrorState))

      case res =>
        ot rCounter. ncr()
        baseHandler(res)
    }

  }
}

/**
 * A Lock ngCac .P cker for use w h Cach ngT etRepos ory wh ch prevents overwr  ng values  n
 * cac  that are ne r than t  value prev ously read from cac .
 */
class T etRepoCac P cker[T](cac dAt: T => Opt on[T  ]) extends Lock ngCac .P cker[Cac d[T]] {
  pr vate val ne stP cker = new PreferNe stCac d[T]

  overr de def apply(newValue: Cac d[T], oldValue: Cac d[T]): Opt on[Cac d[T]] = {
    oldValue.status match {
      // never overwr e a `Deleted` tombstone v a read-through.
      case Cac dValueStatus.Deleted => None

      // only overwr e a `Found` value w h an update based off of that sa  cac  entry.
      case Cac dValueStatus.Found =>
        newValue.value.flatMap(cac dAt) match {
          //  f prevCac At  s t  sa  as oldValue.cac dAt, t n t  value  n cac  hasn't changed
          case So (prevCac dAt)  f prevCac dAt == oldValue.cac dAt => So (newValue)
          // ot rw se, t  value  n cac  has changed s nce   read  , so don't overwr e
          case _ => None
        }

      //   may h  an exp red/older tombstone, wh ch should be safe to overwr e w h a fresh
      // tombstone of a new value returned from Manhattan.
      case Cac dValueStatus.NotFound => ne stP cker(newValue, oldValue)

      //   shouldn't see any ot r Cac dValueStatus, but  f   do, play   safe and don't
      // overwr e (  w ll be as  f t  read that tr ggered t  never happened)
      case _ => None
    }
  }
}
