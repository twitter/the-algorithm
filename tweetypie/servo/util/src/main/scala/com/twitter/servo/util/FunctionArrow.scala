package com.tw ter.servo.ut l

/**
 * A collect on of Funct onArrow factory funct ons.
 */
object Funct onArrow {
  def apply[A, B](f: A => B): Funct onArrow[A, B] = fromFunct on(f)

  /**
   * Produce an Funct onArrow from a funct on `A => B`.
   */
  def fromFunct on[A, B](f: A => B): Funct onArrow[A, B] =
    new Funct onArrow[A, B] {
      def apply(a: A): B = f(a)
    }

  /**
   * Produces a Funct onArrow w h no s de-effects that s mply returns  s argu nt.
   */
  def  dent y[A]: Funct onArrow[A, A] = apply(Predef. dent y[A])

  /**
   * Appends two Funct onArrows toget r.
   *
   * T  forms a mono d w h ' dent y'.
   */
  def append[A, B, C](a: Funct onArrow[A, B], b: Funct onArrow[B, C]): Funct onArrow[A, C] =
    a.andT n(b)

  /**
   * Produce an Funct onArrow that appl es an Effect, return ng t  argu nt
   * value as- s.
   */
  def effect[A](effect: Effect[A]): Funct onArrow[A, A] = apply { a =>
    effect(a); a
  }

  /**
   * Produces an Funct onArrow that prox es to one of two ot rs, depend ng on a
   * pred cate.
   */
  def choose[A, B](
    pred cate: A => Boolean,
     fTrue: Funct onArrow[A, B],
     fFalse: Funct onArrow[A, B]
  ): Funct onArrow[A, B] =
    apply { a: A =>
       f (pred cate(a))  fTrue(a) else  fFalse(a)
    }

  /**
   * Produces an Funct onArrow whose appl cat on  s guarded by a pred cate. `f`  s
   * appl ed  f t  pred cate returns true, ot rw se t  argu nt  s s mply
   * returned.
   */
  def only f[A](pred cate: A => Boolean, f: Funct onArrow[A, A]): Funct onArrow[A, A] =
    choose(pred cate, f,  dent y[A])
}

/**
 * A funct on encapsulat ng a computat on.
 *
 * Background on t  Arrow abstract on:
 * http://en.w k ped a.org/w k /Arrow_(computer_sc ence)
 */
tra  Funct onArrow[-A, +B] extends (A => B) { self =>

  /**
   * Composes two Funct onArrows. Produces a new Funct onArrow that performs both  n ser es.
   */
  def andT n[C](next: Funct onArrow[B, C]): Funct onArrow[A, C] =
    new Funct onArrow[A, C] {
      overr de def apply(a: A) = next.apply(self(a))
    }
}
