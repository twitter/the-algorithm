package com.tw ter.servo.cac 

 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.servo.cac .thr ftscala.Cac dValueStatus.DoNotCac 
 mport com.tw ter.servo.ut l.{Gate, Transfor r}
 mport com.tw ter.ut l.{Durat on, Return, Throw, T  }
 mport java.n o.ByteBuffer

object Cac d {

  pr vate[t ] val m ll sToT  : Long => T   =
    ms => T  .fromM ll seconds(ms)

  pr vate val t  ToM lls: T   => Long =
    t   => t  . nM ll seconds

  /**
   * Deser al ze a Cac dValue to a Cac d[V]
   *
   *  f t  ByteBuffer conta ned  n t  `cac dValue`  s backed by an `Array[Byte]` w h  s offset
   * at 0,   w ll apply t  ser al zer d rectly to t  back ng array for performance reasons.
   *
   * As such, t  `Ser al zer[V]` t  caller prov des MUST NOT mutate t  buffer    s g ven.
   * T  exhortat on  s also g ven  n com.tw ter.servo.ut l.Transfor r, but repeated  re.
   */
  def apply[V](cac dValue: Cac dValue, ser al zer: Ser al zer[V]): Cac d[V] = {
    val value: Opt on[V] = cac dValue.value match {
      case So (buf)  f buf.hasArray && buf.arrayOffset() == 0 =>
        ser al zer.from(buf.array).toOpt on
      case So (buf) =>
        val array = new Array[Byte](buf.rema n ng)
        buf.dupl cate.get(array)
        ser al zer.from(array).toOpt on
      case None => None
    }
    val status =
       f (cac dValue.value.nonEmpty && value. sEmpty)
        Cac dValueStatus.Deser al zat onFa led
      else
        cac dValue.status

    Cac d(
      value,
      status,
      T  .fromM ll seconds(cac dValue.cac dAtMsec),
      cac dValue.readThroughAtMsec.map(m ll sToT  ),
      cac dValue.wr tenThroughAtMsec.map(m ll sToT  ),
      cac dValue.doNotCac Unt lMsec.map(m ll sToT  ),
      cac dValue.softTtlStep
    )
  }
}

/**
 * A s mple  tadata wrapper for cac d values. T   s stored  n t  cac 
 * us ng t  [[com.tw ter.servo.cac .thr ftscala.Cac dValue]] struct, wh ch  s s m lar, but
 * untyped.
 */
case class Cac d[V](
  value: Opt on[V],
  status: Cac dValueStatus,
  cac dAt: T  ,
  readThroughAt: Opt on[T  ] = None,
  wr tenThroughAt: Opt on[T  ] = None,
  doNotCac Unt l: Opt on[T  ] = None,
  softTtlStep: Opt on[Short] = None) {

  /**
   * produce a new cac d value w h t  sa   tadata
   */
  def map[W](f: V => W): Cac d[W] = copy(value = value.map(f))

  /**
   * ser al ze to a Cac dValue
   */
  def toCac dValue(ser al zer: Ser al zer[V]): Cac dValue = {
    var ser al zedValue: Opt on[ByteBuffer] = None
    val cac dValueStatus = value match {
      case So (v) =>
        ser al zer.to(v) match {
          case Return(sv) =>
            ser al zedValue = So (ByteBuffer.wrap(sv))
            status
          case Throw(_) => Cac dValueStatus.Ser al zat onFa led
        }
      case None => status
    }

    Cac dValue(
      ser al zedValue,
      cac dValueStatus,
      cac dAt. nM ll seconds,
      readThroughAt.map(Cac d.t  ToM lls),
      wr tenThroughAt.map(Cac d.t  ToM lls),
      doNotCac Unt l.map(Cac d.t  ToM lls),
      softTtlStep
    )
  }

  /**
   * Resolves confl cts bet en a value be ng  nserted  nto cac  and a value already  n cac  by
   * us ng t  t   a cac d value was last updated.
   *  f t  cac d value has a wr tenThroughAt, returns  . Ot rw se returns readThroughAt, but
   *  f that doesn't ex st, returns cac dAt.
   * T  makes   favor wr es to reads  n t  event of a race cond  on.
   */
  def effect veUpdateT  [V](wr tenThroughBuffer: Durat on = 0.second): T   = {
    t .wr tenThroughAt match {
      case So (wta) => wta + wr tenThroughBuffer
      case None =>
        t .readThroughAt match {
          case So (rta) => rta
          case None => t .cac dAt
        }
    }
  }
}

/**
 * Sw ch bet en two cac  p ckers by prov d ng dec derable gate
 */
class Dec derableP cker[V](
  pr maryP cker: Lock ngCac .P cker[Cac d[V]],
  secondaryP cker: Lock ngCac .P cker[Cac d[V]],
  usePr mary: Gate[Un ],
  statsRece ver: StatsRece ver)
    extends Lock ngCac .P cker[Cac d[V]] {
  pr vate[t ] val stats = statsRece ver.scope("dec derable_p cker")
  pr vate[t ] val p ckerScope = stats.scope("p cker")
  pr vate[t ] val pr maryP ckerCount = p ckerScope.counter("pr mary")
  pr vate[t ] val secondaryP ckerCount = p ckerScope.counter("secondary")

  pr vate[t ] val p ckedScope = stats.scope("p cked_values")
  pr vate[t ] val p ckedValuesMatc d = p ckedScope.counter("matc d")
  pr vate[t ] val p ckedValuesM smatc d = p ckedScope.counter("m smatc d")

  overr de def apply(newValue: Cac d[V], oldValue: Cac d[V]): Opt on[Cac d[V]] = {
    val secondaryP ckerValue = secondaryP cker(newValue, oldValue)

     f (usePr mary()) {
      val pr maryP ckerValue = pr maryP cker(newValue, oldValue)

      pr maryP ckerCount. ncr()
       f (pr maryP ckerValue == secondaryP ckerValue) p ckedValuesMatc d. ncr()
      else p ckedValuesM smatc d. ncr()

      pr maryP ckerValue
    } else {
      secondaryP ckerCount. ncr()
      secondaryP ckerValue
    }
  }

  overr de def toStr ng(): Str ng = "Dec derableP cker"

}

/**
 *  's s m lar to t  PreferNe stCac d p cker, but   prefers wr ten-through value
 * over read-through as long as wr ten-through value + wr tenThroughExtra  s
 * ne r than read-through value. Sa  as  n PreferNe stCac d,  f values cac d
 * have t  sa  cac d  thod and t   p cker p cks t  new value.
 *
 *    ntends to solve race cond  on w n t  read and wr e requests co  at t 
 * sa  t  , but wr e requests  s gett ng cac d f rst and t n gett ng overr de w h
 * a stale value from t  read request.
 *
 *  f enabled gate  s d sabled,   falls back to PreferNe stCac d log c.
 *
 */
class PreferWr tenThroughCac d[V](
  wr tenThroughBuffer: Durat on = 1.second)
    extends PreferNe stCac d[V] {
  overr de def apply(newValue: Cac d[V], oldValue: Cac d[V]): Opt on[Cac d[V]] = {
    // t  t e goes to newValue
     f (oldValue.effect veUpdateT  (wr tenThroughBuffer) > newValue.effect veUpdateT  (
        wr tenThroughBuffer))
      None
    else
      So (newValue)
  }
  overr de def toStr ng(): Str ng = "PreferWr tenThroughCac d"
}

/**
 * prefer one value over anot r based on Cac d  tadata
 */
class PreferNe stCac d[V] extends Lock ngCac .P cker[Cac d[V]] {

  overr de def apply(newValue: Cac d[V], oldValue: Cac d[V]): Opt on[Cac d[V]] = {
     f (oldValue.effect veUpdateT  () > newValue.effect veUpdateT  ())
      None
    else
      So (newValue)
  }

  overr de def toStr ng(): Str ng = "PreferNe stCac d"
}

/**
 * Prefer non-empty values.  f a non-empty value  s  n cac , and t 
 * value to store  s empty, return t  non-empty value w h a fresh cac dAt
 *  nstead.
 */
class PreferNe stNonEmptyCac d[V] extends PreferNe stCac d[V] {
  overr de def apply(newValue: Cac d[V], oldValue: Cac d[V]) = {
    (newValue.value, oldValue.value) match {
      // So /So  and None/None cases are handled by t  super class
      case (So (_), So (_)) => super.apply(newValue, oldValue)
      case (None, None) => super.apply(newValue, oldValue)
      case (So (_), None) => So (newValue)
      case (None, So (_)) => So (oldValue.copy(cac dAt = T  .now))
    }
  }
}

/**
 * Prefer do not cac  entr es  f t y're not exp red. Ot rw se uses fallbackP cker
 * @param fallBackP cker t  p cker to use w n t  oldvalue  sn't do not cac  or  s exp red.
 *                       Defaults to PreferNe stCac .
 */
class PreferDoNotCac [V](
  fallBackP cker: Lock ngCac .P cker[Cac d[V]] = new PreferNe stCac d[V]: PreferNe stCac d[V],
  statsRece ver: StatsRece ver)
    extends Lock ngCac .P cker[Cac d[V]] {
  pr vate[t ] val p ckDoNotCac EntryCounter = statsRece ver.counter("p ck_do_not_cac _entry")
  pr vate[t ] val useFallbackCounter = statsRece ver.counter("use_fallback")
  overr de def apply(newValue: Cac d[V], oldValue: Cac d[V]): Opt on[Cac d[V]] = {
     f (oldValue.status == DoNotCac  && oldValue.doNotCac Unt l.forall(
        _ > newValue.effect veUpdateT  ())) { // evaluates to true  f dnc unt l  s None
      p ckDoNotCac EntryCounter. ncr()
      None
    } else {
      useFallbackCounter. ncr()
      fallBackP cker.apply(newValue, oldValue)
    }
  }
}

/**
 * A Transfor r of Cac d values composed of a Transfor r of t  underly ng values.
 */
class Cac dTransfor r[A, B](underly ng: Transfor r[A, B])
    extends Transfor r[Cac d[A], Cac d[B]] {
  def to(cac dA: Cac d[A]) = cac dA.value match {
    case None => Return(cac dA.copy(value = None))
    case So (a) =>
      underly ng.to(a) map { b =>
        cac dA.copy(value = So (b))
      }
  }

  def from(cac dB: Cac d[B]) = cac dB.value match {
    case None => Return(cac dB.copy(value = None))
    case So (b) =>
      underly ng.from(b) map { a =>
        cac dB.copy(value = So (a))
      }
  }
}
