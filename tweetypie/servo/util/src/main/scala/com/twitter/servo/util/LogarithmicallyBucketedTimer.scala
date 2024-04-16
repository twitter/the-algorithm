package com.tw ter.servo.ut l

 mport com.tw ter.f nagle.stats.{StatsRece ver, Stat}
 mport com.tw ter.ut l.Future

object Logar hm callyBucketedT  r {
  val LatencyStatNa  = "latency_ms"
}

/**
 *  lper to bucket t m ngs by quant y.   produces base10 and baseE log buckets.
 */
class Logar hm callyBucketedT  r(
  statsRece ver: StatsRece ver,
  pref x: Str ng = Logar hm callyBucketedT  r.LatencyStatNa ) {

  protected[t ] def base10Key(count:  nt) =
    pref x + "_log_10_" + math.floor(math.log10(count)).to nt

  protected[t ] def baseEKey(count:  nt) =
    pref x + "_log_E_" + math.floor(math.log(count)).to nt

  /**
   * takes t  base10 and baseE logs of t  count, adds t m ngs to t 
   * appropr ate buckets
   */
  def apply[T](count:  nt = 0)(f: => Future[T]) = {
    Stat.t  Future(statsRece ver.stat(pref x)) {
      // only bucket ze for pos  ve, non-zero counts
       f (count > 0) {
        Stat.t  Future(statsRece ver.stat(base10Key(count))) {
          Stat.t  Future(statsRece ver.stat(baseEKey(count))) {
            f
          }
        }
      } else {
        f
      }
    }
  }
}
