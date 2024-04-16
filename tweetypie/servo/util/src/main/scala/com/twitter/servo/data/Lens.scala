package com.tw ter.servo.data

 mport scala.language.ex stent als

object Lens {
  pr vate[t ] val _ dent y =  so[Any, Any](x => x, x => x)

  /**
   * T   dent y lens.
   */
  def  dent y[A] = _ dent y.as nstanceOf[Lens[A, A]]

  /**
   * Conven ence  thod for creat ng lenses w h sl ghtly more
   * eff c ent setters.
   */
  def c ckEq[A, B](get: A => B, set: (A, B) => A) = Lens[A, B](get, set).c ckEq

  /**
   * Create a lens from an  somorp m.
   */
  def  so[A, B](to: A => B, from: B => A) = Lens[A, B](to, (_, x) => from(x))

  /**
   * Us ng mult ple lenses, copy mult ple f elds from one object to anot r, return ng
   * t  updated result.
   */
  def copyAll[A](lenses: Lens[A, _]*)(src: A, dst: A): A =
    lenses.foldLeft(dst) { (t, l) =>
      l.copy(src, t)
    }

  /**
   * setAll can be used to set mult ple values us ng mult ple lenses on t  sa   nput
   * value  n one call, wh ch  s more readable than nested calls.  For example, say
   * that   have lenses (lensX: Lens[A, X]), (lensY: Lens[A, Y]), and (lensZ: Lens[A, Z]),
   * t n  nstead of wr  ng:
   *
   *    lensX.set(lensY.set(lensZ.set(a, z), y), x)
   *
   *   can wr e:
   *
   *    Lens.setAll(a, lensX -> x, lensY -> y, lensZ -> z)
   */
  def setAll[A](a: A, lensAndValues: ((Lens[A, B], B) forSo  { type B })*): A =
    lensAndValues.foldLeft(a) { case (a, (l, b)) => l.set(a, b) }

  /**
   * Comb nes two lenses  nto one that gets and sets a tuple of values.
   */
  def jo n[A, B, C](lensB: Lens[A, B], lensC: Lens[A, C]): Lens[A, (B, C)] =
    Lens[A, (B, C)](
      a => (lensB.get(a), lensC.get(a)),
      { case (a, (b, c)) => lensC.set(lensB.set(a, b), c) }
    )

  /**
   * Comb nes three lenses  nto one that gets and sets a tuple of values.
   */
  def jo n[A, B, C, D](
    lensB: Lens[A, B],
    lensC: Lens[A, C],
    lensD: Lens[A, D]
  ): Lens[A, (B, C, D)] =
    Lens[A, (B, C, D)](
      a => (lensB.get(a), lensC.get(a), lensD.get(a)),
      { case (a, (b, c, d)) => lensD.set(lensC.set(lensB.set(a, b), c), d) }
    )
}

/**
 * A Lens  s a f rst-class getter/setter. T  value of lenses  s that
 * t y can be composed w h ot r operat ons.
 *
 * Note that    s up to   to ensure that t  funct ons   pass to
 * Lens obey t  follow ng laws for all  nputs:
 *
 *  a => set(a, get(a)) == a
 *  (a, b) => get(set(a, b)) == b
 *  (a, b, b1) => set(set(a, b), b1) == set(a, b1)
 *
 * T   ntu  on for t  na  Lens[A, B]  s that   are "v ew ng" A
 * through a Lens that lets   see (and man pulate) a B.
 *
 * See e.g.
 * http://stackoverflow.com/quest ons/5767129/lenses-fclabels-data-accessor-wh ch-l brary-for-structure-access-and-mutat o#ans r-5769285
 * for a more  n-depth explanat on of lenses.
 */
case class Lens[A, B](get: A => B, set: (A, B) => A) {

  /**
   * Get t  f eld.
   */
  def apply(a: A) = get(a)

  /**
   * Compose w h anot r lens, such that t  setter updates t 
   * outermost structure, and t  getter gets t   nnermost structure.
   */
  def andT n[C](next: Lens[B, C]) =
    Lens(get andT n next.get, (a: A, c: C) => set(a, next.set(get(a), c)))

  /**
   * An operator al as for `andT n`.
   */
  def >>[C](next: Lens[B, C]) = andT n(next)

  /**
   * L ft t  funct on on t  v e d value to a funct on on t  outer
   * value.
   */
  def update(f: B => B): A => A = a => set(a, f(get(a)))

  /**
   * Cop es t  f eld from one object to anot r.
   */
  def copy(src: A, dst: A): A = set(dst, get(src))

  /**
   * L ft a mutat on of t  v e d value to a transform of t 
   * conta ner. (E.g. a Mutat on[Seq[UrlEnt y]] to a Mutat on[T et])
   */
  def mutat on(m: Mutat on[B]) =
    Mutat on[A] { a =>
      m(get(a)) map { set(a, _) }
    }

  /**
   * Create a new lens whose setter makes sure that t  update would
   * change t  value.
   *
   * T  should not change t   an ng of t  lens, but can poss bly
   * make   more eff c ent by avo d ng cop es w n perform ng no-op
   * sets.
   *
   * T   s only worthwh le w n t  getter and equal y compar son
   * are c ap compared to t  setter.
   */
  def c ckEq = Lens[A, B](get, (a, b) =>  f (get(a) == b) a else set(a, b))

  /**
   * Comb nes t  lens and t  g ven lens  nto one that gets and sets a tuple
   * of values.
   */
  def jo n[C](r ght: Lens[A, C]): Lens[A, (B, C)] =
    Lens.jo n(t , r ght)
}
