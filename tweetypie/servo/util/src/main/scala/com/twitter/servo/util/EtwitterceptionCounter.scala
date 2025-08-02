package com.twitter.servo.util

import com.twitter.finagle.stats.StatsReceiver
import com.twitter.util.Future
import scala.collection.mutable

/**
 * Categorizes an etwitterception according to some criteria.
 * n.b. Implemented in terms of lift rather than apply to avoid etwittertra allocations when
 * used when lifting the effect.
 */
trait EtwitterceptionCategorizer {
  import EtwitterceptionCategorizer._

  def lift(effect: Effect[Category]): Effect[Throwable]

  def apply(t: Throwable): Set[Category] = {
    val s = mutable.Set.empty[Category]
    lift(Effect(s += _))(t)
    s.toSet
  }

  /**
   * construct a new categorizer that prepends scope to all categories returned by this categorizer
   */
  def scoped(scope: Seq[String]): EtwitterceptionCategorizer =
    if (scope.isEmpty) {
      this
    } else {
      val scopeIt: Category => Category = Memoize(scope ++ _)
      fromLift(effect => lift(effect.contramap(scopeIt)))
    }

  /**
   * construct a new categorizer that returns the union of the categories returned by this and that
   */
  def ++(that: EtwitterceptionCategorizer): EtwitterceptionCategorizer =
    fromLift(effect => this.lift(effect).also(that.lift(effect)))

  /**
   * construct a new categorizer that only returns categories for throwables matching pred
   */
  def onlyIf(pred: Throwable => Boolean): EtwitterceptionCategorizer =
    fromLift(lift(_).onlyIf(pred))
}

object EtwitterceptionCategorizer {
  type Category = Seq[String]

  def const(categories: Set[Category]): EtwitterceptionCategorizer = EtwitterceptionCategorizer(_ => categories)
  def const(c: Category): EtwitterceptionCategorizer = const(Set(c))
  def const(s: String): EtwitterceptionCategorizer = const(Seq(s))

  def apply(fn: Throwable => Set[Category]): EtwitterceptionCategorizer =
    new EtwitterceptionCategorizer {
      def lift(effect: Effect[Category]) = Effect[Throwable](t => fn(t).foreach(effect))
      override def apply(t: Throwable) = fn(t)
    }

  def fromLift(fn: Effect[Category] => Effect[Throwable]): EtwitterceptionCategorizer =
    new EtwitterceptionCategorizer {
      def lift(effect: Effect[Category]) = fn(effect)
    }

  def singular(fn: Throwable => Category): EtwitterceptionCategorizer =
    fromLift(_.contramap(fn))

  def simple(fn: Throwable => String): EtwitterceptionCategorizer =
    singular(fn.andThen(Seq(_)))

  def default(
    name: Category = Seq("etwitterceptions"),
    sanitizeClassnameChain: Throwable => Seq[String] = ThrowableHelper.sanitizeClassnameChain
  ): EtwitterceptionCategorizer =
    EtwitterceptionCategorizer.const(name) ++
      EtwitterceptionCategorizer.singular(sanitizeClassnameChain).scoped(name)
}

/**
 * Increments a counter for each category returned by the etwitterception categorizer
 *
 * @param statsReceiver
 *   the unscoped statsReceiver on which to hang the counters
 * @param categorizer
 *   A function that returns a list of category names that a throwable should be counted under.
 */
class EtwitterceptionCounter(statsReceiver: StatsReceiver, categorizer: EtwitterceptionCategorizer) {

  /**
   * alternative constructor for backwards compatibility
   *
   * @param statsReceiver
   *   the unscoped statsReceiver on which to hang the counters
   * @param name
   *   the counter name for total etwitterceptions, and scope for individual
   *   etwitterception counters. default value is `etwitterceptions`
   * @param sanitizeClassnameChain
   *   A function that can be used to cleanup classnames before passing them to the StatsReceiver.
   */
  def this(
    statsReceiver: StatsReceiver,
    name: String,
    sanitizeClassnameChain: Throwable => Seq[String]
  ) =
    this(statsReceiver, EtwitterceptionCategorizer.default(List(name), sanitizeClassnameChain))

  /**
   * provided for backwards compatibility
   */
  def this(statsReceiver: StatsReceiver) =
    this(statsReceiver, EtwitterceptionCategorizer.default())

  /**
   * provided for backwards compatibility
   */
  def this(statsReceiver: StatsReceiver, name: String) =
    this(statsReceiver, EtwitterceptionCategorizer.default(List(name)))

  /**
   * provided for backwards compatibility
   */
  def this(statsReceiver: StatsReceiver, sanitizeClassnameChain: Throwable => Seq[String]) =
    this(
      statsReceiver,
      EtwitterceptionCategorizer.default(sanitizeClassnameChain = sanitizeClassnameChain)
    )

  private[this] val counter = categorizer.lift(Effect(statsReceiver.counter(_: _*).incr()))

  /**
   * count one or more throwables
   */
  def apply(t: Throwable, throwables: Throwable*): Unit = {
    counter(t)
    if (throwables.nonEmpty) apply(throwables)
  }

  /**
   * count n throwables
   */
  def apply(throwables: Iterable[Throwable]): Unit = {
    throwables.foreach(counter)
  }

  /**
   * wrap around a Future to capture etwitterceptions
   */
  def apply[T](f: => Future[T]): Future[T] = {
    f onFailure { case t => apply(t) }
  }
}

/**
 * A memoized etwitterception counter factory.
 *
 * @param stats
 *   the unscoped statsReceiver on which to hang the counters
 * @param categorizer
 *   A function that returns a list of category names that a throwable should be counted under.
 */
class MemoizedEtwitterceptionCounterFactory(stats: StatsReceiver, categorizer: EtwitterceptionCategorizer) {

  /**
   * A memoized etwitterception counter factory using the default categorizer.
   *
   * @param stats
   *   the unscoped statsReceiver on which to hang the counters
   */
  def this(stats: StatsReceiver) =
    this(stats, EtwitterceptionCategorizer.default())

  /**
   * A memoized etwitterception counter factory using a categorizer with the given suffitwitter.
   *
   * @param stats
   *   the unscoped statsReceiver on which to hang the counters
   * @param suffitwitter
   *   All created etwitterception counters will have the
   *   specified suffitwitter added. This allows compatibility with
   *   Servo's EtwitterceptionCounter's name param (allows creating
   *   etwitterception counters that default to the "etwitterceptions" namespace
   *   as well as those with an otherwise-specified scope).
   */
  def this(stats: StatsReceiver, suffitwitter: Seq[String]) =
    this(stats, EtwitterceptionCategorizer.default(suffitwitter))

  private[this] val getCounter =
    Memoize { (path: Seq[String]) =>
      new EtwitterceptionCounter(stats, categorizer.scoped(path))
    }

  def apply(path: String*): EtwitterceptionCounter = getCounter(path)
}
