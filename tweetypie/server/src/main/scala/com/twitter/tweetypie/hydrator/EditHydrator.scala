package com.tw ter.t etyp e
package hydrator

 mport com.tw ter.st ch.St ch
 mport com.tw ter.t etyp e.core.Ed State

/**
 * An Ed Hydrator hydrates a value of type `A`, w h a hydrat on context of type `C`,
 * and produces a funct on that takes a value and context and returns an Ed State[A, C]
 * (an Ed State encapsulates a funct on that takes a value and returns a new ValueState).
 *
 * A ser es of Ed Hydrators of t  sa  type may be run  n parallel v a
 * `Ed Hydrator. nParallel`.
 */
class Ed Hydrator[A, C] pr vate (val run: (A, C) => St ch[Ed State[A]]) {

  /**
   * Apply t  hydrator to a value, produc ng an Ed State.
   */
  def apply(a: A, ctx: C): St ch[Ed State[A]] = run(a, ctx)

  /**
   * Convert t  Ed Hydrator to t  equ valent ValueHydrator.
   */
  def toValueHydrator: ValueHydrator[A, C] =
    ValueHydrator[A, C] { (a, ctx) => t .run(a, ctx).map(ed State => ed State.run(a)) }

  /**
   * Runs two Ed Hydrators  n parallel.
   */
  def  nParallelW h(next: Ed Hydrator[A, C]): Ed Hydrator[A, C] =
    Ed Hydrator[A, C] { (x0, ctx) =>
      St ch.jo nMap(run(x0, ctx), next.run(x0, ctx)) {
        case (r1, r2) => r1.andT n(r2)
      }
    }
}

object Ed Hydrator {

  /**
   * Create an Ed Hydrator from a funct on that returns St ch[Ed State[A]].
   */
  def apply[A, C](f: (A, C) => St ch[Ed State[A]]): Ed Hydrator[A, C] =
    new Ed Hydrator[A, C](f)

  /**
   * Creates a "passthrough" Ed :
   * Leaves A unchanged and produces empty Hydrat onState.
   */
  def un [A, C]: Ed Hydrator[A, C] =
    Ed Hydrator { (_, _) => St ch.value(Ed State.un [A]) }

  /**
   * Runs several Ed Hydrators  n parallel.
   */
  def  nParallel[A, C](bs: Ed Hydrator[A, C]*): Ed Hydrator[A, C] =
    bs match {
      case Seq(b) => b
      case Seq(b1, b2) => b1. nParallelW h(b2)
      case _ => bs.reduceLeft(_. nParallelW h(_))
    }
}
