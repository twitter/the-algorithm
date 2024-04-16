package com.tw ter.t etyp e.cach ng

 mport com.tw ter.ut l.Durat on
 mport com.tw ter.ut l.T  
 mport scala.ut l.Random
 mport com.tw ter.logg ng.Logger

/**
 * Used to determ ne w t r values successfully retr eved from cac 
 * are [[Cac Result.Fresh]] or [[Cac Result.Stale]]. T   s useful
 *  n t   mple ntat on of a [[ValueSer al zer]].
 */
tra  SoftTtl[-V] {

  /**
   * Determ nes w t r a cac d value was fresh.
   *
   * @param cac dAt  t  t   at wh ch t  value was cac d.
   */
  def  sFresh(value: V, cac dAt: T  ): Boolean

  /**
   * Wraps t  value  n Fresh or Stale depend ng on t  value of ` sFresh`.
   *
   * (T  type var able U ex sts because    s not allo d to return
   * values of a contravar ant type, so   must def ne a var able that
   *  s a spec f c subclass of V. T   s worth   because   allows
   * us to create polymorph c pol c es w hout hav ng to spec fy t 
   * type. Anot r solut on would be to make t  type  nvar ant, but
   * t n   would have to spec fy t  type w never   create an
   *  nstance.)
   */
  def toCac Result[U <: V](value: U, cac dAt: T  ): Cac Result[U] =
     f ( sFresh(value, cac dAt)) Cac Result.Fresh(value) else Cac Result.Stale(value)
}

object SoftTtl {

  /**
   * Regardless of t   nputs, t  value w ll always be cons dered
   * fresh.
   */
  object NeverRefresh extends SoftTtl[Any] {
    overr de def  sFresh(_unusedValue: Any, _unusedCac dAt: T  ): Boolean = true
  }

  /**
   * Tr gger refresh based on t  length of t   that a value has been
   * stored  n cac ,  gnor ng t  value.
   *
   * @param softTtl  ems that  re cac d longer ago than t  value
   *   w ll be refres d w n t y are accessed.
   *
   * @param j ter Add nondeterm n sm to t  soft TTL to prevent a
   *   thunder ng  rd of requests refresh ng t  value at t  sa 
   *   t  . T  t   at wh ch t  value  s cons dered stale w ll be
   *   un formly spread out over a range of +/- (j ter/2).    s
   *   val d to set t  j ter to zero, wh ch w ll turn off j ter ng.
   *
   * @param logger  f non-null, use t  logger rat r than one based
   *   on t  class na . T  logger  s only used for trace-level
   *   logg ng.
   */
  case class ByAge[V](
    softTtl: Durat on,
    j ter: Durat on,
    spec f cLogger: Logger = null,
    rng: Random = Random)
      extends SoftTtl[Any] {

    pr vate[t ] val logger: Logger =
       f (spec f cLogger == null) Logger(getClass) else spec f cLogger

    pr vate[t ] val maxJ terMs: Long = j ter. nM ll seconds

    // t  requ re nt  s due to us ng Random.next nt to choose t 
    // j ter, but   allows j ter of greater than 24 days
    requ re(maxJ terMs <= ( nt.MaxValue / 2))

    // Negat ve j ter probably  nd cates m suse of t  AP 
    requ re(maxJ terMs >= 0)

    //   want per od +/- j ter, but t  random generator
    // generates non-negat ve numbers, so   generate [0, 2 *
    // maxJ ter) and subtract maxJ ter to obta n [-maxJ ter,
    // maxJ ter)
    pr vate[t ] val maxJ terRangeMs:  nt = (maxJ terMs * 2).to nt

    //   perform all calculat ons  n m ll seconds, so convert t 
    // per od to m ll seconds out  re.
    pr vate[t ] val softTtlMs: Long = softTtl. nM ll seconds

    //  f t  value  s below t  age,   w ll always be fresh,
    // regardless of j ter.
    pr vate[t ] val alwaysFreshAgeMs: Long = softTtlMs - maxJ terMs

    //  f t  value  s above t  age,   w ll always be stale,
    // regardless of j ter.
    pr vate[t ] val alwaysStaleAgeMs: Long = softTtlMs + maxJ terMs

    overr de def  sFresh(value: Any, cac dAt: T  ): Boolean = {
      val ageMs: Long = (T  .now - cac dAt). nM ll seconds
      val fresh =
         f (ageMs <= alwaysFreshAgeMs) {
          true
        } else  f (ageMs > alwaysStaleAgeMs) {
          false
        } else {
          val j terMs: Long = rng.next nt(maxJ terRangeMs) - maxJ terMs
          ageMs <= softTtlMs + j terMs
        }

      logger. fTrace(
        s"C cked soft ttl: fresh = $fresh, " +
          s"soft_ttl_ms = $softTtlMs, age_ms = $ageMs, value = $value")

      fresh
    }
  }
}
