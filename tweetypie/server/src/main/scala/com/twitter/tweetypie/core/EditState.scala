package com.tw ter.t etyp e.core

 mport com.tw ter.servo.data.Mutat on

/**
 * An Ed State  s a funct on that changes a value and may generate
 * so  state about what was mod f ed. For  nstance,   may record
 * w t r an  em was changed, or w t r t re was an error.
 * Ed States are useful because t y are f rst-class values that can
 * be composed.  n part cular,    s useful to concurrently access
 * external data to bu ld ed s and t n apply t m.
 *
 * @tparam A T  type of t  value that  s be ng ed ed (for  nstance,
 *           hav ng f elds hydrated w h data from anot r serv ce)
 */
f nal case class Ed State[A](run: A => ValueState[A]) {

  /**
   * Composes two Ed States  n sequence
   */
  def andT n(ot r: Ed State[A]): Ed State[A] =
    Ed State[A] { a0: A =>
      val ValueState(a1, s1) = run(a0)
      val ValueState(a2, s2) = ot r.run(a1)
      ValueState(a2, s1 ++ s2)
    }
}

object Ed State {

  /**
   * Creates a "passthrough" Ed State:
   * Leaves A unchanged and produces empty state S
   */
  def un [A]: Ed State[A] =
    Ed State[A](ValueState.un [A])

  /**
   * Creates an `Ed State[A]` us ng a `Mutat on[A]`.
   */
  def fromMutat on[A](mut: Mutat on[A]): Ed State[A] =
    Ed State[A] { a =>
      mut(a) match {
        case None => ValueState.unmod f ed(a)
        case So (a2) => ValueState.mod f ed(a2)
      }
    }
}
