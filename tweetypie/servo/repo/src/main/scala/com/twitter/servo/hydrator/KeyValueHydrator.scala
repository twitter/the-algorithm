package com.tw ter.servo.hydrator

 mport com.tw ter.servo.data.Mutat on
 mport com.tw ter.servo.ut l.{Effect, Gate}
 mport com.tw ter.servo.repos ory._
 mport com.tw ter.ut l.{Future, Return, Try}

object KeyValueHydrator {
  // KeyValueHydrator extends t  funct on type
  type Funct onType[Q, K, V] = (Q, Future[KeyValueResult[K, V]]) => Future[Mutat on[V]]
  type F lter[Q, K, V] = (Q, Future[KeyValueResult[K, V]]) => Future[Boolean]

  pr vate[t ] val _un  = fromMutat on[Any, Any, Any](Mutat on.un [Any])

  /**
   * A no-op hydrator.  Forms a mono d w h `also`.
   */
  def un [Q, K, V]: KeyValueHydrator[Q, K, V] =
    _un .as nstanceOf[KeyValueHydrator[Q, K, V]]

  /**
   * Packages a funct on as a KeyValueHydrator
   */
  def apply[Q, K, V](f: Funct onType[Q, K, V]): KeyValueHydrator[Q, K, V] =
    new KeyValueHydrator[Q, K, V] {
      overr de def apply(query: Q, futureResults: Future[KeyValueResult[K, V]]) =
        f(query, futureResults)
    }

  /**
   * Creates a new KeyValueHydrator out of several underly ng KVHydrators. T 
   * apply  thod  s called on each KeyValueHydrator w h t  sa 
   * futureResults, allow ng each to k ck-off so  asynchronous work
   * to produce a future Hydrated[Mutat on]. W n all t  future
   * Hydrated[Mutat on]s are ava lable, t  results are folded,
   * left-to-r ght, over t  mutat ons, to bu ld up t  f nal
   * results.
   */
  def  nParallel[Q, K, V](hydrators: KeyValueHydrator[Q, K, V]*): KeyValueHydrator[Q, K, V] =
    KeyValueHydrator[Q, K, V] { (query, futureResults) =>
      val futureMutat ons = hydrators map { t =>
        t(query, futureResults)
      }
      Future.collect(futureMutat ons) map Mutat on.all
    }

  def const[Q, K, V](futureMutat on: Future[Mutat on[V]]): KeyValueHydrator[Q, K, V] =
    KeyValueHydrator[Q, K, V] { (_, _) =>
      futureMutat on
    }

  def fromMutat on[Q, K, V](mutat on: Mutat on[V]): KeyValueHydrator[Q, K, V] =
    const[Q, K, V](Future.value(mutat on))
}

/**
 * A KeyValueHydrator bu lds a Mutat on to be appl ed to t  values  n a KeyValueResult, but does
 * not  self apply t  Mutat on.  T  allows several KeyValueHydrators to be composed toget r to
 * beg n t  r work  n parallel to bu ld t  Mutat ons, wh ch can t n be comb ned and appl ed
 * to t  results later (see asRepos oryF lter).
 *
 * Forms a mono d w h KeyValueHydrator.un  as un  and `also` as t  comb n ng funct on.
 */
tra  KeyValueHydrator[Q, K, V] extends KeyValueHydrator.Funct onType[Q, K, V] {
  protected[t ] val un Mutat on = Mutat on.un [V]
  protected[t ] val futureUn Mutat on = Future.value(un Mutat on)

  /**
   * Comb nes two KeyValueHydrators.  Forms a mono d w h KeyValueHydator.un 
   */
  def also(next: KeyValueHydrator[Q, K, V]): KeyValueHydrator[Q, K, V] =
    KeyValueHydrator. nParallel(t , next)

  /**
   * Turns a s ngle KeyValueHydrator  nto a Repos oryF lter by apply ng t  Mutat on to
   * found values  n t  KeyValueResult.   f t  mutat on throws an except on,   w ll
   * be caught and t  result ng key/value pa red moved to t  fa led map of t  result ng
   * KeyValueResult.
   */
  lazy val asRepos oryF lter: Repos oryF lter[Q, KeyValueResult[K, V], KeyValueResult[K, V]] =
    (query, futureResults) => {
      t (query, futureResults) flatMap { mutat on =>
        val update = mutat on.endo
        futureResults map { results =>
          results.mapValues {
            case Return(So (value)) => Try(So (update(value)))
            case x => x
          }
        }
      }
    }

  /**
   * Apply t  hydrator to t  result of a repos ory.
   */
  def hydratedBy_:(repo: KeyValueRepos ory[Q, K, V]): KeyValueRepos ory[Q, K, V] =
    Repos ory.composed(repo, asRepos oryF lter)

  /**
   * Return a new hydrator that appl es t  sa  mutat on as t 
   * hydrator, but can be enabled/d sabled or dark enabled/d sabled v a Gates.  T  l ght
   * gate takes precedence over t  dark gate.  T  allows   to go from 0%->100% dark,
   * and t n from 0%->100% l ght w hout affect ng backend traff c.
   */
  @deprecated("Use enabledBy(() => Boolean, () => Boolean)", "2.5.1")
  def enabledBy(l ght: Gate[Un ], dark: Gate[Un ] = Gate.False): KeyValueHydrator[Q, K, V] =
    enabledBy(
      { () =>
        l ght()
      },
      { () =>
        dark()
      })

  /**
   * Return a new hydrator that appl es t  sa  mutat on as t 
   * hydrator, but can be enabled/d sabled or dark enable/d sabled v a nullary boolean funct ons.
   * T  l ght funct on takes precedence over t  dark funct on.
   * T  allows   to go from 0%->100% dark, and t n from 0%->100% l ght
   * w hout affect ng backend traff c.
   */
  def enabledBy(l ght: () => Boolean, dark: () => Boolean): KeyValueHydrator[Q, K, V] =
    KeyValueHydrator[Q, K, V] { (query, futureResults) =>
      val  sL ght = l ght()
      val  sDark = ! sL ght && dark()
       f (! sL ght && ! sDark) {
        futureUn Mutat on
      } else {
        t (query, futureResults) map {
          case mutat on  f  sL ght => mutat on
          case mutat on  f  sDark => mutat on.dark
        }
      }
    }

  /**
   * Bu ld a new hydrator that w ll return t  sa  result as t  current hydrator,
   * but w ll add  onally perform t  suppl ed effect on t  result of hydrat on.
   */
  def w hEffect(effect: Effect[Opt on[V]]): KeyValueHydrator[Q, K, V] =
    KeyValueHydrator[Q, K, V] { (query, futureResults) =>
      t (query, futureResults) map { _ w hEffect effect }
    }

  /**
   * Bu lds a new hydrator that only attempt to hydrate  f t 
   * suppl ed f lter returns true.
   */
  def f lter(pred cate: KeyValueHydrator.F lter[Q, K, V]): KeyValueHydrator[Q, K, V] =
    KeyValueHydrator[Q, K, V] { (q, r) =>
      pred cate(q, r) flatMap { t =>
         f (t) t (q, r) else futureUn Mutat on
      }
    }
}
