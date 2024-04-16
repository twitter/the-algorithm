package com.tw ter.servo.data

 mport com.tw ter.ut l.{Return, Throw, Try}
 mport com.tw ter.f nagle.stats.{Counter, StatsRece ver}
 mport com.tw ter.servo.ut l.{Effect, Gate}

object Mutat on {

  /**
   * A mutat on that  gnores  s  nput and always returns t  g ven
   * value as new. Use c ckEq  f t  value could be t  sa  as t 
   *  nput.
   */
  def const[T](x: T) = Mutat on[T] { _ =>
    So (x)
  }

  pr vate[t ] val _un  = Mutat on[Any] { _ =>
    None
  }

  /**
   * A "no-op" mutat on that w ll never alter t  value.
   *
   * For any Mutat ons A, (A also un ) == (un  also A) == A.
   *
   * Forms a mono d w h also as t  operat on.
   */
  def un [A]: Mutat on[A] = _un .as nstanceOf[Mutat on[A]]

  /**
   * Makes a Mutat on out of a funct on.
   */
  def apply[A](f: A => Opt on[A]): Mutat on[A] =
    new Mutat on[A] {
      overr de def apply(x: A) = f(x)
    }

  /**
   * L ft a funct on that returns t  sa  type to a Mutat on, us ng
   * t  type's not on of equal y to detect w n t  mutat on has
   * not changed t  value.
   */
  def fromEndo[A](f: A => A): Mutat on[A] =
    Mutat on[A] { x =>
      val y = f(x)
       f (y == x) None else So (y)
    }

  /**
   * L ft a part al funct on from A to A to a mutat on.
   */
  def fromPart al[A](f: Part alFunct on[A, A]): Mutat on[A] = Mutat on[A](f.l ft)

  /**
   * Creates a new Mutat on that appl es all t  g ven mutat ons  n order.
   */
  def all[A](mutat ons: Seq[Mutat on[A]]): Mutat on[A] =
    mutat ons.foldLeft(un [A])(_ also _)
}

/**
 * A Mutat on encapsulates a computat on that may opt onally "mutate" a value, w re
 * "mutate" should be  nterpreted  n t  stateless/funct onal sense of mak ng a copy w h a
 * a change.   f t  value  s unchanged, t  mutat on should return None. W n mutat ons are
 * composed w h `also`, t  f nal result w ll be None  ff no mutat on actually changed t 
 * value.
 *
 * Forms a mono d w h Mutat on.un  as un  and `also` as t 
 * comb n ng operat on.
 *
 * T  abstract on  s useful for compos ng changes to a value w n
 * so  act on (such as updat ng a cac ) should be perfor d  f t 
 * value has changed.
 */
tra  Mutat on[A] extends (A => Opt on[A]) {

  /**
   * Convert t  mutat on to a funct on that always returns a
   * result.  f t  mutat on has no effect,   returns t  or g nal
   *  nput.
   *
   * (convert to an endofunct on on A)
   */
  lazy val endo: A => A =
    x =>
      apply(x) match {
        case So (v) => v
        case None => x
      }

  /**
   * Apply t  mutat on, and t n apply t  next mutat on to t 
   * result.  f t  mutat on leaves t  value unchanged, t  next
   * mutat on  s  nvoked w h t  or g nal  nput.
   */
  def also(g: Mutat on[A]): Mutat on[A] =
    Mutat on[A] { x =>
      apply(x) match {
        case None => g(x)
        case so Y @ So (y) =>
          g(y) match {
            case so  @ So (_) => so 
            case None => so Y
          }
      }
    }

  /**
   * Apply t  mutat on, but refuse to return an altered value. T 
   * y elds all of t  effects of t  mutat on w hout affect ng t 
   * f nal result.
   */
  def dark: Mutat on[A] = Mutat on[A] { x =>
    apply(x); None
  }

  /**
   * Convert a Mutat on on A to a Mutat on on B by way of a pa r of funct ons for
   * convert ng from B to A and back.
   */
  def xmap[B](f: B => A, g: A => B): Mutat on[B] =
    Mutat on[B](f andT n t  andT n { _ map g })

  /**
   * Converts a Mutat on on A to a Mutat on on Try[A], w re t  Mutat on  s only appl ed
   * to Return values and any except ons caught by t  undery ng funct on are caught and
   * returned as So (Throw(_))
   */
  def tryable: Mutat on[Try[A]] =
    Mutat on[Try[A]] {
      case Throw(x) => So (Throw(x))
      case Return(x) =>
        Try(apply(x)) match {
          case Throw(y) => So (Throw(y))
          case Return(None) => None
          case Return(So (y)) => So (Return(y))
        }
    }

  /**
   * Perform t  mutat on only  f t  prov ded pred cate returns true
   * for t   nput.
   */
  def only f(pred cate: A => Boolean): Mutat on[A] =
    Mutat on[A] { x =>
       f (pred cate(x)) t (x) else None
    }

  /**
   * Performs t  mutat on only  f t  g ven gate returns true.
   */
  def enabledBy(enabled: Gate[Un ]): Mutat on[A] =
    enabledBy(() => enabled())

  /**
   * Performs t  mutat on only  f t  g ven funct on returns true.
   */
  def enabledBy(enabled: () => Boolean): Mutat on[A] =
    only f { _ =>
      enabled()
    }

  /**
   * A new mutat on that returns t  sa  result as t  mutat on,
   * and add  onally calls t  spec f ed Effect.
   */
  def w hEffect(effect: Effect[Opt on[A]]): Mutat on[A] =
    Mutat on[A](t  andT n effect. dent y)

  /**
   * Perform an equal y c ck w n a value  s returned from t 
   * mutat on.  f t  values are equal, t n t  mutat on w ll y eld
   * None.
   *
   * T   s useful for two reasons:
   *
   *  1. Any effects that are cond  onal upon mutat on w ll not occur
   *     w n t  values are equal (e.g. updat ng a cac )
   *
   *  2. W n us ng a Lens to l ft a mutat on to a mutat on on a
   *     larger structure, c ck ng equal y on t  smaller structure
   *     can prevent unnecessary cop es of t  larger structure.
   */
  def c ckEq = Mutat on[A] { x =>
    t (x) match {
      case so Y @ So (y)  f y != x => so Y
      case _ => None
    }
  }

  /**
   * Converts t  mutat on to a mutat on of a d fferent type, us ng a Lens to
   * convert bet en types.
   */
  def lensed[B](lens: Lens[B, A]): Mutat on[B] =
    Mutat on[B](b => t (lens(b)).map(lens.set(b, _)))

  /**
   * Convert t  mutat on to a mutat on of a Seq of  s type.   w ll
   * y eld None  f no values are changed, or a Seq of both t  changed
   * and unchanged values  f any value  s mutated.
   */
  def l ftSeq = Mutat on[Seq[A]] { xs =>
    var changed = false
    val detectChange = Effect.fromPart al[Opt on[A]] { case So (_) => changed = true }
    val mutated = xs map (t  w hEffect detectChange).endo
     f (changed) So (mutated) else None
  }

  /**
   * Convert t  mutat on to a mutat on of a Opt on of  s type.   w ll y eld
   * None  f t  value  s not changed, or a So (So (_))  f t  value  s mutated.
   */
  def l ftOpt on = Mutat on[Opt on[A]] {
    case None => None
    case So (x) =>
      t (x) match {
        case None => None
        case So (y) => So (So (y))
      }
  }

  /**
   * Convert t  mutat on to a mutat on of t  values of a Map.   w ll
   * y eld None  f no values are changed, or a Map w h both t  changed
   * and unchanged values  f any value  s mutated.
   */
  def l ftMapValues[K] = Mutat on[Map[K, A]] { m =>
    var changed = false
    val detectChange = Effect.fromPart al[Opt on[A]] { case So (_) => changed = true }
    val f = (t  w hEffect detectChange).endo
    val mutated = m map { case (k, v) => (k, f(v)) }
     f (changed) So (mutated) else None
  }

  /**
   * Return a new mutat on that returns t  sa  result as t 
   * mutat on, as  ll as  ncre nt ng t  g ven counter w n t 
   * value  s mutated.
   */
  def countMutat ons(c: Counter) =
    t  w hEffect { Effect.fromPart al { case So (_) => c. ncr() } }

  /**
   * Wrap a mutat on  n stats w h t  follow ng counters:
   *  - no-op (returned value was t  sa  as t   nput)
   *  - none (mutat on returned none)
   *  - mutated (mutat on mod f ed t  result)
   */
  def w hStats(stats: StatsRece ver): Mutat on[A] = {
    val none = stats.counter("none")
    val noop = stats.counter("noop")
    val mutated = stats.counter("mutated")
     nput: A => {
      val result = apply( nput)
      result.fold(none. ncr()) { output =>
         f (output ==  nput) {
          noop. ncr()
        } else {
          mutated. ncr()
        }
      }
      result
    }
  }

}
