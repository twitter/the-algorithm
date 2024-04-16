package com.tw ter.t etyp e.cach ng

 mport com.tw ter.f nagle.serv ce.StatsF lter
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.f nagle.stats.Except onStatsHandler
 mport com.tw ter.f nagle.stats.Counter
 mport com.tw ter.ut l.Future
 mport com.tw ter.ut l.logg ng.Logger
 mport com.tw ter.f nagle. mcac d
 mport scala.ut l.control.NonFatal

/**
 * Wrapper around a  mcac d cl ent that performs ser al zat on and
 * deser al zat on, tracks stats, prov des trac ng, and prov des
 * per-key fresh/stale/fa lure/m ss results.
 *
 * T  operat ons that wr e values to cac  w ll only wr e values
 * that t  ValueSer al zer says are cac able. T   dea  re  s that
 * t  deser al ze and ser al ze funct ons must be co rent, and no
 * matter how   choose to wr e t se values back to cac , t 
 * ser al zer w ll have t  appropr ate knowledge about w t r t 
 * values are cac able.
 *
 * For most cases,   w ll want to use [[St chCach ng]] rat r than
 * call ng t  wrapper d rectly.
 *
 * @param keySer al zer How to convert a K value to a  mcac d key.
 *
 * @param valueSer al zer How to ser al ze and deser al ze V values,
 *   as  ll as wh ch values are cac able, and how long to store t 
 *   values  n cac .
 */
class Cac Operat ons[K, V](
  keySer al zer: K => Str ng,
  valueSer al zer: ValueSer al zer[V],
   mcac dCl ent:  mcac d.Cl ent,
  statsRece ver: StatsRece ver,
  logger: Logger,
  except onStatsHandler: Except onStatsHandler = StatsF lter.DefaultExcept ons) {
  // T   mcac d operat ons that are perfor d v a t 
  // [[Cac Operat ons]]  nstance w ll be tracked under t  stats
  // rece ver.
  //
  //   count all  mcac d fa lures toget r under t  scope,
  // because  mcac d operat ons should not fa l unless t re are
  // commun cat on problems, so d fferent at ng t   thod that was
  // be ng called w ll not g ve us any useful  nformat on.
  pr vate[t ] val  mcac dStats: StatsRece ver = statsRece ver.scope(" mcac d")

  //  ncre nted for every attempt to `get` a key from cac .
  pr vate[t ] val  mcac dGetCounter: Counter =  mcac dStats.counter("get")

  // One of t se two counters  s  ncre nted for every successful
  // response returned from a `get` call to  mcac d.
  pr vate[t ] val  mcac dNotFoundCounter: Counter =  mcac dStats.counter("not_found")
  pr vate[t ] val  mcac dFoundCounter: Counter =  mcac dStats.counter("found")

  // Records t  state of t  cac  load after ser al zat on. T 
  // pol cy may transform a value that was successfully loaded from
  // cac   nto any result type, wh ch  s why   expl c ly track
  // "found" and "not_found" above.  f `stale` + `fresh`  s not equal
  // to `found`, t n    ans that t  pol cy has translated a found
  // value  nto a m ss or fa lure. T  pol cy may do t   n order to
  // cause t  cach ng f lter to treat t  value that was found  n
  // cac   n t  way   would have treated a m ss or fa lure from
  // cac .
  pr vate[t ] val resultStats: StatsRece ver = statsRece ver.scope("result")
  pr vate[t ] val resultFreshCounter: Counter = resultStats.counter("fresh")
  pr vate[t ] val resultStaleCounter: Counter = resultStats.counter("stale")
  pr vate[t ] val resultM ssCounter: Counter = resultStats.counter("m ss")
  pr vate[t ] val resultFa lureCounter: Counter = resultStats.counter("fa lure")

  // Used for record ng except ons that occurred dur ng
  // deser al zat on. T  w ll never be  ncre nted  f t 
  // deser al zer returns a result, even  f t  result  s a
  // [[Cac Result.Fa lure]]. See t  com nt w re t  stat  s
  //  ncre nted for more deta ls.
  pr vate[t ] val deser al zeFa lureStats: StatsRece ver = statsRece ver.scope("deser al ze")

  pr vate[t ] val notSer al zedCounter: Counter = statsRece ver.counter("not_ser al zed")

  /**
   * Load a batch of values from cac . Mostly t  deals w h
   * convert ng t  [[ mcac d.GetResult]] to a
   * [[Seq[Cac dResult[V]]]]. T  result  s  n t  sa  order as t 
   * keys, and t re w ll always be an entry for each key. T   thod
   * should never return a [[Future.except on]].
   */
  def get(keys: Seq[K]): Future[Seq[Cac Result[V]]] = {
     mcac dGetCounter. ncr(keys.s ze)
    val cac Keys: Seq[Str ng] = keys.map(keySer al zer)
     f (logger. sTraceEnabled) {
      logger.trace {
        val l nes: Seq[Str ng] = keys.z p(cac Keys).map { case (k, c) => s"\n  $k ($c)" }
        "Start ng load for keys:" + l nes.mkStr ng
      }
    }

     mcac dCl ent
      .getResult(cac Keys)
      .map { getResult =>
         mcac dNotFoundCounter. ncr(getResult.m sses.s ze)
        val results: Seq[Cac Result[V]] =
          cac Keys.map { cac Key =>
            val result: Cac Result[V] =
              getResult.h s.get(cac Key) match {
                case So ( mcac dValue) =>
                   mcac dFoundCounter. ncr()
                  try {
                    valueSer al zer.deser al ze( mcac dValue.value)
                  } catch {
                    case NonFatal(e) =>
                      //  f t  ser al zer throws an except on, t n
                      // t  ser al zed value was malfor d.  n that
                      // case,   record t  fa lure so that   can be
                      // detected and f xed, but treat   as a cac 
                      // m ss. T  reason that   treat   as a m ss
                      // rat r than a fa lure  s that a m ss w ll
                      // cause a wr e back to cac , and   want to
                      // wr e a val d result back to cac  to replace
                      // t  bad entry that   just loaded.
                      //
                      // A ser al zer  s free to return M ss  self to
                      // obta n t  behav or  f    s expected or
                      // des red, to avo d t  logg ng and stats (and
                      // t  m nor over ad of catch ng an except on).
                      //
                      // T  except ons are tracked separately from
                      // ot r except ons so that    s easy to see
                      // w t r t  deser al zer  self ever throws an
                      // except on.
                      except onStatsHandler.record(deser al zeFa lureStats, e)
                      logger.warn(s"Fa led deser al z ng value for cac  key $cac Key", e)
                      Cac Result.M ss
                  }

                case None  f getResult.m sses.conta ns(cac Key) =>
                  Cac Result.M ss

                case None =>
                  val except on =
                    getResult.fa lures.get(cac Key) match {
                      case None =>
                        // To get  re, t  was not a h  or a m ss,
                        // so   expect t  key to be present  n
                        // fa lures.  f    s not, t n e  r t 
                        // contract of getResult was v olated, or t 
                        //  thod  s so how attempt ng to access a
                        // result for a key that was not
                        // loaded. E  r of t se  nd cates a bug, so
                        //   log a h gh pr or y log  ssage.
                        logger.error(
                          s"Key $cac Key not found  n h s, m sses or fa lures. " +
                            "T   nd cates a bug  n t   mcac d l brary or " +
                            "Cac Operat ons.load"
                        )
                        //   return t  as a fa lure because that
                        // w ll cause t  repo to be consulted and t 
                        // value *not* to be wr ten back to cac ,
                        // wh ch  s probably t  safest th ng to do
                        // ( f   don't know what's go ng on, default
                        // to an uncac d repo).
                        new  llegalStateExcept on

                      case So (e) =>
                        e
                    }
                  except onStatsHandler.record( mcac dStats, except on)
                  Cac Result.Fa lure(except on)
              }

            // Count each k nd of Cac Result, to make   poss ble to
            // see how effect ve t  cach ng  s.
            result match {
              case Cac Result.Fresh(_) => resultFreshCounter. ncr()
              case Cac Result.Stale(_) => resultStaleCounter. ncr()
              case Cac Result.M ss => resultM ssCounter. ncr()
              case Cac Result.Fa lure(_) => resultFa lureCounter. ncr()
            }

            result
          }

         f (logger. sTraceEnabled) {
          logger.trace {
            val l nes: Seq[Str ng] =
              (keys, cac Keys, results).z pped.map {
                case (key, cac Key, result) => s"\n  $key ($cac Key) -> $result"
              }

            "Cac  results:" + l nes.mkStr ng
          }
        }

        results
      }
      .handle {
        case e =>
          //  f t re  s a fa lure from t   mcac d cl ent, fan  
          // out to each cac  key, so that t  caller does not need
          // to handle fa lure of t  batch d fferently than fa lure
          // of  nd v dual keys. T  should be rare anyway, s nce t 
          //  mcac d cl ent already does t  for common F nagle
          // except ons
          resultFa lureCounter. ncr(keys.s ze)
          val t Fa lure: Cac Result[V] = Cac Result.Fa lure(e)
          keys.map { _ =>
            // Record t  as many t  s as   would  f    re  n t  GetResult
            except onStatsHandler.record( mcac dStats, e)
            t Fa lure
          }
      }
  }

  //  ncre nted for every attempt to `set` a key  n value.
  pr vate[t ] val  mcac dSetCounter: Counter =  mcac dStats.counter("set")

  /**
   * Wr e an entry back to cac , us ng `set`.  f t  ser al zer does
   * not ser al ze t  value, t n t   thod w ll  m d ately return
   * w h success.
   */
  def set(key: K, value: V): Future[Un ] =
    valueSer al zer.ser al ze(value) match {
      case So ((exp ry, ser al zed)) =>
         f (logger. sTraceEnabled) {
          logger.trace(s"Wr  ng back to cac  $key -> $value (exp ry = $exp ry)")
        }
         mcac dSetCounter. ncr()
         mcac dCl ent
          .set(key = keySer al zer(key), flags = 0, exp ry = exp ry, value = ser al zed)
          .onFa lure(except onStatsHandler.record( mcac dStats, _))

      case None =>
         f (logger. sTraceEnabled) {
          logger.trace(s"Not wr  ng back $key -> $value")
        }
        notSer al zedCounter. ncr()
        Future.Done
    }
}
