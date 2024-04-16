package com.tw ter.servo.ut l

object Effect {
  // a no-op effect
  pr vate[t ] val _un  = Effect[Any] { _ =>
    ()
  }

  /**
   * A "no-op" Effect.  For any effect E, (E also un ) == (un  also E) == E.
   * Forms a mono d w h `also`.
   */
  def un [A]: Effect[A] = _un .as nstanceOf[Effect[A]]

  /**
   * Package a funct on as an Effect.
   */
  def apply[A](f: A => Un ): Effect[A] =
    new Effect[A] {
      overr de def apply(value: A) = f(value)
    }

  /**
   * An effect that only appl es to so  values.
   */
  def fromPart al[A](f: Part alFunct on[A, Un ]): Effect[A] =
    Effect[A] { x =>
       f (f. sDef nedAt(x)) f(x)
    }
}

/**
 * Perform an effect w h t  g ven value, w hout alter ng t  result.
 *
 * Forms a mono d w h Effect.un  as un  and `also` as t  comb n ng operat on.
 */
tra  Effect[A] extends (A => Un ) { self =>

  /**
   * An  dent y funct on that executes t  effect as a s de-effect.
   */
  lazy val  dent y: A => A = { value =>
    self(value); value
  }

  /**
   * Comb ne effects, so that both effects are perfor d.
   * Forms a mono d w h Effect.un .
   */
  def also(next: Effect[A]): Effect[A] =
    Effect[A]( dent y andT n next)

  /**
   * Convert an effect to an effect of a more general type by way
   * of an extract on funct on. (contravar ant map)
   */
  def contramap[B](extract: B => A): Effect[B] =
    Effect[B](extract andT n self)

  /**
   * Perform t  effect only  f t  prov ded gate returns true.
   */
  @deprecated("Use enabledBy(() => Boolean)", "2.5.1")
  def enabledBy(enabled: Gate[Un ]): Effect[A] =
    enabledBy(() => enabled())

  /**
   * Perform t  effect only  f t  prov ded gate returns true.
   */
  def enabledBy(enabled: () => Boolean): Effect[A] =
    only f { _ =>
      enabled()
    }

  /**
   * Perform t  effect only  f t  prov ded pred cate returns true
   * for t   nput.
   */
  def only f(pred cate: A => Boolean) =
    Effect[A] { x =>
       f (pred cate(x)) t (x) else ()
    }
}
