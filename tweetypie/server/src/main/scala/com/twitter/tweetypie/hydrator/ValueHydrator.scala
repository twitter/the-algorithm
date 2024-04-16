package com.tw ter.t etyp e
package hydrator

 mport com.tw ter.servo.ut l.Except onCounter
 mport com.tw ter.st ch.St ch
 mport com.tw ter.t etyp e.core.Ed State
 mport com.tw ter.t etyp e.core.ValueState
 mport com.tw ter.ut l.Try

/**
 * A ValueHydrator hydrates a value of type `A`, w h a hydrat on context of type `C`,
 * and produces a value of type ValueState[A] (ValueState encapsulates t  value and
 *  s assoc ated Hydrat onState).
 *
 * Because ValueHydrators take a value and produce a new value, t y can eas ly be run
 *  n sequence, but not  n parallel. To run hydrators  n parallel, see [[Ed Hydrator]].
 *
 * A ser es of ValueHydrators of t  sa  type may be run  n sequence v a
 * `ValueHydrator. nSequence`.
 *
 */
class ValueHydrator[A, C] pr vate (val run: (A, C) => St ch[ValueState[A]]) {

  /**
   * Apply t  hydrator to a value, produc ng a ValueState.
   */
  def apply(a: A, ctx: C): St ch[ValueState[A]] = run(a, ctx)

  /**
   * Apply w h an empty context: only used  n tests.
   */
  def apply(a: A)( mpl c  ev: Un  <:< C): St ch[ValueState[A]] =
    apply(a, ev(()))

  /**
   * Convert t  ValueHydrator to t  equ valent Ed Hydrator.
   */
  def toEd Hydrator: Ed Hydrator[A, C] =
    Ed Hydrator[A, C] { (a, ctx) => t .run(a, ctx).map(value => Ed State(_ => value)) }

  /**
   * Cha ns two ValueHydrators  n sequence.
   */
  def andT n(next: ValueHydrator[A, C]): ValueHydrator[A, C] =
    ValueHydrator[A, C] { (x0, ctx) =>
      for {
        r1 <- run(x0, ctx)
        r2 <- next.run(r1.value, ctx)
      } y eld {
        ValueState(r2.value, r1.state ++ r2.state)
      }
    }

  /**
   * Executes t  ValueHydrator cond  onally based on a Gate.
   */
  def  fEnabled(gate: Gate[Un ]): ValueHydrator[A, C] =
    only f((_, _) => gate())

  /**
   * Executes t  ValueHydrator cond  onally based on a boolean funct on.
   */
  def only f(cond: (A, C) => Boolean): ValueHydrator[A, C] =
    ValueHydrator { (a, c) =>
       f (cond(a, c)) {
        run(a, c)
      } else {
        St ch.value(ValueState.un (a))
      }
    }

  /**
   * Converts a ValueHydrator of  nput type `A` to  nput type `Opt on[A]`.
   */
  def l ftOpt on: ValueHydrator[Opt on[A], C] =
    l ftOpt on(None)

  /**
   * Converts a ValueHydrator of  nput type `A` to  nput type `Opt on[A]` w h a
   * default  nput value.
   */
  def l ftOpt on(default: A): ValueHydrator[Opt on[A], C] =
    l ftOpt on(So (default))

  pr vate def l ftOpt on(default: Opt on[A]): ValueHydrator[Opt on[A], C] = {
    val none = St ch.value(ValueState.un (None))

    ValueHydrator[Opt on[A], C] { (a, ctx) =>
      a.orElse(default) match {
        case So (a) => t .run(a, ctx).map(s => s.map(So .apply))
        case None => none
      }
    }
  }

  /**
   * Converts a ValueHydrator of  nput type `A` to  nput type `Seq[A]`.
   */
  def l ftSeq: ValueHydrator[Seq[A], C] =
    ValueHydrator[Seq[A], C] { (as, ctx) =>
      St ch.traverse(as)(a => run(a, ctx)).map(rs => ValueState.sequence[A](rs))
    }

  /**
   * Produces a new ValueHydrator that collects stats on t  hydrat on.
   */
  def observe(
    stats: StatsRece ver,
    mkExcept onCounter: (StatsRece ver, Str ng) => Except onCounter = (stats, scope) =>
      new Except onCounter(stats, scope)
  ): ValueHydrator[A, C] = {
    val callCounter = stats.counter("calls")
    val noopCounter = stats.counter("noop")
    val mod f edCounter = stats.counter("mod f ed")
    val part alCounter = stats.counter("part al")
    val completedCounter = stats.counter("completed")

    val except onCounter = mkExcept onCounter(stats, "fa lures")

    ValueHydrator[A, C] { (a, ctx) =>
      t .run(a, ctx).respond {
        case Return(ValueState(_, state)) =>
          callCounter. ncr()

           f (state. sEmpty) {
            noopCounter. ncr()
          } else {
             f (state.mod f ed) mod f edCounter. ncr()
             f (state.fa ledF elds.nonEmpty) part alCounter. ncr()
             f (state.completedHydrat ons.nonEmpty) completedCounter. ncr()
          }
        case Throw(ex) =>
          callCounter. ncr()
          except onCounter(ex)
      }
    }
  }

  /**
   * Produces a new ValueHydrator that uses a lens to extract t  value to hydrate,
   * us ng t  hydrator, and t n to put t  updated value back  n t  enclos ng struct.
   */
  def lensed[B](lens: Lens[B, A]): ValueHydrator[B, C] =
    ValueHydrator[B, C] { (b, ctx) =>
      t .run(lens.get(b), ctx).map {
        case ValueState(value, state) =>
          ValueState(lens.set(b, value), state)
      }
    }
}

object ValueHydrator {

  /**
   * Create a ValueHydrator from a funct on that returns St ch[ValueState[A]]
   */
  def apply[A, C](f: (A, C) => St ch[ValueState[A]]): ValueHydrator[A, C] =
    new ValueHydrator[A, C](f)

  /**
   * Produces a ValueState  nstance w h t  g ven value and an empty Hydrat onState
   */
  def un [A, C]: ValueHydrator[A, C] =
    ValueHydrator { (a, _) => St ch.value(ValueState.un (a)) }

  /**
   * Runs several ValueHydrators  n sequence.
   */
  def  nSequence[A, C](bs: ValueHydrator[A, C]*): ValueHydrator[A, C] =
    bs match {
      case Seq(b) => b
      case Seq(b1, b2) => b1.andT n(b2)
      case _ => bs.reduceLeft(_.andT n(_))
    }

  /**
   * Creates a `ValueHydrator` from a Mutat on.   f t  mutat on returns None ( nd cat ng
   * no change) t  hydrator w ll return an ValueState.unmod f ed w h t   nput value;
   * ot rw se,   w ll return an ValueState.mod f ed w h t  mutated value.
   *  f t  mutat on throws an except on,   w ll be caught and l fted to St ch.except on.
   */
  def fromMutat on[A, C](mutat on: Mutat on[A]): ValueHydrator[A, C] =
    ValueHydrator[A, C] { ( nput, _) =>
      St ch.const(
        Try {
          mutat on( nput) match {
            case None => ValueState.unmod f ed( nput)
            case So (output) => ValueState.mod f ed(output)
          }
        }
      )
    }

  /**
   * Creates a Hydrator from a non-`St ch` produc ng funct on.  f t  funct on throws
   * an error   w ll be caught and converted to a Throw.
   */
  def map[A, C](f: (A, C) => ValueState[A]): ValueHydrator[A, C] =
    ValueHydrator[A, C] { (a, ctx) => St ch.const(Try(f(a, ctx))) }
}
