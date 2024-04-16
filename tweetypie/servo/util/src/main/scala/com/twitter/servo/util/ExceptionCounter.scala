package com.tw ter.servo.ut l

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.ut l.Future
 mport scala.collect on.mutable

/**
 * Categor zes an except on accord ng to so  cr er a.
 * n.b.  mple nted  n terms of l ft rat r than apply to avo d extra allocat ons w n
 * used w n l ft ng t  effect.
 */
tra  Except onCategor zer {
   mport Except onCategor zer._

  def l ft(effect: Effect[Category]): Effect[Throwable]

  def apply(t: Throwable): Set[Category] = {
    val s = mutable.Set.empty[Category]
    l ft(Effect(s += _))(t)
    s.toSet
  }

  /**
   * construct a new categor zer that prepends scope to all categor es returned by t  categor zer
   */
  def scoped(scope: Seq[Str ng]): Except onCategor zer =
     f (scope. sEmpty) {
      t 
    } else {
      val scope : Category => Category =  mo ze(scope ++ _)
      fromL ft(effect => l ft(effect.contramap(scope )))
    }

  /**
   * construct a new categor zer that returns t  un on of t  categor es returned by t  and that
   */
  def ++(that: Except onCategor zer): Except onCategor zer =
    fromL ft(effect => t .l ft(effect).also(that.l ft(effect)))

  /**
   * construct a new categor zer that only returns categor es for throwables match ng pred
   */
  def only f(pred: Throwable => Boolean): Except onCategor zer =
    fromL ft(l ft(_).only f(pred))
}

object Except onCategor zer {
  type Category = Seq[Str ng]

  def const(categor es: Set[Category]): Except onCategor zer = Except onCategor zer(_ => categor es)
  def const(c: Category): Except onCategor zer = const(Set(c))
  def const(s: Str ng): Except onCategor zer = const(Seq(s))

  def apply(fn: Throwable => Set[Category]): Except onCategor zer =
    new Except onCategor zer {
      def l ft(effect: Effect[Category]) = Effect[Throwable](t => fn(t).foreach(effect))
      overr de def apply(t: Throwable) = fn(t)
    }

  def fromL ft(fn: Effect[Category] => Effect[Throwable]): Except onCategor zer =
    new Except onCategor zer {
      def l ft(effect: Effect[Category]) = fn(effect)
    }

  def s ngular(fn: Throwable => Category): Except onCategor zer =
    fromL ft(_.contramap(fn))

  def s mple(fn: Throwable => Str ng): Except onCategor zer =
    s ngular(fn.andT n(Seq(_)))

  def default(
    na : Category = Seq("except ons"),
    san  zeClassna Cha n: Throwable => Seq[Str ng] = Throwable lper.san  zeClassna Cha n
  ): Except onCategor zer =
    Except onCategor zer.const(na ) ++
      Except onCategor zer.s ngular(san  zeClassna Cha n).scoped(na )
}

/**
 *  ncre nts a counter for each category returned by t  except on categor zer
 *
 * @param statsRece ver
 *   t  unscoped statsRece ver on wh ch to hang t  counters
 * @param categor zer
 *   A funct on that returns a l st of category na s that a throwable should be counted under.
 */
class Except onCounter(statsRece ver: StatsRece ver, categor zer: Except onCategor zer) {

  /**
   * alternat ve constructor for backwards compat b l y
   *
   * @param statsRece ver
   *   t  unscoped statsRece ver on wh ch to hang t  counters
   * @param na 
   *   t  counter na  for total except ons, and scope for  nd v dual
   *   except on counters. default value  s `except ons`
   * @param san  zeClassna Cha n
   *   A funct on that can be used to cleanup classna s before pass ng t m to t  StatsRece ver.
   */
  def t (
    statsRece ver: StatsRece ver,
    na : Str ng,
    san  zeClassna Cha n: Throwable => Seq[Str ng]
  ) =
    t (statsRece ver, Except onCategor zer.default(L st(na ), san  zeClassna Cha n))

  /**
   * prov ded for backwards compat b l y
   */
  def t (statsRece ver: StatsRece ver) =
    t (statsRece ver, Except onCategor zer.default())

  /**
   * prov ded for backwards compat b l y
   */
  def t (statsRece ver: StatsRece ver, na : Str ng) =
    t (statsRece ver, Except onCategor zer.default(L st(na )))

  /**
   * prov ded for backwards compat b l y
   */
  def t (statsRece ver: StatsRece ver, san  zeClassna Cha n: Throwable => Seq[Str ng]) =
    t (
      statsRece ver,
      Except onCategor zer.default(san  zeClassna Cha n = san  zeClassna Cha n)
    )

  pr vate[t ] val counter = categor zer.l ft(Effect(statsRece ver.counter(_: _*). ncr()))

  /**
   * count one or more throwables
   */
  def apply(t: Throwable, throwables: Throwable*): Un  = {
    counter(t)
     f (throwables.nonEmpty) apply(throwables)
  }

  /**
   * count n throwables
   */
  def apply(throwables:  erable[Throwable]): Un  = {
    throwables.foreach(counter)
  }

  /**
   * wrap around a Future to capture except ons
   */
  def apply[T](f: => Future[T]): Future[T] = {
    f onFa lure { case t => apply(t) }
  }
}

/**
 * A  mo zed except on counter factory.
 *
 * @param stats
 *   t  unscoped statsRece ver on wh ch to hang t  counters
 * @param categor zer
 *   A funct on that returns a l st of category na s that a throwable should be counted under.
 */
class  mo zedExcept onCounterFactory(stats: StatsRece ver, categor zer: Except onCategor zer) {

  /**
   * A  mo zed except on counter factory us ng t  default categor zer.
   *
   * @param stats
   *   t  unscoped statsRece ver on wh ch to hang t  counters
   */
  def t (stats: StatsRece ver) =
    t (stats, Except onCategor zer.default())

  /**
   * A  mo zed except on counter factory us ng a categor zer w h t  g ven suff x.
   *
   * @param stats
   *   t  unscoped statsRece ver on wh ch to hang t  counters
   * @param suff x
   *   All created except on counters w ll have t 
   *   spec f ed suff x added. T  allows compat b l y w h
   *   Servo's Except onCounter's na  param (allows creat ng
   *   except on counters that default to t  "except ons" na space
   *   as  ll as those w h an ot rw se-spec f ed scope).
   */
  def t (stats: StatsRece ver, suff x: Seq[Str ng]) =
    t (stats, Except onCategor zer.default(suff x))

  pr vate[t ] val getCounter =
     mo ze { (path: Seq[Str ng]) =>
      new Except onCounter(stats, categor zer.scoped(path))
    }

  def apply(path: Str ng*): Except onCounter = getCounter(path)
}
