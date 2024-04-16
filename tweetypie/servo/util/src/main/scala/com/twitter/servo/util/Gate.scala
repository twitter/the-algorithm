package com.tw ter.servo.ut l

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.ut l.{Durat on, T  }
 mport java.ut l.concurrent.ThreadLocalRandom
 mport scala.language. mpl c Convers ons

object Gate {

  /**
   * Construct a new Gate from a boolean funct on and a str ng representat on
   */
  def apply[T](f: T => Boolean, repr: => Str ng): Gate[T] =
    new Gate[T] {
      overr de def apply[U](u: U)( mpl c  asT: <:<[U, T]): Boolean = f(asT(u))
      overr de def toStr ng: Str ng = repr
    }

  /**
   * Construct a new Gate from a boolean funct on
   */
  def apply[T](f: T => Boolean): Gate[T] = Gate(f, "Gate(" + f + ")")

  /**
   * Create a Gate[Any] w h a probab l y of return ng true
   * that  ncreases l nearly w h t  ava lab l y, wh ch should range from 0.0 to 1.0.
   */
  def fromAva lab l y(
    ava lab l y: => Double,
    randomDouble: => Double = ThreadLocalRandom.current().nextDouble(),
    repr: Str ng = "Gate.fromAva lab l y"
  ): Gate[Any] =
    Gate(_ => randomDouble < math.max(math.m n(ava lab l y, 1.0), 0.0), repr)

  /**
   * Creates a Gate[Any] w h a probab l y of return ng true that
   *  ncreases l nearly  n t   bet en startT   and (startT   + rampUpDurat on).
   */
  def l nearRampUp(
    startT  : T  ,
    rampUpDurat on: Durat on,
    randomDouble: => Double = ThreadLocalRandom.current().nextDouble()
  ): Gate[Any] = {
    val ava lab l y = ava lab l yFromL nearRampUp(startT  , rampUpDurat on)

    fromAva lab l y(
      ava lab l y(T  .now),
      randomDouble,
      repr = "Gate.rampUp(" + startT   + ", " + rampUpDurat on + ")"
    )
  }

  /**
   * Generates an ava lab l y funct on that maps a po nt  n t   to an ava lab l y value
   *  n t  range of 0.0 - 1.0.  Ava lab l y  s 0  f t  g ven t    s before startT  ,  s
   * 1  f t  great r than (startT   + rampUpDurat on), and  s ot rw se l nearly
   *  nterpolated bet en 0.0 and 1.0 as t  t   moves through t  two endpo nts.
   */
  def ava lab l yFromL nearRampUp(startT  : T  , rampUpDurat on: Durat on): T   => Double = {
    val endT   = startT   + rampUpDurat on
    val rampUpM ll s = rampUpDurat on. nM ll seconds.toDouble
    now => {
       f (now >= endT  ) {
        1.0
      } else  f (now <= startT  ) {
        0.0
      } else {
        (now - startT  ). nM ll seconds.toDouble / rampUpM ll s
      }
    }
  }

  /**
   * Returns a gate that  ncre nts true / false counters for each Gate  nvocat on.  Counter na 
   * can be overr dden w h trueNa  and falseNa .
   */
  def observed[T](
    gate: Gate[T],
    stats: StatsRece ver,
    trueNa : Str ng = "true",
    falseNa : Str ng = "false"
  ): Gate[T] = {
    val trueCount = stats.counter(trueNa )
    val falseCount = stats.counter(falseNa )
    gate
      .onTrue[T] { _ =>
        trueCount. ncr()
      }
      .onFalse[T] { _ =>
        falseCount. ncr()
      }
  }

  /**
   * Construct a new Gate from a boolean value
   */
  def const(v: Boolean): Gate[Any] = Gate(_ => v, v.toStr ng)

  /**
   * Constructs a new Gate that returns true  f any of t  gates  n t   nput l st return true.
   * Always returns false w n t   nput l st  s empty.
   */
  def any[T](gates: Gate[T]*): Gate[T] = gates.foldLeft[Gate[T]](Gate.False)(_ | _)

  /**
   * Constructs a new Gate that returns true  ff all t  gates  n t   nput l st return true.
   * Always returns true w n t   nput l st  s empty.
   */
  def all[T](gates: Gate[T]*): Gate[T] = gates.foldLeft[Gate[T]](Gate.True)(_ & _)

  /**
   * Gates that always return true/false
   */
  val True: Gate[Any] = const(true)
  val False: Gate[Any] = const(false)

  //  mpl c  convers ons to downcast Gate to a pla n funct on
   mpl c  def gate2funct on1[T](g: Gate[T]): T => Boolean = g(_)
   mpl c  def gate2funct on0(g: Gate[Un ]): () => Boolean = () => g(())
}

/**
 * A funct on from T to Boolean, composable w h boolean-l ke operators.
 * Also supports bu ld ng h g r-order funct ons
 * for d spatch ng based upon t  value of t  funct on over values of type T.
 * Note: Gate does not  n r  from T => Boolean  n order to enforce correct type c ck ng
 *  n t  apply  thod of Gate[Un ]. (Scala  s over eager to convert t  return type of
 * express on to Un .)  nstead, an  mpl c  convers on allows Gate to be used  n  thods that
 * requ re a funct on T => Boolean.
 */
tra  Gate[-T] {

  /**
   * A funct on from T => boolean w h str ct type bounds
   */
  def apply[U](u: U)( mpl c  asT: <:<[U, T]): Boolean

  /**
   * A nullary var ant of apply that can be used w n T  s a Un 
   */
  def apply()( mpl c   sUn : <:<[Un , T]): Boolean = apply( sUn (()))

  /**
   * Return a new Gate wh ch appl es t  g ven funct on and t n calls t  Gate
   */
  def contramap[U](f: U => T): Gate[U] = Gate(f andT n t , "%s.contramap(%s)".format(t , f))

  /**
   * Returns a new Gate of t  requested type that  gnores  s  nput
   */
  def on[U]( mpl c   sUn : <:<[Un , T]): Gate[U] = contramap((_: U) => ())

  /**
   * Returns a new Gate wh ch returns true w n t  Gate returns false
   */
  def unary_! : Gate[T] = Gate(x => !t (x), "!%s".format(t ))

  /**
   * Returns a new Gate wh ch returns true w n both t  Gate and ot r Gate return true
   */
  def &[U <: T](ot r: Gate[U]): Gate[U] =
    Gate(x => t (x) && ot r(x), "(%s & %s)".format(t , ot r))

  /**
   * Returns a new Gate wh ch returns true w n e  r t  Gate or ot r Gate return true
   */
  def |[U <: T](ot r: Gate[U]): Gate[U] =
    Gate(x => t (x) || ot r(x), "(%s | %s)".format(t , ot r))

  /**
   * Returns a new Gate wh ch returns true w n return values of t  Gate and ot r Gate d ffer
   */
  def ^[U <: T](ot r: Gate[U]): Gate[U] =
    Gate(x => t (x) ^ ot r(x), "(%s ^ %s)".format(t , ot r))

  /**
   * Returns t  f rst value w n t  Gate returns true, or t  second value  f   returns false.
   */
  def p ck[A](t: T, x: => A, y: => A): A =  f (t (t)) x else y

  /**
   * A var ent of p ck that doesn't requ re a value  f T  s a subtype of Un 
   */
  def p ck[A](x: => A, y: => A)( mpl c   sUn : <:<[Un , T]): A = p ck( sUn (()), x, y)

  /**
   * Returns a 1-arg funct on that dynam cally p cks x or y based upon t  funct on arg.
   */
  def select[A](x: => A, y: => A): T => A = p ck(_, x, y)

  /**
   * Returns a vers on of t  gate that runs t  effect  f t  gate returns true.
   */
  def onTrue[U <: T](f: U => Un ): Gate[U] =
    Gate { (t: U) =>
      val v = t (t)
       f (v) f(t)
      v
    }

  /**
   * Returns a vers on of t  gate that runs t  effect  f t  gate returns false.
   */
  def onFalse[U <: T](f: U => Un ): Gate[U] =
    Gate { (t: U) =>
      val v = t (t)
       f (!v) f(t)
      v
    }
}
