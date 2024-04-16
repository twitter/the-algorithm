/**
 * Prov des t  ab l y to part ally tee traff c to a secondary
 * serv ce.
 *
 * T  code was or g nally wr ten to prov de a way to prov de
 * product on traff c to t  T etyP e stag ng cluster, select ng a
 * cons stent subset of t et  ds, to enable a product on-l ke cac 
 * h  rate w h a much smaller cac .
 */
package com.tw ter.servo.forked

 mport com.tw ter.servo.data.Lens

object Forked {

  /**
   * A strategy for execut ng forked act ons.
   */
  type Executor = (() => Un ) => Un 

  /**
   * D rectly execute t  forked act on.
   */
  val  nl neExecutor: Executor = f => f()

  /**
   * Produce objects of type A to send to a secondary target.
   * Return ng None s gn f es that noth ng should be forked.
   */
  type Fork[A] = A => Opt on[A]

  /**
   * Fork t   nput unchanged, only w n   passes t  spec f ed
   * pred cate.
   *
   * For  nstance,  f y  serv ce has a get()  thod
   */
  def forkW n[T](f: T => Boolean): Fork[T] =
    a =>  f (f(a)) So (a) else None

  /**
   * Fork a subset of t  ele nts of t  Seq, based on t  suppl ed
   * pred cate.  f t  result ng Seq  s empty, t  secondary act on
   * w ll not be executed.
   */
  def forkSeq[T](f: T => Boolean): Fork[Seq[T]] = { xs =>
    val newXs = xs f lter f
     f (newXs.nonEmpty) So (newXs) else None
  }

  /**
   * Apply fork ng through lens.
   */
  def forkLens[A, B](lens: Lens[A, B], f: Fork[B]): Fork[A] =
    a => f(lens(a)).map(lens.set(a, _))

  /**
   * A factory for bu ld ng act ons that w ll part ally tee t  r  nput
   * to a secondary target. T  executor  s para ter zed to make t 
   * execut on strategy  ndependent from t  fork ng log c.
   */
  def toSecondary[S](secondary: S, executor: Executor): S => Forked[S] =
    pr mary =>
      new Forked[S] {

        /**
         * Tee a subset of requests def ned by t  fork ng funct on to t 
         * secondary serv ce.
         */
        def apply[Q, R](fork: Forked.Fork[Q], act on: (S, Q) => R): Q => R = { req =>
          fork(req) foreach { req =>
            executor(() => act on(secondary, req))
          }
          act on(pr mary, req)
        }
      }

  /**
   * A forked act on bu lder that bypasses t  fork ng altoget r and
   * just calls t  suppl ed act on on a serv ce.
   *
   * T   s useful for conf gurat ons that w ll so t  s have fork
   * targets def ned and so t  s not.
   */
  def notForked[S]: S => Forked[S] =
    serv ce =>
      new Forked[S] {
        def apply[Q, R](unusedFork: Forked.Fork[Q], act on: (S, Q) => R): Q => R =
          act on(serv ce, _)
      }
}

/**
 * Factory for fork ng funct ons, pr mar ly useful for send ng a copy
 * of a stream of requests to a secondary serv ce.
 */
tra  Forked[S] {
   mport Forked._

  /**
   * Fork an act on that takes two para ters, fork ng only on t 
   * f rst para ter, pass ng t  second unchanged.
   */
  def f rst[Q1, Q2, R](
    fork: Fork[Q1],
    act on: S => (Q1, Q2) => R
  ): (Q1, Q2) => R = {
    val f =
      apply[(Q1, Q2), R](
        fork = p =>
          fork(p._1) map { q1 =>
            (q1, p._2)
          },
        act on = (svc, p) => act on(svc)(p._1, p._2)
      )
    (q1, q2) => f((q1, q2))
  }

  def apply[Q, R](fork: Fork[Q], act on: (S, Q) => R): Q => R
}
